package cn.mcmod.sakura.tags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import cn.mcmod_mmf.mmlib.utils.TagUtils;

public class SakuraBlockTags {
    public static final TagKey<Block> TRAY_HEAT_SOURCES = TagUtils.modBlockTag("mysterious_mountain_lib", "tray_heat_sources");
    public static final TagKey<Block> MINEABLE_WITH_KNIFE = TagUtils.modBlockTag("sakura", "mineable_with_knife");
    public static final TagKey<Block> MINEABLE_WITH_HAMMER = TagUtils.modBlockTag("sakura", "mineable_with_hammer");
}
