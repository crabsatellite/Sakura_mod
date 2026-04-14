package cn.mcmod.sakura.recipes.base;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * Local replacement for cn.mcmod_mmf.mmlib.recipe.ChanceResult
 * which was removed from MMLib 1.21.1.
 */
public record ChanceResult(ItemStack stack, float chance) {
    public static final ChanceResult EMPTY = new ChanceResult(ItemStack.EMPTY, 0);

    public ItemStack rollOutput(RandomSource random, int fortuneLevel) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        float adjustedChance = Math.min(1.0f, chance + fortuneLevel * 0.1f);
        if (random.nextFloat() < adjustedChance) {
            return stack.copy();
        }
        return ItemStack.EMPTY;
    }
}
