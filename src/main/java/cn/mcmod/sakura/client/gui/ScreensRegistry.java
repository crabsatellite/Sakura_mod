package cn.mcmod.sakura.client.gui;

import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.container.ContainerRegistry;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = SakuraMod.MODID, value = Dist.CLIENT)
public class ScreensRegistry {
    @SubscribeEvent
    public static void screenRegistry(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ContainerRegistry.STONE_MORTAR.get(), StoneMortarScreen::new);
        event.register(ContainerRegistry.COOKING_POT.get(), CookingPotScreen::new);
        event.register(ContainerRegistry.FERMENTER.get(), FermenterScreen::new);
        event.register(ContainerRegistry.DISTILLER.get(), DistillerScreen::new);
        event.register(ContainerRegistry.CAMPFIRE_POT.get(), CampfirePotScreen::new);
        event.register(ContainerRegistry.MAPLE_CAULDRON.get(), MapleCauldronScreen::new);
        event.register(ContainerRegistry.BARREL_OUTPUT.get(), BarrelOutputScreen::new);
    }

}
