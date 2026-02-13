package cn.mcmod.sakura.client.layers;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.entity.DeerModel;
import cn.mcmod.sakura.client.entity.SamuraiIllagerModel;
import cn.mcmod.sakura.client.render.StoneMortarRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SakuraMod.MODID, value = Dist.CLIENT)
public class LayerRegistry {
    public static final ModelLayerLocation STONE_MORTAR = register("stone_mortar");
    public static final ModelLayerLocation DEER = register("deer");
    public static final ModelLayerLocation SAMURAI_ILLAGER = register("samurai_illager");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(STONE_MORTAR, StoneMortarRenderer::createLayer);
        event.registerLayerDefinition(DEER, DeerModel::createBodyLayer);
        event.registerLayerDefinition(SAMURAI_ILLAGER, SamuraiIllagerModel::createBodyLayer);
    }

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String part) {
        return new ModelLayerLocation(new ResourceLocation(SakuraMod.MODID, path), part);
    }
}
