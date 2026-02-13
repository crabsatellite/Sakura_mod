package cn.mcmod.sakura.block.noodles;

import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraFoodSet;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockUdon extends BlockNoodle {

    public BlockUdon() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .strength(0.5F)
                .noOcclusion());
    }

    @Override
    public ItemStack getNoodle() {
        return new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.UDON).get(), 2);
    }

    @Override
    public ItemStack getUnfinishedItem() {
        // Return UDON_RAW material - represents the dough you'd place to start over
        return new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.UDON_RAW).get());
    }
}
