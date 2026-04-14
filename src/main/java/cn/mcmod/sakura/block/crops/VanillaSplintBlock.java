package cn.mcmod.sakura.block.crops;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import com.mojang.serialization.MapCodec;

/**
 * Vanilla Splint - A wooden support post for vanilla crops.
 * Players can right-click with vanilla seeds to plant a vanilla crop on it.
 * Must be placed on dirt/grass or on top of another vanilla splint/vanilla crop.
 * Has full block bounding box but no collision (like the 1.12.2 version).
 */
public class VanillaSplintBlock extends Block {
    public static final MapCodec<VanillaSplintBlock> CODEC = simpleCodec(p -> new VanillaSplintBlock());

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }


    public VanillaSplintBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .noCollission()
                .randomTicks());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) return ItemInteractionResult.SUCCESS;

        if (stack.is(ItemRegistry.VANILLA_SEEDS.get())) {
            level.setBlock(pos, BlockRegistry.VANILLA_CROP.get().defaultBlockState(), 3);
            if (!player.isCreative()) stack.shrink(1);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        Block belowBlock = below.getBlock();
        return belowBlock == Blocks.DIRT || belowBlock == Blocks.GRASS_BLOCK || belowBlock == Blocks.FARMLAND
                || belowBlock == Blocks.COARSE_DIRT || belowBlock == Blocks.ROOTED_DIRT || belowBlock == Blocks.PODZOL
                || belowBlock == BlockRegistry.VANILLA_SPLINT.get()
                || belowBlock == BlockRegistry.VANILLA_CROP.get();
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
