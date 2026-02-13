package cn.mcmod.sakura.fluid;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SakuraMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BucketItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SakuraMod.MODID);
    public static final RegistryObject<Item> FOOD_OIL_BUCKET = ITEMS.register("food_oil_bucket", () -> 
        new BucketItem(FluidRegistry.FOOD_OIL, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> DOBUROKU_BUCKET = ITEMS.register("doburoku_bucket", () -> 
        new BucketItem(FluidRegistry.DOBUROKU, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> SAKE_BUCKET = ITEMS.register("sake_bucket", () -> 
        new BucketItem(FluidRegistry.SAKE, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> SHOUCHU_BUCKET = ITEMS.register("shouchu_bucket", () -> 
        new BucketItem(FluidRegistry.SHOUCHU, new Item.Properties().craftRemainder(Items.BUCKET)));
    
    public static final RegistryObject<Item> BEER_BUCKET = ITEMS.register("beer_bucket", () -> 
        new BucketItem(FluidRegistry.BEER, new Item.Properties().craftRemainder(Items.BUCKET)));
    
    public static final RegistryObject<Item> WHISKEY_BUCKET = ITEMS.register("whiskey_bucket", () -> 
        new BucketItem(FluidRegistry.WHISKEY, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> RED_WINE_BUCKET = ITEMS.register("red_wine_bucket", () -> 
        new BucketItem(FluidRegistry.RED_WINE, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> WHITE_WINE_BUCKET = ITEMS.register("white_wine_bucket", () -> 
        new BucketItem(FluidRegistry.WHITE_WINE, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> CHAMPAGNE_BUCKET = ITEMS.register("champagne_bucket", () -> 
        new BucketItem(FluidRegistry.CHAMPAGNE, new Item.Properties().craftRemainder(Items.BUCKET)));
    
    public static final RegistryObject<Item> RUM_BUCKET = ITEMS.register("rum_bucket", () -> 
        new BucketItem(FluidRegistry.RUM, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> BRANDY_BUCKET = ITEMS.register("brandy_bucket", () ->
        new BucketItem(FluidRegistry.BRANDY, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> VODKA_BUCKET = ITEMS.register("vodka_bucket", () ->
        new BucketItem(FluidRegistry.VODKA, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> LIQUEUR_BUCKET = ITEMS.register("liqueur_bucket", () ->
        new BucketItem(FluidRegistry.LIQUEUR, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> COCOA_LIQUEUR_BUCKET = ITEMS.register("cocoa_liqueur_bucket", () ->
        new BucketItem(FluidRegistry.COCOA_LIQUEUR, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> GIN_BUCKET = ITEMS.register("gin_bucket", () ->
        new BucketItem(FluidRegistry.GIN, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> TEQUILA_BUCKET = ITEMS.register("tequila_bucket", () ->
        new BucketItem(FluidRegistry.TEQUILA, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> GRAPE_FLUID_BUCKET = ITEMS.register("grape_fluid_bucket", () ->
        new BucketItem(FluidRegistry.GRAPE_FLUID, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> GREEN_GRAPE_FLUID_BUCKET = ITEMS.register("green_grape_fluid_bucket", () ->
        new BucketItem(FluidRegistry.GREEN_GRAPE_FLUID, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> YEAST_LIQUID_BUCKET = ITEMS.register("yeast_liquid_bucket", () ->
        new BucketItem(FluidRegistry.YEAST_LIQUID, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> MAPLE_SYRUP_BUCKET = ITEMS.register("maple_syrup_bucket", () ->
        new BucketItem(FluidRegistry.MAPLE_SYRUP, new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> HOT_SPRING_WATER_BUCKET = ITEMS.register("hot_spring_water_bucket", () ->
        new BucketItem(FluidRegistry.HOT_SPRING_WATER, new Item.Properties().craftRemainder(Items.BUCKET)));

    @SubscribeEvent
    public static void onAddCreativeModeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey()== CreativeModeTabs.INGREDIENTS){
            ITEMS.getEntries().forEach(entry -> event.accept(entry.get()));
        }
    }
}
