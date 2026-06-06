package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.KimonoItem;
import cn.mcmod.sakura.item.KimonoRenderLayerPolicy;
import cn.mcmod.sakura.item.SamuraiArmorItem;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class ArmorTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void straw_hat_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.STRAW_HAT, "STRAW_HAT");
        SakuraTestBase.assertTrue(helper, ItemRegistry.STRAW_HAT.get() instanceof SamuraiArmorItem,
                "STRAW_HAT must be a SamuraiArmorItem");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.HELMET,
                ((ArmorItem) ItemRegistry.STRAW_HAT.get()).getType(),
                "STRAW_HAT must be HELMET slot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void samurai_set_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAMURAI_HELMET, "SAMURAI_HELMET");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAMURAI_CHEST, "SAMURAI_CHEST");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAMURAI_PANTS, "SAMURAI_PANTS");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAMURAI_SHOES, "SAMURAI_SHOES");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.HELMET,
                ((ArmorItem) ItemRegistry.SAMURAI_HELMET.get()).getType(),
                "SAMURAI_HELMET wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.CHESTPLATE,
                ((ArmorItem) ItemRegistry.SAMURAI_CHEST.get()).getType(),
                "SAMURAI_CHEST wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.LEGGINGS,
                ((ArmorItem) ItemRegistry.SAMURAI_PANTS.get()).getType(),
                "SAMURAI_PANTS wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.BOOTS,
                ((ArmorItem) ItemRegistry.SAMURAI_SHOES.get()).getType(),
                "SAMURAI_SHOES wrong slot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void soldier_set_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SOLDIER_HELMET, "SOLDIER_HELMET");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SOLDIER_CHEST, "SOLDIER_CHEST");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SOLDIER_PANTS, "SOLDIER_PANTS");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SOLDIER_SHOES, "SOLDIER_SHOES");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.HELMET,
                ((ArmorItem) ItemRegistry.SOLDIER_HELMET.get()).getType(),
                "SOLDIER_HELMET wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.CHESTPLATE,
                ((ArmorItem) ItemRegistry.SOLDIER_CHEST.get()).getType(),
                "SOLDIER_CHEST wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.LEGGINGS,
                ((ArmorItem) ItemRegistry.SOLDIER_PANTS.get()).getType(),
                "SOLDIER_PANTS wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.BOOTS,
                ((ArmorItem) ItemRegistry.SOLDIER_SHOES.get()).getType(),
                "SOLDIER_SHOES wrong slot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void kimono_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.KIMONO, "KIMONO");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.HAORI, "HAORI");
        SakuraTestBase.assertTrue(helper, ItemRegistry.KIMONO.get() instanceof KimonoItem,
                "KIMONO must be KimonoItem");
        SakuraTestBase.assertTrue(helper, ItemRegistry.HAORI.get() instanceof KimonoItem,
                "HAORI must be KimonoItem");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.LEGGINGS,
                ((ArmorItem) ItemRegistry.KIMONO.get()).getType(),
                "KIMONO wrong slot");
        SakuraTestBase.assertEquals(helper, ArmorItem.Type.CHESTPLATE,
                ((ArmorItem) ItemRegistry.HAORI.get()).getType(),
                "HAORI wrong slot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void kimono_and_haori_render_layers_do_not_overlap(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper, KimonoRenderLayerPolicy.showsBody(EquipmentSlot.CHEST),
                "Haori chest layer should show body");
        SakuraTestBase.assertTrue(helper, KimonoRenderLayerPolicy.showsArms(EquipmentSlot.CHEST),
                "Haori chest layer should show arms");
        SakuraTestBase.assertFalse(helper, KimonoRenderLayerPolicy.showsLegs(EquipmentSlot.CHEST),
                "Haori chest layer must not render legs over kimono");

        SakuraTestBase.assertFalse(helper, KimonoRenderLayerPolicy.showsBody(EquipmentSlot.LEGS),
                "Kimono leg layer must not render body under haori");
        SakuraTestBase.assertFalse(helper, KimonoRenderLayerPolicy.showsArms(EquipmentSlot.LEGS),
                "Kimono leg layer must not render arms under haori");
        SakuraTestBase.assertTrue(helper, KimonoRenderLayerPolicy.showsLegs(EquipmentSlot.LEGS),
                "Kimono leg layer should render legs");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void kimono_pattern_variants_registered(GameTestHelper helper) {
        DeferredHolder<Item, Item>[] kimonoVariants = new DeferredHolder[] {
                ItemRegistry.KIMONO_BASE, ItemRegistry.KIMONO_1, ItemRegistry.KIMONO_2,
                ItemRegistry.KIMONO_3, ItemRegistry.KIMONO_4, ItemRegistry.KIMONO_5,
                ItemRegistry.KIMONO_6, ItemRegistry.KIMONO_MIKO, ItemRegistry.KIMONO_ENE
        };
        for (DeferredHolder<Item, Item> obj : kimonoVariants) {
            SakuraTestBase.assertItemRegistered(helper, obj, obj.getId().toString());
            SakuraTestBase.assertTrue(helper, obj.get() instanceof KimonoItem,
                    obj.getId() + " must be KimonoItem");
            SakuraTestBase.assertEquals(helper, ArmorItem.Type.LEGGINGS,
                    ((ArmorItem) obj.get()).getType(),
                    obj.getId() + " wrong slot (should be LEGGINGS)");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void haori_pattern_variants_registered(GameTestHelper helper) {
        DeferredHolder<Item, Item>[] haoriVariants = new DeferredHolder[] {
                ItemRegistry.HAORI_BASE, ItemRegistry.HAORI_1, ItemRegistry.HAORI_2,
                ItemRegistry.HAORI_3, ItemRegistry.HAORI_4
        };
        for (DeferredHolder<Item, Item> obj : haoriVariants) {
            SakuraTestBase.assertItemRegistered(helper, obj, obj.getId().toString());
            SakuraTestBase.assertEquals(helper, ArmorItem.Type.CHESTPLATE,
                    ((ArmorItem) obj.get()).getType(),
                    obj.getId() + " wrong slot (should be CHESTPLATE)");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void yukata_variants_registered(GameTestHelper helper) {
        DeferredHolder<Item, Item>[] yukataVariants = new DeferredHolder[] {
                ItemRegistry.YUKATA_0, ItemRegistry.YUKATA_1, ItemRegistry.YUKATA_2,
                ItemRegistry.YUKATA_3, ItemRegistry.YUKATA_4
        };
        for (DeferredHolder<Item, Item> obj : yukataVariants) {
            SakuraTestBase.assertItemRegistered(helper, obj, obj.getId().toString());
            SakuraTestBase.assertEquals(helper, ArmorItem.Type.LEGGINGS,
                    ((ArmorItem) obj.get()).getType(),
                    obj.getId() + " wrong slot (should be LEGGINGS)");
        }
        helper.succeed();
    }
}
