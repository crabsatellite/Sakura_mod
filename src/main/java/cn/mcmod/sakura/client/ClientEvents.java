package cn.mcmod.sakura.client;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.entity.BlockEntityRegistry;
import cn.mcmod.sakura.client.entity.DeerRenderer;
import cn.mcmod.sakura.client.entity.SamuraiIllagerRenderer;
import cn.mcmod.sakura.client.particle.FallenLeafParticle;
import cn.mcmod.sakura.client.particle.ParticleRegistry;
import cn.mcmod.sakura.client.particle.SyrupDropParticle;
import cn.mcmod.sakura.client.render.CampfirePotRenderer;
import cn.mcmod.sakura.client.render.CampfireRenderer;
import cn.mcmod.sakura.client.render.ChoppingBoardRender;
import cn.mcmod.sakura.client.render.MapleCauldronRenderer;
import cn.mcmod.sakura.client.render.ObonRender;
import cn.mcmod.sakura.client.render.ShojiRenderer;
import cn.mcmod.sakura.client.render.StoneMortarRenderer;
import cn.mcmod.sakura.client.render.StrawWebRenderer;
import cn.mcmod.sakura.entity.EntityRegistry;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.fluid.FluidRegistry;
import cn.mcmod.sakura.fluid.FluidTypeRegistry;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SakuraMod.MODID, value = Dist.CLIENT)
public class ClientEvents {

    public static final KeyMapping SHEATH_KEY = new KeyMapping(
            "key.sakura.sheath_in",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.sakura"
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SHEATH_KEY);
    }

    @SuppressWarnings("deprecation")
	@SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SAKURA_SAPLING.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.RICE_CROP_ROOT.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BAMBOO_PLANT.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BAMBOOSHOOT.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.COOKING_POT.get(), RenderType.cutoutMipped());
            
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.NABE_ODEN.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.NABE_SUKIYAKI.get(), RenderType.cutoutMipped());

            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CAMPFIRE_IDLE.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CAMPFIRE_LIT.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CAMPFIRE_POT_IDLE.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CAMPFIRE_POT_LIT.get(), RenderType.cutoutMipped());

            BlockRegistry.BLOCKS.getEntries().forEach(block -> {
                if (block.get() instanceof BushBlock) {
                    ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutoutMipped());
                }
            });
            FluidRegistry.FLUIDS.getEntries().forEach(fluid -> {
                ItemBlockRenderTypes.setRenderLayer(fluid.get(), RenderType.translucent());
            });
            
            BlockEntityRenderers.register(BlockEntityRegistry.STONE_MORTAR.get(), StoneMortarRenderer::new);
            BlockEntityRenderers.register(BlockEntityRegistry.CHOPPING_BOARD.get(), ChoppingBoardRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.OBON.get(), ObonRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.CAMPFIRE_POT.get(), CampfirePotRenderer::new);
            BlockEntityRenderers.register(BlockEntityRegistry.CAMPFIRE.get(), CampfireRenderer::new);
            BlockEntityRenderers.register(BlockEntityRegistry.STRAW_WEB.get(), StrawWebRenderer::new);
            BlockEntityRenderers.register(BlockEntityRegistry.MAPLE_CAULDRON.get(), MapleCauldronRenderer::new);
            BlockEntityRenderers.register(BlockEntityRegistry.SHOJI.get(), ShojiRenderer::new);
        });
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.DEER.get(), DeerRenderer::new);
        event.registerEntityRenderer(EntityRegistry.SAMURAI_ILLAGER.get(), SamuraiIllagerRenderer::new);
    }

    @SubscribeEvent
    public static void onParticleFactoryRegistration(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.SAKURA_LEAF.get(),
                FallenLeafParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.RED_MAPLE_LEAF.get(),
                FallenLeafParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.YELLOW_MAPLE_LEAF.get(),
                FallenLeafParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.GREEN_MAPLE_LEAF.get(),
                FallenLeafParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.ORANGE_MAPLE_LEAF.get(),
                FallenLeafParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SYRUP_DROP.get(),
                SyrupDropParticle.Factory::new);
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        // Register bucket colors - apply fluid tint color to layer0 (fluid mask) only
        ItemColor bucketColor = (stack, tintIndex) -> {
            if (tintIndex == 0) {
                net.minecraft.world.item.BucketItem bucketItem = (net.minecraft.world.item.BucketItem) stack.getItem();
                var fluid = bucketItem.getFluid();
                if (fluid != null) {
                    IClientFluidTypeExtensions fluidExtensions = IClientFluidTypeExtensions.of(fluid);
                    FluidStack fluidStack = new FluidStack(fluid, 1000);
                    return fluidExtensions.getTintColor(fluidStack);
                }
            }
            return 0xFFFFFFFF; // Default white (layer1 = bucket cover, no tint)
        };

        // Only register color handler for tint-dependent buckets (those using bucket_fluid + bucket_cover model)
        // Pre-colored buckets (beer, brandy, etc.) already have correct colors baked into their textures
        event.register(bucketColor,
                BucketItemRegistry.HOT_SPRING_WATER_BUCKET.get(),
                BucketItemRegistry.COCOA_LIQUEUR_BUCKET.get(),
                BucketItemRegistry.GIN_BUCKET.get(),
                BucketItemRegistry.GRAPE_FLUID_BUCKET.get(),
                BucketItemRegistry.GREEN_GRAPE_FLUID_BUCKET.get(),
                BucketItemRegistry.LIQUEUR_BUCKET.get(),
                BucketItemRegistry.MAPLE_SYRUP_BUCKET.get(),
                BucketItemRegistry.TEQUILA_BUCKET.get(),
                BucketItemRegistry.VODKA_BUCKET.get(),
                BucketItemRegistry.YEAST_LIQUID_BUCKET.get()
        );
    }

}
