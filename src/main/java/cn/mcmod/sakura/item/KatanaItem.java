package cn.mcmod.sakura.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Katana weapon item for Sakura mod.
 * <p>
 * A Japanese-style sword that can block like a shield when right-clicked.
 * Supports sweeping attacks when the Sweeping Edge enchantment is applied.
 * Players cannot dual-wield two katanas -- the off-hand katana is forcibly
 * moved back to inventory every tick (matching 1.12.2 behavior).
 */
public class KatanaItem extends SwordItem {

    public KatanaItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    public KatanaItem(Tier tier, Properties properties) {
        this(tier, 3, -2.2F, properties);
    }

    // --- Forced dual-wield prevention (1.12.2 onUpdate) ---

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide()) return;
        if (!(entity instanceof Player player)) return;

        ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);

        if (!mainhand.isEmpty() && !offhand.isEmpty()
                && mainhand.getItem() instanceof KatanaItem
                && offhand.getItem() instanceof KatanaItem) {
            // Forcibly remove the off-hand katana
            player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
            if (!player.getInventory().add(offhand)) {
                player.drop(offhand, false);
            }
            player.displayClientMessage(
                    Component.translatable("sakura.katana.wrong_duel"), false);
        }
    }

    // --- Blocking (right-click use) ---

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Prevent dual-wielding katanas: if the other hand also holds a katana, deny use
        InteractionHand otherHand = (hand == InteractionHand.MAIN_HAND)
                ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherStack = player.getItemInHand(otherHand);
        if (otherStack.getItem() instanceof KatanaItem) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    // --- Sweep attack on release ---

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) return;
        if (level.isClientSide()) return;

        int ticksUsed = getUseDuration(stack) - timeLeft;
        if (ticksUsed < 5) return; // Minimum hold time before sweep triggers

        float sweepRatio = EnchantmentHelper.getSweepingDamageRatio(player);
        if (sweepRatio <= 0.0F) return;

        float baseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float enchantBonus = EnchantmentHelper.getDamageBonus(stack, entityLiving.getMobType()) / 1.2F;
        float sweepDamage = 2.0F + sweepRatio * (baseDamage + enchantBonus);

        // Scale sweep area with enchantment level (matching 1.12.2 behavior)
        double sweepRange = 1.4D + sweepRatio * 1.2D;
        double sweepHeight = 0.3D + sweepRatio * 0.15D;
        AABB aabb = player.getBoundingBox().inflate(sweepRange, sweepHeight, sweepRange);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb,
                e -> e != player && !player.isAlliedTo(e) && player.distanceToSqr(e) < 9.0D);

        float knockbackStrength = 0.4F + 0.4F * sweepRatio;
        for (LivingEntity target : targets) {
            // Disable shields on blocking players (matching 1.12.2)
            if (target instanceof Player targetPlayer && targetPlayer.isBlocking()) {
                targetPlayer.disableShield(false);
            }

            target.knockback(knockbackStrength,
                    Math.sin(Math.toRadians(player.getYRot())),
                    -Math.cos(Math.toRadians(player.getYRot())));
            target.hurt(player.damageSources().playerAttack(player), sweepDamage);
        }

        if (!targets.isEmpty()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            player.sweepAttack();
            stack.hurtAndBreak(2, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        // Apply cooldown after sweep attack (matching 1.12.2's 25-tick cooldown)
        player.getCooldowns().addCooldown(this, 25);
    }

    // --- Enemy hurt: durability cost ---

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    // --- Cobweb harvesting ---

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, net.minecraft.world.level.block.state.BlockState state) {
        return state.is(net.minecraft.world.level.block.Blocks.COBWEB) || super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, net.minecraft.world.level.block.state.BlockState state) {
        if (state.is(net.minecraft.world.level.block.Blocks.COBWEB)) {
            return 15.0F;
        }
        return super.getDestroySpeed(stack, state);
    }
}
