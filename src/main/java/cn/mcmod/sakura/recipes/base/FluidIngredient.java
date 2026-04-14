package cn.mcmod.sakura.recipes.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Local replacement for cn.mcmod_mmf.mmlib.fluid.FluidIngredient
 * which was removed from MMLib 1.21.1.
 */
public abstract class FluidIngredient implements Predicate<FluidStack> {
    public static final FluidIngredient EMPTY = new FluidIngredient() {
        @Override
        protected boolean testInternal(FluidStack fluidStack) { return false; }
        @Override
        protected void readInternal(JsonObject json) {}
        @Override
        protected void writeInternal(JsonObject json) {}
        @Override
        protected List<FluidStack> determineMatchingFluidStacks() { return Collections.emptyList(); }
        @Override
        public int getRequiredAmount() { return 0; }
    };

    public List<FluidStack> matchingFluidStacks;
    protected int amountRequired;

    public static FluidIngredient fromTag(TagKey<Fluid> tag, int amount) {
        return new TagFluidIngredient(tag, amount);
    }

    public static FluidIngredient fromFluid(Fluid fluid, int amount) {
        return new SingleFluidIngredient(fluid, amount);
    }

    public static FluidIngredient fromFluidStack(FluidStack stack) {
        return new SingleFluidIngredient(stack.getFluid(), stack.getAmount());
    }

    protected abstract boolean testInternal(FluidStack fluidStack);
    protected abstract void readInternal(JsonObject json);
    protected abstract void writeInternal(JsonObject json);
    protected abstract List<FluidStack> determineMatchingFluidStacks();

    public int getRequiredAmount() {
        return amountRequired;
    }

    public List<FluidStack> getMatchingFluidStacks() {
        if (matchingFluidStacks == null) {
            matchingFluidStacks = determineMatchingFluidStacks();
        }
        return matchingFluidStacks;
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        if (this == EMPTY) return false;
        return testInternal(fluidStack);
    }

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        writeInternal(json);
        json.addProperty("amount", amountRequired);
        return json;
    }

    public static boolean isFluidIngredient(JsonElement json) {
        if (json == null || !json.isJsonObject()) return false;
        JsonObject obj = json.getAsJsonObject();
        return obj.has("fluid") || obj.has("tag");
    }

    public static FluidIngredient deserialize(JsonElement json) {
        if (json == null || !json.isJsonObject()) return EMPTY;
        JsonObject obj = json.getAsJsonObject();
        int amount = obj.has("amount") ? obj.get("amount").getAsInt() : 1000;
        if (obj.has("tag")) {
            String tagName = obj.get("tag").getAsString();
            net.minecraft.resources.ResourceLocation tagRL = net.minecraft.resources.ResourceLocation.parse(tagName);
            TagKey<Fluid> tagKey = TagKey.create(net.minecraft.core.registries.Registries.FLUID, tagRL);
            return fromTag(tagKey, amount);
        }
        if (obj.has("fluid")) {
            String fluidName = obj.get("fluid").getAsString();
            net.minecraft.resources.ResourceLocation rl = net.minecraft.resources.ResourceLocation.parse(fluidName);
            Fluid fluid = net.minecraft.core.registries.BuiltInRegistries.FLUID.get(rl);
            return fromFluid(fluid, amount);
        }
        return EMPTY;
    }

    private static class SingleFluidIngredient extends FluidIngredient {
        private final Fluid fluid;

        SingleFluidIngredient(Fluid fluid, int amount) {
            this.fluid = fluid;
            this.amountRequired = amount;
        }

        @Override
        protected boolean testInternal(FluidStack fluidStack) {
            return fluidStack.getFluid().isSame(fluid) && fluidStack.getAmount() >= amountRequired;
        }

        @Override
        protected void readInternal(JsonObject json) {}

        @Override
        protected void writeInternal(JsonObject json) {
            json.addProperty("fluid", net.minecraft.core.registries.BuiltInRegistries.FLUID.getKey(fluid).toString());
        }

        @Override
        protected List<FluidStack> determineMatchingFluidStacks() {
            return Collections.singletonList(new FluidStack(fluid, amountRequired));
        }
    }

    private static class TagFluidIngredient extends FluidIngredient {
        private final TagKey<Fluid> tag;

        TagFluidIngredient(TagKey<Fluid> tag, int amount) {
            this.tag = tag;
            this.amountRequired = amount;
        }

        @Override
        protected boolean testInternal(FluidStack fluidStack) {
            return fluidStack.getFluid().builtInRegistryHolder().is(tag)
                    && fluidStack.getAmount() >= amountRequired;
        }

        @Override
        protected void readInternal(JsonObject json) {}

        @Override
        protected void writeInternal(JsonObject json) {
            json.addProperty("tag", tag.location().toString());
        }

        @Override
        protected List<FluidStack> determineMatchingFluidStacks() {
            List<FluidStack> stacks = new java.util.ArrayList<>();
            net.minecraft.core.registries.BuiltInRegistries.FLUID.getTag(tag).ifPresent(holders -> {
                holders.forEach(holder -> stacks.add(new FluidStack(holder.value(), amountRequired)));
            });
            return stacks;
        }
    }
}
