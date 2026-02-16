package cn.mcmod.sakura.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/**
 * Full samurai armor model, ported from 1.12.2 ModelSamuraiArmors.
 * 128x64 texture, state=3 (all parts visible).
 */
public class SamuraiArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

    // Head parts
    private final ModelPart ornamentL, ornamentL2, ornamentL3;
    private final ModelPart ornamentR, ornamentR2, ornamentR3;
    private final ModelPart helmet, helmetR, helmetL, helmetB;
    private final ModelPart capsthingy;
    private final ModelPart flapR, flapL;
    private final ModelPart gemornament, gemornament2, gemornament3, gem;

    // Body parts
    private final ModelPart chestplate, backplate;
    private final ModelPart beltR, beltL;
    private final ModelPart mbelt, mbeltL, mbeltR;

    // Right arm parts
    private final ModelPart shoulderR, gauntletR, gauntletstrapR;
    private final ModelPart shoulderplateRtop, shoulderplateR1, shoulderplateR2, shoulderplateR3;

    // Left arm parts
    private final ModelPart shoulderL, gauntletL, gauntletstrapL;
    private final ModelPart shoulderplateLtop, shoulderplateL1, shoulderplateL2, shoulderplateL3;

    // Right leg parts
    private final ModelPart legpanelR4, legpanelR5, legpanelR6;
    private final ModelPart sidepanelR1, sidepanelR2, sidepanelR3;
    private final ModelPart backpanelR1, backpanelR2, backpanelR3;

    // Left leg parts
    private final ModelPart legpanelL4, legpanelL5, legpanelL6;
    private final ModelPart sidepanelL1, sidepanelL2, sidepanelL3;
    private final ModelPart backpanelL1, backpanelL2, backpanelL3;

    // Feet parts
    private final ModelPart shoesR, shoesL;
    private final ModelPart shoesPartR, shoesPartL;

    public SamuraiArmorModel(ModelPart root) {
        super(root);

        // Head children
        ModelPart headPart = root.getChild("head");
        this.ornamentL = headPart.getChild("ornament_l");
        this.ornamentL2 = headPart.getChild("ornament_l2");
        this.ornamentL3 = headPart.getChild("ornament_l3");
        this.ornamentR = headPart.getChild("ornament_r");
        this.ornamentR2 = headPart.getChild("ornament_r2");
        this.ornamentR3 = headPart.getChild("ornament_r3");
        this.helmet = headPart.getChild("helmet");
        this.helmetR = headPart.getChild("helmet_r");
        this.helmetL = headPart.getChild("helmet_l");
        this.helmetB = headPart.getChild("helmet_b");
        this.capsthingy = headPart.getChild("capsthingy");
        this.flapR = headPart.getChild("flap_r");
        this.flapL = headPart.getChild("flap_l");
        this.gemornament = headPart.getChild("gemornament");
        this.gemornament2 = headPart.getChild("gemornament2");
        this.gemornament3 = headPart.getChild("gemornament3");
        this.gem = headPart.getChild("gem");

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
        this.shoulderplateR2 = rightArmPart.getChild("shoulderplate_r2");
        this.shoulderplateR3 = rightArmPart.getChild("shoulderplate_r3");

        // Left arm children
        ModelPart leftArmPart = root.getChild("left_arm");
        this.shoulderL = leftArmPart.getChild("shoulder_l");
        this.gauntletL = leftArmPart.getChild("gauntlet_l");
        this.gauntletstrapL = leftArmPart.getChild("gauntletstrap_l");
        this.shoulderplateLtop = leftArmPart.getChild("shoulderplate_l_top");
        this.shoulderplateL1 = leftArmPart.getChild("shoulderplate_l1");
        this.shoulderplateL2 = leftArmPart.getChild("shoulderplate_l2");
        this.shoulderplateL3 = leftArmPart.getChild("shoulderplate_l3");

        // Right leg children
        ModelPart rightLegPart = root.getChild("right_leg");
        this.legpanelR4 = rightLegPart.getChild("legpanel_r4");
        this.legpanelR5 = rightLegPart.getChild("legpanel_r5");
        this.legpanelR6 = rightLegPart.getChild("legpanel_r6");
        this.sidepanelR1 = rightLegPart.getChild("sidepanel_r1");
        this.sidepanelR2 = rightLegPart.getChild("sidepanel_r2");
        this.sidepanelR3 = rightLegPart.getChild("sidepanel_r3");
        this.backpanelR1 = rightLegPart.getChild("backpanel_r1");
        this.backpanelR2 = rightLegPart.getChild("backpanel_r2");
        this.backpanelR3 = rightLegPart.getChild("backpanel_r3");
        this.shoesR = rightLegPart.getChild("shoes_r");
        this.shoesPartR = rightLegPart.getChild("shoes_part_r");

        // Left leg children
        ModelPart leftLegPart = root.getChild("left_leg");
        this.legpanelL4 = leftLegPart.getChild("legpanel_l4");
        this.legpanelL5 = leftLegPart.getChild("legpanel_l5");
        this.legpanelL6 = leftLegPart.getChild("legpanel_l6");
        this.sidepanelL1 = leftLegPart.getChild("sidepanel_l1");
        this.sidepanelL2 = leftLegPart.getChild("sidepanel_l2");
        this.sidepanelL3 = leftLegPart.getChild("sidepanel_l3");
        this.backpanelL1 = leftLegPart.getChild("backpanel_l1");
        this.backpanelL2 = leftLegPart.getChild("backpanel_l2");
        this.backpanelL3 = leftLegPart.getChild("backpanel_l3");
        this.shoesL = leftLegPart.getChild("shoes_l");
        this.shoesPartL = leftLegPart.getChild("shoes_part_l");
    }

    public static MeshDefinition createMesh() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // ===== HEAD =====
        PartDefinition headDef = root.addOrReplaceChild("head",
                CubeListBuilder.create(), PartPose.ZERO);

        // Ornaments (left side, mirrored)
        headDef.addOrReplaceChild("ornament_l",
                CubeListBuilder.create().mirror()
                        .texOffs(78, 8).addBox(1.5F, -8.5F, -6.5F, 2, 2, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("ornament_l2",
                CubeListBuilder.create().mirror()
                        .texOffs(78, 8).addBox(3.5F, -9.5F, -6.5F, 1, 2, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("ornament_l3",
                CubeListBuilder.create().mirror()
                        .texOffs(78, 8).addBox(4.5F, -11.5F, -6.5F, 1, 3, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        // Ornaments (right side)
        headDef.addOrReplaceChild("ornament_r",
                CubeListBuilder.create()
                        .texOffs(78, 8).addBox(-3.5F, -8.5F, -6.5F, 2, 2, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("ornament_r2",
                CubeListBuilder.create()
                        .texOffs(78, 8).addBox(-4.5F, -9.5F, -6.5F, 1, 2, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("ornament_r3",
                CubeListBuilder.create()
                        .texOffs(78, 8).addBox(-5.5F, -11.5F, -6.5F, 1, 3, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        // Helmet
        headDef.addOrReplaceChild("helmet",
                CubeListBuilder.create()
                        .texOffs(41, 8).addBox(-4.5F, -9.0F, -4.5F, 9, 4, 9),
                PartPose.ZERO);

        headDef.addOrReplaceChild("helmet_r",
                CubeListBuilder.create()
                        .texOffs(21, 13).addBox(-6.5F, -3.0F, -4.5F, 1, 5, 9),
                PartPose.rotation(0.0F, 0.0F, 0.5235988F));

        headDef.addOrReplaceChild("helmet_l",
                CubeListBuilder.create().mirror()
                        .texOffs(21, 13).addBox(5.5F, -3.0F, -4.5F, 1, 5, 9),
                PartPose.rotation(0.0F, 0.0F, -0.5235988F));

        headDef.addOrReplaceChild("helmet_b",
                CubeListBuilder.create()
                        .texOffs(41, 21).addBox(-4.5F, -3.0F, 5.5F, 9, 5, 1),
                PartPose.rotation(0.5235988F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("capsthingy",
                CubeListBuilder.create()
                        .texOffs(21, 0).addBox(-4.5F, -6.0F, -6.5F, 9, 1, 2),
                PartPose.ZERO);

        // Flaps
        headDef.addOrReplaceChild("flap_r",
                CubeListBuilder.create()
                        .texOffs(59, 10).addBox(-10.0F, -2.0F, -1.0F, 3, 3, 1),
                PartPose.rotation(0.0F, -0.5235988F, 0.5235988F));

        headDef.addOrReplaceChild("flap_l",
                CubeListBuilder.create().mirror()
                        .texOffs(59, 10).addBox(7.0F, -2.0F, -1.0F, 3, 3, 1),
                PartPose.rotation(0.0F, 0.5235988F, -0.5235988F));

        // Gem ornaments
        headDef.addOrReplaceChild("gemornament",
                CubeListBuilder.create()
                        .texOffs(68, 11).addBox(-1.5F, -9.0F, -7.0F, 3, 3, 2),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("gemornament2",
                CubeListBuilder.create()
                        .texOffs(78, 8).addBox(-1F, -10.0F, -7F, 2, 1, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("gemornament3",
                CubeListBuilder.create()
                        .texOffs(78, 8).addBox(-0.5F, -13.0F, -7F, 1, 3, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        headDef.addOrReplaceChild("gem",
                CubeListBuilder.create()
                        .texOffs(72, 8).addBox(-0.5F, -8F, -7.5F, 1, 1, 1),
                PartPose.rotation(-0.1396263F, 0.0F, 0.0F));

        // ===== HAT (empty) =====
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        // ===== BODY =====
        PartDefinition bodyDef = root.addOrReplaceChild("body",
                CubeListBuilder.create(), PartPose.ZERO);

        // Outer layer parts (chestplate, backplate, belts)
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

        rightArmDef.addOrReplaceChild("shoulderplate_r2",
                CubeListBuilder.create()
                        .texOffs(94, 45).addBox(-3.5F, 1.5F, -3.5F, 1, 3, 7),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightArmDef.addOrReplaceChild("shoulderplate_r3",
                CubeListBuilder.create()
                        .texOffs(94, 45).addBox(-2.5F, 3.5F, -3.5F, 1, 3, 7),
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

        leftArmDef.addOrReplaceChild("shoulderplate_l2",
                CubeListBuilder.create().mirror()
                        .texOffs(94, 45).addBox(2.5F, 1.5F, -3.5F, 1, 3, 7),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftArmDef.addOrReplaceChild("shoulderplate_l3",
                CubeListBuilder.create().mirror()
                        .texOffs(94, 45).addBox(1.5F, 3.5F, -3.5F, 1, 3, 7),
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

        rightLegDef.addOrReplaceChild("legpanel_r6",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 4.5F, -1.5F, 5, 3, 1),
                PartPose.rotation(-0.4363323F, 0.0F, 0.0F));

        rightLegDef.addOrReplaceChild("sidepanel_r1",
                CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-2.5F, 0.5F, -2.5F, 1, 4, 5),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightLegDef.addOrReplaceChild("sidepanel_r2",
                CubeListBuilder.create()
                        .texOffs(0, 31).addBox(-1.5F, 3.5F, -2.5F, 1, 3, 5),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightLegDef.addOrReplaceChild("sidepanel_r3",
                CubeListBuilder.create()
                        .texOffs(12, 31).addBox(-0.5F, 5.5F, -2.5F, 1, 3, 5),
                PartPose.rotation(0.0F, 0.0F, 0.4363323F));

        rightLegDef.addOrReplaceChild("backpanel_r1",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 0.5F, 2.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        rightLegDef.addOrReplaceChild("backpanel_r2",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 2.5F, 1.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        rightLegDef.addOrReplaceChild("backpanel_r3",
                CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-3.0F, 4.5F, 0.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        // Shoes on right leg
        rightLegDef.addOrReplaceChild("shoes_r",
                CubeListBuilder.create()
                        .texOffs(84, 4).addBox(-2.0F, 5F, -2.0F, 4, 4, 4, new CubeDeformation(1.0005f)),
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

        leftLegDef.addOrReplaceChild("legpanel_l6",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2F, 4.5F, -1.5F, 5, 3, 1),
                PartPose.rotation(-0.4363323F, 0.0F, 0.0F));

        leftLegDef.addOrReplaceChild("sidepanel_l1",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 22).addBox(1.5F, 0.5F, -2.5F, 1, 4, 5),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftLegDef.addOrReplaceChild("sidepanel_l2",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 31).addBox(0.5F, 3.5F, -2.5F, 1, 3, 5),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftLegDef.addOrReplaceChild("sidepanel_l3",
                CubeListBuilder.create().mirror()
                        .texOffs(12, 31).addBox(-0.5F, 5.5F, -2.5F, 1, 3, 5),
                PartPose.rotation(0.0F, 0.0F, -0.4363323F));

        leftLegDef.addOrReplaceChild("backpanel_l1",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2.0F, 0.5F, 2.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        leftLegDef.addOrReplaceChild("backpanel_l2",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2.0F, 2.5F, 1.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        leftLegDef.addOrReplaceChild("backpanel_l3",
                CubeListBuilder.create().mirror()
                        .texOffs(0, 18).addBox(-2.0F, 4.5F, 0.5F, 5, 3, 1),
                PartPose.rotation(0.4363323F, 0.0F, 0.0F));

        // Shoes on left leg
        leftLegDef.addOrReplaceChild("shoes_l",
                CubeListBuilder.create()
                        .texOffs(84, 4).addBox(-2.0F, 5F, -2.0F, 4, 4, 4, new CubeDeformation(1.0005f)),
                PartPose.ZERO);

        leftLegDef.addOrReplaceChild("shoes_part_l",
                CubeListBuilder.create()
                        .texOffs(100, 4).addBox(-2.5F, 10.5F, -3.6F, 5, 2, 6),
                PartPose.ZERO);

        return mesh;
    }

    /**
     * Sets part visibility based on the equipped armor slot.
     * Matches the render() logic from 1.12.2 ModelSamuraiArmors with state=3.
     */
    public void setVisibleForSlot(EquipmentSlot slot) {
        setAllVisible(false);

        switch (slot) {
            case HEAD:
                this.head.visible = true;
                // All head ornaments visible for state >= 3
                setHeadPartsVisible(true);
                break;
            case CHEST:
                this.body.visible = true;
                this.rightArm.visible = true;
                this.leftArm.visible = true;
                // Outer body: chestplate + backplate (state >= 1.0F)
                this.chestplate.visible = true;
                this.backplate.visible = true;
                this.beltR.visible = false; // hidden in 1.12.2
                this.beltL.visible = false; // hidden in 1.12.2
                this.mbelt.visible = false;
                this.mbeltL.visible = false;
                this.mbeltR.visible = false;
                // Arm parts
                setArmPartsVisible(true);
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
                // Leg panels visible (state=3, all layers)
                setLegPanelsVisible(true);
                // Shoes hidden for legs slot
                setShoesVisible(false);
                break;
            case FEET:
                this.rightLeg.visible = true;
                this.leftLeg.visible = true;
                // Only shoes visible
                setLegPanelsVisible(false);
                setShoesVisible(true);
                break;
            default:
                break;
        }
    }

    private void setHeadPartsVisible(boolean visible) {
        // Ornaments (state >= 3)
        this.ornamentL.visible = visible;
        this.ornamentL2.visible = visible;
        this.ornamentL3.visible = visible;
        this.ornamentR.visible = visible;
        this.ornamentR2.visible = visible;
        this.ornamentR3.visible = visible;
        // Gem (state >= 3)
        this.gemornament.visible = visible;
        this.gemornament2.visible = visible;
        this.gemornament3.visible = visible;
        this.gem.visible = visible;
        // Helmet
        this.helmet.visible = visible;
        this.helmetR.visible = visible;
        this.helmetL.visible = visible;
        this.helmetB.visible = visible;
        this.capsthingy.visible = visible;
        // Flaps (state >= 2)
        this.flapR.visible = visible;
        this.flapL.visible = visible;
    }

    private void setArmPartsVisible(boolean visible) {
        this.shoulderR.visible = visible;
        this.shoulderL.visible = visible;
        // Gauntlets (state >= 3)
        this.gauntletR.visible = visible;
        this.gauntletL.visible = visible;
        this.gauntletstrapR.visible = visible;
        this.gauntletstrapL.visible = visible;
        // Shoulder plates (state >= 2 for top/1, state >= 3 for 2/3)
        this.shoulderplateRtop.visible = visible;
        this.shoulderplateR1.visible = visible;
        this.shoulderplateR2.visible = visible;
        this.shoulderplateR3.visible = visible;
        this.shoulderplateLtop.visible = visible;
        this.shoulderplateL1.visible = visible;
        this.shoulderplateL2.visible = visible;
        this.shoulderplateL3.visible = visible;
    }

    private void setLegPanelsVisible(boolean visible) {
        // Front panels (state >= 1/2/3)
        this.legpanelR4.visible = visible;
        this.legpanelR5.visible = visible;
        this.legpanelR6.visible = visible;
        this.legpanelL4.visible = visible;
        this.legpanelL5.visible = visible;
        this.legpanelL6.visible = visible;
        // Side panels
        this.sidepanelR1.visible = visible;
        this.sidepanelR2.visible = visible;
        this.sidepanelR3.visible = visible;
        this.sidepanelL1.visible = visible;
        this.sidepanelL2.visible = visible;
        this.sidepanelL3.visible = visible;
        // Back panels
        this.backpanelR1.visible = visible;
        this.backpanelR2.visible = visible;
        this.backpanelR3.visible = visible;
        this.backpanelL1.visible = visible;
        this.backpanelL2.visible = visible;
        this.backpanelL3.visible = visible;
    }

    private void setShoesVisible(boolean visible) {
        this.shoesR.visible = visible;
        this.shoesL.visible = visible;
        this.shoesPartR.visible = visible;
        this.shoesPartL.visible = visible;
    }
}
