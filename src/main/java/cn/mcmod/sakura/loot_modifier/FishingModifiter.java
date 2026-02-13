package cn.mcmod.sakura.loot_modifier;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class FishingModifiter extends LootModifier {

    public static final Codec<FishingModifiter> CODEC = RecordCodecBuilder
            .create(inst -> codecStart(inst).apply(inst, FishingModifiter::new));

    protected FishingModifiter(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // In 1.12.2, the fishing loot added shrimp (meta 78, weight 45), machined_fish (meta 141, weight 45),
        // and seaweed_raw (weight 15). We replicate this with weighted random selection.
        List<Item> fishingItems = Lists.newArrayList(
                FoodRegistry.FOODSET.get(SakuraFoodSet.SHRIMP).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH).get(),
                FoodRegistry.FOODSET.get(SakuraFoodSet.SEAWEED_RAW).get()
        );
        // Weights: shrimp=45, machined_fish=45, seaweed_raw=15 -> total=105
        int roll = context.getRandom().nextInt(105);
        Item selected;
        if (roll < 45) {
            selected = fishingItems.get(0); // shrimp
        } else if (roll < 90) {
            selected = fishingItems.get(1); // machined_fish
        } else {
            selected = fishingItems.get(2); // seaweed_raw
        }
        generatedLoot.add(new ItemStack(selected));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
