package cn.mcmod.sakura.level;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
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
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.level.feature.SakuraFeatureRegistry;
import cn.mcmod.sakura.level.tree.SakuraTreeFeatures;

import java.util.List;

public class WorldGenerationRegistry {

    // ===== Resource Keys (safe during class loading) =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_BAMBOOSHOOT_KEY = key("patch_bambooshoot");
    public static final ResourceKey<PlacedFeature> PATCH_BAMBOOSHOOT_KEY = pkey("patch_bambooshoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_BAMBOO_COLUMN_KEY = key("bamboo_column");
    public static final ResourceKey<PlacedFeature> BAMBOO_COLUMN_KEY = pkey("bamboo_column");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_WILD_PEPPER_KEY = key("patch_wild_pepper");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_PEPPER_KEY = pkey("patch_wild_pepper");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_WILD_VANILLA_KEY = key("patch_wild_vanilla");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_VANILLA_KEY = pkey("patch_wild_vanilla");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_ORE_IRON_SAND_KEY = key("ore_iron_sand");
    public static final ResourceKey<PlacedFeature> ORE_IRON_SAND_KEY = pkey("ore_iron_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_ORE_SAKURA_DIAMOND_KEY = key("ore_sakura_diamond");
    public static final ResourceKey<PlacedFeature> ORE_SAKURA_DIAMOND_KEY = pkey("ore_sakura_diamond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_HOT_SPRING_KEY = key("hot_spring");
    public static final ResourceKey<PlacedFeature> HOT_SPRING_KEY = pkey("hot_spring");
    public static final ResourceKey<PlacedFeature> BAMBOO_COLUMN_DENSE_KEY = pkey("bamboo_column_dense");

    public static final ResourceKey<PlacedFeature> SAKURA_CHECKED_KEY = pkey("sakura_checked");
    public static final ResourceKey<PlacedFeature> FANCY_SAKURA_CHECKED_KEY = pkey("fancy_sakura_checked");
    public static final ResourceKey<PlacedFeature> BIG_SAKURA_CHECKED_KEY = pkey("big_sakura_checked");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_TREES_SAKURA_KEY = key("trees_sakura");
    public static final ResourceKey<PlacedFeature> TREES_SAKURA_KEY = pkey("trees_sakura");
    public static final ResourceKey<PlacedFeature> TREES_SAKURA_DENSE_KEY = pkey("trees_sakura_dense");

    public static final ResourceKey<PlacedFeature> MAPLE_RED_CHECKED_KEY = pkey("maple_red_checked");
    public static final ResourceKey<PlacedFeature> MAPLE_YELLOW_CHECKED_KEY = pkey("maple_yellow_checked");
    public static final ResourceKey<PlacedFeature> MAPLE_ORANGE_CHECKED_KEY = pkey("maple_orange_checked");
    public static final ResourceKey<PlacedFeature> MAPLE_GREEN_CHECKED_KEY = pkey("maple_green_checked");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE_RED_CHECKED_KEY = pkey("fancy_maple_red_checked");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE_YELLOW_CHECKED_KEY = pkey("fancy_maple_yellow_checked");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE_ORANGE_CHECKED_KEY = pkey("fancy_maple_orange_checked");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE_GREEN_CHECKED_KEY = pkey("fancy_maple_green_checked");
    public static final ResourceKey<PlacedFeature> BIG_MAPLE_RED_CHECKED_KEY = pkey("big_maple_red_checked");
    public static final ResourceKey<PlacedFeature> BIG_MAPLE_YELLOW_CHECKED_KEY = pkey("big_maple_yellow_checked");
    public static final ResourceKey<PlacedFeature> BIG_MAPLE_ORANGE_CHECKED_KEY = pkey("big_maple_orange_checked");
    public static final ResourceKey<PlacedFeature> BIG_MAPLE_GREEN_CHECKED_KEY = pkey("big_maple_green_checked");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_TREES_MAPLE_KEY = key("trees_maple");
    public static final ResourceKey<PlacedFeature> TREES_MAPLE_KEY = pkey("trees_maple");
    public static final ResourceKey<PlacedFeature> TREES_MAPLE_DENSE_KEY = pkey("trees_maple_dense");

    public static final ResourceKey<PlacedFeature> UME_CHECKED_KEY = pkey("ume_checked");
    public static final ResourceKey<PlacedFeature> FANCY_UME_CHECKED_KEY = pkey("fancy_ume_checked");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_TREES_UME_KEY = key("trees_ume");
    public static final ResourceKey<PlacedFeature> TREES_UME_KEY = pkey("trees_ume");

    // ===== Feature/PlacedFeature instances (populated lazily by init()) =====
    public static ConfiguredFeature<?, ?> FEATURE_PATCH_BAMBOOSHOOT;
    public static PlacedFeature PATCH_BAMBOOSHOOT;
    public static ConfiguredFeature<?, ?> FEATURE_BAMBOO_COLUMN;
    public static PlacedFeature BAMBOO_COLUMN;
    public static ConfiguredFeature<?, ?> FEATURE_PATCH_WILD_PEPPER;
    public static PlacedFeature PATCH_WILD_PEPPER;
    public static ConfiguredFeature<?, ?> FEATURE_PATCH_WILD_VANILLA;
    public static PlacedFeature PATCH_WILD_VANILLA;
    public static ConfiguredFeature<?, ?> FEATURE_ORE_IRON_SAND;
    public static PlacedFeature ORE_IRON_SAND;
    public static ConfiguredFeature<?, ?> FEATURE_ORE_SAKURA_DIAMOND;
    public static PlacedFeature ORE_SAKURA_DIAMOND;
    public static ConfiguredFeature<?, ?> FEATURE_HOT_SPRING;
    public static PlacedFeature HOT_SPRING;
    public static PlacedFeature BAMBOO_COLUMN_DENSE;

    public static PlacedFeature SAKURA_CHECKED;
    public static PlacedFeature FANCY_SAKURA_CHECKED;
    public static PlacedFeature BIG_SAKURA_CHECKED;
    public static ConfiguredFeature<?, ?> FEATURE_TREES_SAKURA;
    public static PlacedFeature TREES_SAKURA;
    public static PlacedFeature TREES_SAKURA_DENSE;

    public static PlacedFeature MAPLE_RED_CHECKED;
    public static PlacedFeature MAPLE_YELLOW_CHECKED;
    public static PlacedFeature MAPLE_ORANGE_CHECKED;
    public static PlacedFeature MAPLE_GREEN_CHECKED;
    public static PlacedFeature FANCY_MAPLE_RED_CHECKED;
    public static PlacedFeature FANCY_MAPLE_YELLOW_CHECKED;
    public static PlacedFeature FANCY_MAPLE_ORANGE_CHECKED;
    public static PlacedFeature FANCY_MAPLE_GREEN_CHECKED;
    public static PlacedFeature BIG_MAPLE_RED_CHECKED;
    public static PlacedFeature BIG_MAPLE_YELLOW_CHECKED;
    public static PlacedFeature BIG_MAPLE_ORANGE_CHECKED;
    public static PlacedFeature BIG_MAPLE_GREEN_CHECKED;
    public static ConfiguredFeature<?, ?> FEATURE_TREES_MAPLE;
    public static PlacedFeature TREES_MAPLE;
    public static PlacedFeature TREES_MAPLE_DENSE;

    public static PlacedFeature UME_CHECKED;
    public static PlacedFeature FANCY_UME_CHECKED;
    public static ConfiguredFeature<?, ?> FEATURE_TREES_UME;
    public static PlacedFeature TREES_UME;

    private static boolean initialized = false;

    /**
     * Must be called after block and feature registration is complete.
     * Requires SakuraTreeFeatures.init() to be called first.
     */
    public static void init() {
        if (initialized) return;
        initialized = true;

        // Bamboo Shoot Patch
        FEATURE_PATCH_BAMBOOSHOOT = new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(
                64, 1, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(496156461L,
                new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, BlockRegistry.BAMBOOSHOOT.get().defaultBlockState(),
                List.of(BlockRegistry.BAMBOOSHOOT.get().defaultBlockState()),
                List.of(BlockRegistry.BAMBOOSHOOT.get().defaultBlockState()))))));
        PATCH_BAMBOOSHOOT = new PlacedFeature(Holder.direct(FEATURE_PATCH_BAMBOOSHOOT),
                List.of(PlacementUtils.HEIGHTMAP,
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING),
                        RarityFilter.onAverageOnceEvery(30)));

        // Bamboo Column
        FEATURE_BAMBOO_COLUMN = new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.layer(BiasedToBottomInt.of(9, 18),
                                BlockStateProvider.simple(BlockRegistry.BAMBOO_PLANT.get()))),
                        Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
        BAMBOO_COLUMN = new PlacedFeature(Holder.direct(FEATURE_BAMBOO_COLUMN),
                List.of(CountPlacement.of(15),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));

        // Wild Pepper Patch (placed at age=0, grows naturally like 1.12.2)
        FEATURE_PATCH_WILD_PEPPER = new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                32, 6, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(BlockRegistry.WILD_PEPPER.get().defaultBlockState())))));
        PATCH_WILD_PEPPER = new PlacedFeature(Holder.direct(FEATURE_PATCH_WILD_PEPPER),
                List.of(PlacementUtils.HEIGHTMAP,
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(64)));

        // Wild Vanilla Patch (placed at age=0, grows naturally like 1.12.2)
        FEATURE_PATCH_WILD_VANILLA = new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                32, 6, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(BlockRegistry.WILD_VANILLA.get().defaultBlockState())))));
        PATCH_WILD_VANILLA = new PlacedFeature(Holder.direct(FEATURE_PATCH_WILD_VANILLA),
                List.of(PlacementUtils.HEIGHTMAP,
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(48)));

        // Iron Sand Ore
        FEATURE_ORE_IRON_SAND = new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new TagMatchTest(BlockTags.SAND), BlockRegistry.IRON_SAND.get().defaultBlockState(), 12));
        ORE_IRON_SAND = new PlacedFeature(Holder.direct(FEATURE_ORE_IRON_SAND),
                List.of(CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(58), VerticalAnchor.absolute(66)),
                        BiomeFilter.biome()));

        // Sakura Diamond Ore
        FEATURE_ORE_SAKURA_DIAMOND = new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), BlockRegistry.SAKURA_DIAMOND_ORE.get().defaultBlockState(), 5));
        ORE_SAKURA_DIAMOND = new PlacedFeature(Holder.direct(FEATURE_ORE_SAKURA_DIAMOND),
                List.of(CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(34)),
                        BiomeFilter.biome()));

        // Hot Spring
        FEATURE_HOT_SPRING = new ConfiguredFeature<>(SakuraFeatureRegistry.HOT_SPRING_FEATURE.get(), NoneFeatureConfiguration.INSTANCE);
        HOT_SPRING = new PlacedFeature(Holder.direct(FEATURE_HOT_SPRING),
                List.of(PlacementUtils.HEIGHTMAP,
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(80)));

        // Dense Bamboo Column
        BAMBOO_COLUMN_DENSE = new PlacedFeature(Holder.direct(FEATURE_BAMBOO_COLUMN),
                List.of(CountPlacement.of(30),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));

        // ===== Sakura Trees =====
        SAKURA_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.SAKURA),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.SAKURA_SAPLING.get())));
        FANCY_SAKURA_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.FANCY_SAKURA),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.SAKURA_SAPLING.get())));
        BIG_SAKURA_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.BIG_SAKURA),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.SAKURA_SAPLING.get())));

        FEATURE_TREES_SAKURA = new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(Holder.direct(SAKURA_CHECKED), 0.125F),
                                new WeightedPlacedFeature(Holder.direct(FANCY_SAKURA_CHECKED), 0.05F)),
                        Holder.direct(BIG_SAKURA_CHECKED)));

        TREES_SAKURA = new PlacedFeature(Holder.direct(FEATURE_TREES_SAKURA),
                List.of(PlacementUtils.countExtra(0, 0.05F, 1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));

        TREES_SAKURA_DENSE = new PlacedFeature(Holder.direct(FEATURE_TREES_SAKURA),
                List.of(PlacementUtils.countExtra(0, 0.1F, 1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));

        // ===== Maple Trees =====
        MAPLE_RED_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.MAPLE_RED),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_RED.get())));
        MAPLE_YELLOW_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.MAPLE_YELLOW),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_YELLOW.get())));
        MAPLE_ORANGE_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.MAPLE_ORANGE),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_ORANGE.get())));
        MAPLE_GREEN_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.MAPLE_GREEN),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_GREEN.get())));

        FANCY_MAPLE_RED_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.FANCY_MAPLE_RED),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_RED.get())));
        FANCY_MAPLE_YELLOW_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.FANCY_MAPLE_YELLOW),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_YELLOW.get())));
        FANCY_MAPLE_ORANGE_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.FANCY_MAPLE_ORANGE),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_ORANGE.get())));
        FANCY_MAPLE_GREEN_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.FANCY_MAPLE_GREEN),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_GREEN.get())));

        BIG_MAPLE_RED_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.BIG_MAPLE_RED),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_RED.get())));
        BIG_MAPLE_YELLOW_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.BIG_MAPLE_YELLOW),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_YELLOW.get())));
        BIG_MAPLE_ORANGE_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.BIG_MAPLE_ORANGE),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_ORANGE.get())));
        BIG_MAPLE_GREEN_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.BIG_MAPLE_GREEN),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING_GREEN.get())));

        FEATURE_TREES_MAPLE = new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(Holder.direct(FANCY_MAPLE_RED_CHECKED), 0.02F),
                                new WeightedPlacedFeature(Holder.direct(BIG_MAPLE_RED_CHECKED), 0.05F),
                                new WeightedPlacedFeature(Holder.direct(MAPLE_YELLOW_CHECKED), 0.15F),
                                new WeightedPlacedFeature(Holder.direct(FANCY_MAPLE_YELLOW_CHECKED), 0.02F),
                                new WeightedPlacedFeature(Holder.direct(BIG_MAPLE_YELLOW_CHECKED), 0.04F),
                                new WeightedPlacedFeature(Holder.direct(MAPLE_ORANGE_CHECKED), 0.15F),
                                new WeightedPlacedFeature(Holder.direct(FANCY_MAPLE_ORANGE_CHECKED), 0.02F),
                                new WeightedPlacedFeature(Holder.direct(BIG_MAPLE_ORANGE_CHECKED), 0.04F),
                                new WeightedPlacedFeature(Holder.direct(MAPLE_GREEN_CHECKED), 0.12F),
                                new WeightedPlacedFeature(Holder.direct(FANCY_MAPLE_GREEN_CHECKED), 0.02F),
                                new WeightedPlacedFeature(Holder.direct(BIG_MAPLE_GREEN_CHECKED), 0.04F)),
                        Holder.direct(MAPLE_RED_CHECKED)));

        TREES_MAPLE = new PlacedFeature(Holder.direct(FEATURE_TREES_MAPLE),
                List.of(PlacementUtils.countExtra(0, 0.05F, 1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));

        TREES_MAPLE_DENSE = new PlacedFeature(Holder.direct(FEATURE_TREES_MAPLE),
                List.of(PlacementUtils.countExtra(10, 0.1F, 1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));

        // ===== Ume Trees =====
        UME_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.UME),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.UME_SAPLING.get())));
        FANCY_UME_CHECKED = new PlacedFeature(Holder.direct(SakuraTreeFeatures.FANCY_UME),
                List.of(PlacementUtils.filteredByBlockSurvival(BlockRegistry.UME_SAPLING.get())));

        FEATURE_TREES_UME = new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(new WeightedPlacedFeature(Holder.direct(FANCY_UME_CHECKED), 0.1F)),
                        Holder.direct(UME_CHECKED)));

        TREES_UME = new PlacedFeature(Holder.direct(FEATURE_TREES_UME),
                List.of(PlacementUtils.countExtra(0, 0.03F, 1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome()));
    }

    // ===== Helper methods =====
    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, name));
    }

    private static ResourceKey<PlacedFeature> pkey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, name));
    }
}
