package cn.mcmod.sakura.level.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Handles Voronoi-based biome placement for Sakura biomes.
 * Adapted from AlexsCaves' ACBiomeRarity for surface biome placement.
 */
public class SakuraBiomeRarity {
    private static final List<Integer> BIOME_OCTAVES = ImmutableList.of(0);
    private static final PerlinSimplexNoise NOISE_X = new PerlinSimplexNoise(new XoroshiroRandomSource(5678L), BIOME_OCTAVES);
    private static final PerlinSimplexNoise NOISE_Z = new PerlinSimplexNoise(new XoroshiroRandomSource(8765L), BIOME_OCTAVES);
    private static final VoronoiGenerator VORONOI_GENERATOR = new VoronoiGenerator(42L);

    // Biome sizing parameters - tuned for surface biomes
    // Mean width of ~200 blocks (50 quads * 4), separation of ~600 blocks (150 quads * 4)
    private static final double BIOME_SIZE = 50.0D * 0.25D;  // 12.5 in quad space
    private static final double SEPARATION_DISTANCE = BIOME_SIZE + 150.0D * 0.25D; // 50 in quad space
    private static final double SPACING_RANDOMNESS = 0.85D;
    private static final double WIDTH_RANDOMNESS = 3.0D;

    /**
     * Initialize the Voronoi generator. Called on server start.
     */
    public static void init() {
        VORONOI_GENERATOR.setOffsetAmount(SPACING_RANDOMNESS);
    }

    /**
     * Does the heavy lifting of finding if x and z quads should contain a Sakura biome.
     *
     * @param worldSeed seed of the world for Voronoi generation
     * @param x         x quad being tested
     * @param z         z quad being tested
     * @return the Voronoi info if a Sakura biome is present, null otherwise
     */
    @Nullable
    public static VoronoiGenerator.VoronoiInfo getRareBiomeInfoForQuad(long worldSeed, int x, int z) {
        VORONOI_GENERATOR.setSeed(worldSeed);
        double sampleX = x / SEPARATION_DISTANCE;
        double sampleZ = z / SEPARATION_DISTANCE;
        double positionOffsetX = WIDTH_RANDOMNESS * NOISE_X.getValue(sampleX, sampleZ, false);
        double positionOffsetZ = WIDTH_RANDOMNESS * NOISE_Z.getValue(sampleX, sampleZ, false);
        VoronoiGenerator.VoronoiInfo info = VORONOI_GENERATOR.get2(sampleX + positionOffsetX, sampleZ + positionOffsetZ);
        if (info.distance() < (BIOME_SIZE / SEPARATION_DISTANCE)) {
            return info;
        } else {
            return null;
        }
    }

    /**
     * Gets the center of the biome pertaining to the voronoiInfo. Result is in quad coordinates.
     *
     * @param voronoiInfo the info of the sampled biome
     * @return a coordinate
     */
    @Nullable
    public static Vec3 getRareBiomeCenter(VoronoiGenerator.VoronoiInfo voronoiInfo) {
        return voronoiInfo.cellPos().scale(SEPARATION_DISTANCE);
    }

    /**
     * Gets the 'rarityOffset' for voronoiInfo, which is essentially an internal biome ID.
     * Maps the voronoi hash to a biome index based on configured biome count.
     *
     * @param voronoiInfo the info of the sampled biome
     * @return the rarityOffset of the info
     */
    public static int getRareBiomeOffsetId(VoronoiGenerator.VoronoiInfo voronoiInfo) {
        return (int) (((voronoiInfo.hash() + 1D) * 0.5D) * (double) SakuraBiomeConfig.getBiomeCount());
    }

    public static boolean isQuartInRareBiome(long worldSeed, int x, int z) {
        return getRareBiomeInfoForQuad(worldSeed, x, z) != null;
    }
}
