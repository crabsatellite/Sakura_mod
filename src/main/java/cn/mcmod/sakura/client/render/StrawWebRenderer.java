package cn.mcmod.sakura.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import cn.mcmod.sakura.block.entity.StrawWebBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

/**
 * BlockEntityRenderer for the Straw Web (drying rack).
 * Renders the item lying flat on the rack surface.
 * Ported from 1.12.2 RenderTileEntityWeb.
 */
public class StrawWebRenderer implements BlockEntityRenderer<StrawWebBlockEntity> {

    public StrawWebRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StrawWebBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = blockEntity.getStoredItem();
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();

        // Position the item on the rack surface.
        // The rack is 4px tall (0-4/16), so place the item at the top of it.
        // 1.12.2 used: translate(x+0.5, y+0.2, z+0.5), scale(0.75), rotate facing on Y, rotate 90 on X
        poseStack.translate(0.5D, 0.2D, 0.5D);
        poseStack.scale(0.75F, 0.75F, 0.75F);

        // Rotate item flat (lying on the rack) -- 90 degrees around X axis
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        int posLong = (int) blockEntity.getBlockPos().asLong();
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
                packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), posLong);

        poseStack.popPose();
    }
}
