package cn.mcmod.sakura.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;

// Japanese Village Structures: See SakuraVillageStructures.java for the pool injection hook.
// To enable: create .nbt structure files in data/sakura/structures/village/japanese/
// and set STRUCTURES_AVAILABLE = true in SakuraVillageStructures.
public class VillagerRegistry {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, SakuraMod.MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, SakuraMod.MODID);

    // POI for wa_farmer: uses stone mortar as workstation
    public static final DeferredHolder<PoiType, PoiType> WA_FARMER_POI = POI_TYPES.register("wa_farmer",
            () -> new PoiType(ImmutableSet.copyOf(
                    BlockRegistry.STONE_MORTAR.get().getStateDefinition().getPossibleStates()), 1, 1));

    // POI for wa_silk: uses chopping board as workstation
    public static final DeferredHolder<PoiType, PoiType> WA_SILK_POI = POI_TYPES.register("wa_silk",
            () -> new PoiType(ImmutableSet.copyOf(
                    BlockRegistry.CHOPPING_BOARD.get().getStateDefinition().getPossibleStates()), 1, 1));

    // Professions
    public static final DeferredHolder<VillagerProfession, VillagerProfession> WA_FARMER = PROFESSIONS.register("wa_farmer",
            () -> new VillagerProfession("sakura:wa_farmer",
                    x -> x.value() == WA_FARMER_POI.get(),
                    x -> x.value() == WA_FARMER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_FARMER));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> WA_SILK = PROFESSIONS.register("wa_silk",
            () -> new VillagerProfession("sakura:wa_silk",
                    x -> x.value() == WA_SILK_POI.get(),
                    x -> x.value() == WA_SILK_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_LEATHERWORKER));
}
