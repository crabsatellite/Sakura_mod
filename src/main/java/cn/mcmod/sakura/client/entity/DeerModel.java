package cn.mcmod.sakura.client.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import cn.mcmod.sakura.entity.DeerEntity;

/**
 * DeerModel - ported from 1.12.2 ModelDeer by bagu (Tabula 7.0.0)
 * Texture size: 64x32
 *
 * Hierarchy:
 *   body (root)
 *     ├─ neck
 *     │    └─ head
 *     │         ├─ head2 (snout)
 *     │         ├─ earR
 *     │         └─ earL
 *     ├─ legR  (front-right)
 *     ├─ legL  (front-left)
 *     ├─ backlegR
 *     └─ backlegL
 */
public class DeerModel extends AgeableListModel<DeerEntity> {

    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart legR;
    private final ModelPart legL;
    private final ModelPart backlegR;
    private final ModelPart backlegL;

    public DeerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.legR = this.body.getChild("legR");
        this.legL = this.body.getChild("legL");
        this.backlegR = this.body.getChild("backlegR");
        this.backlegL = this.body.getChild("backlegL");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // body: pivot (0, 15, -5.5), box (-3.5, -3.5, 0, 7, 7, 13), texOffs(0, 0)
        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 13.0F),
                PartPose.offset(0.0F, 15.0F, -5.5F));

        // neck: pivot (0, -1, 2) relative to body, rotX = -1.0927506f
        PartDefinition neck = body.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(27, 0)
                        .addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, -1.0927506F, 0.0F, 0.0F));

        // head: pivot (0, -1.1, -5.1) relative to neck, rotX = 1.0821041f
        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 20)
                        .addBox(-2.5F, -3.0F, -5.0F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -1.1F, -5.1F, 1.0821041F, 0.0F, 0.0F));

        // head2 (snout): pivot (0, 0, -4) relative to head
        head.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(20, 20)
                        .addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, -4.0F));

        // earR: pivot (-1.6, -4, -2) relative to head
        head.addOrReplaceChild("earR",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 1.0F),
                PartPose.offset(-1.6F, -4.0F, -2.0F));

        // earL: pivot (1.6, -4, -2) relative to head
        head.addOrReplaceChild("earL",
                CubeListBuilder.create().texOffs(0, 3)
                        .addBox(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 1.0F),
                PartPose.offset(1.6F, -4.0F, -2.0F));

        // legR (front-right): pivot (-2, 2, 2) relative to body
        body.addOrReplaceChild("legR",
                CubeListBuilder.create().texOffs(40, 11)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F),
                PartPose.offset(-2.0F, 2.0F, 2.0F));

        // legL (front-left): pivot (2, 2, 2) relative to body
        body.addOrReplaceChild("legL",
                CubeListBuilder.create().texOffs(48, 11)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F),
                PartPose.offset(2.0F, 2.0F, 2.0F));

        // backlegR: pivot (-2, 2, 10) relative to body
        body.addOrReplaceChild("backlegR",
                CubeListBuilder.create().texOffs(40, 20)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F),
                PartPose.offset(-2.0F, 2.0F, 10.0F));

        // backlegL: pivot (2, 2, 10.7) relative to body
        body.addOrReplaceChild("backlegL",
                CubeListBuilder.create().texOffs(48, 20)
                        .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F),
                PartPose.offset(2.0F, 2.0F, 10.7F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(DeerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Head tracking: apply pitch to neck (offset by the model's base rotation)
        this.head.xRot = 1.0821041F;
        this.neck.xRot = (headPitch * ((float) Math.PI / 180F)) - 1.0927506F;
        this.neck.yRot = netHeadYaw * ((float) Math.PI / 180F);

        // Walk cycle - front and back legs alternate
        this.legR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backlegR.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backlegL.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
