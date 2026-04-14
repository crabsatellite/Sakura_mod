package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import com.mojang.serialization.MapCodec;

@SuppressWarnings("deprecation")
public class MapleTreeSapLogBlock extends RotatedPillarBlock {
    public static final MapCodec<MapleTreeSapLogBlock> CODEC = simpleCodec(p -> new MapleTreeSapLogBlock());

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    /** Sap age 0-5. At 5 the log is exhausted and no more sap can be collected. Matches 1.12.2 SAP_AGE. */
    public static final IntegerProperty EXHAUSTION = IntegerProperty.create("exhaustion", 0, 5);
    public static final int MAX_SAP_AGE = 5;

    public MapleTreeSapLogBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(
                state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD
                        : MapColor.PODZOL))
                .strength(2.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.defaultBlockState().setValue(EXHAUSTION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, EXHAUSTION);
    }

    /** Returns true if the log is fully exhausted (sap age >= 5). */
    public static boolean isExhausted(BlockState state) {
        return state.getValue(EXHAUSTION) >= MAX_SAP_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.randomTick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1) || isExhausted(state)) {
            return;
        }
        if (rand.nextInt(36000) == 0) {
            worldIn.setBlockAndUpdate(pos, BlockRegistry.MAPLE_LOG.get().withPropertiesOf(state));
        }
    }
}
