package cn.mcmod.sakura.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Utility for rendering fluid textures in GUI screens.
 * Replaces the removed RenderUtils.renderFluidStack() from MMLib.
 */
public class FluidRenderHelper {

    /**
     * Renders a fluid stack into a rectangular area of the GUI, tiling the fluid's
     * still texture as needed to fill the specified width and height.
     *
     * @param guiGraphics the GuiGraphics context
     * @param x           left edge of the render area (screen coordinates)
     * @param y           top edge of the render area (screen coordinates)
     * @param width       width of the area to fill
     * @param height      height of the area to fill
     * @param fluidStack  the fluid to render
     */
    public static void renderFluidStack(GuiGraphics guiGraphics, int x, int y, int width, int height, FluidStack fluidStack) {
        if (fluidStack.isEmpty() || width <= 0 || height <= 0) {
            return;
        }

        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = extensions.getStillTexture(fluidStack);
        if (stillTexture == null) {
            return;
        }

        int tintColor = extensions.getTintColor(fluidStack);
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(stillTexture);

        // Extract ARGB components from the tint color
        int a = (tintColor >> 24) & 0xFF;
        int r = (tintColor >> 16) & 0xFF;
        int g = (tintColor >> 8) & 0xFF;
        int b = tintColor & 0xFF;

        // If alpha is 0, default to fully opaque (some fluids return 0x00RRGGBB)
        if (a == 0) {
            a = 255;
        }

        guiGraphics.pose().pushPose();
        guiGraphics.setColor(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);

        int tileSize = 16;

        // Tile the sprite across the area
        int xRemaining = width;
        for (int xOffset = 0; xOffset < width; xOffset += tileSize) {
            int drawWidth = Math.min(tileSize, xRemaining);
            int yRemaining = height;
            for (int yOffset = 0; yOffset < height; yOffset += tileSize) {
                int drawHeight = Math.min(tileSize, yRemaining);
                guiGraphics.blit(
                        x + xOffset, y + yOffset,
                        0,  // blitOffset
                        drawWidth, drawHeight,
                        sprite
                );
                yRemaining -= tileSize;
            }
            xRemaining -= tileSize;
        }

        // Reset color back to default
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.pose().popPose();
    }
}
