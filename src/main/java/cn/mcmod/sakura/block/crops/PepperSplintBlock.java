package cn.mcmod.sakura.block.crops;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Pepper Splint - A wooden support post for pepper crops.
 * Players can right-click with pepper seeds to plant a pepper crop on it.
 * Must be placed on dirt/grass or on top of another pepper splint/pepper crop.
 */
public class PepperSplintBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public PepperSplintBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .randomTicks());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (heldItem.is(ItemRegistry.PEPPER_SEEDS.get())) {
            level.setBlock(pos, BlockRegistry.PEPPER_CROP.get().defaultBlockState(), 3);
            if (!player.isCreative()) heldItem.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        Block belowBlock = below.getBlock();
        return belowBlock == Blocks.DIRT || belowBlock == Blocks.GRASS_BLOCK || belowBlock == Blocks.FARMLAND
                || belowBlock == Blocks.COARSE_DIRT || belowBlock == Blocks.ROOTED_DIRT || belowBlock == Blocks.PODZOL
                || belowBlock == BlockRegistry.PEPPER_SPLINT.get()
                || belowBlock == BlockRegistry.PEPPER_CROP.get();
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        }
    }
}
