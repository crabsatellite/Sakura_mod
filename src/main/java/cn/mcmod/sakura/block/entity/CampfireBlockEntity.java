package cn.mcmod.sakura.block.entity;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod.sakura.block.SakuraCampfireBlock;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * BlockEntity for the Sakura Campfire block.
 * Ported from 1.12.2 TileEntityCampfire.
 * Stores an item that can be cooked when the campfire is burning.
 */
public class CampfireBlockEntity extends SyncedBlockEntity {

    private final ItemStackHandler inventory;
    private LazyOptional<IItemHandler> itemHandler;

    private int burnTime;
    private int cookTime;

    public CampfireBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CAMPFIRE.get(), pos, state);
        this.inventory = createHandler();
        this.itemHandler = LazyOptional.of(() -> inventory);
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
                    Optional<SmeltingRecipe> recipe = level.getRecipeManager()
                            .getRecipeFor(RecipeType.SMELTING, container, level);
                    if (recipe.isPresent()) {
                        ItemStack result = recipe.get().assemble(container, level.registryAccess());
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

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        drops.add(inventory.getStackInSlot(0));
        return drops;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved() && cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        burnTime = compound.getInt("BurnTime");
        cookTime = compound.getInt("CookTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("CookTime", cookTime);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandler.invalidate();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = LazyOptional.of(() -> inventory);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
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
