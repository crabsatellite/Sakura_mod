package cn.mcmod.sakura.fluid;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;

public class FluidBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, SakuraMod.MODID);
    public static final DeferredHolder<Block, LiquidBlock> FOOD_OIL_BLOCK = BLOCKS.register("food_oil", () ->
        new LiquidBlock(FluidRegistry.FOOD_OIL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static final DeferredHolder<Block, LiquidBlock> DOBUROKU_BLOCK = BLOCKS.register("doburoku", () ->
        new LiquidBlock(FluidRegistry.DOBUROKU.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> SAKE_BLOCK = BLOCKS.register("sake", () ->
        new LiquidBlock(FluidRegistry.SAKE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> SHOUCHU_BLOCK = BLOCKS.register("shouchu", () ->
        new LiquidBlock(FluidRegistry.SHOUCHU.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    
    public static final DeferredHolder<Block, LiquidBlock> BEER_BLOCK = BLOCKS.register("beer", () ->
        new LiquidBlock(FluidRegistry.BEER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> WHISKEY_BLOCK = BLOCKS.register("whiskey", () ->
        new LiquidBlock(FluidRegistry.WHISKEY.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> RED_WINE_BLOCK = BLOCKS.register("red_wine", () ->
        new LiquidBlock(FluidRegistry.RED_WINE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> WHITE_WINE_BLOCK = BLOCKS.register("white_wine", () ->
        new LiquidBlock(FluidRegistry.WHITE_WINE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> BRANDY_BLOCK = BLOCKS.register("brandy", () ->
        new LiquidBlock(FluidRegistry.BRANDY.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> RUM_BLOCK = BLOCKS.register("rum", () ->
        new LiquidBlock(FluidRegistry.RUM.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> CHAMPAGNE_BLOCK = BLOCKS.register("champagne", () ->
        new LiquidBlock(FluidRegistry.CHAMPAGNE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> VODKA_BLOCK = BLOCKS.register("vodka", () ->
        new LiquidBlock(FluidRegistry.VODKA.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> LIQUEUR_BLOCK = BLOCKS.register("liqueur", () ->
        new LiquidBlock(FluidRegistry.LIQUEUR.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> COCOA_LIQUEUR_BLOCK = BLOCKS.register("cocoa_liqueur", () ->
        new LiquidBlock(FluidRegistry.COCOA_LIQUEUR.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> GIN_BLOCK = BLOCKS.register("gin", () ->
        new LiquidBlock(FluidRegistry.GIN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> TEQUILA_BLOCK = BLOCKS.register("tequila", () ->
        new LiquidBlock(FluidRegistry.TEQUILA.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> GRAPE_FLUID_BLOCK = BLOCKS.register("grape_fluid", () ->
        new LiquidBlock(FluidRegistry.GRAPE_FLUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> GREEN_GRAPE_FLUID_BLOCK = BLOCKS.register("green_grape_fluid", () ->
        new LiquidBlock(FluidRegistry.GREEN_GRAPE_FLUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> YEAST_LIQUID_BLOCK = BLOCKS.register("yeast_liquid", () ->
        new LiquidBlock(FluidRegistry.YEAST_LIQUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> MAPLE_SYRUP_BLOCK = BLOCKS.register("maple_syrup", () ->
        new LiquidBlock(FluidRegistry.MAPLE_SYRUP.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, LiquidBlock> HOT_SPRING_WATER_BLOCK = BLOCKS.register("hot_spring_water_liquid", () ->
        new LiquidBlock(FluidRegistry.HOT_SPRING_WATER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

}
