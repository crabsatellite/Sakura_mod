package cn.mcmod.sakura.level.tree.decorator;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.level.feature.SakuraFeatureRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Port of the 1.12.2 chestnut burr placement from WorldGenMapleTreeGreen.
 * Places chestnut burr blocks hanging below some leaf blocks (25% chance per leaf position).
 * The burr is placed one block below a leaf, only if that position and the position
 * below it are both air (to avoid floating/embedded burrs).
 */
public class ChestnutBurrDecorator extends TreeDecorator {

    public static final MapCodec<ChestnutBurrDecorator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("probability").forGetter(d -> d.probability)
            ).apply(instance, ChestnutBurrDecorator::new));

    private final float probability;

    /**
     * @param probability Chance per leaf block of spawning a chestnut burr below it.
     *                   1.12.2 used rand.nextInt(4)==0, so ~0.25.
     */
    public ChestnutBurrDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return SakuraFeatureRegistry.CHESTNUT_BURR_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource rand = context.random();

        for (BlockPos leafPos : context.leaves()) {
            if (rand.nextFloat() >= probability) continue;

            BlockPos burrPos = leafPos.below();
            BlockPos belowBurrPos = burrPos.below();

            // Only place if the block below the leaf is air, and the block below that is also air
            // (avoids placing burrs at ground level or inside other blocks, matching 1.12.2 logic)
            if (context.isAir(burrPos) && context.isAir(belowBurrPos)) {
                context.setBlock(burrPos, BlockRegistry.CHESTNUT_BURR.get().defaultBlockState());
            }
        }
    }
}
