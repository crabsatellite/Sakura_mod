package cn.mcmod.sakura.block.machines;

import javax.annotation.Nullable;

import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.block.entity.MapleCauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Maple Syrup Cauldron from the Sakura mod.
 * Collects maple sap from logs with spiles attached
 * and processes it into maple syrup when heated.
 */
public class MapleCauldronBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 3);
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public MapleCauldronBlock() {
        super(Properties.copy(Blocks.CAULDRON));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LEVEL, 0));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.MAPLE_CAULDRON.get().create(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntity) {
        return level.isClientSide() ? null
                : createTickerHelper(blockEntity, BlockEntityRegistry.MAPLE_CAULDRON.get(),
                        MapleCauldronBlockEntity::workingTick);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LEVEL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
            BlockHitResult result) {
        ItemStack stack = player.getItemInHand(handIn);
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (!(blockentity instanceof MapleCauldronBlockEntity cauldron)) {
            return InteractionResult.FAIL;
        }
        IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1))
                .orElse(null);
        if (handler != null && handler instanceof FluidBucketWrapper) {
            FluidUtil.interactWithFluidHandler(player, handIn, cauldron.getFluidTank().orElse(null));
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {
            NetworkHooks.openScreen((ServerPlayer) player, cauldron, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MapleCauldronBlockEntity cauldron) {
                Containers.dropContents(level, pos, cauldron.getDroppableInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
