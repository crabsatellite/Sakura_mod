package cn.mcmod.sakura.level.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import cn.mcmod.sakura.SakuraConfig;

import java.util.LinkedHashMap;

/**
 * Manages the configuration for Sakura biome generation.
 * Reads from SakuraConfig to determine which biomes are enabled
 * and creates the appropriate noise conditions for each.
 */
public class SakuraBiomeConfig {

    /**
     * Map of biome resource keys to their noise generation conditions.
     * This is rebuilt on server start from the config.
     */
    public static final LinkedHashMap<ResourceKey<Biome>, BiomeNoiseCondition> BIOMES = new LinkedHashMap<>();

    /**
     * Total number of possible biome slots. This must remain constant
     * regardless of which biomes are enabled/disabled, because the Voronoi
     * hash distribution depends on it. If we changed this based on enabled
     * biome count, the biome placement would shift when toggling biomes.
     */
    private static final int TOTAL_BIOME_SLOTS = 2;

    /**
     * Reload biome configuration from SakuraConfig.
     * Called on server start after config values are available.
     */
    public static void reload() {
        BIOMES.clear();

        // Bamboo Forest: warm, inland areas
        // Temperature 0.5 to 1.0 (warm climates)
        // Continentalness 0.0 to 1.0 (inland, not ocean)
        if (SakuraConfig.COMMON.enableBambooForestBiome.get()) {
            BIOMES.put(SakuraBiomeRegistry.BAMBOO_FOREST,
                    new BiomeNoiseCondition(0, 400, 0.5f, 1.0f, 0.0f, 1.0f));
        }

        // Maple Forest: temperate, inland areas
        // Temperature -0.5 to 0.5 (temperate climates)
        // Continentalness 0.2 to 1.0 (further inland)
        if (SakuraConfig.COMMON.enableMapleForestBiome.get()) {
            BIOMES.put(SakuraBiomeRegistry.MAPLE_FOREST,
                    new BiomeNoiseCondition(1, 400, -0.5f, 0.5f, 0.2f, 1.0f));
        }
    }

    /**
     * Returns the total number of biome slots (not the number of enabled biomes).
     * This value must be constant for consistent Voronoi hash distribution.
     */
    public static int getBiomeCount() {
        return TOTAL_BIOME_SLOTS;
    }

    /**
     * Check if a specific biome is disabled completely.
     */
    public static boolean isBiomeDisabled(ResourceKey<Biome> biome) {
        BiomeNoiseCondition condition = BIOMES.get(biome);
        return condition == null || condition.isDisabledCompletely();
    }
}
