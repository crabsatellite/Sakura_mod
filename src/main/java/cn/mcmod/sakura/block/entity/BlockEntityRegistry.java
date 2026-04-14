package cn.mcmod.sakura.block.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(Registries.BLOCK_ENTITY_TYPE, SakuraMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneMortarBlockEntity>> STONE_MORTAR = BLOCK_ENTITIES
            .register("stone_mortar", () -> BlockEntityType.Builder
                    .of(StoneMortarBlockEntity::new, BlockRegistry.STONE_MORTAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CookingPotBlockEntity>> COOKING_POT = BLOCK_ENTITIES.register(
            "cooking_pot",
            () -> BlockEntityType.Builder.of(CookingPotBlockEntity::new, BlockRegistry.COOKING_POT.get()).build(null));
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FermenterBlockEntity>> FERMENTER = BLOCK_ENTITIES.register(
            "fermenter",
            () -> BlockEntityType.Builder.of(FermenterBlockEntity::new, BlockRegistry.FERMENTER.get()).build(null));
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DistillerBlockEntity>> DISTILLER = BLOCK_ENTITIES.register(
            "distiller",
            () -> BlockEntityType.Builder.of(DistillerBlockEntity::new, BlockRegistry.DISTILLER.get()).build(null));
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ObonBlockEntity>> OBON = BLOCK_ENTITIES.register(
            "obon",
            () -> BlockEntityType.Builder.of(ObonBlockEntity::new, BlockRegistry.OBON.get()).build(null));
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD = BLOCK_ENTITIES.register(
            "chopping_board",
            () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, BlockRegistry.CHOPPING_BOARD.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CampfireBlockEntity>> CAMPFIRE = BLOCK_ENTITIES.register(
            "campfire",
            () -> BlockEntityType.Builder.of(CampfireBlockEntity::new,
                    BlockRegistry.CAMPFIRE_IDLE.get(), BlockRegistry.CAMPFIRE_LIT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CampfirePotBlockEntity>> CAMPFIRE_POT = BLOCK_ENTITIES.register(
            "campfire_pot",
            () -> BlockEntityType.Builder.of(CampfirePotBlockEntity::new,
                    BlockRegistry.CAMPFIRE_POT_IDLE.get(), BlockRegistry.CAMPFIRE_POT_LIT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MapleCauldronBlockEntity>> MAPLE_CAULDRON = BLOCK_ENTITIES.register(
            "maple_cauldron",
            () -> BlockEntityType.Builder.of(MapleCauldronBlockEntity::new, BlockRegistry.MAPLE_CAULDRON.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShojiBlockEntity>> SHOJI = BLOCK_ENTITIES.register(
            "shoji",
            () -> BlockEntityType.Builder.of(ShojiBlockEntity::new, BlockRegistry.SHOJI.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BarrelOutputBlockEntity>> BARREL_OUTPUT = BLOCK_ENTITIES.register(
            "barrel_output",
            () -> BlockEntityType.Builder.of(BarrelOutputBlockEntity::new, BlockRegistry.BARREL_OUT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StrawWebBlockEntity>> STRAW_WEB = BLOCK_ENTITIES.register(
            "straw_web",
            () -> BlockEntityType.Builder.of(StrawWebBlockEntity::new, BlockRegistry.STRAW_WEB.get()).build(null));
}
