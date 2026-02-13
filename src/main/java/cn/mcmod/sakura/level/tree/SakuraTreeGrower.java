package cn.mcmod.sakura.level.tree;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SakuraTreeGrower extends AbstractTreeGrower {

	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean pHasFlowers) {
        // In 1.12.2, sakura saplings grew big trees 7/8 of the time, small trees 1/8
        if (random.nextInt(8) == 0) {
            return SakuraTreeFeatures.SAKURA_KEY;
        } else {
            return SakuraTreeFeatures.BIG_SAKURA_KEY;
        }
	}

}
