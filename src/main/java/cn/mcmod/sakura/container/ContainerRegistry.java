package cn.mcmod.sakura.container;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister
            .create(ForgeRegistries.MENU_TYPES, SakuraMod.MODID);

    public static final RegistryObject<MenuType<StoneMortarContainer>> STONE_MORTAR = CONTAINER_TYPES
            .register("stone_mortar", () -> IForgeMenuType.create(StoneMortarContainer::new));

    public static final RegistryObject<MenuType<CookingPotContainer>> COOKING_POT = CONTAINER_TYPES
            .register("cooking_pot", () -> IForgeMenuType.create(CookingPotContainer::new));
    
    public static final RegistryObject<MenuType<FermenterContainer>> FERMENTER = CONTAINER_TYPES
            .register("fermenter", () -> IForgeMenuType.create(FermenterContainer::new));
    
    public static final RegistryObject<MenuType<DistillerContainer>> DISTILLER = CONTAINER_TYPES
            .register("distiller", () -> IForgeMenuType.create(DistillerContainer::new));

    public static final RegistryObject<MenuType<CampfirePotContainer>> CAMPFIRE_POT = CONTAINER_TYPES
            .register("campfire_pot", () -> IForgeMenuType.create(CampfirePotContainer::new));

    public static final RegistryObject<MenuType<MapleCauldronContainer>> MAPLE_CAULDRON = CONTAINER_TYPES
            .register("maple_cauldron", () -> IForgeMenuType.create(MapleCauldronContainer::new));

    public static final RegistryObject<MenuType<BarrelOutputContainer>> BARREL_OUTPUT = CONTAINER_TYPES
            .register("barrel_output", () -> IForgeMenuType.create(BarrelOutputContainer::new));
}
