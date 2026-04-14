package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.enums.SakuraCuisineSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import cn.mcmod_mmf.mmlib.item.ItemFoodBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class CuisinesTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_cuisines_registered(GameTestHelper helper) {
        SakuraTestBase.assertAllRegistered(helper, FoodRegistry.CUISINES, "CUISINES");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cuisine_count_matches_enum(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper,
                SakuraCuisineSet.values().length,
                FoodRegistry.CUISINES.size(),
                "CUISINES map size differs from SakuraCuisineSet enum length");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cuisines_have_container_remainder(GameTestHelper helper) {
        for (SakuraCuisineSet key : SakuraCuisineSet.values()) {
            DeferredHolder<Item, ItemFoodBase> obj = FoodRegistry.CUISINES.get(key);
            SakuraTestBase.assertNotNull(helper, obj, "CUISINES missing " + key.name());
            SakuraTestBase.assertTrue(helper, obj.isBound(), key.name() + " not bound");
            Item container = key.getContainer().get();
            SakuraTestBase.assertNotNull(helper, container, key.name() + " has null container item");
            ItemStack stack = new ItemStack(obj.get());
            ItemStack remainder = stack.getCraftingRemainingItem();
            SakuraTestBase.assertFalse(helper, remainder.isEmpty(),
                    key.name() + " must have crafting remainder (container)");
            SakuraTestBase.assertEquals(helper, container, remainder.getItem(),
                    key.name() + " crafting remainder should be its container");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cuisines_have_food_info(GameTestHelper helper) {
        for (SakuraCuisineSet key : SakuraCuisineSet.values()) {
            ItemFoodBase food = FoodRegistry.CUISINES.get(key).get();
            SakuraTestBase.assertNotNull(helper, food.getFoodInfo(),
                    key.name() + " has null FoodInfo");
            SakuraTestBase.assertEquals(helper, key.getFoodInfo().getName(),
                    food.getFoodInfo().getName(),
                    key.name() + " FoodInfo name mismatch");
        }
        helper.succeed();
    }
}
