package cn.mcmod.sakura.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

/**
 * Periodically removes harmful effects from the entity.
 * Ticks every 2 ticks and clears all non-beneficial effects (matching 1.12.2).
 */
public class GoldenHeartEffect extends MobEffect {
    public GoldenHeartEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFD700); // gold
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.getActiveEffects().stream()
                .filter(e -> !e.getEffect().isBeneficial())
                .map(MobEffectInstance::getEffect)
                .toList()
                .forEach(entity::removeEffect);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return (duration & 1) == 0; // every 2 ticks, matching 1.12.2
    }
}
