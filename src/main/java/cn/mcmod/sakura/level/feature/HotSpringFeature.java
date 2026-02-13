package cn.mcmod.sakura.level.feature;

import cn.mcmod.sakura.block.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


/**
 * Port of the 1.12.2 WorldGenHotSpring.
 * Carves an ellipsoidal basin (16x8x16 working area), fills the lower half with hot spring water,
 * clears air above, restores grass on exposed dirt, and lines edges with stone.
 * This is essentially vanilla's lake generation algorithm adapted for hot spring water.
 */
public class HotSpringFeature extends Feature<NoneFeatureConfiguration> {

    public HotSpringFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        var rand = context.random();

        // Move to surface, then down 4 (like 1.12.2)
        BlockPos pos = origin;
        while (pos.getY() > 5 && level.isEmptyBlock(pos)) {
            pos = pos.below();
        }
        pos = pos.below(4);

        // Ellipsoid carving array: 16x16x8 working volume
        boolean[] carved = new boolean[2048];
        int ellipsoidCount = rand.nextInt(4) + 4;

        // Generate random ellipsoids
        for (int e = 0; e < ellipsoidCount; e++) {
            double rx = rand.nextDouble() * 6.0 + 3.0;
            double ry = rand.nextDouble() * 4.0 + 2.0;
            double rz = rand.nextDouble() * 6.0 + 3.0;
            double cx = rand.nextDouble() * (16.0 - rx - 2.0) + 1.0 + rx / 2.0;
            double cy = rand.nextDouble() * (8.0 - ry - 4.0) + 2.0 + ry / 2.0;
            double cz = rand.nextDouble() * (16.0 - rz - 2.0) + 1.0 + rz / 2.0;

            for (int x = 1; x < 15; x++) {
                for (int z = 1; z < 15; z++) {
                    for (int y = 1; y < 7; y++) {
                        double dx = (x - cx) / (rx / 2.0);
                        double dy = (y - cy) / (ry / 2.0);
                        double dz = (z - cz) / (rz / 2.0);
                        if (dx * dx + dy * dy + dz * dz < 1.0) {
                            carved[(x * 16 + z) * 8 + y] = true;
                        }
                    }
                }
            }
        }

        // Validation pass: check edges for conflicts
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 8; y++) {
                    boolean isEdge = !carved[(x * 16 + z) * 8 + y]
                            && (x < 15 && carved[((x + 1) * 16 + z) * 8 + y]
                            || x > 0 && carved[((x - 1) * 16 + z) * 8 + y]
                            || z < 15 && carved[(x * 16 + z + 1) * 8 + y]
                            || z > 0 && carved[(x * 16 + (z - 1)) * 8 + y]
                            || y < 7 && carved[(x * 16 + z) * 8 + y + 1]
                            || y > 0 && carved[(x * 16 + z) * 8 + (y - 1)]);

                    if (isEdge) {
                        BlockState state = level.getBlockState(pos.offset(x, y, z));
                        if (y >= 4 && !state.getFluidState().isEmpty()) {
                            return false;
                        }
                        if (y < 4 && !state.isSolid()
                                && !state.is(BlockRegistry.HOT_SPRING_WATER.get())) {
                            return false;
                        }
                    }
                }
            }
        }

        // Carving pass: fill with water (lower) or air (upper)
        BlockState hotSpringState = BlockRegistry.HOT_SPRING_WATER.get().defaultBlockState();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 8; y++) {
                    if (carved[(x * 16 + z) * 8 + y]) {
                        level.setBlock(pos.offset(x, y, z),
                                y >= 4 ? Blocks.AIR.defaultBlockState() : hotSpringState, 2);
                    }
                }
            }
        }

        // Grass restoration: convert dirt to grass where sky-exposed above carved area
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 4; y < 8; y++) {
                    if (carved[(x * 16 + z) * 8 + y]) {
                        BlockPos below = pos.offset(x, y - 1, z);
                        if (level.getBlockState(below).is(Blocks.DIRT)
                                && level.canSeeSky(pos.offset(x, y, z))) {
                            level.setBlock(below, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        // Stone lining pass: replace solid edge blocks with stone
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 8; y++) {
                    boolean isEdge = !carved[(x * 16 + z) * 8 + y]
                            && (x < 15 && carved[((x + 1) * 16 + z) * 8 + y]
                            || x > 0 && carved[((x - 1) * 16 + z) * 8 + y]
                            || z < 15 && carved[(x * 16 + z + 1) * 8 + y]
                            || z > 0 && carved[(x * 16 + (z - 1)) * 8 + y]
                            || y < 7 && carved[(x * 16 + z) * 8 + y + 1]
                            || y > 0 && carved[(x * 16 + z) * 8 + (y - 1)]);

                    if (isEdge && (y < 4 || rand.nextInt(2) != 0)
                            && level.getBlockState(pos.offset(x, y, z)).isSolid()) {
                        level.setBlock(pos.offset(x, y, z), Blocks.STONE.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
