package cn.mcmod.sakura.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SakuraTiers {
    // Tachi: diamond-level mining, moderate durability, fast, good damage, high enchantability
    public static final Tier TACHI = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, 457, 7.0F, 3.0F, 18, () -> Ingredient.EMPTY);

    // Sakura: above-diamond mining, high durability, very fast, excellent damage
    public static final Tier SAKURA = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, 1561, 8.5F, 4.0F, 12, () -> Ingredient.EMPTY);

    // Straw: wood-level mining, low durability, slow, weak, low enchantability
    public static final Tier STRAW = new SimpleTier(BlockTags.MINEABLE_WITH_HOE, 256, 2.0F, 1.0F, 8, () -> Ingredient.EMPTY);

    private record SimpleTier(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float attackDamageBonus,
                              int enchantmentValue, Supplier<Ingredient> repairIngredient) implements Tier {
        @Override
        public int getUses() { return uses; }
        @Override
        public float getSpeed() { return speed; }
        @Override
        public float getAttackDamageBonus() { return attackDamageBonus; }
        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() { return incorrectBlocksForDrops; }
        @Override
        public int getEnchantmentValue() { return enchantmentValue; }
        @Override
        public Ingredient getRepairIngredient() { return repairIngredient.get(); }
    }
}
