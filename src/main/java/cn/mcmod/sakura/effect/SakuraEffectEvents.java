package cn.mcmod.sakura.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import cn.mcmod.sakura.SakuraMod;

@EventBusSubscriber(modid = SakuraMod.MODID)
public class SakuraEffectEvents {

    /**
     * Handles on-hit effects: Fire Blade, Poisom, and Scorpion.
     */
    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        // Fire Blade: set target on fire for 600 seconds (matches 1.12.2 setFire(600))
        if (player.hasEffect(EffectRegistry.FIRE_BLADE)) {
            event.getEntity().igniteForSeconds(600);
        }

        // Poisom: apply Poison II (15 seconds) — 1.12.2 used lvl=1 → amplifier 1
        if (player.hasEffect(EffectRegistry.POISOM)) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1));
        }

        // Scorpion: apply Poison IV (45 seconds) — 1.12.2 used lvl=3 → amplifier 3
        if (player.hasEffect(EffectRegistry.SCORPION)) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.POISON, 900, 3));
        }
    }

    /**
     * Handles Exp Up effect: increases experience drops from killed mobs.
     */
    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent event) {
        Player player = event.getAttackingPlayer();
        if (player != null && player.hasEffect(EffectRegistry.EXP_UP)) {
            int amplifier = player.getEffect(EffectRegistry.EXP_UP).getAmplifier();
            int originalExp = event.getOriginalExperience();
            // 1.12.2 formula: bonus = (originalExp / 2) * amplifier
            // Level 0 = no boost, Level 1 = +50%, Level 2 = +100%, etc.
            int bonus = (originalExp / 2) * amplifier;
            event.setDroppedExperience(originalExp + bonus);
        }
    }

    /**
     * Handles Cannon effect: increases arrow velocity / charge power.
     */
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        if (player.hasEffect(EffectRegistry.CANNON)) {
            int amplifier = player.getEffect(EffectRegistry.CANNON).getAmplifier();
            // 1.12.2 formula: charge += amplifier * 25 (no cap)
            event.setCharge(event.getCharge() + amplifier * 25);
        }
    }
}
