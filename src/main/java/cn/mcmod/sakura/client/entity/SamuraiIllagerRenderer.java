package cn.mcmod.sakura.client.entity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.layers.LayerRegistry;
import cn.mcmod.sakura.entity.SamuraiIllagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;

public class SamuraiIllagerRenderer extends MobRenderer<SamuraiIllagerEntity, SamuraiIllagerModel> {
    private static final ResourceLocation ILLAGER_TEXTURE = ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "textures/entity/illager/samuraiillager.png");

    public SamuraiIllagerRenderer(EntityRendererProvider.Context context) {
        super(context, new SamuraiIllagerModel(context.bakeLayer(LayerRegistry.SAMURAI_ILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                    SamuraiIllagerEntity entity, float limbSwing, float limbSwingAmount,
                    float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
                if (entity.isAggressive()) {
                    super.render(poseStack, buffer, packedLight, entity, limbSwing, limbSwingAmount,
                            partialTick, ageInTicks, netHeadYaw, headPitch);
                }
            }
        });
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
