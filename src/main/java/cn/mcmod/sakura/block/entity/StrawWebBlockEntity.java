package cn.mcmod.sakura.block.entity;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * BlockEntity for the Straw Web (drying rack).
 * Ported from 1.12.2 TileEntityWeb.
 * Dries items over time based on weather and biome conditions.
 * Items placed on the rack will convert to their dried counterpart after enough ticks.
 */
public class StrawWebBlockEntity extends SyncedBlockEntity {

    /** Maximum cook time in ticks before the item finishes drying. */
    private static final int MAX_COOK_TIME = 32000;

    private int cookTime;
    private final ItemStackHandler inventory;

    /**
     * Static map of drying recipes: input item -> output item.
     * Ported from 1.12.2 WebRecipe registrations.
     */
    private static Map<Item, Item> DRYING_RECIPES;

    private static Map<Item, Item> getDryingRecipes() {
        if (DRYING_RECIPES == null) {
            ImmutableMap.Builder<Item, Item> builder = ImmutableMap.builder();
            // Ported from 1.12.2 WebRecipe registrations.
            // FOODSET(143) -> FOODSET(144) : boiled bonito -> smoked bonito
            builder.put(
                    FoodRegistry.FOODSET.get(SakuraFoodSet.BOILED_BONITO).get(),
                    FoodRegistry.FOODSET.get(SakuraFoodSet.SMOKED_BONITO).get());
            // SEAWEED_RAW -> MATERIAL(34) : raw seaweed -> dried seaweed (SEAWEED material)
            builder.put(
                    FoodRegistry.FOODSET.get(SakuraFoodSet.SEAWEED_RAW).get(),
                    ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SEAWEED).get());
            // FOODSET(162) -> FOODSET(163) : brown rice cooked -> dried brown rice
            builder.put(
                    FoodRegistry.FOODSET.get(SakuraFoodSet.BROWN_RICE_COOKED).get(),
                    FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BROWN_RICE).get());
            // FOODSET(7) -> FOODSET(164) : rice cooked -> dried rice
            builder.put(
                    FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED).get(),
                    FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_RICE).get());
            // MATERIAL(63) -> MATERIAL(64) : imogara item -> dried imogara
            builder.put(
                    ItemRegistry.MATERIALS.get(SakuraNormalItemSet.IMOGARA).get(),
                    ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DRIED_IMOGARA).get());
            // smoked bonito -> dried bonito (additional useful drying recipe)
            builder.put(
                    FoodRegistry.FOODSET.get(SakuraFoodSet.SMOKED_BONITO).get(),
                    FoodRegistry.FOODSET.get(SakuraFoodSet.DRIED_BONITO).get());
            DRYING_RECIPES = builder.build();
        }
        return DRYING_RECIPES;
    }

    public StrawWebBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.STRAW_WEB.get(), pos, state);
        this.inventory = createHandler();
    }

    /**
     * Server-side tick method. Called every tick by the block's ticker.
     * Advances the drying timer based on biome/weather conditions.
     */
    public static void workingTick(Level level, BlockPos pos, BlockState state, StrawWebBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        ItemStack input = blockEntity.inventory.getStackInSlot(0);
        if (!input.isEmpty() && isValidRecipe(input)) {
            float rate = calcAdaptation(level, pos);
            blockEntity.cookTime += (int) rate;

            if (blockEntity.cookTime >= MAX_COOK_TIME) {
                blockEntity.cookTime = 0;
                Item resultItem = getDryingRecipes().get(input.getItem());
                if (resultItem != null) {
                    ItemStack result = new ItemStack(resultItem, input.getCount());
                    blockEntity.inventory.setStackInSlot(0, result);
                }
                blockEntity.inventoryChanged();
            }
        } else {
            if (blockEntity.cookTime != 0) {
                blockEntity.cookTime = 0;
            }
        }
    }

    /**
     * Checks whether the given item has a drying recipe.
     */
    public static boolean isValidRecipe(ItemStack stack) {
        return getDryingRecipes().containsKey(stack.getItem());
    }

    /**
     * Calculates the drying speed multiplier based on biome conditions.
     * Factors: sun exposure, rain, day/night, humidity, temperature.
     * Returns 0 if the block cannot see the sky or it is raining.
     */
    private static float calcAdaptation(Level level, BlockPos pos) {
        Biome biome = level.getBiome(pos).value();
        boolean isUnderTheSun = level.canSeeSky(pos);
        boolean isRaining = level.isRaining();
        boolean isDaytime = level.getDayTime() % 24000L < 12000L;
        // Humidity not available in 1.20.1 Biome API - using simplified logic

        if (!isUnderTheSun || isRaining) {
            return 0.0F;
        }

        float rate = isDaytime ? 2.0F : 1.0F;

        // Biome temperature factor (ported from 1.12.2)
        float temperature = biome.getBaseTemperature();
        if (temperature < 0.0F) {
            rate *= 1.0F;
        } else if (temperature < 0.6F) {
            rate *= 1.5F;
        } else if (temperature < 1.0F) {
            rate *= 2.0F;
        } else {
            rate *= 4.0F;
        }

        return rate;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStack getStoredItem() {
        return this.inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return this.inventory.getStackInSlot(0).isEmpty();
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            ItemStack toPlace = itemStack.copy();
            toPlace.setCount(Math.min(itemStack.getCount(), inventory.getSlotLimit(0)));
            inventory.setStackInSlot(0, toPlace);
            itemStack.shrink(toPlace.getCount());
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack item = getStoredItem().copy();
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            cookTime = 0;
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        cookTime = compound.getInt("CookTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        compound.putInt("CookTime", cookTime);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return isValidRecipe(stack);
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }
}
