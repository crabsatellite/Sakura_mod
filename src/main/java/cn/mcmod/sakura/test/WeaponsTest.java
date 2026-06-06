package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.KatanaItem;
import cn.mcmod.sakura.item.KodachiItem;
import cn.mcmod.sakura.item.SheathItem;
import cn.mcmod.sakura.item.SheathKatanaItem;
import cn.mcmod.sakura.item.ShinaiItem;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class WeaponsTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void katanas_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.KATANA, "KATANA");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.TACHI, "TACHI");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_KATANA, "SAKURA_KATANA");
        SakuraTestBase.assertTrue(helper, ItemRegistry.KATANA.get() instanceof KatanaItem,
                "KATANA must be a KatanaItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.TACHI.get() instanceof KatanaItem,
                "TACHI must be a KatanaItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SAKURA_KATANA.get() instanceof KatanaItem,
                "SAKURA_KATANA must be a KatanaItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void kodachis_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.KODACHI, "KODACHI");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_KODACHI, "SAKURA_KODACHI");
        SakuraTestBase.assertTrue(helper, ItemRegistry.KODACHI.get() instanceof KodachiItem,
                "KODACHI must be a KodachiItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SAKURA_KODACHI.get() instanceof KodachiItem,
                "SAKURA_KODACHI must be a KodachiItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void shinai_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SHINAI, "SHINAI");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SHINAI.get() instanceof ShinaiItem,
                "SHINAI must be a ShinaiItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sheaths_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SHEATH, "SHEATH");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.KATANA_SHEATH, "KATANA_SHEATH");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_KATANA_SHEATH, "SAKURA_KATANA_SHEATH");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SHEATH.get() instanceof SheathItem,
                "SHEATH must be a SheathItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.KATANA_SHEATH.get() instanceof SheathKatanaItem,
                "KATANA_SHEATH must be a SheathKatanaItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SAKURA_KATANA_SHEATH.get() instanceof SheathKatanaItem,
                "SAKURA_KATANA_SHEATH must be a SheathKatanaItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void unsheathed_katana_does_not_start_blocking_use_action(GameTestHelper helper) {
        Item katana = ItemRegistry.KATANA.get();
        ItemStack stack = new ItemStack(katana);
        SakuraTestBase.assertEquals(helper, UseAnim.NONE, katana.getUseAnimation(stack),
                "KATANA should not use BLOCK animation when unsheathed");
        SakuraTestBase.assertEquals(helper, 0, katana.getUseDuration(stack, null),
                "KATANA should not have a right-click use duration when unsheathed");
        Item tachi = ItemRegistry.TACHI.get();
        SakuraTestBase.assertEquals(helper, UseAnim.NONE, tachi.getUseAnimation(new ItemStack(tachi)),
                "TACHI should not use BLOCK animation when unsheathed");
        SakuraTestBase.assertEquals(helper, 0, tachi.getUseDuration(new ItemStack(tachi), null),
                "TACHI should not have a right-click use duration when unsheathed");
        Item sakuraKatana = ItemRegistry.SAKURA_KATANA.get();
        SakuraTestBase.assertEquals(helper, UseAnim.NONE, sakuraKatana.getUseAnimation(new ItemStack(sakuraKatana)),
                "SAKURA_KATANA should not use BLOCK animation when unsheathed");
        SakuraTestBase.assertEquals(helper, 0, sakuraKatana.getUseDuration(new ItemStack(sakuraKatana), null),
                "SAKURA_KATANA should not have a right-click use duration when unsheathed");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sheathed_katana_keeps_quick_draw_use_action(GameTestHelper helper) {
        Item sheathed = ItemRegistry.KATANA_SHEATH.get();
        ItemStack stack = new ItemStack(sheathed);
        SakuraTestBase.assertEquals(helper, UseAnim.BOW, sheathed.getUseAnimation(stack),
                "Sheathed katana should keep BOW quick-draw animation");
        SakuraTestBase.assertEquals(helper, 20, sheathed.getUseDuration(stack, null),
                "Sheathed katana should keep short quick-draw duration");
        Item sakuraSheathed = ItemRegistry.SAKURA_KATANA_SHEATH.get();
        ItemStack sakuraStack = new ItemStack(sakuraSheathed);
        SakuraTestBase.assertEquals(helper, UseAnim.BOW, sakuraSheathed.getUseAnimation(sakuraStack),
                "Sakura sheathed katana should keep BOW quick-draw animation");
        SakuraTestBase.assertEquals(helper, 20, sakuraSheathed.getUseDuration(sakuraStack, null),
                "Sakura sheathed katana should keep short quick-draw duration");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void empty_sheath_still_blocks(GameTestHelper helper) {
        Item sheath = ItemRegistry.SHEATH.get();
        ItemStack stack = new ItemStack(sheath);
        SakuraTestBase.assertEquals(helper, UseAnim.BLOCK, sheath.getUseAnimation(stack),
                "Empty sheath should still use BLOCK animation");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void weapons_stack_to_one(GameTestHelper helper) {
        Item[] weapons = new Item[] {
                ItemRegistry.KATANA.get(), ItemRegistry.TACHI.get(), ItemRegistry.SAKURA_KATANA.get(),
                ItemRegistry.KODACHI.get(), ItemRegistry.SAKURA_KODACHI.get(),
                ItemRegistry.SHINAI.get(),
                ItemRegistry.SHEATH.get(), ItemRegistry.KATANA_SHEATH.get(), ItemRegistry.SAKURA_KATANA_SHEATH.get()
        };
        for (Item w : weapons) {
            SakuraTestBase.assertEquals(helper, 1, new ItemStack(w).getMaxStackSize(),
                    w + " should stack to 1");
        }
        helper.succeed();
    }
}
