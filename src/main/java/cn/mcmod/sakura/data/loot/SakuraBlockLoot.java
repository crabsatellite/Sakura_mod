package cn.mcmod.sakura.data.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import cn.mcmod.sakura.block.BambooPlant;
import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.HotSpringBlock;
import cn.mcmod.sakura.block.crops.RiceCropRoot;
import cn.mcmod.sakura.block.foods.NabeBlock;
import cn.mcmod.sakura.block.foods.TeishokuBlock;
import cn.mcmod.sakura.block.foods.TeishokuFinishedBlock;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod_mmf.mmlib.data.loot.AbstartctBlockLoot;

import java.util.Set;

public class SakuraBlockLoot extends AbstartctBlockLoot {

    public SakuraBlockLoot(net.minecraft.core.HolderLookup.Provider provider) {
		super(provider);
	}

    @Override
    public void addTables() {
        dropSelf(BlockRegistry.BAMBOO_BLOCK.get());
        BlockRegistry.BLOCKS.getEntries().forEach(block -> {
            if (block.get() instanceof LeavesBlock)
                ;
            else if (block.get() instanceof CropBlock)
                ;
            else if (block.get() instanceof TeishokuBlock)
                ;
            else if (block.get() instanceof RiceCropRoot)
                ;
            else if (block.get() instanceof BambooPlant)
                dropOther(block.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO).get());
            else if (block.get() instanceof TeishokuFinishedBlock)
                dropOther(block.get(), BlockItemRegistry.OBON.get());
            else if (block.get() instanceof HotSpringBlock)
                ; // skip - block has .noLootTable() in its properties
            else if (block.get() == BlockRegistry.SAKURA_DIAMOND_ORE.get())
                ; // handled explicitly below with fortune
            else if (block.get() instanceof SlabBlock)
                this.add(block.get(), createSlabItemTable(block.get()));
            else if (block.get() instanceof DoorBlock)
                this.add(block.get(), createDoorTable(block.get()));
            else if (block.get() instanceof NabeBlock)
                ; // skip - handled explicitly below with bites-based logic
            else
                dropSelf(block.get());
        });

        // Leaves drops (sapling chance)
        this.add(BlockRegistry.MAPLE_LEAVES_RED.get(), createLeavesDrops(BlockRegistry.MAPLE_LEAVES_RED.get(),
                BlockRegistry.MAPLE_SAPLING_RED.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.MAPLE_LEAVES_ORANGE.get(), createLeavesDrops(BlockRegistry.MAPLE_LEAVES_ORANGE.get(),
                BlockRegistry.MAPLE_SAPLING_ORANGE.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.MAPLE_LEAVES_YELLOW.get(), createLeavesDrops(BlockRegistry.MAPLE_LEAVES_YELLOW.get(),
                BlockRegistry.MAPLE_SAPLING_YELLOW.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.MAPLE_LEAVES_GREEN.get(), createLeavesDrops(BlockRegistry.MAPLE_LEAVES_GREEN.get(),
                BlockRegistry.MAPLE_SAPLING_GREEN.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.SAKURA_LEAVES.get(), createLeavesDrops(BlockRegistry.SAKURA_LEAVES.get(),
                BlockRegistry.SAKURA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.UME_LEAVES.get(), createLeavesDrops(BlockRegistry.UME_LEAVES.get(),
                BlockRegistry.UME_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        // Teishoku (food plate) drops
        this.createTeishoku(BlockRegistry.TEISHOKU_FISH_COOKED.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_FISH_RAW.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_FISH_SALT.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_TAMAGOYAKI.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_YAKINIKU.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_TEMPURA.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_FRIED.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_KATSU.get());
        this.createTeishoku(BlockRegistry.TEISHOKU_BURGER.get());
        this.createTeishoku(BlockRegistry.SUSHI_PLATE.get());
        this.createTeishoku(BlockRegistry.TEMPURA_PLATE.get());

        // Nabe (hot pot) drops - at BITES==0 drop nabe itself, otherwise drop cooking pot
        this.createNabe(BlockRegistry.NABE_SUKIYAKI.get());
        this.createNabe(BlockRegistry.NABE_ODEN.get());

        // Ore drops (with fortune)
        this.add(BlockRegistry.SAKURA_DIAMOND_ORE.get(), createOreDrop(BlockRegistry.SAKURA_DIAMOND_ORE.get(),
                ItemRegistry.SAKURA_DIAMOND.get()));

        // Existing crop drops
        createCrop(BlockRegistry.CABBAGE_CROP.get(), FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE).get(),
                ItemRegistry.CABBAGE_SEEDS.get(), 7);

        createCrop(BlockRegistry.RADISH_CROP.get(), FoodRegistry.FOODSET.get(SakuraFoodSet.RADISH).get(),
                ItemRegistry.RADISH_SEEDS.get(), 3);

        createCrop(BlockRegistry.ONION_CROP.get(), FoodRegistry.FOODSET.get(SakuraFoodSet.ONION).get(),
                ItemRegistry.ONION_SEEDS.get(), 3);

        createCrop(BlockRegistry.REDBEAN_CROP.get(), ItemRegistry.RED_BEAN.get(), ItemRegistry.RED_BEAN.get(), 3);
        createCrop(BlockRegistry.SOYBEAN_CROP.get(), ItemRegistry.SOYBEAN.get(), ItemRegistry.SOYBEAN.get(), 3);

        createCrop(BlockRegistry.EGGPLANT_CROP.get(), FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT).get(),
                ItemRegistry.EGGPLANT_SEEDS.get(), 7);

        createCrop(BlockRegistry.TOMATO_CROP.get(), FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO).get(),
                ItemRegistry.TOMATO_SEEDS.get(), 7);

        createCrop(BlockRegistry.RICE_CROP.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW).get(),
                ItemRegistry.RICE_SEEDS.get(), 7);

        createCrop(BlockRegistry.RICE_CROP_ROOT.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW).get(),
                ItemRegistry.RICE_SEEDS.get(), 7);

        createCrop(BlockRegistry.RAPESEED_CROP.get(), ItemRegistry.RAPESEEDS.get(), ItemRegistry.RAPESEEDS.get(), 7);

        createCrop(BlockRegistry.TARO_CROP.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.IMOGARA).get(),
                ItemRegistry.TARO.get(), 3);

        createCrop(BlockRegistry.BUCKWHEAT_CROP.get(), ItemRegistry.BUCKWHEAT.get(), ItemRegistry.BUCKWHEAT.get(), 7);

        // New crop drops
        createCrop(BlockRegistry.PEPPER_CROP.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PEPPERCORN_GREEN).get(),
                ItemRegistry.PEPPER_SEEDS.get(), 7);

        createCrop(BlockRegistry.VANILLA_CROP.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.VANILLA).get(),
                ItemRegistry.VANILLA_SEEDS.get(), 7);

        createCrop(BlockRegistry.GRAPE_CROP.get(), FoodRegistry.FOODSET.get(SakuraFoodSet.GRAPE).get(),
                ItemRegistry.GRAPE_SEEDS.get(), 7);

        createCrop(BlockRegistry.HOPS_CROP.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.HOP).get(),
                ItemRegistry.HOP_SEEDS.get(), 7);

        createCrop(BlockRegistry.SEAWEED_CROP.get(), ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SEAWEED).get(),
                ItemRegistry.SEAWEED_SEEDS.get(), 7);
    }

    private void createTeishoku(Block block) {
        LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeishokuBlock.BITES, 0));
        this.add(block, createTeishokuDrops(block, BlockItemRegistry.OBON.get(), block.asItem(), builder));
    }

    private void createNabe(Block block) {
        LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(NabeBlock.BITES, 0));
        this.add(block, createTeishokuDrops(block, BlockItemRegistry.COOKING_POT.get(), block.asItem(), builder));
    }

    protected LootTable.Builder createTeishokuDrops(Block p_124143_, Item p_124144_, Item p_124145_,
                                                    LootItemCondition.Builder p_124146_) {
        return applyExplosionDecay(p_124143_, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(
                        LootItem.lootTableItem(p_124145_).when(p_124146_)))
                .withPool(
                        LootPool.lootPool().when(p_124146_.invert()).add(LootItem.lootTableItem(p_124144_))));
    }

    private void createCrop(Block block, Item crop, Item seeds, int age) {
        LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, age));
        this.add(block, createCropDrops(block, crop, seeds, builder));
    }
}
