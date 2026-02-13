package cn.mcmod.sakura.client.gui;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.container.BarrelOutputContainer;
import cn.mcmod_mmf.mmlib.client.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BarrelOutputScreen extends AbstractContainerScreen<BarrelOutputContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(SakuraMod.MODID,
            "textures/gui/barrel_out.png");

    public BarrelOutputScreen(BarrelOutputContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics ms, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics ms, int mouseX, int mouseY) {
        super.renderLabels(ms, mouseX, mouseY);
        ms.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics ms, float partialTicks, int mouseX, int mouseY) {
        if (this.minecraft == null) {
            return;
        }
        ms.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Render fluid tank level (horizontal bar)
        this.menu.tileEntity.getFluidTank().ifPresent(fluidTank -> {
            int heightInd = (int) (162.0F * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0) {
                RenderUtils.renderFluidStack(this.leftPos + 168 - heightInd, this.topPos + 60, heightInd, 16, 0.0F,
                        fluidTank.getFluid());
            }
        });
    }
}
