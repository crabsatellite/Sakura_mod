package cn.mcmod.sakura.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import com.mojang.serialization.MapCodec;

public class MapleTreeLogBlock extends RotatedPillarBlock {
    public static final MapCodec<MapleTreeLogBlock> CODEC = simpleCodec(p -> new MapleTreeLogBlock());

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }


    public MapleTreeLogBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(
                state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD
                        : MapColor.PODZOL))
                .strength(2.0F).sound(SoundType.WOOD));
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction,
            boolean simulate) {
        if (context.getItemInHand().canPerformAction(ItemAbilities.SHEARS_CARVE)) {
            return BlockRegistry.MAPLE_SAP_LOG.get().withPropertiesOf(state).setValue(MapleTreeSapLogBlock.EXHAUSTION,
                    0);
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
