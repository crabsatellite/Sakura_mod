package cn.mcmod.sakura.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.fluid.FluidRegistry;
import cn.mcmod.sakura.item.DrinkRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraAlcoholSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry for all Liquid-to-Item (L2IS) conversion recipes.
 * Ported from 1.12.2's LiquidToItemRecipe system.
 *
 * Defines three types of conversions:
 * 1. Fluid + Bucket -> Filled Bucket (1000 mB)
 * 2. Fluid + Cup -> Alcoholic Drink in Cup (200 mB)
 * 3. Fluid + Empty Bottle -> Bottled Alcoholic Drink (1000 mB)
 */
public class LiquidToItemRegistry {

    private static List<LiquidToItemRecipe> RECIPES;

    public static List<LiquidToItemRecipe> getRecipes() {
        if (RECIPES == null) {
            RECIPES = buildRecipes();
        }
        return Collections.unmodifiableList(RECIPES);
    }

    /**
     * Find a matching L2IS recipe for the given fluid and container.
     */
    public static LiquidToItemRecipe findRecipe(FluidStack fluid, ItemStack container) {
        for (LiquidToItemRecipe recipe : getRecipes()) {
            if (recipe.matches(fluid, container)) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Invalidate cached recipes (call if recipes need to be rebuilt).
     */
    public static void invalidate() {
        RECIPES = null;
    }

    private static List<LiquidToItemRecipe> buildRecipes() {
        List<LiquidToItemRecipe> recipes = new ArrayList<>();

        // ==========================================
        // BUCKET RECIPES (fluid + bucket -> filled bucket, 1000 mB)
        // ==========================================
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.BEER.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.BEER_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.DOBUROKU.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.DOBUROKU_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.SAKE.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.SAKE_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.SHOUCHU.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.SHOUCHU_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.RED_WINE.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.RED_WINE_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.WHITE_WINE.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.WHITE_WINE_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.CHAMPAGNE.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.CHAMPAGNE_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.RUM.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.RUM_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.VODKA.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.VODKA_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.BRANDY.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.BRANDY_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.WHISKEY.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.WHISKEY_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.YEAST_LIQUID.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.YEAST_LIQUID_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.LIQUEUR.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.LIQUEUR_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.COCOA_LIQUEUR.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.COCOA_LIQUEUR_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.GIN.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.GIN_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.TEQUILA.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.TEQUILA_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.FOOD_OIL.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.FOOD_OIL_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.GRAPE_FLUID.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.GRAPE_FLUID_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.GREEN_GRAPE_FLUID.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.GREEN_GRAPE_FLUID_BUCKET.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.MAPLE_SYRUP.get(), 1000),
                Ingredient.of(Items.BUCKET),
                new ItemStack(BucketItemRegistry.MAPLE_SYRUP_BUCKET.get())));

        // ==========================================
        // CUP RECIPES (fluid + cup -> alcoholic drink in cup, 200 mB)
        // ==========================================
        Ingredient cupIngredient = Ingredient.of(ItemRegistry.CUP.get());

        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.BEER.get(), 400),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.DOBUROKU.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_DOBUROKU).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.SAKE.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SAKE).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.SHOUCHU.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SHOUCHU).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.RED_WINE.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.WHITE_WINE.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.CHAMPAGNE.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.RUM.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.VODKA.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.WHISKEY.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.BRANDY.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.GIN.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.TEQUILA.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.LIQUEUR.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.COCOA_LIQUEUR.get(), 200),
                cupIngredient,
                new ItemStack(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_COCOA_LIQUEUR).get())));

        // ==========================================
        // BOTTLE RECIPES (fluid + empty bottle -> bottled drink, 1000 mB)
        // ==========================================
        Ingredient bottleIngredient = Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get());

        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.BEER.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.BEER_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.DOBUROKU.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.DOBUROKU_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.SAKE.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.SAKE_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.SHOUCHU.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.SHOUCHU_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.RED_WINE.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.RED_WINE_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.WHITE_WINE.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.WHITE_WINE_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.CHAMPAGNE.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.CHAMPAGNE_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.RUM.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.RUM_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.VODKA.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.VODKA_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.WHISKEY.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.WHISKEY_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.BRANDY.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.BRANDY_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.GIN.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.GIN_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.TEQUILA.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.TEQUILA_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.LIQUEUR.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.LIQUEUR_BOTTLE.get())));
        recipes.add(new LiquidToItemRecipe(
                new FluidStack(FluidRegistry.COCOA_LIQUEUR.get(), 1000),
                bottleIngredient,
                new ItemStack(ItemRegistry.COCOA_LIQUEUR_BOTTLE.get())));

        return recipes;
    }
}
