package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.RegistryObject;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class BucketsTest {

    private static final RegistryObject<Item>[] BUCKETS = new RegistryObject[] {
            BucketItemRegistry.FOOD_OIL_BUCKET,
            BucketItemRegistry.DOBUROKU_BUCKET,
            BucketItemRegistry.SAKE_BUCKET,
            BucketItemRegistry.SHOUCHU_BUCKET,
            BucketItemRegistry.BEER_BUCKET,
            BucketItemRegistry.WHISKEY_BUCKET,
            BucketItemRegistry.RED_WINE_BUCKET,
            BucketItemRegistry.WHITE_WINE_BUCKET,
            BucketItemRegistry.CHAMPAGNE_BUCKET,
            BucketItemRegistry.RUM_BUCKET,
            BucketItemRegistry.BRANDY_BUCKET,
            BucketItemRegistry.VODKA_BUCKET,
            BucketItemRegistry.LIQUEUR_BUCKET,
            BucketItemRegistry.COCOA_LIQUEUR_BUCKET,
            BucketItemRegistry.GIN_BUCKET,
            BucketItemRegistry.TEQUILA_BUCKET,
            BucketItemRegistry.GRAPE_FLUID_BUCKET,
            BucketItemRegistry.GREEN_GRAPE_FLUID_BUCKET,
            BucketItemRegistry.YEAST_LIQUID_BUCKET,
            BucketItemRegistry.MAPLE_SYRUP_BUCKET,
            BucketItemRegistry.HOT_SPRING_WATER_BUCKET
    };

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_buckets_registered(GameTestHelper helper) {
        for (RegistryObject<Item> obj : BUCKETS) {
            SakuraTestBase.assertItemRegistered(helper, obj, obj.getId().toString());
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_buckets_are_bucket_items(GameTestHelper helper) {
        for (RegistryObject<Item> obj : BUCKETS) {
            SakuraTestBase.assertTrue(helper, obj.get() instanceof BucketItem,
                    obj.getId() + " must be a BucketItem");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_buckets_return_empty_bucket(GameTestHelper helper) {
        for (RegistryObject<Item> obj : BUCKETS) {
            ItemStack stack = new ItemStack(obj.get());
            ItemStack remainder = stack.getCraftingRemainingItem();
            SakuraTestBase.assertFalse(helper, remainder.isEmpty(),
                    obj.getId() + " must have crafting remainder");
            SakuraTestBase.assertEquals(helper, Items.BUCKET, remainder.getItem(),
                    obj.getId() + " remainder should be minecraft:bucket");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void bucket_count_is_twenty_one(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper, 21, BUCKETS.length,
                "Expected 21 fluid buckets");
        helper.succeed();
    }
}
