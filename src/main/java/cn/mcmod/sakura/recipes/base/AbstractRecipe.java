package cn.mcmod.sakura.recipes.base;

import com.google.gson.annotations.Expose;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

/**
 * Local replacement for cn.mcmod_mmf.mmlib.recipe.AbstractRecipe
 * which was removed from MMLib 1.21.1.
 */
public abstract class AbstractRecipe implements Recipe<RecipeWrapper> {
    public String group = "";
    @Expose
    public float experience;
    @Expose
    public int recipeTime;

    public float getExperience() {
        return experience;
    }

    public int getRecipeTime() {
        return recipeTime;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
