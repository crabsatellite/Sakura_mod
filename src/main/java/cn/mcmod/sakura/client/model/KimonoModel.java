package cn.mcmod.sakura.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class KimonoModel<T extends LivingEntity> extends HumanoidModel<T> {

    public KimonoModel(ModelPart root) {
        super(root);
    }

    public static MeshDefinition createMesh() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Body container (empty parent, children carry the geometry)
        PartDefinition bodyContainer = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.ZERO);

        // Body box with inflation 0.75f (matches 1.12.2)
        PartDefinition bodyBox = bodyContainer.addOrReplaceChild("body_box",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-3.5F, 0F, -1.35F, 7, 12, 3, new CubeDeformation(0.75f)),
                PartPose.ZERO);

        // Obi (belt sash) - no inflation (matches 1.12.2)
        PartDefinition obi = bodyContainer.addOrReplaceChild("obi",
                CubeListBuilder.create()
                        .texOffs(28, 32)
                        .addBox(-4.5F, 7F, -2.275F, 9, 3, 5),
                PartPose.ZERO);

        // Obi back - no inflation (matches 1.12.2)
        PartDefinition obiBack = bodyContainer.addOrReplaceChild("obi_back",
                CubeListBuilder.create()
                        .texOffs(28, 40)
                        .addBox(-3F, 6F, 2F, 6, 4, 1),
                PartPose.ZERO);

        // Right arm with inflation 1.025f (matches 1.12.2)
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(24, 16)
                        .addBox(-2.5F, -2F, -1.6F, 3, 12, 3, new CubeDeformation(1.025f)),
                PartPose.ZERO);

        // Left arm with inflation 1.025f (matches 1.12.2)
        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-0.5F, -2F, -1.6F, 3, 12, 3, new CubeDeformation(1.025f)),
                PartPose.ZERO);

        // Right leg with inflation 0.75f (matches 1.12.2)
        PartDefinition rightLeg = root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-1.5F, 0F, -1.5F, 3, 12, 3, new CubeDeformation(0.75f)),
                PartPose.ZERO);

        // Left leg with inflation 0.75f (matches 1.12.2)
        PartDefinition leftLeg = root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(16, 32)
                        .addBox(-1.5F, 0F, -1.5F, 3, 12, 3, new CubeDeformation(0.75f)),
                PartPose.ZERO);

        // Body bottom (skirt/hakama) - no inflation (matches 1.12.2)
        PartDefinition bodyBottomRight = rightLeg.addOrReplaceChild("body_bottom_right",
                CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.75F, -1F, -2.5F, 5, 10, 5),
                PartPose.ZERO);

        PartDefinition bodyBottomLeft = leftLeg.addOrReplaceChild("body_bottom_left",
                CubeListBuilder.create()
                        .texOffs(21, 48)
                        .addBox(-2.25F, -1F, -2.5F, 5, 10, 5),
                PartPose.ZERO);

        // Empty head and hat (kimono doesn't cover head)
        root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        return mesh;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head, this.hat);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
    }

    public void setVisibleForSlot(EquipmentSlot slot) {
        setAllVisible(false);

        switch (slot) {
            case LEGS:
                this.body.visible = true;
                this.rightArm.visible = true;
                this.leftArm.visible = true;
                this.rightLeg.visible = true;
                this.leftLeg.visible = true;
                break;
            case CHEST:
                this.body.visible = true;
                this.rightArm.visible = true;
                this.leftArm.visible = true;
                break;
            default:
                break;
        }
    }
}
