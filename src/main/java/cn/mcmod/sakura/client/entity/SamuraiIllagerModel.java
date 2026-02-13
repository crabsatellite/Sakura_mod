package cn.mcmod.sakura.client.entity;

import cn.mcmod.sakura.entity.SamuraiIllagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

/**
 * SamuraiIllagerModel - ported from 1.12.2 ModelSamuraiIllager.
 * Uses the vanilla illager model geometry (same as Vindicator):
 *   - head, hat (hidden), body, arms (crossed), right_arm, left_arm, right_leg, left_leg
 *
 * Uses HierarchicalModel with ArmedModel for the ItemInHandLayer to work,
 * with custom animation logic matching the original 1.12.2 model.
 */
public class SamuraiIllagerModel extends HierarchicalModel<SamuraiIllagerEntity> implements ArmedModel {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart body;
    private final ModelPart arms;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public SamuraiIllagerModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hat.visible = false;
        this.body = root.getChild("body");
        this.arms = root.getChild("arms");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    /**
     * Creates the illager body layer definition, matching vanilla's IllagerModel geometry.
     * Texture size: 64x64
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Head at (0, 0, 0)
        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Hat (child of head) - same position, slightly inflated
        head.addOrReplaceChild("hat",
                CubeListBuilder.create().texOffs(32, 0)
                        .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)),
                PartPose.ZERO);

        // Nose
        head.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(24, 0)
                        .addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        // Body
        partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 20)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F)
                        .texOffs(0, 38)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Arms (crossed pose)
        PartDefinition arms = partdefinition.addOrReplaceChild("arms",
                CubeListBuilder.create().texOffs(44, 22)
                        .addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F)
                        .texOffs(44, 22).mirror()
                        .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F)
                        .texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));

        // Right arm
        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 46)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        // Left arm
        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(40, 46).mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        // Right leg
        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 22)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));

        // Left leg
        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 22).mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(SamuraiIllagerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        // Leg walking animation
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;

        // Determine if attacking based on whether the entity is aggressive (has attack target)
        boolean attacking = entity.isAggressive();

        if (attacking) {
            // Show individual arms for weapon holding, hide crossed arms
            this.arms.visible = false;
            this.rightArm.visible = true;
            this.leftArm.visible = true;

            // Attack animation - swing arms
            float f = Mth.sin(this.attackTime * (float) Math.PI);
            float f1 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);

            this.rightArm.zRot = -0.3363323F;
            this.leftArm.zRot = 0.3363323F;
            this.rightArm.yRot = -0.3363323F;
            this.leftArm.yRot = 0.3363323F;

            this.rightArm.xRot = -1.4849558F + Mth.cos(ageInTicks * 0.09F) * 0.15F;
            this.leftArm.xRot = -1.4849558F + Mth.cos(ageInTicks * 0.09F) * 0.15F;
            this.rightArm.xRot += f * 2.2F - f1 * 0.4F;
            this.leftArm.xRot += f * 2.2F - f1 * 0.4F;

            this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.rightArm.xRot += Mth.sin(ageInTicks * 0.09F) * 0.05F;
            this.leftArm.xRot -= Mth.sin(ageInTicks * 0.09F) * 0.05F;
        } else {
            // Crossed arms idle pose
            this.arms.visible = true;
            this.rightArm.visible = false;
            this.leftArm.visible = false;

            this.arms.y = 3.0F;
            this.arms.z = -1.0F;
            this.arms.xRot = -0.75F;
        }
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        ModelPart modelpart = arm == HumanoidArm.RIGHT ? this.rightArm : this.leftArm;
        modelpart.translateAndRotate(poseStack);
    }

    public ModelPart getHead() {
        return this.head;
    }
}
