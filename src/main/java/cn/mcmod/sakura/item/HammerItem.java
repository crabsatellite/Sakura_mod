package cn.mcmod.sakura.item;

import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import cn.mcmod.sakura.tags.SakuraBlockTags;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(attackDamage, attackSpeed, tier, SakuraBlockTags.MINEABLE_WITH_HAMMER, properties);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        if (copy.hurt(1, net.minecraft.util.RandomSource.create(), null)) {
            return ItemStack.EMPTY;
        }
        return copy;
    }
}
