package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.CreativeModeTabRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class LegacyRecipeExposureTest {

    private static final String[] REMOVED_RECIPES = {
            "campfire_idle",
            "straw_web",
            "ramen_raw_chopping",
            "udon_raw_chopping",
            "soba_raw_chopping",
            "pasta_raw_chopping"
    };

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void removed_legacy_blocks_are_not_craftable(GameTestHelper helper) {
        RecipeManager recipeManager = helper.getLevel().getRecipeManager();
        for (String recipe : REMOVED_RECIPES) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, recipe);
            SakuraTestBase.assertFalse(helper, recipeManager.byKey(id).isPresent(),
                    id + " should not be exposed as a loadable recipe");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void legacy_blocks_remain_internal_only(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.isLegacyInternalBlockItem("campfire_idle"),
                "campfire_idle should be internal compatibility content");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.isLegacyInternalBlockItem("straw_web"),
                "straw_web should be internal compatibility content");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.isLegacyInternalBlockItem("barrel_out"),
                "barrel_out should be internal compatibility content");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.isLegacyInternalBlockItem("ramen_block"),
                "raw noodle blocks should be internal compatibility content");
        helper.succeed();
    }
}
