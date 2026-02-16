package cn.mcmod.sakura.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/**
 * Soldier (足轻) armor model, ported from 1.12.2 ModelSamuraiArmors2.
 * 128x64 texture, state=2 (simplified version without ornaments).
 */
public class SoldierArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

    // Head parts (no ornaments in soldier variant)
    private final ModelPart helmet, helmetR, helmetL, helmetB;
    private final ModelPart capsthingy;

    // Body parts
    private final ModelPart chestplate, backplate;
    private final ModelPart beltR, beltL;
    private final ModelPart mbelt, mbeltL, mbeltR;

    // Right arm parts
    private final ModelPart shoulderR, gauntletR, gauntletstrapR;
    private final ModelPart shoulderplateRtop, shoulderplateR1;

    // Left arm parts
    private final ModelPart shoulderL, gauntletL, gauntletstrapL;
    private final ModelPart shoulderplateLtop, shoulderplateL1;

    // Right leg parts
    private final ModelPart legpanelR4, legpanelR5;
    private final ModelPart sidepanelR1, sidepanelR2;
    private final ModelPart backpanelR1, backpanelR2;

    // Left leg parts
    private final ModelPart legpanelL4, legpanelL5;
    private final ModelPart sidepanelL1, sidepanelL2;
    private final ModelPart backpanelL1, backpanelL2;

    // Feet parts
    private final ModelPart shoesR, shoesL;
    private final ModelPart shoesPartR, shoesPartL;

    public SoldierArmorModel(ModelPart root) {
        super(root);

        // Head children
        ModelPart headPart = root.getChild("head");
        this.helmet = headPart.getChild("helmet");
        this.helmetR = headPart.getChild("helmet_r");
        this.helmetL = headPart.getChild("helmet_l");
        this.helmetB = headPart.getChild("helmet_b");
        this.capsthingy = headPart.getChild("capsthingy");

        // Body children
        ModelPart bodyPart = root.getChild("body");
        this.chestplate = bodyPart.getChild("chestplate");
        this.backplate = bodyPart.getChild("backplate");
        this.beltR = bodyPart.getChild("belt_r");
        this.beltL = bodyPart.getChild("belt_l");
        this.mbelt = bodyPart.getChild("mbelt");
        this.mbeltL = bodyPart.getChild("mbelt_l");
        this.mbeltR = bodyPart.getChild("mbelt_r");

        // Right arm children
        ModelPart rightArmPart = root.getChild("right_arm");
        this.shoulderR = rightArmPart.getChild("shoulder_r");
        this.gauntletR = rightArmPart.getChild("gauntlet_r");
        this.gauntletstrapR = rightArmPart.getChild("gauntletstrap_r");
        this.shoulderplateRtop = rightArmPart.getChild("shoulderplate_r_top");
        this.shoulderplateR1 = rightArmPart.getChild("shoulderplate_r1");

        // Left arm children
        ModelPart leftArmPart = root.getChild("left_arm");
        this.shoulderL = leftArmPart.getChild("shoulder_l");
        this.gauntletL = leftArmPart.getChild("gauntlet_l");
        this.gauntletstrapL = leftArmPart.getChild("gauntletstrap_l");
        this.shoulderplateLtop = leftArmPart.getChild("shoulderplate_l_top");
        this.shoulderplateL1 = leftArmPart.getChild("shoulderplate_l1");

        // Right leg children
        ModelPart rightLegPart = root.getChild("right_leg");
        this.legpanelR4 = rightLegPart.getChild("legpanel_r4");
        this.legpanelR5 = rightLegPart.getChild("legpanel_r5");
        this.sidepanelR1 = rightLegPart.getChild("sidepanel_r1");
        this.sidepanelR2 = rightLegPart.getChild("sidepanel_r2");
        this.backpanelR1 = rightLegPart.getChild("backpanel_r1");
        this.backpanelR2 = rightLegPart.getChild("backpanel_r2");
        this.shoesR = rightLegPart.getChild("shoes_r");
        this.shoesPartR = rightLegPart.getChild("shoes_part_r");

        // Left leg children
        ModelPart leftLegPart = root.getChild("left_leg");
        this.legpanelL4 = leftLegPart.getChild("legpanel_l4");
        this.legpanelL5 = leftLegPart.getChild("legpanel_l5");
        this.sidepanelL1 = leftLegPart.getChild("sidepanel_l1");
        this.sidepanelL2 = leftLegPart.getChild("sidepanel_l2");
        this.backpanelL1 = leftLegPart.getChild("backpanel_l1");
        this.backpanelL2 = leftLegPart.getChild("backpanel_l2");
        this.shoesL = leftLegPart.getChild("shoes_l");
        this.shoesPartL = leftLegPart.getChild("shoes_part_l");
    }

    public static MeshDefinition createMesh() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // ===== HEAD =====
        PartDefinition headDef = root.addOrReplaceChild("head",
                CubeListBuilder.create(), PartPose.ZERO);

        // Helmet (simpler than samurai - no ornaments, different flap angles)
        headDef.addOrReplaceChild("helmet",
                CubeListBuilder.create()
                        .texOffs(41, 8).addBox(-4.5F, -9.0F, -4.5F, 9, 4, 9),
                PartPose.ZERO);

        headDef.addOrReplaceChild("helmet_r",
                CubeListBuilder.create()
                        .texOffs(21, 13).addBox(-5.5F, -5.0F, -4.5F, 1, 5, 9),
                PartPose.rotation(0.0F, 0.0F, 0.15235988F));

        headDef.addOrReplaceChild("helmet_l",
                CubeListBuilder.create().mirror()
                        .texOffs(21, 13).addBox(4.50F, -5.0F, -4.5F, 1, 5, 9),
                PartPose.rotation(0.0F, 0.0F, -0.15235988F));

        headDef.addOrReplaceChild("helmet_b",
                CubeListBuilder.create()
                        .texOffs(41, 21).addBox(-4.5F, -3.0F, 5.5F, 9, 5, 1),
                PartPose.rotation(0.5235988F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("capsthingy",
                CubeListBuilder.create()
                        .texOffs(21, 0).addBox(-4.5F, -6.0F, -6.5F, 9, 1, 2),
                PartPose.ZERO);

        // ===== HAT (empty) =====
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        // ===== BODY =====
        PartDefinition bodyDef = root.addOrReplaceChild("body",
                CubeListBuilder.create(), PartPose.ZERO);

        // Outer layer parts
        bodyDef.addOrReplaceChild("chestplate",
                CubeListBuilder.create()
                        .texOffs(56, 45).addBox(-4.0F, 1.0F, -4.0F, 8, 7, 2),
                PartPose.ZERO);

        bodyDef.addOrReplaceChild("backplate",
                CubeListBuilder.create()
                        .texOffs(36, 45).addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2),
                PartPose.ZERO);

        bodyDef.addOrReplaceChild("belt_r",
                CubeListBuilder.create()
                        .texOffs(76, 44).addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6),
                PartPose.ZERO);

        bodyDef.addOrReplaceChild("belt_l",
                CubeListBuilder.create()
                        .texOffs(76, 44).addBox(4.0F, 4.0F, -3.0F, 1, 3, 6),
                PartPose.ZERO);

        // Inner layer parts (mid-belt)
        bodyDef.addOrReplaceChild("mbelt",
                CubeListBuilder.create()
                        .texOffs(56, 55).addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1),
                PartPose.ZERO);

        bodyDef.addOrReplaceChild("mbelt_l",
                CubeListBuilder.create()
                        .texOffs(76, 44).addBox(4.0F, 8.0F, -3.0F, 1, 3, 6),
                PartPose.ZERO);

        bodyDef.addOrReplaceChild("mbelt_r",
                CubeListBuilder.create()
                        .texOffs(76, 44).addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6),
                PartPose.ZERO);

        // ===== RIGHT ARM =====
        PartDefinition rightArmDef = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create(), PartPose.ZERO);

        rightArmDef.addOrReplaceChild("shoulder_r",
                CubeListBuilder.create()
                        .texOffs(56, 35).addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5),
                PartPose.ZERO);

        rightArmDef.addOrReplaceChild("gauntlet_r",
                CubeListBuilder.create()
                        .texOffs(100, 26).addBox(-3.5F, 4F, -2.5F, 2, 5, 5),
                PartPose.ZERO);

        rightArmDef.addOrReplaceChild("gauntletstrap_r",
                CubeListBuilder.create()
                        .texOffs(84, 31).addBox(-1.5F, 2.5F, -2.5F, 3, 7, 5),
                PartPose.ZERO);

        rightArmDef.addOrReplaceChild("shoulderplate_r_top",
                CubeListBuilder.create()
                        .texOffs(110, 37).addBox(-5.5F, -2.5F, -3.5F, 2, 1, 7),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightArmDef.addOrReplaceChild("shoulderplate_r1",
                CubeListBuilder.create()
                        .texOffs(110, 45).addBox(-4.5F, -1.5F, -3.5F, 1, 4, 7),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        // ===== LEFT ARM =====
        PartDefinition leftArmDef = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create(), PartPose.ZERO);

        leftArmDef.addOrReplaceChild("shoulder_l",
                CubeListBuilder.create().mirror()
                        .texOffs(56, 35).addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5),
                PartPose.ZERO);

        leftArmDef.addOrReplaceChild("gauntlet_l",
                CubeListBuilder.create()
                        .texOffs(114, 26).addBox(1.5F, 4F, -2.5F, 2, 5, 5),
                PartPose.ZERO);

        leftArmDef.addOrReplaceChild("gauntletstrap_l",
                CubeListBuilder.create().mirror()
                        .texOffs(84, 31).addBox(-1.5F, 2.5F, -2.5F, 3, 7, 5),
                PartPose.ZERO);

        leftArmDef.addOrReplaceChild("shoulderplate_l_top",
                CubeListBuilder.create().mirror()
                        .texOffs(110, 37).addBox(3.5F, -2.5F, -3.5F, 2, 1, 7),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftArmDef.addOrReplaceChild("shoulderplate_l1",
                CubeListBuilder.create().mirror()
                        .texOffs(110, 45).addBox(3.5F, -1.5F, -3.5F, 1, 4, 7),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        // ===== RIGHT LEG =====
        PartDefinition rightLegDef = root.addOrReplaceChild("right_leg",
                CubeListBuilder.create(), PartPose.ZERO);

        rightLegDef.addOrReplaceChild("legpanel_r4",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 0.5F, -3.5F, 5, 3, 1),
                PartPose.rotation(-0.4363323F, 0.0F, 0.0F));

        rightLegDef.addOrReplaceChild("legpanel_r5",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 2.5F, -2.5F, 5, 3, 1),
                PartPose.rotation(-0.4363323F, 0.0F, 0.0F));

        rightLegDef.addOrReplaceChild("sidepanel_r1",
                CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-2.5F, 0.5F, -2.5F, 1, 4, 5),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightLegDef.addOrReplaceChild("sidepanel_r2",
                CubeListBuilder.create()
                        .texOffs(0, 31).addBox(-1.5F, 3.5F, -2.5F, 1, 3, 5),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightLegDef.addOrReplaceChild("backpanel_r1",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 0.5F, 2.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        rightLegDef.addOrReplaceChild("backpanel_r2",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 2.5F, 1.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        // Shoes on right leg
        rightLegDef.addOrReplaceChild("shoes_r",
                CubeListBuilder.create()
                        .texOffs(84, 4).addBox(-2.0F, 5F, -2.0F, 4, 4, 4, new CubeDeformation(0.5005f)),
                PartPose.ZERO);

        rightLegDef.addOrReplaceChild("shoes_part_r",
                CubeListBuilder.create()
                        .texOffs(100, 4).addBox(-2.5F, 10.5F, -3.6F, 5, 2, 6),
                PartPose.ZERO);

        // ===== LEFT LEG =====
        PartDefinition leftLegDef = root.addOrReplaceChild("left_leg",
                CubeListBuilder.create(), PartPose.ZERO);

        leftLegDef.addOrReplaceChild("legpanel_l4",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2F, 0.5F, -3.5F, 5, 3, 1),
                PartPose.rotation(-0.4363323F, 0.0F, 0.0F));

        leftLegDef.addOrReplaceChild("legpanel_l5",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2.0F, 2.5F, -2.5F, 5, 3, 1),
                PartPose.rotation(-0.4363323F, 0.0F, 0.0F));

        leftLegDef.addOrReplaceChild("sidepanel_l1",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 22).addBox(1.5F, 0.5F, -2.5F, 1, 4, 5),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftLegDef.addOrReplaceChild("sidepanel_l2",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 31).addBox(0.5F, 3.5F, -2.5F, 1, 3, 5),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftLegDef.addOrReplaceChild("backpanel_l1",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2.0F, 0.5F, 2.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        leftLegDef.addOrReplaceChild("backpanel_l2",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2.0F, 2.5F, 1.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        // Shoes on left leg
        leftLegDef.addOrReplaceChild("shoes_l",
                CubeListBuilder.create()
                        .texOffs(84, 4).addBox(-2.0F, 5F, -2.0F, 4, 4, 4, new CubeDeformation(0.5005f)),
                PartPose.ZERO);

        leftLegDef.addOrReplaceChild("shoes_part_l",
                CubeListBuilder.create()
                        .texOffs(100, 4).addBox(-2.5F, 10.5F, -3.6F, 5, 2, 6),
                PartPose.ZERO);

        return mesh;
    }

    /**
     * Sets part visibility based on the equipped armor slot.
     * Matches the render() logic from 1.12.2 ModelSamuraiArmors2 with state=2.
     */
    public void setVisibleForSlot(EquipmentSlot slot) {
        setAllVisible(false);

        switch (slot) {
            case HEAD:
                this.head.visible = true;
                this.helmet.visible = true;
                this.helmetR.visible = true;
                this.helmetL.visible = true;
                this.helmetB.visible = true;
                this.capsthingy.visible = true;
                break;
            case CHEST:
                this.body.visible = true;
                this.rightArm.visible = true;
                this.leftArm.visible = true;
                // Outer body: chestplate + backplate
                this.chestplate.visible = true;
                this.backplate.visible = true;
                this.beltR.visible = false;
                this.beltL.visible = false;
                this.mbelt.visible = false;
                this.mbeltL.visible = false;
                this.mbeltR.visible = false;
                // Arm parts (state=2: shoulder, gauntlet, shoulderplate top/1)
                this.shoulderR.visible = true;
                this.shoulderL.visible = true;
                this.gauntletR.visible = true;
                this.gauntletL.visible = true;
                this.gauntletstrapR.visible = true;
                this.gauntletstrapL.visible = true;
                this.shoulderplateRtop.visible = true;
                this.shoulderplateR1.visible = true;
                this.shoulderplateLtop.visible = true;
                this.shoulderplateL1.visible = true;
                break;
            case LEGS:
                this.body.visible = true;
                this.rightLeg.visible = true;
                this.leftLeg.visible = true;
                // Inner body: mbelt
                this.chestplate.visible = false;
                this.backplate.visible = false;
                this.beltR.visible = false;
                this.beltL.visible = false;
                this.mbelt.visible = true;
                this.mbeltL.visible = true;
                this.mbeltR.visible = true;
                // Leg panels (state=2: level 1+2 panels)
                this.legpanelR4.visible = true;
                this.legpanelR5.visible = true;
                this.legpanelL4.visible = true;
                this.legpanelL5.visible = true;
                this.sidepanelR1.visible = true;
                this.sidepanelR2.visible = true;
                this.sidepanelL1.visible = true;
                this.sidepanelL2.visible = true;
                this.backpanelR1.visible = true;
                this.backpanelR2.visible = true;
                this.backpanelL1.visible = true;
                this.backpanelL2.visible = true;
                // Shoes hidden for legs slot
                this.shoesR.visible = false;
                this.shoesL.visible = false;
                this.shoesPartR.visible = false;
                this.shoesPartL.visible = false;
                break;
            case FEET:
                this.rightLeg.visible = true;
                this.leftLeg.visible = true;
                // Only shoes visible
                this.shoesR.visible = true;
                this.shoesL.visible = true;
                this.shoesPartR.visible = true;
                this.shoesPartL.visible = true;
                break;
            default:
                break;
        }
    }
}
