package cn.mcmod.sakura.level.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.level.tree.decorator.ChestnutBurrDecorator;
import cn.mcmod.sakura.level.tree.decorator.FallenLeavesDecorator;
import cn.mcmod.sakura.level.tree.decorator.MapleSapLogDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import com.mojang.datafixers.util.Pair;

public class SakuraTreeFeatures {

    public static List<Pair<ResourceKey<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>>> ENTRY = new ArrayList<>();

    // ===== Resource Keys (safe to access during class loading - no .get() calls) =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAKURA_KEY = key("sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SAKURA_KEY = key("fancy_sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_RED_KEY = key("maple_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_RED_KEY = key("fancy_maple_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_YELLOW_KEY = key("maple_yellow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_YELLOW_KEY = key("fancy_maple_yellow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_ORANGE_KEY = key("maple_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_ORANGE_KEY = key("fancy_maple_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_GREEN_KEY = key("maple_green");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_GREEN_KEY = key("fancy_maple_green");
    public static final ResourceKey<ConfiguredFeature<?, ?>> UME_KEY = key("ume");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_UME_KEY = key("fancy_ume");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_SAKURA_KEY = key("big_sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_MAPLE_RED_KEY = key("big_maple_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_MAPLE_YELLOW_KEY = key("big_maple_yellow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_MAPLE_ORANGE_KEY = key("big_maple_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_MAPLE_GREEN_KEY = key("big_maple_green");

    // ===== ConfiguredFeature instances (populated lazily by init()) =====
    public static ConfiguredFeature<?, ?> SAKURA;
    public static ConfiguredFeature<?, ?> FANCY_SAKURA;
    public static ConfiguredFeature<?, ?> MAPLE_RED;
    public static ConfiguredFeature<?, ?> FANCY_MAPLE_RED;
    public static ConfiguredFeature<?, ?> MAPLE_YELLOW;
    public static ConfiguredFeature<?, ?> FANCY_MAPLE_YELLOW;
    public static ConfiguredFeature<?, ?> MAPLE_ORANGE;
    public static ConfiguredFeature<?, ?> FANCY_MAPLE_ORANGE;
    public static ConfiguredFeature<?, ?> MAPLE_GREEN;
    public static ConfiguredFeature<?, ?> FANCY_MAPLE_GREEN;
    public static ConfiguredFeature<?, ?> UME;
    public static ConfiguredFeature<?, ?> FANCY_UME;
    public static ConfiguredFeature<?, ?> BIG_SAKURA;
    public static ConfiguredFeature<?, ?> BIG_MAPLE_RED;
    public static ConfiguredFeature<?, ?> BIG_MAPLE_YELLOW;
    public static ConfiguredFeature<?, ?> BIG_MAPLE_ORANGE;
    public static ConfiguredFeature<?, ?> BIG_MAPLE_GREEN;

    private static boolean initialized = false;

    /**
     * Must be called after block registration is complete (e.g. during FMLCommonSetupEvent).
     * This defers all BlockRegistry.XXX.get() calls until blocks are actually registered.
     */
    public static void init() {
        if (initialized) return;
        initialized = true;

        // Sakura trees
        SAKURA = registryTree(SAKURA_KEY, createSimpleBlobTree(BlockRegistry.SAKURA_LOG.get(), BlockRegistry.SAKURA_LEAVES.get()).ignoreVines());
        FANCY_SAKURA = registryTree(FANCY_SAKURA_KEY, createFancyTree(BlockRegistry.SAKURA_LOG.get(), BlockRegistry.SAKURA_LEAVES.get()));

        // Maple Red trees
        MAPLE_RED = registryTree(MAPLE_RED_KEY,
                createMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_RED.get(), BlockRegistry.FALLEN_LEAVES_RED.get(), 4, 2));
        FANCY_MAPLE_RED = registryTree(FANCY_MAPLE_RED_KEY,
                createFancyMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_RED.get(), BlockRegistry.FALLEN_LEAVES_RED.get()));

        // Maple Yellow trees
        MAPLE_YELLOW = registryTree(MAPLE_YELLOW_KEY,
                createMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_YELLOW.get(), BlockRegistry.FALLEN_LEAVES_YELLOW.get(), 4, 2));
        FANCY_MAPLE_YELLOW = registryTree(FANCY_MAPLE_YELLOW_KEY,
                createFancyMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_YELLOW.get(), BlockRegistry.FALLEN_LEAVES_YELLOW.get()));

        // Maple Orange trees
        MAPLE_ORANGE = registryTree(MAPLE_ORANGE_KEY,
                createMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_ORANGE.get(), BlockRegistry.FALLEN_LEAVES_ORANGE.get(), 4, 2));
        FANCY_MAPLE_ORANGE = registryTree(FANCY_MAPLE_ORANGE_KEY,
                createFancyMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_ORANGE.get(), BlockRegistry.FALLEN_LEAVES_ORANGE.get()));

        // Maple Green trees (with chestnut burr decorator)
        MAPLE_GREEN = registryTree(MAPLE_GREEN_KEY,
                createGreenMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_GREEN.get(), BlockRegistry.FALLEN_LEAVES_GREEN.get(), 4, 2));
        FANCY_MAPLE_GREEN = registryTree(FANCY_MAPLE_GREEN_KEY,
                createFancyGreenMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_GREEN.get(), BlockRegistry.FALLEN_LEAVES_GREEN.get()));

        // Ume (Plum) tree
        UME = registryTree(UME_KEY, createSimpleBlobTree(BlockRegistry.UME_LOG.get(), BlockRegistry.UME_LEAVES.get()).ignoreVines());
        FANCY_UME = registryTree(FANCY_UME_KEY, createFancyTree(BlockRegistry.UME_LOG.get(), BlockRegistry.UME_LEAVES.get()));

        // Big tree variants
        BIG_SAKURA = registryTree(BIG_SAKURA_KEY,
                createBigTree(BlockRegistry.SAKURA_LOG.get(), BlockRegistry.SAKURA_LEAVES.get()));
        BIG_MAPLE_RED = registryTree(BIG_MAPLE_RED_KEY,
                createBigMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_RED.get(), BlockRegistry.FALLEN_LEAVES_RED.get()));
        BIG_MAPLE_YELLOW = registryTree(BIG_MAPLE_YELLOW_KEY,
                createBigMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_YELLOW.get(), BlockRegistry.FALLEN_LEAVES_YELLOW.get()));
        BIG_MAPLE_ORANGE = registryTree(BIG_MAPLE_ORANGE_KEY,
                createBigMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_ORANGE.get(), BlockRegistry.FALLEN_LEAVES_ORANGE.get()));
        BIG_MAPLE_GREEN = registryTree(BIG_MAPLE_GREEN_KEY,
                createBigGreenMapleTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES_GREEN.get(), BlockRegistry.FALLEN_LEAVES_GREEN.get()));
    }

    // ===== Helper methods =====

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraMod.MODID, name));
    }

    private static ConfiguredFeature<?, ?> registryTree(ResourceKey<ConfiguredFeature<?, ?>> key, TreeConfiguration.TreeConfigurationBuilder tree) {
        ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> feature = new ConfiguredFeature<>(Feature.TREE, tree.build());
        ENTRY.add(new Pair<>(key, feature));
        return feature;
    }

    private static TreeConfiguration.TreeConfigurationBuilder createSimpleBlobTree(Block log, Block leaves) {
        return createStraightBlobTree(log, leaves, 4, 2, 0, 2);
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block log, Block leaves,
                                                                                     int baseHeight, int heightRandA, int heightRandB, int leaves_radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB), BlockStateProvider.simple(leaves),
                new BlobFoliagePlacer(ConstantInt.of(leaves_radius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyTree(Block log, Block leaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMapleTree(Block log, Block leaves, Block fallenLeaves,
                                                                              int radiusXZ, int searchUp) {
        return createStraightBlobTree(log, leaves, 5, 2, 0, 2)
                .ignoreVines()
                .decorators(List.of(
                        new FallenLeavesDecorator(fallenLeaves, radiusXZ, radiusXZ, searchUp, 4),
                        new MapleSapLogDecorator(0.5F)
                ));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyMapleTree(Block log, Block leaves, Block fallenLeaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines()
                .decorators(List.of(
                        new FallenLeavesDecorator(fallenLeaves, 5, 5, 3, 4),
                        new MapleSapLogDecorator(0.5F)
                ));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createGreenMapleTree(Block log, Block leaves, Block fallenLeaves,
                                                                                    int radiusXZ, int searchUp) {
        return createStraightBlobTree(log, leaves, 5, 2, 0, 2)
                .ignoreVines()
                .decorators(List.of(
                        new FallenLeavesDecorator(fallenLeaves, radiusXZ, radiusXZ, searchUp, 4),
                        new MapleSapLogDecorator(0.5F),
                        new ChestnutBurrDecorator(0.25F)
                ));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyGreenMapleTree(Block log, Block leaves, Block fallenLeaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines()
                .decorators(List.of(
                        new FallenLeavesDecorator(fallenLeaves, 5, 5, 3, 4),
                        new MapleSapLogDecorator(0.5F),
                        new ChestnutBurrDecorator(0.25F)
                ));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBigTree(Block log, Block leaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(5, 8, 4), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBigMapleTree(Block log, Block leaves, Block fallenLeaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(5, 8, 4), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines()
                .decorators(List.of(
                        new FallenLeavesDecorator(fallenLeaves, 6, 6, 3, 4),
                        new MapleSapLogDecorator(0.5F)
                ));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBigGreenMapleTree(Block log, Block leaves, Block fallenLeaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(5, 8, 4), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines()
                .decorators(List.of(
                        new FallenLeavesDecorator(fallenLeaves, 6, 6, 3, 4),
                        new MapleSapLogDecorator(0.5F),
                        new ChestnutBurrDecorator(0.25F)
                ));
    }
}
