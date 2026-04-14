package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.block.entity.CampfireBlockEntity;
import com.mojang.serialization.MapCodec;

import javax.annotation.Nullable;

/**
 * Campfire block from the Sakura mod.
 * A custom campfire that can cook items placed on it.
 * The idle/lit states are controlled by the LIT blockstate property.
 *
 * Right-click interactions (from 1.12.2):
 * - Food item: place on campfire to cook
 * - Cooking pot item: convert campfire to campfire_pot block
 * - Fuel item: add burn time
 * - Flint and steel: ignite (add burn time)
 * - Empty hand: remove item from campfire
 */
public class SakuraCampfireBlock extends BaseEntityBlock {
    public static final MapCodec<SakuraCampfireBlock> CODEC = simpleCodec(p -> new SakuraCampfireBlock(false));

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public SakuraCampfireBlock(boolean defaultLit) {
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
        return BlockEntityRegistry.CAMPFIRE.get().create(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntity) {
        return level.isClientSide() ? null
                : createTickerHelper(blockEntity, BlockEntityRegistry.CAMPFIRE.get(), CampfireBlockEntity::workingTick);
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        Object tile = level.getBlockEntity(pos);
        if (tile instanceof CampfireBlockEntity campfire) {

            // 1. Place food on the campfire (if inventory accepts it and has room)
            if (campfire.getInventory().isItemValid(0, stack)
                    && campfire.getInventory().getStackInSlot(0).getCount() < 16) {
                ItemStack toInsert = stack.copyWithCount(1);
                stack.shrink(1);
                campfire.getInventory().insertItem(0, toInsert, false);
                return ItemInteractionResult.CONSUME;
            }

            // 2. Place cooking pot on the campfire -> convert to campfire_pot block
            if (stack.is(BlockRegistry.CAMPFIRE_POT_IDLE.get().asItem())) {
                boolean wasLit = state.getValue(LIT);
                Direction facing = state.getValue(FACING);
                Containers.dropContents(level, pos, campfire.getDroppableInventory());
                level.removeBlockEntity(pos);
                level.setBlock(pos, BlockRegistry.CAMPFIRE_POT_IDLE.get().defaultBlockState()
                        .setValue(CampfirePotBlock.FACING, facing)
                        .setValue(CampfirePotBlock.LIT, wasLit), 3);
                stack.shrink(1);
                return ItemInteractionResult.CONSUME;
            }

            // 3. Add fuel
            int burnValue = stack.getBurnTime(null);
            if (burnValue > 0) {
                campfire.setBurnTime(campfire.getBurnTime() + burnValue);
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

            // 4. Ignite with flint and steel
            if (stack.is(Items.FLINT_AND_STEEL)) {
                campfire.setBurnTime(campfire.getBurnTime() + 10000);
                setLitState(true, level, pos, state);
                stack.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
                return ItemInteractionResult.CONSUME;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Object tile = level.getBlockEntity(pos);
        if (tile instanceof CampfireBlockEntity campfire) {
            ItemStack removed = campfire.getInventory().getStackInSlot(0);
            if (!removed.isEmpty()) {
                Block.popResource(level, pos, removed);
                campfire.getInventory().setStackInSlot(0, ItemStack.EMPTY);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
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

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            Object blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CampfireBlockEntity campfire) {
                Containers.dropContents(level, pos, campfire.getDroppableInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
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
            if (random.nextInt(5) == 0) {
                for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                    level.addParticle(ParticleTypes.LAVA,
                            pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                            random.nextFloat() / 2.0F, 5.0E-5D, random.nextFloat() / 2.0F);
                }
            }
        }
    }
}
