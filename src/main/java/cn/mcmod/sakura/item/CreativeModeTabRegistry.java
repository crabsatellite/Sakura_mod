package cn.mcmod.sakura.item;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraConfig;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.item.enums.SakuraTeaSet;

import java.util.Set;

public class CreativeModeTabRegistry {
    private static final Set<String> LEGACY_INTERNAL_BLOCK_ITEMS = Set.of(
            "straw_web",
            "campfire_idle",
            "campfire_pot_idle",
            "barrel_out",
            "soba_block",
            "ramen_block",
            "pasta_block",
            "udon_unfinished_block"
    );

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SakuraMod.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GROUP = TABS.register(
            "sakura",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(BlockRegistry.SAKURA_LOG.get()))
                    .title(Component.translatable("itemGroup.sakura.blocks"))
                    .displayItems(
                            (parameters, output) -> {
                                BlockItemRegistry.ITEMS.getEntries().forEach((entry) -> {
                                    if (shouldShowInCreative(entry.getId().getPath())) {
                                        output.accept(new ItemStack(entry.get()));
                                    }
                                });
                            }
                    )
                    .build()
    );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS = TABS.register(
            "sakura_items",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA).get()))
                    .title(Component.translatable("itemGroup.sakura.items"))
                    .displayItems(
                            (parameters, output) -> {
                                ItemRegistry.ITEMS.getEntries().forEach((entry) -> {
                                    if (shouldShowInCreative(entry.getId().getPath())) {
                                        output.accept(new ItemStack(entry.get()));
                                    }
                                });
                            }
                    )
                    .build()
    );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FOODS = TABS.register(
            "sakura_foods",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI).get()))
                    .title(Component.translatable("itemGroup.sakura.foods"))
                    .displayItems(
                            (parameters, output) -> {
                                FoodRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                            }
                    )
                    .build()
    );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DRINKS = TABS.register(
            "sakura_drinks",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(DrinkRegistry.TEAS.get(SakuraTeaSet.GREEN_TEA).get()))
                    .title(Component.translatable("itemGroup.sakura.drinks"))
                    .displayItems(
                            (parameters, output) -> {
                                DrinkRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                                BucketItemRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                            }
                    )
                    .build()
    );

    public static boolean shouldShowInCreative(String id) {
        if (isLegacyInternalBlockItem(id)) {
            return false;
        }
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

    public static boolean isLegacyInternalBlockItem(String id) {
        return LEGACY_INTERNAL_BLOCK_ITEMS.contains(id);
    }
}
