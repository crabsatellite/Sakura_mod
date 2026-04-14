package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.BroomItem;
import cn.mcmod.sakura.item.HammerItem;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.KnifeItem;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.RegistryObject;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class ToolsTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void hammers_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.STONE_HAMMER, "STONE_HAMMER");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.IRON_HAMMER, "IRON_HAMMER");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_HAMMER, "SAKURA_HAMMER");
        SakuraTestBase.assertTrue(helper, ItemRegistry.STONE_HAMMER.get() instanceof HammerItem,
                "STONE_HAMMER must be a HammerItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.IRON_HAMMER.get() instanceof HammerItem,
                "IRON_HAMMER must be a HammerItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SAKURA_HAMMER.get() instanceof HammerItem,
                "SAKURA_HAMMER must be a HammerItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void knives_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.IRON_FISH_KNIFE, "IRON_FISH_KNIFE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.IRON_NOODLE_KNIFE, "IRON_NOODLE_KNIFE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_KNIFE_NOODLE, "SAKURA_KNIFE_NOODLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_KNIFE_FISH, "SAKURA_KNIFE_FISH");
        SakuraTestBase.assertTrue(helper, ItemRegistry.IRON_FISH_KNIFE.get() instanceof KnifeItem,
                "IRON_FISH_KNIFE must be a KnifeItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.SAKURA_KNIFE_NOODLE.get() instanceof KnifeItem,
                "SAKURA_KNIFE_NOODLE must be a KnifeItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sakura_tier_tools_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_AXE, "SAKURA_AXE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_PICKAXE, "SAKURA_PICKAXE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_HOE, "SAKURA_HOE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKURA_SHOVEL, "SAKURA_SHOVEL");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void broom_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.BROOM, "BROOM");
        SakuraTestBase.assertTrue(helper, ItemRegistry.BROOM.get() instanceof BroomItem,
                "BROOM must be a BroomItem");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void one_handed_tools_stack_to_one(GameTestHelper helper) {
        RegistryObject<?>[] singleStackTools = new RegistryObject[] {
                ItemRegistry.STONE_HAMMER, ItemRegistry.IRON_HAMMER, ItemRegistry.SAKURA_HAMMER,
                ItemRegistry.IRON_FISH_KNIFE, ItemRegistry.IRON_NOODLE_KNIFE,
                ItemRegistry.SAKURA_KNIFE_NOODLE, ItemRegistry.SAKURA_KNIFE_FISH,
                ItemRegistry.BROOM
        };
        for (RegistryObject<?> obj : singleStackTools) {
            Item item = (Item) obj.get();
            SakuraTestBase.assertEquals(helper, 1, new ItemStack(item).getMaxStackSize(),
                    obj.getId() + " should stack to 1");
        }
        helper.succeed();
    }
}
