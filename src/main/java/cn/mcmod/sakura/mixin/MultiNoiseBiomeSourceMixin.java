package cn.mcmod.sakura.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import cn.mcmod.sakura.level.biome.BiomeNoiseCondition;
import cn.mcmod.sakura.level.biome.SakuraBiomeConfig;
import cn.mcmod.sakura.level.biome.SakuraBiomeRarity;
import cn.mcmod.sakura.level.biome.VoronoiGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = MultiNoiseBiomeSource.class, priority = -69420)
public class MultiNoiseBiomeSourceMixin implements MultiNoiseBiomeSourceAccessor {

    private long lastSampledWorldSeed;
    private ResourceKey<Level> lastSampledDimension;

    @Inject(at = @At("HEAD"),
            method = "Lnet/minecraft/world/level/biome/MultiNoiseBiomeSource;getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;",
            cancellable = true
    )
    private void sakura_getNoiseBiomeCoords(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        if (SakuraBiomeConfig.BIOMES.isEmpty()) {
            return;
        }
        VoronoiGenerator.VoronoiInfo voronoiInfo = SakuraBiomeRarity.getRareBiomeInfoForQuad(lastSampledWorldSeed, x, z);
        if (voronoiInfo != null) {
            int foundRarityOffset = SakuraBiomeRarity.getRareBiomeOffsetId(voronoiInfo);
            for (Map.Entry<ResourceKey<Biome>, BiomeNoiseCondition> condition : SakuraBiomeConfig.BIOMES.entrySet()) {
                if (foundRarityOffset == condition.getValue().getRarityOffset()
                        && condition.getValue().test(x, y, z, sampler, lastSampledDimension, voronoiInfo)) {
                    Holder<Biome> biomeHolder = ((BiomeSourceAccessor) this).getResourceKeyMap().get(condition.getKey());
                    if (biomeHolder != null) {
                        cir.setReturnValue(biomeHolder);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void setLastSampledSeed(long seed) {
        lastSampledWorldSeed = seed;
    }

    @Override
    public void setLastSampledDimension(ResourceKey<Level> dimension) {
        lastSampledDimension = dimension;
    }
}
