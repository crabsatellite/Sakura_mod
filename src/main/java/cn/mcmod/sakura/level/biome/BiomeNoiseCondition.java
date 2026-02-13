package cn.mcmod.sakura.level.biome;

import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.phys.Vec3;

/**
 * Simplified climate noise condition for Sakura surface biomes.
 * Unlike AlexsCaves which checks depth for cave biomes, this only
 * checks temperature and continentalness for surface placement.
 */
public class BiomeNoiseCondition {

    private final int rarityOffset;
    private final int distanceFromSpawn;
    private final float temperatureMin;
    private final float temperatureMax;
    private final float continentalnessMin;
    private final float continentalnessMax;
    private boolean disabledCompletely;

    /**
     * @param rarityOffset       internal biome ID for Voronoi mapping
     * @param distanceFromSpawn  minimum distance from spawn in blocks
     * @param temperatureMin     minimum temperature noise value (-1.0 to 1.0)
     * @param temperatureMax     maximum temperature noise value (-1.0 to 1.0)
     * @param continentalnessMin minimum continentalness noise value (-1.0 to 1.0)
     * @param continentalnessMax maximum continentalness noise value (-1.0 to 1.0)
     */
    public BiomeNoiseCondition(int rarityOffset, int distanceFromSpawn,
                               float temperatureMin, float temperatureMax,
                               float continentalnessMin, float continentalnessMax) {
        this.rarityOffset = rarityOffset;
        this.distanceFromSpawn = distanceFromSpawn;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.continentalnessMin = continentalnessMin;
        this.continentalnessMax = continentalnessMax;
        this.disabledCompletely = false;
    }

    /**
     * Tests whether a given position should have this biome based on climate conditions.
     *
     * @param x              x quad coordinate
     * @param y              y quad coordinate
     * @param z              z quad coordinate
     * @param climateSampler the climate sampler for noise values
     * @param dimension      the dimension being sampled
     * @param info           Voronoi info for the cell
     * @return true if conditions are met for this biome
     */
    public boolean test(int x, int y, int z, Climate.Sampler climateSampler,
                        ResourceKey<Level> dimension, VoronoiGenerator.VoronoiInfo info) {
        if (disabledCompletely) {
            return false;
        }

        // Only generate in the overworld
        if (dimension != null && !dimension.equals(Level.OVERWORLD)) {
            return false;
        }

        if (!isFarEnoughFromSpawn(x, z, distanceFromSpawn)) {
            return false;
        }

        // Sample climate at the center of the Voronoi cell for consistent biome placement
        Vec3 rareBiomeCenter = SakuraBiomeRarity.getRareBiomeCenter(info);
        if (rareBiomeCenter == null) {
            return false;
        }

        Climate.TargetPoint centerTargetPoint = climateSampler.sample(
                (int) Math.floor(rareBiomeCenter.x), y, (int) Math.floor(rareBiomeCenter.z));

        float temperature = Climate.unquantizeCoord(centerTargetPoint.temperature());
        float continentalness = Climate.unquantizeCoord(centerTargetPoint.continentalness());

        // Check temperature range
        if (temperature < temperatureMin || temperature > temperatureMax) {
            return false;
        }

        // Check continentalness range (how far inland)
        if (continentalness < continentalnessMin || continentalness > continentalnessMax) {
            return false;
        }

        return true;
    }

    private static boolean isFarEnoughFromSpawn(int xIn, int zIn, double dist) {
        int x = QuartPos.toBlock(xIn);
        int z = QuartPos.toBlock(zIn);
        return (long) x * x + (long) z * z >= dist * dist;
    }

    public boolean isDisabledCompletely() {
        return disabledCompletely;
    }

    public void setDisabledCompletely(boolean disabledCompletely) {
        this.disabledCompletely = disabledCompletely;
    }

    public int getRarityOffset() {
        return rarityOffset;
    }
}
