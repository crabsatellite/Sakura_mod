package cn.mcmod.sakura.data.client;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

// BlockState datagen is not used — blockstate/model JSONs are hand-written in src/main/resources.
// This stub exists so DataGen.java can reference the class if re-enabled later.
public class SakuraBlockStateProvider extends BlockStateProvider {

    public SakuraBlockStateProvider(PackOutput packOutput, String modid, ExistingFileHelper exFileHelper) {
        super(packOutput, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }
}
