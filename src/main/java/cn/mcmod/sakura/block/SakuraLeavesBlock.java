package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import com.mojang.serialization.MapCodec;

import java.util.function.Supplier;

public class SakuraLeavesBlock extends LeavesBlock {
    public static final MapCodec<SakuraLeavesBlock> CODEC = simpleCodec(p -> new SakuraLeavesBlock(p, null));

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    private final Supplier<SimpleParticleType> leaf_particle;

    public SakuraLeavesBlock(Properties builder, Supplier<SimpleParticleType> particle) {
        super(builder);
        this.leaf_particle = particle;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(40) == 0) {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;

            double d0 = pos.getX() + 0.5D + 0.25D * j;
            double d1 = pos.getY() - 0.15D;
            double d2 = pos.getZ() + 0.5D + 0.25D * k;
            double d3 = rand.nextFloat() * j * 0.1D;
            double d4 = (rand.nextFloat() * 0.055D) + 0.015D;
            double d5 = rand.nextFloat() * k * 0.1D;
            worldIn.addParticle(this.leaf_particle.get(), d0, d1, d2, d3, -d4, d5);
        }
    }
}
