package cn.mcmod.sakura.block.noodles;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.material.MapColor;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;

public class BlockPasta extends BlockNoodle {
    public static final MapCodec<BlockPasta> CODEC = simpleCodec(p -> new BlockPasta());

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

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
