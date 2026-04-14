package cn.mcmod.sakura.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.network.SheathKeyPacket;

/**
 * Handles GAME bus events on the client side.
 * Key input events must be listened on the GAME bus, not the MOD bus.
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = SakuraMod.MODID, value = Dist.CLIENT)
public class SakuraClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ClientEvents.SHEATH_KEY.consumeClick()) {
            PacketDistributor.sendToServer(new SheathKeyPacket());
        }
    }
}
