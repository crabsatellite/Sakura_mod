package cn.mcmod.sakura.item;

import java.util.Map;
import java.util.function.Supplier;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.enums.SakuraAlcoholSet;
import cn.mcmod.sakura.item.enums.SakuraCocktailSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.item.enums.SakuraTeaSet;
import cn.mcmod_mmf.mmlib.registry.ItemRegistryUtil;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DrinkRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SakuraMod.MODID);

    public static final Map<SakuraTeaSet, RegistryObject<Item>> TEAS = ItemRegistryUtil.mapOfKeys(
            SakuraTeaSet.class,
            tea -> register(tea.getName(), () -> new DrinkItem(
                    SakuraMod.defaultItemProperties(),
                    () -> ItemRegistry.CUP.get(),
                    false,
                    tea.getEffects())));

    public static final Map<SakuraAlcoholSet, RegistryObject<Item>> ALCOHOLS = ItemRegistryUtil.mapOfKeys(
            SakuraAlcoholSet.class,
            alcohol -> register(alcohol.getName(), () -> new DrinkItem(
                    SakuraMod.defaultItemProperties(),
                    () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get(),
                    true,
                    alcohol.getEffects())));

    public static final Map<SakuraCocktailSet, RegistryObject<Item>> COCKTAILS = ItemRegistryUtil.mapOfKeys(
            SakuraCocktailSet.class,
            cocktail -> register(cocktail.getName(), () -> new DrinkItem(
                    SakuraMod.defaultItemProperties(),
                    () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.EMPTY_BOTTLE).get(),
                    true,
                    cocktail.getEffects())));

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }
}
