package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class SpecialItemsTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cup_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.CUP, "CUP");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sakura_diamond_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_DIAMOND, "SAKURA_DIAMOND");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void hydra_ramen_has_food_properties(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.HYDRA_RAMEN, "HYDRA_RAMEN");
        Item item = ItemRegistry.HYDRA_RAMEN.get();
        FoodProperties food = item.getFoodProperties();
        SakuraTestBase.assertNotNull(helper, food, "HYDRA_RAMEN must have FoodProperties");
        SakuraTestBase.assertEquals(helper, 20, food.getNutrition(),
                "HYDRA_RAMEN nutrition should be 20");
        SakuraTestBase.assertFalse(helper, food.getEffects().isEmpty(),
                "HYDRA_RAMEN must have effects");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void buggys_meat_has_food_properties(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.BUGGYS_MEAT, "BUGGYS_MEAT");
        Item item = ItemRegistry.BUGGYS_MEAT.get();
        FoodProperties food = item.getFoodProperties();
        SakuraTestBase.assertNotNull(helper, food, "BUGGYS_MEAT must have FoodProperties");
        SakuraTestBase.assertEquals(helper, 20, food.getNutrition(),
                "BUGGYS_MEAT nutrition should be 20");
        SakuraTestBase.assertFalse(helper, food.getEffects().isEmpty(),
                "BUGGYS_MEAT must have effects");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void imogaranawa_has_durability(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.IMOGARANAWA, "IMOGARANAWA");
        Item item = ItemRegistry.IMOGARANAWA.get();
        ItemStack stack = new ItemStack(item);
        SakuraTestBase.assertEquals(helper, 1, stack.getMaxStackSize(),
                "IMOGARANAWA should stack to 1");
        SakuraTestBase.assertTrue(helper, stack.isDamageableItem(),
                "IMOGARANAWA should be damageable");
        SakuraTestBase.assertEquals(helper, 15, stack.getMaxDamage(),
                "IMOGARANAWA max damage should be 15");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void spawn_eggs_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.DEER_SPAWN_EGG, "DEER_SPAWN_EGG");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAMURAI_ILLAGER_SPAWN_EGG, "SAMURAI_ILLAGER_SPAWN_EGG");
        SakuraTestBase.assertTrue(helper, ItemRegistry.DEER_SPAWN_EGG.get() instanceof ForgeSpawnEggItem,
                "DEER_SPAWN_EGG must be a ForgeSpawnEggItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SAMURAI_ILLAGER_SPAWN_EGG.get() instanceof ForgeSpawnEggItem,
                "SAMURAI_ILLAGER_SPAWN_EGG must be a ForgeSpawnEggItem");
        helper.succeed();
    }
}
