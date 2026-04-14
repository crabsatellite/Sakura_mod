package cn.mcmod.sakura;

import cn.mcmod.sakura.block.entity.*;
import cn.mcmod.sakura.inventory.CookingPotItemHandler;
import cn.mcmod.sakura.inventory.FermenterItemHandler;
import cn.mcmod.sakura.inventory.StoneMortarItemHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = SakuraMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SakuraCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Sided item handlers (input from top, output from sides)
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.COOKING_POT.get(),
                (be, direction) -> new CookingPotItemHandler(be.getInventory(), direction));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.CAMPFIRE_POT.get(),
                (be, direction) -> new CookingPotItemHandler(be.getInventory(), direction));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.FERMENTER.get(),
                (be, direction) -> new FermenterItemHandler(be.getInventory(), direction));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.DISTILLER.get(),
                (be, direction) -> new FermenterItemHandler(be.getInventory(), direction));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.STONE_MORTAR.get(),
                (be, direction) -> new StoneMortarItemHandler(be.getInventory(), direction));

        // Simple item handlers (no sided logic)
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.OBON.get(),
                (be, direction) -> be.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.CHOPPING_BOARD.get(),
                (be, direction) -> be.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.CAMPFIRE.get(),
                (be, direction) -> be.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.MAPLE_CAULDRON.get(),
                (be, direction) -> be.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.BARREL_OUTPUT.get(),
                (be, direction) -> be.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.STRAW_WEB.get(),
                (be, direction) -> be.getInventory());

        // Fluid handlers
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.COOKING_POT.get(),
                (be, direction) -> be.getFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.CAMPFIRE_POT.get(),
                (be, direction) -> be.getFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.FERMENTER.get(),
                (be, direction) -> be.getInputFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.DISTILLER.get(),
                (be, direction) -> be.getInputFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.MAPLE_CAULDRON.get(),
                (be, direction) -> be.getFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.BARREL_OUTPUT.get(),
                (be, direction) -> be.getFluidTank());
    }
}
