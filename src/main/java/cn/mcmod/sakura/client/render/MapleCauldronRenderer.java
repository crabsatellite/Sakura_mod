package cn.mcmod.sakura.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod.sakura.block.entity.MapleCauldronBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.joml.Matrix4f;

/**
 * BlockEntityRenderer for the Maple Cauldron.
 * Renders the maple syrup fluid surface inside the cauldron,
 * scaled by how full the 5000 mB tank is.
 * The cauldron model inner area is approximately (2/16, 1/16, 2/16) to (14/16, 13/16, 14/16).
 */
public class MapleCauldronRenderer implements BlockEntityRenderer<MapleCauldronBlockEntity> {

    // Inner bounds of the cauldron (in block-space 0-1 units)
    private static final float INNER_X1 = 2f / 16f;
    private static final float INNER_X2 = 14f / 16f;
    private static final float INNER_Z1 = 2f / 16f;
    private static final float INNER_Z2 = 14f / 16f;
    private static final float BOTTOM_Y = 5f / 16f;
    private static final float MAX_FLUID_Y = 13f / 16f;

    public MapleCauldronRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MapleCauldronBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        FluidTank tank = blockEntity.getFluidTank().orElse(null);
        if (tank == null) return;

        FluidStack fluidStack = tank.getFluid();
        if (fluidStack.isEmpty()) return;

        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions fluidExtensions = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation stillTexture = fluidExtensions.getStillTexture(fluidStack);
        if (stillTexture == null) return;

        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);

        int color = fluidExtensions.getTintColor(fluidStack);
        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red   = ((color >> 16) & 0xFF) / 255f;
        float green  = ((color >> 8) & 0xFF) / 255f;
        float blue   = (color & 0xFF) / 255f;

        // Calculate fluid height based on fill ratio
        float fillRatio = (float) tank.getFluidAmount() / (float) tank.getCapacity();
        float fluidY = BOTTOM_Y + (MAX_FLUID_Y - BOTTOM_Y) * fillRatio;

        poseStack.pushPose();

        VertexConsumer builder = bufferSource.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        // Render the top face of the fluid (quad facing up)
        addVertex(builder, matrix, INNER_X1, fluidY, INNER_Z1, minU, minV, red, green, blue, alpha, packedLight);
        addVertex(builder, matrix, INNER_X1, fluidY, INNER_Z2, minU, maxV, red, green, blue, alpha, packedLight);
        addVertex(builder, matrix, INNER_X2, fluidY, INNER_Z2, maxU, maxV, red, green, blue, alpha, packedLight);
        addVertex(builder, matrix, INNER_X2, fluidY, INNER_Z1, maxU, minV, red, green, blue, alpha, packedLight);

        poseStack.popPose();
    }

    private void addVertex(VertexConsumer builder, Matrix4f matrix, float x, float y, float z,
            float u, float v, float r, float g, float b, float a, int packedLight) {
        builder.vertex(matrix, x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(0, 10)
                .uv2(packedLight)
                .normal(0, 1, 0)
                .endVertex();
    }
}
