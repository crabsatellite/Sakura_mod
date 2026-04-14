package cn.mcmod.sakura.data.compat;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.FoodRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod_mmf.mmlib.data.compat.TFCFoodDefinitionProvider;

public class SakuraTFCFoodCompatProvider extends TFCFoodDefinitionProvider {

    public SakuraTFCFoodCompatProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, SakuraMod.MODID);
    }
    
    @Override
    public void addDatas() {
        FoodRegistry.ITEMS.getEntries().forEach( item -> {
            this.addData(item.get());
        });
        ItemRegistry.ITEMS.getEntries().forEach( item -> {
            this.addData(item.get());
        });
    }
    
    @Override
    public String getName() {
        return "Sakura TFC FoodDefinition Provider";
    }
}
