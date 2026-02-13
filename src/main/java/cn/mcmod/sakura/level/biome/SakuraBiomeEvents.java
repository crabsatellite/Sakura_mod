package cn.mcmod.sakura.level.biome;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.mixin.BiomeSourceAccessor;
import cn.mcmod.sakura.mixin.MultiNoiseBiomeSourceAccessor;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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

        // Process each dimension's BiomeSource
        for (ResourceKey<LevelStem> levelStemResourceKey : levelStems.registryKeySet()) {
            Optional<Holder.Reference<LevelStem>> holderOptional = levelStems.getHolder(levelStemResourceKey);
            if (holderOptional.isPresent()) {
                BiomeSource biomeSource = holderOptional.get().value().generator().getBiomeSource();

                if (biomeSource instanceof BiomeSourceAccessor expandedBiomeSource) {
                    // Set the resource key map on all dimensions (needed for biome lookup)
                    expandedBiomeSource.setResourceKeyMap(biomeMap);

                    // Only expand the overworld with Sakura biomes
                    if (levelStemResourceKey.equals(LevelStem.OVERWORLD)) {
                        ImmutableSet.Builder<Holder<Biome>> biomeHolders = ImmutableSet.builder();
                        for (ResourceKey<Biome> biomeResourceKey : SAKURA_BIOMES) {
                            allBiomes.getHolder(biomeResourceKey).ifPresent(biomeHolders::add);
                        }
                        expandedBiomeSource.expandBiomesWith(biomeHolders.build());

                        SakuraMod.getLogger().info("Sakura biome injection complete. {} biome(s) configured.",
                                SakuraBiomeConfig.BIOMES.size());
                    }
                }

                // Set seed and dimension on MultiNoiseBiomeSource for the mixin
                if (biomeSource instanceof MultiNoiseBiomeSourceAccessor multiNoiseAccessor) {
                    multiNoiseAccessor.setLastSampledSeed(server.getWorldData().worldGenOptions().seed());
                    multiNoiseAccessor.setLastSampledDimension(ResourceKey.create(Registries.DIMENSION,
                            levelStemResourceKey.location()));
                }
            }
        }
    }
}
