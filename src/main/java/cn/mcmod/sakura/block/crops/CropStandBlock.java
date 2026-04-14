package cn.mcmod.sakura.block.crops;

import net.minecraft.world.level.block.Block;
import com.mojang.serialization.MapCodec;

public class CropStandBlock extends Block {
    public static final MapCodec<CropStandBlock> CODEC = simpleCodec(CropStandBlock::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec codec() {
        return CODEC;
    }


    public CropStandBlock(Properties property) {
        super(property);
    }

}
