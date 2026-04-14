package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.SakuraCampfireBlock;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class InteractiveFireTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_idle_has_lit_false_by_default(GameTestHelper helper) {
        BlockState idle = BlockRegistry.CAMPFIRE_IDLE.get().defaultBlockState();
        SakuraTestBase.assertFalse(helper, idle.getValue(BlockStateProperties.LIT),
                "CAMPFIRE_IDLE default state must have LIT=false");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_lit_has_lit_true_by_default(GameTestHelper helper) {
        BlockState lit = BlockRegistry.CAMPFIRE_LIT.get().defaultBlockState();
        SakuraTestBase.assertTrue(helper, lit.getValue(BlockStateProperties.LIT),
                "CAMPFIRE_LIT default state must have LIT=true");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_lit_emits_full_light(GameTestHelper helper) {
        BlockState lit = BlockRegistry.CAMPFIRE_LIT.get().defaultBlockState();
        SakuraTestBase.assertEquals(helper, 15, lit.getLightEmission(),
                "CAMPFIRE_LIT must emit light level 15");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_idle_emits_no_light(GameTestHelper helper) {
        BlockState idle = BlockRegistry.CAMPFIRE_IDLE.get().defaultBlockState();
        SakuraTestBase.assertEquals(helper, 0, idle.getLightEmission(),
                "CAMPFIRE_IDLE must emit no light");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_idle_has_lit_false_by_default(GameTestHelper helper) {
        BlockState idle = BlockRegistry.CAMPFIRE_POT_IDLE.get().defaultBlockState();
        SakuraTestBase.assertFalse(helper, idle.getValue(BlockStateProperties.LIT),
                "CAMPFIRE_POT_IDLE default state must have LIT=false");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_lit_has_lit_true_by_default(GameTestHelper helper) {
        BlockState lit = BlockRegistry.CAMPFIRE_POT_LIT.get().defaultBlockState();
        SakuraTestBase.assertTrue(helper, lit.getValue(BlockStateProperties.LIT),
                "CAMPFIRE_POT_LIT default state must have LIT=true");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_lit_emits_full_light(GameTestHelper helper) {
        BlockState lit = BlockRegistry.CAMPFIRE_POT_LIT.get().defaultBlockState();
        SakuraTestBase.assertEquals(helper, 15, lit.getLightEmission(),
                "CAMPFIRE_POT_LIT must emit light level 15");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_idle_emits_no_light(GameTestHelper helper) {
        BlockState idle = BlockRegistry.CAMPFIRE_POT_IDLE.get().defaultBlockState();
        SakuraTestBase.assertEquals(helper, 0, idle.getLightEmission(),
                "CAMPFIRE_POT_IDLE must emit no light");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_has_facing_property(GameTestHelper helper) {
        BlockState idle = BlockRegistry.CAMPFIRE_IDLE.get().defaultBlockState();
        SakuraTestBase.assertTrue(helper,
                idle.hasProperty(SakuraCampfireBlock.FACING),
                "CAMPFIRE_IDLE must have FACING property");
        SakuraTestBase.assertTrue(helper,
                idle.hasProperty(SakuraCampfireBlock.LIT),
                "CAMPFIRE_IDLE must have LIT property");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_has_facing_and_lit_properties(GameTestHelper helper) {
        BlockState lit = BlockRegistry.CAMPFIRE_POT_LIT.get().defaultBlockState();
        SakuraTestBase.assertTrue(helper,
                lit.hasProperty(BlockStateProperties.HORIZONTAL_FACING),
                "CAMPFIRE_POT_LIT must have HORIZONTAL_FACING property");
        SakuraTestBase.assertTrue(helper,
                lit.hasProperty(BlockStateProperties.LIT),
                "CAMPFIRE_POT_LIT must have LIT property");
        helper.succeed();
    }
}
