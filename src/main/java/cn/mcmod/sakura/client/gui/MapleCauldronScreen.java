package cn.mcmod.sakura.client.gui;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.container.MapleCauldronContainer;
import cn.mcmod_mmf.mmlib.client.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MapleCauldronScreen extends AbstractContainerScreen<MapleCauldronContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(SakuraMod.MODID,
            "textures/gui/maple_pot.png");

    public MapleCauldronScreen(MapleCauldronContainer screenContainer, Inventory inv, Component titleIn) {
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

        // Burning indicator
        if (this.menu.isBurning()) {
            ms.blit(BACKGROUND_TEXTURE, this.leftPos + 71, this.topPos + 59, 176, 17, 14, 14);
        }

        // Progress arrow
        int l = this.menu.getProgressScaled(44);
        ms.blit(BACKGROUND_TEXTURE, this.leftPos + 59, this.topPos + 36, 176, 0, l + 1, 17);

        // Render fluid tank
        this.menu.tileEntity.getFluidTank().ifPresent(fluidTank -> {
            int heightInd = (int) (68.0F * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0) {
                RenderUtils.renderFluidStack(this.leftPos + 35, this.topPos + 78 - heightInd, 16, heightInd, 0.0F,
                        fluidTank.getFluid());
            }
        });
    }
}
