package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.crops.GrapeLeavesBlock;
import cn.mcmod.sakura.block.crops.GrapeVineBlock;
import cn.mcmod.sakura.block.crops.WildCropBlock;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class CropsBehaviorTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_vine_is_bonemealable(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper,
                BlockRegistry.GRAPE_VINE.get() instanceof BonemealableBlock,
                "GRAPE_VINE must implement BonemealableBlock");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_leaves_is_bonemealable(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper,
                BlockRegistry.GRAPE_LEAVES.get() instanceof BonemealableBlock,
                "GRAPE_LEAVES must implement BonemealableBlock");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_vine_default_age_is_zero(GameTestHelper helper) {
        BlockState def = BlockRegistry.GRAPE_VINE.get().defaultBlockState();
        SakuraTestBase.assertEquals(helper, 0, (int) def.getValue(GrapeVineBlock.AGE),
                "GRAPE_VINE default AGE should be 0");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_leaves_default_age_is_zero(GameTestHelper helper) {
        BlockState def = BlockRegistry.GRAPE_LEAVES.get().defaultBlockState();
        SakuraTestBase.assertEquals(helper, 0, (int) def.getValue(GrapeLeavesBlock.AGE),
                "GRAPE_LEAVES default AGE should be 0");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_vine_bonemeal_valid_below_max_age(GameTestHelper helper) {
        BonemealableBlock bm = (BonemealableBlock) BlockRegistry.GRAPE_VINE.get();
        BlockState young = BlockRegistry.GRAPE_VINE.get().defaultBlockState()
                .setValue(GrapeVineBlock.AGE, 3);
        SakuraTestBase.assertTrue(helper,
                bm.isValidBonemealTarget(helper.getLevel(), helper.absolutePos(new net.minecraft.core.BlockPos(0, 0, 0)), young),
                "GRAPE_VINE bonemeal should be valid at age 3");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_vine_bonemeal_invalid_at_max_age(GameTestHelper helper) {
        BonemealableBlock bm = (BonemealableBlock) BlockRegistry.GRAPE_VINE.get();
        BlockState mature = BlockRegistry.GRAPE_VINE.get().defaultBlockState()
                .setValue(GrapeVineBlock.AGE, 7);
        SakuraTestBase.assertFalse(helper,
                bm.isValidBonemealTarget(helper.getLevel(), helper.absolutePos(new net.minecraft.core.BlockPos(0, 0, 0)), mature),
                "GRAPE_VINE bonemeal should be invalid at age 7");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_leaves_bonemeal_valid_below_max_age(GameTestHelper helper) {
        BonemealableBlock bm = (BonemealableBlock) BlockRegistry.GRAPE_LEAVES.get();
        BlockState young = BlockRegistry.GRAPE_LEAVES.get().defaultBlockState()
                .setValue(GrapeLeavesBlock.AGE, 2);
        SakuraTestBase.assertTrue(helper,
                bm.isValidBonemealTarget(helper.getLevel(), helper.absolutePos(new net.minecraft.core.BlockPos(0, 0, 0)), young),
                "GRAPE_LEAVES bonemeal should be valid at age 2");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_leaves_bonemeal_invalid_at_max_age(GameTestHelper helper) {
        BonemealableBlock bm = (BonemealableBlock) BlockRegistry.GRAPE_LEAVES.get();
        BlockState mature = BlockRegistry.GRAPE_LEAVES.get().defaultBlockState()
                .setValue(GrapeLeavesBlock.AGE, 7);
        SakuraTestBase.assertFalse(helper,
                bm.isValidBonemealTarget(helper.getLevel(), helper.absolutePos(new net.minecraft.core.BlockPos(0, 0, 0)), mature),
                "GRAPE_LEAVES bonemeal should be invalid at age 7");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_vine_randomTicks_enabled(GameTestHelper helper) {
        BlockState def = BlockRegistry.GRAPE_VINE.get().defaultBlockState();
        SakuraTestBase.assertTrue(helper, def.isRandomlyTicking(),
                "GRAPE_VINE must be randomly ticking for growth");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_leaves_randomTicks_enabled(GameTestHelper helper) {
        BlockState def = BlockRegistry.GRAPE_LEAVES.get().defaultBlockState();
        SakuraTestBase.assertTrue(helper, def.isRandomlyTicking(),
                "GRAPE_LEAVES must be randomly ticking for growth");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void rice_crop_is_crop_block(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper,
                BlockRegistry.RICE_CROP.get() instanceof CropBlock,
                "RICE_CROP must extend CropBlock");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_registered_crop_blocks_break_instantly(GameTestHelper helper) {
        BlockPos pos = helper.absolutePos(new BlockPos(0, 0, 0));
        for (DeferredHolder<Block, ? extends Block> crop : cropBlocks()) {
            BlockState state = crop.get().defaultBlockState();
            SakuraTestBase.assertEquals(helper, 0.0F,
                    state.getDestroySpeed(helper.getLevel(), pos),
                    crop.getId() + " should break instantly like vanilla crops");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void wild_pepper_is_wild_crop_block(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper,
                BlockRegistry.WILD_PEPPER.get() instanceof WildCropBlock,
                "WILD_PEPPER must be WildCropBlock (so it survives on natural ground)");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void wild_vanilla_is_wild_crop_block(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper,
                BlockRegistry.WILD_VANILLA.get() instanceof WildCropBlock,
                "WILD_VANILLA must be WildCropBlock");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void grape_vine_age_range_is_zero_to_seven(GameTestHelper helper) {
        BlockState zero = BlockRegistry.GRAPE_VINE.get().defaultBlockState()
                .setValue(GrapeVineBlock.AGE, 0);
        BlockState seven = BlockRegistry.GRAPE_VINE.get().defaultBlockState()
                .setValue(GrapeVineBlock.AGE, 7);
        SakuraTestBase.assertEquals(helper, 0, (int) zero.getValue(GrapeVineBlock.AGE),
                "GRAPE_VINE must accept AGE=0");
        SakuraTestBase.assertEquals(helper, 7, (int) seven.getValue(GrapeVineBlock.AGE),
                "GRAPE_VINE must accept AGE=7");
        helper.succeed();
    }

    private static List<DeferredHolder<Block, ? extends Block>> cropBlocks() {
        return List.of(
                BlockRegistry.RICE_CROP_ROOT,
                BlockRegistry.RICE_CROP,
                BlockRegistry.CABBAGE_CROP,
                BlockRegistry.RADISH_CROP,
                BlockRegistry.ONION_CROP,
                BlockRegistry.REDBEAN_CROP,
                BlockRegistry.SOYBEAN_CROP,
                BlockRegistry.RAPESEED_CROP,
                BlockRegistry.BUCKWHEAT_CROP,
                BlockRegistry.TARO_CROP,
                BlockRegistry.TOMATO_CROP,
                BlockRegistry.EGGPLANT_CROP,
                BlockRegistry.PEPPER_CROP,
                BlockRegistry.VANILLA_CROP,
                BlockRegistry.GRAPE_CROP,
                BlockRegistry.HOPS_CROP,
                BlockRegistry.SEAWEED_CROP
        );
    }
}
