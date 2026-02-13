package cn.mcmod.sakura.client;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.network.SakuraNetwork;
import cn.mcmod.sakura.network.SheathKeyPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles FORGE bus events on the client side.
 * Key input events must be listened on the FORGE bus, not the MOD bus.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SakuraMod.MODID, value = Dist.CLIENT)
public class SakuraClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ClientEvents.SHEATH_KEY.consumeClick()) {
            SakuraNetwork.CHANNEL.sendToServer(new SheathKeyPacket());
        }
    }
}
