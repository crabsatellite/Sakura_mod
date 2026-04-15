package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.SakuraLanternBlock;
import cn.mcmod.sakura.block.WindBellBlock;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Reflection on Bug 3: the only thing the earlier tests verified was that lantern
 * blocks were registered. They never checked WHICH base class was used, so six
 * blocks silently inheriting from vanilla LanternBlock (with its tiny hitbox)
 * were undetectable via registration tests. This file enumerates the whole
 * BLOCKS registry and asserts class-family invariants:
 *   1. No Sakura block inherits from vanilla net.minecraft...LanternBlock.
 *   2. Every registry name ending in "_lantern" is a SakuraLanternBlock.
 *   3. windbell is a WindBellBlock.
 * Adding a new lantern later that accidentally uses the vanilla class will
 * trigger (1) and (2) immediately.
 *
 * Catches the whole class of "wrong base class inherited, behaviour silently broken" bugs.
 */
@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class BlockClassFamilyTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void no_sakura_block_uses_vanilla_lantern_block(GameTestHelper helper) {
        List<String> violations = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> holder : BlockRegistry.BLOCKS.getEntries()) {
            Block block = holder.get();
            if (block instanceof LanternBlock && !(block instanceof SakuraLanternBlock)) {
                ResourceLocation id = holder.getId();
                violations.add(id + " (" + block.getClass().getName() + ")");
            }
        }
        SakuraTestBase.assertTrue(helper, violations.isEmpty(),
                "Sakura blocks must not instantiate vanilla LanternBlock. Violations: " + violations);
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void all_lantern_named_blocks_are_sakura_lantern_block(GameTestHelper helper) {
        List<String> violations = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> holder : BlockRegistry.BLOCKS.getEntries()) {
            String path = holder.getId().getPath();
            if (!path.endsWith("_lantern")) continue;
            Block block = holder.get();
            if (!(block instanceof SakuraLanternBlock)) {
                violations.add(path + " -> " + block.getClass().getSimpleName());
            }
        }
        SakuraTestBase.assertTrue(helper, violations.isEmpty(),
                "Every block with id ending in _lantern must be SakuraLanternBlock. Violations: "
                        + violations);
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void windbell_is_wind_bell_block(GameTestHelper helper) {
        Block block = BlockRegistry.WINDBELL.get();
        SakuraTestBase.assertTrue(helper, block instanceof WindBellBlock,
                "windbell must be WindBellBlock, got " + block.getClass().getName());
        SakuraTestBase.assertFalse(helper, block instanceof LanternBlock,
                "windbell must not inherit from vanilla LanternBlock");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void every_block_has_nonnull_class(GameTestHelper helper) {
        for (DeferredHolder<Block, ? extends Block> holder : BlockRegistry.BLOCKS.getEntries()) {
            SakuraTestBase.assertNotNull(helper, holder.get(),
                    "Block " + holder.getId() + " resolved to null");
        }
        helper.succeed();
    }
}
