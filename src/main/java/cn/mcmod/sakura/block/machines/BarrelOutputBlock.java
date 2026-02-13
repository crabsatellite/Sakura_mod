package cn.mcmod.sakura.block.machines;

import javax.annotation.Nullable;

import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.block.entity.BarrelOutputBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.NetworkHooks;

/**
 * Barrel Output block from the Sakura mod.
 * Stores output fluids from fermentation/distillation and allows
 * draining into container items (e.g. bottles, buckets).
 */
public class BarrelOutputBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BarrelOutputBlock() {
        super(Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.BARREL_OUTPUT.get().create(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntity) {
        return level.isClientSide() ? null
                : createTickerHelper(blockEntity, BlockEntityRegistry.BARREL_OUTPUT.get(),
                        BarrelOutputBlockEntity::workingTick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
            BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof BarrelOutputBlockEntity barrelOut)) {
            return InteractionResult.FAIL;
        }

        ItemStack stack = player.getItemInHand(handIn);
        // Allow bucket/fluid container interaction
        if (barrelOut.getFluidTank().isPresent()) {
            FluidTank tank = barrelOut.getFluidTank().orElse(null);
            if (tank != null && FluidUtil.interactWithFluidHandler(player, handIn, tank)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (!level.isClientSide()) {
            NetworkHooks.openScreen((ServerPlayer) player, barrelOut, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BarrelOutputBlockEntity barrelOut) {
                Containers.dropContents(level, pos, barrelOut.getDroppableInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
