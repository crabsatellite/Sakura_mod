package cn.mcmod.sakura.entity;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SakuraMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SakuraMod.MODID);

    // 1.12.2: Deer hitbox was 0.9F x 0.95F, tracking range 90
    public static final RegistryObject<EntityType<DeerEntity>> DEER = ENTITY_TYPES.register("deer",
            () -> EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.95F).clientTrackingRange(90).build("deer"));

    // 1.12.2: Samurai tracking range was 90
    public static final RegistryObject<EntityType<SamuraiIllagerEntity>> SAMURAI_ILLAGER = ENTITY_TYPES.register("samurai_illager",
            () -> EntityType.Builder.of(SamuraiIllagerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F).clientTrackingRange(90).build("samurai_illager"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DEER.get(), DeerEntity.createAttributes().build());
        event.put(SAMURAI_ILLAGER.get(), SamuraiIllagerEntity.createAttributes().build());
    }
}
