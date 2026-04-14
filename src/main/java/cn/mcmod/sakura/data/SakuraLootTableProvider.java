package cn.mcmod.sakura.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import cn.mcmod.sakura.data.loot.SakuraBlockLoot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;

public class SakuraLootTableProvider extends LootTableProvider {

    public SakuraLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Set.of()
                ,List.of(new SubProviderEntry(
                SakuraBlockLoot::new,
                LootContextParamSets.BLOCK)),
                lookupProvider
        );
    }
    
//    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(SakuraBlockLoot::new, LootContextParamSets.BLOCK));

//    @Override
//    public String getName() {
//        return "Sakura's Loot Tables";
//    }
//
//    @Override
//    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
//        return tables;
//    }

}
