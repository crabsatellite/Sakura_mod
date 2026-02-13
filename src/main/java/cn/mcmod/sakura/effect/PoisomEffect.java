package cn.mcmod.sakura.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Applies Poison I to targets when the player hits them in melee combat.
 * The on-hit logic is handled by {@link SakuraEffectEvents}.
 */
public class PoisomEffect extends MobEffect {
    public PoisomEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x4E9331); // dark green
    }
}
