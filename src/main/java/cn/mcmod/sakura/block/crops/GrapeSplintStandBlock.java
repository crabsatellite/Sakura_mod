package cn.mcmod.sakura.block.crops;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Grape Splint Stand - A vertical support post for grape vine growing.
 * Players can right-click with grape seeds to plant a grape vine on it,
 * or with hop seeds to plant hops.
 */
public class GrapeSplintStandBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public GrapeSplintStandBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .noOcclusion());
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

        if (heldItem.is(ItemRegistry.GRAPE_SEEDS.get())) {
            level.setBlock(pos, BlockRegistry.GRAPE_CROP.get().defaultBlockState(), 3);
            if (!player.isCreative()) heldItem.shrink(1);
            return InteractionResult.SUCCESS;
        }
        if (heldItem.is(ItemRegistry.HOP_SEEDS.get())) {
            level.setBlock(pos, BlockRegistry.HOPS_CROP.get().defaultBlockState(), 3);
            if (!player.isCreative()) heldItem.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
