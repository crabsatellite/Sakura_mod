package cn.mcmod.sakura.client.entity;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.layers.LayerRegistry;
import cn.mcmod.sakura.entity.DeerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DeerRenderer extends MobRenderer<DeerEntity, DeerModel> {
    private static final ResourceLocation DEER_TEXTURE = new ResourceLocation(SakuraMod.MODID, "textures/entity/deer.png");

    public DeerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerModel(context.bakeLayer(LayerRegistry.DEER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(DeerEntity entity) {
        return DEER_TEXTURE;
    }
}
