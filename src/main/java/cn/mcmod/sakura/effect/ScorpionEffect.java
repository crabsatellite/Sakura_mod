package cn.mcmod.sakura.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Applies strong Poison III to targets when the player hits them in melee combat.
 * The on-hit logic is handled by {@link SakuraEffectEvents}.
 */
public class ScorpionEffect extends MobEffect {
    public ScorpionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5A3A1A); // dark brown — benefits the player (poisons attackers' targets)
    }
}
