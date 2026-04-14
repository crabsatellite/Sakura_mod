package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.serialization.MapCodec;
import cn.mcmod_mmf.mmlib.block.FacingSlab;

public class TatamiSlabBlock extends FacingSlab {
    public static final MapCodec<TatamiSlabBlock> CODEC = simpleCodec(TatamiSlabBlock::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    public TatamiSlabBlock(Properties prop) {
        super(prop.randomTicks());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.randomTick(state, worldIn, pos, rand);
        if (worldIn.isDay() && worldIn.canSeeSky(pos)) {
            worldIn.setBlockAndUpdate(pos, BlockRegistry.TATAMI_SLAB_SUNBURNT.get().withPropertiesOf(state));
        }
    }

}
