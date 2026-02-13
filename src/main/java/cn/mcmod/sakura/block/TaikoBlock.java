package cn.mcmod.sakura.block;

import cn.mcmod.sakura.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class TaikoBlock extends Block {

    public TaikoBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(1.0F).sound(SoundType.WOOD));
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).is(Items.STICK)) {
            world.playSound(player, pos, SoundRegistry.TAIKO.get(), SoundSource.BLOCKS, 1.2F, 1.2F);
            player.swing(hand);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return super.use(state, world, pos, player, hand, hit);
    }
}
