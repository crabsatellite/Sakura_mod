package cn.mcmod.sakura.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import cn.mcmod.sakura.level.biome.SakuraBiomeRegistry;
import cn.mcmod.sakura.tags.SakuraBiomeTags;

import java.util.concurrent.CompletableFuture;

public class SakuraBiomeTagProvider extends BiomeTagsProvider {
    public SakuraBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, ExistingFileHelper existingFileHelper) {
        super(output, provider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Bamboo shoot biomes
        TagAppender<Biome> bambooTag = this.tag(SakuraBiomeTags.CAN_SPAWN_BAMBOO);
        bambooTag.add(Biomes.MEADOW);
        bambooTag.add(Biomes.CHERRY_GROVE);
        bambooTag.add(Biomes.BIRCH_FOREST);
        bambooTag.add(Biomes.OLD_GROWTH_BIRCH_FOREST);
        bambooTag.add(Biomes.FOREST);
        bambooTag.add(Biomes.FLOWER_FOREST);
        bambooTag.add(Biomes.DARK_FOREST);
        bambooTag.add(Biomes.PLAINS);
        bambooTag.add(Biomes.SUNFLOWER_PLAINS);
        bambooTag.add(Biomes.MANGROVE_SWAMP);
        bambooTag.add(Biomes.SWAMP);
        bambooTag.add(Biomes.JUNGLE);
        bambooTag.add(Biomes.BAMBOO_JUNGLE);
        bambooTag.add(Biomes.SPARSE_JUNGLE);

        // Sakura tree biomes - cherry groves, meadows, flower forests, and temperate forests
        TagAppender<Biome> sakuraTag = this.tag(SakuraBiomeTags.CAN_SPAWN_SAKURA_TREE);
        sakuraTag.add(Biomes.CHERRY_GROVE);
        sakuraTag.add(Biomes.MEADOW);
        sakuraTag.add(Biomes.FLOWER_FOREST);
        sakuraTag.add(Biomes.BIRCH_FOREST);
        sakuraTag.add(Biomes.OLD_GROWTH_BIRCH_FOREST);
        sakuraTag.add(Biomes.PLAINS);
        sakuraTag.add(Biomes.SUNFLOWER_PLAINS);
        sakuraTag.add(Biomes.FOREST);

        // Maple tree biomes - temperate forests with seasonal foliage
        TagAppender<Biome> mapleTag = this.tag(SakuraBiomeTags.CAN_SPAWN_MAPLE_TREE);
        mapleTag.add(Biomes.FOREST);
        mapleTag.add(Biomes.FLOWER_FOREST);
        mapleTag.add(Biomes.BIRCH_FOREST);
        mapleTag.add(Biomes.OLD_GROWTH_BIRCH_FOREST);
        mapleTag.add(Biomes.DARK_FOREST);
        mapleTag.add(Biomes.MEADOW);
        mapleTag.add(Biomes.PLAINS);
        mapleTag.add(Biomes.WINDSWEPT_FOREST);
        mapleTag.add(Biomes.TAIGA);

        // Ume (plum) tree biomes - similar to sakura but slightly rarer
        TagAppender<Biome> umeTag = this.tag(SakuraBiomeTags.CAN_SPAWN_UME_TREE);
        umeTag.add(Biomes.CHERRY_GROVE);
        umeTag.add(Biomes.MEADOW);
        umeTag.add(Biomes.FLOWER_FOREST);
        umeTag.add(Biomes.FOREST);
        umeTag.add(Biomes.PLAINS);

        // Samurai Illager spawn biomes - forests, plains, and custom sakura biomes
        // 1.12.2 had spawns in bamboo forest and maple forest; also adding vanilla biomes
        TagAppender<Biome> samuraiTag = this.tag(SakuraBiomeTags.HAS_SAMURAI_SPAWNS);
        samuraiTag.add(Biomes.PLAINS);
        samuraiTag.add(Biomes.SUNFLOWER_PLAINS);
        samuraiTag.add(Biomes.FOREST);
        samuraiTag.add(Biomes.FLOWER_FOREST);
        samuraiTag.add(Biomes.BIRCH_FOREST);
        samuraiTag.add(Biomes.OLD_GROWTH_BIRCH_FOREST);
        samuraiTag.add(Biomes.DARK_FOREST);
        samuraiTag.add(Biomes.MEADOW);
        samuraiTag.add(Biomes.CHERRY_GROVE);
        samuraiTag.add(Biomes.TAIGA);
        samuraiTag.add(Biomes.BAMBOO_JUNGLE);
        // Custom sakura biomes (from 1.12.2: BAMBOOFOREST, MAPLEFOREST)
        samuraiTag.addOptional(SakuraBiomeRegistry.BAMBOO_FOREST.location());
        samuraiTag.addOptional(SakuraBiomeRegistry.MAPLE_FOREST.location());

        // Deer spawn biomes - forests and taigas (1.12.2 had deer in bamboo/maple forests)
        TagAppender<Biome> deerTag = this.tag(SakuraBiomeTags.HAS_DEER_SPAWNS);
        deerTag.addTag(BiomeTags.IS_FOREST);
        deerTag.addTag(BiomeTags.IS_TAIGA);
        deerTag.addOptional(SakuraBiomeRegistry.BAMBOO_FOREST.location());
        deerTag.addOptional(SakuraBiomeRegistry.MAPLE_FOREST.location());

    }
}
