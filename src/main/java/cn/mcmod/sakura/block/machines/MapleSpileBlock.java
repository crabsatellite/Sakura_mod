package cn.mcmod.sakura.block.machines;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.MapleTreeSapLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Maple Spile (tap) from 1.12.2.
 * Placed on the side of a MapleTreeSapLog to extract sap.
 * Random ticks advance the sap log toward exhaustion.
 * Dripping particles appear when sap is being collected.
 */
public class MapleSpileBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 2.0D, 0.0D, 12.0D, 10.0D, 8.0D);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 2.0D, 8.0D, 12.0D, 10.0D, 16.0D);
    protected static final VoxelShape SHAPE_WEST = Block.box(0.0D, 2.0D, 4.0D, 8.0D, 10.0D, 12.0D);
    protected static final VoxelShape SHAPE_EAST = Block.box(8.0D, 2.0D, 4.0D, 16.0D, 10.0D, 12.0D);

    public MapleSpileBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                .strength(0.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockPos attachedPos = context.getClickedPos().relative(facing.getOpposite());
        BlockState attachedState = context.getLevel().getBlockState(attachedPos);
        // Only place if attached to a non-exhausted maple sap log
        if (isValidSapLog(attachedState)) {
            return this.defaultBlockState().setValue(FACING, facing);
        }
        // Try all horizontal facings
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos logPos = context.getClickedPos().relative(dir.getOpposite());
            BlockState logState = context.getLevel().getBlockState(logPos);
            if (isValidSapLog(logState)) {
                return this.defaultBlockState().setValue(FACING, dir);
            }
        }
        return null; // Cannot place
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedState = level.getBlockState(attachedPos);
        return isValidSapLog(attachedState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                   LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!canSurvive(state, level, pos)) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    /**
     * Check if the block behind the spile is a valid (non-exhausted) maple sap log.
     */
    private boolean isValidSapLog(BlockState state) {
        return state.is(BlockRegistry.MAPLE_SAP_LOG.get())
                && !MapleTreeSapLogBlock.isExhausted(state);
    }

    /**
     * Check if the spile can currently work (attached log is not exhausted).
     */
    public static boolean canWork(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        BlockPos logPos = pos.relative(facing.getOpposite());
        BlockState logState = level.getBlockState(logPos);
        return logState.is(BlockRegistry.MAPLE_SAP_LOG.get())
                && !MapleTreeSapLogBlock.isExhausted(logState);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (canWork(level, pos, state)) {
            // Slowly exhaust the sap log over 5 steps (1.12.2 used rand.nextInt(5000)==0, SAP_AGE 0->5)
            if (random.nextInt(5000) == 0) {
                Direction facing = state.getValue(FACING);
                BlockPos logPos = pos.relative(facing.getOpposite());
                BlockState logState = level.getBlockState(logPos);
                int currentAge = logState.getValue(MapleTreeSapLogBlock.EXHAUSTION);
                level.setBlockAndUpdate(logPos, logState.setValue(MapleTreeSapLogBlock.EXHAUSTION, currentAge + 1));
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (canWork(level, pos, state)) {
            if (rand.nextInt(10) == 0) {
                double d0 = pos.getX() + 0.5D;
                double d1 = pos.getY() - 0.15D;
                double d2 = pos.getZ() + 0.5D;
                level.addParticle(ParticleTypes.DRIPPING_HONEY, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}
