package cn.mcmod.sakura.level.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import cn.mcmod.sakura.SakuraMod;

public class SakuraBiomeRegistry {
    public static final ResourceKey<Biome> BAMBOO_FOREST = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "bamboo_forest"));
    public static final ResourceKey<Biome> MAPLE_FOREST = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "maple_forest"));
}
