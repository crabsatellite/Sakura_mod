package cn.mcmod.sakura.data;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.data.builder.ChoppingBoardRecipeBuilder;
import cn.mcmod.sakura.data.builder.CookingPotRecipeBuilder;
import cn.mcmod.sakura.data.builder.DistillerRecipeBuilder;
import cn.mcmod.sakura.data.builder.FermenterRecipeBuilder;
import cn.mcmod.sakura.data.builder.StoneMortarRecipeBuilder;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.fluid.FluidRegistry;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.DrinkRegistry;
import cn.mcmod.sakura.item.enums.SakuraAlcoholSet;
import cn.mcmod.sakura.item.enums.SakuraCocktailSet;
import cn.mcmod.sakura.item.enums.SakuraCuisineSet;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.item.enums.SakuraTeaSet;
import cn.mcmod.sakura.tags.SakuraFluidTags;
import cn.mcmod.sakura.tags.SakuraItemTags;
import cn.mcmod.sakura.recipes.base.FluidIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

public class SakuraRecipeProvider extends RecipeProvider {

    public SakuraRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        registerCraftingRecipe(output);
        registerBuildingBlockRecipes(output);
        registerWeaponArmorRecipes(output);
        registerToolRecipes(output);
        registerDecorationRecipes(output);
        registerMissingBlockRecipes(output);
        registerSmeltingRecipes(output);
        registerDrinkRecipes(output);
        registerMortarRecipe(output);
        registerCookingRecipe(output);
        registerFermenterRecipe(output);
        registerDistillerRecipe(output);
        registerChoppingRecipes(output);
    }

    private void registerCraftingRecipe(RecipeOutput output) {

        makeSlab(output,BlockRegistry.TATAMI_SLAB, BlockRegistry.TATAMI);
        makeSlab(output,BlockRegistry.TATAMI_SLAB_WAXED, BlockRegistry.TATAMI_WAXED);
        makeSlab(output,BlockRegistry.TATAMI_SLAB_SUNBURNT, BlockRegistry.TATAMI_SUNBURNT);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_WAXED.get(), 1)
        .requires(BlockRegistry.TATAMI.get()).requires(Items.HONEYCOMB)
        .unlockedBy("has_tatami", has(BlockRegistry.TATAMI.get())).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.STRAW_BLOCK.get(),4).pattern("LLL").pattern("LLL").pattern("LLL")
                .define('L', SakuraItemTags.STRAW).unlockedBy("has_item", has(SakuraItemTags.STRAW)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.IRON_FISH_KNIFE.get()).pattern("  I").pattern(" I ").pattern("L  ")
                .define('I', Tags.Items.INGOTS_IRON).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI.get(), 6).pattern("LLL").pattern("L#L").pattern("LLL")
                .define('#', SakuraItemTags.LUMBER).define('L', SakuraItemTags.STRAW)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);

        // Tatami sunburnt (from smelting tatami)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_SUNBURNT.get(), 1)
                .requires(BlockRegistry.TATAMI.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_sunburnt"));

        // Tatami variant recipes - NS (rotated) variants
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_NS.get(), 1)
                .requires(BlockRegistry.TATAMI.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_ns"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_TAN.get(), 1)
                .requires(BlockRegistry.TATAMI_SUNBURNT.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI_SUNBURNT.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_tan"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_TAN_NS.get(), 1)
                .requires(BlockRegistry.TATAMI_TAN.get())
                .unlockedBy("has_tatami_tan", has(BlockRegistry.TATAMI_TAN.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_tan_ns"));

        // Tatami half variants (2 halves from 1 full)
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_HALF.get(), 2).pattern("T")
                .define('T', BlockRegistry.TATAMI.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_half"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_NS_HALF.get(), 2).pattern("T")
                .define('T', BlockRegistry.TATAMI_NS.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI_NS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_ns_half"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_TAN_HALF.get(), 2).pattern("T")
                .define('T', BlockRegistry.TATAMI_TAN.get())
                .unlockedBy("has_tatami_tan", has(BlockRegistry.TATAMI_TAN.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_tan_half"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_TAN_NS_HALF.get(), 2).pattern("T")
                .define('T', BlockRegistry.TATAMI_TAN_NS.get())
                .unlockedBy("has_tatami_tan_ns", has(BlockRegistry.TATAMI_TAN_NS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_tan_ns_half"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.TORCH, 4).pattern("C").pattern("#")
                .define('C', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL).get())
                .define('#', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(Tags.Items.RODS_WOODEN))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "torchs_from_charcoal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.STICK, 4).pattern("#").pattern("#").define('#', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sticks_from_lumbers"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.OBON.get()).pattern("LLL").pattern("L#L")
                .define('#', BlockRegistry.SAKURA_LEAVES.get()).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.PAPER, 4).pattern("###").define('#', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "papers_from_lumbers"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockItemRegistry.CHOPPING_BOARD.get()).pattern("###").pattern("I I")
                .define('#', SakuraItemTags.LUMBER).define('I', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chopping_board"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockItemRegistry.FERMENTER.get()).pattern("SSS").pattern("PPP").pattern("SSS")
                .define('S', SakuraItemTags.LUMBER).define('P', ItemTags.LOGS)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fermenter"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockItemRegistry.DISTILLER.get()).pattern("ISI").pattern("PPP").pattern("III")
                .define('S', SakuraItemTags.LUMBER).define('P', ItemTags.LOGS).define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "distiller"));

        // Farmer's Delight compat recipes are shipped as static JSON with neoforge:conditional
        // in src/generated/resources/data/sakura/recipe/*_from_sakura.json

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.COOKING_POT.get()).pattern("#L#").pattern("###")
                .define('#', Tags.Items.INGOTS_IRON).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.STONE_MORTAR.get()).pattern("L  ").pattern("###").pattern("###")
                .define('#', Tags.Items.COBBLESTONES).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);

        foodSmeltingRecipes("eggplant_bake", FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT_BAKED).get(), 0.5F, output);
        foodSmeltingRecipes("taro_bake", ItemRegistry.TARO.get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.TARO_BAKED).get(), 0.5F, output);
        foodSmeltingRecipes("burger", FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_RAW).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get(), 0.5F, output);

        foodSmeltingRecipes("chikuwa", FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA_RAW).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA).get(), 0.5F, output);

        foodSmeltingRecipes("bun", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get(), 0.5F, output);
        foodSmeltingRecipes("buckwheat_bread", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_BUCKWHEAT).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.BUCKWHEAT_BREAD).get(), 0.5F, output);
        foodSmeltingRecipes("rice_bread", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_RICE).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BREAD).get(), 0.5F, output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH).get(), 3)
                .requires(SakuraItemTags.FLOUR_WHEAT).requires(SakuraItemTags.FLOUR_WHEAT)
                .requires(SakuraItemTags.FLOUR_WHEAT).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR_WHEAT)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockItemRegistry.NABE_SUKIYAKI.get())
                .requires(BlockItemRegistry.COOKING_POT.get()).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get()).requires(SakuraItemTags.RAW_BEEF)
                .requires(Tags.Items.CROPS_CARROT).requires(SakuraItemTags.MUSHROOMS)
                .requires(SakuraItemTags.VEGETABLES).requires(SakuraItemTags.VEGETABLES)
                .unlockedBy("has_pot", has(BlockItemRegistry.COOKING_POT.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockItemRegistry.NABE_ODEN.get())
                .requires(BlockItemRegistry.COOKING_POT.get()).requires(SakuraItemTags.FISHCAKE)
                .requires(SakuraItemTags.FISHCAKE).requires(SakuraItemTags.FISHCAKE).requires(SakuraItemTags.FISHCAKE)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.DASHI).requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .unlockedBy("has_pot", has(BlockItemRegistry.COOKING_POT.get())).save(output);

        makeItemToBucket(BucketItemRegistry.FOOD_OIL_BUCKET, Ingredient.of(SakuraItemTags.SEEDS_RAPESEED))
                .unlockedBy("has_seeds", has(SakuraItemTags.SEEDS_RAPESEED)).save(output);

        // Grape Fluid Bucket: 8 grapes + bucket
        makeItemToBucket(BucketItemRegistry.GRAPE_FLUID_BUCKET, Ingredient.of(SakuraItemTags.CROPS_GRAPE))
                .unlockedBy("has_grape", has(SakuraItemTags.CROPS_GRAPE))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "grape_fluid_bucket"));

        // Green Grape Fluid Bucket: 8 green grapes + bucket
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BucketItemRegistry.GREEN_GRAPE_FLUID_BUCKET.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(Items.BUCKET)
                .unlockedBy("has_green_grape", has(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "green_grape_fluid_bucket"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_BUCKWHEAT).get(), 3)
                .requires(SakuraItemTags.FLOUR_BUCKWHEAT).requires(SakuraItemTags.FLOUR_BUCKWHEAT)
                .requires(SakuraItemTags.FLOUR_BUCKWHEAT).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR_BUCKWHEAT)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_RICE).get(), 3)
                .requires(SakuraItemTags.FLOUR_RICE).requires(SakuraItemTags.FLOUR_RICE)
                .requires(SakuraItemTags.FLOUR_RICE).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR_RICE)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_TAMAGOYAKI.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_FISH_COOKED.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_FISH_SALT.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE_SALT).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_FISH_RAW.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_YAKINIKU.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.YAKINIKU).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_TEMPURA.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_FRIED.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_CHICKEN).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_KATSU.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKU_BURGER.get()).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(BlockRegistry.OBON.get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_DISH).get())
                .unlockedBy("has_obon", has(BlockRegistry.OBON.get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI).get())
                .requires(SakuraItemTags.SLICES_RAW_FISHES).requires(SakuraItemTags.SLICES_RAW_FISHES)
                .requires(SakuraItemTags.SOYSAUCE).unlockedBy("has_fish", has(SakuraItemTags.SLICES_RAW_FISHES))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA_RAW).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get()).requires(SakuraItemTags.SALT)
                .unlockedBy("has_fish", has(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.SAKURA_SAPLING.get()).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_PINK).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_RED.get()).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_RED).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_GREEN.get()).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_GREEN).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_YELLOW.get()).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_YELLOW).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_ORANGE.get()).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_ORANGE).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(Items.DRIED_KELP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_BAMBOO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(Items.DRIED_KELP)
                .requires(BlockRegistry.BAMBOOSHOOT.get())
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_SEAWEED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(Items.DRIED_KELP)
                .requires(Items.DRIED_KELP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_MUSHROOM).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(Items.DRIED_KELP)
                .requires(SakuraItemTags.MUSHROOMS)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_TEMPURA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(Items.DRIED_KELP)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUSHI).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(SakuraItemTags.SLICES_RAW_FISHES)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUSHI_SHRIMP).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(SakuraItemTags.SHRIMP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUSHI_TAMAGO).get(), 3)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI).get()).requires(Items.DRIED_KELP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TEMPURA_BATTER).get(), 8)
                .requires(SakuraItemTags.FLOUR).requires(SakuraItemTags.SALT).requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.HAMBURGER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get()).requires(SakuraItemTags.TOMATOSAUCE)
                .unlockedBy("has_bun", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE).get())
                .requires(SakuraItemTags.MILK).requires(SakuraItemTags.SALT)
                .unlockedBy("has_salt", has(SakuraItemTags.SALT)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_DISH).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get())
                .requires(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE)
                .unlockedBy("has_burger", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE_BURGER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE).get()).requires(SakuraItemTags.CHEESE)
                .unlockedBy("has_bun", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get())).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE_BURGER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.HAMBURGER).get()).requires(SakuraItemTags.CHEESE)
                .unlockedBy("has_bun", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "cheese_burger_from_hamburger"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get(), 8)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())).save(output);

        foodSmeltingRecipes("mochi_toasted", FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI_TOASTED).get(), 0.5F, output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI_SAKURA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get())
                .requires(BlockRegistry.SAKURA_LEAVES.get())
                .unlockedBy("has_mochi", has(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get())).save(output);

        makeIngotToBlock(BlockItemRegistry.BAMBOO_BLOCK, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO))
                .unlockedBy("has_item", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO).get()))
                .save(output);
        makeIngotToBlock(BlockItemRegistry.BAMBOO_BLOCK, () -> Items.BAMBOO).unlockedBy("has_item", has(Items.BAMBOO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_block_from_vanilla_bamboo"));
        makeIngotToBlock(BlockItemRegistry.BAMBOO_BLOCK_SUNBURNT,
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT))
                        .unlockedBy("has_item",
                                has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT).get()))
                        .save(output);
        makeIngotToBlock(BlockItemRegistry.BAMBOO_CHARCOAL_BLOCK,
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL))
                        .unlockedBy("has_item",
                                has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL).get()))
                        .save(output);

        makeBlockToIngot(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO), BlockItemRegistry.BAMBOO_BLOCK)
                .save(output);
        makeBlockToIngot(() -> Items.BAMBOO, BlockItemRegistry.BAMBOO_BLOCK).save(output,
                ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_block_to_vanilla_bamboo"));
        makeBlockToIngot(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL),
                BlockItemRegistry.BAMBOO_CHARCOAL_BLOCK).save(output);
        makeBlockToIngot(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT),
                BlockItemRegistry.BAMBOO_BLOCK_SUNBURNT).save(output);

        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_BAMBOO), Ingredient.of(SakuraItemTags.BAMBOO))
                .unlockedBy("has_item", has(SakuraItemTags.BAMBOO)).save(output);
        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE),
                Ingredient.of(BlockRegistry.MAPLE_LOG.get()))
                        .unlockedBy("has_item", has(BlockItemRegistry.MAPLE_LOG.get())).save(output);
        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA),
                Ingredient.of(BlockRegistry.SAKURA_LOG.get()))
                        .unlockedBy("has_item", has(BlockItemRegistry.SAKURA_LOG.get())).save(output);

        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE),
                Ingredient.of(BlockRegistry.MAPLE_WOOD.get()))
                        .unlockedBy("has_item", has(BlockItemRegistry.MAPLE_LOG.get()))
                        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maple_lumber_from_wood"));
        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA),
                Ingredient.of(BlockRegistry.SAKURA_WOOD.get()))
                        .unlockedBy("has_item", has(BlockItemRegistry.SAKURA_LOG.get()))
                        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_lumber_from_wood"));

        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE),
                Ingredient.of(BlockRegistry.STRIPPED_MAPLE_LOG.get()))
                        .unlockedBy("has_item", has(BlockItemRegistry.MAPLE_LOG.get()))
                        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maple_lumber_from_stripped"));
        makeLumber(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA),
                Ingredient.of(BlockRegistry.STRIPPED_SAKURA_LOG.get()))
                        .unlockedBy("has_item", has(BlockItemRegistry.SAKURA_LOG.get()))
                        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_lumber_from_stripped"));

        makeLumberToPlank(BlockRegistry.BAMBOO_PLANK, Ingredient.of(SakuraItemTags.LUMBER_BAMBOO))
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);
        makeLumberToPlank(BlockRegistry.MAPLE_PLANK, Ingredient.of(SakuraItemTags.LUMBER_MAPLE))
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);
        makeLumberToPlank(BlockRegistry.SAKURA_PLANK, Ingredient.of(SakuraItemTags.LUMBER_SAKURA))
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.BAMBOO_BLOCK.get()),RecipeCategory.MISC,BlockRegistry.BAMBOO_CHARCOAL_BLOCK.get(), 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_block_from_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get()),RecipeCategory.MISC,BlockRegistry.BAMBOO_CHARCOAL_BLOCK.get(), 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_block_sunburnt_from_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO).get()),RecipeCategory.MISC,ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL).get(), 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_charcoal_from_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT).get()),RecipeCategory.MISC,ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL).get(), 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_charcoal_sunburnt_from_smelt"));

        // ===== BENTO RECIPES =====
        // Bento Standard: bento_box + rice_cooked + tempura + vegetable + cooked_meat
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.CUISINES.get(SakuraCuisineSet.BENTO_STANDARD).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .requires(SakuraItemTags.VEGETABLES)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.COOKED_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_PORK),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_MUTTON))))
                .unlockedBy("has_bento_box", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bento_standard"));

        // Bento Deluxe: bento_box + rice_cooked + tempura + vegetable + seaweed + cooked_meat
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.CUISINES.get(SakuraCuisineSet.BENTO_DELUXE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.CROPS_SEAWEED)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.COOKED_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_PORK),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_MUTTON))))
                .unlockedBy("has_bento_box", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bento_deluxe"));

        // Bento Premium: bento_box + omurice + vegetable
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.CUISINES.get(SakuraCuisineSet.BENTO_PREMIUM).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.OMURICE).get())
                .requires(SakuraItemTags.VEGETABLES)
                .unlockedBy("has_bento_box", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bento_premium"));

        // Bento Supreme: bento_box + rice_cooked + curry_sauce + vegetable + cooked_meat
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.CUISINES.get(SakuraCuisineSet.BENTO_SUPREME).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get())
                .requires(SakuraItemTags.VEGETABLES)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.COOKED_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_PORK),
                        new Ingredient.TagValue(SakuraItemTags.COOKED_MUTTON))))
                .unlockedBy("has_bento_box", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bento_supreme"));

        // ===== MISSING ONIGIRI/FOOD RECIPES =====
        // Onigiri Fish: rice_cooked + noodle_soup + fish
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_FISH).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.COOKED_FISHES)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "onigiri_fish"));

        // Onigiri Matsutake: rice_cooked + kelp + matsutake
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_MATSUTAKE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(Items.DRIED_KELP)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MATSUTAKE).get())
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "onigiri_matsutake"));

        // Okinoyaki Plus: okinoyaki + worcester_sauce + mayo
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI_PLUS).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.WORCESTER_SAUCE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MAYO).get())
                .unlockedBy("has_okinoyaki", has(FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "okinoyaki_plus"));

        // Okinoyaki Final: okinoyaki_plus + bonito_shaving
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI_FINAL).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI_PLUS).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BONITO_SHAVING).get())
                .unlockedBy("has_okinoyaki_plus", has(FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI_PLUS).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "okinoyaki_final"));

        // Taiyaki Mocha: raw_taiyaki + mocha (then smelted, but also as crafting)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.TAIYAKI_MOCHA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TAIYAKI).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get())
                .unlockedBy("has_taiyaki", has(FoodRegistry.FOODSET.get(SakuraFoodSet.TAIYAKI).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "taiyaki_mocha"));

        // Mocha Cookie: flour + egg + mocha + milk + sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHA_COOKIE).get(), 2)
                .requires(SakuraItemTags.FLOUR).requires(SakuraItemTags.EGGS)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get())
                .requires(SakuraItemTags.MILK).requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_mocha", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mocha_cookie"));

        // Pound Cake Mocha: pound_cake + mocha
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.POUND_CAKE_MOCHA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.POUND_CAKE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get())
                .unlockedBy("has_pound_cake", has(FoodRegistry.FOODSET.get(SakuraFoodSet.POUND_CAKE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pound_cake_mocha"));

        // Rice Curry Cheese Katsu: rice_curry_cheese + katsu
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE_KATSU).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .unlockedBy("has_curry_cheese", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_curry_cheese_katsu"));

        // Rice Curry Cheese Burger: rice_curry_cheese + burger
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE_BURGER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get())
                .unlockedBy("has_curry_cheese", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_curry_cheese_burger"));

        // Imogaranawa Piece: 3 dried_imogara + water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.IMOGARANAWA_PIECE).get(), 3)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get())
                .requires(SakuraItemTags.WATER)
                .unlockedBy("has_dried_imogara", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "imogaranawa_piece"));

        // Miso Ball: miso + dried_rice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MISO_BALL).get())
                .requires(SakuraItemTags.MISO)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get())
                .unlockedBy("has_miso", has(SakuraItemTags.MISO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "miso_ball"));

        // Hyorogan (military ration): dried_rice + miso + sake_kasu
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.HYOROGAN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get())
                .requires(SakuraItemTags.MISO)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU).get())
                .unlockedBy("has_dried_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "hyorogan"));

        // Suikatsugan (military water ration): dried_rice + ume + sake_kasu + sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUIKATSUGAN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.UME).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU).get())
                .requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_dried_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "suikatsugan"));

        // Croquette Dish: croquette + sliced_cabbage
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE_DISH).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE).get())
                .requires(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE)
                .unlockedBy("has_croquette", has(FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "croquette_dish"));

        // ===== ADDITIONAL MISSING CRAFTING RECIPES =====

        // Pound Cake: flour + egg + grape + milk + sugar -> 2
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.POUND_CAKE).get(), 2)
                .requires(SakuraItemTags.FLOUR).requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.CROPS_GRAPE).requires(SakuraItemTags.MILK)
                .requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pound_cake"));

        // Ehoumaki: seaweed + veggie + raw_fish + tamagoyaki + rice_cooked -> 3
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.EHOUMAKI).get(), 3)
                .requires(SakuraItemTags.CROPS_SEAWEED).requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ehoumaki"));

        // Raw Taiyaki: flour + sugar + egg + egg + red_bean_paste -> 2
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.RAW_TAIYAKI).get(), 2)
                .requires(SakuraItemTags.FLOUR).requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.EGGS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .unlockedBy("has_redbean_paste", has(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "raw_taiyaki"));

        // Dorayaki: flour*2 + sugar + egg*2 + red_bean_paste*2 -> 3
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.DORAYAKI).get(), 3)
                .requires(SakuraItemTags.FLOUR).requires(SakuraItemTags.FLOUR)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.EGGS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .unlockedBy("has_redbean_paste", has(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dorayaki"));

        // Dough Okinoyaki: dough + salt + raw_meat + veggie + egg -> 2
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.DOUGH_OKINOYAKI).get(), 2)
                .requires(SakuraItemTags.DOUGH)
                .requires(SakuraItemTags.SALT)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.EGGS)
                .unlockedBy("has_dough", has(SakuraItemTags.DOUGH))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dough_okinoyaki"));

        // Ohagi: mochi + red_bean_paste
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.OHAGI).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .unlockedBy("has_mochi", has(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ohagi"));

    }

    private void registerBuildingBlockRecipes(RecipeOutput output) {
        // ===== STAIRS from planks (6 planks -> 4 stairs) =====
        makeStair(output, BlockRegistry.SAKURA_STAIRS, BlockRegistry.SAKURA_PLANK);
        makeStair(output, BlockRegistry.MAPLE_STAIRS, BlockRegistry.MAPLE_PLANK);
        makeStair(output, BlockRegistry.BAMBOO_PLANK_STAIRS, BlockRegistry.BAMBOO_PLANK);

        // Stairs from bamboo blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BAMBOO_STAIRS.get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', BlockRegistry.BAMBOO_BLOCK.get())
                .unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_stair_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BAMBOO_STAIRS_SUNBURNT.get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get())
                .unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_stair_sunburnt_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STRAW_STAIRS.get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', BlockRegistry.STRAW_BLOCK.get())
                .unlockedBy("has_item", has(BlockRegistry.STRAW_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "straw_stair_from_block"));

        // ===== SLABS from planks (3 planks -> 6 slabs) =====
        makeSlab(output, BlockRegistry.SAKURA_SLAB, BlockRegistry.SAKURA_PLANK);
        makeSlab(output, BlockRegistry.MAPLE_SLAB, BlockRegistry.MAPLE_PLANK);
        makeSlab(output, BlockRegistry.BAMBOO_PLANK_SLAB, BlockRegistry.BAMBOO_PLANK);

        // Slabs from bamboo blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BAMBOO_SLAB.get(), 6)
                .pattern("###")
                .define('#', BlockRegistry.BAMBOO_BLOCK.get())
                .unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_slab_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BAMBOO_SLAB_SUNBURNT.get(), 6)
                .pattern("###")
                .define('#', BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get())
                .unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_slab_sunburnt_from_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STRAW_SLAB.get(), 6)
                .pattern("###")
                .define('#', BlockRegistry.STRAW_BLOCK.get())
                .unlockedBy("has_item", has(BlockRegistry.STRAW_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "straw_slab_from_block"));

        // ===== FENCES (bamboo) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BAMBOO_FENCE.get(), 3)
                .pattern("#I#").pattern("#I#")
                .define('#', SakuraItemTags.BAMBOO).define('I', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_bamboo", has(SakuraItemTags.BAMBOO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_fence"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BAMBOO_FENCE_SUNBURNT.get(), 3)
                .pattern("#I#").pattern("#I#")
                .define('#', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT).get()).define('I', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_bamboo", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_fence_sunburnt"));

        // ===== DOOR (bamboo) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.BAMBOO_DOOR.get(), 3)
                .pattern("##").pattern("##").pattern("##")
                .define('#', SakuraItemTags.BAMBOO)
                .unlockedBy("has_bamboo", has(SakuraItemTags.BAMBOO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_door"));

        // ===== KAWARA (roof tiles) =====
        // Kawara block from clay + gray dye (like terracotta)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.KAWARA_BLOCK.get(), 8)
                .pattern("###").pattern("#D#").pattern("###")
                .define('#', Items.CLAY_BALL).define('D', Tags.Items.DYES_GRAY)
                .unlockedBy("has_clay", has(Items.CLAY_BALL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kawara_block"));
        // Kawara stairs from kawara block
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.KAWARA.get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', BlockRegistry.KAWARA_BLOCK.get())
                .unlockedBy("has_kawara", has(BlockRegistry.KAWARA_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kawara_stairs"));

        // ===== TATAMI CARPETS =====
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_CARPET.get(), 3)
                .pattern("##")
                .define('#', BlockRegistry.TATAMI.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_carpet"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_CARPET_WAXED.get(), 3)
                .pattern("##")
                .define('#', BlockRegistry.TATAMI_WAXED.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI_WAXED.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_ns_carpet"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_CARPET_TAN.get(), 3)
                .pattern("##")
                .define('#', BlockRegistry.TATAMI_SUNBURNT.get())
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI_SUNBURNT.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_tan_carpet"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI_CARPET_TAN_WAXED.get())
                .requires(BlockRegistry.TATAMI_CARPET_TAN.get()).requires(Items.HONEYCOMB)
                .unlockedBy("has_tatami", has(BlockRegistry.TATAMI_CARPET_TAN.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatami_tan_ns_carpet"));
    }

    private void registerWeaponArmorRecipes(RecipeOutput output) {
        // ===== WEAPONS =====
        // Katana: 2 tamahagane + 1 stick (vertical sword pattern)
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.KATANA.get())
                .pattern(" T").pattern(" T").pattern(" S")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "katana"));

        // Tachi: 2 steel + 1 stick
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.TACHI.get())
                .pattern(" T").pattern(" T").pattern(" S")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STEEL_INGOT).get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_steel", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STEEL_INGOT).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tachi"));

        // Sakura Katana: 2 sakura diamond + 1 stick
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SAKURA_KATANA.get())
                .pattern(" T").pattern(" T").pattern(" S")
                .define('T', ItemRegistry.SAKURA_DIAMOND.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_katana"));

        // Kodachi: 1 tamahagane + 1 stick (short sword)
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.KODACHI.get())
                .pattern("T").pattern("S")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kodachi"));

        // Sakura Kodachi
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SAKURA_KODACHI.get())
                .pattern("T").pattern("S")
                .define('T', ItemRegistry.SAKURA_DIAMOND.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_kodachi"));

        // Shinai: 2 bamboo + 1 stick (practice sword)
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SHINAI.get())
                .pattern(" B").pattern(" B").pattern(" S")
                .define('B', SakuraItemTags.BAMBOO)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_bamboo", has(SakuraItemTags.BAMBOO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "shinai"));

        // Sheath: leather + lumber
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SHEATH.get())
                .pattern("L").pattern("W")
                .define('L', Items.LEATHER).define('W', SakuraItemTags.LUMBER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sheath"));

        // Katana Sheath: katana + sheath
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.KATANA_SHEATH.get())
                .requires(ItemRegistry.KATANA.get()).requires(ItemRegistry.SHEATH.get())
                .unlockedBy("has_katana", has(ItemRegistry.KATANA.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "katana_sheath"));

        // Sakura Katana Sheath
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SAKURA_KATANA_SHEATH.get())
                .requires(ItemRegistry.SAKURA_KATANA.get()).requires(ItemRegistry.SHEATH.get())
                .unlockedBy("has_sakura_katana", has(ItemRegistry.SAKURA_KATANA.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_katana_sheath"));

        // ===== SAMURAI ARMOR (tamahagane in standard patterns) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SAMURAI_HELMET.get())
                .pattern("TTT").pattern("T T")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get())
                .unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "samurai_helmet"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SAMURAI_CHEST.get())
                .pattern("T T").pattern("TTT").pattern("TTT")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get())
                .unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "samurai_chest"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SAMURAI_PANTS.get())
                .pattern("TTT").pattern("T T").pattern("T T")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get())
                .unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "samurai_pants"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SAMURAI_SHOES.get())
                .pattern("T T").pattern("T T")
                .define('T', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get())
                .unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "samurai_shoes"));

        // ===== SOLDIER ARMOR (iron + leather) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SOLDIER_HELMET.get())
                .pattern("ILI").pattern("L L")
                .define('I', Tags.Items.INGOTS_IRON).define('L', Items.LEATHER)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soldier_helmet"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SOLDIER_CHEST.get())
                .pattern("L L").pattern("ILI").pattern("LIL")
                .define('I', Tags.Items.INGOTS_IRON).define('L', Items.LEATHER)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soldier_chest"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SOLDIER_PANTS.get())
                .pattern("ILI").pattern("L L").pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON).define('L', Items.LEATHER)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soldier_pants"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SOLDIER_SHOES.get())
                .pattern("I I").pattern("L L")
                .define('I', Tags.Items.INGOTS_IRON).define('L', Items.LEATHER)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soldier_shoes"));

        // ===== COSMETIC ARMOR =====
        // Kimono: silk in leggings pattern
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.KIMONO.get())
                .pattern("SSS").pattern("S S").pattern("S S")
                .define('S', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get())
                .unlockedBy("has_silk", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kimono"));

        // Haori: silk in chestplate pattern
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.HAORI.get())
                .pattern("S S").pattern("SSS").pattern("SSS")
                .define('S', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get())
                .unlockedBy("has_silk", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "haori"));

        // Straw Hat: straw in helmet pattern
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.STRAW_HAT.get())
                .pattern("SSS").pattern("S S")
                .define('S', SakuraItemTags.STRAW)
                .unlockedBy("has_straw", has(SakuraItemTags.STRAW))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "strawhat"));
    }

    private void registerToolRecipes(RecipeOutput output) {
        // ===== HAMMERS =====
        // Stone Hammer
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.STONE_HAMMER.get())
                .pattern("SS").pattern(" L")
                .define('S', Tags.Items.COBBLESTONES).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONES))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "stone_hammer"));

        // Iron Hammer
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.IRON_HAMMER.get())
                .pattern("II").pattern(" L")
                .define('I', Tags.Items.INGOTS_IRON).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "iron_hammer"));

        // Sakura Hammer
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_HAMMER.get())
                .pattern("DD").pattern(" L")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_hammer"));

        // ===== BROOM =====
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.BROOM.get())
                .pattern("S").pattern("B").pattern("B")
                .define('S', Tags.Items.STRINGS).define('B', SakuraItemTags.BAMBOO)
                .unlockedBy("has_bamboo", has(SakuraItemTags.BAMBOO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "broom"));

        // ===== SAKURA TIER TOOLS =====
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_AXE.get())
                .pattern("DD").pattern("DL").pattern(" L")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_axe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_PICKAXE.get())
                .pattern("DDD").pattern(" L ").pattern(" L ")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_SHOVEL.get())
                .pattern("D").pattern("L").pattern("L")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_shovel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_HOE.get())
                .pattern("DD").pattern(" L").pattern(" L")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_hoe"));

        // ===== SAKURA KNIVES =====
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_KNIFE_FISH.get())
                .pattern("  D").pattern(" D ").pattern("L  ")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_knife_fish"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAKURA_KNIFE_NOODLE.get())
                .pattern("DD").pattern(" L")
                .define('D', ItemRegistry.SAKURA_DIAMOND.get()).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_knife_noodle"));

        // Iron Noodle Knife
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.IRON_NOODLE_KNIFE.get())
                .pattern("II").pattern(" L")
                .define('I', Tags.Items.INGOTS_IRON).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "knife_noodle"));

        // ===== CUP =====
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CUP.get(), 4)
                .pattern("# #").pattern(" # ")
                .define('#', Items.CLAY_BALL)
                .unlockedBy("has_clay", has(Items.CLAY_BALL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "cup"));

        // ===== EMPTY BOTTLE =====
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get(), 4)
                .pattern("# #").pattern("# #").pattern(" # ")
                .define('#', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "empty_bottle"));

        // ===== BENTO BOX =====
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX).get())
                .pattern("L L").pattern("LLL")
                .define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bento_box"));
    }

    private void registerDecorationRecipes(RecipeOutput output) {
        // ===== LANTERNS =====
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.STONE_LANTERN.get())
                .pattern(" S ").pattern("STS").pattern(" S ")
                .define('S', Tags.Items.STONES).define('T', Items.TORCH)
                .unlockedBy("has_stone", has(Tags.Items.STONES))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "stone_lantern"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.COBBLESTONE_LANTERN.get())
                .pattern(" C ").pattern("CTC").pattern(" C ")
                .define('C', Tags.Items.COBBLESTONES).define('T', Items.TORCH)
                .unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONES))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "cobblestone_lantern"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MOSSY_STONE_LANTERN.get())
                .requires(BlockRegistry.STONE_LANTERN.get()).requires(Items.VINE)
                .unlockedBy("has_lantern", has(BlockRegistry.STONE_LANTERN.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mossy_stone_lantern"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.RED_LANTERN.get())
                .pattern(" I ").pattern("PTP").pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON).define('P', Items.RED_WOOL).define('T', Items.TORCH)
                .unlockedBy("has_wool", has(Items.RED_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "red_lantern"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.WHITE_LANTERN.get())
                .pattern(" I ").pattern("PTP").pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON).define('P', Items.WHITE_WOOL).define('T', Items.TORCH)
                .unlockedBy("has_wool", has(Items.WHITE_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "white_lantern"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BAMBOO_LANTERN.get())
                .pattern(" B ").pattern("BTB").pattern(" B ")
                .define('B', SakuraItemTags.BAMBOO).define('T', Items.TORCH)
                .unlockedBy("has_bamboo", has(SakuraItemTags.BAMBOO))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_lantern"));

        // ===== FURNITURE =====
        // Windbell
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.WINDBELL.get())
                .pattern(" I ").pattern("IGI").pattern(" S ")
                .define('I', Tags.Items.INGOTS_IRON).define('G', Tags.Items.GLASS_BLOCKS).define('S', Tags.Items.STRINGS)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "windbell"));

        // Andon (paper lantern)
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ANDON.get())
                .pattern("PPP").pattern("PTP").pattern("LLL")
                .define('P', Items.PAPER).define('T', Items.TORCH).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "andon"));

        // Zabuton (sitting cushion)
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ZABUTON.get())
                .pattern("WW")
                .define('W', Items.RED_WOOL)
                .unlockedBy("has_wool", has(Items.RED_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "zabuton"));

        // Futon (bedding)
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.FUTON.get())
                .pattern("WWW").pattern("SSS")
                .define('W', Items.WHITE_WOOL).define('S', SakuraItemTags.STRAW)
                .unlockedBy("has_wool", has(Items.WHITE_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "futon"));

        // Taiko (drum)
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TAIKO.get())
                .pattern("LLL").pattern("LSL").pattern("LLL")
                .define('L', SakuraItemTags.LUMBER).define('S', Items.LEATHER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "taiko"));

        // ===== NOREN (curtains) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.NOREN_WHITE.get())
                .pattern("LLL").pattern("W W").pattern("W W")
                .define('L', SakuraItemTags.LUMBER).define('W', Items.WHITE_WOOL)
                .unlockedBy("has_wool", has(Items.WHITE_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "noren_white"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.NOREN_BLUE.get())
                .pattern("LLL").pattern("W W").pattern("W W")
                .define('L', SakuraItemTags.LUMBER).define('W', Items.BLUE_WOOL)
                .unlockedBy("has_wool", has(Items.BLUE_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "noren_blue"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.NOREN_PINK.get())
                .pattern("LLL").pattern("W W").pattern("W W")
                .define('L', SakuraItemTags.LUMBER).define('W', Items.PINK_WOOL)
                .unlockedBy("has_wool", has(Items.PINK_WOOL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "noren_pink"));
    }

    private void registerMissingBlockRecipes(RecipeOutput output) {
        // ===== SHOJI (sliding door panel) =====
        // 1.12.2: SPP / SPP / SPP  S=lumber, P=paper
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.SHOJI.get())
                .pattern("SPP").pattern("SPP").pattern("SPP")
                .define('S', SakuraItemTags.LUMBER).define('P', Items.PAPER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "shoji"));

        // ===== TATARA (iron smelting station) =====
        // 1.12.2: shapeless - iron ore + bamboo charcoal block + hammer
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlockRegistry.TATARA.get())
                .requires(Tags.Items.ORES_IRON)
                .requires(BlockRegistry.BAMBOO_CHARCOAL_BLOCK.get())
                .requires(ItemRegistry.IRON_HAMMER.get())
                .unlockedBy("has_bamboo_charcoal_block", has(BlockRegistry.BAMBOO_CHARCOAL_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tatara"));

        // ===== MAPLE_CAULDRON (maple syrup collector) =====
        // 1.12.2: #P# / #P# / # #  #=iron, P=lumber
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.MAPLE_CAULDRON.get())
                .pattern("#P#").pattern("#P#").pattern("# #")
                .define('#', Tags.Items.INGOTS_IRON).define('P', SakuraItemTags.LUMBER)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maple_cauldron"));

        // ===== MAPLE_SPILE (tree tap) =====
        // 1.12.2: PPP / ### / #    P=lumber, #=iron
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.MAPLE_SPILE.get())
                .pattern("PPP").pattern("###").pattern("#  ")
                .define('#', Tags.Items.INGOTS_IRON).define('P', SakuraItemTags.LUMBER)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maple_spile"));

        // ===== SAKURA_DIAMOND_BLOCK (storage block: 9 diamonds -> block) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SAKURA_DIAMOND_BLOCK.get())
                .pattern("###").pattern("###").pattern("###")
                .define('#', ItemRegistry.SAKURA_DIAMOND.get())
                .unlockedBy("has_sakura_diamond", has(ItemRegistry.SAKURA_DIAMOND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_diamond_block"));

        // ===== SAKURA_DIAMOND decomposition (block -> 9 diamonds) =====
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SAKURA_DIAMOND.get(), 9)
                .requires(BlockRegistry.SAKURA_DIAMOND_BLOCK.get())
                .unlockedBy("has_sakura_diamond_block", has(BlockRegistry.SAKURA_DIAMOND_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_diamond_from_block"));

        // ===== GRAPE_SPLINT =====
        // 1.12.2:  S  / SSS /  S   S=lumber
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.GRAPE_SPLINT.get())
                .pattern(" S ").pattern("SSS").pattern(" S ")
                .define('S', SakuraItemTags.LUMBER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "grape_splint"));

        // ===== GRAPE_SPLINT_STAND =====
        // 1.12.2:  S  / S#S /  S   #=fenceWood, S=lumber -> yields 2
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.GRAPE_SPLINT_STAND.get(), 2)
                .pattern(" S ").pattern("S#S").pattern(" S ")
                .define('#', Tags.Items.FENCES_WOODEN).define('S', SakuraItemTags.LUMBER)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "grape_splint_stand"));

        // ===== PEPPER_SPLINT =====
        // 1.12.2: S S /  #  / S S   #=lumber, S=stickWood -> yields 2
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.PEPPER_SPLINT.get(), 2)
                .pattern("S S").pattern(" # ").pattern("S S")
                .define('#', SakuraItemTags.LUMBER).define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pepper_splint"));

        // ===== VANILLA_SPLINT =====
        // 1.12.2: #S# / S S / #S#   #=lumber, S=stickWood -> yields 2
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.VANILLA_SPLINT.get(), 2)
                .pattern("#S#").pattern("S S").pattern("#S#")
                .define('#', SakuraItemTags.LUMBER).define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_lumber", has(SakuraItemTags.LUMBER))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "vanilla_splint"));

        // ===== KITUNEBI (fox fire lantern) =====
        // 1.12.2: LLL / WTW / LLL   T=lit_pumpkin(jack_o_lantern), W=lumber, L=lapis -> yields 16
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.KITUNEBI.get(), 16)
                .pattern("LLL").pattern("WTW").pattern("LLL")
                .define('T', Items.JACK_O_LANTERN).define('W', SakuraItemTags.LUMBER).define('L', Tags.Items.GEMS_LAPIS)
                .unlockedBy("has_jack_o_lantern", has(Items.JACK_O_LANTERN))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kitunebi"));

        // ===== BARREL OUTPUT (fluid output tap) =====
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.BARREL_OUT.get())
                .pattern(" S ").pattern("SBS").pattern(" S ")
                .define('S', SakuraItemTags.LUMBER).define('B', Items.BARREL)
                .unlockedBy("has_barrel", has(Items.BARREL))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "barrel_out"));
    }

    private void registerSmeltingRecipes(RecipeOutput output) {
        // Iron Sand -> Iron Nuggets (smelting)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.IRON_SAND.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 200)
                .group("sakura").unlockedBy("has_iron_sand", has(BlockRegistry.IRON_SAND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "iron_ingot_from_iron_sand"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockRegistry.IRON_SAND.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 100)
                .group("sakura").unlockedBy("has_iron_sand", has(BlockRegistry.IRON_SAND.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "iron_ingot_from_iron_sand_blasting"));

        // Sakura Diamond Ore -> Sakura Diamond (smelting/blasting)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.SAKURA_DIAMOND_ORE.get()), RecipeCategory.MISC, ItemRegistry.SAKURA_DIAMOND.get(), 1.0F, 200)
                .group("sakura").unlockedBy("has_ore", has(BlockRegistry.SAKURA_DIAMOND_ORE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_diamond_from_smelting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockRegistry.SAKURA_DIAMOND_ORE.get()), RecipeCategory.MISC, ItemRegistry.SAKURA_DIAMOND.get(), 1.0F, 100)
                .group("sakura").unlockedBy("has_ore", has(BlockRegistry.SAKURA_DIAMOND_ORE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_diamond_from_blasting"));

        // Zuku -> Tamahagane (smelting iron processing chain)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ZUKU).get()), RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ZUKU_INGOT).get(), 0.5F, 200)
                .group("sakura").unlockedBy("has_zuku", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ZUKU).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "zuku_ingot_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAGEGANE).get()), RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get(), 0.7F, 200)
                .group("sakura").unlockedBy("has_sagegane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAGEGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tamahagane_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()), RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STEEL_INGOT).get(), 0.7F, 200)
                .group("sakura").unlockedBy("has_tamahagane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "steel_ingot_from_smelting"));

        // Forging chain: zuku_ingot -> sagegane -> iron_ingot (restores 1.12.2 progression).
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAGEGANE).get(), 1)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ZUKU_INGOT).get(), 4)
                .group("sakura").unlockedBy("has_zuku_ingot", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ZUKU_INGOT).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sagegane_from_zuku_ingot"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.IRON_INGOT, 1)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAGEGANE).get(), 2)
                .group("sakura").unlockedBy("has_sagegane", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAGEGANE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "iron_ingot_from_sagegane"));

        // Maple Log -> Charcoal (smelting)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.MAPLE_LOG.get()), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 200)
                .group("sakura").unlockedBy("has_maple_log", has(BlockRegistry.MAPLE_LOG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "charcoal_from_maple_log"));

        // Sakura Log -> Charcoal (smelting)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.SAKURA_LOG.get()), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 200)
                .group("sakura").unlockedBy("has_sakura_log", has(BlockRegistry.SAKURA_LOG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "charcoal_from_sakura_log"));

        // Vanilla -> Vanilla Roast (smelting/smoking/campfire)
        foodSmeltingRecipes("vanilla_roast", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VANILLA).get(),
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VANILLA_ROAST).get(), 0.35F, output);

        // Chestnut -> Chestnut Toasted (smelting/smoking/campfire)
        foodSmeltingRecipes("chestnut_toasted", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHESTNUT).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.CHESTNUT_TOASTED).get(), 0.35F, output);

        // Matsutake -> Roast Matsutake (smelting/smoking/campfire)
        foodSmeltingRecipes("roast_matsutake", FoodRegistry.FOODSET.get(SakuraFoodSet.MATSUTAKE).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.ROAST_MATSUTAKE).get(), 0.35F, output);

        // Raw Taiyaki -> Taiyaki (smelting/smoking/campfire)
        foodSmeltingRecipes("taiyaki", FoodRegistry.FOODSET.get(SakuraFoodSet.RAW_TAIYAKI).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.TAIYAKI).get(), 0.35F, output);

        // Boiled Bonito -> Smoked Bonito (smoking/smelting - drying process)
        foodSmeltingRecipes("smoked_bonito", FoodRegistry.FOODSET.get(SakuraFoodSet.BOILED_BONITO).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.SMOKED_BONITO).get(), 0.35F, output);

        // Smoked Bonito -> Dried Bonito (smelting - further drying)
        foodSmeltingRecipes("dried_bonito", FoodRegistry.FOODSET.get(SakuraFoodSet.SMOKED_BONITO).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get(), 0.35F, output);

        // Brown Rice Cooked -> Dried Brown Rice (smelting/smoking - drying)
        foodSmeltingRecipes("dried_brown_rice", FoodRegistry.FOODSET.get(SakuraFoodSet.BROWN_RICE_COOKED).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BROWN_RICE).get(), 0.35F, output);

        // Rice Cooked -> Dried Rice (smelting/smoking - drying)
        foodSmeltingRecipes("dried_rice", FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get(), 0.35F, output);

        // Seaweed Raw -> Seaweed (dried) (smelting/smoking - drying)
        foodSmeltingRecipes("seaweed_dried", FoodRegistry.FOODSET.get(SakuraFoodSet.SEAWEED_RAW).get(),
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SEAWEED).get(), 0.1F, output);

        // Imogara -> Dried Imogara (smelting/smoking - drying)
        foodSmeltingRecipes("dried_imogara", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.IMOGARA).get(),
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get(), 0.1F, output);

        // Dough Okinoyaki -> Okinoyaki (smelting/smoking/campfire)
        foodSmeltingRecipes("okinoyaki", FoodRegistry.FOODSET.get(SakuraFoodSet.DOUGH_OKINOYAKI).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.OKINOYAKI).get(), 0.35F, output);

        // Brown Rice -> Fried Brown Rice (smelting - roasting)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE).get()), RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_BROWN_RICE).get(), 0.35F, 200)
                .group("sakura").unlockedBy("has_brown_rice", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fried_brown_rice_from_smelting"));

        // Chestnut Burrs -> Chestnut (smelting to remove burrs)
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHESTNUT_BURRS).get()), RecipeCategory.MISC, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHESTNUT).get(), 0.15F, 200)
                .group("sakura").unlockedBy("has_chestnut_burrs", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHESTNUT_BURRS).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chestnut_from_smelting"));
    }

    private void registerDrinkRecipes(RecipeOutput output) {
        // ===== TEA DRINKS (CookingPot) =====
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.GREEN_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "green_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.BLACK_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BLACK_TEA_LEAVES).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "black_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.EARL_GREY).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EARL_GREY_LEAVES).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "earl_grey_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.FRUIT_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FRUIT_TEA_LEAVES).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fruit_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.MINT_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MINT_TEA_LEAVES).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mint_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.BARLEY_TEA).get())
                .requires(SakuraItemTags.GRAIN_WHEAT)
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "barley_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.BROWN_RICE_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RICE_TEA_LEAVES).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "brown_rice_tea_cooking"));

        // Milk teas: tea + milk
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.MILK_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BLACK_TEA_LEAVES).get())
                .requires(SakuraItemTags.MILK)
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "milk_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.MILK_GREEN_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .requires(SakuraItemTags.MILK)
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "milk_green_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.MILK_EARL_GREY).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EARL_GREY_LEAVES).get())
                .requires(SakuraItemTags.MILK)
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "milk_earl_grey_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.MILK_FRUIT_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FRUIT_TEA_LEAVES).get())
                .requires(SakuraItemTags.MILK)
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "milk_fruit_tea_cooking"));

        // Lemon teas: tea + lemon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.LEMON_BLACK_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BLACK_TEA_LEAVES).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "lemon_black_tea_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        DrinkRegistry.TEAS.get(SakuraTeaSet.LEMON_GREEN_TEA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .requires(ItemRegistry.CUP.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "lemon_green_tea_cooking"));

        // ===== ALCOHOL DRINKS (shapeless: bottle + cup/container = filled drink) =====
        // These are filled from fluid using L2IS (LiquidToItem) in game, but we provide a crafting alternative
        // Beer glass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.BEER_BUCKET.get())
                .unlockedBy("has_beer", has(BucketItemRegistry.BEER_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_beer"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_DOBUROKU).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.DOBUROKU_BUCKET.get())
                .unlockedBy("has_doburoku", has(BucketItemRegistry.DOBUROKU_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_doburoku"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SAKE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.SAKE_BUCKET.get())
                .unlockedBy("has_sake", has(BucketItemRegistry.SAKE_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_sake"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SHOUCHU).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.SHOUCHU_BUCKET.get())
                .unlockedBy("has_shouchu", has(BucketItemRegistry.SHOUCHU_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_shouchu"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.RED_WINE_BUCKET.get())
                .unlockedBy("has_red_wine", has(BucketItemRegistry.RED_WINE_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_red_wine"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.WHITE_WINE_BUCKET.get())
                .unlockedBy("has_white_wine", has(BucketItemRegistry.WHITE_WINE_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_white_wine"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.CHAMPAGNE_BUCKET.get())
                .unlockedBy("has_champagne", has(BucketItemRegistry.CHAMPAGNE_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_champagne"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.RUM_BUCKET.get())
                .unlockedBy("has_rum", has(BucketItemRegistry.RUM_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_rum"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.WHISKEY_BUCKET.get())
                .unlockedBy("has_whiskey", has(BucketItemRegistry.WHISKEY_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_whiskey"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.BRANDY_BUCKET.get())
                .unlockedBy("has_brandy", has(BucketItemRegistry.BRANDY_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_brandy"));

        // ===== BASE SPIRITS =====
        // Gin and tequila - glass filled from bucket
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.GIN_BUCKET.get())
                .unlockedBy("has_gin", has(BucketItemRegistry.GIN_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_gin"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.TEQUILA_BUCKET.get())
                .unlockedBy("has_tequila", has(BucketItemRegistry.TEQUILA_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_tequila"));
        // Vodka, liqueur, cocoa_liqueur have fluids - glass filled from bucket
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.VODKA_BUCKET.get())
                .unlockedBy("has_vodka", has(BucketItemRegistry.VODKA_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_vodka"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.LIQUEUR_BUCKET.get())
                .unlockedBy("has_liqueur", has(BucketItemRegistry.LIQUEUR_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_liqueur"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_COCOA_LIQUEUR).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .requires(BucketItemRegistry.COCOA_LIQUEUR_BUCKET.get())
                .unlockedBy("has_cocoa_liqueur", has(BucketItemRegistry.COCOA_LIQUEUR_BUCKET.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_cocoa_liqueur"));

        // ===== COCKTAILS (combining alcohol drinks) =====
        // Kir: white wine + blackcurrant juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_KIR).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get())
                .unlockedBy("has_white_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_kir"));

        // Kir Royale: champagne + blackcurrant juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_KIR_ROYALE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get())
                .unlockedBy("has_champagne", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_kir_royale"));

        // Cassis Orange: blackcurrant juice + orange juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_CASSIS_ORANGE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .unlockedBy("has_juice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_cassis_orange"));

        // Cassis Soda: blackcurrant juice + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_CASSIS_SODA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())
                .unlockedBy("has_juice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_cassis_soda"));

        // Mimosa: champagne + orange juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_MIMOSA).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .unlockedBy("has_champagne", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_mimosa"));

        // Shandy Gaff: beer + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SHANDY_GAFF).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_beer", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_shandy_gaff"));

        // Red Eye: beer + tomato sauce
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_RED_EYE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE).get())
                .unlockedBy("has_beer", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_red_eye"));

        // Highball: whiskey + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_HIGHBALL).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_whiskey", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_highball"));

        // Gin Tonic: gin + soda water + lemon
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_GIN_TONIC).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .unlockedBy("has_gin", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_gin_tonic"));

        // Gimlet: gin + lemon juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_GIMLET).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .unlockedBy("has_gin", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_gimlet"));

        // Gin Fizz: gin + lemon juice + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_GIN_FIZZ).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_gin", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_gin_fizz"));

        // Screwdriver: vodka + orange juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SCREWDRIVER).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .unlockedBy("has_vodka", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_screwdriver"));

        // Salty Dog: vodka + lemon juice + salt
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SALTY_DOG).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SALT)
                .unlockedBy("has_vodka", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_salty_dog"));

        // Moscow Mule: vodka + soda water + lemon
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_MOSCOW_MULE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .unlockedBy("has_vodka", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_moscow_mule"));

        // Tequila Sunrise: tequila + orange juice + lemon juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_TEQUILA_SUNRISE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .unlockedBy("has_tequila", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_tequila_sunrise"));

        // Margarita: tequila + lemon juice + salt
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_MARGARITA).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SALT)
                .unlockedBy("has_tequila", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_margarita"));

        // Cuba Libre: rum + soda water + lemon
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_CUBA_LIBRE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .unlockedBy("has_rum", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_cuba_libre"));

        // Daiquiri: rum + lemon juice + sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_DAIQUIRI).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_rum", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_daiquiri"));

        // Grog: rum + lemon juice + sugar (hot)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_GROG).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SUGAR).requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_rum", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_grog"));

        // Hot Toddy: whiskey + lemon juice + sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_HOT_TODDY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_whiskey", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_hot_toddy"));

        // Sangria: red wine + orange juice + fruit
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SANGRIA).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .requires(Items.APPLE)
                .unlockedBy("has_red_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_sangria"));

        // Spritzer: white wine + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SPRITZER).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_white_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_spritzer"));

        // Martini: gin + white wine
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_MARTINI).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get())
                .unlockedBy("has_gin", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_martini"));

        // Americano: red wine + liqueur + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_AMERICANO).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_red_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_americano"));

        // Negroni: gin + red wine + liqueur
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_NEGRONI).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .unlockedBy("has_gin", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_negroni"));

        // Alexander: brandy + cocoa liqueur + milk
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_ALEXANDER).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_COCOA_LIQUEUR).get())
                .requires(SakuraItemTags.MILK)
                .unlockedBy("has_brandy", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_alexander"));

        // Bellini: champagne + lemon juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_BELLINI).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .unlockedBy("has_champagne", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_bellini"));

        // Kalimotxo: red wine + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_KALIMOTXO).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_red_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_kalimotxo"));

        // Kitty: red wine + orange juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_KITTY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .unlockedBy("has_red_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_kitty"));

        // Operator: white wine + orange juice + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_OPERATOR).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_white_wine", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_operator"));

        // Matador: tequila + orange juice + lemon
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_MATADOR).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .unlockedBy("has_tequila", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_matador"));

        // Hot Buttered Rum: rum + sugar + milk
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_HOT_BUTTERED_RUM).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.MILK)
                .unlockedBy("has_rum", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_hot_buttered_rum"));

        // Pina Colada: rum + milk + sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_PINA_COLADA).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(SakuraItemTags.MILK)
                .requires(SakuraItemTags.SUGAR).requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_rum", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_pina_colada"));

        // Black Russian: vodka + cocoa liqueur
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_BLACK_RUSSIAN).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_COCOA_LIQUEUR).get())
                .unlockedBy("has_vodka", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_black_russian"));

        // Godfather: whiskey + liqueur + almond
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_GODFATHER).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ALMOND).get())
                .unlockedBy("has_whiskey", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_godfather"));

        // Godmother: vodka + liqueur + almond
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_GODMOTHER).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.ALMOND).get())
                .unlockedBy("has_vodka", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_godmother"));

        // Sidecar: brandy + liqueur + lemon juice
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SIDECAR).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .unlockedBy("has_brandy", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_sidecar"));

        // Bloody Mary: vodka + tomato sauce + lemon juice + salt
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_BLOODY_MARY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SALT)
                .unlockedBy("has_vodka", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_bloody_mary"));

        // Old Fashioned: whiskey + sugar + lemon
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_OLD_FASHIONED).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(SakuraItemTags.SUGAR)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .unlockedBy("has_whiskey", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_old_fashioned"));

        // Whiskey Sour: whiskey + lemon juice + sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_WHISKEY_SOUR).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SUGAR)
                .unlockedBy("has_whiskey", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_whiskey_sour"));

        // Mojito: rum + lemon juice + sugar + soda water
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_MOJITO).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get())
                .requires(SakuraItemTags.SUGAR)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get())
                .unlockedBy("has_rum", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_mojito"));

        // Rusty Nail: whiskey + liqueur
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_RUSTY_NAIL).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get())
                .unlockedBy("has_whiskey", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_rusty_nail"));

        // Saketini: sake + gin
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DrinkRegistry.COCKTAILS.get(SakuraCocktailSet.GLASS_SAKETINI).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SAKE).get())
                .requires(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get())
                .unlockedBy("has_sake", has(DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SAKE).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "glass_saketini"));
    }

    private void registerMortarRecipe(RecipeOutput output) {
        StoneMortarRecipeBuilder.mortar(Items.BONE_MEAL, 3).addResult(Items.BONE_MEAL, 3).requires(Tags.Items.BONES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bonemeal_from_mortar"));

        StoneMortarRecipeBuilder.mortar(Items.SAND).addResult(Items.FLINT).requires(Tags.Items.GRAVELS).save(output,
                ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "flint_from_mortar"));

        StoneMortarRecipeBuilder.mortar(Items.GRAVEL)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SALT).get(), 2)
                .requires(Tags.Items.COBBLESTONES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "salt_from_mortar"));

        StoneMortarRecipeBuilder.mortar(Items.COBBLESTONE)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ALKALINE).get(), 2).requires(Tags.Items.STONES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "alkaline_from_mortar"));

        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHARCOAL_POWDER).get(), 1)
                .requires(Ingredient.of(Items.CHARCOAL,
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL).get()))
                .requires(Ingredient.of(Items.CHARCOAL,
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL).get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "charcoal_powder"));

        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE).get(), 1)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE).get(), 1)
                .requires(SakuraItemTags.SEEDS_RICE).requires(SakuraItemTags.SEEDS_RICE)
                .requires(SakuraItemTags.SEEDS_RICE).requires(SakuraItemTags.SEEDS_RICE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "brown_rice_from_mortar"));
        StoneMortarRecipeBuilder.mortar(Items.GREEN_DYE, 1).addResult(Items.GREEN_DYE, 1).requires(ItemTags.LEAVES)
                .requires(ItemTags.LEAVES).requires(ItemTags.LEAVES).requires(ItemTags.LEAVES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dye_green_from_leaves"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.MINCED_MEAT).get(), 2)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.MINCED_MEAT).get(), 2)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "minced_meat"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_RAW).get(), 2)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_RAW).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MINCED_MEAT).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get())
                .requires(SakuraItemTags.CROPS_ONION).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "burger_raw"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get(), 1)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get(), 1).requires(SakuraItemTags.FISHES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "surimi_from_mortar"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get(), 2)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get(), 2).requires(SakuraItemTags.BREAD)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "breadcrumbs_from_breads"));

        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RICE).get(), 1)
        		.addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NUKA).get())
                .requires(SakuraItemTags.RICE_BROWN).requires(SakuraItemTags.RICE_BROWN)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_from_mortar"));
        StoneMortarRecipeBuilder.mortar(Items.SUGAR, 3)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES).get())
                .requires(Items.SUGAR_CANE).save(output,
                ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sugar_from_mortar"));
        StoneMortarRecipeBuilder.mortar(Items.SUGAR, 1)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES).get())
                .requires(Items.BEETROOT).save(output,
                ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "beetsugar_from_mortar"));
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR).get(), 1)
        		.addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW).get(), 1)
                .requires(SakuraItemTags.GRAIN_WHEAT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "flour_from_mortar"));
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR_BUCKWHEAT).get(), 1)
                .requires(SakuraItemTags.GRAIN_BUCKWHEAT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "flour_buckwheat_from_mortar"));
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR_RICE).get(), 1)
                .requires(SakuraItemTags.RICE_RICE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "flour_rice_from_mortar"));

        // Blaze rod -> blaze powder (3 + 2 bonus)
        StoneMortarRecipeBuilder.mortar(Items.BLAZE_POWDER, 3)
                .addResult(Items.BLAZE_POWDER, 2)
                .requires(Items.BLAZE_ROD)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "blaze_powder_from_mortar"));

        // Fish paste (surimi) from potato + fish + egg
        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get(), 8)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "surimi_paste_from_mortar"));

        // Flour from potato (2)
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR).get(), 2)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(Tags.Items.CROPS_POTATO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "flour_from_potato_mortar"));

        // Flour from bread (2)
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR).get(), 2)
                .requires(SakuraItemTags.BREAD)
                .requires(SakuraItemTags.BREAD)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "flour_from_bread_mortar"));

        // Green tea leaves -> Mocha (matcha/ground tea) - 4 tea leaves = 6 mocha
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get(), 3)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get(), 3)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mocha_from_mortar"));

        // Dried bonito -> Bonito shavings - 4 dried bonito = 32 bonito shavings (split as 16+16)
        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BONITO_SHAVING).get(), 16)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.BONITO_SHAVING).get(), 16)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bonito_shaving_from_mortar"));

        // Bun -> Breadcrumbs (4) - grinding buns into crumbs
        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get(), 4)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "breadcrumbs_from_bun_mortar"));

        // Cocktail base ingredient recipes
        // Lemon -> Lemon Juice (2)
        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "lemon_juice_from_mortar"));

        // Apple -> Orange Juice (2) - apples as citrus substitute
        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.ORANGE_JUICE).get(), 2)
                .requires(Items.APPLE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "orange_juice_from_mortar"));

        // Sweet Berries -> Blackcurrant Juice (2)
        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BLACKCURRANT_JUICE).get(), 2)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SWEET_BERRIES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "blackcurrant_juice_from_mortar"));

    }

    // Farmer's Delight compat recipes are shipped as static JSON with neoforge:conditional conditions.
    // See src/generated/resources/data/sakura/recipe/*_from_sakura.json

    private void registerCookingRecipe(RecipeOutput output) {
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.CUISINES.get(SakuraCuisineSet.BEEF_STICK).get(), 2)
                .requires(SakuraItemTags.RAW_BEEF).requires(SakuraItemTags.BAMBOO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "beef_stick_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.FOODSET.get(SakuraFoodSet.NATTO).get(), 2, 1.0f, 600)
                .requires(SakuraItemTags.CROPS_SOYBEAN).requires(SakuraItemTags.STRAW)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "natto_fermenting"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.CUISINES.get(SakuraCuisineSet.CHICKEN_STICK).get(), 2)
                .requires(SakuraItemTags.RAW_CHICKEN).requires(SakuraItemTags.BAMBOO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chicken_stick_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.CUISINES.get(SakuraCuisineSet.PORK_STICK).get(), 2)
                .requires(SakuraItemTags.RAW_PORK).requires(SakuraItemTags.BAMBOO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pork_stick_cooking"));

        // Soda Water - water + sugar in cooking pot
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get(), 2)
                .requires(Items.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soda_water_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU).get(), 2)
                .requires(SakuraItemTags.CROPS_SOYBEAN).requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tofu_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU_FRIED).get(), 2)
                .requires(SakuraItemTags.TOFU).requires(SakuraItemTags.FLOUR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tofu_fried_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KAMABOKO).get(), 2)
                .requires(SakuraItemTags.SALT).requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kamaboko_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SATSUMAAGE).get(), 2)
                .requires(SakuraItemTags.SALT).requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "satsumaage_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get(), 2)
                .requires(SakuraItemTags.RAW_PORK)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get())
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pork_katsu_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_CHICKEN).get(), 2)
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get())),
                        new Ingredient.TagValue(SakuraItemTags.FLOUR))
                        ))
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fried_chicken_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MASHED_POTATO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS).get())
                .requires(SakuraItemTags.MILK)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "croquette_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISHCAKE).get(), 4)
                .requires(SakuraItemTags.SALT).requires(SakuraItemTags.EGGS).requires(SakuraItemTags.CROPS_TARO)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "hanpen_taro_cooking"));
        
        CookingPotRecipeBuilder
            .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                    ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get(), 4)
            .requires(SakuraItemTags.SUGAR)
            .requires(SakuraItemTags.SOYSAUCE)
            .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
            .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kaeshi_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISHCAKE).get(), 4)
                .requires(SakuraItemTags.SALT).requires(SakuraItemTags.EGGS)
                .requires(
                        Ingredient.fromValues(Stream.of(
                                new Ingredient.TagValue(SakuraItemTags.CROPS_TARO),
                                new Ingredient.TagValue(Tags.Items.CROPS_POTATO)
                                )
                            )
                        )
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "hanpen_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOUP_REDBEAN).get(), 2)
                .requires(SakuraItemTags.CROPS_REDBEAN).requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soup_redbean_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE_ROLL).get())
                .requires(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON),
                        new Ingredient.TagValue(SakuraItemTags.FISHES))))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "cabbage_roll_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get(), 2)
                .requires(SakuraItemTags.CROPS_REDBEAN).requires(SakuraItemTags.CROPS_REDBEAN)
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "redbean_paste_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE).get(), 2)
                .requires(SakuraItemTags.CROPS_TOMATO).requires(SakuraItemTags.CROPS_TOMATO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tomato_sauce_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO).get(), 2)
                .requires(SakuraItemTags.DOUGH_RICE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dango_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANANKO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dananko_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANMITARASHI).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO).get()).requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "danmitarashi_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANSANSYOKU).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO).get())
                .requires(BlockRegistry.SAKURA_LEAVES.get()).requires(Items.SHORT_GRASS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dansansyoku_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DAIFUKU).get(), 2)
                .requires(SakuraItemTags.DOUGH_RICE)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "daifuku_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KUSA_DAIFUKU).get(), 2)
                .requires(SakuraItemTags.DOUGH_RICE).requires(Items.SHORT_GRASS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kusa_daifuku_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.BROWN_RICE_COOKED).get())
                .requires(SakuraItemTags.RICE_BROWN)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "brown_rice_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get())
                .requires(SakuraItemTags.RICE_RICE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_REDBEAN).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.CROPS_REDBEAN)
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_redbean_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_NATTO).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.NATTO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_natto_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_NATTO_EGG).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.NATTO).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_natto_egg_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BAMBOO).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(BlockRegistry.BAMBOOSHOOT.get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_bamboo_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_MUSHROOM).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.MUSHROOMS)
                
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_mushrooms_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BEEF).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_BEEF)
                
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_beef_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_PORK).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_PORK)
                
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_pork_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_FISH).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_FISHES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_fish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_EGG).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_eggs_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BEEF_EGG).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_BEEF).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_beef_eggs_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_PORK_EGG).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_PORK).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_pork_eggs_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU_DISH).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_katsu_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_OYAKO).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_CHICKEN).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_oyako_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_OYAKO_FISH).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.RAW_FISHES).requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_oyako_fish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.OMURICE).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON),
                        new Ingredient.TagValue(SakuraItemTags.FISHES))))
                .requires(SakuraItemTags.TOMATOSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "omurice_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TEMPURA_BATTER).get())
                .requires(SakuraItemTags.SHRIMP)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tempura_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FRIES).get(), 2)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fries_cooking"));
        
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.MASHED_POTATO).get(), 2)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mashed_potato_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE_SALT).get())
                .requires(SakuraItemTags.SALT).requires(SakuraItemTags.RAW_FISHES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fish_bake_salt_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE).get())
                .requires(SakuraItemTags.RAW_FISHES).requires(SakuraItemTags.SOYSAUCE)
                
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fish_bake_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI).get(), 2)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.EGGS).requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.DASHI)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tamagoyaki_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.OSUIMONO).get(), 2)
                .requires(Items.DRIED_KELP).requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "osuimono_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOUP_MISO).get(), 2)
                .requires(SakuraItemTags.MISO).requires(SakuraItemTags.TOFU)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soup_miso_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIKUJAGA).get(), 2)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF))))
                .requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_POTATO).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nikujaga_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_PUMPKIN).get(), 2)
                .requires(SakuraItemTags.CROPS_PUMPKIN).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nimono_pumpkin_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_RADISH).get(), 2)
                .requires(SakuraItemTags.CROPS_RADISH).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nimono_radish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.IMOTAKI).get(), 2)
                .requires(SakuraItemTags.CROPS_TARO).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "imotaki_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUZENNI).get(), 2)
                .requires(SakuraItemTags.RAW_CHICKEN).requires(SakuraItemTags.MUSHROOMS)
                .requires(SakuraItemTags.VEGETABLES).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chikuzenni_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NOPPEI_JIRU).get(), 2)
                .requires(SakuraItemTags.RAW_CHICKEN).requires(SakuraItemTags.CROPS_TARO)
                .requires(SakuraItemTags.VEGETABLES).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "noppei_jiru_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_FISH).get(), 2)
                .requires(SakuraItemTags.RAW_FISHES).requires(SakuraItemTags.MISO).requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nimono_fish_cooking"));
        
        CookingPotRecipeBuilder
		        .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
		                FoodRegistry.FOODSET.get(SakuraFoodSet.NIKUJAGA).get(), 2)
		        .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
		                new Ingredient.TagValue(SakuraItemTags.RAW_BEEF))))
		        .requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_POTATO)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get())
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nikujaga_cooking_kaeshi"));
		
		CookingPotRecipeBuilder
		        .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
		                FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_PUMPKIN).get(), 2)
		        .requires(SakuraItemTags.CROPS_PUMPKIN)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get())
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nimono_pumpkin_cooking_kaeshi"));
		
		CookingPotRecipeBuilder
		        .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
		                FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_RADISH).get(), 2)
		        .requires(SakuraItemTags.CROPS_RADISH)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get())
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nimono_radish_cooking_kaeshi"));
		
		CookingPotRecipeBuilder
		        .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
		                FoodRegistry.FOODSET.get(SakuraFoodSet.IMOTAKI).get(), 2)
		        .requires(SakuraItemTags.CROPS_TARO)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get())
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "imotaki_cooking_kaeshi"));
		
		CookingPotRecipeBuilder
		        .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
		                FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUZENNI).get(), 2)
		        .requires(SakuraItemTags.RAW_CHICKEN).requires(SakuraItemTags.MUSHROOMS)
		        .requires(SakuraItemTags.VEGETABLES)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get())
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chikuzenni_cooking_kaeshi"));
		
		CookingPotRecipeBuilder
		        .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
		                FoodRegistry.FOODSET.get(SakuraFoodSet.NOPPEI_JIRU).get(), 2)
		        .requires(SakuraItemTags.RAW_CHICKEN).requires(SakuraItemTags.CROPS_TARO)
		        .requires(SakuraItemTags.VEGETABLES)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI).get())
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "noppei_jiru_cooking_kaeshi"));
		    

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FUROFUKI_DAIKON).get(), 2)
                .requires(SakuraItemTags.CROPS_RADISH).requires(SakuraItemTags.MISO).requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "furofuki_daikon_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DASHI).get(), 1)
                .requires(SakuraItemTags.RAW_FISHES).requires(Items.DRIED_KELP)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "dashi_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.YAKINIKU).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "yakiniku_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_FRIED).get())
                .requires(SakuraItemTags.RICE_RICE).requires(SakuraItemTags.EGGS).requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_fried_cooking"));

        // ==========================================
        // RAMEN RECIPES
        // ==========================================
        // Plain ramen: raw ramen + noodle soup + water
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_cooking"));

        // Beef ramen
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_BEEF).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.RAW_BEEF)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_beef_cooking"));

        // Egg ramen
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_EGG).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_egg_cooking"));

        // Tempura ramen
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_TEMPURA).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_tempura_cooking"));

        // Fried tofu ramen
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_FRIEDTOFU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.TOFU_FRIED)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_friedtofu_cooking"));

        // Katsu ramen
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_KATSU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_katsu_cooking"));

        // Chicken ramen (fried chicken)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_CHICKEN).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_CHICKEN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_chicken_cooking"));

        // Croquette ramen
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_CROQUETTE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_croquette_cooking"));

        // Large ramen (all toppings)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RAMEN_LARGE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RAMEN_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ramen_large_cooking"));

        // ==========================================
        // UDON RECIPES
        // ==========================================
        // Plain udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_cooking"));

        // Beef udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_BEEF).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.RAW_BEEF)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_beef_cooking"));

        // Egg udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_EGG).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_egg_cooking"));

        // Tempura udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_TEMPURA).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_tempura_cooking"));

        // Fried tofu udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_FRIEDTOFU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.TOFU_FRIED)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_friedtofu_cooking"));

        // Katsu udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_KATSU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_katsu_cooking"));

        // Chicken udon (fried chicken)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_CHICKEN).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_CHICKEN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_chicken_cooking"));

        // Croquette udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_CROQUETTE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_croquette_cooking"));

        // Large udon
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.UDON_LARGE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "udon_large_cooking"));

        // Yaki udon (fried)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.YAKI_UDON).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "yaki_udon_cooking"));

        // ==========================================
        // SOBA RECIPES
        // ==========================================
        // Plain soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_cooking"));

        // Beef soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_BEEF).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.RAW_BEEF)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_beef_cooking"));

        // Egg soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_EGG).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_egg_cooking"));

        // Tempura soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_TEMPURA).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_tempura_cooking"));

        // Fried tofu soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_FRIEDTOFU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(SakuraItemTags.TOFU_FRIED)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_friedtofu_cooking"));

        // Katsu soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_KATSU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_katsu_cooking"));

        // Chicken soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_CHICKEN).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_CHICKEN).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_chicken_cooking"));

        // Croquette soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_CROQUETTE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_croquette_cooking"));

        // Large soba
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_LARGE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_large_cooking"));

        // Yaki soba (fried)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.YAKI_SOBA).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "yaki_soba_cooking"));

        // Zaru soba (cold soba)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA_ZARU).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get())
                .requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "soba_zaru_cooking"));

        // ==========================================
        // PASTA RECIPES
        // ==========================================
        // Pasta tomato
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.PASTA_TOMATO).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PASTA_RAW).get())
                .requires(SakuraItemTags.TOMATOSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pasta_tomato_cooking"));

        // Pasta mushroom
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.PASTA_MUSHROOM).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PASTA_RAW).get())
                .requires(SakuraItemTags.MUSHROOMS)
                .requires(SakuraItemTags.MUSHROOMS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pasta_mushroom_cooking"));

        // Pasta white sauce
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.PASTA_WHITESAUCE).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PASTA_RAW).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.WHITE_SAUCE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pasta_whitesauce_cooking"));

        // Yaki pasta (fried)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.YAKI_PASTA).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PASTA_RAW).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "yaki_pasta_cooking"));

        // ==========================================
        // NOODLE SOUP (dashi-based)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get(), 4)
                .requires(SakuraItemTags.DASHI)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(Items.DRIED_KELP)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "noodle_soup_cooking"));

        // ==========================================
        // WHITE SAUCE
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.WHITE_SAUCE).get(), 2)
                .requires(SakuraItemTags.MILK)
                .requires(SakuraItemTags.FLOUR)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "white_sauce_cooking"));

        // ==========================================
        // CURRY SAUCE
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_POWDER).get())
                .requires(SakuraItemTags.CROPS_ONION)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(Tags.Items.CROPS_CARROT)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON))))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "curry_sauce_cooking"));

        // ==========================================
        // CURRY RICE
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_curry_cooking"));

        // Curry katsu rice
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_KATSU).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_curry_katsu_cooking"));

        // Curry burger rice
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_BURGER).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_curry_burger_cooking"));

        // Curry cheese rice
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_CURRY_CHEESE).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get())
                .requires(SakuraItemTags.CHEESE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_curry_cheese_cooking"));

        // Curry omurice
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CURRY_OMURICE).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CURRY_SAUCE).get())
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "curry_omurice_cooking"));

        // ==========================================
        // WHITE STEW
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.WHITE_STEW).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.WHITE_SAUCE).get())
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.MUSHROOMS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "white_stew_cooking"));

        // ==========================================
        // PUDDING AND SWEETS
        // ==========================================
        // Pudding (vanilla)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.PUDDING).get(), 2)
                .requires(SakuraItemTags.MILK)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.EGGS)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VANILLA).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pudding_cooking"));

        // Maple pudding
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.PUDDING_MAPLE).get(), 2)
                .requires(SakuraItemTags.MILK)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.EGGS)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VANILLA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MAPLE_SYRUP).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pudding_maple_cooking"));

        // Mocha pudding
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.PUDDING_MOCHA).get(), 2)
                .requires(SakuraItemTags.MILK)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.EGGS)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VANILLA).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOCHA).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "pudding_mocha_cooking"));

        // ==========================================
        // MATSUTAKE RICE
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_MATSUTAKE).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MATSUTAKE).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_matsutake_cooking"));

        // ==========================================
        // EGG DISHES
        // ==========================================
        // Soft-boiled egg
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.EGG_SOFT).get(), 2)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "egg_soft_cooking"));

        // Soy sauce egg
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.EGG_SOYSAUCE).get(), 2)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "egg_soysauce_cooking"));

        // ==========================================
        // ODEN
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.ODEN).get(), 2)
                .requires(Items.STICK)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI).get())
                .requires(SakuraItemTags.CROPS_RADISH)
                .requires(SakuraItemTags.EGGS)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "oden_cooking"));

        // ==========================================
        // CHAWANMUSHI
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CHAWANMUSHI).get(), 2)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.VEGETABLES)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NOODLE_SOUP).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chawanmushi_cooking"));

        // ==========================================
        // OCHAZUKE
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.OCHAZUKE).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.GREEN_TEA_LEAVES).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "ochazuke_cooking"));

        // ==========================================
        // FRUIT SALAD (using cooking pot as mixing)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 50),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FRUITSALAD).get(), 2)
                .requires(Items.APPLE)
                .requires(SakuraItemTags.SUGAR)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "fruitsalad_cooking"));

        // ==========================================
        // MABODOFU (mapo tofu)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.MABODOFU).get(), 2)
                .requires(SakuraItemTags.TOFU)
                .requires(SakuraItemTags.MISO)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mabodofu_cooking"));

        // Mabodofu variant using miso ball
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.MABODOFU).get(), 2)
                .requires(SakuraItemTags.TOFU)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MISO_BALL).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mabodofu_cooking_misoball"));

        // ==========================================
        // MABOQIEZI (mapo eggplant)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.MABOQIEZI).get(), 2)
                .requires(SakuraItemTags.CROPS_EGGPLANT)
                .requires(SakuraItemTags.MISO)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maboqiezi_cooking"));

        // ==========================================
        // RICE PORK FRIED (special variant)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_PORK_FRIED).get())
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_PORK)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.VEGETABLES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rice_pork_fried_cooking"));

        // ==========================================
        // BONITO PROCESSING
        // ==========================================
        // Boiled bonito (machined bonito + water)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.BOILED_BONITO).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_BONITO).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "boiled_bonito_cooking"));

        // ==========================================
        // WORCESTER SAUCE
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.WORCESTER_SAUCE).get(), 2)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(SakuraItemTags.SUGAR)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VINEGAR).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "worcester_sauce_cooking"));

        // ==========================================
        // VINEGAR
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VINEGAR).get(), 2)
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "vinegar_cooking"));

        // ==========================================
        // MAYO (mayonnaise)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MAYO).get(), 2)
                .requires(SakuraItemTags.EGGS)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VINEGAR).get())
                .requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mayo_cooking"));

        // ==========================================
        // ZOSUI (rice porridge with fish)
        // ==========================================
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.ZOSUI).get(), 2)
                .requires(SakuraItemTags.RICE_RICE)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(SakuraItemTags.RAW_CHICKEN),
                        new Ingredient.TagValue(SakuraItemTags.RAW_PORK),
                        new Ingredient.TagValue(SakuraItemTags.RAW_BEEF),
                        new Ingredient.TagValue(SakuraItemTags.RAW_MUTTON),
                        new Ingredient.TagValue(SakuraItemTags.FISHES))))
                .requires(SakuraItemTags.SOYSAUCE)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "zosui_cooking"));

        // Zosui zuiki (taro variant)
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.ZOSUI_ZUIKI).get(), 2)
                .requires(SakuraItemTags.RICE_RICE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "zosui_zuiki_cooking"));

    }

    private void registerFermenterRecipe(RecipeOutput output) {
    	
        FermenterRecipeBuilder
		        .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 100),
		        		FoodRegistry.FOODSET.get(SakuraFoodSet.PICKLED_RADISH).get(), 2, FluidStack.EMPTY, 0, 400)
		        .requires(SakuraItemTags.CROPS_RADISH)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NUKA).get())
		        .requires(SakuraItemTags.SALT)
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nukazuke_radish"));
        
        FermenterRecipeBuilder
		        .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 100),
		        		FoodRegistry.FOODSET.get(SakuraFoodSet.PICKLED_EGGPLANT).get(), 2, FluidStack.EMPTY, 0, 400)
		        .requires(SakuraItemTags.CROPS_EGGPLANT)
		        .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.NUKA).get())
		        .requires(SakuraItemTags.SALT)
		        .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "nukazuke_eggplant"));
    	
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KOUJI).get(), 2, FluidStack.EMPTY)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(SakuraItemTags.SALT)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "kouji_fermenting"));
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 1000),
                        new FluidStack(FluidRegistry.DOBUROKU.get(), 500))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(SakuraItemTags.KOUJI)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "doburoku_fermenting"));
        
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.BEER.get(), 100))
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.CROPS_HOP)
                .requires(SakuraItemTags.SUGAR_SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "basic_beer_fermenting"));
        
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.BEER.get(), 200),0,400)
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.YEAST)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "beer_fermenting"));
        
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.YEAST).get(), 4,FluidStack.EMPTY,0,400)
                .requires(SakuraItemTags.BROWN_MUSHROOMS)
                .requires(SakuraItemTags.SUGAR_SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "yeast_fermenting"));
        
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 100),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.YEAST).get(), 4,FluidStack.EMPTY,0,200)
                .requires(SakuraItemTags.YEAST)
                .requires(SakuraItemTags.SUGAR_SUGAR)
                .requires(SakuraItemTags.SUGAR_SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "yeast_multiply"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.BREWERS_ALCOHOL, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get(), 8, FluidStack.EMPTY)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN_KASU).get(), 1)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get()).requires(SakuraItemTags.KOUJI)
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "mirin_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromFluid(FluidRegistry.DOBUROKU.get(), 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU).get(), 2,
                        new FluidStack(FluidRegistry.SAKE.get(), 250), 10F, 500)
                .requires(SakuraItemTags.DUST_CHARCOAL)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sake_charcoal_fermenting"));
        
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromFluid(FluidRegistry.DOBUROKU.get(), 500),
                        new FluidStack(FluidRegistry.SAKE.get(), 100), 10F, 1000)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sake_fermenting"));
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 1000),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MISO).get(), 4,
                        FluidStack.EMPTY)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOYSAUCE).get(), 4)
                .requires(SakuraItemTags.CROPS_SOYBEAN)
                .requires(SakuraItemTags.CROPS_SOYBEAN)
                .requires(SakuraItemTags.KOUJI)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "miso_fermenting"));

        // Red wine: grape + sugar + yeast → red wine
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.RED_WINE.get(), 100))
                .requires(SakuraItemTags.CROPS_GRAPE)
                .requires(SakuraItemTags.CROPS_GRAPE)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.YEAST)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "red_wine_fermenting"));

        // White wine: green grape + sugar + yeast → white wine
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.WHITE_WINE.get(), 100))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.YEAST)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "white_wine_fermenting"));

        // Champagne: white wine + sugar + yeast → champagne
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromFluid(FluidRegistry.WHITE_WINE.get(), 200),
                        new FluidStack(FluidRegistry.CHAMPAGNE.get(), 100))
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.YEAST)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "champagne_fermenting"));
    }

    private void registerDistillerRecipe(RecipeOutput output) {
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromFluid(FluidRegistry.SAKE.get(), 1000),
                        new FluidStack(FluidRegistry.SHOUCHU.get(), 500))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "shouchu_from_sake_distillation"));
        
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromFluid(FluidRegistry.BEER.get(), 1000),
                        new FluidStack(FluidRegistry.WHISKEY.get(), 500))
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "whiskey_from_beer_distillation"));
        
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        new FluidStack(FluidRegistry.RUM.get(), 100))
                .requires(Items.SUGAR_CANE)
                .requires(Items.SUGAR_CANE)
                .requires(SakuraItemTags.YEAST)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rum_cane_distillation"));
        
        DistillerRecipeBuilder
            .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                    new FluidStack(FluidRegistry.RUM.get(), 100))
            .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES).get())
            .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES).get())
            .requires(SakuraItemTags.YEAST)
            .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "rum_molasses_distillation"));
        
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        new FluidStack(FluidRegistry.SHOUCHU.get(), 100))
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU).get())
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "shouchu_from_sakekasu_distillation"));

        // Vodka: water + potatoes (3x) → vodka
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.VODKA.get(), 100))
                .requires(Items.POTATO)
                .requires(Items.POTATO)
                .requires(Items.POTATO)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "vodka_distillation"));

        // Liqueur: base spirit + fruit + sugar → liqueur
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.BREWERS_ALCOHOL, 200),
                        new FluidStack(FluidRegistry.LIQUEUR.get(), 200))
                .requires(Items.APPLE)
                .requires(Items.APPLE)
                .requires(Items.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "liqueur_distillation"));

        // Cocoa Liqueur: base spirit + cocoa beans + sugar → cocoa liqueur
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.BREWERS_ALCOHOL, 200),
                        new FluidStack(FluidRegistry.COCOA_LIQUEUR.get(), 200))
                .requires(Items.COCOA_BEANS)
                .requires(Items.COCOA_BEANS)
                .requires(Items.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "cocoa_liqueur_distillation"));

        // Gin: base spirit + sweet berries (juniper substitute) → gin
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.BREWERS_ALCOHOL, 200),
                        new FluidStack(FluidRegistry.GIN.get(), 200))
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SWEET_BERRIES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "gin_distillation"));

        // Tequila: water + cactus (agave substitute) → tequila
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.TEQUILA.get(), 100))
                .requires(Items.CACTUS)
                .requires(Items.CACTUS)
                .requires(Items.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "tequila_distillation"));

        // Brandy: grape + sugar → brandy (from red grapes)
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.BRANDY.get(), 100))
                .requires(SakuraItemTags.CROPS_GRAPE)
                .requires(SakuraItemTags.CROPS_GRAPE)
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "brandy_grape_distillation"));

        // Brandy: green grape + sugar → brandy (from green grapes)
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.BRANDY.get(), 100))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE_GREEN).get())
                .requires(SakuraItemTags.SUGAR)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "brandy_green_grape_distillation"));
    }

    private void registerChoppingRecipes(RecipeOutput output) {
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH).get())
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.ItemValue(new ItemStack(Items.COD)),
                        new Ingredient.ItemValue(new ItemStack(Items.SALMON)),
                        new Ingredient.ItemValue(new ItemStack(Items.TROPICAL_FISH)))))
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduce(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH).get())
                .addByproduceWithChance(Items.BONE_MEAL, 0.5F)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "machined_fish_chopping"));

        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE).get())
                .requires(SakuraItemTags.CROPS_CABBAGE).requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduce(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE).get())
                .addByproduceWithChance(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE).get(), 0.5F)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sliced_cabbage_chopping"));

        // Sashimi slicing: machined fish -> sashimi with fish knife
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI).get())
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH).get())
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduce(FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI).get())
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sashimi_chopping"));

        // Bonito machining: bonito -> machined bonito with fish knife
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_BONITO).get(), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BONITO).get())
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduceWithChance(Items.BONE_MEAL, 0.5F)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "machined_bonito_chopping"));

        // Dried bonito -> bonito shavings with fish knife
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.BONITO_SHAVING).get(), 4)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get())
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduce(FoodRegistry.FOODSET.get(SakuraFoodSet.BONITO_SHAVING).get(), 2)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bonito_shaving_chopping"));

        // Onion slicing: onion -> sliced onion (reuse sliced cabbage mechanic pattern)
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.ONION).get(), 2)
                .requires(SakuraItemTags.CROPS_ONION)
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sliced_onion_chopping"));

        // Radish slicing
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.RADISH).get(), 2)
                .requires(SakuraItemTags.CROPS_RADISH)
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sliced_radish_chopping"));

        // Eggplant slicing
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT).get(), 2)
                .requires(SakuraItemTags.CROPS_EGGPLANT)
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sliced_eggplant_chopping"));

        // Tomato slicing
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO).get(), 2)
                .requires(SakuraItemTags.CROPS_TOMATO)
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sliced_tomato_chopping"));

        // Lumber from logs: chopping board with axe
        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA).get(), 8)
                .requires(BlockRegistry.SAKURA_LOG.get())
                .requiresTool(SakuraItemTags.TOOLS_AXES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "sakura_lumber_chopping"));

        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE).get(), 8)
                .requires(BlockRegistry.MAPLE_LOG.get())
                .requiresTool(SakuraItemTags.TOOLS_AXES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maple_lumber_chopping"));

        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_BAMBOO).get(), 8)
                .requires(SakuraItemTags.BAMBOO)
                .requiresTool(SakuraItemTags.TOOLS_AXES)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_lumber_chopping"));

        // Chestnut: remove burrs with knife
        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHESTNUT).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHESTNUT_BURRS).get())
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "chestnut_chopping"));

        // Peppercorn -> black pepper with mortar-like chopping
        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BLACK_PEPPER).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PEPPERCORN_GREEN).get())
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "black_pepper_chopping"));

        // Peppercorn red -> white pepper
        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.WHITE_PEPPER).get(), 2)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PEPPERCORN_RED).get())
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "white_pepper_chopping"));

        // Seaweed slicing
        ChoppingBoardRecipeBuilder.chop(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SEAWEED).get(), 2)
                .requires(SakuraItemTags.CROPS_SEAWEED)
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .save(output, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "seaweed_chopping"));

    }

    private void foodSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience,
            RecipeOutput output) {
        String namePrefix = ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, name).toString();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD,result, experience,200).unlockedBy("has_ingredient",has(ingredient)).group("sakura").save(output);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD,result, experience,600).unlockedBy("has_ingredient",has(ingredient)).group("sakura").save(output, namePrefix + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD,result, experience,100).unlockedBy("has_ingredient",has(ingredient)).group("sakura").save(output, namePrefix + "_from_smoking");
    }

    public ShapedRecipeBuilder makeLumberToPlank(Supplier<? extends Block> blockOut, Ingredient ingreIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,blockOut.get()).pattern("##").pattern("##").define('#', ingreIn);
    }

    public ShapelessRecipeBuilder makeLumber(Supplier<? extends Item> ingotOut, Ingredient ingreIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ingotOut.get(), 8).requires(ingreIn);
    }

    public ShapelessRecipeBuilder makeItemToBucket(Supplier<? extends Item> ingotOut, Ingredient ingreIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ingotOut.get()).requires(ingreIn).requires(ingreIn).requires(ingreIn)
                .requires(ingreIn).requires(ingreIn).requires(ingreIn).requires(ingreIn).requires(ingreIn)
                .requires(Items.BUCKET);
    }

    public  ShapedRecipeBuilder makeIngotToBlock(Supplier<? extends Item> result, Supplier<? extends Item> ingredient){
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC,result.get(),1).pattern("###").pattern("###").pattern("###").define('#', ingredient.get())
                .group("sakura").unlockedBy("has_ingredient",has(ingredient.get()));
    }

    public ShapelessRecipeBuilder makeBlockToIngot(Supplier<? extends Item> result, Supplier<? extends Item> ingredient){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,result.get(),9).requires(ingredient.get())
                .group("sakura").unlockedBy("has_ingredient",has(ingredient.get()));
    }

    private void makeSlab(RecipeOutput output, Supplier<? extends Block> slabBlock, Supplier<? extends Block> sourceBlock) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slabBlock.get(), 6)
                .pattern("###")
                .define('#', sourceBlock.get())
                .group("sakura").unlockedBy("has_block", has(sourceBlock.get()))
                .save(output);
    }

    private void makeStair(RecipeOutput output, Supplier<? extends Block> stairBlock, Supplier<? extends Block> plankBlock) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairBlock.get(), 4)
                .pattern("#  ").pattern("## ").pattern("###")
                .define('#', plankBlock.get())
                .group("sakura").unlockedBy("has_planks", has(plankBlock.get()))
                .save(output);
    }
}
