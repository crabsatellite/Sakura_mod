package cn.mcmod.sakura.item.enums;

import java.util.function.Supplier;

import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod_mmf.mmlib.item.info.FoodInfo;
import net.minecraft.world.item.Item;

public enum SakuraCuisineSet {
    BEEF_STICK(FoodInfo.builder().name("beef_stick").amountAndCalories(8, 0.8F).water(2F).nutrients(0F, 0F, 0F, 4F, 0F)
            .decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build(),ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO)),
    CHICKEN_STICK(FoodInfo.builder().name("chicken_stick").amountAndCalories(6, 0.4F).water(2F).nutrients(0F, 0F, 0F, 4F, 0F)
            .decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build(),ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO)),
    PORK_STICK(FoodInfo.builder().name("pork_stick").amountAndCalories(6, 0.6F).water(2F).nutrients(0F, 0F, 0F, 4F, 0F)
            .decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build(),ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO)),
    BENTO_STANDARD(FoodInfo.builder().name("bento").amountAndCalories(10, 1F).water(0.5F).nutrients(3F, 0F, 3F, 3F, 3F)
            .decayModifier(3F).heatCapacity(0F).cookingTemp(-1F).build(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX)),
    BENTO_DELUXE(FoodInfo.builder().name("bento_deluxe").amountAndCalories(12, 1F).water(0.5F).nutrients(3F, 0F, 3F, 3F, 3F)
            .decayModifier(3F).heatCapacity(0F).cookingTemp(-1F).build(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX)),
    BENTO_PREMIUM(FoodInfo.builder().name("bento_premium").amountAndCalories(14, 1F).water(0.5F).nutrients(3F, 0F, 3F, 3F, 3F)
            .decayModifier(3F).heatCapacity(0F).cookingTemp(-1F).build(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX)),
    BENTO_SUPREME(FoodInfo.builder().name("bento_supreme").amountAndCalories(16, 1F).water(0.5F).nutrients(3F, 0F, 3F, 3F, 3F)
            .decayModifier(3F).heatCapacity(0F).cookingTemp(-1F).build(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BENTO_BOX))
    ;
    private final FoodInfo info;
    private final Supplier<Item> container;
    
    private SakuraCuisineSet(FoodInfo info, Supplier<Item> container) {
        this.info = info;
        this.container = container;
    }

    public FoodInfo getFoodInfo() {
        return info;
    }

    public Supplier<Item> getContainer() {
        return container;
    }
}
