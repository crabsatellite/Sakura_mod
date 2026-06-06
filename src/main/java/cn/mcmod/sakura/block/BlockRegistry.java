package cn.mcmod.sakura.block;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.crops.GrapeLeavesBlock;
import cn.mcmod.sakura.block.crops.GrapeSplintBlock;
import cn.mcmod.sakura.block.crops.GrapeSplintStandBlock;
import cn.mcmod.sakura.block.crops.GrapeVineBlock;
import cn.mcmod.sakura.block.crops.PepperSplintBlock;
import cn.mcmod.sakura.block.crops.RiceCrop;
import cn.mcmod.sakura.block.crops.RiceCropRoot;
import cn.mcmod.sakura.block.crops.VanillaSplintBlock;
import cn.mcmod.sakura.block.foods.NabeBlock;
import cn.mcmod.sakura.block.foods.TeishokuBlock;
import cn.mcmod.sakura.block.foods.TeishokuFinishedBlock;
import cn.mcmod.sakura.block.machines.BarrelOutputBlock;
import cn.mcmod.sakura.block.machines.ChoppingBoardBlock;
import cn.mcmod.sakura.block.machines.CookingPotBlock;
import cn.mcmod.sakura.block.machines.DistillerBlock;
import cn.mcmod.sakura.block.machines.FermenterBlock;
import cn.mcmod.sakura.block.machines.MapleCauldronBlock;
import cn.mcmod.sakura.block.machines.MapleSpileBlock;
import cn.mcmod.sakura.block.machines.StoneMortarBlock;
import cn.mcmod.sakura.block.machines.TataraBlock;
import cn.mcmod.sakura.block.noodles.BlockNoren;
import cn.mcmod.sakura.block.noodles.BlockPasta;
import cn.mcmod.sakura.block.noodles.BlockRamen;
import cn.mcmod.sakura.block.noodles.BlockSoba;
import cn.mcmod.sakura.block.noodles.BlockUdon;
import cn.mcmod.sakura.block.noodles.BlockUdonUnfinished;
import cn.mcmod.sakura.client.particle.ParticleRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.level.tree.MapleTreeGrower;
import cn.mcmod.sakura.level.tree.SakuraTreeFeatures;
import cn.mcmod.sakura.level.tree.SakuraTreeGrower;
import cn.mcmod.sakura.level.tree.UmeTreeGrower;
import cn.mcmod_mmf.mmlib.block.Age3CropBlock;
import cn.mcmod_mmf.mmlib.block.BaseCropBlock;
import cn.mcmod_mmf.mmlib.block.BaseHorizonBlock;
import cn.mcmod_mmf.mmlib.block.FacingSlab;
import cn.mcmod_mmf.mmlib.block.HighCropBlock;
import cn.mcmod_mmf.mmlib.item.info.FoodInfo;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK,
            SakuraMod.MODID);

    public static final DeferredHolder<Block, Block> SAKURA_LEAVES = BLOCKS.register("sakuraleaves",
            () -> new SakuraLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.SAKURA_LEAF));

    public static final DeferredHolder<Block, Block> MAPLE_LEAVES_RED = BLOCKS.register("mapleleaves_red",
            () -> new SakuraLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.RED_MAPLE_LEAF));
    public static final DeferredHolder<Block, Block> MAPLE_LEAVES_GREEN = BLOCKS.register("mapleleaves_green",
            () -> new SakuraLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.GREEN_MAPLE_LEAF));
    public static final DeferredHolder<Block, Block> MAPLE_LEAVES_YELLOW = BLOCKS.register("mapleleaves_yellow",
            () -> new SakuraLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.YELLOW_MAPLE_LEAF));
    public static final DeferredHolder<Block, Block> MAPLE_LEAVES_ORANGE = BLOCKS.register("mapleleaves_orange",
            () -> new SakuraLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.ORANGE_MAPLE_LEAF));

    public static final DeferredHolder<Block, RotatedPillarBlock> SAKURA_LOG = BLOCKS.register("sakura_log",
            () -> log(MapColor.WOOD, MapColor.PODZOL));

    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_SAKURA_LOG = BLOCKS.register("stripped_sakura_log",
            () -> log(MapColor.WOOD, MapColor.WOOD));

    public static final DeferredHolder<Block, RotatedPillarBlock> SAKURA_WOOD = BLOCKS.register("sakura_wood",
            () -> log(MapColor.PODZOL, MapColor.PODZOL));

    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_SAKURA_WOOD = BLOCKS
            .register("stripped_sakura_wood", () -> log(MapColor.WOOD, MapColor.WOOD));

    public static final DeferredHolder<Block, SaplingBlock> SAKURA_SAPLING = BLOCKS.register("sakura_sapling",
            () -> sapling(SakuraTreeGrower.GROWER));

    public static final DeferredHolder<Block, RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log",
            MapleTreeLogBlock::new);

    public static final DeferredHolder<Block, RotatedPillarBlock> MAPLE_SAP_LOG = BLOCKS.register("maple_sap_log",
            MapleTreeSapLogBlock::new);

    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log",
            () -> log(MapColor.WOOD, MapColor.WOOD));

    public static final DeferredHolder<Block, RotatedPillarBlock> MAPLE_WOOD = BLOCKS.register("maple_wood",
            () -> log(MapColor.PODZOL, MapColor.PODZOL));

    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood",
            () -> log(MapColor.WOOD, MapColor.WOOD));

    public static final DeferredHolder<Block, RotatedPillarBlock> BAMBOO_BLOCK = BLOCKS.register("bamboo_block",
            BambooBlock::new);
    public static final DeferredHolder<Block, RotatedPillarBlock> BAMBOO_BLOCK_SUNBURNT = BLOCKS
            .register("bamboo_block_sunburnt", () -> simplebambooBlock(MapColor.SAND, MapColor.WOOD));
    public static final DeferredHolder<Block, RotatedPillarBlock> BAMBOO_CHARCOAL_BLOCK = BLOCKS.register(
            "bamboo_charcoal_block", () -> simplebambooBlock(MapColor.COLOR_GRAY, MapColor.COLOR_BLACK));

    public static final DeferredHolder<Block, Block> MAPLE_SAPLING_RED = BLOCKS.register("maple_sapling_red",
            () -> sapling(MapleTreeGrower.create("maple",SakuraTreeFeatures.MAPLE_RED_KEY, SakuraTreeFeatures.FANCY_MAPLE_RED_KEY, SakuraTreeFeatures.BIG_MAPLE_RED_KEY)));
    public static final DeferredHolder<Block, Block> MAPLE_SAPLING_GREEN = BLOCKS.register("maple_sapling_green",
            () -> sapling(MapleTreeGrower.create("maple",SakuraTreeFeatures.MAPLE_GREEN_KEY, SakuraTreeFeatures.FANCY_MAPLE_GREEN_KEY, SakuraTreeFeatures.BIG_MAPLE_GREEN_KEY)));
    public static final DeferredHolder<Block, Block> MAPLE_SAPLING_YELLOW = BLOCKS.register("maple_sapling_yellow",
            () -> sapling(MapleTreeGrower.create("maple",SakuraTreeFeatures.MAPLE_YELLOW_KEY, SakuraTreeFeatures.FANCY_MAPLE_YELLOW_KEY, SakuraTreeFeatures.BIG_MAPLE_YELLOW_KEY)));
    public static final DeferredHolder<Block, Block> MAPLE_SAPLING_ORANGE = BLOCKS.register("maple_sapling_orange",
            () -> sapling(MapleTreeGrower.create("maple",SakuraTreeFeatures.MAPLE_ORANGE_KEY, SakuraTreeFeatures.FANCY_MAPLE_ORANGE_KEY, SakuraTreeFeatures.BIG_MAPLE_ORANGE_KEY)));

    public static final DeferredHolder<Block, Block> BAMBOO_PLANT = BLOCKS.register("bamboo_plant", BambooPlant::new);
    public static final DeferredHolder<Block, Block> BAMBOOSHOOT = BLOCKS.register("bamboo_shoot", BambooShoot::new);

    public static final DeferredHolder<Block, Block> SAKURA_PLANK = BLOCKS.register("plank_sakura",
            () -> plank(MapColor.WOOD));
    public static final DeferredHolder<Block, Block> MAPLE_PLANK = BLOCKS.register("plank_maple",
            () -> plank(MapColor.SAND));
    public static final DeferredHolder<Block, Block> BAMBOO_PLANK = BLOCKS.register("plank_bamboo",
            () -> plank(MapColor.SAND));
    
    public static final DeferredHolder<Block, Block> STRAW_BLOCK = BLOCKS.register("straw_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    public static final DeferredHolder<Block, Block> TATAMI = BLOCKS.register("tatami",
            () -> new TatamiBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, Block> TATAMI_WAXED = BLOCKS.register("tatami_waxed",
            () -> new BaseHorizonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, Block> TATAMI_SUNBURNT = BLOCKS.register("tatami_sunburnt",
            () -> new BaseHorizonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_SLAB = BLOCKS.register("tatami_slab",
            () -> new TatamiSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_SLAB_WAXED = BLOCKS.register("tatami_slab_waxed",
            () -> new FacingSlab(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_SLAB_SUNBURNT = BLOCKS.register("tatami_slab_sunburnt",
            () -> new FacingSlab(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    public static final DeferredHolder<Block, Block> RICE_CROP_ROOT = BLOCKS.register("rice_crop_root",
            () -> new RiceCropRoot(cropProperties()));
    public static final DeferredHolder<Block, Block> RICE_CROP = BLOCKS.register("rice_crop",
            () -> new RiceCrop(cropProperties()));

    public static final DeferredHolder<Block, Block> CABBAGE_CROP = BLOCKS.register("cabbage_crop",
            () -> new BaseCropBlock(cropProperties(), ItemRegistry.CABBAGE_SEEDS));

    public static final DeferredHolder<Block, Block> RADISH_CROP = BLOCKS.register("radish_crop",
            () -> new Age3CropBlock(cropProperties(), ItemRegistry.RADISH_SEEDS));

    public static final DeferredHolder<Block, Block> ONION_CROP = BLOCKS.register("onion_crop",
            () -> new Age3CropBlock(cropProperties(), ItemRegistry.ONION_SEEDS));

    public static final DeferredHolder<Block, Block> REDBEAN_CROP = BLOCKS.register("redbean_crop",
            () -> new Age3CropBlock(cropProperties(), ItemRegistry.RED_BEAN));
    
    public static final DeferredHolder<Block, Block> SOYBEAN_CROP = BLOCKS.register("soybean_crop",
            () -> new Age3CropBlock(cropProperties(), ItemRegistry.SOYBEAN));

    public static final DeferredHolder<Block, Block> RAPESEED_CROP = BLOCKS.register("rapeseed_crop",
            () -> new BaseCropBlock(cropProperties(), ItemRegistry.RAPESEEDS));

    public static final DeferredHolder<Block, Block> BUCKWHEAT_CROP = BLOCKS.register("buckwheat_crop",
            () -> new BaseCropBlock(cropProperties(), ItemRegistry.BUCKWHEAT));

    public static final DeferredHolder<Block, Block> TARO_CROP = BLOCKS.register("taro_crop",
            () -> new Age3CropBlock(cropProperties(), ItemRegistry.TARO));

    public static final DeferredHolder<Block, Block> TOMATO_CROP = BLOCKS.register("tomato_crop",
            () -> new HighCropBlock(cropProperties(), ItemRegistry.TOMATO_SEEDS));

    public static final DeferredHolder<Block, Block> EGGPLANT_CROP = BLOCKS.register("eggplant_crop",
            () -> new HighCropBlock(cropProperties(), ItemRegistry.EGGPLANT_SEEDS));

    public static final DeferredHolder<Block, Block> PEPPER_CROP = BLOCKS.register("pepper_crop",
            () -> new HighCropBlock(cropProperties(), ItemRegistry.PEPPER_SEEDS));

    public static final DeferredHolder<Block, Block> VANILLA_CROP = BLOCKS.register("vanilla_crop",
            () -> new HighCropBlock(cropProperties(), ItemRegistry.VANILLA_SEEDS));

    public static final DeferredHolder<Block, Block> GRAPE_CROP = BLOCKS.register("grape_crop",
            () -> new HighCropBlock(cropProperties(), ItemRegistry.GRAPE_SEEDS));

    public static final DeferredHolder<Block, Block> HOPS_CROP = BLOCKS.register("hops_crop",
            () -> new HighCropBlock(cropProperties(), ItemRegistry.HOP_SEEDS));

    public static final DeferredHolder<Block, Block> SEAWEED_CROP = BLOCKS.register("seaweed_crop",
            () -> new BaseCropBlock(cropProperties(), ItemRegistry.SEAWEED_SEEDS));

    // Crop support blocks
    public static final DeferredHolder<Block, Block> PEPPER_SPLINT = BLOCKS.register("pepper_splint",
            PepperSplintBlock::new);
    public static final DeferredHolder<Block, Block> VANILLA_SPLINT = BLOCKS.register("vanilla_splint",
            VanillaSplintBlock::new);
    public static final DeferredHolder<Block, Block> GRAPE_SPLINT_STAND = BLOCKS.register("grape_splint_stand",
            GrapeSplintStandBlock::new);
    public static final DeferredHolder<Block, Block> GRAPE_VINE = BLOCKS.register("grapevine",
            GrapeVineBlock::new);
    public static final DeferredHolder<Block, Block> GRAPE_SPLINT = BLOCKS.register("grape_splint",
            GrapeSplintBlock::new);
    public static final DeferredHolder<Block, Block> GRAPE_LEAVES = BLOCKS.register("grapeleaves",
            GrapeLeavesBlock::new);

    public static final DeferredHolder<Block, Block> STONE_MORTAR = BLOCKS.register("stone_mortar", StoneMortarBlock::new);
    public static final DeferredHolder<Block, Block> COOKING_POT = BLOCKS.register("cooking_pot", CookingPotBlock::new);
    public static final DeferredHolder<Block, Block> FERMENTER = BLOCKS.register("fermenter", FermenterBlock::new);
    public static final DeferredHolder<Block, Block> DISTILLER = BLOCKS.register("distiller", DistillerBlock::new);
    public static final DeferredHolder<Block, Block> OBON = BLOCKS.register("obon", ObonBlock::new);
    public static final DeferredHolder<Block, Block> CHOPPING_BOARD = BLOCKS.register("chopping_board", ChoppingBoardBlock::new);
    public static final DeferredHolder<Block, Block> TEISHOKU_FINISHED = BLOCKS.register("teishoku_finished", TeishokuFinishedBlock::new);
    public static final DeferredHolder<Block, Block> TEISHOKU_FISH_SALT = BLOCKS.register("teishoku_fish_salt", 
            ()->new TeishokuBlock(FoodInfo.builder().amountAndCalories(8, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_FISH_COOKED = BLOCKS.register("teishoku_fish_cooked", 
            ()->new TeishokuBlock(FoodInfo.builder().amountAndCalories(8, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_FISH_RAW = BLOCKS.register("teishoku_fish_raw", 
            ()->new TeishokuBlock(FoodInfo.builder().amountAndCalories(6, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_TAMAGOYAKI = BLOCKS.register("teishoku_tamagoyaki", 
            ()->new TeishokuBlock(FoodInfo.builder().amountAndCalories(6, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_YAKINIKU = BLOCKS.register("teishoku_yakiniku", 
            ()->new TeishokuBlock(FoodInfo.builder().amountAndCalories(10, 0.8f).build()));
    
    public static final DeferredHolder<Block, Block> NABE_SUKIYAKI = BLOCKS.register("nabe_sukiyaki", 
            ()->new NabeBlock(FoodInfo.builder().amountAndCalories(12, 1f).build()));
    public static final DeferredHolder<Block, Block> NABE_ODEN = BLOCKS.register("nabe_oden",
            ()->new NabeBlock(FoodInfo.builder().amountAndCalories(12, 1f).build()));

    // Stairs
    public static final DeferredHolder<Block, Block> SAKURA_STAIRS = BLOCKS.register("stairs_plank_sakura",
            () -> new net.minecraft.world.level.block.StairBlock(SAKURA_PLANK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    public static final DeferredHolder<Block, Block> MAPLE_STAIRS = BLOCKS.register("stairs_plank_maple",
            () -> new net.minecraft.world.level.block.StairBlock(MAPLE_PLANK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    public static final DeferredHolder<Block, Block> BAMBOO_PLANK_STAIRS = BLOCKS.register("stairs_plank_bamboo",
            () -> new net.minecraft.world.level.block.StairBlock(BAMBOO_PLANK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    public static final DeferredHolder<Block, Block> BAMBOO_STAIRS = BLOCKS.register("bamboo_stair",
            () -> new net.minecraft.world.level.block.StairBlock(BAMBOO_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredHolder<Block, Block> BAMBOO_STAIRS_SUNBURNT = BLOCKS.register("bamboo_stair_sunburnt",
            () -> new net.minecraft.world.level.block.StairBlock(BAMBOO_BLOCK_SUNBURNT.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredHolder<Block, Block> STRAW_STAIRS = BLOCKS.register("straw_stair",
            () -> new net.minecraft.world.level.block.StairBlock(STRAW_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    // Slabs
    public static final DeferredHolder<Block, Block> SAKURA_SLAB = BLOCKS.register("slab_plank_sakura",
            () -> new net.minecraft.world.level.block.SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredHolder<Block, Block> MAPLE_SLAB = BLOCKS.register("slab_plank_maple",
            () -> new net.minecraft.world.level.block.SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredHolder<Block, Block> BAMBOO_PLANK_SLAB = BLOCKS.register("slab_plank_bamboo",
            () -> new net.minecraft.world.level.block.SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final DeferredHolder<Block, Block> BAMBOO_SLAB = BLOCKS.register("bamboo_slab",
            () -> new net.minecraft.world.level.block.SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredHolder<Block, Block> BAMBOO_SLAB_SUNBURNT = BLOCKS.register("bamboo_slab_sunburnt",
            () -> new net.minecraft.world.level.block.SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredHolder<Block, Block> STRAW_SLAB = BLOCKS.register("slab_straw_block",
            () -> new net.minecraft.world.level.block.SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    // Fences
    public static final DeferredHolder<Block, Block> BAMBOO_FENCE = BLOCKS.register("bamboo_fence",
            () -> new net.minecraft.world.level.block.FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredHolder<Block, Block> BAMBOO_FENCE_SUNBURNT = BLOCKS.register("bamboo_fence_sunburnt",
            () -> new net.minecraft.world.level.block.FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));

    // Door
    public static final DeferredHolder<Block, Block> BAMBOO_DOOR = BLOCKS.register("bamboo_door",
            () -> new net.minecraft.world.level.block.DoorBlock(net.minecraft.world.level.block.state.properties.BlockSetType.BAMBOO, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_DOOR)));

    // Fallen Leaves
    public static final DeferredHolder<Block, Block> FALLEN_LEAVES_RED = BLOCKS.register("fallen_leaves_red",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_RED).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> FALLEN_LEAVES_YELLOW = BLOCKS.register("fallen_leaves_yellow",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> FALLEN_LEAVES_ORANGE = BLOCKS.register("fallen_leaves_orange",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> FALLEN_LEAVES_GREEN = BLOCKS.register("fallen_leaves_green",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_GREEN).sound(SoundType.GRASS)));

    // Missing Teishoku blocks
    public static final DeferredHolder<Block, Block> TEISHOKU_TEMPURA = BLOCKS.register("teishoku_tempura",
            () -> new TeishokuBlock(FoodInfo.builder().amountAndCalories(8, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_FRIED = BLOCKS.register("teishoku_fried",
            () -> new TeishokuBlock(FoodInfo.builder().amountAndCalories(8, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_KATSU = BLOCKS.register("teishoku_katsu",
            () -> new TeishokuBlock(FoodInfo.builder().amountAndCalories(10, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEISHOKU_BURGER = BLOCKS.register("teishoku_burger",
            () -> new TeishokuBlock(FoodInfo.builder().amountAndCalories(10, 0.8f).build()));

    // Lantern shapes (from Blockbench models)
    public static final VoxelShape STONE_LANTERN_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape BAMBOO_LANTERN_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);
    public static final VoxelShape PAPER_LANTERN_SHAPE = Block.box(5.5D, 0.0D, 5.5D, 10.5D, 16.0D, 10.5D);

    // Lanterns
    public static final DeferredHolder<Block, Block> STONE_LANTERN = BLOCKS.register("stone_lantern",
            () -> new SakuraLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.STONE).sound(SoundType.STONE).lightLevel(s -> 15).noOcclusion(), STONE_LANTERN_SHAPE));
    public static final DeferredHolder<Block, Block> COBBLESTONE_LANTERN = BLOCKS.register("cobblestone_lantern",
            () -> new SakuraLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.STONE).sound(SoundType.STONE).lightLevel(s -> 15).noOcclusion(), STONE_LANTERN_SHAPE));
    public static final DeferredHolder<Block, Block> MOSSY_STONE_LANTERN = BLOCKS.register("mossy_stone_lantern",
            () -> new SakuraLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.STONE).sound(SoundType.STONE).lightLevel(s -> 15).noOcclusion(), STONE_LANTERN_SHAPE));
    public static final DeferredHolder<Block, Block> RED_LANTERN = BLOCKS.register("red_lantern",
            () -> new SakuraLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.COLOR_RED).lightLevel(s -> 15).noOcclusion(), PAPER_LANTERN_SHAPE));
    public static final DeferredHolder<Block, Block> WHITE_LANTERN = BLOCKS.register("white_lantern",
            () -> new SakuraLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.SNOW).lightLevel(s -> 15).noOcclusion(), PAPER_LANTERN_SHAPE));
    public static final DeferredHolder<Block, Block> BAMBOO_LANTERN = BLOCKS.register("bamboo_lantern",
            () -> new SakuraLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.SAND).sound(SoundType.BAMBOO).lightLevel(s -> 15).noOcclusion(), BAMBOO_LANTERN_SHAPE));

    // Kawara (roof tiles)
    public static final DeferredHolder<Block, Block> KAWARA_BLOCK = BLOCKS.register("kawara_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_GRAY).strength(1.5F, 6.0F).sound(SoundType.STONE)));
    public static final DeferredHolder<Block, Block> KAWARA = BLOCKS.register("kawara",
            () -> new net.minecraft.world.level.block.StairBlock(KAWARA_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_GRAY).strength(1.5F, 6.0F).sound(SoundType.STONE)));

    // Decorative furniture
    public static final DeferredHolder<Block, Block> WINDBELL = BLOCKS.register("windbell",
            () -> new WindBellBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.METAL).noOcclusion().noCollission()));
    public static final DeferredHolder<Block, Block> ANDON = BLOCKS.register("andon",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(0.5F).sound(SoundType.WOOD).lightLevel(s -> 14).noOcclusion()));
    public static final DeferredHolder<Block, Block> KITUNEBI = BLOCKS.register("kitunebi",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0F).sound(SoundType.WOOL).lightLevel(s -> 15).noOcclusion().noCollission()));
    public static final DeferredHolder<Block, Block> ZABUTON = BLOCKS.register("zabuton",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).mapColor(MapColor.COLOR_RED)));
    public static final DeferredHolder<Block, Block> FUTON = BLOCKS.register("futon",
            FutonBlock::new);
    public static final DeferredHolder<Block, Block> TAIKO = BLOCKS.register("taiko",
            () -> new TaikoBlock());

    // Noren (curtains)
    public static final DeferredHolder<Block, Block> NOREN_WHITE = BLOCKS.register("noren_white",
            () -> new BlockNoren(MapColor.SNOW));
    public static final DeferredHolder<Block, Block> NOREN_BLUE = BLOCKS.register("noren_blue",
            () -> new BlockNoren(MapColor.COLOR_BLUE));
    public static final DeferredHolder<Block, Block> NOREN_PINK = BLOCKS.register("noren_pink",
            () -> new BlockNoren(MapColor.COLOR_PINK));

    // World generation blocks
    public static final DeferredHolder<Block, Block> HOT_SPRING_WATER = BLOCKS.register("hot_spring_water",
            () -> new HotSpringBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER)
                    .noCollission().strength(100F).noLootTable().lightLevel(s -> 3)));

    public static final DeferredHolder<Block, Block> SAKURA_DIAMOND_ORE = BLOCKS.register("sakura_diamond_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)));

    public static final DeferredHolder<Block, Block> IRON_SAND = BLOCKS.register("iron_sand",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_GRAY)));

    public static final DeferredHolder<Block, Block> WILD_PEPPER = BLOCKS.register("wild_pepper",
            () -> new cn.mcmod.sakura.block.crops.WildCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)
                    .strength(0.2F), ItemRegistry.PEPPER_SEEDS));

    public static final DeferredHolder<Block, Block> WILD_VANILLA = BLOCKS.register("wild_vanilla",
            () -> new cn.mcmod.sakura.block.crops.WildCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)
                    .strength(0.2F), ItemRegistry.VANILLA_SEEDS));

    // ===== MISSING BLOCKS FROM 1.12.2 =====

    // Priority 1 - Machine/Functional Blocks
    public static final DeferredHolder<Block, Block> BARREL_OUT = BLOCKS.register("barrel_out", BarrelOutputBlock::new);
    public static final DeferredHolder<Block, Block> TATARA = BLOCKS.register("tatara", TataraBlock::new);
    public static final DeferredHolder<Block, Block> MAPLE_CAULDRON = BLOCKS.register("maple_cauldron", MapleCauldronBlock::new);
    public static final DeferredHolder<Block, Block> MAPLE_SPILE = BLOCKS.register("maple_spile", MapleSpileBlock::new);

    // Priority 2 - Campfire Blocks
    public static final DeferredHolder<Block, Block> CAMPFIRE_IDLE = BLOCKS.register("campfire_idle",
            () -> new SakuraCampfireBlock(false));
    public static final DeferredHolder<Block, Block> CAMPFIRE_LIT = BLOCKS.register("campfire_lit",
            () -> new SakuraCampfireBlock(true));
    public static final DeferredHolder<Block, Block> CAMPFIRE_POT_IDLE = BLOCKS.register("campfire_pot_idle",
            () -> new CampfirePotBlock(false));
    public static final DeferredHolder<Block, Block> CAMPFIRE_POT_LIT = BLOCKS.register("campfire_pot_lit",
            () -> new CampfirePotBlock(true));

    // Priority 3 - Decorative/Other
    public static final DeferredHolder<Block, Block> SHOJI = BLOCKS.register("shoji", ShojiBlock::new);
    public static final DeferredHolder<Block, Block> SUSHI_PLATE = BLOCKS.register("sushi_plate",
            () -> new TeishokuBlock(FoodInfo.builder().amountAndCalories(6, 0.8f).build()));
    public static final DeferredHolder<Block, Block> TEMPURA_PLATE = BLOCKS.register("tempura_plate",
            () -> new TeishokuBlock(FoodInfo.builder().amountAndCalories(6, 0.8f).build()));
    public static final DeferredHolder<Block, Block> UDON_BLOCK = BLOCKS.register("udon_block", BlockUdon::new);
    public static final DeferredHolder<Block, Block> CHESTNUT_BURR = BLOCKS.register("chestnut_burr",
            () -> {
                BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).randomTicks();
                return new net.minecraft.world.level.block.BushBlock(props) {
                    @SuppressWarnings("unchecked")
                    @Override public com.mojang.serialization.MapCodec codec() {
                        return simpleCodec(p -> new Block(p) {});
                    }
                };
            });
    public static final DeferredHolder<Block, Block> MUSHROOM_FALLEN_LEAVES = BLOCKS.register("mushroom_fallen_leaves",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET)
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> SAKURA_DIAMOND_BLOCK = BLOCKS.register("sakura_diamond_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .strength(5.0F, 10.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> STRAW_WEB = BLOCKS.register("straw_web", StrawWebBlock::new);

    // Priority 4 - Wood Variants: Ume (Plum) tree
    public static final DeferredHolder<Block, RotatedPillarBlock> UME_LOG = BLOCKS.register("ume_log",
            () -> log(MapColor.WOOD, MapColor.PODZOL));
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_UME_LOG = BLOCKS.register("stripped_ume_log",
            () -> log(MapColor.WOOD, MapColor.WOOD));
    public static final DeferredHolder<Block, RotatedPillarBlock> UME_WOOD = BLOCKS.register("ume_wood",
            () -> log(MapColor.PODZOL, MapColor.PODZOL));
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_UME_WOOD = BLOCKS.register("stripped_ume_wood",
            () -> log(MapColor.WOOD, MapColor.WOOD));
    public static final DeferredHolder<Block, Block> UME_LEAVES = BLOCKS.register("umeleaves",
            () -> new SakuraLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.SAKURA_LEAF));
    public static final DeferredHolder<Block, SaplingBlock> UME_SAPLING = BLOCKS.register("ume_sapling",
            () -> sapling(UmeTreeGrower.GROWER));

    // Priority 5 - Tatami Variants (1.12.2 had tan/ns/half combinations)
    public static final DeferredHolder<Block, Block> TATAMI_TAN = BLOCKS.register("tatami_tan",
            () -> new BaseHorizonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, Block> TATAMI_TAN_NS = BLOCKS.register("tatami_tan_ns",
            () -> new BaseHorizonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, Block> TATAMI_NS = BLOCKS.register("tatami_ns",
            () -> new BaseHorizonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_HALF = BLOCKS.register("tatami_half",
            () -> new FacingSlab(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_NS_HALF = BLOCKS.register("tatami_ns_half",
            () -> new FacingSlab(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_TAN_HALF = BLOCKS.register("tatami_tan_half",
            () -> new FacingSlab(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredHolder<Block, FacingSlab> TATAMI_TAN_NS_HALF = BLOCKS.register("tatami_tan_ns_half",
            () -> new FacingSlab(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    // Noodle Dough Blocks (from 1.12.2)
    public static final DeferredHolder<Block, Block> RAMEN_BLOCK = BLOCKS.register("ramen_block", BlockRamen::new);
    public static final DeferredHolder<Block, Block> SOBA_BLOCK = BLOCKS.register("soba_block", BlockSoba::new);
    public static final DeferredHolder<Block, Block> PASTA_BLOCK = BLOCKS.register("pasta_block", BlockPasta::new);
    public static final DeferredHolder<Block, Block> UDON_UNFINISHED = BLOCKS.register("udon_unfinished_block", BlockUdonUnfinished::new);

    // Tatami carpets
    public static final DeferredHolder<Block, Block> TATAMI_CARPET = BLOCKS.register("tatami_carpet",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.SAND).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> TATAMI_CARPET_WAXED = BLOCKS.register("tatami_ns_carpet",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.SAND).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> TATAMI_CARPET_TAN = BLOCKS.register("tatami_tan_carpet",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.SAND).sound(SoundType.GRASS)));
    public static final DeferredHolder<Block, Block> TATAMI_CARPET_TAN_WAXED = BLOCKS.register("tatami_tan_ns_carpet",
            () -> new net.minecraft.world.level.block.CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.SAND).sound(SoundType.GRASS)));

    private static RotatedPillarBlock log(MapColor top, MapColor bark) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .ofFullCopy(Blocks.OAK_LOG).mapColor( state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? top : bark))
                .strength(2.0F).sound(SoundType.WOOD));
    }

    private static SaplingBlock sapling(TreeGrower tree) {
        return new SaplingBlock(tree, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollission().randomTicks()
                .instabreak().sound(SoundType.GRASS));
    }

    private static BlockBehaviour.Properties cropProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noCollission().instabreak().sound(SoundType.CROP);
    }

    private static RotatedPillarBlock simplebambooBlock(MapColor top, MapColor bark) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .ofFullCopy(Blocks.BAMBOO_BLOCK).mapColor(state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? top : bark))

                .strength(2.0F).sound(SoundType.BAMBOO));
    }

    private static Block plank(MapColor material_color) {
        return new Block(
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(material_color).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    }

}
