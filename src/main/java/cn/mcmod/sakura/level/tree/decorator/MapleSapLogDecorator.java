package cn.mcmod.sakura.level.tree.decorator;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.level.feature.SakuraFeatureRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

/**
 * Port of the 1.12.2 addSapLog() behavior from WorldGenMapleTree / WorldGenBigMaple.
 * Randomly replaces some trunk log blocks with maple sap log variants.
 * In 1.12.2, the sap log was placed at height 1 of the trunk (second block from base).
 * The MapleSpileBlock interacts with this block to extract maple sap.
 */
public class MapleSapLogDecorator extends TreeDecorator {

    public static final Codec<MapleSapLogDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("chance").forGetter(d -> d.chance)
            ).apply(instance, MapleSapLogDecorator::new));

    private final float chance;

    /**
     * @param chance Probability (0.0-1.0) that a given tree gets a sap log.
     *              In 1.12.2, about half of maple trees in a forest had sap logs.
     */
    public MapleSapLogDecorator(float chance) {
        this.chance = chance;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return SakuraFeatureRegistry.MAPLE_SAP_LOG_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource rand = context.random();
        if (rand.nextFloat() >= chance) return;
        if (context.logs().size() < 2) return;

        // Place sap log at position index 1 (second trunk log from base, like 1.12.2)
        BlockPos sapPos = context.logs().get(1);
        context.setBlock(sapPos, BlockRegistry.MAPLE_SAP_LOG.get().defaultBlockState());
    }
}
