package cn.mcmod.sakura.item;

import net.minecraft.world.entity.EquipmentSlot;

public final class KimonoRenderLayerPolicy {
    private KimonoRenderLayerPolicy() {}

    public static boolean showsBody(EquipmentSlot slot) {
        return slot == EquipmentSlot.CHEST;
    }

    public static boolean showsArms(EquipmentSlot slot) {
        return slot == EquipmentSlot.CHEST;
    }

    public static boolean showsLegs(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}
