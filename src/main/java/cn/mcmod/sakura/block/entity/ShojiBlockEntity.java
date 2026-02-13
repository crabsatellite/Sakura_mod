package cn.mcmod.sakura.block.entity;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * BlockEntity for the Shoji (Japanese sliding door) block.
 * Ported from 1.12.2 TileEntityShoji.
 * Stores the shoji type variant (texture) and animation state.
 * The facing and open/closed state are stored in BlockState properties,
 * while type (texture variant) and animation are stored here in the block entity.
 */
public class ShojiBlockEntity extends SyncedBlockEntity {

    private int type = 0;
    private int animation = 0;

    public ShojiBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SHOJI.get(), pos, state);
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, ShojiBlockEntity blockEntity) {
        if (blockEntity.animation > 0) {
            blockEntity.animation -= 1;
        }
    }

    public int getShojiType() {
        return type;
    }

    public void setShojiType(int type) {
        this.type = type;
        inventoryChanged();
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("type")) {
            type = compound.getInt("type");
        }
        if (compound.contains("animation")) {
            animation = compound.getInt("animation");
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("type", type);
        compound.putInt("animation", animation);
    }
}
