package cn.mcmod.sakura.container;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister
            .create(BuiltInRegistries.MENU, SakuraMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<StoneMortarContainer>> STONE_MORTAR = CONTAINER_TYPES
            .register("stone_mortar", () -> IMenuTypeExtension.create(StoneMortarContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<CookingPotContainer>> COOKING_POT = CONTAINER_TYPES
            .register("cooking_pot", () -> IMenuTypeExtension.create(CookingPotContainer::new));
    
    public static final DeferredHolder<MenuType<?>, MenuType<FermenterContainer>> FERMENTER = CONTAINER_TYPES
            .register("fermenter", () -> IMenuTypeExtension.create(FermenterContainer::new));
    
    public static final DeferredHolder<MenuType<?>, MenuType<DistillerContainer>> DISTILLER = CONTAINER_TYPES
            .register("distiller", () -> IMenuTypeExtension.create(DistillerContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<CampfirePotContainer>> CAMPFIRE_POT = CONTAINER_TYPES
            .register("campfire_pot", () -> IMenuTypeExtension.create(CampfirePotContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<MapleCauldronContainer>> MAPLE_CAULDRON = CONTAINER_TYPES
            .register("maple_cauldron", () -> IMenuTypeExtension.create(MapleCauldronContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<BarrelOutputContainer>> BARREL_OUTPUT = CONTAINER_TYPES
            .register("barrel_output", () -> IMenuTypeExtension.create(BarrelOutputContainer::new));
}
