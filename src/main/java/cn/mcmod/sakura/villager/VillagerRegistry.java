package cn.mcmod.sakura.villager;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Japanese Village Structures: See SakuraVillageStructures.java for the pool injection hook.
// To enable: create .nbt structure files in data/sakura/structures/village/japanese/
// and set STRUCTURES_AVAILABLE = true in SakuraVillageStructures.
public class VillagerRegistry {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, SakuraMod.MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, SakuraMod.MODID);

    // POI for wa_farmer: uses stone mortar as workstation
    public static final RegistryObject<PoiType> WA_FARMER_POI = POI_TYPES.register("wa_farmer",
            () -> new PoiType(ImmutableSet.copyOf(
                    BlockRegistry.STONE_MORTAR.get().getStateDefinition().getPossibleStates()), 1, 1));

    // POI for wa_silk: uses chopping board as workstation
    public static final RegistryObject<PoiType> WA_SILK_POI = POI_TYPES.register("wa_silk",
            () -> new PoiType(ImmutableSet.copyOf(
                    BlockRegistry.CHOPPING_BOARD.get().getStateDefinition().getPossibleStates()), 1, 1));

    // Professions
    public static final RegistryObject<VillagerProfession> WA_FARMER = PROFESSIONS.register("wa_farmer",
            () -> new VillagerProfession("sakura:wa_farmer",
                    x -> x.get() == WA_FARMER_POI.get(),
                    x -> x.get() == WA_FARMER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_FARMER));

    public static final RegistryObject<VillagerProfession> WA_SILK = PROFESSIONS.register("wa_silk",
            () -> new VillagerProfession("sakura:wa_silk",
                    x -> x.get() == WA_SILK_POI.get(),
                    x -> x.get() == WA_SILK_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_LEATHERWORKER));
}
