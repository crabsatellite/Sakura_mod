package cn.mcmod.sakura.item;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;

public class RiceSeedsItem extends ItemNameBlockItem {

    public RiceSeedsItem() {
        super(BlockRegistry.RICE_CROP_ROOT.get(), SakuraMod.defaultItemProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = this.place(new BlockPlaceContext(context));
        if (result.equals(InteractionResult.FAIL)) {
            Player player = context.getPlayer();
            BlockState targetState = context.getLevel().getBlockState(context.getClickedPos());
            if (player != null && context.getClickedFace().equals(Direction.UP)
                    && (targetState.is(BlockTags.DIRT) || targetState.getBlock() instanceof FarmBlock)) {
                player.displayClientMessage(
                        Component.translatable(SakuraMod.MODID + "." + "block.rice.invalid_placement"), true);
            }
        }
        // isEdible() removed in 1.21; rice seeds are not edible, so just return the placement result
        return result;
    }
}
