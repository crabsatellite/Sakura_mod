package cn.mcmod.sakura.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.container.FermenterContainer;
import cn.mcmod.sakura.client.gui.FluidRenderHelper;

public class FermenterScreen extends AbstractContainerScreen<FermenterContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "textures/gui/barrel.png");

    public FermenterScreen(FermenterContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics ms, final int mouseX, final int mouseY, float partialTicks) {
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
        // Render UI background
        if (this.minecraft == null) {
            return;
        }
//        RenderUtils.setup(BACKGROUND_TEXTURE);
        ms.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Render progress arrow
        int l = this.menu.getCookProgressionScaled();
        ms.blit(BACKGROUND_TEXTURE, this.leftPos + 75, this.topPos + 45, 176, 0, l + 1, 17);

        int m = this.menu.getWorking();
        ms.blit(BACKGROUND_TEXTURE, this.leftPos + 78, this.topPos + 44 - m, 176, 53 - m, 18, m);
        
        {
            var fluidTank = this.menu.tileEntity.getInputFluidTank();
            int heightInd = (int) (52.0F * ((float)fluidTank.getFluidAmount() / (float)fluidTank.getCapacity()));
            if (heightInd > 0) {
                FluidRenderHelper.renderFluidStack(ms, this.leftPos + 33, this.topPos + 69 - heightInd, 16, heightInd, fluidTank.getFluid());
            }
        }
        {
            var fluidTank = this.menu.tileEntity.getOutputFluidTank();
            int heightInd = (int) (52.0F * ((float)fluidTank.getFluidAmount() / (float)fluidTank.getCapacity()));
            if (heightInd > 0) {
                FluidRenderHelper.renderFluidStack(ms, this.leftPos + 125, this.topPos + 69 - heightInd, 16, heightInd, fluidTank.getFluid());
            }
        }
    }
}
