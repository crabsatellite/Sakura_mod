package cn.mcmod.sakura.item;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DrinkItem extends Item {
    private final MobEffectInstance[] effects;
    private final boolean isAlcoholic;
    private final Supplier<Item> containerItem;

    public DrinkItem(Properties properties, Supplier<Item> containerItem, boolean isAlcoholic, MobEffectInstance... effects) {
        super(properties.stacksTo(16));
        this.effects = effects;
        this.isAlcoholic = isAlcoholic;
        this.containerItem = containerItem;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            for (MobEffectInstance effect : effects) {
                entity.addEffect(new MobEffectInstance(effect));
            }
            if (isAlcoholic && level.getRandom().nextFloat() < 0.7F) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            }
        }

        if (entity instanceof Player player) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                ItemStack containerStack = new ItemStack(containerItem.get());
                if (stack.isEmpty()) {
                    return containerStack;
                }
                player.getInventory().add(containerStack);
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
