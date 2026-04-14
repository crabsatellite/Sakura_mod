package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.block.entity.CampfirePotBlockEntity;
import com.mojang.serialization.MapCodec;

import javax.annotation.Nullable;

/**
 * Campfire with Cooking Pot from the Sakura mod.
 * A campfire with an integrated cooking pot for recipes.
 * The idle/lit states are controlled by the LIT blockstate property.
 */
public class CampfirePotBlock extends BaseEntityBlock {
    public static final MapCodec<CampfirePotBlock> CODEC = simpleCodec(p -> new CampfirePotBlock(false));

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);

    public CampfirePotBlock(boolean defaultLit) {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                .strength(0.5F)
                .sound(SoundType.WOOD)
                .lightLevel(state -> state.getValue(LIT) ? 15 : 0)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, defaultLit));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.CAMPFIRE_POT.get().create(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntity) {
        return level.isClientSide() ? null
                : createTickerHelper(blockEntity, BlockEntityRegistry.CAMPFIRE_POT.get(),
                        CampfirePotBlockEntity::workingTick);
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
        builder.add(FACING, LIT);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (!(blockentity instanceof CampfirePotBlockEntity campfirePot)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // Add fuel
        int burnValue = stack.getBurnTime(null);
        if (burnValue > 0) {
            campfirePot.setBurnTime(campfirePot.getBurnTime() + burnValue);
            setLitState(true, level, pos, state);
            if (stack.hasCraftingRemainingItem()) {
                ItemStack container = stack.getCraftingRemainingItem();
                stack.shrink(1);
                if (!player.getInventory().add(container)) {
                    player.drop(container, false);
                }
            } else {
                stack.shrink(1);
            }
            return ItemInteractionResult.CONSUME;
        }

        // Ignite with flint and steel
        if (stack.is(Items.FLINT_AND_STEEL)) {
            campfirePot.setBurnTime(campfirePot.getBurnTime() + 10000);
            setLitState(true, level, pos, state);
            stack.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
            return ItemInteractionResult.CONSUME;
        }

        // Handle fluid containers (buckets)
        IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack.copyWithCount(1))
                .orElse(null);
        if (handler != null && handler instanceof FluidBucketWrapper) {
            FluidUtil.interactWithFluidHandler(player, hand, campfirePot.getFluidTank());
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof CampfirePotBlockEntity menuProvider)) {
            return InteractionResult.FAIL;
        }
        if (!level.isClientSide()) {
            player.openMenu(menuProvider, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CampfirePotBlockEntity campfirePot) {
                Containers.dropContents(level, pos, campfirePot.getDroppableInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    /**
     * Update the LIT blockstate property. Preserves the block entity.
     */
    public static void setLitState(boolean lit, Level level, BlockPos pos, BlockState currentState) {
        BlockState newState = currentState.setValue(LIT, lit);
        if (level.getBlockState(pos).getValue(LIT) != lit) {
            level.setBlock(pos, newState, 3);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            if (random.nextInt(10) == 0) {
                level.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                        SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.6F, false);
            }
            // Steam particles from the pot
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    pos.getX() + 0.5D + random.nextDouble() / 3.0D * (random.nextBoolean() ? 1 : -1),
                    pos.getY() + 0.7D + random.nextDouble() + random.nextDouble(),
                    pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (random.nextBoolean() ? 1 : -1),
                    0.0D, 0.07D, 0.0D);
        }
    }
}
