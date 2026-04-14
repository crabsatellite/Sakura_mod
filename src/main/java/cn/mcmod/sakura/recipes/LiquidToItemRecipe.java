package cn.mcmod.sakura.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Liquid-to-Item (L2IS) conversion recipe.
 * These recipes define how a fluid + container item can produce an output item.
 * Used by the Barrel Output block entity and displayed in JEI.
 */
public class LiquidToItemRecipe {

    private final FluidStack requiredFluid;
    private final Ingredient containerInput;
    private final ItemStack resultItem;

    public LiquidToItemRecipe(FluidStack requiredFluid, Ingredient containerInput, ItemStack resultItem) {
        this.requiredFluid = requiredFluid;
        this.containerInput = containerInput;
        this.resultItem = resultItem;
    }

    public FluidStack getRequiredFluid() {
        return requiredFluid;
    }

    public Ingredient getContainerInput() {
        return containerInput;
    }

    public ItemStack getResultItem() {
        return resultItem;
    }

    /**
     * Check if the given fluid and container item match this recipe.
     */
    public boolean matches(FluidStack fluid, ItemStack container) {
        if (fluid.isEmpty() || container.isEmpty()) return false;
        return fluid.getFluid() == requiredFluid.getFluid()
                && fluid.getAmount() >= requiredFluid.getAmount()
                && containerInput.test(container);
    }
}
