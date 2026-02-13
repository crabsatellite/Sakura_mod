package cn.mcmod.sakura.item.enums;

import cn.mcmod.sakura.effect.EffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Supplier;

public enum SakuraCocktailSet {
    // === Original 1.12.2 cocktails with corrected effects ===
    // Index 0: glass_kir -> night_vision 200, exp_up 200
    GLASS_KIR("glass_kir", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0),
            new MobEffectInstance(EffectRegistry.EXP_UP.get(), 200, 0)
    }),
    // Index 1: glass_royal_kir -> night_vision 400 lv1, exp_up 200
    GLASS_ROYAL_KIR("glass_royal_kir", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 1),
            new MobEffectInstance(EffectRegistry.EXP_UP.get(), 200, 0)
    }),
    // Index 2: glass_margarita -> fire_resistance 200, speed 200
    GLASS_MARGARITA("glass_margarita", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    // Index 3: glass_paradise -> haste 200, instant_health 2, regeneration 100
    GLASS_PARADISE("glass_paradise", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.HEAL, 2, 0),
            new MobEffectInstance(MobEffects.REGENERATION, 100, 0)
    }),
    // Index 4: glass_sidecar -> haste 200, golden_heart 2
    GLASS_SIDECAR("glass_sidecar", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(EffectRegistry.GOLDEN_HEART.get(), 2, 0)
    }),
    // Index 5: glass_french_sevenfive -> cannon 200
    GLASS_FRENCH_SEVENFIVE("glass_french_sevenfive", () -> new MobEffectInstance[]{
            new MobEffectInstance(EffectRegistry.CANNON.get(), 200, 0)
    }),
    // Index 6: glass_john_collins -> haste 200, resistance 200
    GLASS_JOHN_COLLINS("glass_john_collins", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0)
    }),
    // Index 7: glass_daiquiri -> water_breathing 200, resistance 200
    GLASS_DAIQUIRI("glass_daiquiri", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0)
    }),
    // Index 8: glass_between_the_sheets -> haste 200, fire_blade 200
    GLASS_BETWEEN_THE_SHEETS("glass_between_the_sheets", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(EffectRegistry.FIRE_BLADE.get(), 200, 0)
    }),
    // Index 9: glass_black_russian -> strength 200, fire_blade 200
    GLASS_BLACK_RUSSIAN("glass_black_russian", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(EffectRegistry.FIRE_BLADE.get(), 200, 0)
    }),
    // Index 10: glass_godfather -> haste 200, exp_up 200
    GLASS_GODFATHER("glass_godfather", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(EffectRegistry.EXP_UP.get(), 200, 0)
    }),
    // Index 11: glass_godmother -> strength 200, exp_up 200
    GLASS_GODMOTHER("glass_godmother", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(EffectRegistry.EXP_UP.get(), 200, 0)
    }),
    // Index 12: glass_grasshopper -> jump_boost 200, speed 200
    GLASS_GRASSHOPPER("glass_grasshopper", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.JUMP, 200, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    // Index 13: glass_mint_julep -> haste 200, speed 200
    GLASS_MINT_JULEP("glass_mint_julep", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    // Index 14: glass_mojito -> haste 200, speed 200, night_vision 200
    GLASS_MOJITO("glass_mojito", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0)
    }),
    // Index 15: glass_rusty_nail -> haste 200, luck 200
    GLASS_RUSTY_NAIL("glass_rusty_nail", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.LUCK, 200, 0)
    }),
    // Index 16: glass_lemon_margarita -> fire_resistance 400 lv1, speed 400 lv1
    GLASS_LEMON_MARGARITA("glass_lemon_margarita", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 1),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1)
    }),
    // Index 17: glass_russian_spring -> strength 200, resistance 200, regeneration 200
    GLASS_RUSSIAN_SPRING("glass_russian_spring", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0),
            new MobEffectInstance(MobEffects.REGENERATION, 200, 0)
    }),
    // Index 18: glass_alexander -> regeneration 200, hunger 200 lv1
    GLASS_ALEXANDER("glass_alexander", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.REGENERATION, 200, 0),
            new MobEffectInstance(MobEffects.HUNGER, 200, 1)
    }),
    // Index 19: glass_aviation -> levitation 100, speed 200
    GLASS_AVIATION("glass_aviation", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.LEVITATION, 100, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    // Index 20: glass_porto_flip -> haste 200, night_vision 200
    GLASS_PORTO_FLIP("glass_porto_flip", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0)
    }),
    // Index 21: glass_red_eyes -> strength 200, health_boost 200
    GLASS_RED_EYES("glass_red_eyes", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.HEALTH_BOOST, 200, 0)
    }),
    // Index 22: glass_spritzer -> night_vision 400
    GLASS_SPRITZER("glass_spritzer", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0)
    }),
    // Index 23: glass_panache -> saturation 200, levitation 100
    GLASS_PANACHE("glass_panache", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.SATURATION, 200, 0),
            new MobEffectInstance(MobEffects.LEVITATION, 100, 0)
    }),
    // Index 24: glass_bloody_mary -> strength 400 lv1
    GLASS_BLOODY_MARY("glass_bloody_mary", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1)
    }),
    // Index 25: glass_screw_driver -> strength 200, absorption 200, health_boost 200
    GLASS_SCREW_DRIVER("glass_screw_driver", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.ABSORPTION, 200, 0),
            new MobEffectInstance(MobEffects.HEALTH_BOOST, 200, 0)
    }),
    // Index 26: glass_saketini -> instant_health 2 lv1, regeneration 200, health_boost 200
    GLASS_SAKETINI("glass_saketini", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.HEAL, 2, 1),
            new MobEffectInstance(MobEffects.REGENERATION, 200, 0),
            new MobEffectInstance(MobEffects.HEALTH_BOOST, 200, 0)
    }),
    // Index 27: glass_boilermaker -> saturation 200, haste 200
    GLASS_BOILERMAKER("glass_boilermaker", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.SATURATION, 200, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0)
    }),
    // Index 28: glass_beer_margarita -> saturation 200, speed 200
    GLASS_BEER_MARGARITA("glass_beer_margarita", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.SATURATION, 200, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    // Index 29: glass_long_island_iced_tea -> golden_heart 200
    GLASS_LONG_ISLAND_ICED_TEA("glass_long_island_iced_tea", () -> new MobEffectInstance[]{
            new MobEffectInstance(EffectRegistry.GOLDEN_HEART.get(), 200, 0)
    }),
    // Index 30: glass_highball -> haste 400
    GLASS_HIGHBALL("glass_highball", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0)
    }),
    // Index 31: glass_porchcrawler -> strength 200, haste 200, saturation 200
    GLASS_PORCHCRAWLER("glass_porchcrawler", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.SATURATION, 200, 0)
    }),
    // Index 32: glass_stinger -> poisom 200
    GLASS_STINGER("glass_stinger", () -> new MobEffectInstance[]{
            new MobEffectInstance(EffectRegistry.POISOM.get(), 200, 0)
    }),
    // Index 33: glass_negroni -> resistance 200, golden_heart 100
    GLASS_NEGRONI("glass_negroni", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0),
            new MobEffectInstance(EffectRegistry.GOLDEN_HEART.get(), 100, 0)
    }),
    // Index 34: glass_old_fashioned -> speed 200, haste 200, night_vision 200
    GLASS_OLD_FASHIONED("glass_old_fashioned", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0)
    }),
    // Index 35: glass_whiskey_sour -> speed 200, haste 200
    GLASS_WHISKEY_SOUR("glass_whiskey_sour", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0)
    }),
    // Index 36: glass_gimlet -> speed 400, haste 400
    GLASS_GIMLET("glass_gimlet", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0)
    }),
    // Index 37: glass_tequila_sunrise -> instant_health 2, speed 200
    GLASS_TEQUILA_SUNRISE("glass_tequila_sunrise", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.HEAL, 2, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    // Index 38: glass_flying_grasshopper -> jump_boost 200 lv5, speed 200 lv1
    GLASS_FLYING_GRASSHOPPER("glass_flying_grasshopper", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.JUMP, 200, 5),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1)
    }),
    // Index 39: glass_eggnog -> scorpion 200
    GLASS_EGGNOG("glass_eggnog", () -> new MobEffectInstance[]{
            new MobEffectInstance(EffectRegistry.SCORPION.get(), 200, 0)
    }),
    // Index 40: glass_scorpion -> strength 200, saturation 200
    GLASS_SCORPION("glass_scorpion", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.SATURATION, 200, 0)
    }),
    // Index 41: glass_moscow_mule -> strength 200, saturation 200
    GLASS_MOSCOW_MULE("glass_moscow_mule", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.SATURATION, 200, 0)
    }),

    // === Cocktails new to 1.20.1 (not in 1.12.2 original) ===
    GLASS_KIR_ROYALE("glass_kir_royale", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 800, 0),
            new MobEffectInstance(MobEffects.REGENERATION, 400, 0)
    }),
    GLASS_CASSIS_ORANGE("glass_cassis_orange", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0)
    }),
    GLASS_CASSIS_SODA("glass_cassis_soda", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0),
            new MobEffectInstance(MobEffects.JUMP, 400, 0)
    }),
    GLASS_MIMOSA("glass_mimosa", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0),
            new MobEffectInstance(MobEffects.JUMP, 400, 0)
    }),
    GLASS_BELLINI("glass_bellini", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 800, 0),
            new MobEffectInstance(MobEffects.REGENERATION, 200, 0)
    }),
    GLASS_SHANDY_GAFF("glass_shandy_gaff", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.SATURATION, 400, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)
    }),
    GLASS_RED_EYE("glass_red_eye", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0),
            new MobEffectInstance(MobEffects.SATURATION, 200, 0)
    }),
    GLASS_SANGRIA("glass_sangria", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0),
            new MobEffectInstance(MobEffects.HEALTH_BOOST, 400, 0)
    }),
    GLASS_KALIMOTXO("glass_kalimotxo", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0)
    }),
    GLASS_KITTY("glass_kitty", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0),
            new MobEffectInstance(MobEffects.LUCK, 400, 0)
    }),
    GLASS_OPERATOR("glass_operator", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0),
            new MobEffectInstance(MobEffects.ABSORPTION, 400, 0)
    }),
    GLASS_AMERICANO("glass_americano", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.SATURATION, 600, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0)
    }),
    GLASS_GIN_TONIC("glass_gin_tonic", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0)
    }),
    GLASS_GIN_FIZZ("glass_gin_fizz", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0),
            new MobEffectInstance(MobEffects.JUMP, 400, 0)
    }),
    GLASS_MARTINI("glass_martini", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 800, 0),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0)
    }),
    GLASS_SCREWDRIVER("glass_screwdriver", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0)
    }),
    GLASS_SALTY_DOG("glass_salty_dog", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0),
            new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0)
    }),
    GLASS_MATADOR("glass_matador", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0),
            new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0)
    }),
    GLASS_HOT_TODDY("glass_hot_toddy", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0),
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0)
    }),
    GLASS_HOT_BUTTERED_RUM("glass_hot_buttered_rum", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0),
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0)
    }),
    GLASS_GROG("glass_grog", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0)
    }),
    GLASS_CUBA_LIBRE("glass_cuba_libre", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0),
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0)
    }),
    GLASS_PINA_COLADA("glass_pina_colada", () -> new MobEffectInstance[]{
            new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0),
            new MobEffectInstance(MobEffects.ABSORPTION, 400, 0)
    });

    private final String name;
    private final Supplier<MobEffectInstance[]> effectsSupplier;

    SakuraCocktailSet(String name, Supplier<MobEffectInstance[]> effectsSupplier) {
        this.name = name;
        this.effectsSupplier = effectsSupplier;
    }

    public String getName() {
        return name;
    }

    public MobEffectInstance[] getEffects() {
        return effectsSupplier.get();
    }
}
