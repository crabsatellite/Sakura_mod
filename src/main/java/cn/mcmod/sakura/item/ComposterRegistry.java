package cn.mcmod.sakura.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import cn.mcmod_mmf.mmlib.item.IFoodLike;

import java.util.List;
import java.util.function.Supplier;

public class ComposterRegistry {
    public static final float SEED_COMPOST_CHANCE = 0.3F;
    public static final List<Supplier<? extends Item>> CROP_INPUTS = List.of(
            ItemRegistry.CABBAGE_SEEDS,
            ItemRegistry.BUCKWHEAT,
            ItemRegistry.RED_BEAN,
            ItemRegistry.SOYBEAN,
            ItemRegistry.RADISH_SEEDS,
            ItemRegistry.ONION_SEEDS,
            ItemRegistry.RICE_SEEDS,
            ItemRegistry.TOMATO_SEEDS,
            ItemRegistry.TARO,
            ItemRegistry.EGGPLANT_SEEDS,
            ItemRegistry.RAPESEEDS,
            ItemRegistry.PEPPER_SEEDS,
            ItemRegistry.VANILLA_SEEDS,
            ItemRegistry.GRAPE_SEEDS,
            ItemRegistry.HOP_SEEDS,
            ItemRegistry.SEAWEED_SEEDS
    );

    public static void registerCompost() {
        FoodRegistry.ITEMS.getEntries().forEach( item->{
            register(item.get());
        });
        CROP_INPUTS.forEach(item -> register(item.get(), SEED_COMPOST_CHANCE));
    }
    
    private static void register(Item item) {
        if(item instanceof IFoodLike food) {
            if(food.getFoodInfo().getCompostChance() > 0)
            register(item, food.getFoodInfo().getCompostChance());
        }
    }
    
    private static void register(Item item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item, chance);
    }
}
