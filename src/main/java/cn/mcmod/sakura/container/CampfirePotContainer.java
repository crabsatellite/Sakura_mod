package cn.mcmod.sakura.container;

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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.entity.CampfirePotBlockEntity;

import java.util.Objects;

public class CampfirePotContainer extends AbstractContainerMenu {

    public final CampfirePotBlockEntity tileEntity;
    public final ItemStackHandler inventory;
    private final ContainerData containerData;
    private final ContainerLevelAccess canInteractWithCallable;

    public CampfirePotContainer(final int windowId, final Inventory playerInventory,
            final CampfirePotBlockEntity tileEntity, ContainerData dataIn) {
        super(ContainerRegistry.CAMPFIRE_POT.get(), windowId);
        this.tileEntity = tileEntity;
        this.inventory = tileEntity.getInventory();
        this.containerData = dataIn;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        // Slot 0: seasoning/water input (top-left)
        this.addSlot(new SlotItemHandler(inventory, 0, 45, 19));

        // Slots 1-4: ingredient row 1
        for (int k = 1; k < 5; ++k) {
            this.addSlot(new SlotItemHandler(inventory, k, 18 + (k - 1) * 18, 37));
        }

        // Slots 5-8: ingredient row 2
        for (int l = 5; l < 9; ++l) {
            this.addSlot(new SlotItemHandler(inventory, l, 18 + (l - 5) * 18, 55));
        }

        // Slot 9: output (read-only)
        this.addSlot(new SlotItemHandler(inventory, 9, 130, 46) {
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
        // 0-9: Container inventory
        // 10-36: Player inventory
        // 37-45: Hotbar

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (index >= 0 && index <= 9) {
                if (!this.moveItemStackTo(itemStack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= 10) {
                if (!this.moveItemStackTo(itemStack1, 0, 9, false)) {
                    if (index >= 10 && index < 37) {
                        if (!this.moveItemStackTo(itemStack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 37 && index < 46 && !this.moveItemStackTo(itemStack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack1, 10, 46, false)) {
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

    private static CampfirePotBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof CampfirePotBlockEntity) {
            return (CampfirePotBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public CampfirePotContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(3));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, BlockRegistry.CAMPFIRE_POT_IDLE.get())
                || stillValid(canInteractWithCallable, playerIn, BlockRegistry.CAMPFIRE_POT_LIT.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.containerData.get(1);
        int j = this.containerData.get(2);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.containerData.get(0) > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnTimeScaled(int pixels) {
        int burnTime = this.containerData.get(0);
        return burnTime > 0 ? burnTime * pixels / 200 : 0;
    }
}
