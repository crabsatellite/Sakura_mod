package cn.mcmod.sakura.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.recipes.base.AbstractRecipeSerializer;

public class RecipeTypeRegistry {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister
            .create(Registries.RECIPE_TYPE, SakuraMod.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, SakuraMod.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CookingPotRecipe>> COOKING_RECIPE_TYPE = RECIPE_TYPES
            .register("cooking", () -> recipeType("cooking"));
    public static final DeferredHolder<RecipeType<?>, RecipeType<StoneMortarRecipe>> STONE_MORTAR_RECIPE_TYPE = RECIPE_TYPES
            .register("stone_mortar", () -> recipeType("stone_mortar"));
    public static final DeferredHolder<RecipeType<?>, RecipeType<FermenterRecipe>> FERMENTER_RECIPE_TYPE = RECIPE_TYPES
            .register("fermenting", () -> recipeType("fermenting"));
    public static final DeferredHolder<RecipeType<?>, RecipeType<DistillerRecipe>> DISTILLER_RECIPE_TYPE = RECIPE_TYPES
            .register("distillation", () -> recipeType("distillation"));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ChoppingRecipe>> CHOPPING_RECIPE_TYPE = RECIPE_TYPES
            .register("chopping", () -> recipeType("chopping"));


    public static final DeferredHolder<RecipeSerializer<?>, AbstractRecipeSerializer<StoneMortarRecipe>> STONE_MORTAR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("stone_mortar", () -> new AbstractRecipeSerializer<>(StoneMortarRecipe.class));
    public static final DeferredHolder<RecipeSerializer<?>, AbstractRecipeSerializer<CookingPotRecipe>> COOKING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("cooking", () -> new AbstractRecipeSerializer<>(CookingPotRecipe.class));
    public static final DeferredHolder<RecipeSerializer<?>, AbstractRecipeSerializer<FermenterRecipe>> FERMENTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("fermenting", () -> new AbstractRecipeSerializer<>(FermenterRecipe.class));
    public static final DeferredHolder<RecipeSerializer<?>, AbstractRecipeSerializer<DistillerRecipe>> DISTILLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("distillation", () -> new AbstractRecipeSerializer<>(DistillerRecipe.class));

    public static final DeferredHolder<RecipeSerializer<?>, AbstractRecipeSerializer<ChoppingRecipe>> CHOPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("chopping", () -> new AbstractRecipeSerializer<>(ChoppingRecipe.class));

    private static <T extends Recipe<?>> RecipeType<T> recipeType(String name) {
        return new RecipeType<T>() {
            public String toString() {
                return ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, name).toString();
            }
        };
    }
}
