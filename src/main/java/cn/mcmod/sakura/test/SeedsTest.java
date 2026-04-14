package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.RegistryObject;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class SeedsTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_seeds_registered(GameTestHelper helper) {
        RegistryObject<? extends Item>[] seeds = new RegistryObject[] {
                ItemRegistry.RICE_SEEDS,
                ItemRegistry.ONION_SEEDS,
                ItemRegistry.RADISH_SEEDS,
                ItemRegistry.CABBAGE_SEEDS,
                ItemRegistry.RAPESEEDS,
                ItemRegistry.RED_BEAN,
                ItemRegistry.SOYBEAN,
                ItemRegistry.BUCKWHEAT,
                ItemRegistry.EGGPLANT_SEEDS,
                ItemRegistry.TOMATO_SEEDS,
                ItemRegistry.TARO,
                ItemRegistry.PEPPER_SEEDS,
                ItemRegistry.VANILLA_SEEDS,
                ItemRegistry.GRAPE_SEEDS,
                ItemRegistry.HOP_SEEDS,
                ItemRegistry.SEAWEED_SEEDS
        };
        for (RegistryObject<? extends Item> obj : seeds) {
            SakuraTestBase.assertItemRegistered(helper, obj, obj.getId().toString());
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void taro_has_food_info(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.TARO, "TARO");
        SakuraTestBase.assertTrue(helper,
                ItemRegistry.TARO.get() instanceof cn.mcmod_mmf.mmlib.item.ItemFoodSeeds,
                "TARO should be ItemFoodSeeds");
        helper.succeed();
    }
}
