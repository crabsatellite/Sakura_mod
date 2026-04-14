package cn.mcmod.sakura.level.tree;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

/**
 * Factory for creating TreeGrower instances for maple saplings.
 * In 1.21, TreeGrower is a final record and cannot be extended.
 * Each maple color variant gets its own TreeGrower instance.
 */
public class MapleTreeGrower {

    public static TreeGrower create(String name,
                                     ResourceKey<ConfiguredFeature<?, ?>> tree,
                                     ResourceKey<ConfiguredFeature<?, ?>> fancyTree,
                                     ResourceKey<ConfiguredFeature<?, ?>> bigTree) {
        // tree = normal tree, fancyTree = secondary variant (10% chance),
        // bigTree = mega tree (2x2 sapling pattern)
        return new TreeGrower(name, 0.1f,
                Optional.of(bigTree), Optional.empty(),
                Optional.of(tree), Optional.of(fancyTree),
                Optional.empty(), Optional.empty());
    }

    private MapleTreeGrower() {}
}
