package cn.mcmod.sakura.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import cn.mcmod.sakura.client.model.KimonoModel;
import cn.mcmod.sakura.item.KimonoItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Helper class for kimono armor rendering.
 * The actual rendering is handled by the IClientItemExtensions in KimonoItem.
 */
public class KimonoArmorRenderer {

    private static final Map<String, HumanoidModel<?>> MODELS = new ConcurrentHashMap<>();

    /**
     * Gets or creates the model for a specific texture.
     */
    @SuppressWarnings("unchecked")
    public static <A extends LivingEntity> HumanoidModel<A> getModel(String textureName) {
        return (HumanoidModel<A>) MODELS.computeIfAbsent(textureName, name -> {
            LayerDefinition layerDefinition = LayerDefinition.create(KimonoModel.createMesh(), 64, 64);
            HumanoidModel<A> model = new KimonoModel<>(layerDefinition.bakeRoot());
            model.young = false;
            model.crouching = false;
            model.riding = false;
            return model;
        });
    }

    /**
     * Updates model visibility based on equipment slot.
     */
    public static void updateModelForSlot(HumanoidModel<?> model, EquipmentSlot slot) {
        if (model instanceof KimonoModel<?> kimonoSpecific) {
            kimonoSpecific.setVisibleForSlot(slot);
        } else {
            model.setAllVisible(false);
            if (slot == EquipmentSlot.CHEST) {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            } else if (slot == EquipmentSlot.LEGS) {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
        }
    }

    /**
     * Copies the pose from the default humanoid model to the kimono model.
     * This ensures animations like walking, sneaking, and item holding work correctly.
     */
    public static void copyModelPose(HumanoidModel<?> source, HumanoidModel<?> target) {
        target.head.xRot = source.head.xRot;
        target.head.yRot = source.head.yRot;
        target.head.zRot = source.head.zRot;

        target.body.xRot = source.body.xRot;
        target.body.yRot = source.body.yRot;
        target.body.zRot = source.body.zRot;

        target.leftArm.xRot = source.leftArm.xRot;
        target.leftArm.yRot = source.leftArm.yRot;
        target.leftArm.zRot = source.leftArm.zRot;

        target.rightArm.xRot = source.rightArm.xRot;
        target.rightArm.yRot = source.rightArm.yRot;
        target.rightArm.zRot = source.rightArm.zRot;

        target.leftLeg.xRot = source.leftLeg.xRot;
        target.leftLeg.yRot = source.leftLeg.yRot;
        target.leftLeg.zRot = source.leftLeg.zRot;

        target.rightLeg.xRot = source.rightLeg.xRot;
        target.rightLeg.yRot = source.rightLeg.yRot;
        target.rightLeg.zRot = source.rightLeg.zRot;

        // Copy entity state flags
        target.young = source.young;
        target.crouching = source.crouching;
        target.riding = source.riding;
    }
}
