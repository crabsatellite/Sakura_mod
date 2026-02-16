package cn.mcmod.sakura.block.crops;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A crop block that can survive on natural ground (dirt, grass, etc.)
 * in addition to farmland. Used for wild-generated crops that grow
 * naturally in the world, matching the 1.12.2 behavior where crops
 * were placed directly on dirt by world generation.
 */
public class WildCropBlock extends CropBlock {
    private final Supplier<? extends ItemLike> seedItem;

    public WildCropBlock(Properties properties, Supplier<? extends ItemLike> seed) {
        super(properties);
        this.seedItem = seed;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.seedItem.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.FARMLAND
                || block == Blocks.DIRT || block == Blocks.GRASS_BLOCK
                || block == Blocks.COARSE_DIRT || block == Blocks.ROOTED_DIRT
                || block == Blocks.PODZOL || block == Blocks.MUD;
    }
}
