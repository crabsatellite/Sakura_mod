package cn.mcmod.sakura.fluid;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;

@EventBusSubscriber(modid = SakuraMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BucketItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, SakuraMod.MODID);
    public static final DeferredHolder<Item, Item> FOOD_OIL_BUCKET = ITEMS.register("food_oil_bucket", () -> 
        new BucketItem(FluidRegistry.FOOD_OIL.get(), new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final DeferredHolder<Item, Item> DOBUROKU_BUCKET = ITEMS.register("doburoku_bucket", () -> 
        new BucketItem(FluidRegistry.DOBUROKU.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> SAKE_BUCKET = ITEMS.register("sake_bucket", () -> 
        new BucketItem(FluidRegistry.SAKE.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> SHOUCHU_BUCKET = ITEMS.register("shouchu_bucket", () -> 
        new BucketItem(FluidRegistry.SHOUCHU.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    
    public static final DeferredHolder<Item, Item> BEER_BUCKET = ITEMS.register("beer_bucket", () -> 
        new BucketItem(FluidRegistry.BEER.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    
    public static final DeferredHolder<Item, Item> WHISKEY_BUCKET = ITEMS.register("whiskey_bucket", () -> 
        new BucketItem(FluidRegistry.WHISKEY.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> RED_WINE_BUCKET = ITEMS.register("red_wine_bucket", () -> 
        new BucketItem(FluidRegistry.RED_WINE.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> WHITE_WINE_BUCKET = ITEMS.register("white_wine_bucket", () -> 
        new BucketItem(FluidRegistry.WHITE_WINE.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> CHAMPAGNE_BUCKET = ITEMS.register("champagne_bucket", () -> 
        new BucketItem(FluidRegistry.CHAMPAGNE.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    
    public static final DeferredHolder<Item, Item> RUM_BUCKET = ITEMS.register("rum_bucket", () -> 
        new BucketItem(FluidRegistry.RUM.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> BRANDY_BUCKET = ITEMS.register("brandy_bucket", () ->
        new BucketItem(FluidRegistry.BRANDY.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> VODKA_BUCKET = ITEMS.register("vodka_bucket", () ->
        new BucketItem(FluidRegistry.VODKA.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> LIQUEUR_BUCKET = ITEMS.register("liqueur_bucket", () ->
        new BucketItem(FluidRegistry.LIQUEUR.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> COCOA_LIQUEUR_BUCKET = ITEMS.register("cocoa_liqueur_bucket", () ->
        new BucketItem(FluidRegistry.COCOA_LIQUEUR.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> GIN_BUCKET = ITEMS.register("gin_bucket", () ->
        new BucketItem(FluidRegistry.GIN.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> TEQUILA_BUCKET = ITEMS.register("tequila_bucket", () ->
        new BucketItem(FluidRegistry.TEQUILA.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> GRAPE_FLUID_BUCKET = ITEMS.register("grape_fluid_bucket", () ->
        new BucketItem(FluidRegistry.GRAPE_FLUID.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> GREEN_GRAPE_FLUID_BUCKET = ITEMS.register("green_grape_fluid_bucket", () ->
        new BucketItem(FluidRegistry.GREEN_GRAPE_FLUID.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> YEAST_LIQUID_BUCKET = ITEMS.register("yeast_liquid_bucket", () ->
        new BucketItem(FluidRegistry.YEAST_LIQUID.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> MAPLE_SYRUP_BUCKET = ITEMS.register("maple_syrup_bucket", () ->
        new BucketItem(FluidRegistry.MAPLE_SYRUP.get(), new Item.Properties().craftRemainder(Items.BUCKET)));
    public static final DeferredHolder<Item, Item> HOT_SPRING_WATER_BUCKET = ITEMS.register("hot_spring_water_bucket", () ->
        new BucketItem(FluidRegistry.HOT_SPRING_WATER.get(), new Item.Properties().craftRemainder(Items.BUCKET)));

    @SubscribeEvent
    public static void onAddCreativeModeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey()== CreativeModeTabs.INGREDIENTS){
            ITEMS.getEntries().forEach(entry -> event.accept(entry.get()));
        }
    }
}
