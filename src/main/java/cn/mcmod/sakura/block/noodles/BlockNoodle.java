package cn.mcmod.sakura.block.noodles;

import cn.mcmod.sakura.tags.SakuraItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BlockNoodle extends Block {
    public static final IntegerProperty CUTTING = IntegerProperty.create("cutting", 0, 7);
    protected static final VoxelShape NOODLE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);

    public BlockNoodle(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CUTTING, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CUTTING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return NOODLE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return NOODLE_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(CUTTING, 0);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return belowState.isFaceSturdy(level, belowPos, Direction.UP);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    protected boolean isReady(BlockState state) {
        return state.getValue(CUTTING) >= 7;
    }

    public abstract ItemStack getNoodle();

    public abstract ItemStack getUnfinishedItem();

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack heldStack = player.getItemInHand(hand);
        int cutting = state.getValue(CUTTING);

        if (!isReady(state)) {
            if (heldStack.is(SakuraItemTags.TOOLS_KNIVES_NOODLE)) {
                if (!player.getAbilities().instabuild && level.random.nextInt(10) == 0) {
                    heldStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                }
                if (level.random.nextInt(10) == 0) {
                    level.setBlock(pos, state.setValue(CUTTING, Math.min(cutting + 1, 7)), 3);
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (!level.isClientSide && !player.isCreative()) {
                ItemStack dropStack = getNoodle().copy();
                Block.popResource(level, pos, dropStack);
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return getUnfinishedItem().copy();
    }
}
