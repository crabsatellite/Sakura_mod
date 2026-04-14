package cn.mcmod.sakura.level.biome;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import cn.mcmod.sakura.SakuraMod;
// TODO: Mixin accessor imports disabled for 1.21 migration - re-enable when mixins are ported
// import cn.mcmod.sakura.mixin.BiomeSourceAccessor;
// import cn.mcmod.sakura.mixin.MultiNoiseBiomeSourceAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles server-side biome registration events for Sakura biome injection.
 * On server start, this class:
 * 1. Reloads biome config from SakuraConfig
 * 2. Initializes the Voronoi noise generator
 * 3. Builds a ResourceKey-to-Holder map for all biomes
 * 4. Expands the overworld BiomeSource with Sakura biomes
 * 5. Sets the world seed and dimension on the MultiNoiseBiomeSource
 */
public class SakuraBiomeEvents {

    /** All Sakura biome keys that may be injected into the overworld. */
    private static final List<ResourceKey<Biome>> SAKURA_BIOMES = List.of(
            SakuraBiomeRegistry.BAMBOO_FOREST,
            SakuraBiomeRegistry.MAPLE_FOREST
    );

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        // Reload biome configuration from SakuraConfig
        SakuraBiomeConfig.reload();
        SakuraBiomeRarity.init();

        if (SakuraBiomeConfig.BIOMES.isEmpty()) {
            SakuraMod.getLogger().info("All Sakura biomes are disabled in config, skipping biome injection.");
            return;
        }

        MinecraftServer server = event.getServer();
        Registry<Biome> allBiomes = server.registryAccess().registryOrThrow(Registries.BIOME);
        Registry<LevelStem> levelStems = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);

        // Build a map of all biome ResourceKeys to their Holders
        Map<ResourceKey<Biome>, Holder<Biome>> biomeMap = new HashMap<>();
        for (ResourceKey<Biome> biomeResourceKey : allBiomes.registryKeySet()) {
            Optional<Holder.Reference<Biome>> holderOptional = allBiomes.getHolder(biomeResourceKey);
            holderOptional.ifPresent(biomeHolder -> biomeMap.put(biomeResourceKey, biomeHolder));
        }

        // TODO: Biome injection via mixin disabled for 1.21 migration
        // Re-enable when BiomeSourceMixin and MultiNoiseBiomeSourceMixin are ported
        SakuraMod.getLogger().info("Sakura biome injection is disabled pending mixin port to 1.21.1");
    }
}
