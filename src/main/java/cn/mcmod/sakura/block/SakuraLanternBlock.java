package cn.mcmod.sakura.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Decorative lantern with a fixed VoxelShape matching its Blockbench model.
 * Unlike vanilla LanternBlock this has no HANGING state and is purely floor-placed,
 * mirroring how the 1.12.2 Sakura lanterns behaved.
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
