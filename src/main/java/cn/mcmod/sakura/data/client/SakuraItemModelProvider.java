package cn.mcmod.sakura.data.client;

import cn.mcmod.sakura.block.BlockItemRegistry;
import cn.mcmod.sakura.block.machines.StoneMortarBlock;
import cn.mcmod.sakura.fluid.BucketItemRegistry;
import cn.mcmod.sakura.item.DrinkRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SakuraItemModelProvider extends AbstractItemModelProvider {

    public SakuraItemModelProvider(PackOutput packOutput, String modid, ExistingFileHelper existingFileHelper) {
        super(packOutput, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // ==============================
        // Block Items (BlockItemRegistry)
        // ==============================
        BlockItemRegistry.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) item.get();
                // Skip blocks with hand-written Blockbench item models or special models
                if (blockItem.getBlock() instanceof StoneMortarBlock)
                    return;
                if (blockItem.getBlock() instanceof FenceBlock)
                    return; // bamboo fences have hand-written Blockbench item models
                if (blockItem.getBlock() instanceof DoorBlock)
                    return; // bamboo door has hand-written item/generated model
                if (blockItem.getBlock() instanceof BushBlock)
                    bushItem(item);
                else
                    itemBlock(blockItem::getBlock);
            } else {
                normalItem(item);
            }
        });

        // ==============================
        // Bucket Items (BucketItemRegistry)
        // ==============================
        BucketItemRegistry.ITEMS.getEntries().forEach(item -> {
            normalItem(item);
        });

        // ==============================
        // Regular Items (ItemRegistry)
        // All items here use normalItem() which generates:
        //   {"parent": "minecraft:item/generated", "textures": {"layer0": "sakura:item/<name>"}}
        // Most items already have hand-written Blockbench models in src/main/resources
        // which will take priority over these generated ones.
        // ==============================
        ItemRegistry.ITEMS.getEntries().forEach(item -> {
            normalItem(item);
        });

        // ==============================
        // Drink Items (DrinkRegistry)
        // Teas use hand-written cup_drink parent models.
        // Alcohols use hand-written Blockbench glass models.
        // Cocktails with models use hand-written Blockbench glass models.
        // Missing cocktails get a basic generated model as a fallback.
        // All hand-written models in src/main/resources override these generated ones.
        // ==============================
        DrinkRegistry.ITEMS.getEntries().forEach(item -> {
            normalItem(item);
        });
    }
}
