package cn.mcmod.sakura.network;

import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.KatanaItem;
import cn.mcmod.sakura.item.SheathItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Packet sent from client to server when the player presses the sheath keybinding.
 * This triggers the "sheath in" action: if the player is holding a sheath in one hand
 * and a katana in the other, they are combined into a SheathKatanaItem.
 */
public class SheathKeyPacket {

    public SheathKeyPacket() {
    }

    public static void encode(SheathKeyPacket msg, FriendlyByteBuf buf) {
        // No data needed - server reads player inventory directly
    }

    public static SheathKeyPacket decode(FriendlyByteBuf buf) {
        return new SheathKeyPacket();
    }

    public static void handle(SheathKeyPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            // Look for a sheath in either hand
            InteractionHand sheathHand = findSheathHand(player);
            if (sheathHand == null) return;

            InteractionHand otherHand = (sheathHand == InteractionHand.MAIN_HAND)
                    ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack otherStack = player.getItemInHand(otherHand);

            // The other hand must contain a katana
            if (!(otherStack.getItem() instanceof KatanaItem)) return;

            ItemStack sheathStack = player.getItemInHand(sheathHand);

            // Pick the correct sheathed variant based on katana type
            Item sheathKatanaType;
            if (otherStack.is(ItemRegistry.SAKURA_KATANA.get())) {
                sheathKatanaType = ItemRegistry.SAKURA_KATANA_SHEATH.get();
            } else {
                sheathKatanaType = ItemRegistry.KATANA_SHEATH.get();
            }

            ItemStack sheathKatana = new ItemStack(sheathKatanaType);
            net.minecraft.nbt.CompoundTag tag = sheathKatana.getOrCreateTag();
            tag.put("SheathBlade", otherStack.copy().save(new net.minecraft.nbt.CompoundTag()));

            // Consume the katana from the other hand
            otherStack.shrink(1);

            // Replace the sheath with the sheathed katana
            player.setItemInHand(sheathHand, sheathKatana);

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ARMOR_EQUIP_IRON, player.getSoundSource(), 1.0F, 1.2F);
        });
        ctx.setPacketHandled(true);
    }

    private static InteractionHand findSheathHand(ServerPlayer player) {
        if (player.getMainHandItem().getItem() instanceof SheathItem) {
            return InteractionHand.MAIN_HAND;
        }
        if (player.getOffhandItem().getItem() instanceof SheathItem) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }
}
