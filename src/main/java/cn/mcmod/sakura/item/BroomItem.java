package cn.mcmod.sakura.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BroomItem extends ShovelItem {
    public BroomItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (level.getBlockState(pos.above()).isAir() &&
            (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT))) {
            level.setBlock(pos, Blocks.DIRT_PATH.defaultBlockState(), 11);
            level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.2F, 1.0F);
            context.getItemInHand().hurtAndBreak(1, context.getPlayer(),
                p -> p.broadcastBreakEvent(context.getHand()));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
