package cn.mcmod.sakura.item;

import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import cn.mcmod.sakura.tags.SakuraBlockTags;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, SakuraBlockTags.MINEABLE_WITH_HAMMER, properties.attributes(DiggerItem.createAttributes(tier, attackDamage, attackSpeed)));
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setDamageValue(copy.getDamageValue() + 1);
        if (copy.getDamageValue() >= copy.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return copy;
    }
}
