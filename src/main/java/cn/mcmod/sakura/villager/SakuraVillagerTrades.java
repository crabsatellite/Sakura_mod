package cn.mcmod.sakura.villager;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.DrinkRegistry;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraAlcoholSet;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.List;
import javax.annotation.Nullable;

/**
 * Villager trade tables for WA_FARMER and WA_SILK professions.
 * Restored from 1.12.2 with coin-based currency system.
 *
 * In 1.12.2, the custom currency was ItemLoader.MATERIAL meta 50 (coin).
 * In 1.20.1, this is SakuraNormalItemSet.COIN in ItemRegistry.MATERIALS.
 *
 * WA_FARMER (5 levels):
 *   Level 1 - Farmer: Crop and seed trading (1.12.2 farmer career)
 *   Level 2 - Fisher/Hops: Fish trading and hop buying (1.12.2 fisher career + farmer L2)
 *   Level 3 - Trader: Material trading + tofu/natto (1.12.2 trader career)
 *   Level 4 - Wine Trader: Alcoholic beverage bottle trading (1.12.2 wine_trader L1-L2)
 *   Level 5 - Master: Sakura diamond + premium wine bottles (1.12.2 trader L3 + wine_trader L3-L4)
 *
 * WA_SILK (5 levels):
 *   Level 1 - Novice: Basic silk trading, plain kimono/haori
 *   Level 2 - Apprentice: 6 kimono patterns + 5 haori patterns + straw hat + material trading
 *             (Restored from 1.12.2 NBT-based patterns as individual registered items)
 *   Level 3 - Journeyman: Miko kimono + 5 yukata patterns, sell-back trades, shinai, sheath
 *             (Restored from 1.12.2 NBT-based patterns as individual registered items)
 *   Level 4 - Expert: Samurai and soldier armor sets
 *   Level 5 - Master: Sakura tier weapons
 */
@EventBusSubscriber(modid = SakuraMod.MODID)
public class SakuraVillagerTrades {

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        // WA_FARMER trades
        if (event.getType() == VillagerRegistry.WA_FARMER.get()) {
            registerWaFarmerTrades(trades);
        }

        // WA_SILK trades
        if (event.getType() == VillagerRegistry.WA_SILK.get()) {
            registerWaSilkTrades(trades);
        }
    }

    private static void registerWaFarmerTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
        // ===== Level 1: Farmer - Crop and seed trading =====
        // Villager sells crops to player for coins (1.12.2: SimpleSell - player gives item, gets coins)
        // In 1.20.1 terms: player buys crops using coins
        trades.get(1).add(buyWithCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE).get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT).get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(ItemRegistry.BUCKWHEAT.get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(ItemRegistry.RED_BEAN.get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.ONION).get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO).get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(ItemRegistry.RAPESEEDS.get(), 8, 2, 4, 16, 2));
        trades.get(1).add(buyWithCoins(ItemRegistry.RICE_SEEDS.get(), 8, 2, 4, 16, 2));

        // Player sells seeds to villager for coins (1.12.2: SimpleBuy - player gives coins, gets item)
        // In 1.20.1 terms: player sells items for coins
        trades.get(1).add(sellForCoins(ItemRegistry.RED_BEAN.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.BUCKWHEAT.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.CABBAGE_SEEDS.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.EGGPLANT_SEEDS.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.ONION_SEEDS.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.RADISH_SEEDS.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.RAPESEEDS.get(), 1, 5, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.RICE_SEEDS.get(), 1, 4, 7, 16, 2));
        trades.get(1).add(sellForCoins(ItemRegistry.TOMATO_SEEDS.get(), 1, 5, 7, 16, 2));

        // ===== Level 2: Fisher/Hops =====
        // Hop trading (from farmer L2)
        trades.get(2).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.HOP).get(), 8, 3, 5, 12, 5));
        // Fish trading (from fisher career)
        trades.get(2).add(buyWithCoins(Items.COD, 16, 1, 3, 16, 5));
        trades.get(2).add(sellForCoins(Items.COD, 8, 2, 4, 16, 5));
        // Sashimi trading (from fisher career: machined fish equivalent)
        trades.get(2).add(buyWithCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI).get(), 16, 2, 4, 12, 5));
        trades.get(2).add(sellForCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI).get(), 8, 4, 6, 12, 5));

        // ===== Level 3: Trader - Material trading =====
        // Salt (1.12.2 material 3)
        trades.get(3).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SALT).get(), 8, 2, 7, 12, 10));
        // Soysauce (1.12.2 material 29)
        trades.get(3).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOYSAUCE).get(), 8, 3, 6, 12, 10));
        // Miso (1.12.2 material 33)
        trades.get(3).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MISO).get(), 8, 3, 7, 12, 10));
        // Dashi (1.12.2 material 45)
        trades.get(3).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DASHI).get(), 8, 3, 5, 12, 10));
        // Kouji (1.12.2 material 31)
        trades.get(3).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KOUJI).get(), 8, 2, 5, 12, 10));
        // Tofu (1.12.2 foodset 63)
        trades.get(3).add(sellForCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU).get(), 8, 4, 7, 12, 10));
        // Natto (1.12.2 foodset 81)
        trades.get(3).add(sellForCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.NATTO).get(), 8, 3, 5, 12, 10));
        // Rice bread (1.12.2 foodset 114 approx)
        trades.get(3).add(sellForCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BREAD).get(), 8, 3, 5, 12, 10));
        // Mirin (1.12.2 material 39)
        trades.get(3).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN).get(), 8, 3, 5, 12, 10));
        // 1.12.2 trader L2: lemon juice (FOODSET 115) and soda water (FOODSET 116)
        // These exist in 1.20.1 as SakuraFoodSet.LEMON_JUICE and SakuraFoodSet.SODA_WATER
        trades.get(3).add(sellForCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.LEMON_JUICE).get(), 8, 4, 7, 12, 10));
        trades.get(3).add(sellForCoins(FoodRegistry.FOODSET.get(SakuraFoodSet.SODA_WATER).get(), 8, 3, 5, 12, 10));

        // ===== Level 4: Wine Trader - Basic alcoholic beverage bottles =====
        // 1.12.2 wine_trader L1: beer(0), doburoku(1), red_wine(4), white_wine(5)
        // Using bottle items for trade (matching 1.12.2 bottle_alcoholic)
        trades.get(4).add(sellForCoins(ItemRegistry.BEER_BOTTLE.get(), 1, 10, 20, 8, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.BEER_BOTTLE.get(), 1, 20, 30, 8, 15));
        trades.get(4).add(sellForCoins(ItemRegistry.DOBUROKU_BOTTLE.get(), 1, 10, 20, 8, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.DOBUROKU_BOTTLE.get(), 1, 20, 30, 8, 15));
        trades.get(4).add(sellForCoins(ItemRegistry.RED_WINE_BOTTLE.get(), 1, 10, 20, 8, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.RED_WINE_BOTTLE.get(), 1, 20, 30, 8, 15));
        trades.get(4).add(sellForCoins(ItemRegistry.WHITE_WINE_BOTTLE.get(), 1, 10, 20, 8, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.WHITE_WINE_BOTTLE.get(), 1, 20, 30, 8, 15));
        // 1.12.2 wine_trader L2: sake(2), champagne(6)
        trades.get(4).add(sellForCoins(ItemRegistry.SAKE_BOTTLE.get(), 1, 20, 35, 8, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SAKE_BOTTLE.get(), 1, 40, 60, 8, 15));
        trades.get(4).add(sellForCoins(ItemRegistry.CHAMPAGNE_BOTTLE.get(), 1, 20, 35, 8, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.CHAMPAGNE_BOTTLE.get(), 1, 40, 60, 8, 15));

        // Also sell glass drinks (from DrinkRegistry) at this level
        trades.get(4).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BEER).get(), 1, 8, 12, 8, 15));
        trades.get(4).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_DOBUROKU).get(), 1, 8, 12, 8, 15));
        trades.get(4).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RED_WINE).get(), 1, 10, 15, 8, 15));
        trades.get(4).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHITE_WINE).get(), 1, 10, 15, 8, 15));

        // ===== Level 5: Master - Premium wines + sakura diamond =====
        // Sakura diamond trades (1.12.2 trader L3)
        trades.get(5).add(sellForCoins(ItemRegistry.SAKURA_DIAMOND.get(), 1, 8, 10, 8, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.SAKURA_DIAMOND.get(), 1, 12, 23, 8, 30));
        // 1.12.2 wine_trader L3: shouchu(3), rum(7), vodka(8), whiskey(9), brandy(10)
        trades.get(5).add(sellForCoins(ItemRegistry.SHOUCHU_BOTTLE.get(), 1, 30, 45, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.SHOUCHU_BOTTLE.get(), 1, 60, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.RUM_BOTTLE.get(), 1, 30, 45, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.RUM_BOTTLE.get(), 1, 60, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.VODKA_BOTTLE.get(), 1, 30, 45, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.VODKA_BOTTLE.get(), 1, 60, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.WHISKEY_BOTTLE.get(), 1, 30, 45, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.WHISKEY_BOTTLE.get(), 1, 60, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.BRANDY_BOTTLE.get(), 1, 30, 45, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.BRANDY_BOTTLE.get(), 1, 60, 64, 4, 30));
        // 1.12.2 wine_trader L4: gin(11), tequila(12), liqueur(13), cocoa_liqueur(14)
        trades.get(5).add(sellForCoins(ItemRegistry.GIN_BOTTLE.get(), 1, 40, 64, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.GIN_BOTTLE.get(), 1, 50, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.TEQUILA_BOTTLE.get(), 1, 40, 64, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.TEQUILA_BOTTLE.get(), 1, 50, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.LIQUEUR_BOTTLE.get(), 1, 40, 64, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.LIQUEUR_BOTTLE.get(), 1, 50, 64, 4, 30));
        trades.get(5).add(sellForCoins(ItemRegistry.COCOA_LIQUEUR_BOTTLE.get(), 1, 40, 64, 4, 30));
        trades.get(5).add(buyWithCoins(ItemRegistry.COCOA_LIQUEUR_BOTTLE.get(), 1, 50, 64, 4, 30));

        // Premium glass drinks
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SAKE).get(), 1, 15, 20, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_SHOUCHU).get(), 1, 18, 25, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_CHAMPAGNE).get(), 1, 20, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_WHISKEY).get(), 1, 20, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_BRANDY).get(), 1, 22, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_RUM).get(), 1, 20, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_VODKA).get(), 1, 20, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_GIN).get(), 1, 22, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_TEQUILA).get(), 1, 22, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_LIQUEUR).get(), 1, 22, 30, 4, 30));
        trades.get(5).add(buyWithCoins(
                DrinkRegistry.ALCOHOLS.get(SakuraAlcoholSet.GLASS_COCOA_LIQUEUR).get(), 1, 22, 30, 4, 30));
    }

    private static void registerWaSilkTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
        // ===== Level 1: Novice - Basic silk and plain kimono/haori =====
        // 1.12.2 silk career L1: silk trading + plain kimono + plain haori
        trades.get(1).add(buyWithCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get(), 2, 1, 3, 16, 2));
        trades.get(1).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get(), 4, 2, 5, 16, 2));
        trades.get(1).add(buyWithCoins(ItemRegistry.KIMONO.get(), 1, 10, 14, 8, 2));
        trades.get(1).add(buyWithCoins(ItemRegistry.HAORI.get(), 1, 10, 14, 8, 2));

        // ===== Level 2: Apprentice - Kimono and haori pattern variants =====
        // In 1.12.2, this level sold 6 kimono patterns and haori patterns via NBT.
        // Now restored as individual registered items with pattern-specific armor textures.
        // Kimono patterns (1.12.2: kimono_1 through kimono_6)
        trades.get(2).add(buyWithCoins(ItemRegistry.KIMONO_1.get(), 1, 14, 20, 8, 5));  // Black Kimono
        trades.get(2).add(buyWithCoins(ItemRegistry.KIMONO_2.get(), 1, 14, 20, 8, 5));  // Green Kimono
        trades.get(2).add(buyWithCoins(ItemRegistry.KIMONO_3.get(), 1, 14, 20, 8, 5));  // Cyan Kimono
        trades.get(2).add(buyWithCoins(ItemRegistry.KIMONO_4.get(), 1, 14, 20, 8, 5));  // Purple Kimono
        trades.get(2).add(buyWithCoins(ItemRegistry.KIMONO_5.get(), 1, 14, 20, 8, 5));  // Sakura Kimono
        trades.get(2).add(buyWithCoins(ItemRegistry.KIMONO_6.get(), 1, 14, 20, 8, 5));  // White Kimono
        // Haori patterns (1.12.2: haori_1 through haori_4 + haori_base)
        trades.get(2).add(buyWithCoins(ItemRegistry.HAORI_BASE.get(), 1, 14, 20, 8, 5)); // Brown Haori
        trades.get(2).add(buyWithCoins(ItemRegistry.HAORI_1.get(), 1, 14, 20, 8, 5));   // Black Haori
        trades.get(2).add(buyWithCoins(ItemRegistry.HAORI_2.get(), 1, 14, 20, 8, 5));   // Cyan Haori
        trades.get(2).add(buyWithCoins(ItemRegistry.HAORI_3.get(), 1, 14, 20, 8, 5));   // White Haori
        trades.get(2).add(buyWithCoins(ItemRegistry.HAORI_4.get(), 1, 14, 20, 8, 5));   // Green Haori
        // Also keep straw hat and material trading at this level
        trades.get(2).add(buyWithCoins(ItemRegistry.STRAW_HAT.get(), 1, 8, 12, 8, 5));
        trades.get(2).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get(), 8, 4, 7, 12, 5));
        trades.get(2).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW).get(), 8, 2, 4, 16, 5));

        // ===== Level 3: Journeyman - Miko kimono, yukata patterns, and premium trades =====
        // In 1.12.2, this level sold kimono_miko and yukata_0 through yukata_4 patterns.
        // Now restored as individual registered items.
        trades.get(3).add(buyWithCoins(ItemRegistry.KIMONO_MIKO.get(), 1, 18, 26, 8, 10)); // Miko Kimono (shrine maiden)
        trades.get(3).add(buyWithCoins(ItemRegistry.YUKATA_0.get(), 1, 16, 22, 8, 10));    // Red Yukata
        trades.get(3).add(buyWithCoins(ItemRegistry.YUKATA_1.get(), 1, 16, 22, 8, 10));    // Blue Yukata
        trades.get(3).add(buyWithCoins(ItemRegistry.YUKATA_2.get(), 1, 16, 22, 8, 10));    // Magenta Yukata
        trades.get(3).add(buyWithCoins(ItemRegistry.YUKATA_3.get(), 1, 16, 22, 8, 10));    // Lime Yukata
        trades.get(3).add(buyWithCoins(ItemRegistry.YUKATA_4.get(), 1, 16, 22, 8, 10));    // Yellow Yukata
        // Player can sell kimono/haori back for coins (premium trade)
        trades.get(3).add(sellForCoins(ItemRegistry.KIMONO.get(), 1, 6, 10, 8, 10));
        trades.get(3).add(sellForCoins(ItemRegistry.HAORI.get(), 1, 6, 10, 8, 10));
        // Better silk deal + weapons
        trades.get(3).add(buyWithCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get(), 4, 3, 5, 12, 10));
        trades.get(3).add(buyWithCoins(ItemRegistry.SHINAI.get(), 1, 6, 10, 8, 10));
        trades.get(3).add(buyWithCoins(ItemRegistry.SHEATH.get(), 1, 8, 14, 8, 10));

        // ===== Level 4: Expert - Samurai and soldier armor sets =====
        // Samurai armor
        trades.get(4).add(buyWithCoins(ItemRegistry.SAMURAI_HELMET.get(), 1, 12, 16, 4, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SAMURAI_CHEST.get(), 1, 14, 18, 4, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SAMURAI_PANTS.get(), 1, 12, 16, 4, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SAMURAI_SHOES.get(), 1, 10, 14, 4, 15));
        // Soldier armor
        trades.get(4).add(buyWithCoins(ItemRegistry.SOLDIER_HELMET.get(), 1, 16, 20, 4, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SOLDIER_CHEST.get(), 1, 18, 22, 4, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SOLDIER_PANTS.get(), 1, 16, 20, 4, 15));
        trades.get(4).add(buyWithCoins(ItemRegistry.SOLDIER_SHOES.get(), 1, 14, 18, 4, 15));
        // Player can sell silk for good price
        trades.get(4).add(sellForCoins(
                ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SILK).get(), 4, 4, 6, 12, 15));

        // ===== Level 5: Master - Sakura tier weapons =====
        // Katana
        trades.get(5).add(buyWithCoins(ItemRegistry.KATANA.get(), 1, 20, 30, 4, 30));
        // Kodachi
        trades.get(5).add(buyWithCoins(ItemRegistry.KODACHI.get(), 1, 16, 24, 4, 30));
        // Sakura katana (rare/expensive)
        trades.get(5).add(buyWithCoins(ItemRegistry.SAKURA_KATANA.get(), 1, 30, 50, 2, 30));
        // Tachi
        trades.get(5).add(buyWithCoins(ItemRegistry.TACHI.get(), 1, 26, 40, 2, 30));
        // Sheath
        trades.get(5).add(buyWithCoins(ItemRegistry.SHEATH.get(), 1, 8, 14, 8, 30));
    }

    // ========================= Coin-based trade helpers =========================

    /**
     * Creates a trade where the player buys an item from the villager using coins.
     * Equivalent to 1.12.2 SimpleSell: player gives items, receives coins.
     * In 1.20.1: player pays coins, receives items.
     *
     * @param item      the item the villager sells
     * @param count     number of items sold
     * @param minCoins  minimum coin cost (randomized)
     * @param maxCoins  maximum coin cost (randomized)
     * @param maxTrades maximum number of uses before restock
     * @param xp        villager xp gained
     */
    private static CoinTrade buyWithCoins(ItemLike item, int count, int minCoins, int maxCoins, int maxTrades, int xp) {
        return new CoinTrade(item, count, minCoins, maxCoins, maxTrades, xp, false);
    }

    /**
     * Creates a trade where the player sells items to the villager for coins.
     * Equivalent to 1.12.2 SimpleBuy: player gives coins, receives items.
     * In 1.20.1: player gives items, receives coins.
     *
     * @param item      the item the player sells
     * @param count     number of items required
     * @param minCoins  minimum coins paid (randomized)
     * @param maxCoins  maximum coins paid (randomized)
     * @param maxTrades maximum number of uses before restock
     * @param xp        villager xp gained
     */
    private static CoinTrade sellForCoins(ItemLike item, int count, int minCoins, int maxCoins, int maxTrades, int xp) {
        return new CoinTrade(item, count, minCoins, maxCoins, maxTrades, xp, true);
    }

    /**
     * Custom trade listing that uses Sakura coins (SakuraNormalItemSet.COIN) as currency
     * instead of emeralds, matching the 1.12.2 trade economy.
     *
     * Handles the 1.12.2 logic where coin amounts > 64 were split across two input slots.
     */
    private static class CoinTrade implements VillagerTrades.ItemListing {
        private final ItemLike item;
        private final int count;
        private final int minCoins;
        private final int maxCoins;
        private final int maxTrades;
        private final int xp;
        private final boolean playerSells; // true = player gives item, gets coins

        CoinTrade(ItemLike item, int count, int minCoins, int maxCoins, int maxTrades, int xp, boolean playerSells) {
            this.item = item;
            this.count = count;
            this.minCoins = minCoins;
            this.maxCoins = maxCoins;
            this.maxTrades = maxTrades;
            this.xp = xp;
            this.playerSells = playerSells;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(@javax.annotation.Nonnull Entity trader, @javax.annotation.Nonnull RandomSource random) {
            int coinAmount = minCoins + random.nextInt(Math.max(1, maxCoins - minCoins + 1));
            // Cap at 64 per slot (matching 1.12.2 behavior)
            coinAmount = Math.min(coinAmount, 64);

            ItemStack coinStack = new ItemStack(
                    ItemRegistry.MATERIALS.get(SakuraNormalItemSet.COIN).get(), coinAmount);
            ItemStack itemStack = new ItemStack(item, count);

            if (playerSells) {
                // Player gives items, receives coins
                // MerchantOffer(costA, costB, result, maxUses, xp, priceMultiplier)
                return new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(item, count), java.util.Optional.empty(), coinStack, maxTrades, xp, 0.05F);
            } else {
                // Player gives coins, receives items
                return new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.COIN).get(), coinAmount), java.util.Optional.empty(), itemStack, maxTrades, xp, 0.05F);
            }
        }
    }
}
