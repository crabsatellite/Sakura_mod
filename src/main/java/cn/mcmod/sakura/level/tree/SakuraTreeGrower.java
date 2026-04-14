package cn.mcmod.sakura.level.tree;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class SakuraTreeGrower {
    public static final TreeGrower GROWER = new TreeGrower(
        "sakura", 0.1f,
        Optional.of(SakuraTreeFeatures.BIG_SAKURA_KEY), Optional.empty(),
        Optional.of(SakuraTreeFeatures.SAKURA_KEY), Optional.of(SakuraTreeFeatures.FANCY_SAKURA_KEY),
        Optional.empty(), Optional.empty()
    );

    private SakuraTreeGrower() {}
}
