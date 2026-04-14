package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.DrinkItem;
import cn.mcmod.sakura.item.DrinkRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraAlcoholSet;
import cn.mcmod.sakura.item.enums.SakuraCocktailSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.item.enums.SakuraTeaSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.RegistryObject;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class DrinksTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_teas_registered(GameTestHelper helper) {
        SakuraTestBase.assertAllRegistered(helper, DrinkRegistry.TEAS, "TEAS");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_alcohols_registered(GameTestHelper helper) {
        SakuraTestBase.assertAllRegistered(helper, DrinkRegistry.ALCOHOLS, "ALCOHOLS");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_cocktails_registered(GameTestHelper helper) {
        SakuraTestBase.assertAllRegistered(helper, DrinkRegistry.COCKTAILS, "COCKTAILS");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void tea_count_matches_enum(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper, SakuraTeaSet.values().length,
                DrinkRegistry.TEAS.size(),
                "TEAS map size differs from SakuraTeaSet enum length");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void alcohol_count_matches_enum(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper, SakuraAlcoholSet.values().length,
                DrinkRegistry.ALCOHOLS.size(),
                "ALCOHOLS map size differs from SakuraAlcoholSet enum length");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cocktail_count_matches_enum(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper, SakuraCocktailSet.values().length,
                DrinkRegistry.COCKTAILS.size(),
                "COCKTAILS map size differs from SakuraCocktailSet enum length");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void drinks_use_drink_animation(GameTestHelper helper) {
        for (RegistryObject<Item> obj : DrinkRegistry.TEAS.values()) {
            DrinkItem drink = (DrinkItem) obj.get();
            ItemStack stack = new ItemStack(drink);
            SakuraTestBase.assertEquals(helper, UseAnim.DRINK, drink.getUseAnimation(stack),
                    "Tea " + obj.getId() + " must use DRINK animation");
            SakuraTestBase.assertEquals(helper, 32, drink.getUseDuration(stack),
                    "Tea " + obj.getId() + " use duration should be 32");
        }
        for (RegistryObject<Item> obj : DrinkRegistry.ALCOHOLS.values()) {
            DrinkItem drink = (DrinkItem) obj.get();
            ItemStack stack = new ItemStack(drink);
            SakuraTestBase.assertEquals(helper, UseAnim.DRINK, drink.getUseAnimation(stack),
                    "Alcohol " + obj.getId() + " must use DRINK animation");
        }
        for (RegistryObject<Item> obj : DrinkRegistry.COCKTAILS.values()) {
            DrinkItem drink = (DrinkItem) obj.get();
            ItemStack stack = new ItemStack(drink);
            SakuraTestBase.assertEquals(helper, UseAnim.DRINK, drink.getUseAnimation(stack),
                    "Cocktail " + obj.getId() + " must use DRINK animation");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void drinks_stack_to_16(GameTestHelper helper) {
        for (RegistryObject<Item> obj : DrinkRegistry.TEAS.values()) {
            SakuraTestBase.assertEquals(helper, 16, new ItemStack(obj.get()).getMaxStackSize(),
                    "Tea " + obj.getId() + " should stack to 16");
        }
        for (RegistryObject<Item> obj : DrinkRegistry.ALCOHOLS.values()) {
            SakuraTestBase.assertEquals(helper, 16, new ItemStack(obj.get()).getMaxStackSize(),
                    "Alcohol " + obj.getId() + " should stack to 16");
        }
        for (RegistryObject<Item> obj : DrinkRegistry.COCKTAILS.values()) {
            SakuraTestBase.assertEquals(helper, 16, new ItemStack(obj.get()).getMaxStackSize(),
                    "Cocktail " + obj.getId() + " should stack to 16");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void tea_effects_match_enum(GameTestHelper helper) {
        for (SakuraTeaSet key : SakuraTeaSet.values()) {
            MobEffectInstance[] effects = key.getEffects();
            SakuraTestBase.assertNotNull(helper, effects, key.name() + " effects null");
            SakuraTestBase.assertTrue(helper, effects.length >= 1,
                    key.name() + " should define at least 1 effect");
            for (MobEffectInstance e : effects) {
                SakuraTestBase.assertNotNull(helper, e.getEffect(), key.name() + " has null MobEffect");
            }
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void alcohol_effects_match_enum(GameTestHelper helper) {
        for (SakuraAlcoholSet key : SakuraAlcoholSet.values()) {
            MobEffectInstance[] effects = key.getEffects();
            SakuraTestBase.assertNotNull(helper, effects, key.name() + " effects null");
            SakuraTestBase.assertTrue(helper, effects.length >= 1,
                    key.name() + " should define at least 1 effect");
            for (MobEffectInstance e : effects) {
                SakuraTestBase.assertNotNull(helper, e.getEffect(), key.name() + " has null MobEffect");
            }
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cocktail_effects_match_enum(GameTestHelper helper) {
        for (SakuraCocktailSet key : SakuraCocktailSet.values()) {
            MobEffectInstance[] effects = key.getEffects();
            SakuraTestBase.assertNotNull(helper, effects, key.name() + " effects null");
            SakuraTestBase.assertTrue(helper, effects.length >= 1,
                    key.name() + " should define at least 1 effect");
            for (MobEffectInstance e : effects) {
                SakuraTestBase.assertNotNull(helper, e.getEffect(), key.name() + " has null MobEffect");
            }
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void teas_use_cup_container_on_use(GameTestHelper helper) {
        Item cup = ItemRegistry.CUP.get();
        SakuraTestBase.assertNotNull(helper, cup, "CUP not registered");
        for (SakuraTeaSet key : SakuraTeaSet.values()) {
            RegistryObject<Item> obj = DrinkRegistry.TEAS.get(key);
            SakuraTestBase.assertNotNull(helper, obj, "Tea missing: " + key.name());
            SakuraTestBase.assertTrue(helper, obj.get() instanceof DrinkItem,
                    key.name() + " must be a DrinkItem");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void alcohols_use_empty_bottle_container(GameTestHelper helper) {
        Item emptyBottle = ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get();
        SakuraTestBase.assertNotNull(helper, emptyBottle, "EMPTY_BOTTLE not registered");
        for (SakuraAlcoholSet key : SakuraAlcoholSet.values()) {
            RegistryObject<Item> obj = DrinkRegistry.ALCOHOLS.get(key);
            SakuraTestBase.assertNotNull(helper, obj, "Alcohol missing: " + key.name());
            SakuraTestBase.assertTrue(helper, obj.get() instanceof DrinkItem,
                    key.name() + " must be a DrinkItem");
        }
        helper.succeed();
    }
}
