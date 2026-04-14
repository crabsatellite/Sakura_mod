package cn.mcmod.sakura;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.client.particle.ParticleRegistry;
import cn.mcmod.sakura.container.ContainerRegistry;
import cn.mcmod.sakura.effect.EffectRegistry;
import cn.mcmod.sakura.entity.EntityRegistry;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.fluid.FluidBlockRegistry;
import cn.mcmod.sakura.fluid.FluidRegistry;
import cn.mcmod.sakura.fluid.FluidTypeRegistry;
import cn.mcmod.sakura.item.ComposterRegistry;
import cn.mcmod.sakura.item.CreativeModeTabRegistry;
import cn.mcmod.sakura.item.DrinkRegistry;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.SakuraArmorMaterials;
import cn.mcmod.sakura.level.biome.SakuraBiomeEvents;
import cn.mcmod.sakura.level.feature.SakuraFeatureRegistry;
import cn.mcmod.sakura.level.structure.SakuraStructureRegistry;
import cn.mcmod.sakura.loot_modifier.LootModifiterRegistry;
import cn.mcmod.sakura.network.SakuraNetwork;
import cn.mcmod.sakura.recipes.RecipeTypeRegistry;
import cn.mcmod.sakura.villager.VillagerRegistry;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(SakuraMod.MODID)
public class SakuraMod {
    public static final String MODID = "sakura";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties();
    }

    public SakuraMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::setup);

        SakuraArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockItemRegistry.ITEMS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        FoodRegistry.ITEMS.register(modEventBus);
        DrinkRegistry.ITEMS.register(modEventBus);
        FluidRegistry.FLUIDS.register(modEventBus);
        FluidBlockRegistry.BLOCKS.register(modEventBus);
        FluidTypeRegistry.FLUID_TYPES.register(modEventBus);
        BucketItemRegistry.ITEMS.register(modEventBus);
        SoundRegistry.SOUNDS.register(modEventBus);
        ParticleRegistry.PARTICLE_TYPES.register(modEventBus);
        ContainerRegistry.CONTAINER_TYPES.register(modEventBus);
        LootModifiterRegistry.GLM.register(modEventBus);
        RecipeTypeRegistry.RECIPE_TYPES.register(modEventBus);
        RecipeTypeRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        CreativeModeTabRegistry.TABS.register(modEventBus);
        EntityRegistry.ENTITY_TYPES.register(modEventBus);
        EffectRegistry.MOB_EFFECTS.register(modEventBus);
        VillagerRegistry.POI_TYPES.register(modEventBus);
        VillagerRegistry.PROFESSIONS.register(modEventBus);
        SakuraFeatureRegistry.FEATURES.register(modEventBus);
        SakuraFeatureRegistry.TREE_DECORATOR_TYPES.register(modEventBus);
        SakuraStructureRegistry.STRUCTURE_POOL_ELEMENT_TYPES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, SakuraConfig.COMMON_CONFIG);

        // Register Sakura biome injection event handler
        NeoForge.EVENT_BUS.register(SakuraBiomeEvents.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterRegistry.registerCompost();
            // SakuraNetwork.register() is now handled via @SubscribeEvent on RegisterPayloadHandlersEvent
        });
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
