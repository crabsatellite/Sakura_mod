package cn.mcmod.sakura.item;

import java.util.List;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
 * Kodachi weapon item for Sakura mod.
 * <p>
 * A short Japanese sword: lower damage than the katana but significantly faster attack speed.
 * Has reduced durability (75% of the tier's normal uses) and a weaker sweep attack.
 * Supports right-click blocking like the katana.
 */
public class KodachiItem extends SwordItem {

    private final int reducedMaxDamage;

    public KodachiItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.reducedMaxDamage = (int) (tier.getUses() * 0.75F);
    }

    public KodachiItem(Tier tier, Properties properties) {
        this(tier, 0, -1.6F, properties);
    }

    // --- Reduced durability ---

    @Override
    public int getMaxDamage(ItemStack stack) {
        return reducedMaxDamage;
    }

    // --- Blocking (right-click use) ---

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Prevent dual-wielding kodachis or kodachi + katana
        InteractionHand otherHand = (hand == InteractionHand.MAIN_HAND)
                ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherStack = player.getItemInHand(otherHand);
        if (otherStack.getItem() instanceof KatanaItem || otherStack.getItem() instanceof KodachiItem) {
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

    // --- Weaker sweep attack on release ---

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) return;
        if (level.isClientSide()) return;

        int ticksUsed = getUseDuration(stack) - timeLeft;
        if (ticksUsed < 3) return; // Shorter minimum hold time for the faster weapon

        float sweepRatio = EnchantmentHelper.getSweepingDamageRatio(player);
        if (sweepRatio <= 0.0F) return;

        float baseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        // Weaker sweep: 50% of normal sweep damage
        float sweepDamage = 0.5F + sweepRatio * baseDamage * 0.5F;

        AABB aabb = player.getBoundingBox().inflate(1.5D, 0.4D, 1.5D);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb,
                e -> e != player && !player.isAlliedTo(e) && player.distanceToSqr(e) < 6.25D);

        for (LivingEntity target : targets) {
            target.knockback(0.3F,
                    Math.sin(Math.toRadians(player.getYRot())),
                    -Math.cos(Math.toRadians(player.getYRot())));
            target.hurt(player.damageSources().playerAttack(player), sweepDamage);
        }

        if (!targets.isEmpty()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            player.sweepAttack();
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    // --- Enemy hurt: durability cost ---

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }
}
