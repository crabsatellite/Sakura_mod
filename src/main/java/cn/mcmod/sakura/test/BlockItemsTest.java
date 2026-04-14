package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class BlockItemsTest {

    @SuppressWarnings("unchecked")
    private static List<DeferredHolder<Item, Item>> collect() {
        List<DeferredHolder<Item, Item>> out = new ArrayList<>();
        for (Field f : BlockItemRegistry.class.getDeclaredFields()) {
            int mods = f.getModifiers();
            if (!(Modifier.isPublic(mods) && Modifier.isStatic(mods) && Modifier.isFinal(mods))) continue;
            if (!DeferredHolder.class.isAssignableFrom(f.getType())) continue;
            try {
                out.add((DeferredHolder<Item, Item>) f.get(null));
            } catch (IllegalAccessException ignored) {}
        }
        return out;
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_block_items_registered(GameTestHelper helper) {
        List<DeferredHolder<Item, Item>> all = collect();
        SakuraTestBase.assertFalse(helper, all.isEmpty(), "BlockItemRegistry has no DeferredHolder fields");
        for (DeferredHolder<Item, Item> obj : all) {
            SakuraTestBase.assertItemRegistered(helper, obj, obj.getId().toString());
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void block_item_count_is_expected(GameTestHelper helper) {
        int count = collect().size();
        SakuraTestBase.assertTrue(helper, count >= 120,
                "Expected >= 120 block items, got " + count);
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_block_items_reference_valid_block(GameTestHelper helper) {
        for (DeferredHolder<Item, Item> obj : collect()) {
            Item item = obj.get();
            if (item instanceof BlockItem blockItem) {
                SakuraTestBase.assertNotNull(helper, blockItem.getBlock(),
                        obj.getId() + " has null backing Block");
            }
        }
        helper.succeed();
    }
}
