package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

/**
 * Regression tests for Bug 2: the zuku -&gt; iron_ingot chain was broken because
 * zuku_ingot had no downstream consumer and sagegane had no way to become iron.
 * These tests assert that the complete chain is loadable from the RecipeManager.
 */
@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class IronChainRecipeTest {

    private static Recipe<?> recipe(GameTestHelper helper, String path) {
        RecipeManager rm = helper.getLevel().getRecipeManager();
        ResourceLocation id = new ResourceLocation(SakuraMod.MODID, path);
        return rm.byKey(id).orElse(null);
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void zuku_to_zuku_ingot_recipe_exists(GameTestHelper helper) {
        Recipe<?> r = recipe(helper, "zuku_ingot_from_smelting");
        SakuraTestBase.assertNotNull(helper, r, "zuku_ingot_from_smelting recipe must exist");
        ItemStack out = r.getResultItem(helper.getLevel().registryAccess());
        SakuraTestBase.assertTrue(helper,
                out.is(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ZUKU_INGOT).get()),
                "recipe must produce zuku_ingot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sagegane_from_zuku_ingot_recipe_exists(GameTestHelper helper) {
        Recipe<?> r = recipe(helper, "sagegane_from_zuku_ingot");
        SakuraTestBase.assertNotNull(helper, r,
                "sagegane_from_zuku_ingot recipe must exist (fills the missing middle link of the chain)");
        ItemStack out = r.getResultItem(helper.getLevel().registryAccess());
        SakuraTestBase.assertTrue(helper,
                out.is(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAGEGANE).get()),
                "recipe must produce sagegane");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void iron_ingot_from_sagegane_recipe_exists(GameTestHelper helper) {
        Recipe<?> r = recipe(helper, "iron_ingot_from_sagegane");
        SakuraTestBase.assertNotNull(helper, r,
                "iron_ingot_from_sagegane recipe must exist (final step of the chain)");
        ItemStack out = r.getResultItem(helper.getLevel().registryAccess());
        SakuraTestBase.assertTrue(helper, out.is(Items.IRON_INGOT), "recipe must produce minecraft:iron_ingot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void full_iron_chain_is_closed(GameTestHelper helper) {
        SakuraTestBase.assertNotNull(helper, recipe(helper, "zuku_ingot_from_smelting"), "step 1: zuku -> zuku_ingot");
        SakuraTestBase.assertNotNull(helper, recipe(helper, "sagegane_from_zuku_ingot"), "step 2: zuku_ingot -> sagegane");
        SakuraTestBase.assertNotNull(helper, recipe(helper, "iron_ingot_from_sagegane"), "step 3: sagegane -> iron_ingot");
        helper.succeed();
    }
}
