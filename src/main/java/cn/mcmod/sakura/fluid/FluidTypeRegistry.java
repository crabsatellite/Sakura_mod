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

    // Alcohol colors are web-authoritative references (Encycolorpedia / product photography) tuned
    // for legibility at 16px: real-world hue but dominant enough that each drink reads distinct from
    // its neighbors. The matching _still/_flow PNGs are painted with the same value, so buckets,
    // in-world fluid blocks, and glass-drink items all render the same hue.
    public static final RegistryObject<FluidType> FOOD_OIL = register("food_oil", 0xFFFFEF45, 920, 1500, 300);
    public static final RegistryObject<FluidType> DOBUROKU = register("doburoku", 0xFFF0EDE0, 1020, 1200, 300);
    public static final RegistryObject<FluidType> SAKE = register("sake", 0xFFF5F2D8, 990, 1100, 300);
    public static final RegistryObject<FluidType> SHOUCHU = register("shouchu", 0xFFEAF4F5, 960, 900, 300);
    public static final RegistryObject<FluidType> BEER = register("beer", 0xFFE89611, 1010, 1050, 280);
    public static final RegistryObject<FluidType> WHISKEY = register("whiskey", 0xFFA04A0C, 940, 850, 300);
    public static final RegistryObject<FluidType> RUM = register("rum", 0xFFB8661C, 940, 850, 300);
    public static final RegistryObject<FluidType> RED_WINE = register("red_wine", 0xFF6B1A24, 990, 1100, 300);
    public static final RegistryObject<FluidType> WHITE_WINE = register("white_wine", 0xFFE8D76A, 985, 1050, 300);
    public static final RegistryObject<FluidType> CHAMPAGNE = register("champagne", 0xFFF8E79B, 985, 1000, 280);
    public static final RegistryObject<FluidType> BRANDY = register("brandy", 0xFFC76A1E, 940, 850, 300);
    public static final RegistryObject<FluidType> VODKA = register("vodka", 0xFFF2FBFF, 935, 800, 300);
    public static final RegistryObject<FluidType> LIQUEUR = register("liqueur", 0xFFD4E020, 1050, 1400, 300);
    public static final RegistryObject<FluidType> COCOA_LIQUEUR = register("cocoa_liqueur", 0xFF3A1E0F, 1080, 1600, 300);
    public static final RegistryObject<FluidType> GIN = register("gin", 0xFFE4F6E8, 940, 850, 300);
    public static final RegistryObject<FluidType> TEQUILA = register("tequila", 0xFFD4A34A, 940, 850, 300);
    public static final RegistryObject<FluidType> GRAPE_FLUID = register("grape_fluid", 0xFF682961, 1050, 1300, 300);
    public static final RegistryObject<FluidType> GREEN_GRAPE_FLUID = register("green_grape_fluid", 0xFFD4E09C, 1050, 1300, 300);
    public static final RegistryObject<FluidType> YEAST_LIQUID = register("yeast_liquid", 0xFFE0C57F, 1030, 1200, 305);
    public static final RegistryObject<FluidType> MAPLE_SYRUP = register("maple_syrup", 0xFFBB9351, 1370, 3000, 300);
    public static final RegistryObject<FluidType> HOT_SPRING_WATER = register("hot_spring_water", 0xFFA7D0EA, 1000, 400, 340);

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
