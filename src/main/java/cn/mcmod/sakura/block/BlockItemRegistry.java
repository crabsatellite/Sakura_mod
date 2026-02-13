package cn.mcmod.sakura.block;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.StoneMortarItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SakuraMod.MODID);

    public static final RegistryObject<Item> SAKURA_LEAVES = ITEMS.register("sakuraleaves",
            () -> new BlockItem(BlockRegistry.SAKURA_LEAVES.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> MAPLE_LEAVES_RED = ITEMS.register("mapleleaves_red",
            () -> new BlockItem(BlockRegistry.MAPLE_LEAVES_RED.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_LEAVES_YELLOW = ITEMS.register("mapleleaves_yellow",
            () -> new BlockItem(BlockRegistry.MAPLE_LEAVES_YELLOW.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_LEAVES_ORANGE = ITEMS.register("mapleleaves_orange",
            () -> new BlockItem(BlockRegistry.MAPLE_LEAVES_ORANGE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_LEAVES_GREEN = ITEMS.register("mapleleaves_green",
            () -> new BlockItem(BlockRegistry.MAPLE_LEAVES_GREEN.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> SAKURA_LOG = ITEMS.register("sakura_log",
            () -> new BlockItem(BlockRegistry.SAKURA_LOG.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> STRIPPED_SAKURA_LOG = ITEMS.register("stripped_sakura_log",
            () -> new BlockItem(BlockRegistry.STRIPPED_SAKURA_LOG.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> SAKURA_WOOD = ITEMS.register("sakura_wood",
            () -> new BlockItem(BlockRegistry.SAKURA_WOOD.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> STRIPPED_SAKURA_WOOD = ITEMS.register("stripped_sakura_wood",
            () -> new BlockItem(BlockRegistry.STRIPPED_SAKURA_WOOD.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> MAPLE_LOG = ITEMS.register("maple_log",
            () -> new BlockItem(BlockRegistry.MAPLE_LOG.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_SAP_LOG = ITEMS.register("maple_sap_log",
            () -> new BlockItem(BlockRegistry.MAPLE_SAP_LOG.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> STRIPPED_MAPLE_LOG = ITEMS.register("stripped_maple_log",
            () -> new BlockItem(BlockRegistry.STRIPPED_MAPLE_LOG.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> MAPLE_WOOD = ITEMS.register("maple_wood",
            () -> new BlockItem(BlockRegistry.MAPLE_WOOD.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> STRIPPED_MAPLE_WOOD = ITEMS.register("stripped_maple_wood",
            () -> new BlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> BAMBOO_BLOCK = ITEMS.register("bamboo_block",
            () -> new BlockItem(BlockRegistry.BAMBOO_BLOCK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_BLOCK_SUNBURNT = ITEMS.register("bamboo_block_sunburnt",
            () -> new BlockItem(BlockRegistry.BAMBOO_BLOCK_SUNBURNT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_CHARCOAL_BLOCK = ITEMS.register("bamboo_charcoal_block",
            () -> new BlockItem(BlockRegistry.BAMBOO_CHARCOAL_BLOCK.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> SAKURA_PLANK = ITEMS.register("plank_sakura",
            () -> new BlockItem(BlockRegistry.SAKURA_PLANK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_PLANK = ITEMS.register("plank_maple",
            () -> new BlockItem(BlockRegistry.MAPLE_PLANK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_PLANK = ITEMS.register("plank_bamboo",
            () -> new BlockItem(BlockRegistry.BAMBOO_PLANK.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TATAMI = ITEMS.register("tatami",
            () -> new BlockItem(BlockRegistry.TATAMI.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_SLAB = ITEMS.register("tatami_slab",
            () -> new BlockItem(BlockRegistry.TATAMI_SLAB.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TATAMI_WAXED = ITEMS.register("tatami_waxed",
            () -> new BlockItem(BlockRegistry.TATAMI_WAXED.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_SLAB_WAXED = ITEMS.register("tatami_slab_waxed",
            () -> new BlockItem(BlockRegistry.TATAMI_SLAB_WAXED.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TATAMI_SUNBURNT = ITEMS.register("tatami_sunburnt",
            () -> new BlockItem(BlockRegistry.TATAMI_SUNBURNT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_SLAB_SUNBURNT = ITEMS.register("tatami_slab_sunburnt",
            () -> new BlockItem(BlockRegistry.TATAMI_SLAB_SUNBURNT.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> STRAW_BLOCK = ITEMS.register("straw_block",
            () -> new BlockItem(BlockRegistry.STRAW_BLOCK.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> BAMBOOSHOOT = ITEMS.register("bamboo_shoot",
            () -> new BlockItem(BlockRegistry.BAMBOOSHOOT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_PLANT = ITEMS.register("bamboo_plant",
            () -> new BlockItem(BlockRegistry.BAMBOO_PLANT.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> SAKURA_SAPLING = ITEMS.register("sakura_sapling",
            () -> new BlockItem(BlockRegistry.SAKURA_SAPLING.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> MAPLE_SAPLING_RED = ITEMS.register("maple_sapling_red",
            () -> new BlockItem(BlockRegistry.MAPLE_SAPLING_RED.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_SAPLING_YELLOW = ITEMS.register("maple_sapling_yellow",
            () -> new BlockItem(BlockRegistry.MAPLE_SAPLING_YELLOW.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_SAPLING_ORANGE = ITEMS.register("maple_sapling_orange",
            () -> new BlockItem(BlockRegistry.MAPLE_SAPLING_ORANGE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_SAPLING_GREEN = ITEMS.register("maple_sapling_green",
            () -> new BlockItem(BlockRegistry.MAPLE_SAPLING_GREEN.get(), SakuraMod.defaultItemProperties()));

    public static final RegistryObject<Item> STONE_MORTAR = ITEMS.register("stone_mortar", StoneMortarItem::new);

    public static final RegistryObject<Item> COOKING_POT = ITEMS.register("cooking_pot",
            () -> new BlockItem(BlockRegistry.COOKING_POT.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> FERMENTER = ITEMS.register("fermenter",
            () -> new BlockItem(BlockRegistry.FERMENTER.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> DISTILLER = ITEMS.register("distiller",
            () -> new BlockItem(BlockRegistry.DISTILLER.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> OBON = ITEMS.register("obon",
            () -> new BlockItem(BlockRegistry.OBON.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> CHOPPING_BOARD = ITEMS.register("chopping_board",
            () -> new BlockItem(BlockRegistry.CHOPPING_BOARD.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TEISHOKU_FISH_RAW = ITEMS.register("teishoku_fish_raw",
            () -> new BlockItem(BlockRegistry.TEISHOKU_FISH_RAW.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TEISHOKU_FISH_COOKED = ITEMS.register("teishoku_fish_cooked",
            () -> new BlockItem(BlockRegistry.TEISHOKU_FISH_COOKED.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TEISHOKU_FISH_SALT = ITEMS.register("teishoku_fish_salt",
            () -> new BlockItem(BlockRegistry.TEISHOKU_FISH_SALT.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TEISHOKU_TAMAGOYAKI = ITEMS.register("teishoku_tamagoyaki",
            () -> new BlockItem(BlockRegistry.TEISHOKU_TAMAGOYAKI.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> TEISHOKU_YAKINIKU = ITEMS.register("teishoku_yakiniku",
            () -> new BlockItem(BlockRegistry.TEISHOKU_YAKINIKU.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> NABE_SUKIYAKI = ITEMS.register("nabe_sukiyaki",
            () -> new BlockItem(BlockRegistry.NABE_SUKIYAKI.get(), SakuraMod.defaultItemProperties()));
    
    public static final RegistryObject<Item> NABE_ODEN = ITEMS.register("nabe_oden",
            () -> new BlockItem(BlockRegistry.NABE_ODEN.get(), SakuraMod.defaultItemProperties()));

    // Stairs
    public static final RegistryObject<Item> SAKURA_STAIRS = ITEMS.register("stairs_plank_sakura",
            () -> new BlockItem(BlockRegistry.SAKURA_STAIRS.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_STAIRS = ITEMS.register("stairs_plank_maple",
            () -> new BlockItem(BlockRegistry.MAPLE_STAIRS.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_PLANK_STAIRS = ITEMS.register("stairs_plank_bamboo",
            () -> new BlockItem(BlockRegistry.BAMBOO_PLANK_STAIRS.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_STAIRS = ITEMS.register("bamboo_stair",
            () -> new BlockItem(BlockRegistry.BAMBOO_STAIRS.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_STAIRS_SUNBURNT = ITEMS.register("bamboo_stair_sunburnt",
            () -> new BlockItem(BlockRegistry.BAMBOO_STAIRS_SUNBURNT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> STRAW_STAIRS = ITEMS.register("straw_stair",
            () -> new BlockItem(BlockRegistry.STRAW_STAIRS.get(), SakuraMod.defaultItemProperties()));

    // Slabs
    public static final RegistryObject<Item> SAKURA_SLAB = ITEMS.register("slab_plank_sakura",
            () -> new BlockItem(BlockRegistry.SAKURA_SLAB.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_SLAB = ITEMS.register("slab_plank_maple",
            () -> new BlockItem(BlockRegistry.MAPLE_SLAB.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_PLANK_SLAB = ITEMS.register("slab_plank_bamboo",
            () -> new BlockItem(BlockRegistry.BAMBOO_PLANK_SLAB.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_SLAB = ITEMS.register("bamboo_slab",
            () -> new BlockItem(BlockRegistry.BAMBOO_SLAB.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_SLAB_SUNBURNT = ITEMS.register("bamboo_slab_sunburnt",
            () -> new BlockItem(BlockRegistry.BAMBOO_SLAB_SUNBURNT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> STRAW_SLAB = ITEMS.register("slab_straw_block",
            () -> new BlockItem(BlockRegistry.STRAW_SLAB.get(), SakuraMod.defaultItemProperties()));

    // Fences
    public static final RegistryObject<Item> BAMBOO_FENCE = ITEMS.register("bamboo_fence",
            () -> new BlockItem(BlockRegistry.BAMBOO_FENCE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_FENCE_SUNBURNT = ITEMS.register("bamboo_fence_sunburnt",
            () -> new BlockItem(BlockRegistry.BAMBOO_FENCE_SUNBURNT.get(), SakuraMod.defaultItemProperties()));

    // Door
    public static final RegistryObject<Item> BAMBOO_DOOR = ITEMS.register("bamboo_door",
            () -> new BlockItem(BlockRegistry.BAMBOO_DOOR.get(), SakuraMod.defaultItemProperties()));

    // Fallen Leaves
    public static final RegistryObject<Item> FALLEN_LEAVES_RED = ITEMS.register("fallen_leaves_red",
            () -> new BlockItem(BlockRegistry.FALLEN_LEAVES_RED.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> FALLEN_LEAVES_YELLOW = ITEMS.register("fallen_leaves_yellow",
            () -> new BlockItem(BlockRegistry.FALLEN_LEAVES_YELLOW.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> FALLEN_LEAVES_ORANGE = ITEMS.register("fallen_leaves_orange",
            () -> new BlockItem(BlockRegistry.FALLEN_LEAVES_ORANGE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> FALLEN_LEAVES_GREEN = ITEMS.register("fallen_leaves_green",
            () -> new BlockItem(BlockRegistry.FALLEN_LEAVES_GREEN.get(), SakuraMod.defaultItemProperties()));

    // Missing Teishoku blocks
    public static final RegistryObject<Item> TEISHOKU_TEMPURA = ITEMS.register("teishoku_tempura",
            () -> new BlockItem(BlockRegistry.TEISHOKU_TEMPURA.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TEISHOKU_FRIED = ITEMS.register("teishoku_fried",
            () -> new BlockItem(BlockRegistry.TEISHOKU_FRIED.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TEISHOKU_KATSU = ITEMS.register("teishoku_katsu",
            () -> new BlockItem(BlockRegistry.TEISHOKU_KATSU.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TEISHOKU_BURGER = ITEMS.register("teishoku_burger",
            () -> new BlockItem(BlockRegistry.TEISHOKU_BURGER.get(), SakuraMod.defaultItemProperties()));

    // Lanterns
    public static final RegistryObject<Item> STONE_LANTERN = ITEMS.register("stone_lantern",
            () -> new BlockItem(BlockRegistry.STONE_LANTERN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> COBBLESTONE_LANTERN = ITEMS.register("cobblestone_lantern",
            () -> new BlockItem(BlockRegistry.COBBLESTONE_LANTERN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MOSSY_STONE_LANTERN = ITEMS.register("mossy_stone_lantern",
            () -> new BlockItem(BlockRegistry.MOSSY_STONE_LANTERN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> RED_LANTERN = ITEMS.register("red_lantern",
            () -> new BlockItem(BlockRegistry.RED_LANTERN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> WHITE_LANTERN = ITEMS.register("white_lantern",
            () -> new BlockItem(BlockRegistry.WHITE_LANTERN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> BAMBOO_LANTERN = ITEMS.register("bamboo_lantern",
            () -> new BlockItem(BlockRegistry.BAMBOO_LANTERN.get(), SakuraMod.defaultItemProperties()));

    // Kawara (roof tiles)
    public static final RegistryObject<Item> KAWARA_BLOCK = ITEMS.register("kawara_block",
            () -> new BlockItem(BlockRegistry.KAWARA_BLOCK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> KAWARA = ITEMS.register("kawara",
            () -> new BlockItem(BlockRegistry.KAWARA.get(), SakuraMod.defaultItemProperties()));

    // Decorative furniture
    public static final RegistryObject<Item> WINDBELL = ITEMS.register("windbell",
            () -> new BlockItem(BlockRegistry.WINDBELL.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> ANDON = ITEMS.register("andon",
            () -> new BlockItem(BlockRegistry.ANDON.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> KITUNEBI = ITEMS.register("kitunebi",
            () -> new BlockItem(BlockRegistry.KITUNEBI.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> ZABUTON = ITEMS.register("zabuton",
            () -> new BlockItem(BlockRegistry.ZABUTON.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> FUTON = ITEMS.register("futon",
            () -> new BlockItem(BlockRegistry.FUTON.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TAIKO = ITEMS.register("taiko",
            () -> new BlockItem(BlockRegistry.TAIKO.get(), SakuraMod.defaultItemProperties()));

    // Noren (curtains)
    public static final RegistryObject<Item> NOREN_WHITE = ITEMS.register("noren_white",
            () -> new BlockItem(BlockRegistry.NOREN_WHITE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> NOREN_BLUE = ITEMS.register("noren_blue",
            () -> new BlockItem(BlockRegistry.NOREN_BLUE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> NOREN_PINK = ITEMS.register("noren_pink",
            () -> new BlockItem(BlockRegistry.NOREN_PINK.get(), SakuraMod.defaultItemProperties()));

    // Tatami carpets
    public static final RegistryObject<Item> TATAMI_CARPET = ITEMS.register("tatami_carpet",
            () -> new BlockItem(BlockRegistry.TATAMI_CARPET.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_CARPET_WAXED = ITEMS.register("tatami_ns_carpet",
            () -> new BlockItem(BlockRegistry.TATAMI_CARPET_WAXED.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_CARPET_TAN = ITEMS.register("tatami_tan_carpet",
            () -> new BlockItem(BlockRegistry.TATAMI_CARPET_TAN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_CARPET_TAN_WAXED = ITEMS.register("tatami_tan_ns_carpet",
            () -> new BlockItem(BlockRegistry.TATAMI_CARPET_TAN_WAXED.get(), SakuraMod.defaultItemProperties()));

    // World generation blocks
    // HOT_SPRING_WATER BlockItem removed - hot spring water is a fluid, use the bucket (BucketItemRegistry.HOT_SPRING_WATER_BUCKET)
    public static final RegistryObject<Item> SAKURA_DIAMOND_ORE = ITEMS.register("sakura_diamond_ore",
            () -> new BlockItem(BlockRegistry.SAKURA_DIAMOND_ORE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> IRON_SAND = ITEMS.register("iron_sand",
            () -> new BlockItem(BlockRegistry.IRON_SAND.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> WILD_PEPPER = ITEMS.register("wild_pepper",
            () -> new BlockItem(BlockRegistry.WILD_PEPPER.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> WILD_VANILLA = ITEMS.register("wild_vanilla",
            () -> new BlockItem(BlockRegistry.WILD_VANILLA.get(), SakuraMod.defaultItemProperties()));

    // Crop support blocks
    public static final RegistryObject<Item> PEPPER_SPLINT = ITEMS.register("pepper_splint",
            () -> new BlockItem(BlockRegistry.PEPPER_SPLINT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> VANILLA_SPLINT = ITEMS.register("vanilla_splint",
            () -> new BlockItem(BlockRegistry.VANILLA_SPLINT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> GRAPE_SPLINT_STAND = ITEMS.register("grape_splint_stand",
            () -> new BlockItem(BlockRegistry.GRAPE_SPLINT_STAND.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> GRAPE_SPLINT = ITEMS.register("grape_splint",
            () -> new BlockItem(BlockRegistry.GRAPE_SPLINT.get(), SakuraMod.defaultItemProperties()));

    // ===== BLOCK ITEMS FOR MISSING 1.12.2 BLOCKS =====

    // Priority 1 - Machine/Functional Blocks
    public static final RegistryObject<Item> BARREL_OUT = ITEMS.register("barrel_out",
            () -> new BlockItem(BlockRegistry.BARREL_OUT.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATARA = ITEMS.register("tatara",
            () -> new BlockItem(BlockRegistry.TATARA.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_CAULDRON = ITEMS.register("maple_cauldron",
            () -> new BlockItem(BlockRegistry.MAPLE_CAULDRON.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MAPLE_SPILE = ITEMS.register("maple_spile",
            () -> new BlockItem(BlockRegistry.MAPLE_SPILE.get(), SakuraMod.defaultItemProperties()));

    // Priority 2 - Campfire Blocks (only idle variant gets an item, like 1.12.2)
    public static final RegistryObject<Item> CAMPFIRE_IDLE = ITEMS.register("campfire_idle",
            () -> new BlockItem(BlockRegistry.CAMPFIRE_IDLE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> CAMPFIRE_POT_IDLE = ITEMS.register("campfire_pot_idle",
            () -> new BlockItem(BlockRegistry.CAMPFIRE_POT_IDLE.get(), SakuraMod.defaultItemProperties()));

    // Priority 3 - Decorative/Other
    public static final RegistryObject<Item> SHOJI = ITEMS.register("shoji",
            () -> new BlockItem(BlockRegistry.SHOJI.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SUSHI_PLATE = ITEMS.register("sushi_plate",
            () -> new BlockItem(BlockRegistry.SUSHI_PLATE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TEMPURA_PLATE = ITEMS.register("tempura_plate",
            () -> new BlockItem(BlockRegistry.TEMPURA_PLATE.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> MUSHROOM_FALLEN_LEAVES = ITEMS.register("mushroom_fallen_leaves",
            () -> new BlockItem(BlockRegistry.MUSHROOM_FALLEN_LEAVES.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SAKURA_DIAMOND_BLOCK = ITEMS.register("sakura_diamond_block",
            () -> new BlockItem(BlockRegistry.SAKURA_DIAMOND_BLOCK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> STRAW_WEB = ITEMS.register("straw_web",
            () -> new BlockItem(BlockRegistry.STRAW_WEB.get(), SakuraMod.defaultItemProperties()));

    // Priority 4 - Wood Variants: Ume (Plum) tree
    public static final RegistryObject<Item> UME_LOG = ITEMS.register("ume_log",
            () -> new BlockItem(BlockRegistry.UME_LOG.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> UME_LEAVES = ITEMS.register("umeleaves",
            () -> new BlockItem(BlockRegistry.UME_LEAVES.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> UME_SAPLING = ITEMS.register("ume_sapling",
            () -> new BlockItem(BlockRegistry.UME_SAPLING.get(), SakuraMod.defaultItemProperties()));

    // Priority 5 - Tatami Variants
    public static final RegistryObject<Item> TATAMI_TAN = ITEMS.register("tatami_tan",
            () -> new BlockItem(BlockRegistry.TATAMI_TAN.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_TAN_NS = ITEMS.register("tatami_tan_ns",
            () -> new BlockItem(BlockRegistry.TATAMI_TAN_NS.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_NS = ITEMS.register("tatami_ns",
            () -> new BlockItem(BlockRegistry.TATAMI_NS.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_HALF = ITEMS.register("tatami_half",
            () -> new BlockItem(BlockRegistry.TATAMI_HALF.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_NS_HALF = ITEMS.register("tatami_ns_half",
            () -> new BlockItem(BlockRegistry.TATAMI_NS_HALF.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_TAN_HALF = ITEMS.register("tatami_tan_half",
            () -> new BlockItem(BlockRegistry.TATAMI_TAN_HALF.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> TATAMI_TAN_NS_HALF = ITEMS.register("tatami_tan_ns_half",
            () -> new BlockItem(BlockRegistry.TATAMI_TAN_NS_HALF.get(), SakuraMod.defaultItemProperties()));

    // Noodle Dough Blocks
    public static final RegistryObject<Item> RAMEN_BLOCK = ITEMS.register("ramen_block",
            () -> new BlockItem(BlockRegistry.RAMEN_BLOCK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SOBA_BLOCK = ITEMS.register("soba_block",
            () -> new BlockItem(BlockRegistry.SOBA_BLOCK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> PASTA_BLOCK = ITEMS.register("pasta_block",
            () -> new BlockItem(BlockRegistry.PASTA_BLOCK.get(), SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> UDON_UNFINISHED = ITEMS.register("udon_unfinished_block",
            () -> new BlockItem(BlockRegistry.UDON_UNFINISHED.get(), SakuraMod.defaultItemProperties()));
}
