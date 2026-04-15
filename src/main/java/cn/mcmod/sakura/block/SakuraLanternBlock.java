package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Lantern block with a custom VoxelShape that matches the Blockbench model.
 * Vanilla LanternBlock hardcodes a tiny hanging-cage shape, which is visually
 * wrong for Sakura's larger stone/paper/bamboo lantern models.
 */
public class SakuraLanternBlock extends Block {
    private final VoxelShape shape;

    public SakuraLanternBlock(Properties properties, VoxelShape shape) {
        super(properties);
        this.shape = shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return shape;
    }
}
