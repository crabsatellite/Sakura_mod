package cn.mcmod.sakura.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import cn.mcmod.sakura.SakuraMod;

@EventBusSubscriber(modid = SakuraMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SakuraNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(SakuraMod.MODID);
        registrar.playToServer(SheathKeyPacket.TYPE, SheathKeyPacket.STREAM_CODEC, SheathKeyPacket::handle);
    }
}
