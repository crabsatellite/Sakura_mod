package cn.mcmod.sakura.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class SakuraTiers {
    // Tachi: diamond-level mining, moderate durability, fast, good damage, high enchantability
    public static final Tier TACHI = new ForgeTier(3, 457, 7.0F, 3.0F, 18,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);

    // Sakura: above-diamond mining, high durability, very fast, excellent damage
    public static final Tier SAKURA = new ForgeTier(4, 1561, 8.5F, 4.0F, 12,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);

    // Straw: wood-level mining, low durability, slow, weak, low enchantability
    public static final Tier STRAW = new ForgeTier(0, 256, 2.0F, 1.0F, 8,
            BlockTags.MINEABLE_WITH_HOE, () -> Ingredient.EMPTY);
}
