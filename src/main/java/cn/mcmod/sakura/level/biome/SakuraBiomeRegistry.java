package cn.mcmod.sakura.level.biome;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class SakuraBiomeRegistry {
    public static final ResourceKey<Biome> BAMBOO_FOREST = ResourceKey.create(Registries.BIOME, new ResourceLocation(SakuraMod.MODID, "bamboo_forest"));
    public static final ResourceKey<Biome> MAPLE_FOREST = ResourceKey.create(Registries.BIOME, new ResourceLocation(SakuraMod.MODID, "maple_forest"));
}
