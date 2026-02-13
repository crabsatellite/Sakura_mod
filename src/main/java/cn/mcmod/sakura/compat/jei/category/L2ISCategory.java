package cn.mcmod.sakura.compat.jei.category;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.compat.jei.JEIPlugin;
import cn.mcmod.sakura.recipes.LiquidToItemRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

/**
 * JEI recipe category for Liquid-to-Item (L2IS) conversions.
 * Shows: input fluid + container item -> output item.
 * Used by the Barrel Output block.
 */
public class L2ISCategory implements IRecipeCategory<LiquidToItemRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(SakuraMod.MODID, "liquid_to_item");
    protected final IDrawableAnimated arrow;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public L2ISCategory(IGuiHelper helper) {
        title = Component.translatable("sakura.jei.liquid_to_item");
        ResourceLocation backgroundImage = new ResourceLocation(SakuraMod.MODID, "textures/gui/barrel_out.png");
        // Use a region of the barrel_out GUI texture as the background
        background = helper.createDrawable(backgroundImage, 32, 16, 110, 54);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.BARREL_OUT.get()));
        arrow = helper.drawableBuilder(backgroundImage, 176, 0, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<LiquidToItemRecipe> getRecipeType() {
        return JEIPlugin.L2IS_JEI_TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LiquidToItemRecipe recipe, IFocusGroup focuses) {
        // Input fluid on the left
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .setFluidRenderer(10000, true, 16, 52)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.getRequiredFluid());

        // Container item input
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 19)
                .addIngredients(recipe.getContainerInput());

        // Output item
        builder.addSlot(RecipeIngredientRole.OUTPUT, 71, 19)
                .addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(LiquidToItemRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 44, 20);
    }
}
