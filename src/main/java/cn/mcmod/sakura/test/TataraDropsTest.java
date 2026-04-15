package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.machines.TataraBlock;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

/**
 * Regression tests for Bug 1: Tatara was dropping raw steel_ingot directly,
 * skipping the zuku -&gt; zuku_ingot -&gt; sagegane -&gt; iron_ingot progression.
 * After the fix the Tatara never drops steel_ingot; it drops zuku (default),
 * sagegane, or zuku_ingot depending on the harderIronDifficult config.
 */
@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class TataraDropsTest {

    private static LootParams.Builder baseParams(GameTestHelper helper, BlockPos pos) {
        ServerLevel level = helper.getLevel();
        return new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(helper.absolutePos(pos)))
                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY);
    }

    private static boolean containsItem(List<ItemStack> drops, Item item) {
        for (ItemStack s : drops) {
            if (s.is(item)) return true;
        }
        return false;
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void tatara_drops_resolve_to_zuku_by_default(GameTestHelper helper) {
        SakuraNormalItemSet material = TataraBlock.resolveTataraDrop();
        SakuraTestBase.assertTrue(helper,
                material == SakuraNormalItemSet.ZUKU
                        || material == SakuraNormalItemSet.SAGEGANE
                        || material == SakuraNormalItemSet.ZUKU_INGOT,
                "Tatara drop material must be one of zuku/sagegane/zuku_ingot, got " + material);
        SakuraTestBase.assertFalse(helper, material == SakuraNormalItemSet.STEEL_INGOT,
                "Tatara must not resolve to steel_ingot");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void unfinished_tatara_drops_itself(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        TataraBlock tatara = (TataraBlock) BlockRegistry.TATARA.get();
        BlockState unlit = tatara.defaultBlockState()
                .setValue(TataraBlock.LIT, false)
                .setValue(TataraBlock.TIMER, 0);
        helper.setBlock(pos, unlit);
        List<ItemStack> drops = tatara.getDrops(helper.getBlockState(pos), baseParams(helper, pos));
        SakuraTestBase.assertTrue(helper, drops.size() == 1, "unfinished tatara must drop exactly 1 item");
        SakuraTestBase.assertTrue(helper, drops.get(0).is(BlockRegistry.TATARA.get().asItem()),
                "unfinished tatara must drop the tatara block");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void finished_tatara_never_drops_steel_ingot(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        TataraBlock tatara = (TataraBlock) BlockRegistry.TATARA.get();
        BlockState finished = tatara.defaultBlockState()
                .setValue(TataraBlock.LIT, true)
                .setValue(TataraBlock.TIMER, 3);
        helper.setBlock(pos, finished);
        Item steelIngot = ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STEEL_INGOT).get();
        for (int i = 0; i < 64; ++i) {
            List<ItemStack> drops = tatara.getDrops(helper.getBlockState(pos), baseParams(helper, pos));
            SakuraTestBase.assertFalse(helper, containsItem(drops, steelIngot),
                    "finished tatara drops must not contain steel_ingot (iteration " + i + ")");
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void finished_tatara_drops_expected_material(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        TataraBlock tatara = (TataraBlock) BlockRegistry.TATARA.get();
        BlockState finished = tatara.defaultBlockState()
                .setValue(TataraBlock.LIT, true)
                .setValue(TataraBlock.TIMER, 3);
        helper.setBlock(pos, finished);
        Item expected = ItemRegistry.MATERIALS.get(TataraBlock.resolveTataraDrop()).get();
        Item tamahagane = ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get();
        boolean seenExpected = false;
        for (int i = 0; i < 128 && !seenExpected; ++i) {
            List<ItemStack> drops = tatara.getDrops(helper.getBlockState(pos), baseParams(helper, pos));
            for (ItemStack s : drops) {
                SakuraTestBase.assertTrue(helper, s.is(expected) || s.is(tamahagane),
                        "finished tatara dropped unexpected item: " + s);
                if (s.is(expected)) seenExpected = true;
            }
        }
        SakuraTestBase.assertTrue(helper, seenExpected,
                "finished tatara must drop the configured raw material (" + expected + ")");
        helper.succeed();
    }
}
