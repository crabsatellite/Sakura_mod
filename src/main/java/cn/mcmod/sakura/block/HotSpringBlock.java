package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Hot spring water block that applies Regeneration I to entities standing inside it.
 * The effect is refreshed every 80 ticks (4 seconds) to provide a gentle healing zone.
 */
public class HotSpringBlock extends Block {

    public HotSpringBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            if (level.getGameTime() % 80L == 0L) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, true, true));
            }
        }
    }
}
