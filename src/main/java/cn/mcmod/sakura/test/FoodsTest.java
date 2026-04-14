package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import cn.mcmod_mmf.mmlib.item.ItemFoodBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.RegistryObject;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class FoodsTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_foods_registered(GameTestHelper helper) {
        SakuraTestBase.assertAllRegistered(helper, FoodRegistry.FOODSET, "FOODSET");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void food_count_matches_enum(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper,
                SakuraFoodSet.values().length,
                FoodRegistry.FOODSET.size(),
                "FOODSET map size differs from SakuraFoodSet enum length");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_foods_have_food_properties(GameTestHelper helper) {
        for (SakuraFoodSet key : SakuraFoodSet.values()) {
            RegistryObject<ItemFoodBase> obj = FoodRegistry.FOODSET.get(key);
            SakuraTestBase.assertNotNull(helper, obj, "FOODSET missing key " + key.name());
            SakuraTestBase.assertTrue(helper, obj.isPresent(), key.name() + " not present in registry");
            ItemFoodBase food = obj.get();
            SakuraTestBase.assertNotNull(helper, food.getFoodInfo(),
                    key.name() + " has null FoodInfo");
            SakuraTestBase.assertEquals(helper, key.getFoodInfo().getName(), food.getFoodInfo().getName(),
                    key.name() + " FoodInfo name mismatch");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void food_amounts_are_non_negative(GameTestHelper helper) {
        for (SakuraFoodSet key : SakuraFoodSet.values()) {
            SakuraTestBase.assertTrue(helper,
                    key.getFoodInfo().getAmount() >= 0,
                    key.name() + " has negative amount");
        }
        helper.succeed();
    }
}
