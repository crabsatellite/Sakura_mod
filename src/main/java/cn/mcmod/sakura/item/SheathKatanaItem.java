package cn.mcmod.sakura.item;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * Sheathed Katana item for Sakura mod.
 * <p>
 * A katana stored inside its sheath (iaido stance). Right-clicking performs a quick-draw
 * attack that damages nearby enemies and separates the item back into a katana + sheath.
 * Stores the original katana's NBT so enchantments and damage are preserved.
 */
public class SheathKatanaItem extends Item {

    private static final String TAG_BLADE = "SheathBlade";

    private final Supplier<Item> katanaSupplier;
    private final Supplier<Item> sheathSupplier;

    public SheathKatanaItem(Properties properties, Supplier<Item> katanaSupplier, Supplier<Item> sheathSupplier) {
        super(properties);
        this.katanaSupplier = katanaSupplier;
        this.sheathSupplier = sheathSupplier;
    }

    // --- Quick-draw attack on right-click ---

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
        return 20; // Short draw time
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) return;
        if (level.isClientSide()) return;

        int ticksUsed = getUseDuration(stack) - timeLeft;
        if (ticksUsed < 4) return; // Minimum hold time

        // Perform quick-draw slash
        float baseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float drawDamage = baseDamage + 4.0F; // Bonus damage for iaido strike

        var targets = level.getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(2.5D, 0.5D, 2.5D),
                e -> e != player && !player.isAlliedTo(e) && player.distanceToSqr(e) < 10.0D);

        for (LivingEntity target : targets) {
            target.knockback(0.5F,
                    Math.sin(Math.toRadians(player.getYRot())),
                    -Math.cos(Math.toRadians(player.getYRot())));
            target.hurt(player.damageSources().playerAttack(player), drawDamage);
        }

        if (!targets.isEmpty()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            player.sweepAttack();
        }

        // Separate back into katana + sheath
        InteractionHand usedHand = player.getUsedItemHand();
        unsheathe(stack, player, usedHand);
    }

    /**
     * Separates the sheathed katana back into a katana and a sheath.
     */
    private void unsheathe(ItemStack sheathKatanaStack, Player player, InteractionHand hand) {
        // Restore the stored blade
        ItemStack blade;
        CompoundTag tag = sheathKatanaStack.getTag();
        if (tag != null && tag.contains(TAG_BLADE)) {
            blade = ItemStack.of(tag.getCompound(TAG_BLADE));
        } else {
            // Fallback: create a fresh katana
            blade = new ItemStack(katanaSupplier.get());
        }

        // Create the sheath
        ItemStack sheath = new ItemStack(sheathSupplier.get());

        // Put katana in the hand, try to add sheath to inventory
        player.setItemInHand(hand, blade);
        if (!player.getInventory().add(sheath)) {
            player.drop(sheath, false);
        }

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARMOR_EQUIP_IRON, player.getSoundSource(), 1.0F, 0.8F);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }
}
