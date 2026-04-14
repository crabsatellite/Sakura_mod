package cn.mcmod.sakura.client.particle;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister
            .create(BuiltInRegistries.PARTICLE_TYPE, SakuraMod.MODID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SAKURA_LEAF = PARTICLE_TYPES.register("sakura",
            () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_MAPLE_LEAF = PARTICLE_TYPES.register("red_maple",
            () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_MAPLE_LEAF = PARTICLE_TYPES.register("yellow_maple",
            () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREEN_MAPLE_LEAF = PARTICLE_TYPES.register("green_maple",
            () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ORANGE_MAPLE_LEAF = PARTICLE_TYPES.register("orange_maple",
            () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SYRUP_DROP = PARTICLE_TYPES.register("syrup_drop",
            () -> new SimpleParticleType(false));
}
