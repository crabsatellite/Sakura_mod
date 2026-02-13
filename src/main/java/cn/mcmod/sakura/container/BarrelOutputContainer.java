package cn.mcmod.sakura.container;

import java.util.Objects;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.entity.BarrelOutputBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BarrelOutputContainer extends AbstractContainerMenu {

    public final BarrelOutputBlockEntity tileEntity;
    public final ItemStackHandler inventory;
    private final ContainerLevelAccess canInteractWithCallable;

    public BarrelOutputContainer(final int windowId, final Inventory playerInventory,
            final BarrelOutputBlockEntity tileEntity) {
        super(ContainerRegistry.BARREL_OUTPUT.get(), windowId);
        this.tileEntity = tileEntity;
        this.inventory = tileEntity.getInventory();
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        // Slot 0: input container item
        this.addSlot(new SlotItemHandler(inventory, 0, 58, 26));

        // Slot 1: output filled item (read-only)
        this.addSlot(new SlotItemHandler(inventory, 1, 105, 26) {
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
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // 0-1: Container inventory
        // 2-28: Player inventory
        // 29-37: Hotbar

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (index >= 0 && index <= 1) {
                if (!this.moveItemStackTo(itemStack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= 2) {
                if (!this.moveItemStackTo(itemStack1, 0, 1, false)) {
                    if (index >= 2 && index < 29) {
                        if (!this.moveItemStackTo(itemStack1, 29, 38, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemStack1, 2, 29, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack1, 2, 38, false)) {
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

    private static BarrelOutputBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof BarrelOutputBlockEntity) {
            return (BarrelOutputBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public BarrelOutputContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, BlockRegistry.BARREL_OUT.get());
    }
}
