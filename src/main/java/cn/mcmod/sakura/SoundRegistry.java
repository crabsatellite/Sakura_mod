package cn.mcmod.sakura;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
            .create(BuiltInRegistries.SOUND_EVENT, SakuraMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TAIKO = SOUNDS.register("taiko",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "taiko")));
}
