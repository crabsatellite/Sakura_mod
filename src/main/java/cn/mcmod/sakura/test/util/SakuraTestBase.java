package cn.mcmod.sakura.test.util;

import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public final class SakuraTestBase {
    public static final String EMPTY_TEMPLATE = "sakura:empty";

    private SakuraTestBase() {}

    public static void assertTrue(GameTestHelper helper, boolean cond, String message) {
        if (!cond) throw new GameTestAssertException(message);
    }

    public static void assertFalse(GameTestHelper helper, boolean cond, String message) {
        assertTrue(helper, !cond, message);
    }

    public static void assertNotNull(GameTestHelper helper, Object obj, String message) {
        assertTrue(helper, obj != null, message);
    }

    public static void assertEquals(GameTestHelper helper, int expected, int actual, String message) {
        assertTrue(helper, expected == actual,
                message + " (expected=" + expected + ", actual=" + actual + ")");
    }

    public static void assertEquals(GameTestHelper helper, float expected, float actual, String message) {
        assertTrue(helper, Math.abs(expected - actual) < 0.0001F,
                message + " (expected=" + expected + ", actual=" + actual + ")");
    }

    public static void assertEquals(GameTestHelper helper, Object expected, Object actual, String message) {
        assertTrue(helper, expected == null ? actual == null : expected.equals(actual),
                message + " (expected=" + expected + ", actual=" + actual + ")");
    }

    public static void assertItemRegistered(GameTestHelper helper, RegistryObject<? extends Item> obj, String name) {
        assertNotNull(helper, obj, "RegistryObject for '" + name + "' is null");
        assertTrue(helper, obj.isPresent(), "Item '" + name + "' is not present in registry");
        Item item = obj.get();
        assertNotNull(helper, item, "Item '" + name + "' resolved to null");
        ItemStack stack = new ItemStack(item);
        assertFalse(helper, stack.isEmpty(), "Item '" + name + "' produced empty ItemStack");
    }

    public static <K extends Enum<K>, V extends Item> void assertAllRegistered(
            GameTestHelper helper, Map<K, ? extends RegistryObject<? extends V>> map, String category) {
        assertNotNull(helper, map, category + " map is null");
        assertFalse(helper, map.isEmpty(), category + " map is empty");
        for (Map.Entry<K, ? extends RegistryObject<? extends V>> e : map.entrySet()) {
            assertItemRegistered(helper, e.getValue(), category + "/" + e.getKey().name());
        }
    }
}
