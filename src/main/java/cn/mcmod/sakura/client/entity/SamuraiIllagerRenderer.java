package cn.mcmod.sakura.client.entity;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.layers.LayerRegistry;
import cn.mcmod.sakura.entity.SamuraiIllagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class SamuraiIllagerRenderer extends MobRenderer<SamuraiIllagerEntity, SamuraiIllagerModel> {
    private static final ResourceLocation ILLAGER_TEXTURE = new ResourceLocation(SakuraMod.MODID, "textures/entity/illager/samuraiillager.png");

    public SamuraiIllagerRenderer(EntityRendererProvider.Context context) {
        super(context, new SamuraiIllagerModel(context.bakeLayer(LayerRegistry.SAMURAI_ILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(SamuraiIllagerEntity entity) {
        return ILLAGER_TEXTURE;
    }

    @Override
    protected void scale(SamuraiIllagerEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
