package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.CreativeModeTabRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class CreativeModeTabTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sakura_content_is_split_across_focused_tabs(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper, 4, CreativeModeTabRegistry.TABS.getEntries().size(),
                "Sakura content should be split into block/item/food/drink creative tabs");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.GROUP.isBound(),
                "Sakura blocks tab is not registered");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.ITEMS.isBound(),
                "Sakura items tab is not registered");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.FOODS.isBound(),
                "Sakura foods tab is not registered");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.DRINKS.isBound(),
                "Sakura drinks tab is not registered");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void legacy_internal_blocks_are_hidden_from_creative_tabs(GameTestHelper helper) {
        String[] hidden = {
                "straw_web",
                "campfire_idle",
                "campfire_pot_idle",
                "barrel_out",
                "soba_block",
                "ramen_block",
                "pasta_block",
                "udon_unfinished_block"
        };
        for (String id : hidden) {
            SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.isLegacyInternalBlockItem(id),
                    id + " should be classified as legacy/internal content");
            SakuraTestBase.assertFalse(helper, CreativeModeTabRegistry.shouldShowInCreative(id),
                    id + " should not be shown in creative tabs");
        }
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.shouldShowInCreative("tatara"),
                "normal gameplay blocks should still be visible");
        SakuraTestBase.assertTrue(helper, CreativeModeTabRegistry.shouldShowInCreative("onigiri"),
                "normal food/items should still be visible");
        helper.succeed();
    }
}
