package cn.mcmod.sakura.compat.jei;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class SakuraJeiInfo {
    public static final ResourceLocation PLUGIN_ID = ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "jei_plugin");
    public static final String TATARA_INFO_KEY = "jei.sakura.tatara.description";

    private SakuraJeiInfo() {}

    public static ItemStack tataraInfoStack() {
        return new ItemStack(BlockRegistry.TATARA.get());
    }
}
