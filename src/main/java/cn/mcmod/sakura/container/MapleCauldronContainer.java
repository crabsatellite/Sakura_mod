package cn.mcmod.sakura.container;

import java.util.Objects;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.entity.MapleCauldronBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MapleCauldronContainer extends AbstractContainerMenu {

    public final MapleCauldronBlockEntity tileEntity;
    public final ItemStackHandler inventory;
    private final ContainerData containerData;
    private final ContainerLevelAccess canInteractWithCallable;

    public MapleCauldronContainer(final int windowId, final Inventory playerInventory,
            final MapleCauldronBlockEntity tileEntity, ContainerData dataIn) {
        super(ContainerRegistry.MAPLE_CAULDRON.get(), windowId);
        this.tileEntity = tileEntity;
        this.inventory = tileEntity.getInventory();
        this.containerData = dataIn;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        // Slot 0: output slot (read-only)
        this.addSlot(new SlotItemHandler(inventory, 0, 114, 36) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Main Player Inventory
        int startX = 8;
        int startPlayerInvY = 84;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * 18),
                        startPlayerInvY + (row * 18)));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * 18), 142));
        }

        this.addDataSlots(dataIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // 0: Container inventory (output)
        // 1-27: Player inventory
        // 28-36: Hotbar

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(itemStack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= 1) {
                if (index >= 1 && index < 28) {
                    if (!this.moveItemStackTo(itemStack1, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 28 && index < 37 && !this.moveItemStackTo(itemStack1, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemStack1);
        }

        return itemStack;
    }

    private static MapleCauldronBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof MapleCauldronBlockEntity) {
            return (MapleCauldronBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public MapleCauldronContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(2));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, BlockRegistry.MAPLE_CAULDRON.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getProgressScaled(int pixels) {
        int i = this.containerData.get(1);
        return i != 0 ? i * pixels / 1200 : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        // In the 1.12.2 version, isBurning() checked if there was enough fluid (>=500)
        // and a heat source. We expose mapleTime (field 0) as an indicator.
        return this.containerData.get(0) > 0;
    }
}
