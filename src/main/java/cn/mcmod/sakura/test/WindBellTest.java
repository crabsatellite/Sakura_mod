package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

/**
 * Regression tests for Bug 3 (windbell): placement requires a sturdy block above,
 * collision is empty (entities pass through), and removing the supporting block
 * breaks the bell.
 */
@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class WindBellTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_collision_shape_is_empty(GameTestHelper helper) {
        BlockPos bellPos = new BlockPos(1, 1, 1);
        BlockPos abovePos = bellPos.above();
        helper.setBlock(abovePos, Blocks.STONE.defaultBlockState());
        helper.setBlock(bellPos, BlockRegistry.WINDBELL.get().defaultBlockState());
        BlockState state = helper.getBlockState(bellPos);
        VoxelShape collision = state.getCollisionShape(helper.getLevel(), helper.absolutePos(bellPos), CollisionContext.empty());
        SakuraTestBase.assertTrue(helper, collision.isEmpty(),
                "windbell collision shape must be empty so entities pass through");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_survives_with_block_above(GameTestHelper helper) {
        BlockPos bellPos = new BlockPos(1, 1, 1);
        BlockPos abovePos = bellPos.above();
        helper.setBlock(abovePos, Blocks.STONE.defaultBlockState());
        BlockState bellState = BlockRegistry.WINDBELL.get().defaultBlockState();
        boolean canSurvive = bellState.canSurvive(helper.getLevel(), helper.absolutePos(bellPos));
        SakuraTestBase.assertTrue(helper, canSurvive,
                "windbell must survive when a sturdy block is above");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_fails_to_survive_without_block_above(GameTestHelper helper) {
        BlockPos bellPos = new BlockPos(1, 1, 1);
        BlockPos abovePos = bellPos.above();
        helper.setBlock(abovePos, Blocks.AIR.defaultBlockState());
        BlockState bellState = BlockRegistry.WINDBELL.get().defaultBlockState();
        boolean canSurvive = bellState.canSurvive(helper.getLevel(), helper.absolutePos(bellPos));
        SakuraTestBase.assertFalse(helper, canSurvive,
                "windbell must not survive without a sturdy block above");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_breaks_when_support_removed(GameTestHelper helper) {
        BlockPos bellPos = new BlockPos(1, 1, 1);
        BlockPos abovePos = bellPos.above();
        helper.setBlock(abovePos, Blocks.STONE.defaultBlockState());
        helper.setBlock(bellPos, BlockRegistry.WINDBELL.get().defaultBlockState());
        SakuraTestBase.assertTrue(helper, helper.getBlockState(bellPos).is(BlockRegistry.WINDBELL.get()),
                "windbell should be placed before support removal");
        helper.setBlock(abovePos, Blocks.AIR.defaultBlockState());
        BlockState after = helper.getBlockState(bellPos);
        SakuraTestBase.assertFalse(helper, after.is(BlockRegistry.WINDBELL.get()),
                "windbell must be removed (broken) when the block above is gone");
        helper.succeed();
    }
}
