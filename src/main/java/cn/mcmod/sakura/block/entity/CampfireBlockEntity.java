package cn.mcmod.sakura.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import cn.mcmod.sakura.block.SakuraCampfireBlock;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * BlockEntity for the Sakura Campfire block.
 * Ported from 1.12.2 TileEntityCampfire.
 * Stores an item that can be cooked when the campfire is burning.
 */
public class CampfireBlockEntity extends SyncedBlockEntity {

    private final ItemStackHandler inventory;

    private int burnTime;
    private int cookTime;

    public CampfireBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CAMPFIRE.get(), pos, state);
        this.inventory = createHandler();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        boolean wasBurning = blockEntity.isBurning();
        boolean changed = false;

        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
        }

        if (blockEntity.isBurning()) {
            ItemStack cookStack = blockEntity.getItemBurning();
            if (!cookStack.isEmpty()) {
                ++blockEntity.cookTime;
                if (blockEntity.cookTime >= 700) {
                    // Cooking complete -- look up the vanilla smelting recipe for this item
                    SimpleContainer container = new SimpleContainer(cookStack.copy());
                    SingleRecipeInput recipeInput = new SingleRecipeInput(cookStack.copy());
                    Optional<RecipeHolder<SmeltingRecipe>> recipe = level.getRecipeManager()
                            .getRecipeFor(RecipeType.SMELTING, recipeInput, level);
                    if (recipe.isPresent()) {
                        ItemStack result = recipe.get().value().assemble(recipeInput, level.registryAccess());
                        result.setCount(cookStack.getCount());
                        blockEntity.inventory.setStackInSlot(0, result);
                    }
                    // If no recipe found, item stays as-is (not consumed)
                    blockEntity.cookTime = 0;
                    changed = true;
                }
            } else {
                blockEntity.cookTime = 0;
            }
        }

        // Check if burning state changed and update LIT blockstate property
        boolean isNowBurning = blockEntity.isBurning();
        if (wasBurning != isNowBurning) {
            changed = true;
            SakuraCampfireBlock.setLitState(isNowBurning, level, pos, state);
        }

        if (changed) {
            blockEntity.inventoryChanged();
        }
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public void setBurnTime(int tick) {
        this.burnTime = tick;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public ItemStack getItemBurning() {
        return this.inventory.getStackInSlot(0);
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    /** Returns true if the item's smelting result is food (matches 1.12.2 behavior). */
    public boolean isCookableFood(ItemStack stack) {
        if (!this.hasLevel()) return false;
        Level world = this.getLevel();
        SingleRecipeInput recipeInput = new SingleRecipeInput(stack);
        Optional<RecipeHolder<SmeltingRecipe>> recipe = world.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, recipeInput, world);
        if (recipe.isEmpty()) return false;
        ItemStack result = recipe.get().value().getResultItem(world.registryAccess());
        return result.getFoodProperties(null) != null;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        drops.add(inventory.getStackInSlot(0));
        return drops;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        burnTime = compound.getInt("BurnTime");
        cookTime = compound.getInt("CookTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        compound.putInt("BurnTime", burnTime);
        compound.putInt("CookTime", cookTime);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return CampfireBlockEntity.this.isCookableFood(stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 16;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }
}
