package cn.mcmod.sakura.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, SakuraMod.MODID);

    public static final Holder<MobEffect> EXP_UP =
            MOB_EFFECTS.register("exp_up", ExpUpEffect::new);
    public static final Holder<MobEffect> CANNON =
            MOB_EFFECTS.register("cannon", CannonEffect::new);
    public static final Holder<MobEffect> FIRE_BLADE =
            MOB_EFFECTS.register("fire_blade", FireBladeEffect::new);
    public static final Holder<MobEffect> GOLDEN_HEART =
            MOB_EFFECTS.register("golden_heart", GoldenHeartEffect::new);
    public static final Holder<MobEffect> POISOM =
            MOB_EFFECTS.register("poisom", PoisomEffect::new);
    public static final Holder<MobEffect> SCORPION =
            MOB_EFFECTS.register("scorpion", ScorpionEffect::new);
}
