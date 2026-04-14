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
    public static void katana_uses_block_animation(GameTestHelper helper) {
        Item katana = ItemRegistry.KATANA.get();
        ItemStack stack = new ItemStack(katana);
        SakuraTestBase.assertEquals(helper, UseAnim.BLOCK, katana.getUseAnimation(stack),
                "KATANA right-click animation should be BLOCK");
        Item tachi = ItemRegistry.TACHI.get();
        SakuraTestBase.assertEquals(helper, UseAnim.BLOCK, tachi.getUseAnimation(new ItemStack(tachi)),
                "TACHI right-click animation should be BLOCK");
        Item sakuraKatana = ItemRegistry.SAKURA_KATANA.get();
        SakuraTestBase.assertEquals(helper, UseAnim.BLOCK, sakuraKatana.getUseAnimation(new ItemStack(sakuraKatana)),
                "SAKURA_KATANA right-click animation should be BLOCK");
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
