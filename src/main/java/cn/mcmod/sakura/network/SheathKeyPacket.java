package cn.mcmod.sakura.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.KatanaItem;
import cn.mcmod.sakura.item.SheathItem;

/**
 * Packet sent from client to server when the player presses the sheath keybinding.
 */
public record SheathKeyPacket() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SheathKeyPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sheath_key"));

    public static final StreamCodec<ByteBuf, SheathKeyPacket> STREAM_CODEC =
            StreamCodec.unit(new SheathKeyPacket());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SheathKeyPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;

            // Look for a sheath in either hand
            InteractionHand sheathHand = findSheathHand(player);
            if (sheathHand == null) return;

            InteractionHand otherHand = (sheathHand == InteractionHand.MAIN_HAND)
                    ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack otherStack = player.getItemInHand(otherHand);

            // The other hand must contain a katana
            if (!(otherStack.getItem() instanceof KatanaItem)) return;

            // Pick the correct sheathed variant based on katana type
            Item sheathKatanaType;
            if (otherStack.is(ItemRegistry.SAKURA_KATANA.get())) {
                sheathKatanaType = ItemRegistry.SAKURA_KATANA_SHEATH.get();
            } else {
                sheathKatanaType = ItemRegistry.KATANA_SHEATH.get();
            }

            ItemStack sheathKatana = new ItemStack(sheathKatanaType);
            net.minecraft.nbt.CompoundTag customTag = new net.minecraft.nbt.CompoundTag();
            customTag.put("SheathBlade", (net.minecraft.nbt.CompoundTag) otherStack.copy().save(player.registryAccess(), new net.minecraft.nbt.CompoundTag()));
            sheathKatana.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(customTag));

            // Consume the katana from the other hand
            otherStack.shrink(1);

            // Replace the sheath with the sheathed katana
            player.setItemInHand(sheathHand, sheathKatana);

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ARMOR_EQUIP_IRON, player.getSoundSource(), 1.0F, 1.2F);
        });
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
