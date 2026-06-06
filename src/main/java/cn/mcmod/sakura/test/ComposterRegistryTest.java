package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ComposterRegistry;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import cn.mcmod_mmf.mmlib.item.IFoodLike;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class ComposterRegistryTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_crop_inputs_are_compostable(GameTestHelper helper) {
        ComposterRegistry.registerCompost();
        for (var supplier : ComposterRegistry.CROP_INPUTS) {
            Item item = supplier.get();
            SakuraTestBase.assertTrue(helper, ComposterBlock.COMPOSTABLES.containsKey(item),
                    item + " should be registered as compostable");
            Float chance = ComposterBlock.COMPOSTABLES.get(item);
            SakuraTestBase.assertNotNull(helper, chance,
                    item + " should have a compost chance");
            SakuraTestBase.assertEquals(helper, ComposterRegistry.SEED_COMPOST_CHANCE, chance.floatValue(),
                    item + " should use the crop/seed compost chance");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void positive_chance_foods_are_compostable(GameTestHelper helper) {
        ComposterRegistry.registerCompost();
        for (var entry : FoodRegistry.ITEMS.getEntries()) {
            Item item = entry.get();
            if (!(item instanceof IFoodLike food)) {
                continue;
            }
            float expected = food.getFoodInfo().getCompostChance();
            if (expected <= 0.0F) {
                continue;
            }
            SakuraTestBase.assertTrue(helper, ComposterBlock.COMPOSTABLES.containsKey(item),
                    entry.getId() + " has positive compost chance but was not registered");
            SakuraTestBase.assertEquals(helper, expected, ComposterBlock.COMPOSTABLES.get(item).floatValue(),
                    entry.getId() + " compost chance mismatch");
        }
        helper.succeed();
    }
}
