package cn.mcmod.sakura.villager;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.level.structure.JapaneseHouseElement;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Adds Japanese-style village buildings to vanilla village generation.
 *
 * <h2>Overview</h2>
 * In 1.12.2, Sakura added a custom Japanese-themed village house ({@code WAVillagerHouse}) that
 * generated procedurally via {@code addComponentParts()} -- a 9x9x6 building with stone brick
 * foundation, bamboo plank walls, straw thatch roof, glass pane windows, bamboo fence furniture,
 * and a crafting table.
 *
 * <p>In 1.20.1, village generation uses the Jigsaw/Structure Pool system. This class injects
 * a procedurally-generated {@link JapaneseHouseElement} (a direct port of the 1.12.2 code)
 * into vanilla village pools at server start, so no .nbt template files are needed.</p>
 *
 * <h2>How it works</h2>
 * <ol>
 *   <li>On {@link ServerAboutToStartEvent}, we look up the vanilla village house pools
 *       (e.g., {@code village/plains/houses}, {@code village/taiga/houses})</li>
 *   <li>We create a {@link JapaneseHouseElement} instance (our custom StructurePoolElement
 *       that places blocks procedurally)</li>
 *   <li>We inject it into the pool's {@code templates} and {@code rawTemplates} lists
 *       via reflection (the fields are private in StructureTemplatePool)</li>
 * </ol>
 */
@Mod.EventBusSubscriber(modid = SakuraMod.MODID)
public class SakuraVillageStructures {

    // Village biome types where Japanese buildings can appear
    private static final String[] VILLAGE_TYPES = new String[]{"plains", "taiga"};

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        try {
            Registry<StructureTemplatePool> templatePoolRegistry =
                    event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();

            for (String villageType : VILLAGE_TYPES) {
                ResourceLocation housesPool = new ResourceLocation("village/" + villageType + "/houses");
                addProceduralHouseToPool(templatePoolRegistry, housesPool, 2);
                SakuraMod.getLogger().info("Added Sakura Japanese house to {} village pool", villageType);
            }
        } catch (Exception e) {
            SakuraMod.getLogger().error("Failed to inject Sakura village structures", e);
        }
    }

    public static void addProceduralHouseToPool(Registry<StructureTemplatePool> templatePoolRegistry,
                                                ResourceLocation poolRL,
                                                int weight) {
        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) {
            SakuraMod.getLogger().warn("Village pool {} not found, skipping Japanese house injection", poolRL);
            return;
        }

        JapaneseHouseElement piece = new JapaneseHouseElement(StructureTemplatePool.Projection.RIGID);
        addElementToPool(pool, piece, weight);
    }

    public static void addNbtBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry,
                                            Registry<StructureProcessorList> processorListRegistry,
                                            ResourceLocation poolRL,
                                            String nbtPieceRL,
                                            int weight) {
        Holder<StructureProcessorList> houseProcessor =
                processorListRegistry.getHolderOrThrow(MOSSIFY_PROCESSOR);

        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) {
            SakuraMod.getLogger().warn("Village pool {} not found, skipping structure {}", poolRL, nbtPieceRL);
            return;
        }

        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, houseProcessor)
                .apply(StructureTemplatePool.Projection.RIGID);

        addElementToPool(pool, piece, weight);
    }

    /**
     * Injects a structure pool element into a StructureTemplatePool's internal lists via reflection.
     * The templates list is an ImmutableList, so we replace it with a mutable ObjectArrayList copy.
     */
    @SuppressWarnings("unchecked")
    private static void addElementToPool(StructureTemplatePool pool,
                                         StructurePoolElement piece,
                                         int weight) {
        try {
            // Get the templates field (ObjectArrayList<StructurePoolElement>)
            Field templatesField = ObfuscationReflectionHelper.findField(
                    StructureTemplatePool.class, "f_210560_"); // templates

            List<StructurePoolElement> currentTemplates =
                    (List<StructurePoolElement>) templatesField.get(pool);
            ObjectArrayList<StructurePoolElement> mutableTemplates = new ObjectArrayList<>(currentTemplates);
            for (int i = 0; i < weight; i++) {
                mutableTemplates.add(piece);
            }
            templatesField.set(pool, mutableTemplates);

            // Get the rawTemplates field (List<Pair<StructurePoolElement, Integer>>)
            Field rawTemplatesField = ObfuscationReflectionHelper.findField(
                    StructureTemplatePool.class, "f_210559_"); // rawTemplates

            List<Pair<StructurePoolElement, Integer>> currentRaw =
                    (List<Pair<StructurePoolElement, Integer>>) rawTemplatesField.get(pool);
            List<Pair<StructurePoolElement, Integer>> mutableRaw = new ArrayList<>(currentRaw);
            mutableRaw.add(new Pair<>(piece, weight));
            rawTemplatesField.set(pool, mutableRaw);

        } catch (Exception e) {
            SakuraMod.getLogger().error("Failed to add element {} to pool via reflection", piece, e);
        }
    }

    private static final ResourceKey<StructureProcessorList> MOSSIFY_PROCESSOR =
            ResourceKey.create(Registries.PROCESSOR_LIST,
                    new ResourceLocation("minecraft", "mossify_10_percent"));
}
