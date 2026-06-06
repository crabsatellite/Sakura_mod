package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.effect.EffectRegistry;
import cn.mcmod.sakura.entity.EntityRegistry;
import cn.mcmod.sakura.network.SheathKeyPacket;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import cn.mcmod.sakura.villager.SakuraVillagerTrades;
import cn.mcmod.sakura.villager.VillagerRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class ContentRegistryTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void restored_entities_and_effects_are_registered(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper, EntityRegistry.DEER.isBound(),
                "Deer entity should be registered");
        SakuraTestBase.assertTrue(helper, EntityRegistry.SAMURAI_ILLAGER.isBound(),
                "Samurai illager entity should be registered");

        SakuraTestBase.assertNotNull(helper, EffectRegistry.EXP_UP.value(), "EXP_UP effect missing");
        SakuraTestBase.assertNotNull(helper, EffectRegistry.CANNON.value(), "CANNON effect missing");
        SakuraTestBase.assertNotNull(helper, EffectRegistry.FIRE_BLADE.value(), "FIRE_BLADE effect missing");
        SakuraTestBase.assertNotNull(helper, EffectRegistry.GOLDEN_HEART.value(), "GOLDEN_HEART effect missing");
        SakuraTestBase.assertNotNull(helper, EffectRegistry.POISOM.value(), "POISOM effect missing");
        SakuraTestBase.assertNotNull(helper, EffectRegistry.SCORPION.value(), "SCORPION effect missing");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void restored_villagers_and_trades_are_registered(GameTestHelper helper) {
        SakuraTestBase.assertTrue(helper, VillagerRegistry.WA_FARMER_POI.isBound(),
                "WA farmer POI should be registered");
        SakuraTestBase.assertTrue(helper, VillagerRegistry.WA_SILK_POI.isBound(),
                "WA silk POI should be registered");
        SakuraTestBase.assertTrue(helper, VillagerRegistry.WA_FARMER.isBound(),
                "WA farmer profession should be registered");
        SakuraTestBase.assertTrue(helper, VillagerRegistry.WA_SILK.isBound(),
                "WA silk profession should be registered");

        assertProfessionHasFiveTradeLevels(helper, new VillagerTradesEvent(
                freshTradeMap(), VillagerRegistry.WA_FARMER.get()), "WA_FARMER");
        assertProfessionHasFiveTradeLevels(helper, new VillagerTradesEvent(
                freshTradeMap(), VillagerRegistry.WA_SILK.get()), "WA_SILK");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void sheath_network_payload_is_available(GameTestHelper helper) {
        SakuraTestBase.assertNotNull(helper, SheathKeyPacket.TYPE,
                "Sakura sheath key payload type should be initialized");
        SakuraTestBase.assertNotNull(helper, SheathKeyPacket.STREAM_CODEC,
                "Sakura sheath key payload codec should be initialized");
        SakuraTestBase.assertEquals(helper, "sheath_key", SheathKeyPacket.TYPE.id().getPath(),
                "Sakura sheath key payload id should stay stable");
        helper.succeed();
    }

    private static void assertProfessionHasFiveTradeLevels(GameTestHelper helper, VillagerTradesEvent event, String name) {
        SakuraVillagerTrades.registerTrades(event);
        for (int level = 1; level <= 5; level++) {
            List<VillagerTrades.ItemListing> listings = event.getTrades().get(level);
            SakuraTestBase.assertNotNull(helper, listings,
                    name + " level " + level + " trade list should exist");
            SakuraTestBase.assertTrue(helper, !listings.isEmpty(),
                    name + " level " + level + " should have restored Sakura trades");
        }
    }

    private static Int2ObjectMap<List<VillagerTrades.ItemListing>> freshTradeMap() {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = new Int2ObjectOpenHashMap<>();
        for (int level = 1; level <= 5; level++) {
            trades.put(level, new ArrayList<>());
        }
        return trades;
    }
}
