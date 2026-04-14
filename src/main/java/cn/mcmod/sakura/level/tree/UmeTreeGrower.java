package cn.mcmod.sakura.level.tree;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class UmeTreeGrower {
    public static final TreeGrower GROWER = new TreeGrower(
        "ume",
        Optional.of(SakuraTreeFeatures.FANCY_UME_KEY),
        Optional.empty(),
        Optional.empty()
    );

    private UmeTreeGrower() {}
}
