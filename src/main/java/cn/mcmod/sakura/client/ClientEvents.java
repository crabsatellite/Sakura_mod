package cn.mcmod.sakura.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.fluids.FluidStack;
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
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = SakuraMod.MODID, value = Dist.CLIENT)
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
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.KITUNEBI.get(), RenderType.cutoutMipped());

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
        // All sakura buckets share the bucket_fluid + bucket_cover model; tint layer 0 with the
        // fluid's registered color so there is exactly one source of truth (FluidTypeRegistry).
        ItemColor bucketColor = (stack, tintIndex) -> {
            if (tintIndex != 0 || !(stack.getItem() instanceof net.minecraft.world.item.BucketItem bucketItem)) {
                return 0xFFFFFFFF;
            }
            Fluid fluid = bucketItem.content;
            if (fluid == null) return 0xFFFFFFFF;
            return IClientFluidTypeExtensions.of(fluid).getTintColor(new FluidStack(fluid, 1000));
        };
        BucketItemRegistry.ITEMS.getEntries().forEach(entry -> event.register(bucketColor, entry.get()));
    }

}
