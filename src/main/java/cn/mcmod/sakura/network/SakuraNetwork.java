package cn.mcmod.sakura.network;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SakuraNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SakuraMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, SheathKeyPacket.class,
                SheathKeyPacket::encode, SheathKeyPacket::decode, SheathKeyPacket::handle);
    }
}
