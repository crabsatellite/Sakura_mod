package cn.mcmod.sakura.block.noodles;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.material.MapColor;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;

public class BlockSoba extends BlockNoodle {
    public static final MapCodec<BlockSoba> CODEC = simpleCodec(p -> new BlockSoba());

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    public BlockSoba() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .strength(0.5F)
                .noOcclusion());
    }

    @Override
    public ItemStack getNoodle() {
        return new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.SOBA).get(), 2);
    }

    @Override
    public ItemStack getUnfinishedItem() {
        return new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOBA_RAW).get());
    }
}
