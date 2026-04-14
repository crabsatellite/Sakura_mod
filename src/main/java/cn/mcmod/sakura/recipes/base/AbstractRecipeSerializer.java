package cn.mcmod.sakura.recipes.base;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Local replacement for cn.mcmod_mmf.mmlib.recipe.AbstractRecipeSerializer.
 * Uses GSON for serialization wrapped in Codec/StreamCodec for 1.21.1 compatibility.
 */
public class AbstractRecipeSerializer<T extends AbstractRecipe> implements RecipeSerializer<T> {
    private final Class<T> recipeClass;
    private final Gson gson;
    private final MapCodec<T> mapCodec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public AbstractRecipeSerializer(Class<T> recipeClass) {
        this.recipeClass = recipeClass;
        this.gson = createGson();
        this.mapCodec = createMapCodec();
        this.streamCodec = createStreamCodec();
    }

    private Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeHierarchyAdapter(Ingredient.class, new IngredientAdapter())
                .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
                .registerTypeHierarchyAdapter(FluidStack.class, new FluidStackAdapter())
                .registerTypeHierarchyAdapter(FluidIngredient.class, new FluidIngredientAdapter())
                .registerTypeHierarchyAdapter(ChanceResult.class, new ChanceResultAdapter())
                .registerTypeAdapter(new TypeToken<NonNullList<Ingredient>>(){}.getType(), new IngredientListAdapter())
                .registerTypeAdapter(new TypeToken<NonNullList<ItemStack>>(){}.getType(), new ItemStackListAdapter())
                .registerTypeAdapter(new TypeToken<NonNullList<ChanceResult>>(){}.getType(), new ChanceResultListAdapter())
                .create();
    }

    private MapCodec<T> createMapCodec() {
        return new MapCodec<T>() {
            @Override
            public <T1> RecordBuilder<T1> encode(T input, DynamicOps<T1> ops, RecordBuilder<T1> prefix) {
                try {
                    JsonObject json = gson.toJsonTree(input).getAsJsonObject();
                    for (var entry : json.entrySet()) {
                        prefix = prefix.add(entry.getKey(), JsonOps.INSTANCE.convertTo(ops, entry.getValue()));
                    }
                } catch (Exception e) {
                    // Log but don't crash
                }
                return prefix;
            }

            @Override
            public <T1> DataResult<T> decode(DynamicOps<T1> ops, MapLike<T1> input) {
                try {
                    JsonObject json = new JsonObject();
                    input.entries().forEach(pair -> {
                        String key = ops.getStringValue(pair.getFirst()).result().orElse("");
                        if (!key.isEmpty()) {
                            JsonElement value = convertToJson(ops, pair.getSecond());
                            json.add(key, value);
                        }
                    });
                    T recipe = gson.fromJson(json, recipeClass);
                    return DataResult.success(recipe);
                } catch (Exception e) {
                    return DataResult.error(() -> "Failed to deserialize " + recipeClass.getSimpleName() + ": " + e.getMessage());
                }
            }

            @Override
            public <T1> Stream<T1> keys(DynamicOps<T1> ops) {
                return Stream.empty();
            }
        };
    }

    private static <T1> JsonElement convertToJson(DynamicOps<T1> ops, T1 value) {
        if (ops instanceof JsonOps) {
            return (JsonElement) value;
        }
        return (JsonElement) ops.convertTo(JsonOps.INSTANCE, value);
    }

    private StreamCodec<RegistryFriendlyByteBuf, T> createStreamCodec() {
        return StreamCodec.of(
                (buf, recipe) -> {
                    String json = gson.toJson(recipe);
                    buf.writeUtf(json);
                },
                buf -> {
                    String json = buf.readUtf(32767);
                    return gson.fromJson(json, recipeClass);
                }
        );
    }

    @Override
    public MapCodec<T> codec() {
        return mapCodec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return streamCodec;
    }

    // JSON → recipe (for data gen compatibility)
    public T fromJson(JsonObject json) {
        return gson.fromJson(json, recipeClass);
    }

    public JsonObject toJson(T recipe) {
        return gson.toJsonTree(recipe).getAsJsonObject();
    }

    // ===== GSON Type Adapters =====

    private static class IngredientAdapter implements JsonSerializer<Ingredient>, JsonDeserializer<Ingredient> {
        @Override
        public JsonElement serialize(Ingredient src, Type typeOfSrc, JsonSerializationContext context) {
            // Encode using Ingredient's codec
            return Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, src).result().orElse(JsonNull.INSTANCE);
        }

        @Override
        public Ingredient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Ingredient.CODEC.decode(JsonOps.INSTANCE, json).result()
                    .map(com.mojang.datafixers.util.Pair::getFirst)
                    .orElse(Ingredient.EMPTY);
        }
    }

    private static class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
            if (src.isEmpty()) return JsonNull.INSTANCE;
            return ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, src).result().orElse(JsonNull.INSTANCE);
        }

        @Override
        public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) return ItemStack.EMPTY;
            return ItemStack.CODEC.decode(JsonOps.INSTANCE, json).result()
                    .map(com.mojang.datafixers.util.Pair::getFirst)
                    .orElse(ItemStack.EMPTY);
        }
    }

    private static class FluidStackAdapter implements JsonSerializer<FluidStack>, JsonDeserializer<FluidStack> {
        @Override
        public JsonElement serialize(FluidStack src, Type typeOfSrc, JsonSerializationContext context) {
            if (src.isEmpty()) return JsonNull.INSTANCE;
            return FluidStack.CODEC.encodeStart(JsonOps.INSTANCE, src).result().orElse(JsonNull.INSTANCE);
        }

        @Override
        public FluidStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) return FluidStack.EMPTY;
            return FluidStack.CODEC.decode(JsonOps.INSTANCE, json).result()
                    .map(com.mojang.datafixers.util.Pair::getFirst)
                    .orElse(FluidStack.EMPTY);
        }
    }

    private static class FluidIngredientAdapter implements JsonSerializer<FluidIngredient>, JsonDeserializer<FluidIngredient> {
        @Override
        public JsonElement serialize(FluidIngredient src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == FluidIngredient.EMPTY) return JsonNull.INSTANCE;
            return src.serialize();
        }

        @Override
        public FluidIngredient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) return FluidIngredient.EMPTY;
            return FluidIngredient.deserialize(json);
        }
    }

    private static class ChanceResultAdapter implements JsonSerializer<ChanceResult>, JsonDeserializer<ChanceResult> {
        @Override
        public JsonElement serialize(ChanceResult src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.add("item", ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, src.stack()).result().orElse(JsonNull.INSTANCE));
            json.addProperty("chance", src.chance());
            return json;
        }

        @Override
        public ChanceResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || !json.isJsonObject()) return ChanceResult.EMPTY;
            JsonObject obj = json.getAsJsonObject();
            ItemStack stack = ItemStack.CODEC.decode(JsonOps.INSTANCE, obj.get("item")).result()
                    .map(com.mojang.datafixers.util.Pair::getFirst)
                    .orElse(ItemStack.EMPTY);
            float chance = obj.has("chance") ? obj.get("chance").getAsFloat() : 1.0f;
            return new ChanceResult(stack, chance);
        }
    }

    private static class IngredientListAdapter implements JsonSerializer<NonNullList<Ingredient>>, JsonDeserializer<NonNullList<Ingredient>> {
        @Override
        public JsonElement serialize(NonNullList<Ingredient> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray arr = new JsonArray();
            for (Ingredient ing : src) {
                arr.add(context.serialize(ing));
            }
            return arr;
        }

        @Override
        public NonNullList<Ingredient> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            NonNullList<Ingredient> list = NonNullList.create();
            if (json.isJsonArray()) {
                for (JsonElement el : json.getAsJsonArray()) {
                    Ingredient ing = context.deserialize(el, Ingredient.class);
                    list.add(ing != null ? ing : Ingredient.EMPTY);
                }
            }
            return list;
        }
    }

    private static class ItemStackListAdapter implements JsonSerializer<NonNullList<ItemStack>>, JsonDeserializer<NonNullList<ItemStack>> {
        @Override
        public JsonElement serialize(NonNullList<ItemStack> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray arr = new JsonArray();
            for (ItemStack stack : src) {
                arr.add(context.serialize(stack));
            }
            return arr;
        }

        @Override
        public NonNullList<ItemStack> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            NonNullList<ItemStack> list = NonNullList.create();
            if (json.isJsonArray()) {
                for (JsonElement el : json.getAsJsonArray()) {
                    ItemStack stack = context.deserialize(el, ItemStack.class);
                    list.add(stack != null ? stack : ItemStack.EMPTY);
                }
            }
            return list;
        }
    }

    private static class ChanceResultListAdapter implements JsonSerializer<NonNullList<ChanceResult>>, JsonDeserializer<NonNullList<ChanceResult>> {
        @Override
        public JsonElement serialize(NonNullList<ChanceResult> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray arr = new JsonArray();
            for (ChanceResult cr : src) {
                arr.add(context.serialize(cr));
            }
            return arr;
        }

        @Override
        public NonNullList<ChanceResult> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            NonNullList<ChanceResult> list = NonNullList.create();
            if (json.isJsonArray()) {
                for (JsonElement el : json.getAsJsonArray()) {
                    ChanceResult cr = context.deserialize(el, ChanceResult.class);
                    list.add(cr != null ? cr : ChanceResult.EMPTY);
                }
            }
            return list;
        }
    }
}
