package cn.mcmod.sakura.level.tree.decorator;

import cn.mcmod.sakura.level.feature.SakuraFeatureRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

/**
 * Port of 1.12.2 fallenLeaves() method from WorldGenMapleTree / WorldGenBigMaple.
 * Places carpet-like fallen leaf blocks on the ground within a radius of the trunk base.
 * Uses TreeDecorator.Context to find valid positions (air above solid ground).
 */
public class FallenLeavesDecorator extends TreeDecorator {

    public static final Codec<FallenLeavesDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fallen_block").forGetter(d -> d.fallenBlock),
                    Codec.INT.fieldOf("radius_x").forGetter(d -> d.radiusX),
                    Codec.INT.fieldOf("radius_z").forGetter(d -> d.radiusZ),
                    Codec.INT.fieldOf("search_up").forGetter(d -> d.searchUp),
                    Codec.INT.fieldOf("search_down").forGetter(d -> d.searchDown)
            ).apply(instance, FallenLeavesDecorator::new));

    private final Block fallenBlock;
    private final int radiusX;
    private final int radiusZ;
    private final int searchUp;
    private final int searchDown;

    public FallenLeavesDecorator(Block fallenBlock, int radiusX, int radiusZ, int searchUp, int searchDown) {
        this.fallenBlock = fallenBlock;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
        this.searchUp = searchUp;
        this.searchDown = searchDown;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return SakuraFeatureRegistry.FALLEN_LEAVES_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource rand = context.random();
        // Find the lowest trunk log position as base
        if (context.logs().isEmpty()) return;

        BlockPos base = context.logs().get(0);
        int bx = base.getX();
        int by = base.getY();
        int bz = base.getZ();

        BlockState fallenState = fallenBlock.defaultBlockState();
        for (int xx = bx - radiusX; xx <= bx + radiusX; xx++) {
            for (int zz = bz - radiusZ; zz <= bz + radiusZ; zz++) {
                // Skip corners (like 1.12.2)
                if ((xx == bx - radiusX && zz == bz - radiusZ)
                        || (xx == bx + radiusX && zz == bz - radiusZ)
                        || (xx == bx - radiusX && zz == bz + radiusZ)) {
                    continue;
                }

                // Inner area always places, outer ring has 50% chance
                boolean isInner = xx >= bx - radiusX + 1 && xx <= bx + radiusX - 1
                        && zz >= bz - radiusZ + 1 && zz <= bz + radiusZ - 1;
                if (!isInner && rand.nextInt(2) == 0) {
                    continue;
                }

                // Scan downward from (base+searchUp) to find a valid spot:
                // air block sitting on top of a solid ground block
                boolean placed = false;
                for (int yy = by + searchUp; yy >= by - searchDown && !placed; yy--) {
                    BlockPos checkPos = new BlockPos(xx, yy, zz);
                    BlockPos belowPos = checkPos.below();

                    if (context.isAir(checkPos)) {
                        BlockState belowState = ((net.minecraft.world.level.LevelAccessor)context.level()).getBlockState(belowPos);
                        // Place on natural ground surfaces where leaves would accumulate
                        if (belowState.is(Blocks.GRASS_BLOCK) || belowState.is(Blocks.DIRT)
                                || belowState.is(Blocks.PODZOL) || belowState.is(Blocks.COARSE_DIRT)
                                || belowState.is(Blocks.MOSS_BLOCK)) {
                            context.setBlock(checkPos, fallenState);
                            placed = true;
                        }
                    } else {
                        // Hit a non-air block: stop scanning downward
                        break;
                    }
                }

            }
        }
    }
}
