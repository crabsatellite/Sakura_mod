package cn.mcmod.sakura.data;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.level.WorldGenerationRegistry;
import cn.mcmod.sakura.level.feature.SakuraFeatureRegistry;
import cn.mcmod.sakura.level.tree.SakuraTreeFeatures;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SakuraFeatureProvider extends DatapackBuiltinEntriesProvider {

    public SakuraFeatureProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries, new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
                    // Initialize tree configurations (these only need block states, no holder refs)
                    SakuraTreeFeatures.init();

                    // Register individual tree configured features
                    SakuraTreeFeatures.ENTRY.forEach(
                            e -> bootstrap.register(e.getFirst(), e.getSecond())
                    );

                    // Register simple configured features (patches, ores, etc.)
                    bootstrap.register(WorldGenerationRegistry.FEATURE_PATCH_BAMBOOSHOOT_KEY,
                            new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(
                                    64, 1, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(new NoiseThresholdProvider(496156461L,
                                            new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F,
                                            BlockRegistry.BAMBOOSHOOT.get().defaultBlockState(),
                                            List.of(BlockRegistry.BAMBOOSHOOT.get().defaultBlockState()),
                                            List.of(BlockRegistry.BAMBOOSHOOT.get().defaultBlockState())))))));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_BAMBOO_COLUMN_KEY,
                            new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                                    new BlockColumnConfiguration(List.of(
                                            BlockColumnConfiguration.layer(BiasedToBottomInt.of(9, 18),
                                                    BlockStateProvider.simple(BlockRegistry.BAMBOO_PLANT.get()))),
                                            Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, true)));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_PATCH_WILD_PEPPER_KEY,
                            new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                                    32, 6, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.WILD_PEPPER.get()))))));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_PATCH_WILD_VANILLA_KEY,
                            new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                                    32, 6, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(BlockRegistry.WILD_VANILLA.get()))))));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_ORE_IRON_SAND_KEY,
                            new ConfiguredFeature<>(Feature.ORE,
                                    new OreConfiguration(new TagMatchTest(BlockTags.SAND),
                                            BlockRegistry.IRON_SAND.get().defaultBlockState(), 12)));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_ORE_SAKURA_DIAMOND_KEY,
                            new ConfiguredFeature<>(Feature.ORE,
                                    new OreConfiguration(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                            BlockRegistry.SAKURA_DIAMOND_ORE.get().defaultBlockState(), 5)));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_HOT_SPRING_KEY,
                            new ConfiguredFeature<>(SakuraFeatureRegistry.HOT_SPRING_FEATURE.get(),
                                    NoneFeatureConfiguration.INSTANCE));

                    // Register tree random selectors - these need PlacedFeature holder references
                    HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);

                    bootstrap.register(WorldGenerationRegistry.FEATURE_TREES_SAKURA_KEY,
                            new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                                    new RandomFeatureConfiguration(
                                            List.of(
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.SAKURA_CHECKED_KEY), 0.125F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.FANCY_SAKURA_CHECKED_KEY), 0.05F)),
                                            placedFeatures.getOrThrow(WorldGenerationRegistry.BIG_SAKURA_CHECKED_KEY))));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_TREES_MAPLE_KEY,
                            new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                                    new RandomFeatureConfiguration(
                                            List.of(
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.FANCY_MAPLE_RED_CHECKED_KEY), 0.02F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.BIG_MAPLE_RED_CHECKED_KEY), 0.05F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.MAPLE_YELLOW_CHECKED_KEY), 0.15F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.FANCY_MAPLE_YELLOW_CHECKED_KEY), 0.02F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.BIG_MAPLE_YELLOW_CHECKED_KEY), 0.04F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.MAPLE_ORANGE_CHECKED_KEY), 0.15F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.FANCY_MAPLE_ORANGE_CHECKED_KEY), 0.02F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.BIG_MAPLE_ORANGE_CHECKED_KEY), 0.04F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.MAPLE_GREEN_CHECKED_KEY), 0.12F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.FANCY_MAPLE_GREEN_CHECKED_KEY), 0.02F),
                                                    new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.BIG_MAPLE_GREEN_CHECKED_KEY), 0.04F)),
                                            placedFeatures.getOrThrow(WorldGenerationRegistry.MAPLE_RED_CHECKED_KEY))));

                    bootstrap.register(WorldGenerationRegistry.FEATURE_TREES_UME_KEY,
                            new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                                    new RandomFeatureConfiguration(
                                            List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(WorldGenerationRegistry.FANCY_UME_CHECKED_KEY), 0.1F)),
                                            placedFeatures.getOrThrow(WorldGenerationRegistry.UME_CHECKED_KEY))));
                })
                .add(Registries.PLACED_FEATURE, bootstrap -> {
                    HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

                    // Simple placed features
                    bootstrap.register(WorldGenerationRegistry.PATCH_BAMBOOSHOOT_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_PATCH_BAMBOOSHOOT_KEY),
                                    List.of(PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome(),
                                            PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING),
                                            RarityFilter.onAverageOnceEvery(30))));

                    bootstrap.register(WorldGenerationRegistry.PATCH_WILD_PEPPER_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_PATCH_WILD_PEPPER_KEY),
                                    List.of(PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome(),
                                            RarityFilter.onAverageOnceEvery(64))));

                    bootstrap.register(WorldGenerationRegistry.PATCH_WILD_VANILLA_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_PATCH_WILD_VANILLA_KEY),
                                    List.of(PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome(),
                                            RarityFilter.onAverageOnceEvery(48))));

                    bootstrap.register(WorldGenerationRegistry.ORE_IRON_SAND_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_ORE_IRON_SAND_KEY),
                                    List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                                            HeightRangePlacement.uniform(VerticalAnchor.absolute(58), VerticalAnchor.absolute(66)),
                                            BiomeFilter.biome())));

                    bootstrap.register(WorldGenerationRegistry.ORE_SAKURA_DIAMOND_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_ORE_SAKURA_DIAMOND_KEY),
                                    List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                                            HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(34)),
                                            BiomeFilter.biome())));

                    bootstrap.register(WorldGenerationRegistry.HOT_SPRING_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_HOT_SPRING_KEY),
                                    List.of(PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome(),
                                            RarityFilter.onAverageOnceEvery(80))));

                    bootstrap.register(WorldGenerationRegistry.BAMBOO_COLUMN_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_BAMBOO_COLUMN_KEY),
                                    List.of(CountPlacement.of(15), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));

                    // Sakura tree checked features
                    bootstrap.register(WorldGenerationRegistry.SAKURA_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.SAKURA_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.SAKURA_SAPLING.get()))));
                    bootstrap.register(WorldGenerationRegistry.FANCY_SAKURA_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.FANCY_SAKURA_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.SAKURA_SAPLING.get()))));
                    bootstrap.register(WorldGenerationRegistry.BIG_SAKURA_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.BIG_SAKURA_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.SAKURA_SAPLING.get()))));

                    // Maple tree checked features (all colors)
                    bootstrap.register(WorldGenerationRegistry.MAPLE_RED_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.MAPLE_RED_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_RED.get()))));
                    bootstrap.register(WorldGenerationRegistry.MAPLE_YELLOW_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.MAPLE_YELLOW_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_YELLOW.get()))));
                    bootstrap.register(WorldGenerationRegistry.MAPLE_ORANGE_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.MAPLE_ORANGE_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_ORANGE.get()))));
                    bootstrap.register(WorldGenerationRegistry.MAPLE_GREEN_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.MAPLE_GREEN_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_GREEN.get()))));

                    bootstrap.register(WorldGenerationRegistry.FANCY_MAPLE_RED_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.FANCY_MAPLE_RED_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_RED.get()))));
                    bootstrap.register(WorldGenerationRegistry.FANCY_MAPLE_YELLOW_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.FANCY_MAPLE_YELLOW_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_YELLOW.get()))));
                    bootstrap.register(WorldGenerationRegistry.FANCY_MAPLE_ORANGE_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.FANCY_MAPLE_ORANGE_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_ORANGE.get()))));
                    bootstrap.register(WorldGenerationRegistry.FANCY_MAPLE_GREEN_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.FANCY_MAPLE_GREEN_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_GREEN.get()))));

                    bootstrap.register(WorldGenerationRegistry.BIG_MAPLE_RED_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.BIG_MAPLE_RED_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_RED.get()))));
                    bootstrap.register(WorldGenerationRegistry.BIG_MAPLE_YELLOW_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.BIG_MAPLE_YELLOW_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_YELLOW.get()))));
                    bootstrap.register(WorldGenerationRegistry.BIG_MAPLE_ORANGE_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.BIG_MAPLE_ORANGE_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_ORANGE.get()))));
                    bootstrap.register(WorldGenerationRegistry.BIG_MAPLE_GREEN_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.BIG_MAPLE_GREEN_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_GREEN.get()))));

                    // Ume tree checked features
                    bootstrap.register(WorldGenerationRegistry.UME_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.UME_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.UME_SAPLING.get()))));
                    bootstrap.register(WorldGenerationRegistry.FANCY_UME_CHECKED_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(SakuraTreeFeatures.FANCY_UME_KEY),
                                    List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.UME_SAPLING.get()))));

                    // Tree placed features for biome spawning
                    bootstrap.register(WorldGenerationRegistry.TREES_SAKURA_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_TREES_SAKURA_KEY),
                                    List.of(PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));
                    bootstrap.register(WorldGenerationRegistry.TREES_MAPLE_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_TREES_MAPLE_KEY),
                                    List.of(PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));
                    bootstrap.register(WorldGenerationRegistry.TREES_UME_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_TREES_UME_KEY),
                                    List.of(PlacementUtils.countExtra(0, 0.04F, 1), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));

                    // Dense variants for custom biome internal generation
                    bootstrap.register(WorldGenerationRegistry.BAMBOO_COLUMN_DENSE_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_BAMBOO_COLUMN_KEY),
                                    List.of(CountPlacement.of(30), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));
                    bootstrap.register(WorldGenerationRegistry.TREES_SAKURA_DENSE_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_TREES_SAKURA_KEY),
                                    List.of(PlacementUtils.countExtra(0, 0.1F, 1), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));
                    bootstrap.register(WorldGenerationRegistry.TREES_MAPLE_DENSE_KEY,
                            new PlacedFeature(configuredFeatures.getOrThrow(WorldGenerationRegistry.FEATURE_TREES_MAPLE_KEY),
                                    List.of(PlacementUtils.countExtra(10, 0.1F, 1), InSquarePlacement.spread(),
                                            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome())));
                })
                , Set.of(SakuraMod.MODID));
    }
}
