package cn.mcmod.sakura.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import cn.mcmod.sakura.block.entity.CampfirePotBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.joml.Matrix4f;

/**
 * BlockEntityRenderer for the Campfire Pot.
 * Renders the fluid surface inside the pot, scaled by how full the tank is.
 * The pot model has walls from the block edges with the interior roughly
 * from (2/16, 5/16, 2/16) to (14/16, 16/16, 14/16), with the bottom
 * (floor of the pot) at Y = 5/16 (the solid bottom element in the model).
 * The fluid surface rises from the bottom up to a max of ~14/16.
 */
public class CampfirePotRenderer implements BlockEntityRenderer<CampfirePotBlockEntity> {

    // Inner bounds of the pot (in block-space units, 0-1)
    private static final float INNER_X1 = 2f / 16f;
    private static final float INNER_X2 = 14f / 16f;
    private static final float INNER_Z1 = 2f / 16f;
    private static final float INNER_Z2 = 14f / 16f;
    private static final float BOTTOM_Y = 5f / 16f;    // top of the bottom slab element
    private static final float MAX_FLUID_Y = 15f / 16f; // just below the rim

    public CampfirePotRenderer(BlockEntityRendererProvider.Context context) {
    }

    // Per-slot offsets for the 9 ingredient items inside the pot.
    // Ported from 1.12.2 RenderTileEntityCampfirePot, converted from
    // GlStateManager scale(0.3) + translate(...) to block-space coordinates.
    // Original: scale 0.3 then translate => actual offset = translate * 0.3
    // These are relative to the pot center (0.5, 0, 0.5).
    private static final float[][] ITEM_OFFSETS = {
        { 0.03F, 0.0F },   // slot 0: (0.1*0.3, 0.0*0.3)
        { 0.15F, 0.0F },   // slot 1: (0.5*0.3, 0.0*0.3)
        {-0.07F, 0.225F},  // slot 2: (-0.23*0.3, 0.75*0.3)
        { 0.15F,-0.225F},  // slot 3: (0.5*0.3, -0.75*0.3)
        {-0.15F,-0.15F},   // slot 4: (-0.5*0.3, -0.5*0.3)
        { 0.18F, 0.15F},   // slot 5: (0.6*0.3, 0.5*0.3)
        {-0.07F, 0.225F},  // slot 6: (-0.23*0.3, 0.75*0.3)
        {-0.12F,-0.225F},  // slot 7: (-0.4*0.3, -0.75*0.3)
        {-0.15F,-0.15F},   // slot 8: (-0.5*0.3, -0.5*0.3)
    };

    // Per-slot rotation axis variants (X, Y) to give visual variety.
    // Ported from 1.12.2: slots used different rotate(rot, axisX, axisY, 0).
    private static final float[][] ITEM_ROT_AXES = {
        {1.0F, 1.0F},  // slot 0
        {0.0F, 1.0F},  // slot 1
        {0.3F, 1.0F},  // slot 2
        {0.0F, 1.0F},  // slot 3
        {0.0F, 1.0F},  // slot 4
        {0.0F, 1.0F},  // slot 5
        {0.3F, 1.0F},  // slot 6
        {0.0F, 1.0F},  // slot 7
        {0.0F, 1.0F},  // slot 8
    };

    @Override
    public void render(CampfirePotBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        // --- Render ingredient items inside the pot ---
        renderItems(blockEntity, partialTicks, poseStack, bufferSource, packedLight, packedOverlay);

        // --- Render fluid surface ---
        FluidTank tank = blockEntity.getFluidTank();
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
        emitVertex(builder, matrix, INNER_X1, fluidY, INNER_Z1, minU, minV, red, green, blue, alpha, packedLight);
        emitVertex(builder, matrix, INNER_X1, fluidY, INNER_Z2, minU, maxV, red, green, blue, alpha, packedLight);
        emitVertex(builder, matrix, INNER_X2, fluidY, INNER_Z2, maxU, maxV, red, green, blue, alpha, packedLight);
        emitVertex(builder, matrix, INNER_X2, fluidY, INNER_Z1, maxU, minV, red, green, blue, alpha, packedLight);

        poseStack.popPose();
    }

    /**
     * Renders up to 9 ingredient items floating and rotating inside the pot.
     * Ported from 1.12.2 RenderTileEntityCampfirePot.renderItem().
     */
    private void renderItems(CampfirePotBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        float gameTime = blockEntity.getLevel() != null
                ? blockEntity.getLevel().getGameTime() + partialTicks : 0;
        float rotation = (gameTime * 2.0F) % 360.0F;
        int posLong = (int) blockEntity.getBlockPos().asLong();

        for (int i = 0; i < CampfirePotBlockEntity.INPUT_SLOTS; i++) {
            ItemStack stack = blockEntity.getInventory().getStackInSlot(i);
            if (stack.isEmpty()) continue;

            poseStack.pushPose();

            // Translate to pot center, then offset per slot
            // Items sit at Y ~0.75 (inside the pot above the bottom at 5/16)
            poseStack.translate(
                    0.5D + ITEM_OFFSETS[i][0],
                    0.75D,
                    0.5D + ITEM_OFFSETS[i][1]);
            poseStack.scale(0.3F, 0.3F, 0.3F);

            // Rotate with per-slot axis variation
            float axisX = ITEM_ROT_AXES[i][0];
            // Approximate the 1.12.2 rotate(angle, axisX, axisY, 0)
            // by combining Y and X rotations
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            if (axisX != 0.0F) {
                poseStack.mulPose(Axis.XP.rotationDegrees(rotation * axisX));
            }

            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
                    packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), posLong + i);

            poseStack.popPose();
        }
    }

    private void emitVertex(VertexConsumer builder, Matrix4f matrix, float x, float y, float z,
            float u, float v, float r, float g, float b, float a, int packedLight) {
        builder.addVertex(matrix, x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setUv1(0, 10)
                .setLight(packedLight)
                .setNormal(0, 1, 0);
    }
}
