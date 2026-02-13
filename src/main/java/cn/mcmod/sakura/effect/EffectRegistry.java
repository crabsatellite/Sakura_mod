package cn.mcmod.sakura.effect;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SakuraMod.MODID);

    public static final RegistryObject<MobEffect> EXP_UP =
            MOB_EFFECTS.register("exp_up", ExpUpEffect::new);
    public static final RegistryObject<MobEffect> CANNON =
            MOB_EFFECTS.register("cannon", CannonEffect::new);
    public static final RegistryObject<MobEffect> FIRE_BLADE =
            MOB_EFFECTS.register("fire_blade", FireBladeEffect::new);
    public static final RegistryObject<MobEffect> GOLDEN_HEART =
            MOB_EFFECTS.register("golden_heart", GoldenHeartEffect::new);
    public static final RegistryObject<MobEffect> POISOM =
            MOB_EFFECTS.register("poisom", PoisomEffect::new);
    public static final RegistryObject<MobEffect> SCORPION =
            MOB_EFFECTS.register("scorpion", ScorpionEffect::new);
}
