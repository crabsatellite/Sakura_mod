package cn.mcmod.sakura.item;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.enums.SakuraAlcoholSet;
import cn.mcmod.sakura.item.enums.SakuraCocktailSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.item.enums.SakuraTeaSet;
import cn.mcmod_mmf.mmlib.registry.ItemRegistryUtil;

import java.util.Map;
import java.util.function.Supplier;

public class DrinkRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, SakuraMod.MODID);

    public static final Map<SakuraTeaSet, DeferredHolder<Item, Item>> TEAS = ItemRegistryUtil.mapOfKeys(
            SakuraTeaSet.class,
            tea -> register(tea.getName(), () -> new DrinkItem(
                    SakuraMod.defaultItemProperties(),
                    () -> ItemRegistry.CUP.get(),
                    false,
                    tea.getEffects())));

    public static final Map<SakuraAlcoholSet, DeferredHolder<Item, Item>> ALCOHOLS = ItemRegistryUtil.mapOfKeys(
            SakuraAlcoholSet.class,
            alcohol -> register(alcohol.getName(), () -> new DrinkItem(
                    SakuraMod.defaultItemProperties(),
                    () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get(),
                    true,
                    alcohol.getEffects())));

    public static final Map<SakuraCocktailSet, DeferredHolder<Item, Item>> COCKTAILS = ItemRegistryUtil.mapOfKeys(
            SakuraCocktailSet.class,
            cocktail -> register(cocktail.getName(), () -> new DrinkItem(
                    SakuraMod.defaultItemProperties(),
                    () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get(),
                    true,
                    cocktail.getEffects())));

    @SuppressWarnings("unchecked")
    private static <V extends Item> DeferredHolder<Item, V> register(String name, Supplier<V> item) {
        return (DeferredHolder<Item, V>) (DeferredHolder<?, ?>) ITEMS.register(name, item);
    }
}
