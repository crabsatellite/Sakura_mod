package cn.mcmod.sakura.item.enums;

import cn.mcmod_mmf.mmlib.item.info.FoodInfo;

public enum SakuraFoodSet {
    SEAWEED_RAW(FoodInfo.builder().name("seaweed_raw").amountAndCalories(1, 0.1F).water(5F).nutrients(0F, 0F, 1F, 0F, 0F)
            .compostChance(0.3F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    SHRIMP(FoodInfo.builder().name("shrimp").amountAndCalories(2, 0.6F).water(0.5F).nutrients(0F, 0F, 0F, 2F, 0F)
            .compostChance(0.2F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    TOMATO(FoodInfo.builder().name("tomato").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.3F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    RADISH(FoodInfo.builder().name("radish").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.3F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    EGGPLANT(FoodInfo.builder().name("eggplant").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.3F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    
    PICKLED_RADISH(FoodInfo.builder().name("pickled_radish").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.5F).decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),
    PICKLED_EGGPLANT(FoodInfo.builder().name("pickled_eggplant").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.5F).decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),
    
    CABBAGE(FoodInfo.builder().name("cabbage").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.3F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    ONION(FoodInfo.builder().name("onion").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 0F, 2F, 0F, 0F)
            .compostChance(0.3F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    SLICED_CABBAGE(FoodInfo.builder().name("sliced_cabbage").amountAndCalories(2, 0.2F).water(5F).compostChance(0.3F)
            .nutrients(0F, 0F, 2F, 0F, 0F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    MACHINED_FISH(
            FoodInfo.builder().name("machined_fish").amountAndCalories(1, 0.2F).water(1F).nutrients(0F, 0F, 0F, 2F, 2F)
                    .compostChance(0.25F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    MINCED_MEAT(FoodInfo.builder().name("minced_meat").amountAndCalories(2, 0.2F).water(1F).compostChance(0.25F)
            .nutrients(0F, 0F, 0F, 3F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(200F).build()),
    SURIMI(FoodInfo.builder().name("surimi").amountAndCalories(2, 0.2F).water(1F).compostChance(0.25F)
            .nutrients(0F, 0F, 0F, 3F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(200F).build()),

    FISHCAKE(FoodInfo.builder().name("fishcake").amountAndCalories(4, 0.6F).water(1F).nutrients(1F, 0F, 1F, 2F, 0F)
            .compostChance(0.5F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    KAMABOKO(FoodInfo.builder().name("kamaboko").amountAndCalories(4, 0.6F).water(1F).nutrients(1F, 0F, 1F, 2F, 0F)
            .compostChance(0.5F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    CHIKUWA_RAW(
            FoodInfo.builder().name("chikuwa_raw").amountAndCalories(4, 0.6F).water(1F).nutrients(1F, 0F, 1F, 2F, 0F)
                    .compostChance(0.5F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    CHIKUWA(FoodInfo.builder().name("chikuwa").amountAndCalories(4, 0.6F).water(1F).nutrients(1F, 0F, 1F, 2F, 0F)
            .compostChance(0.5F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    SATSUMAAGE(FoodInfo.builder().name("satsumaage").amountAndCalories(4, 0.6F).water(1F).nutrients(1F, 0F, 1F, 2F, 0F)
            .compostChance(0.5F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),

    TOMATO_SAUCE(FoodInfo.builder().name("tomato_sauce").amountAndCalories(2, 0.2F).water(5F).compostChance(0.25F)
            .nutrients(0F, 0F, 2F, 0F, 0F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    EGGPLANT_BAKED(FoodInfo.builder().name("eggplant_baked").amountAndCalories(4, 0.5F).water(0F).compostChance(0.5F)
            .nutrients(0F, 0F, 3F, 0F, 0F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    TARO_BAKED(FoodInfo.builder().name("taro_baked").amountAndCalories(5, 0.6F).water(0F).nutrients(2F, 2F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    CHEESE(FoodInfo.builder().name("cheese").amountAndCalories(2, 0.2F).water(1F).nutrients(0F, 0F, 0F, 0F, 2F)
            .compostChance(0.5F).decayModifier(2F).heatCapacity(0F).cookingTemp(-1F).build()),
    TAMAGOYAKI(
            FoodInfo.builder().name("tamagoyaki").amountAndCalories(6, 0.6F).water(0.5F).nutrients(2F, 0F, 0F, 0F, 3F)
                    .compostChance(0.75F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),

    TOFU(FoodInfo.builder().name("tofu").amountAndCalories(2, 0.4F).water(0.5F).nutrients(0F, 0F, 2F, 0F, 0.5F)
            .compostChance(0.5F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    TOFU_FRIED(FoodInfo.builder().name("tofu_fried").amountAndCalories(4, 0.5F).water(0.5F).compostChance(0.5F)
            .nutrients(0.5F, 0F, 3F, 0F, 0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    NATTO(FoodInfo.builder().name("natto").amountAndCalories(2, 0.5F).water(0.5F).nutrients(1F, 0F, 2F, 0F, 0F)
            .compostChance(0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),

    MASHED_POTATO(FoodInfo.builder().name("mashed_potato").amountAndCalories(5, 0.6F).water(0.5F).compostChance(0.5F)
            .nutrients(2F, 0F, 2F, 0F, 0.5F).decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    FRIES(FoodInfo.builder().name("fries").amountAndCalories(5, 0.6F).water(1F).nutrients(2F, 0F, 2F, 0F, 0F)
            .decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),

    BUN(FoodInfo.builder().name("bun").amountAndCalories(5, 0.6F).water(0F).nutrients(2F, 0F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(0.8F).heatCapacity(1F).cookingTemp(480F).build()),
    BUCKWHEAT_BREAD(FoodInfo.builder().name("buckwheat_bread").amountAndCalories(5, 0.6F).water(0F).compostChance(0.5F)
            .nutrients(2F, 0F, 0F, 0F, 0F).decayModifier(0.5F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_BREAD(FoodInfo.builder().name("rice_bread").amountAndCalories(5, 0.6F).water(0F).nutrients(2F, 0F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    REDBEAN_PASTE(FoodInfo.builder().name("red_bean_paste").amountAndCalories(4, 0.25F).water(4F).compostChance(0.5F)
            .nutrients(0.25F, 0F, 1F, 0F, 0F).decayModifier(4F).heatCapacity(0F).cookingTemp(-1F).build()),
    BREADCRUMBS(FoodInfo.builder().name("breadcrumbs").amountAndCalories(1, 0.1F).water(0F).compostChance(0.5F)
            .nutrients(0.25F, 0F, 0F, 0F, 0F).decayModifier(4F).heatCapacity(0F).cookingTemp(-1F).build()),
    
    FRIED_CHICKEN(FoodInfo.builder().name("fried_chicken").amountAndCalories(6, 0.6F).water(2F).nutrients(1F, 0F, 0F, 4F, 0F)
            .decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    CROQUETTE(FoodInfo.builder().name("croquette").amountAndCalories(6, 0.6F).water(2F).nutrients(2F, 0F, 2.5F, 2F, 0F)
            .decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    KATSU(FoodInfo.builder().name("katsu").amountAndCalories(9, 0.6F).water(4F).nutrients(0.25F, 0F, 0F, 3F, 0F)
            .decayModifier(1.25F).heatCapacity(0F).cookingTemp(-1F).build()),
    
    TEMPURA(FoodInfo.builder().name("tempura").amountAndCalories(5, 0.6F).water(0F).nutrients(1F, 0F, 0F, 2F, 0F)
            .compostChance(0.5F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    BROWN_RICE_COOKED(
            FoodInfo.builder().name("brown_rice_cooked").amountAndCalories(4, 0.5F).water(0.5F).compostChance(0.5F)
                    .nutrients(1.5F, 0F, 0F, 0F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_COOKED(FoodInfo.builder().name("rice_cooked").amountAndCalories(4, 0.5F).water(0.5F).compostChance(0.5F)
            .nutrients(1.5F, 0F, 0F, 0F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_REDBEAN(FoodInfo.builder().name("rice_redbean").amountAndCalories(6, 0.6F).water(0.5F).compostChance(0.85F)
            .nutrients(4F, 0F, 2F, 0F, 0F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_BAMBOO(FoodInfo.builder().name("rice_bamboo").amountAndCalories(5, 0.6F).water(0.5F).compostChance(0.85F)
            .nutrients(1.5F, 1F, 0F, 0F, 0F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_BEEF(FoodInfo.builder().name("rice_beef").amountAndCalories(9, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 3F, 0F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_FISH(FoodInfo.builder().name("rice_fish").amountAndCalories(7, 0.7F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 2F, 0F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_PORK(FoodInfo.builder().name("rice_pork").amountAndCalories(7, 0.7F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 3F, 0F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    
    RICE_PORK_FRIED(FoodInfo.builder().name("rice_pork_fried").amountAndCalories(10, 1F).water(0.5F)
            .nutrients(2F, 0F, 0F, 4F, 4F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    
    RICE_MUSHROOM(FoodInfo.builder().name("rice_mushroom").amountAndCalories(6, 0.6F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 2F, 0F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_EGG(FoodInfo.builder().name("rice_egg").amountAndCalories(5, 0.6F).water(0.5F).nutrients(1.5F, 0F, 0F, 0F, 2F)
            .compostChance(0.85F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_BEEF_EGG(FoodInfo.builder().name("rice_beef_egg").amountAndCalories(10, 1F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 3.5F, 2F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_PORK_EGG(FoodInfo.builder().name("rice_pork_egg").amountAndCalories(9, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 3.5F, 2F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_OYAKO(FoodInfo.builder().name("rice_oyako").amountAndCalories(9, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 3.5F, 2F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_OYAKO_FISH(FoodInfo.builder().name("rice_oyako_fish").amountAndCalories(9, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(1.5F, 0F, 0F, 3.5F, 2F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_NATTO(
            FoodInfo.builder().name("rice_natto").amountAndCalories(5, 0.6F).water(0.5F).nutrients(2.5F, 0F, 2F, 0F, 0F)
                    .compostChance(0.85F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_NATTO_EGG(FoodInfo.builder().name("rice_natto_egg").amountAndCalories(6, 0.6F).water(0.5F).compostChance(1F)
            .nutrients(2.5F, 0F, 3F, 0F, 3F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),

    OMURICE(FoodInfo.builder().name("omurice").amountAndCalories(8, 0.6F).water(0.5F).nutrients(2F, 0F, 3F, 3F, 2F)
            .compostChance(1F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_FRIED(
            FoodInfo.builder().name("rice_fried").amountAndCalories(8, 0.6F).water(0.5F).nutrients(1.5F, 0F, 2F, 2F, 0F)
                    .compostChance(1F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),

    ONIGIRI(FoodInfo.builder().name("onigiri").amountAndCalories(6, 0.6F).water(0.5F).nutrients(2F, 0F, 1F, 0F, 0F)
            .compostChance(0.85F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    ONIGIRI_BAMBOO(FoodInfo.builder().name("onigiri_bamboo").amountAndCalories(7, 0.7F).water(0.5F).compostChance(0.85F)
            .nutrients(2F, 0F, 2F, 0F, 0F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    ONIGIRI_FISH(FoodInfo.builder().name("onigiri_fish").amountAndCalories(8, 0.7F).water(0.5F).compostChance(1F)
            .nutrients(2F, 0F, 1F, 2F, 0F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    ONIGIRI_MUSHROOM(
            FoodInfo.builder().name("onigiri_mushroom").amountAndCalories(7, 0.7F).water(0.5F).compostChance(1F)
                    .nutrients(2F, 0F, 2F, 0F, 0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    ONIGIRI_SEAWEED(FoodInfo.builder().name("onigiri_seaweed").amountAndCalories(7, 0.7F).water(0.5F).compostChance(1F)
            .nutrients(2F, 0F, 2F, 0F, 0.5F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    ONIGIRI_TEMPURA(FoodInfo.builder().name("onigiri_tempura").amountAndCalories(10, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(2F, 0F, 2F, 4F, 1F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),

    SUSHI(FoodInfo.builder().name("sushi").amountAndCalories(5, 0.6F).water(1F).nutrients(2F, 0F, 0F, 2F, 0F)
            .compostChance(0.85F).decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    SUSHI_SHRIMP(
            FoodInfo.builder().name("sushi_shrimp").amountAndCalories(5, 0.6F).water(1F).nutrients(2F, 0F, 0F, 2F, 0F)
                    .compostChance(0.85F).decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    SUSHI_TAMAGO(
            FoodInfo.builder().name("sushi_tamago").amountAndCalories(4, 0.6F).water(1F).nutrients(2F, 0F, 0F, 0F, 2F)
                    .compostChance(0.85F).decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),

    MOCHI(FoodInfo.builder().name("mochi").amountAndCalories(2, 0.5F).water(0.5F).nutrients(2F, 0F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    MOCHI_TOASTED(FoodInfo.builder().name("mochi_toasted").amountAndCalories(4, 0.6F).water(0.5F).compostChance(0.75F)
            .nutrients(3F, 0F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()),
    MOCHI_SAKURA(FoodInfo.builder().name("mochi_sakura").amountAndCalories(4, 0.6F).water(0.5F).compostChance(0.85F)
            .nutrients(3F, 0F, 1F, 0F, 0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    OHAGI(FoodInfo.builder().name("ohagi").amountAndCalories(6, 0.6F).water(0.5F).nutrients(3F, 0F, 0.5F, 0F, 0.5F)
            .compostChance(0.85F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    DAIFUKU(FoodInfo.builder().name("daifuku").amountAndCalories(4, 0.6F).water(0.5F).nutrients(3F, 0F, 0.5F, 0F, 0.5F)
            .compostChance(0.85F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    KUSA_DAIFUKU(FoodInfo.builder().name("kusa_daifuku").amountAndCalories(6, 0.6F).water(0.5F).compostChance(0.85F)
            .nutrients(3F, 0F, 1.5F, 0F, 0.5F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    DANGO(FoodInfo.builder().name("dango").amountAndCalories(2, 0.5F).water(0.5F).nutrients(2F, 0F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    DANANKO(FoodInfo.builder().name("dananko").amountAndCalories(6, 0.6F).water(1F).nutrients(3F, 0F, 0F, 0F, 1F)
            .compostChance(0.85F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    DANMITARASHI(FoodInfo.builder().name("danmitarashi").amountAndCalories(6, 0.4F).water(1F).compostChance(0.85F)
            .nutrients(3F, 0F, 0F, 0F, 1F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    DANSANSYOKU(FoodInfo.builder().name("dansansyoku").amountAndCalories(6, 0.6F).water(1F).compostChance(0.85F)
            .nutrients(3F, 0F, 0F, 0F, 1F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    SOUP_REDBEAN(FoodInfo.builder().name("soup_red_bean").amountAndCalories(6, 0.6F).water(50F).compostChance(0.5F)
            .nutrients(2F, 0F, 2F, 0F, 2F).decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),
    SOUP_MISO(FoodInfo.builder().name("soup_miso").amountAndCalories(5, 0.5F).water(50F).nutrients(0F, 0F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    OSUIMONO(FoodInfo.builder().name("osuimono").amountAndCalories(4, 0.5F).water(50F).nutrients(0F, 0F, 0F, 0F, 0F)
            .compostChance(0.5F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    BURGER_RAW(FoodInfo.builder().name("burger_raw").amountAndCalories(2, 0.2F).water(1F).compostChance(0.5F)
            .nutrients(0.5F, 0F, 0F, 3F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(200F).build()),
    BURGER(FoodInfo.builder().name("burger").amountAndCalories(6, 0.6F).water(2F).nutrients(0.5F, 0F, 0F, 4F, 0F)
            .compostChance(0.5F).decayModifier(2F).heatCapacity(1F).cookingTemp(200F).build()),
    BURGER_DISH(FoodInfo.builder().name("burger_dish").amountAndCalories(10, 0.8F).water(2.5F).compostChance(1F)
            .nutrients(0.5F, 0F, 2F, 4F, 0F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    HAMBURGER(FoodInfo.builder().name("hamburger").amountAndCalories(8, 0.6F).water(0.5F).nutrients(2F, 0F, 2F, 4F, 1F)
            .compostChance(1F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    CHEESE_BURGER(FoodInfo.builder().name("cheese_burger").amountAndCalories(10, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(2F, 0F, 2F, 4F, 3F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    CABBAGE_ROLL(FoodInfo.builder().name("cabbage_roll").amountAndCalories(4, 0.4F).water(25F).compostChance(1F)
            .nutrients(0F, 0F, 4F, 0F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SASHIMI(FoodInfo.builder().name("sashimi").amountAndCalories(6, 0.6F).water(1F).nutrients(0F, 0F, 1F, 3F, 0F)
            .compostChance(1F).decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    FISH_BAKE_SALT(FoodInfo.builder().name("fish_bake_salt").amountAndCalories(8, 0.8F).water(0.5F).compostChance(1F)
            .nutrients(0F, 0F, 0F, 4F, 0F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    FISH_BAKE(FoodInfo.builder().name("fish_bake").amountAndCalories(9, 0.8F).water(0.5F).nutrients(0F, 0F, 0F, 4F, 0F)
            .compostChance(1F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    YAKINIKU(FoodInfo.builder().name("yakiniku").amountAndCalories(10, 0.8F).water(0.5F).nutrients(0F, 0F, 0F, 4F, 0F)
            .compostChance(1F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    NIKUJAGA(FoodInfo.builder().name("nikujaga").amountAndCalories(8, 0.6F).water(0.5F).nutrients(2F, 0F, 3F, 4F, 2F)
            .compostChance(1F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    FUROFUKI_DAIKON(FoodInfo.builder().name("furofuki_daikon").amountAndCalories(5, 0.6F).water(25F).compostChance(1F)
            .nutrients(0F, 0F, 4F, 0F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),

    NIMONO_PUMPKIN(
            FoodInfo.builder().name("nimono_pumpkin").amountAndCalories(6, 0.5F).water(5F).nutrients(2F, 0F, 2F, 0F, 0F)
                    .compostChance(1F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    NIMONO_RADISH(
            FoodInfo.builder().name("nimono_radish").amountAndCalories(6, 0.5F).water(5F).nutrients(2F, 0F, 2F, 0F, 0F)
                    .compostChance(1F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    NIMONO_FISH(FoodInfo.builder().name("nimono_fish").amountAndCalories(8, 1F).water(6F).nutrients(0F, 0F, 0F, 3F, 3F)
            .compostChance(1F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    CHIKUZENNI(FoodInfo.builder().name("chikuzenni").amountAndCalories(12, 1F).water(5F).nutrients(0F, 5F, 5F, 5F, 5F)
            .compostChance(1F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    IMOTAKI(FoodInfo.builder().name("imotaki").amountAndCalories(12, 1F).water(5F).nutrients(0F, 5F, 5F, 5F, 5F)
            .compostChance(1F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    NOPPEI_JIRU(FoodInfo.builder().name("noppei_jiru").amountAndCalories(12, 1F).water(5F).nutrients(0F, 5F, 5F, 5F, 5F)
            .compostChance(1F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),

    // Fruits/produce
    GRAPE(FoodInfo.builder().name("grape").amountAndCalories(1, 0.25F).water(4F).nutrients(0.25F, 0F, 1F, 0F, 0F)
            .decayModifier(5F).heatCapacity(0F).cookingTemp(-1F).build()),
    GRAPE_GREEN(FoodInfo.builder().name("grape_green").amountAndCalories(2, 0.2F).water(4F).nutrients(0F, 2F, 0F, 0.5F, 0F)
            .decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),
    LEMON(FoodInfo.builder().name("lemon").amountAndCalories(1, 0.1F).water(5F).nutrients(0F, 2F, 0F, 0F, 0F)
            .decayModifier(3.5F).heatCapacity(1F).cookingTemp(480F).build()),
    UME(FoodInfo.builder().name("ume").amountAndCalories(2, 0.2F).water(5F).nutrients(0F, 1F, 0F, 0F, 0F)
            .decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    UMEBOSHI(FoodInfo.builder().name("umeboshi").amountAndCalories(2, 0.5F).water(5F).nutrients(0F, 2F, 0F, 0F, 0F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    MATSUTAKE(FoodInfo.builder().name("matsutake").amountAndCalories(1, 0.2F).water(1F).nutrients(0F, 0F, 2F, 0F, 0F)
            .decayModifier(2F).heatCapacity(2F).cookingTemp(480F).build()),
    EDODES(FoodInfo.builder().name("edodes").amountAndCalories(1, 0.2F).water(1F).nutrients(0F, 0F, 2F, 0F, 0F)
            .decayModifier(2F).heatCapacity(2F).cookingTemp(480F).build()),
    SHIMEJI(FoodInfo.builder().name("shimeji").amountAndCalories(1, 0.2F).water(1F).nutrients(0F, 0F, 2F, 0F, 0F)
            .decayModifier(2F).heatCapacity(2F).cookingTemp(480F).build()),
    CHESTNUT_TOASTED(FoodInfo.builder().name("chestnut_toasted").amountAndCalories(4, 0.4F).water(0.5F)
            .nutrients(2F, 0F, 2F, 0F, 0F).decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    ALMOND(FoodInfo.builder().name("almond").amountAndCalories(2, 0.4F).water(0F).nutrients(1F, 0F, 2F, 0F, 0F)
            .decayModifier(0F).heatCapacity(0F).cookingTemp(-1F).build()),

    // Bonito series
    BONITO(FoodInfo.builder().name("bonito").amountAndCalories(2, 0.2F).water(1F).nutrients(0F, 0F, 0F, 2F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    MACHINED_BONITO(FoodInfo.builder().name("machined_bonito").amountAndCalories(1, 0.2F).water(1F)
            .nutrients(0F, 0F, 0F, 2F, 2F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    BOILED_BONITO(FoodInfo.builder().name("boiled_bonito").amountAndCalories(2, 0.2F).water(3F).nutrients(0F, 0F, 0F, 2F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SMOKED_BONITO(FoodInfo.builder().name("smoked_bonito").amountAndCalories(3, 0.3F).water(0F).nutrients(0F, 0F, 0F, 2F, 2F)
            .decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    DRIED_BONITO(FoodInfo.builder().name("dried_bonito").amountAndCalories(3, 0.3F).water(0F).nutrients(0F, 0F, 0F, 2F, 2F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    BONITO_SHAVING(FoodInfo.builder().name("bonito_shaving").amountAndCalories(1, 0.1F).water(0F).nutrients(0F, 0F, 0F, 2F, 2F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),

    // Eggs
    EGG_SOFT(FoodInfo.builder().name("egg_soft").amountAndCalories(2, 0.6F).water(1F).nutrients(0F, 0F, 0F, 0.0F, 3.0F)
            .decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    EGG_SOYSAUCE(FoodInfo.builder().name("egg_soysauce").amountAndCalories(4, 0.6F).water(1F)
            .nutrients(0F, 0F, 0F, 0.0F, 3.5F).decayModifier(1.0F).heatCapacity(1F).cookingTemp(480F).build()),

    // Drinks
    LEMON_JUICE(FoodInfo.builder().name("lemon_juice").amountAndCalories(1, 0.1F).water(40F).nutrients(0F, 2F, 0F, 0F, 0F)
            .decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),
    SODA_WATER(FoodInfo.builder().name("soda_water").amountAndCalories(1, 0.1F).water(50F).nutrients(0F, 0F, 0F, 0F, 0F)
            .decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),
    BLACKCURRANT_JUICE(FoodInfo.builder().name("blackcurrant_juice").amountAndCalories(1, 0.1F).water(40F)
            .nutrients(0F, 2F, 0F, 0F, 0F).decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),
    ORANGE_JUICE(FoodInfo.builder().name("orange_juice").amountAndCalories(1, 0.1F).water(40F).nutrients(0F, 2F, 0F, 0F, 0F)
            .decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),

    // Rice dishes
    RICE_MATSUTAKE(FoodInfo.builder().name("rice_matsutake").amountAndCalories(8, 0.6F).water(0.5F)
            .nutrients(2F, 0F, 3F, 0F, 1F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_CURRY(FoodInfo.builder().name("rice_curry").amountAndCalories(6, 0.6F).water(0.5F).nutrients(2.5F, 0F, 1F, 1F, 1F)
            .decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_CURRY_KATSU(FoodInfo.builder().name("rice_curry_katsu").amountAndCalories(10, 0.8F).water(0.5F)
            .nutrients(3F, 0F, 1F, 4F, 1F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_CURRY_BURGER(FoodInfo.builder().name("rice_curry_burger").amountAndCalories(10, 0.8F).water(0.5F)
            .nutrients(3F, 0F, 1F, 4F, 1F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_CURRY_CHEESE(FoodInfo.builder().name("rice_curry_cheese").amountAndCalories(8, 0.8F).water(0.5F)
            .nutrients(2.5F, 0F, 1F, 1F, 4F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_CURRY_CHEESE_KATSU(FoodInfo.builder().name("rice_curry_cheese_katsu").amountAndCalories(12, 0.8F).water(0.5F)
            .nutrients(3F, 0F, 1F, 4F, 3F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    RICE_CURRY_CHEESE_BURGER(FoodInfo.builder().name("rice_curry_cheese_burger").amountAndCalories(12, 0.8F).water(0.5F)
            .nutrients(3F, 0F, 1F, 4F, 3F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    CURRY_OMURICE(FoodInfo.builder().name("curry_omurice").amountAndCalories(8, 0.6F).water(0.5F)
            .nutrients(3F, 0F, 1F, 4F, 3F).decayModifier(2.25F).heatCapacity(1F).cookingTemp(480F).build()),
    ZOSUI_ZUIKI(FoodInfo.builder().name("zosui_zuiki").amountAndCalories(6, 1F).water(5F).nutrients(2F, 0F, 2F, 0F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    ZOSUI(FoodInfo.builder().name("zosui").amountAndCalories(8, 1F).water(5F).nutrients(0F, 0F, 0F, 3F, 3F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    DRIED_BROWN_RICE(FoodInfo.builder().name("dried_brown_rice").amountAndCalories(4, 0.5F).water(0F)
            .nutrients(1.5F, 0F, 0F, 0F, 0F).decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    DRIED_RICE(FoodInfo.builder().name("dried_rice").amountAndCalories(4, 0.5F).water(0F).nutrients(1.5F, 0F, 0F, 0F, 0F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    FRIED_BROWN_RICE(FoodInfo.builder().name("fried_brown_rice").amountAndCalories(4, 0.5F).water(0F)
            .nutrients(1.5F, 0F, 0F, 0F, 0F).decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),

    // Noodles - ramen
    RAMEN(FoodInfo.builder().name("ramen").amountAndCalories(4, 0.5F).water(35F).nutrients(1.5F, 0F, 0F, 0F, 0F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_BEEF(FoodInfo.builder().name("ramen_beef").amountAndCalories(9, 0.8F).water(35F).nutrients(1.5F, 0F, 0F, 3F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_EGG(FoodInfo.builder().name("ramen_egg").amountAndCalories(5, 0.6F).water(35F).nutrients(1.5F, 0F, 0F, 0F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_TEMPURA(FoodInfo.builder().name("ramen_tempura").amountAndCalories(9, 0.8F).water(35F).nutrients(2F, 0F, 0F, 3F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_FRIEDTOFU(FoodInfo.builder().name("ramen_friedtofu").amountAndCalories(9, 0.7F).water(35F)
            .nutrients(2F, 0F, 2F, 0F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_KATSU(FoodInfo.builder().name("ramen_katsu").amountAndCalories(10, 0.8F).water(35F).nutrients(2F, 0F, 0F, 4F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_CHICKEN(FoodInfo.builder().name("ramen_chicken").amountAndCalories(9, 0.8F).water(35F).nutrients(2F, 0F, 0F, 4F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_CROQUETTE(FoodInfo.builder().name("ramen_croquette").amountAndCalories(9, 0.8F).water(35F)
            .nutrients(2F, 0F, 0F, 4F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    RAMEN_LARGE(FoodInfo.builder().name("ramen_large").amountAndCalories(12, 1F).water(35F).nutrients(2F, 0F, 5F, 5F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),

    // Noodles - udon
    UDON(FoodInfo.builder().name("udon").amountAndCalories(4, 0.5F).water(35F).nutrients(1.5F, 0F, 0F, 0F, 0F)
            .decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_BEEF(FoodInfo.builder().name("udon_beef").amountAndCalories(9, 0.8F).water(35F).nutrients(1.5F, 0F, 0F, 3F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_EGG(FoodInfo.builder().name("udon_egg").amountAndCalories(5, 0.6F).water(35F).nutrients(1.5F, 0F, 0F, 0F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_TEMPURA(FoodInfo.builder().name("udon_tempura").amountAndCalories(9, 0.8F).water(35F).nutrients(2F, 0F, 0F, 3F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_FRIEDTOFU(FoodInfo.builder().name("udon_friedtofu").amountAndCalories(9, 0.7F).water(35F)
            .nutrients(2F, 0F, 2F, 0F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_KATSU(FoodInfo.builder().name("udon_katsu").amountAndCalories(10, 0.8F).water(35F).nutrients(2F, 0F, 0F, 4F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_CROQUETTE(FoodInfo.builder().name("udon_croquette").amountAndCalories(9, 0.8F).water(35F)
            .nutrients(2F, 0F, 0F, 4F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_CHICKEN(FoodInfo.builder().name("udon_chicken").amountAndCalories(9, 0.8F).water(35F).nutrients(2F, 0F, 0F, 4F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    UDON_LARGE(FoodInfo.builder().name("udon_large").amountAndCalories(12, 1F).water(35F).nutrients(2F, 0F, 5F, 5F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    YAKI_UDON(FoodInfo.builder().name("yaki_udon").amountAndCalories(9, 0.7F).water(2.5F).nutrients(1.5F, 0F, 4F, 2F, 2F)
            .decayModifier(3.5F).heatCapacity(1F).cookingTemp(480F).build()),

    // Noodles - soba
    SOBA(FoodInfo.builder().name("soba").amountAndCalories(4, 0.5F).water(35F).nutrients(1.5F, 0F, 0F, 0F, 0F)
            .decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_BEEF(FoodInfo.builder().name("soba_beef").amountAndCalories(9, 0.8F).water(35F).nutrients(1.5F, 0F, 0F, 3F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_EGG(FoodInfo.builder().name("soba_egg").amountAndCalories(5, 0.6F).water(35F).nutrients(1.5F, 0F, 0F, 0F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_TEMPURA(FoodInfo.builder().name("soba_tempura").amountAndCalories(9, 0.8F).water(35F).nutrients(2F, 0F, 0F, 3F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_FRIEDTOFU(FoodInfo.builder().name("soba_friedtofu").amountAndCalories(9, 0.7F).water(35F)
            .nutrients(2F, 0F, 2F, 0F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_KATSU(FoodInfo.builder().name("soba_katsu").amountAndCalories(10, 0.8F).water(35F).nutrients(2F, 0F, 0F, 4F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_CROQUETTE(FoodInfo.builder().name("soba_croquette").amountAndCalories(9, 0.8F).water(35F)
            .nutrients(2F, 0F, 0F, 4F, 0F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_CHICKEN(FoodInfo.builder().name("soba_chicken").amountAndCalories(9, 0.8F).water(35F).nutrients(2F, 0F, 0F, 4F, 0F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_LARGE(FoodInfo.builder().name("soba_large").amountAndCalories(12, 1F).water(35F).nutrients(2F, 0F, 5F, 5F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    SOBA_ZARU(FoodInfo.builder().name("soba_zaru").amountAndCalories(6, 0.7F).water(5F).nutrients(2F, 0F, 0.5F, 0F, 0F)
            .decayModifier(3.5F).heatCapacity(1F).cookingTemp(480F).build()),
    YAKI_SOBA(FoodInfo.builder().name("yaki_soba").amountAndCalories(9, 0.7F).water(2.5F).nutrients(1.5F, 0F, 4F, 2F, 2F)
            .decayModifier(3.5F).heatCapacity(1F).cookingTemp(480F).build()),

    // Noodles - pasta
    PASTA_TOMATO(FoodInfo.builder().name("pasta_tomato").amountAndCalories(9, 0.8F).water(1.5F).nutrients(2F, 0F, 4F, 4F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    PASTA_MUSHROOM(FoodInfo.builder().name("pasta_mushroom").amountAndCalories(9, 0.8F).water(1.5F)
            .nutrients(2F, 0F, 4F, 4F, 2F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    PASTA_WHITESAUCE(FoodInfo.builder().name("pasta_whitesauce").amountAndCalories(9, 0.8F).water(1.5F)
            .nutrients(2F, 0F, 4F, 4F, 2F).decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    YAKI_PASTA(FoodInfo.builder().name("yaki_pasta").amountAndCalories(9, 0.7F).water(2.5F).nutrients(1.5F, 0F, 4F, 2F, 2F)
            .decayModifier(3.5F).heatCapacity(1F).cookingTemp(480F).build()),

    // Other dishes
    ODEN(FoodInfo.builder().name("oden").amountAndCalories(8, 0.6F).water(1F).nutrients(1F, 0F, 3F, 3F, 0F)
            .decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    EHOUMAKI(FoodInfo.builder().name("ehoumaki").amountAndCalories(8, 0.6F).water(1F).nutrients(2F, 0F, 3F, 3F, 2F)
            .decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    ONIGIRI_MATSUTAKE(FoodInfo.builder().name("onigiri_matsutake").amountAndCalories(9, 0.7F).water(0.5F)
            .nutrients(2F, 0F, 4F, 0F, 1F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    MABODOFU(FoodInfo.builder().name("mabodofu").amountAndCalories(8, 0.6F).water(1F).nutrients(1F, 0F, 3F, 1F, 0F)
            .decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    MABOQIEZI(FoodInfo.builder().name("maboqiezi").amountAndCalories(8, 0.6F).water(1F).nutrients(1F, 0F, 4F, 0F, 0F)
            .decayModifier(4F).heatCapacity(1F).cookingTemp(480F).build()),
    WHITE_STEW(FoodInfo.builder().name("white_stew").amountAndCalories(6, 0.6F).water(35F).nutrients(2F, 2F, 2F, 2F, 2F)
            .decayModifier(5F).heatCapacity(0F).cookingTemp(0F).build()),
    OCHAZUKE(FoodInfo.builder().name("ochazuke").amountAndCalories(6, 0.6F).water(50F).nutrients(0F, 0F, 2F, 2F, 2F)
            .decayModifier(5F).heatCapacity(1F).cookingTemp(480F).build()),
    CHAWANMUSHI(FoodInfo.builder().name("chawanmushi").amountAndCalories(6, 0.5F).water(5F).nutrients(0F, 0F, 3F, 3F, 3F)
            .decayModifier(5F).heatCapacity(2F).cookingTemp(480F).build()),
    FRUITSALAD(FoodInfo.builder().name("fruitsalad").amountAndCalories(6, 0.6F).water(15F).nutrients(2F, 4F, 0F, 0F, 2F)
            .decayModifier(5.5F).heatCapacity(1F).cookingTemp(480F).build()),
    KATSU_DISH(FoodInfo.builder().name("katsu_dish").amountAndCalories(10, 0.8F).water(0.5F)
            .nutrients(0.5F, 0F, 2F, 5F, 1F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    CROQUETTE_DISH(FoodInfo.builder().name("croquette_dish").amountAndCalories(8, 0.6F).water(0.5F)
            .nutrients(0.5F, 0F, 2F, 5F, 1F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    // Okonomiyaki
    DOUGH_OKINOYAKI(FoodInfo.builder().name("dough_okinoyaki").amountAndCalories(2, 0.2F).water(0F)
            .nutrients(2F, 0F, 2F, 2F, 2F).decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    OKINOYAKI(FoodInfo.builder().name("okinoyaki").amountAndCalories(8, 0.8F).water(0.5F).nutrients(2F, 0F, 3F, 3F, 3F)
            .decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),
    OKINOYAKI_PLUS(FoodInfo.builder().name("okinoyaki_plus").amountAndCalories(10, 1F).water(1F).nutrients(3F, 3F, 3F, 3F, 3F)
            .decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),
    OKINOYAKI_FINAL(FoodInfo.builder().name("okinoyaki_final").amountAndCalories(12, 1F).water(1F).nutrients(5F, 5F, 5F, 5F, 5F)
            .decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),

    // Sweets
    MAPLE_COOKIE(FoodInfo.builder().name("maple_cookie").amountAndCalories(3, 0.25F).water(0.75F)
            .nutrients(2F, 0F, 0F, 0F, 0.2F).decayModifier(0.8F).heatCapacity(1F).cookingTemp(480F).build()),
    PUDDING(FoodInfo.builder().name("pudding").amountAndCalories(4, 0.4F).water(2F).nutrients(2F, 0F, 0F, 0F, 2F)
            .decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    PUDDING_MAPLE(FoodInfo.builder().name("pudding_maple").amountAndCalories(6, 0.6F).water(2F).nutrients(3F, 0F, 0F, 0F, 2F)
            .decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    PUDDING_MOCHA(FoodInfo.builder().name("pudding_mocha").amountAndCalories(6, 0.6F).water(2F).nutrients(3F, 0F, 0F, 0F, 2F)
            .decayModifier(2.5F).heatCapacity(1F).cookingTemp(480F).build()),
    POUND_CAKE(FoodInfo.builder().name("pound_cake").amountAndCalories(5, 0.6F).water(0.5F).nutrients(4F, 2F, 0F, 0F, 4F)
            .decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    POUND_CAKE_MOCHA(FoodInfo.builder().name("pound_cake_mocha").amountAndCalories(7, 0.6F).water(0.5F)
            .nutrients(4F, 2F, 0F, 0F, 4F).decayModifier(3F).heatCapacity(1F).cookingTemp(480F).build()),
    DORAYAKI(FoodInfo.builder().name("dorayaki").amountAndCalories(6, 0.6F).water(1F).nutrients(5F, 2F, 2F, 0F, 0F)
            .decayModifier(2F).heatCapacity(2F).cookingTemp(480F).build()),
    RAW_TAIYAKI(FoodInfo.builder().name("raw_taiyaki").amountAndCalories(2, 0.2F).water(0.5F)
            .nutrients(0.5F, 0F, 0F, 0F, 0F).decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()),
    TAIYAKI(FoodInfo.builder().name("taiyaki").amountAndCalories(6, 0.6F).water(0.5F).nutrients(4F, 0F, 0F, 0F, 4F)
            .decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),
    TAIYAKI_MOCHA(FoodInfo.builder().name("taiyaki_mocha").amountAndCalories(8, 0.6F).water(0.5F)
            .nutrients(4F, 0F, 0F, 0F, 4F).decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),
    MOCHA_COOKIE(FoodInfo.builder().name("mocha_cookie").amountAndCalories(5, 0.25F).water(0.75F)
            .nutrients(2F, 0F, 0F, 0F, 0.2F).decayModifier(0.8F).heatCapacity(1F).cookingTemp(480F).build()),

    // Military rations
    HYOROGAN(FoodInfo.builder().name("hyorogan").amountAndCalories(4, 0.5F).water(0F).nutrients(2F, 0F, 0F, 0F, 0F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),
    SUIKATSUGAN(FoodInfo.builder().name("suikatsugan").amountAndCalories(4, 0.5F).water(20F).nutrients(1.5F, 2F, 0F, 0F, 0F)
            .decayModifier(0F).heatCapacity(1F).cookingTemp(480F).build()),

    // Roast matsutake
    ROAST_MATSUTAKE(FoodInfo.builder().name("roast_matsutake").amountAndCalories(5, 0.6F).water(0F)
            .nutrients(0F, 0F, 3F, 0F, 0F).decayModifier(1F).heatCapacity(1F).cookingTemp(480F).build()),

    ;

    private final FoodInfo info;

    private SakuraFoodSet(FoodInfo info) {
        this.info = info;
    }

    public FoodInfo getFoodInfo() {
        return info;
    }
}
