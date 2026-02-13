package cn.mcmod.sakura.block.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod.sakura.block.machines.MapleSpileBlock;
import cn.mcmod.sakura.container.MapleCauldronContainer;
import cn.mcmod.sakura.fluid.FluidRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod_mmf.mmlib.block.entity.HeatableBlockEntity;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * BlockEntity for the Maple Cauldron block.
 * Ported from 1.12.2 TileEntityMapleCauldron.
 * Collects maple sap from spiles above and processes it into maple sugar
 * when heated. Has a fluid tank (5000 mB) and 1 output slot.
 */
public class MapleCauldronBlockEntity extends SyncedBlockEntity implements MenuProvider, HeatableBlockEntity {

    public static final int TANK_CAPACITY = 5000;

    private final ItemStackHandler inventory;
    private LazyOptional<IItemHandler> itemHandler;
    private LazyOptional<FluidTank> fluidTank;
    protected final ContainerData tileData;

    private int cookTime;
    private int mapleTime;

    public MapleCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MAPLE_CAULDRON.get(), pos, state);
        this.inventory = createHandler();
        this.itemHandler = LazyOptional.of(() -> inventory);
        this.fluidTank = LazyOptional.of(this::createFluidHandler);
        this.tileData = createIntArray();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, MapleCauldronBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        boolean changed = false;

        // Drawing maple sap: checks for a MapleSpile block above with a valid (non-exhausted) log
        // and slowly fills the tank with maple syrup fluid.
        changed |= blockEntity.tickDrawing(level);

        // Cooking - when heated and has enough fluid, produces maple sugar items
        changed |= blockEntity.tickCooking(level);

        if (changed) {
            blockEntity.inventoryChanged();
        }
    }

    private boolean canDraw(Level level) {
        BlockPos abovePos = worldPosition.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.getBlock() instanceof MapleSpileBlock) {
            return MapleSpileBlock.canWork(level, abovePos, aboveState);
        }
        return false;
    }

    private boolean tickDrawing(Level level) {
        boolean changed = false;

        if (canDraw(level)) {
            mapleTime += level.getRandom().nextInt(9) + 1;
        }

        if (mapleTime >= 20) {
            mapleTime = 0;
            FluidTank tank = fluidTank.orElse(null);
            if (tank != null && tank.getSpace() > 0) {
                tank.fill(new FluidStack(FluidRegistry.MAPLE_SYRUP.get(), 10), IFluidHandler.FluidAction.EXECUTE);
            }
            changed = true;
        }

        return changed;
    }

    private boolean isBurning(Level level) {
        FluidTank tank = fluidTank.orElse(null);
        if (tank == null) return false;
        FluidStack fluid = tank.getFluid();
        return !fluid.isEmpty()
                && fluid.getFluid() == FluidRegistry.MAPLE_SYRUP.get()
                && tank.getFluidAmount() >= 500
                && isHeated(level, worldPosition);
    }

    private boolean tickCooking(Level level) {
        boolean changed = false;
        FluidTank tank = fluidTank.orElse(null);
        if (tank == null) return false;

        boolean wasBurning = isBurning(level);

        ItemStack outputStack = inventory.getStackInSlot(0);
        if (wasBurning && (outputStack.isEmpty() || outputStack.getCount() + 8 <= outputStack.getMaxStackSize())) {
            cookTime += 1;
        }

        if (cookTime >= 1200) {
            cookTime = 0;
            // Produce 8x maple syrup items (matches 1.12.2: ItemLoader.MATERIAL meta 49 = maple sugar/syrup)
            ItemStack result = new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MAPLE_SYRUP).get(), 8);
            if (outputStack.isEmpty()) {
                inventory.setStackInSlot(0, result);
            } else {
                outputStack.grow(8);
            }
            tank.drain(500, IFluidHandler.FluidAction.EXECUTE);
            changed = true;
        }

        if (wasBurning != isBurning(level)) {
            changed = true;
        }

        return changed;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getMapleTime() {
        return this.mapleTime;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public LazyOptional<FluidTank> getFluidTank() {
        return fluidTank;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        drops.add(inventory.getStackInSlot(0));
        return drops;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved()) {
            if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
                return itemHandler.cast();
            }
            if (cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
                return this.fluidTank.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        mapleTime = compound.getInt("MapleTime");
        cookTime = compound.getInt("CookTime");
        fluidTank.ifPresent(fluid -> fluid.readFromNBT(compound.getCompound("Tank")));
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putInt("MapleTime", mapleTime);
        compound.putInt("CookTime", cookTime);
        CompoundTag tankTag = new CompoundTag();
        fluidTank.ifPresent(fluid -> compound.put("Tank", fluid.writeToNBT(tankTag)));
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandler.invalidate();
        fluidTank.invalidate();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
        fluidTank.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = LazyOptional.of(() -> inventory);
        fluidTank = LazyOptional.of(this::createFluidHandler);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false; // output only
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
                return !stack.getFluid().getFluidType().isLighterThanAir();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                case 0:
                    return MapleCauldronBlockEntity.this.mapleTime;
                case 1:
                    return MapleCauldronBlockEntity.this.cookTime;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    MapleCauldronBlockEntity.this.mapleTime = value;
                    break;
                case 1:
                    MapleCauldronBlockEntity.this.cookTime = value;
                    break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new MapleCauldronContainer(id, player, this, this.tileData);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sakura.maple_cauldron");
    }
}
