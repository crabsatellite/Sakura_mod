package cn.mcmod.sakura;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, SakuraMod.MODID);

    public static final RegistryObject<SoundEvent> TAIKO = SOUNDS.register("taiko",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SakuraMod.MODID, "taiko")));
}
