package cn.mcmod.sakura.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.ShojiBlock;
import cn.mcmod.sakura.block.entity.ShojiBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * BlockEntityRenderer for the Shoji (Japanese sliding door/screen).
 * The block model is intentionally empty -- all visual rendering is done here
 * so that the texture variant (type 0-5) stored in the BlockEntity can be used.
 * <p>
 * The shoji is a 2-block-tall panel rendered from a single block entity,
 * matching the original 1.12.2 model (16×32×2 box on 64×64 texture).
 * <p>
 * Textures are located at textures/entity/block/shoji_type_N.png (N = 0..5).
 */
public class ShojiRenderer implements BlockEntityRenderer<ShojiBlockEntity> {

    private static final int MAX_TYPES = 6;
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[MAX_TYPES];

    static {
        for (int i = 0; i < MAX_TYPES; i++) {
            TEXTURES[i] = new ResourceLocation(SakuraMod.MODID, "textures/entity/block/shoji_type_" + i + ".png");
        }
    }

    private static final float ANIMATION_DURATION = 10f;

    // UV coordinates for a 16w×32h×2d box on a 64×64 texture (Minecraft standard box UV layout)
    // texU=0, texV=0
    private static final float TW = 64f;
    private static final float TH = 64f;

    // Front face (+Z local): mapped to "North" face in MC box layout → pixels (2,2)→(18,34)
    private static final float FU0 = 2f / TW,  FU1 = 18f / TW;
    private static final float FV0 = 2f / TH,  FV1 = 34f / TH;

    // Back face (-Z local): mapped to "South" face → pixels (20,2)→(36,34)
    private static final float BU0 = 20f / TW, BU1 = 36f / TW;
    private static final float BV0 = 2f / TH,  BV1 = 34f / TH;

    // Top face (+Y): "Up" face → pixels (2,0)→(18,2)
    private static final float TU0 = 2f / TW,  TU1 = 18f / TW;
    private static final float TV0 = 0f / TH,  TV1 = 2f / TH;

    // Bottom face (-Y): "Down" face → pixels (18,0)→(34,2)
    private static final float DU0 = 18f / TW, DU1 = 34f / TW;
    private static final float DV0 = 0f / TH,  DV1 = 2f / TH;

    // Left edge (-X): "West" face → pixels (0,2)→(2,34)
    private static final float LU0 = 0f / TW,  LU1 = 2f / TW;
    private static final float LV0 = 2f / TH,  LV1 = 34f / TH;

    // Right edge (+X): "East" face → pixels (18,2)→(20,34)
    private static final float RU0 = 18f / TW, RU1 = 20f / TW;
    private static final float RV0 = 2f / TH,  RV1 = 34f / TH;

    public ShojiRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public boolean shouldRenderOffScreen(ShojiBlockEntity blockEntity) {
        return true; // Panel is 2 blocks tall, extends beyond block bounds
    }

    @Override
    public void render(ShojiBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        if (!(state.getBlock() instanceof ShojiBlock)) return;

        Direction facing = state.getValue(ShojiBlock.FACING);
        boolean open = state.getValue(ShojiBlock.OPEN);

        int type = blockEntity.getShojiType();
        if (type < 0 || type >= MAX_TYPES) type = 0;

        ResourceLocation texture = TEXTURES[type];

        // Calculate animation progress (1.0 = fully transitioned, 0.0 = just started)
        int animTicks = blockEntity.getAnimation();
        float animProgress;
        if (animTicks > 0) {
            float remaining = animTicks - partialTicks;
            if (remaining < 0) remaining = 0;
            animProgress = 1.0f - (remaining / ANIMATION_DURATION);
        } else {
            animProgress = 1.0f;
        }

        // openFactor: 0.0 = fully closed, 1.0 = fully open
        float openFactor;
        if (open) {
            openFactor = animProgress;
        } else {
            openFactor = 1.0f - animProgress;
        }

        poseStack.pushPose();

        // Move to block center, rotate based on facing
        poseStack.translate(0.5, 0.0, 0.5);

        float yRot = switch (facing) {
            case SOUTH -> 0f;
            case WEST -> 90f;
            case NORTH -> 180f;
            case EAST -> 270f;
            default -> 0f;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        // Slide sideways when open (0.8 blocks, matching 1.12.2 behavior)
        poseStack.translate(openFactor * 0.8, 0.0, 0.0);

        // Panel: 1 block wide, 2 blocks tall, 2/16 thick (matching 1.12.2 model: 16w×32h×2d)
        float panelLeft = -0.5f;
        float panelRight = 0.5f;
        float panelBottom = 0.0f;
        float panelTop = 2.0f;
        float halfThick = 1f / 16f; // 0.0625

        VertexConsumer builder = bufferSource.getBuffer(RenderType.entityTranslucent(texture));
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        // Front face (+Z side, main panel)
        addVertex(builder, matrix, normal, panelLeft,  panelBottom, halfThick, FU0, FV1, 0, 0, 1, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft,  panelTop,    halfThick, FU0, FV0, 0, 0, 1, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelTop,    halfThick, FU1, FV0, 0, 0, 1, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelBottom, halfThick, FU1, FV1, 0, 0, 1, packedLight, packedOverlay);

        // Back face (-Z side)
        addVertex(builder, matrix, normal, panelRight, panelBottom, -halfThick, BU0, BV1, 0, 0, -1, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelTop,    -halfThick, BU0, BV0, 0, 0, -1, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft,  panelTop,    -halfThick, BU1, BV0, 0, 0, -1, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft,  panelBottom, -halfThick, BU1, BV1, 0, 0, -1, packedLight, packedOverlay);

        // Top face (+Y)
        addVertex(builder, matrix, normal, panelLeft,  panelTop, -halfThick, TU0, TV0, 0, 1, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft,  panelTop,  halfThick, TU0, TV1, 0, 1, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelTop,  halfThick, TU1, TV1, 0, 1, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelTop, -halfThick, TU1, TV0, 0, 1, 0, packedLight, packedOverlay);

        // Bottom face (-Y)
        addVertex(builder, matrix, normal, panelLeft,  panelBottom,  halfThick, DU0, DV0, 0, -1, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft,  panelBottom, -halfThick, DU0, DV1, 0, -1, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelBottom, -halfThick, DU1, DV1, 0, -1, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelBottom,  halfThick, DU1, DV0, 0, -1, 0, packedLight, packedOverlay);

        // Left edge face (-X)
        addVertex(builder, matrix, normal, panelLeft, panelBottom, -halfThick, LU0, LV1, -1, 0, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft, panelTop,    -halfThick, LU0, LV0, -1, 0, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft, panelTop,     halfThick, LU1, LV0, -1, 0, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelLeft, panelBottom,  halfThick, LU1, LV1, -1, 0, 0, packedLight, packedOverlay);

        // Right edge face (+X)
        addVertex(builder, matrix, normal, panelRight, panelBottom,  halfThick, RU0, RV1, 1, 0, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelTop,     halfThick, RU0, RV0, 1, 0, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelTop,    -halfThick, RU1, RV0, 1, 0, 0, packedLight, packedOverlay);
        addVertex(builder, matrix, normal, panelRight, panelBottom, -halfThick, RU1, RV1, 1, 0, 0, packedLight, packedOverlay);

        poseStack.popPose();
    }

    private void addVertex(VertexConsumer builder, Matrix4f pose, Matrix3f normal,
            float x, float y, float z, float u, float v,
            float nx, float ny, float nz,
            int packedLight, int packedOverlay) {
        builder.vertex(pose, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(normal, nx, ny, nz)
                .endVertex();
    }
}
