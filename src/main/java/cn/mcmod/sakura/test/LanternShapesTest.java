package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.SakuraLanternBlock;
import cn.mcmod.sakura.block.WindBellBlock;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Regression tests for Bug 3: lantern/windbell block shapes.
 * Before the fix every lantern was a vanilla LanternBlock with an incorrect small
 * hitbox, and windbell shared that hitbox instead of a hanging-bell shape.
 */
@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class LanternShapesTest {

    private static final BlockPos POS = new BlockPos(1, 1, 1);

    private static VoxelShape placeAndGetShape(GameTestHelper helper, Block block) {
        helper.setBlock(POS, block.defaultBlockState());
        BlockState state = helper.getBlockState(POS);
        return state.getShape(helper.getLevel(), helper.absolutePos(POS));
    }

    private static void assertNotFullCube(GameTestHelper helper, VoxelShape shape, String name) {
        SakuraTestBase.assertFalse(helper, shape.isEmpty(), name + " shape must not be empty");
        SakuraTestBase.assertFalse(helper, Shapes.block().equals(shape), name + " shape must not be a full cube");
    }

    private static void assertBoundsClose(GameTestHelper helper, VoxelShape shape,
                                          double minX, double minY, double minZ,
                                          double maxX, double maxY, double maxZ,
                                          String name) {
        AABB b = shape.bounds();
        double tol = 0.001D;
        SakuraTestBase.assertTrue(helper, Math.abs(b.minX - minX) < tol, name + " minX=" + b.minX);
        SakuraTestBase.assertTrue(helper, Math.abs(b.minY - minY) < tol, name + " minY=" + b.minY);
        SakuraTestBase.assertTrue(helper, Math.abs(b.minZ - minZ) < tol, name + " minZ=" + b.minZ);
        SakuraTestBase.assertTrue(helper, Math.abs(b.maxX - maxX) < tol, name + " maxX=" + b.maxX);
        SakuraTestBase.assertTrue(helper, Math.abs(b.maxY - maxY) < tol, name + " maxY=" + b.maxY);
        SakuraTestBase.assertTrue(helper, Math.abs(b.maxZ - maxZ) < tol, name + " maxZ=" + b.maxZ);
    }

    private static void assertIsSakuraLantern(GameTestHelper helper, DeferredHolder<Block, Block> holder, String name) {
        Block block = holder.get();
        SakuraTestBase.assertTrue(helper, block instanceof SakuraLanternBlock,
                name + " must be SakuraLanternBlock, got " + block.getClass().getSimpleName());
        SakuraTestBase.assertFalse(helper, block instanceof LanternBlock,
                name + " must not be vanilla LanternBlock");
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void stone_lantern_uses_sakura_lantern_block(GameTestHelper helper) {
        assertIsSakuraLantern(helper, BlockRegistry.STONE_LANTERN, "stone_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cobblestone_lantern_uses_sakura_lantern_block(GameTestHelper helper) {
        assertIsSakuraLantern(helper, BlockRegistry.COBBLESTONE_LANTERN, "cobblestone_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void mossy_stone_lantern_uses_sakura_lantern_block(GameTestHelper helper) {
        assertIsSakuraLantern(helper, BlockRegistry.MOSSY_STONE_LANTERN, "mossy_stone_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void red_lantern_uses_sakura_lantern_block(GameTestHelper helper) {
        assertIsSakuraLantern(helper, BlockRegistry.RED_LANTERN, "red_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void white_lantern_uses_sakura_lantern_block(GameTestHelper helper) {
        assertIsSakuraLantern(helper, BlockRegistry.WHITE_LANTERN, "white_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void bamboo_lantern_uses_sakura_lantern_block(GameTestHelper helper) {
        assertIsSakuraLantern(helper, BlockRegistry.BAMBOO_LANTERN, "bamboo_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void stone_lantern_is_tall_pedestal(GameTestHelper helper) {
        VoxelShape shape = placeAndGetShape(helper, BlockRegistry.STONE_LANTERN.get());
        assertNotFullCube(helper, shape, "stone_lantern");
        assertBoundsClose(helper, shape,
                1.0D / 16D, 0.0D, 1.0D / 16D,
                15.0D / 16D, 1.0D, 15.0D / 16D,
                "stone_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void bamboo_lantern_is_half_height(GameTestHelper helper) {
        VoxelShape shape = placeAndGetShape(helper, BlockRegistry.BAMBOO_LANTERN.get());
        assertNotFullCube(helper, shape, "bamboo_lantern");
        assertBoundsClose(helper, shape,
                5.0D / 16D, 0.0D, 5.0D / 16D,
                11.0D / 16D, 8.0D / 16D, 11.0D / 16D,
                "bamboo_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void red_lantern_is_tall_slender(GameTestHelper helper) {
        VoxelShape shape = placeAndGetShape(helper, BlockRegistry.RED_LANTERN.get());
        assertNotFullCube(helper, shape, "red_lantern");
        assertBoundsClose(helper, shape,
                5.5D / 16D, 0.0D, 5.5D / 16D,
                10.5D / 16D, 1.0D, 10.5D / 16D,
                "red_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void white_lantern_is_tall_slender(GameTestHelper helper) {
        VoxelShape shape = placeAndGetShape(helper, BlockRegistry.WHITE_LANTERN.get());
        assertNotFullCube(helper, shape, "white_lantern");
        assertBoundsClose(helper, shape,
                5.5D / 16D, 0.0D, 5.5D / 16D,
                10.5D / 16D, 1.0D, 10.5D / 16D,
                "white_lantern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_uses_wind_bell_block(GameTestHelper helper) {
        Block block = BlockRegistry.WINDBELL.get();
        SakuraTestBase.assertTrue(helper, block instanceof WindBellBlock,
                "windbell must be WindBellBlock, got " + block.getClass().getSimpleName());
        SakuraTestBase.assertFalse(helper, block instanceof LanternBlock,
                "windbell must not be vanilla LanternBlock");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_has_hanging_bell_shape(GameTestHelper helper) {
        VoxelShape shape = placeAndGetShape(helper, BlockRegistry.WINDBELL.get());
        assertNotFullCube(helper, shape, "windbell");
        assertBoundsClose(helper, shape,
                6.0D / 16D, 2.75D / 16D, 6.0D / 16D,
                10.0D / 16D, 1.0D, 10.0D / 16D,
                "windbell");
        helper.succeed();
    }
}
