package cn.mcmod.sakura.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import cn.mcmod.sakura.client.model.SamuraiArmorModel;
import cn.mcmod.sakura.client.model.SoldierArmorModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Helper class for samurai/soldier armor rendering.
 * Similar to KimonoArmorRenderer but for samurai armor models.
 */
public class SamuraiArmorRenderer {

    private static final Map<String, HumanoidModel<?>> MODELS = new ConcurrentHashMap<>();

    /**
     * Gets or creates the samurai armor model.
     */
    @SuppressWarnings("unchecked")
    public static <A extends LivingEntity> HumanoidModel<A> getSamuraiModel() {
        return (HumanoidModel<A>) MODELS.computeIfAbsent("samurai", name -> {
            LayerDefinition layerDefinition = LayerDefinition.create(SamuraiArmorModel.createMesh(), 128, 64);
            SamuraiArmorModel<A> model = new SamuraiArmorModel<>(layerDefinition.bakeRoot());
            model.young = false;
            model.crouching = false;
            model.riding = false;
            return model;
        });
    }

    /**
     * Gets or creates the soldier armor model.
     */
    @SuppressWarnings("unchecked")
    public static <A extends LivingEntity> HumanoidModel<A> getSoldierModel() {
        return (HumanoidModel<A>) MODELS.computeIfAbsent("soldier", name -> {
            LayerDefinition layerDefinition = LayerDefinition.create(SoldierArmorModel.createMesh(), 128, 64);
            SoldierArmorModel<A> model = new SoldierArmorModel<>(layerDefinition.bakeRoot());
            model.young = false;
            model.crouching = false;
            model.riding = false;
            return model;
        });
    }

    /**
     * Gets the appropriate model based on armor type.
     */
    public static <A extends LivingEntity> HumanoidModel<A> getModel(boolean isSoldier) {
        return isSoldier ? getSoldierModel() : getSamuraiModel();
    }

    /**
     * Updates model visibility based on equipment slot.
     */
    public static void updateModelForSlot(HumanoidModel<?> model, EquipmentSlot slot) {
        if (model instanceof SamuraiArmorModel<?> samurai) {
            samurai.setVisibleForSlot(slot);
        } else if (model instanceof SoldierArmorModel<?> soldier) {
            soldier.setVisibleForSlot(slot);
        }
    }

    /**
     * Copies the pose from the default humanoid model to the armor model.
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

        target.young = source.young;
        target.crouching = source.crouching;
        target.riding = source.riding;
    }
}
