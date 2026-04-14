package cn.mcmod.sakura.item;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * Sheath item for Sakura mod.
 * <p>
 * A scabbard with wood-tier durability. Can block like a shield when right-clicked.
 * When the player has a katana in the other hand, right-clicking the sheath will combine
 * them into a SheathKatanaItem (sheathed katana / iaido stance).
 */
public class SheathItem extends Item {

    public SheathItem(Properties properties) {
        super(properties.durability(Tiers.WOOD.getUses()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack sheathStack = player.getItemInHand(hand);

        // Check if the other hand has a katana for combining
        InteractionHand otherHand = (hand == InteractionHand.MAIN_HAND)
                ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherStack = player.getItemInHand(otherHand);

        if (otherStack.getItem() instanceof KatanaItem) {
            // Combine sheath + katana into SheathKatanaItem
            if (!level.isClientSide()) {
                // Pick the correct sheathed variant based on katana type
                Item sheathKatanaType;
                if (otherStack.is(ItemRegistry.SAKURA_KATANA.get())) {
                    sheathKatanaType = ItemRegistry.SAKURA_KATANA_SHEATH.get();
                } else {
                    sheathKatanaType = ItemRegistry.KATANA_SHEATH.get();
                }
                ItemStack sheathKatana = new ItemStack(sheathKatanaType);
                // Store the katana blade data using DataComponents (1.21 API)
                net.minecraft.nbt.CompoundTag customTag = new net.minecraft.nbt.CompoundTag();
                customTag.put("SheathBlade", (net.minecraft.nbt.CompoundTag) otherStack.copy().save(player.registryAccess(), new net.minecraft.nbt.CompoundTag()));
                sheathKatana.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(customTag));

                // Consume the katana from the other hand
                otherStack.shrink(1);

                // Replace the sheath with the sheathed katana
                player.setItemInHand(hand, sheathKatana);

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARMOR_EQUIP_IRON, player.getSoundSource(), 1.0F, 1.2F);
            }
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        // No katana in other hand: use as a blocking item
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(sheathStack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
