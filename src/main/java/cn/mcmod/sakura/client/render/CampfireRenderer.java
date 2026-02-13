package cn.mcmod.sakura.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import cn.mcmod.sakura.block.entity.CampfireBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * BlockEntityRenderer for the Sakura Campfire.
 * Renders the item in slot 0 floating and rotating above the campfire.
 * Ported from 1.12.2 RenderTileEntityCampfire.
 */
public class CampfireRenderer implements BlockEntityRenderer<CampfireBlockEntity> {

    public CampfireRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CampfireBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = blockEntity.getItemBurning();
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();

        // Position the item above the campfire center
        // 1.12.2 used: translate(x+0.5, y-0.25, z+0.5) then scale(0.65) then translate(0, 1.8, 0)
        // In 1.20.1 BER, origin is already at the block pos, so we translate relative to that.
        poseStack.translate(0.5D, 0.75D, 0.5D);
        poseStack.scale(0.65F, 0.65F, 0.65F);

        // Rotate the item slowly based on game time
        float gameTime = blockEntity.getLevel() != null
                ? blockEntity.getLevel().getGameTime() + partialTicks : 0;
        float rotation = (gameTime * 2.0F) % 360.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        int posLong = (int) blockEntity.getBlockPos().asLong();
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
                packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), posLong);

        poseStack.popPose();
    }
}
