package cn.mcmod.sakura.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import cn.mcmod.sakura.container.BarrelOutputContainer;
import cn.mcmod.sakura.recipes.LiquidToItemRecipe;
import cn.mcmod.sakura.recipes.LiquidToItemRegistry;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * BlockEntity for the Barrel Output block.
 * Ported from 1.12.2 TileEntityFluidOut.
 * Stores fluid in a tank (10000 mB) and has 2 inventory slots:
 * slot 0 = input container item, slot 1 = filled output item.
 * Each tick it tries to drain fluid into container items.
 */
public class BarrelOutputBlockEntity extends SyncedBlockEntity implements MenuProvider {

    public static final int TANK_CAPACITY = 10000;
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private final ItemStackHandler inventory;
    private final FluidTank tank;

    public BarrelOutputBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BARREL_OUTPUT.get(), pos, state);
        this.inventory = createHandler();
        this.tank = createFluidHandler();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, BarrelOutputBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }
        blockEntity.drainToContainer();
    }

    /**
     * Attempts to drain fluid from the tank into a container item in the input slot,
     * placing the result in the output slot.
     * Uses LiquidToItemRegistry to find matching fluid + container -> filled item recipes.
     */
    private void drainToContainer() {
        ItemStack containerStack = inventory.getStackInSlot(INPUT_SLOT);
        if (containerStack.isEmpty()) {
            return;
        }

        FluidStack fluidInTank = tank.getFluid();
        if (fluidInTank.isEmpty()) {
            return;
        }

        LiquidToItemRecipe recipe = LiquidToItemRegistry.findRecipe(fluidInTank, containerStack);
        if (recipe == null) {
            return;
        }

        // Check if output slot can accept the result
        ItemStack result = recipe.getResultItem();
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
        if (!outputStack.isEmpty()) {
            if (!ItemStack.isSameItem(outputStack, result)) {
                return;
            }
            if (outputStack.getCount() + result.getCount() > outputStack.getMaxStackSize()) {
                return;
            }
        }

        // Drain the required fluid amount
        FluidStack drained = tank.drain(recipe.getRequiredFluid().getAmount(), FluidAction.EXECUTE);
        if (drained.isEmpty() || drained.getAmount() < recipe.getRequiredFluid().getAmount()) {
            // Not enough fluid; refund what we drained
            if (!drained.isEmpty()) {
                tank.fill(drained, FluidAction.EXECUTE);
            }
            return;
        }

        // Consume one container item from input slot
        containerStack.shrink(1);

        // Place or stack result in output slot
        if (outputStack.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, result.copy());
        } else {
            outputStack.grow(result.getCount());
        }

        inventoryChanged();
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public FluidTank getFluidTank() {
        return tank;
    }


    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 2; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        tank.readFromNBT(registries, compound.getCompound("Tank"));
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        CompoundTag tankTag = new CompoundTag();
        compound.put("Tank", tank.writeToNBT(registries, tankTag));
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot == INPUT_SLOT;
            }
        };
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(TANK_CAPACITY) {
            @Override
            public void onContentsChanged() {
                inventoryChanged();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                if (stack.getFluid().getFluidType().isLighterThanAir()) {
                    return false;
                }
                return stack.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new BarrelOutputContainer(id, player, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sakura.barrel_output");
    }
}
