package cn.mcmod.sakura.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.tags.SakuraBlockTags;

import java.util.concurrent.CompletableFuture;

public class SakuraBlockTagsProvider extends BlockTagsProvider {

    public SakuraBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
        super(packOutput,lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Logs
        this.tag(BlockTags.LOGS).add(BlockRegistry.STRIPPED_SAKURA_WOOD.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get(),
                BlockRegistry.SAKURA_WOOD.get(), BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_SAKURA_LOG.get(), BlockRegistry.STRIPPED_MAPLE_LOG.get(),
                BlockRegistry.SAKURA_LOG.get(), BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_SAP_LOG.get(),
                BlockRegistry.UME_LOG.get(),
                BlockRegistry.STRIPPED_UME_LOG.get(), BlockRegistry.UME_WOOD.get(), BlockRegistry.STRIPPED_UME_WOOD.get());
        this.tag(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.STRIPPED_SAKURA_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_WOOD.get(), BlockRegistry.SAKURA_WOOD.get(),
                BlockRegistry.MAPLE_WOOD.get(), BlockRegistry.STRIPPED_SAKURA_LOG.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.SAKURA_LOG.get(), BlockRegistry.MAPLE_LOG.get(),
                BlockRegistry.MAPLE_SAP_LOG.get(),
                BlockRegistry.UME_LOG.get(),
                BlockRegistry.STRIPPED_UME_LOG.get(), BlockRegistry.UME_WOOD.get(), BlockRegistry.STRIPPED_UME_WOOD.get());

        // Leaves
        this.tag(BlockTags.LEAVES).add(BlockRegistry.SAKURA_LEAVES.get(), BlockRegistry.MAPLE_LEAVES_RED.get(),
                BlockRegistry.MAPLE_LEAVES_GREEN.get(), BlockRegistry.MAPLE_LEAVES_ORANGE.get(),
                BlockRegistry.MAPLE_LEAVES_YELLOW.get(),
                BlockRegistry.UME_LEAVES.get());

        // Saplings
        this.tag(BlockTags.SAPLINGS).add(BlockRegistry.SAKURA_SAPLING.get(), BlockRegistry.MAPLE_SAPLING_RED.get(),
                BlockRegistry.MAPLE_SAPLING_GREEN.get(), BlockRegistry.MAPLE_SAPLING_ORANGE.get(),
                BlockRegistry.MAPLE_SAPLING_YELLOW.get(),
                BlockRegistry.UME_SAPLING.get());

        // Planks
        this.tag(BlockTags.PLANKS).add(BlockRegistry.SAKURA_PLANK.get(), BlockRegistry.BAMBOO_PLANK.get(),
                BlockRegistry.MAPLE_PLANK.get());

        // Crops (existing + new)
        this.tag(BlockTags.CROPS).add(BlockRegistry.RICE_CROP.get(), BlockRegistry.BUCKWHEAT_CROP.get(),
                BlockRegistry.CABBAGE_CROP.get(), BlockRegistry.EGGPLANT_CROP.get(), BlockRegistry.ONION_CROP.get(),
                BlockRegistry.RADISH_CROP.get(), BlockRegistry.RAPESEED_CROP.get(), BlockRegistry.REDBEAN_CROP.get(),
                BlockRegistry.RICE_CROP_ROOT.get(), BlockRegistry.TARO_CROP.get(), BlockRegistry.TOMATO_CROP.get(),
                BlockRegistry.SOYBEAN_CROP.get(),
                BlockRegistry.PEPPER_CROP.get(), BlockRegistry.VANILLA_CROP.get(),
                BlockRegistry.GRAPE_CROP.get(), BlockRegistry.HOPS_CROP.get(),
                BlockRegistry.SEAWEED_CROP.get());

        // Wooden stairs
        this.tag(BlockTags.WOODEN_STAIRS).add(
                BlockRegistry.SAKURA_STAIRS.get(),
                BlockRegistry.MAPLE_STAIRS.get(),
                BlockRegistry.BAMBOO_PLANK_STAIRS.get());

        // Wooden slabs
        this.tag(BlockTags.WOODEN_SLABS).add(
                BlockRegistry.SAKURA_SLAB.get(),
                BlockRegistry.MAPLE_SLAB.get(),
                BlockRegistry.BAMBOO_PLANK_SLAB.get());

        // Wooden fences
        this.tag(BlockTags.WOODEN_FENCES).add(
                BlockRegistry.BAMBOO_FENCE.get(),
                BlockRegistry.BAMBOO_FENCE_SUNBURNT.get());

        // Fence gates (none registered currently, but add the tag for future use)
        // this.tag(BlockTags.FENCE_GATES)

        // Wooden doors
        this.tag(BlockTags.WOODEN_DOORS).add(
                BlockRegistry.BAMBOO_DOOR.get());

        // Mineable with pickaxe
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlockRegistry.STONE_MORTAR.get(),
                BlockRegistry.SAKURA_DIAMOND_ORE.get(),
                BlockRegistry.IRON_SAND.get(),
                BlockRegistry.KAWARA_BLOCK.get(),
                BlockRegistry.KAWARA.get(),
                BlockRegistry.STONE_LANTERN.get(),
                BlockRegistry.COBBLESTONE_LANTERN.get(),
                BlockRegistry.MOSSY_STONE_LANTERN.get(),
                BlockRegistry.RED_LANTERN.get(),
                BlockRegistry.WHITE_LANTERN.get(),
                BlockRegistry.BAMBOO_LANTERN.get(),
                BlockRegistry.WINDBELL.get(),
                BlockRegistry.COOKING_POT.get(),
                BlockRegistry.FERMENTER.get(),
                BlockRegistry.DISTILLER.get(),
                BlockRegistry.SAKURA_DIAMOND_BLOCK.get(),
                BlockRegistry.TATARA.get(),
                BlockRegistry.BARREL_OUT.get());

        // Mineable with axe
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                // Logs, wood, stripped variants
                BlockRegistry.SAKURA_LOG.get(),
                BlockRegistry.STRIPPED_SAKURA_LOG.get(),
                BlockRegistry.SAKURA_WOOD.get(),
                BlockRegistry.STRIPPED_SAKURA_WOOD.get(),
                BlockRegistry.MAPLE_LOG.get(),
                BlockRegistry.MAPLE_SAP_LOG.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(),
                BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_WOOD.get(),
                BlockRegistry.UME_LOG.get(),
                BlockRegistry.STRIPPED_UME_LOG.get(),
                BlockRegistry.UME_WOOD.get(),
                BlockRegistry.STRIPPED_UME_WOOD.get(),
                // Planks
                BlockRegistry.SAKURA_PLANK.get(),
                BlockRegistry.MAPLE_PLANK.get(),
                BlockRegistry.BAMBOO_PLANK.get(),
                // Bamboo blocks
                BlockRegistry.BAMBOO_BLOCK.get(),
                BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get(),
                BlockRegistry.BAMBOO_CHARCOAL_BLOCK.get(),
                // Stairs
                BlockRegistry.SAKURA_STAIRS.get(),
                BlockRegistry.MAPLE_STAIRS.get(),
                BlockRegistry.BAMBOO_PLANK_STAIRS.get(),
                BlockRegistry.BAMBOO_STAIRS.get(),
                BlockRegistry.BAMBOO_STAIRS_SUNBURNT.get(),
                // Slabs
                BlockRegistry.SAKURA_SLAB.get(),
                BlockRegistry.MAPLE_SLAB.get(),
                BlockRegistry.BAMBOO_PLANK_SLAB.get(),
                BlockRegistry.BAMBOO_SLAB.get(),
                BlockRegistry.BAMBOO_SLAB_SUNBURNT.get(),
                // Fences & doors
                BlockRegistry.BAMBOO_FENCE.get(),
                BlockRegistry.BAMBOO_FENCE_SUNBURNT.get(),
                BlockRegistry.BAMBOO_DOOR.get(),
                // Crop support blocks
                BlockRegistry.GRAPE_SPLINT.get(),
                BlockRegistry.GRAPE_SPLINT_STAND.get(),
                BlockRegistry.PEPPER_SPLINT.get(),
                BlockRegistry.VANILLA_SPLINT.get(),
                // Furniture & machines
                BlockRegistry.CHOPPING_BOARD.get(),
                BlockRegistry.OBON.get(),
                BlockRegistry.ANDON.get(),
                BlockRegistry.TAIKO.get(),
                BlockRegistry.FUTON.get(),
                BlockRegistry.SHOJI.get(),
                BlockRegistry.MAPLE_CAULDRON.get(),
                BlockRegistry.MAPLE_SPILE.get(),
                BlockRegistry.CAMPFIRE_IDLE.get(),
                BlockRegistry.CAMPFIRE_LIT.get(),
                BlockRegistry.CAMPFIRE_POT_IDLE.get(),
                BlockRegistry.CAMPFIRE_POT_LIT.get(),
                BlockRegistry.RAMEN_BLOCK.get(),
                BlockRegistry.SOBA_BLOCK.get(),
                BlockRegistry.PASTA_BLOCK.get(),
                BlockRegistry.UDON_UNFINISHED.get(),
                BlockRegistry.UDON_BLOCK.get(),
                BlockRegistry.STRAW_WEB.get(),
                BlockRegistry.BARREL_OUT.get());

        // Mineable with shovel
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                BlockRegistry.IRON_SAND.get());

        // Mineable with hoe (leaves + straw/tatami)
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                // Tree leaves
                BlockRegistry.SAKURA_LEAVES.get(),
                BlockRegistry.MAPLE_LEAVES_RED.get(),
                BlockRegistry.MAPLE_LEAVES_GREEN.get(),
                BlockRegistry.MAPLE_LEAVES_ORANGE.get(),
                BlockRegistry.MAPLE_LEAVES_YELLOW.get(),
                BlockRegistry.UME_LEAVES.get(),
                // Fallen leaves
                BlockRegistry.FALLEN_LEAVES_RED.get(),
                BlockRegistry.FALLEN_LEAVES_YELLOW.get(),
                BlockRegistry.FALLEN_LEAVES_ORANGE.get(),
                BlockRegistry.FALLEN_LEAVES_GREEN.get(),
                BlockRegistry.TATAMI_CARPET.get(),
                BlockRegistry.TATAMI_CARPET_WAXED.get(),
                BlockRegistry.TATAMI_CARPET_TAN.get(),
                BlockRegistry.TATAMI_CARPET_TAN_WAXED.get(),
                BlockRegistry.TATAMI.get(),
                BlockRegistry.TATAMI_WAXED.get(),
                BlockRegistry.TATAMI_SUNBURNT.get(),
                BlockRegistry.TATAMI_SLAB.get(),
                BlockRegistry.TATAMI_SLAB_WAXED.get(),
                BlockRegistry.TATAMI_SLAB_SUNBURNT.get(),
                BlockRegistry.STRAW_BLOCK.get(),
                BlockRegistry.STRAW_STAIRS.get(),
                BlockRegistry.STRAW_SLAB.get(),
                BlockRegistry.MUSHROOM_FALLEN_LEAVES.get(),
                BlockRegistry.ZABUTON.get(),
                BlockRegistry.TATAMI_TAN.get(),
                BlockRegistry.TATAMI_TAN_NS.get(),
                BlockRegistry.TATAMI_NS.get(),
                BlockRegistry.TATAMI_HALF.get(),
                BlockRegistry.TATAMI_NS_HALF.get(),
                BlockRegistry.TATAMI_TAN_HALF.get(),
                BlockRegistry.TATAMI_TAN_NS_HALF.get());

        // Ore needs pickaxe at iron level
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                BlockRegistry.SAKURA_DIAMOND_ORE.get(),
                BlockRegistry.SAKURA_DIAMOND_BLOCK.get());

        // Beacon base blocks
        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(
                BlockRegistry.SAKURA_DIAMOND_BLOCK.get());

        // Forge ore tags
        this.tag(Tags.Blocks.ORES).add(
                BlockRegistry.SAKURA_DIAMOND_ORE.get());
        this.tag(Tags.Blocks.ORES_DIAMOND).add(
                BlockRegistry.SAKURA_DIAMOND_ORE.get());

        // Forge sand tag
        this.tag(Tags.Blocks.SANDS).add(
                BlockRegistry.IRON_SAND.get());

        // Mineable with custom tools
        this.tag(SakuraBlockTags.MINEABLE_WITH_KNIFE).add(
                BlockRegistry.NOREN_WHITE.get(),
                BlockRegistry.NOREN_BLUE.get(),
                BlockRegistry.NOREN_PINK.get());
    }

    @Override
    public String getName() {
        return "Sakura Blocks' Tags";
    }
}
