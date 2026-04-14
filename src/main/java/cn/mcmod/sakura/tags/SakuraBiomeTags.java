package cn.mcmod.sakura.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import cn.mcmod.sakura.SakuraMod;

public class SakuraBiomeTags {
    public static final TagKey<Biome> CAN_SPAWN_BAMBOO = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "can_spawn_bamboo"));
    public static final TagKey<Biome> CAN_SPAWN_SAKURA_TREE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "can_spawn_sakura_tree"));
    public static final TagKey<Biome> CAN_SPAWN_MAPLE_TREE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "can_spawn_maple_tree"));
    public static final TagKey<Biome> CAN_SPAWN_UME_TREE = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "can_spawn_ume_tree"));
    public static final TagKey<Biome> HAS_SAMURAI_SPAWNS = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "has_samurai_spawns"));
    public static final TagKey<Biome> HAS_DEER_SPAWNS = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "has_deer_spawns"));
    /** Biomes where Sakura Diamond Ore can spawn. Defaults to sakura mod biomes only.
     *  In 1.12.2, ore only spawned in bamboo/maple forest unless everyWhereSakuraDiamond config was true.
     *  Users can add #minecraft:is_overworld to this tag via datapack to enable everywhere. */
    public static final TagKey<Biome> HAS_SAKURA_DIAMOND = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "has_sakura_diamond"));
}
