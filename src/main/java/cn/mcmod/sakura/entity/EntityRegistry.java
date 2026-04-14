package cn.mcmod.sakura.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;

@EventBusSubscriber(modid = SakuraMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, SakuraMod.MODID);

    // 1.12.2: Deer hitbox was 0.9F x 0.95F, tracking range 90
    public static final DeferredHolder<EntityType<?>, EntityType<DeerEntity>> DEER = ENTITY_TYPES.register("deer",
            () -> EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.95F).clientTrackingRange(90).build("deer"));

    // 1.12.2: Samurai tracking range was 90
    public static final DeferredHolder<EntityType<?>, EntityType<SamuraiIllagerEntity>> SAMURAI_ILLAGER = ENTITY_TYPES.register("samurai_illager",
            () -> EntityType.Builder.of(SamuraiIllagerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F).clientTrackingRange(90).build("samurai_illager"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DEER.get(), DeerEntity.createAttributes().build());
        event.put(SAMURAI_ILLAGER.get(), SamuraiIllagerEntity.createAttributes().build());
    }
}
