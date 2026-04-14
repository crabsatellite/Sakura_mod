package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import cn.mcmod_mmf.mmlib.block.BaseHorizonBlock;
import com.mojang.serialization.MapCodec;

public class TatamiBlock extends BaseHorizonBlock {
    public static final MapCodec<TatamiBlock> CODEC = simpleCodec(TatamiBlock::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }


    public TatamiBlock(Properties prop) {
        super(prop.randomTicks());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.randomTick(state, worldIn, pos, rand);
        if (worldIn.isDay() && worldIn.canSeeSky(pos.above())) {
            worldIn.setBlockAndUpdate(pos, BlockRegistry.TATAMI_SUNBURNT.get().withPropertiesOf(state));
        }
    }

}
