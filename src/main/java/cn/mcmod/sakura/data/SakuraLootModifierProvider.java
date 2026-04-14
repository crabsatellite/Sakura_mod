package cn.mcmod.sakura.data;

import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class SakuraLootModifierProvider extends GlobalLootModifierProvider {
    public SakuraLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modid) {
        super(output, lookupProvider, modid);
    }

    @Override
    protected void start() {
//        add(
//                "grass_drops",
//                new SeedsDrop.SeedDropModifier(new LootItemCondition[]{
//                        LootItemRandomChanceCondition.randomChance(0.0625F).build(),
//                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).build(),
//                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build()
//                })
//        );
    }
}
