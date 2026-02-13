package cn.mcmod.sakura.fluid;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidRegistry {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, SakuraMod.MODID);

    public static final RegistryObject<FlowingFluid> FOOD_OIL = FLUIDS.register("food_oil", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.FOOD_OIL_PROP));
    public static final RegistryObject<FlowingFluid> FOOD_OIL_FLOWING = FLUIDS.register("food_oil_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.FOOD_OIL_PROP));
    
    public static final RegistryObject<FlowingFluid> DOBUROKU = FLUIDS.register("doburoku", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.DOBUROKU_PROP));
    public static final RegistryObject<FlowingFluid> DOBUROKU_FLOWING = FLUIDS.register("doburoku_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.DOBUROKU_PROP));
    
    public static final RegistryObject<FlowingFluid> SAKE = FLUIDS.register("sake", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.SAKE_PROP));
    public static final RegistryObject<FlowingFluid> SAKE_FLOWING = FLUIDS.register("sake_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.SAKE_PROP));
    
    public static final RegistryObject<FlowingFluid> SHOUCHU = FLUIDS.register("shouchu", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.SHOUCHU_PROP));
    public static final RegistryObject<FlowingFluid> SHOUCHU_FLOWING = FLUIDS.register("shouchu_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.SHOUCHU_PROP));
    
    public static final RegistryObject<FlowingFluid> BEER = FLUIDS.register("beer", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.BEER_PROP));
    public static final RegistryObject<FlowingFluid> BEER_FLOWING = FLUIDS.register("beer_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.BEER_PROP));
    
    public static final RegistryObject<FlowingFluid> WHISKEY = FLUIDS.register("whiskey", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.WHISKEY_PROP));
    public static final RegistryObject<FlowingFluid> WHISKEY_FLOWING = FLUIDS.register("whiskey_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.WHISKEY_PROP));
    
    public static final RegistryObject<FlowingFluid> RUM = FLUIDS.register("rum", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.RUM_PROP));
    public static final RegistryObject<FlowingFluid> RUM_FLOWING = FLUIDS.register("rum_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.RUM_PROP));
    
    public static final RegistryObject<FlowingFluid> RED_WINE = FLUIDS.register("red_wine", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.RED_WINE_PROP));
    public static final RegistryObject<FlowingFluid> RED_WINE_FLOWING = FLUIDS.register("red_wine_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.RED_WINE_PROP));
    
    public static final RegistryObject<FlowingFluid> WHITE_WINE = FLUIDS.register("white_wine", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.WHITE_WINE_PROP));
    public static final RegistryObject<FlowingFluid> WHITE_WINE_FLOWING = FLUIDS.register("white_wine_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.WHITE_WINE_PROP));
    
    public static final RegistryObject<FlowingFluid> CHAMPAGNE = FLUIDS.register("champagne", 
            () -> new ForgeFlowingFluid.Source(FluidRegistry.CHAMPAGNE_PROP));
    public static final RegistryObject<FlowingFluid> CHAMPAGNE_FLOWING = FLUIDS.register("champagne_flowing", 
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.CHAMPAGNE_PROP));
    
    public static final RegistryObject<FlowingFluid> BRANDY = FLUIDS.register("brandy",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.BRANDY_PROP));
    public static final RegistryObject<FlowingFluid> BRANDY_FLOWING = FLUIDS.register("brandy_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.BRANDY_PROP));

    public static final RegistryObject<FlowingFluid> VODKA = FLUIDS.register("vodka",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.VODKA_PROP));
    public static final RegistryObject<FlowingFluid> VODKA_FLOWING = FLUIDS.register("vodka_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.VODKA_PROP));

    public static final RegistryObject<FlowingFluid> LIQUEUR = FLUIDS.register("liqueur",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.LIQUEUR_PROP));
    public static final RegistryObject<FlowingFluid> LIQUEUR_FLOWING = FLUIDS.register("liqueur_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.LIQUEUR_PROP));

    public static final RegistryObject<FlowingFluid> COCOA_LIQUEUR = FLUIDS.register("cocoa_liqueur",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.COCOA_LIQUEUR_PROP));
    public static final RegistryObject<FlowingFluid> COCOA_LIQUEUR_FLOWING = FLUIDS.register("cocoa_liqueur_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.COCOA_LIQUEUR_PROP));

    public static final RegistryObject<FlowingFluid> GIN = FLUIDS.register("gin",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.GIN_PROP));
    public static final RegistryObject<FlowingFluid> GIN_FLOWING = FLUIDS.register("gin_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.GIN_PROP));

    public static final RegistryObject<FlowingFluid> TEQUILA = FLUIDS.register("tequila",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.TEQUILA_PROP));
    public static final RegistryObject<FlowingFluid> TEQUILA_FLOWING = FLUIDS.register("tequila_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.TEQUILA_PROP));

    public static final RegistryObject<FlowingFluid> GRAPE_FLUID = FLUIDS.register("grape_fluid",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.GRAPE_FLUID_PROP));
    public static final RegistryObject<FlowingFluid> GRAPE_FLUID_FLOWING = FLUIDS.register("grape_fluid_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.GRAPE_FLUID_PROP));

    public static final RegistryObject<FlowingFluid> GREEN_GRAPE_FLUID = FLUIDS.register("green_grape_fluid",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.GREEN_GRAPE_FLUID_PROP));
    public static final RegistryObject<FlowingFluid> GREEN_GRAPE_FLUID_FLOWING = FLUIDS.register("green_grape_fluid_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.GREEN_GRAPE_FLUID_PROP));

    public static final RegistryObject<FlowingFluid> YEAST_LIQUID = FLUIDS.register("yeast_liquid",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.YEAST_LIQUID_PROP));
    public static final RegistryObject<FlowingFluid> YEAST_LIQUID_FLOWING = FLUIDS.register("yeast_liquid_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.YEAST_LIQUID_PROP));

    public static final RegistryObject<FlowingFluid> MAPLE_SYRUP = FLUIDS.register("maple_syrup",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.MAPLE_SYRUP_PROP));
    public static final RegistryObject<FlowingFluid> MAPLE_SYRUP_FLOWING = FLUIDS.register("maple_syrup_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.MAPLE_SYRUP_PROP));

    public static final RegistryObject<FlowingFluid> HOT_SPRING_WATER = FLUIDS.register("hot_spring_water",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.HOT_SPRING_WATER_PROP));
    public static final RegistryObject<FlowingFluid> HOT_SPRING_WATER_FLOWING = FLUIDS.register("hot_spring_water_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.HOT_SPRING_WATER_PROP));

    private static final ForgeFlowingFluid.Properties FOOD_OIL_PROP = 
            createProp(FOOD_OIL, FOOD_OIL_FLOWING, FluidTypeRegistry.FOOD_OIL, FluidBlockRegistry.FOOD_OIL_BLOCK,BucketItemRegistry.FOOD_OIL_BUCKET);

    private static final ForgeFlowingFluid.Properties DOBUROKU_PROP = 
            createProp(DOBUROKU, DOBUROKU_FLOWING, FluidTypeRegistry.DOBUROKU, FluidBlockRegistry.DOBUROKU_BLOCK,BucketItemRegistry.DOBUROKU_BUCKET);
    
    private static final ForgeFlowingFluid.Properties SAKE_PROP = 
            createProp(SAKE, SAKE_FLOWING, FluidTypeRegistry.SAKE, FluidBlockRegistry.SAKE_BLOCK,BucketItemRegistry.SAKE_BUCKET);
    
    private static final ForgeFlowingFluid.Properties SHOUCHU_PROP = 
            createProp(SHOUCHU, SHOUCHU_FLOWING, FluidTypeRegistry.SHOUCHU, FluidBlockRegistry.SHOUCHU_BLOCK,BucketItemRegistry.SHOUCHU_BUCKET);
    
    private static final ForgeFlowingFluid.Properties BEER_PROP = 
            createProp(BEER, BEER_FLOWING, FluidTypeRegistry.BEER, FluidBlockRegistry.BEER_BLOCK,BucketItemRegistry.BEER_BUCKET);
    
    private static final ForgeFlowingFluid.Properties BRANDY_PROP = 
            createProp(BRANDY, BRANDY_FLOWING, FluidTypeRegistry.BRANDY, FluidBlockRegistry.BRANDY_BLOCK,BucketItemRegistry.BRANDY_BUCKET);
    
    private static final ForgeFlowingFluid.Properties WHISKEY_PROP = 
            createProp(WHISKEY, WHISKEY_FLOWING, FluidTypeRegistry.WHISKEY, FluidBlockRegistry.WHISKEY_BLOCK,BucketItemRegistry.WHISKEY_BUCKET);
    
    private static final ForgeFlowingFluid.Properties RUM_PROP = 
            createProp(RUM, RUM_FLOWING, FluidTypeRegistry.RUM, FluidBlockRegistry.RUM_BLOCK,BucketItemRegistry.RUM_BUCKET);
    
    private static final ForgeFlowingFluid.Properties RED_WINE_PROP = 
            createProp(RED_WINE, RED_WINE_FLOWING, FluidTypeRegistry.RED_WINE, FluidBlockRegistry.RED_WINE_BLOCK,BucketItemRegistry.RED_WINE_BUCKET);
    
    private static final ForgeFlowingFluid.Properties WHITE_WINE_PROP = 
            createProp(WHITE_WINE, WHITE_WINE_FLOWING, FluidTypeRegistry.WHITE_WINE, FluidBlockRegistry.WHITE_WINE_BLOCK,BucketItemRegistry.WHITE_WINE_BUCKET);
    
    private static final ForgeFlowingFluid.Properties CHAMPAGNE_PROP =
            createProp(CHAMPAGNE, CHAMPAGNE_FLOWING, FluidTypeRegistry.CHAMPAGNE, FluidBlockRegistry.CHAMPAGNE_BLOCK,BucketItemRegistry.CHAMPAGNE_BUCKET);

    private static final ForgeFlowingFluid.Properties VODKA_PROP =
            createProp(VODKA, VODKA_FLOWING, FluidTypeRegistry.VODKA, FluidBlockRegistry.VODKA_BLOCK,BucketItemRegistry.VODKA_BUCKET);

    private static final ForgeFlowingFluid.Properties LIQUEUR_PROP =
            createProp(LIQUEUR, LIQUEUR_FLOWING, FluidTypeRegistry.LIQUEUR, FluidBlockRegistry.LIQUEUR_BLOCK,BucketItemRegistry.LIQUEUR_BUCKET);

    private static final ForgeFlowingFluid.Properties COCOA_LIQUEUR_PROP =
            createProp(COCOA_LIQUEUR, COCOA_LIQUEUR_FLOWING, FluidTypeRegistry.COCOA_LIQUEUR, FluidBlockRegistry.COCOA_LIQUEUR_BLOCK,BucketItemRegistry.COCOA_LIQUEUR_BUCKET);

    private static final ForgeFlowingFluid.Properties GIN_PROP =
            createProp(GIN, GIN_FLOWING, FluidTypeRegistry.GIN, FluidBlockRegistry.GIN_BLOCK,BucketItemRegistry.GIN_BUCKET);

    private static final ForgeFlowingFluid.Properties TEQUILA_PROP =
            createProp(TEQUILA, TEQUILA_FLOWING, FluidTypeRegistry.TEQUILA, FluidBlockRegistry.TEQUILA_BLOCK,BucketItemRegistry.TEQUILA_BUCKET);

    private static final ForgeFlowingFluid.Properties GRAPE_FLUID_PROP =
            createProp(GRAPE_FLUID, GRAPE_FLUID_FLOWING, FluidTypeRegistry.GRAPE_FLUID, FluidBlockRegistry.GRAPE_FLUID_BLOCK,BucketItemRegistry.GRAPE_FLUID_BUCKET);

    private static final ForgeFlowingFluid.Properties GREEN_GRAPE_FLUID_PROP =
            createProp(GREEN_GRAPE_FLUID, GREEN_GRAPE_FLUID_FLOWING, FluidTypeRegistry.GREEN_GRAPE_FLUID, FluidBlockRegistry.GREEN_GRAPE_FLUID_BLOCK,BucketItemRegistry.GREEN_GRAPE_FLUID_BUCKET);

    private static final ForgeFlowingFluid.Properties YEAST_LIQUID_PROP =
            createProp(YEAST_LIQUID, YEAST_LIQUID_FLOWING, FluidTypeRegistry.YEAST_LIQUID, FluidBlockRegistry.YEAST_LIQUID_BLOCK,BucketItemRegistry.YEAST_LIQUID_BUCKET);

    private static final ForgeFlowingFluid.Properties MAPLE_SYRUP_PROP =
            createProp(MAPLE_SYRUP, MAPLE_SYRUP_FLOWING, FluidTypeRegistry.MAPLE_SYRUP, FluidBlockRegistry.MAPLE_SYRUP_BLOCK,BucketItemRegistry.MAPLE_SYRUP_BUCKET);

    private static final ForgeFlowingFluid.Properties HOT_SPRING_WATER_PROP =
            createProp(HOT_SPRING_WATER, HOT_SPRING_WATER_FLOWING, FluidTypeRegistry.HOT_SPRING_WATER, FluidBlockRegistry.HOT_SPRING_WATER_BLOCK,BucketItemRegistry.HOT_SPRING_WATER_BUCKET);

    private static ForgeFlowingFluid.Properties createProp(
            Supplier<? extends Fluid> still, 
            Supplier<? extends Fluid> flowing,
            RegistryObject<FluidType> fluidType,
            Supplier<? extends LiquidBlock> block,
            Supplier<? extends Item> bucket){

        UnaryOperator<ForgeFlowingFluid.Properties> blockProperties = p->p.block(block).bucket(bucket).slopeFindDistance(3).explosionResistance(100F);
        return blockProperties.apply(new ForgeFlowingFluid.Properties(fluidType ,still, flowing));
    }
}
