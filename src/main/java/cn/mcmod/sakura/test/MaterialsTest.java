package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class MaterialsTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_materials_registered(GameTestHelper helper) {
        SakuraTestBase.assertAllRegistered(helper, ItemRegistry.MATERIALS, "MATERIALS");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void material_count_matches_enum(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper,
                SakuraNormalItemSet.values().length,
                ItemRegistry.MATERIALS.size(),
                "MATERIALS map size differs from SakuraNormalItemSet enum length");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void empty_bottle_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper,
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE),
                "MATERIALS/EMPTY_BOTTLE");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void bento_box_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper,
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX),
                "MATERIALS/BENTO_BOX");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void bamboo_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper,
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO),
                "MATERIALS/BAMBOO");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void tamahagane_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper,
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE),
                "MATERIALS/TAMAHAGANE");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void alcohol_bottles_registered(GameTestHelper helper) {
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.BEER_BOTTLE, "BEER_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.DOBUROKU_BOTTLE, "DOBUROKU_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SAKE_BOTTLE, "SAKE_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.SHOUCHU_BOTTLE, "SHOUCHU_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.RED_WINE_BOTTLE, "RED_WINE_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.WHITE_WINE_BOTTLE, "WHITE_WINE_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.CHAMPAGNE_BOTTLE, "CHAMPAGNE_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.RUM_BOTTLE, "RUM_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.VODKA_BOTTLE, "VODKA_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.WHISKEY_BOTTLE, "WHISKEY_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.BRANDY_BOTTLE, "BRANDY_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.GIN_BOTTLE, "GIN_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.TEQUILA_BOTTLE, "TEQUILA_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.LIQUEUR_BOTTLE, "LIQUEUR_BOTTLE");
        SakuraTestBase.assertItemRegistered(helper, ItemRegistry.COCOA_LIQUEUR_BOTTLE, "COCOA_LIQUEUR_BOTTLE");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void alcohol_bottles_have_bottle_remainder(GameTestHelper helper) {
        net.minecraft.world.item.Item emptyBottle = ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get();
        net.minecraft.world.item.Item beer = ItemRegistry.BEER_BOTTLE.get();
        net.minecraft.world.item.ItemStack beerStack = new net.minecraft.world.item.ItemStack(beer);
        SakuraTestBase.assertEquals(helper, emptyBottle,
                beerStack.getCraftingRemainingItem().getItem(),
                "BEER_BOTTLE craft remainder should be empty_bottle");
        helper.succeed();
    }
}
