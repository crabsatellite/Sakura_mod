package cn.mcmod.sakura.item;

import java.util.Map;
import java.util.function.Supplier;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.entity.EntityRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod_mmf.mmlib.item.ItemFoodSeeds;
import cn.mcmod_mmf.mmlib.item.info.FoodInfo;
import cn.mcmod_mmf.mmlib.registry.ItemRegistryUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SakuraMod.MODID);

    public static final RegistryObject<Item> RICE_SEEDS = register("rice_seeds", RiceSeedsItem::new);

    public static final RegistryObject<Item> ONION_SEEDS = register("onion_seeds",
            () -> seed(BlockRegistry.ONION_CROP.get()));
    public static final RegistryObject<Item> RADISH_SEEDS = register("radish_seeds",
            () -> seed(BlockRegistry.RADISH_CROP.get()));
    public static final RegistryObject<Item> CABBAGE_SEEDS = register("cabbage_seeds",
            () -> seed(BlockRegistry.CABBAGE_CROP.get()));
    public static final RegistryObject<Item> RAPESEEDS = register("rapeseeds",
            () -> seed(BlockRegistry.RAPESEED_CROP.get()));
    public static final RegistryObject<Item> RED_BEAN = register("red_bean",
            () -> seed(BlockRegistry.REDBEAN_CROP.get()));
    public static final RegistryObject<Item> SOYBEAN = register("soybean",
            () -> seed(BlockRegistry.SOYBEAN_CROP.get()));
    public static final RegistryObject<Item> BUCKWHEAT = register("buckwheat",
            () -> seed(BlockRegistry.BUCKWHEAT_CROP.get()));

    public static final RegistryObject<Item> EGGPLANT_SEEDS = register("eggplant_seeds",
            () -> seed(BlockRegistry.EGGPLANT_CROP.get()));
    public static final RegistryObject<Item> TOMATO_SEEDS = register("tomato_seeds",
            () -> seed(BlockRegistry.TOMATO_CROP.get()));

    public static final RegistryObject<ItemFoodSeeds> TARO = register("taro",
            () -> seed(BlockRegistry.TARO_CROP.get(),
                    FoodInfo.builder().name("taro").amountAndCalories(2, 0.2F).water(0F).nutrients(2F, 2F, 0F, 0F, 0F)
                            .decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()));

    public static final RegistryObject<Item> PEPPER_SEEDS = register("pepper_seeds",
            () -> seed(BlockRegistry.PEPPER_CROP.get()));
    public static final RegistryObject<Item> VANILLA_SEEDS = register("vanilla_seeds",
            () -> seed(BlockRegistry.VANILLA_CROP.get()));
    public static final RegistryObject<Item> GRAPE_SEEDS = register("grape_seeds",
            () -> seed(BlockRegistry.GRAPE_CROP.get()));
    public static final RegistryObject<Item> HOP_SEEDS = register("hop_seeds",
            () -> seed(BlockRegistry.HOPS_CROP.get()));
    public static final RegistryObject<Item> SEAWEED_SEEDS = register("seaweed_seeds",
            () -> seed(BlockRegistry.SEAWEED_CROP.get()));

    public static final Map<SakuraNormalItemSet, RegistryObject<Item>> MATERIALS = ItemRegistryUtil
            .mapOfKeys(SakuraNormalItemSet.class, material -> register(material.getName(), ItemRegistry::normalItem));
    
    // Alcohol bottle items (4 uses each, returns empty bottle)
    public static final RegistryObject<Item> BEER_BOTTLE = register("beer_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> DOBUROKU_BOTTLE = register("doburoku_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> SAKE_BOTTLE = register("sake_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> SHOUCHU_BOTTLE = register("shouchu_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> RED_WINE_BOTTLE = register("red_wine_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> WHITE_WINE_BOTTLE = register("white_wine_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> CHAMPAGNE_BOTTLE = register("champagne_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> RUM_BOTTLE = register("rum_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> VODKA_BOTTLE = register("vodka_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> WHISKEY_BOTTLE = register("whiskey_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> BRANDY_BOTTLE = register("brandy_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> GIN_BOTTLE = register("gin_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> TEQUILA_BOTTLE = register("tequila_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> LIQUEUR_BOTTLE = register("liqueur_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));
    public static final RegistryObject<Item> COCOA_LIQUEUR_BOTTLE = register("cocoa_liqueur_bottle",
            () -> new Item(SakuraMod.defaultItemProperties().durability(4).craftRemainder(MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get())));

    public static final RegistryObject<Item> IRON_FISH_KNIFE = register("knife_fish", ()->new KnifeItem(Tiers.IRON, 1F, -2.0F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_NOODLE_KNIFE = register("knife_noodle", ()->new KnifeItem(Tiers.IRON, 2F, -3.0F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Sakura tier tools
    public static final RegistryObject<Item> SAKURA_AXE = register("sakura_axe", () -> new net.minecraft.world.item.AxeItem(SakuraTiers.SAKURA, 9.0F, -3.3F, SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SAKURA_PICKAXE = register("sakura_pickaxe", () -> new net.minecraft.world.item.PickaxeItem(SakuraTiers.SAKURA, 1, -2.8F, SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SAKURA_HOE = register("sakura_hoe", () -> new net.minecraft.world.item.HoeItem(SakuraTiers.SAKURA, -4, 0.0F, SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SAKURA_SHOVEL = register("sakura_shovel", () -> new net.minecraft.world.item.ShovelItem(SakuraTiers.SAKURA, 1.5F, -3.0F, SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SAKURA_KNIFE_NOODLE = register("sakura_knife_noodle", () -> new KnifeItem(SakuraTiers.SAKURA, 2F, -2.0F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> SAKURA_KNIFE_FISH = register("sakura_knife_fish", () -> new KnifeItem(SakuraTiers.SAKURA, 1F, -2.0F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Hammers
    public static final RegistryObject<Item> STONE_HAMMER = register("stone_hammer", () -> new HammerItem(Tiers.STONE, 1.0F, -2.8F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_HAMMER = register("iron_hammer", () -> new HammerItem(Tiers.IRON, 1.0F, -2.8F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> SAKURA_HAMMER = register("sakura_hammer", () -> new HammerItem(SakuraTiers.SAKURA, 1.0F, -2.8F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Broom
    public static final RegistryObject<Item> BROOM = register("broom", () -> new BroomItem(SakuraTiers.STRAW, 1.0F, -2.4F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Weapons - Katana
    public static final RegistryObject<Item> KATANA = register("katana", () -> new KatanaItem(Tiers.IRON, 3, -2.2F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> TACHI = register("tachi", () -> new KatanaItem(SakuraTiers.TACHI, 3, -2.2F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> SAKURA_KATANA = register("sakura_katana", () -> new KatanaItem(SakuraTiers.SAKURA, 3, -2.2F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Weapons - Kodachi
    public static final RegistryObject<Item> KODACHI = register("kodachi", () -> new KodachiItem(Tiers.IRON, 0, -1.6F, SakuraMod.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> SAKURA_KODACHI = register("sakura_kodachi", () -> new KodachiItem(SakuraTiers.SAKURA, 0, -1.6F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Weapons - Shinai
    public static final RegistryObject<Item> SHINAI = register("shinai", () -> new ShinaiItem(Tiers.WOOD, 2, -2.2F, SakuraMod.defaultItemProperties().stacksTo(1)));

    // Weapons - Sheath system
    public static final RegistryObject<Item> SHEATH = register("sheath", () -> new SheathItem(SakuraMod.defaultItemProperties().stacksTo(1).durability(59)));
    public static final RegistryObject<Item> KATANA_SHEATH = register("katana_sheath", () -> new SheathKatanaItem(SakuraMod.defaultItemProperties().stacksTo(1).durability(250), () -> KATANA.get(), () -> SHEATH.get()));
    public static final RegistryObject<Item> SAKURA_KATANA_SHEATH = register("sakura_katana_sheath", () -> new SheathKatanaItem(SakuraMod.defaultItemProperties().stacksTo(1).durability(1561), () -> SAKURA_KATANA.get(), () -> SHEATH.get()));

    // Armor - Straw Hat (uses soldier model variant, no custom armor model needed for just a hat)
    public static final RegistryObject<Item> STRAW_HAT = register("strawhat", () -> new SamuraiArmorItem(SakuraArmorMaterials.STRAW, ArmorItem.Type.HELMET, SakuraMod.defaultItemProperties(), true));

    // Armor - Samurai set (isSoldier=false → full samurai model with ornaments)
    public static final RegistryObject<Item> SAMURAI_HELMET = register("samurai_helmet", () -> new SamuraiArmorItem(SakuraArmorMaterials.SAMURAI, ArmorItem.Type.HELMET, SakuraMod.defaultItemProperties(), false));
    public static final RegistryObject<Item> SAMURAI_CHEST = register("samurai_chest", () -> new SamuraiArmorItem(SakuraArmorMaterials.SAMURAI, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), false));
    public static final RegistryObject<Item> SAMURAI_PANTS = register("samurai_pants", () -> new SamuraiArmorItem(SakuraArmorMaterials.SAMURAI, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), false));
    public static final RegistryObject<Item> SAMURAI_SHOES = register("samurai_shoes", () -> new SamuraiArmorItem(SakuraArmorMaterials.SAMURAI, ArmorItem.Type.BOOTS, SakuraMod.defaultItemProperties(), false));

    // Armor - Soldier set (isSoldier=true → simplified soldier model)
    public static final RegistryObject<Item> SOLDIER_HELMET = register("soldier_helmet", () -> new SamuraiArmorItem(SakuraArmorMaterials.SOLDIER, ArmorItem.Type.HELMET, SakuraMod.defaultItemProperties(), true));
    public static final RegistryObject<Item> SOLDIER_CHEST = register("soldier_chest", () -> new SamuraiArmorItem(SakuraArmorMaterials.SOLDIER, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), true));
    public static final RegistryObject<Item> SOLDIER_PANTS = register("soldier_pants", () -> new SamuraiArmorItem(SakuraArmorMaterials.SOLDIER, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), true));
    public static final RegistryObject<Item> SOLDIER_SHOES = register("soldier_shoes", () -> new SamuraiArmorItem(SakuraArmorMaterials.SOLDIER, ArmorItem.Type.BOOTS, SakuraMod.defaultItemProperties(), true));

    // Armor - Cosmetic: plain kimono and haori (no pattern)
    public static final RegistryObject<Item> KIMONO = register("kimono", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> HAORI = register("haori", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties()));

    // Kimono pattern variants (restored from 1.12.2 NBT-based patterns to individual items)
    // Each variant uses a different armor texture when worn, matching the original 1.12.2 patterns.
    // Kimono (leggings slot) patterns:
    public static final RegistryObject<Item> KIMONO_BASE = register("kimono_base", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_base"));
    public static final RegistryObject<Item> KIMONO_1 = register("kimono_1", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_1"));
    public static final RegistryObject<Item> KIMONO_2 = register("kimono_2", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_2"));
    public static final RegistryObject<Item> KIMONO_3 = register("kimono_3", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_3"));
    public static final RegistryObject<Item> KIMONO_4 = register("kimono_4", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_4"));
    public static final RegistryObject<Item> KIMONO_5 = register("kimono_5", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_5"));
    public static final RegistryObject<Item> KIMONO_6 = register("kimono_6", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_6"));
    public static final RegistryObject<Item> KIMONO_MIKO = register("kimono_miko", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_miko"));
    public static final RegistryObject<Item> KIMONO_ENE = register("kimono_ene", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "kimono_ene"));

    // Haori (chestplate slot) pattern variants:
    public static final RegistryObject<Item> HAORI_BASE = register("haori_base", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), "haori_base"));
    public static final RegistryObject<Item> HAORI_1 = register("haori_1", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), "haori_1"));
    public static final RegistryObject<Item> HAORI_2 = register("haori_2", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), "haori_2"));
    public static final RegistryObject<Item> HAORI_3 = register("haori_3", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), "haori_3"));
    public static final RegistryObject<Item> HAORI_4 = register("haori_4", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.CHESTPLATE, SakuraMod.defaultItemProperties(), "haori_4"));

    // Yukata (leggings slot) pattern variants - a lighter style kimono:
    public static final RegistryObject<Item> YUKATA_0 = register("yukata_0", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "yukata_0"));
    public static final RegistryObject<Item> YUKATA_1 = register("yukata_1", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "yukata_1"));
    public static final RegistryObject<Item> YUKATA_2 = register("yukata_2", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "yukata_2"));
    public static final RegistryObject<Item> YUKATA_3 = register("yukata_3", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "yukata_3"));
    public static final RegistryObject<Item> YUKATA_4 = register("yukata_4", () -> new KimonoItem(SakuraArmorMaterials.KIMONO, ArmorItem.Type.LEGGINGS, SakuraMod.defaultItemProperties(), "yukata_4"));

    // Drink containers
    public static final RegistryObject<Item> CUP = register("cup", ItemRegistry::normalItem);

    // Sakura Diamond gem
    public static final RegistryObject<Item> SAKURA_DIAMOND = register("sakura_diamond", ItemRegistry::normalItem);

    // Special food items with effects
    public static final RegistryObject<Item> HYDRA_RAMEN = register("hydra_ramen", () -> new Item(
            SakuraMod.defaultItemProperties().food(new FoodProperties.Builder()
                    .nutrition(20).saturationMod(0.7F)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 220, 0), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 80, 0), 1.0F)
                    .build())));

    public static final RegistryObject<Item> BUGGYS_MEAT = register("buggys_meat", () -> new Item(
            SakuraMod.defaultItemProperties().food(new FoodProperties.Builder()
                    .nutrition(20).saturationMod(1.1F)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2000, 0), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 2400, 1), 1.0F)
                    .build())));

    // Imogaranawa - decorative rope item with durability
    public static final RegistryObject<Item> IMOGARANAWA = register("imogaranawa", () -> new Item(
            SakuraMod.defaultItemProperties().stacksTo(1).durability(15)));

    // Spawn eggs
    public static final RegistryObject<Item> DEER_SPAWN_EGG = register("deer_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.DEER, 0xe8a96d, 0xdcdcdc, SakuraMod.defaultItemProperties()));
    public static final RegistryObject<Item> SAMURAI_ILLAGER_SPAWN_EGG = register("samurai_illager_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.SAMURAI_ILLAGER, 9804699, 2580065, SakuraMod.defaultItemProperties()));

    private static Item normalItem() {
        return new Item(SakuraMod.defaultItemProperties());
    }

    private static ItemNameBlockItem seed(Block block) {
        return new ItemNameBlockItem(block, SakuraMod.defaultItemProperties());
    }

    private static ItemFoodSeeds seed(Block block, FoodInfo info) {
        return new ItemFoodSeeds(block, SakuraMod.defaultItemProperties(), info);
    }

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }
}
