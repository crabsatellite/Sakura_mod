package cn.mcmod.sakura.item;

import java.util.List;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Shinai weapon item for Sakura mod.
 * <p>
 * A bamboo practice sword (kendo sword) made from wood tier.
 * Uses BOW-style charge mechanic: hold right-click to charge, release for a powerful strike.
 * Damage scales with charge time: damage = (charge/15)^2 + (charge/15)*2, capped at 8.0.
 * Has a wider AoE sweep than the katana.
 */
public class ShinaiItem extends SwordItem {

    public ShinaiItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    public ShinaiItem(Properties properties) {
        this(Tiers.WOOD, 2, -2.2F, properties);
    }

    // --- Charge-up mechanic (BOW animation) ---

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    // --- Charge-based damage on release ---

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) return;
        if (level.isClientSide()) return;

        int ticksUsed = getUseDuration(stack) - timeLeft;
        if (ticksUsed < 3) return;

        // Calculate charge-based damage: (charge/15)^2 + (charge/15)*2, capped at 8.0
        float chargeRatio = ticksUsed / 15.0F;
        float damage = (chargeRatio * chargeRatio) + (chargeRatio * 2.0F);
        damage = Math.min(damage, 8.0F);

        if (damage < 0.5F) return;

        // Wider AoE sweep than katana (3.0 radius vs 2.0)
        AABB aabb = player.getBoundingBox().inflate(3.0D, 0.75D, 3.0D);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb,
                e -> e != player && !player.isAlliedTo(e) && player.distanceToSqr(e) < 16.0D);

        for (LivingEntity target : targets) {
            target.knockback(0.6F,
                    Math.sin(Math.toRadians(player.getYRot())),
                    -Math.cos(Math.toRadians(player.getYRot())));
            target.hurt(player.damageSources().playerAttack(player), damage);
        }

        if (!targets.isEmpty()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            player.sweepAttack();
        }

        stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
    }

    // --- Enemy hurt: durability cost ---

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }
}
