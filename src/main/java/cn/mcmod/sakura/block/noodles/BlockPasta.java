package cn.mcmod.sakura.block.noodles;

import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockPasta extends BlockNoodle {

    public BlockPasta() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .strength(0.5F)
                .noOcclusion());
    }

    @Override
    public ItemStack getNoodle() {
        return new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.PASTA_TOMATO).get(), 2);
    }

    @Override
    public ItemStack getUnfinishedItem() {
        return new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.PASTA_RAW).get());
    }
}
