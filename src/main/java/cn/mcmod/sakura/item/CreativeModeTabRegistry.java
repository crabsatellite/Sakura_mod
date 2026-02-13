package cn.mcmod.sakura.item;

import cn.mcmod.sakura.SakuraConfig;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SakuraMod.MODID);
    public static final RegistryObject<CreativeModeTab> GROUP = TABS.register(
            "sakura",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI).get()))
                    .title(Component.translatable("itemGroup.sakura"))
                    .displayItems(
                            (parameters, output) -> {
                                BlockItemRegistry.ITEMS.getEntries().forEach((entry) -> {
                                    if (shouldShowInCreative(entry.getId().getPath())) {
                                        output.accept(new ItemStack(entry.get()));
                                    }
                                });
                                ItemRegistry.ITEMS.getEntries().forEach((entry) -> {
                                    if (shouldShowInCreative(entry.getId().getPath())) {
                                        output.accept(new ItemStack(entry.get()));
                                    }
                                });
                                FoodRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                                BucketItemRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                                DrinkRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                            }
                    )
                    .build()
    );

    private static boolean shouldShowInCreative(String id) {
        // Bamboo-related items (except bambooshoot which is a food)
        if (!SakuraConfig.COMMON.showBambooInCreative.get()) {
            if (id.contains("bamboo") && !id.contains("bambooshoot")) {
                return false;
            }
        }
        // Sakura/Cherry-related items (except sakura_diamond which is unique to the mod)
        if (!SakuraConfig.COMMON.showSakuraInCreative.get()) {
            if (id.contains("sakura") && !id.contains("diamond")) {
                return false;
            }
        }
        return true;
    }
}
