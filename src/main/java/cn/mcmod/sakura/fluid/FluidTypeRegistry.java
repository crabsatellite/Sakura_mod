package cn.mcmod.sakura.fluid;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class FluidTypeRegistry {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SakuraMod.MODID);

    // Cooking oils - dense, viscous
    public static final RegistryObject<FluidType> FOOD_OIL = register("food_oil", 0xFFFFF050, 920, 1500, 300);

    // Unfiltered rice wine - slightly viscous, cloudy
    public static final RegistryObject<FluidType> DOBUROKU = register("doburoku", 0xFFCCC299, 1020, 1200, 300);

    // Sake - light, clear rice wine (~15% ABV)
    public static final RegistryObject<FluidType> SAKE = register("sake", 0xDDFFF8CC, 990, 1100, 300);

    // Shouchu - distilled spirit (~25% ABV), lighter than water
    public static final RegistryObject<FluidType> SHOUCHU = register("shouchu", 0xBBFFFCF2, 960, 900, 300);

    // Beer - carbonated, near water density (~5% ABV)
    public static final RegistryObject<FluidType> BEER = register("beer", 0xFFF2A918, 1010, 1050, 280);

    // Whiskey - distilled spirit (~40% ABV)
    public static final RegistryObject<FluidType> WHISKEY = register("whiskey", 0xFFA52121, 940, 850, 300);

    // Rum - distilled spirit (~40% ABV)
    public static final RegistryObject<FluidType> RUM = register("rum", 0xFFFFAA32, 940, 850, 300);

    // Red wine (~14% ABV)
    public static final RegistryObject<FluidType> RED_WINE = register("red_wine", 0xFFA71844, 990, 1100, 300);

    // White wine (~12% ABV)
    public static final RegistryObject<FluidType> WHITE_WINE = register("white_wine", 0xFFFFF8B2, 985, 1050, 300);

    // Champagne - sparkling wine, carbonated
    public static final RegistryObject<FluidType> CHAMPAGNE = register("champagne", 0xFFFFE772, 985, 1000, 280);

    // Brandy - distilled wine (~40% ABV)
    public static final RegistryObject<FluidType> BRANDY = register("brandy", 0xFFBF2F00, 940, 850, 300);

    // Vodka - high-proof spirit (~40% ABV), very clean
    public static final RegistryObject<FluidType> VODKA = register("vodka", 0xBBF0F0FF, 935, 800, 300);

    // Liqueur - sweetened spirit, moderately viscous
    public static final RegistryObject<FluidType> LIQUEUR = register("liqueur", 0xFFE8C040, 1050, 1400, 300);

    // Cocoa liqueur - thick, sweet
    public static final RegistryObject<FluidType> COCOA_LIQUEUR = register("cocoa_liqueur", 0xFF5A3520, 1080, 1600, 300);

    // Gin - distilled spirit with botanicals (~40% ABV)
    public static final RegistryObject<FluidType> GIN = register("gin", 0xBBE8F0E0, 940, 850, 300);

    // Tequila - distilled agave spirit (~40% ABV)
    public static final RegistryObject<FluidType> TEQUILA = register("tequila", 0xFFDDB840, 940, 850, 300);

    // Grape juice - thick fruit juice
    public static final RegistryObject<FluidType> GRAPE_FLUID = register("grape_fluid", 0xFF8B2252, 1050, 1300, 300);

    // Green grape juice
    public static final RegistryObject<FluidType> GREEN_GRAPE_FLUID = register("green_grape_fluid", 0xFFAACC55, 1050, 1300, 300);

    // Yeast liquid - active culture, slightly thick
    public static final RegistryObject<FluidType> YEAST_LIQUID = register("yeast_liquid", 0xFFEEDD99, 1030, 1200, 305);

    // Maple syrup - very dense, very viscous
    public static final RegistryObject<FluidType> MAPLE_SYRUP = register("maple_syrup", 0xFFCC8833, 1370, 3000, 300);

    // Hot spring water - warm, near water density
    public static final RegistryObject<FluidType> HOT_SPRING_WATER = register("hot_spring_water", 0xFF88CCEE, 1000, 400, 340);

    private static RegistryObject<FluidType> register(String name, int color, int density, int viscosity, int temperature) {
        return FLUID_TYPES.register(name, () -> create(color, density, viscosity, temperature));
    }

    private static FluidType create(int color, int density, int viscosity, int temperature) {
        return new FluidType(FluidType.Properties.create()
                .temperature(temperature)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .density(density).viscosity(viscosity)) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public int getTintColor() {
                        return color;
                    }

                    @Override
                    public ResourceLocation getStillTexture() {
                        return new ResourceLocation("block/water_still");
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return new ResourceLocation("block/water_flow");
                    }
                });
            }
        };
    }
}
