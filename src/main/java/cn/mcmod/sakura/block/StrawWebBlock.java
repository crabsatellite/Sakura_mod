package cn.mcmod.sakura.block;

import javax.annotation.Nullable;

import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.block.entity.StrawWebBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Straw Web block (drying rack).
 * Ported from 1.12.2 BlockWeb.
 * Players can right-click to place or remove items.
 * Items dry over time based on biome/weather conditions.
 */
public class StrawWebBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

    public StrawWebBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .strength(0.25F)
                .sound(SoundType.GRASS)
                .noOcclusion());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.STRAW_WEB.get().create(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null
                : createTickerHelper(blockEntityType, BlockEntityRegistry.STRAW_WEB.get(), StrawWebBlockEntity::workingTick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof StrawWebBlockEntity web) {
            ItemStack heldStack = player.getItemInHand(hand);

            if (web.isEmpty()) {
                if (heldStack.isEmpty()) {
                    return InteractionResult.PASS;
                }
                if (StrawWebBlockEntity.isValidRecipe(heldStack)) {
                    if (web.addItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
                        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                                SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.PASS;
            } else if (hand.equals(InteractionHand.MAIN_HAND)) {
                ItemStack removed = web.removeItem();
                if (!removed.isEmpty()) {
                    if (!player.isCreative()) {
                        if (!player.getInventory().add(removed)) {
                            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), removed);
                        }
                    }
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                            SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 0.25F, 0.5F);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof StrawWebBlockEntity web) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), web.getStoredItem());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1F;
    }
}
