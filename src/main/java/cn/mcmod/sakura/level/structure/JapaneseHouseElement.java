package cn.mcmod.sakura.level.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import cn.mcmod.sakura.block.BlockRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.Collections;
import java.util.List;

/**
 * Procedurally-generated Japanese village house for 1.20.1.
 *
 * This is a direct port of the 1.12.2 {@code WAVillagerHouse.addComponentParts()} method,
 * which generated a 9x9x6 Japanese-style house (stone brick foundation, bamboo plank walls,
 * straw thatch roof, bamboo fence furniture, glass pane windows).
 *
 * <p>In 1.20.1, village buildings are placed via the Jigsaw/Structure Pool system using
 * {@link StructurePoolElement} subclasses. This class extends {@link StructurePoolElement}
 * to generate the house procedurally without requiring an .nbt template file.</p>
 *
 * <h2>Building layout (9 wide x 7 deep x 10 tall, coordinates relative to placement origin)</h2>
 * <pre>
 *   Z=-1: Entrance step (maple stair)
 *   Y=0: Stone brick floor (foundation), Z=0..5
 *   Y=1: Stone brick walls (lower ring) + interior furniture
 *   Y=2-4: Bamboo plank walls + glass pane windows
 *   Y=5-8: Stone brick shell + straw stair thatch roof
 * </pre>
 *
 * <h2>Block mapping from 1.12.2 to 1.20.1</h2>
 * <ul>
 *   <li>{@code Blocks.STONEBRICK} -> {@code Blocks.STONE_BRICKS}</li>
 *   <li>{@code BlockLoader.STRAW_BLOCK_STAIR} -> {@code BlockRegistry.STRAW_STAIRS}</li>
 *   <li>{@code BlockLoader.BAMBOO_PLANK_STAIR} -> {@code BlockRegistry.BAMBOO_PLANK_STAIRS}</li>
 *   <li>{@code BlockLoader.BAMBOO_PLANK} -> {@code BlockRegistry.BAMBOO_PLANK}</li>
 *   <li>{@code BlockLoader.MAPLE_PLANK_STAIR} -> {@code BlockRegistry.MAPLE_STAIRS}</li>
 *   <li>{@code BlockLoader.BAMBOO_FENCE} -> {@code BlockRegistry.BAMBOO_FENCE}</li>
 *   <li>{@code Blocks.WOODEN_PRESSURE_PLATE} -> {@code Blocks.OAK_PRESSURE_PLATE}</li>
 * </ul>
 */
public class JapaneseHouseElement extends StructurePoolElement {

    // Codec that allows this element type to be serialized/deserialized by the structure system.
    // Since our element has no configurable parameters (it's a fixed design), we just need
    // the projection field inherited from StructurePoolElement.
    public static final MapCodec<JapaneseHouseElement> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(projectionCodec())
                    .apply(instance, JapaneseHouseElement::new));

    /**
     * Returns the registered StructurePoolElementType for this element.
     * Lazily resolved from {@link SakuraStructureRegistry#JAPANESE_HOUSE}.
     */
    public static StructurePoolElementType<JapaneseHouseElement> type() {
        return SakuraStructureRegistry.JAPANESE_HOUSE.get();
    }

    // Building dimensions: 9 wide (X) x 7 deep (Z, including entrance step at z=-1) x 10 tall (Y)
    private static final int SIZE_X = 9;
    private static final int SIZE_Z = 7; // z=-1 to z=5 inclusive
    private static final int SIZE_Y = 10; // floor to roof peak

    public JapaneseHouseElement(StructureTemplatePool.Projection projection) {
        super(projection);
    }

    @Override
    public Vec3i getSize(StructureTemplateManager manager, Rotation rotation) {
        // Return the bounding box size for this structure piece.
        // For 90/270 rotations, X and Z dimensions are swapped.
        if (rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90) {
            return new Vec3i(SIZE_Z, SIZE_Y, SIZE_X);
        }
        return new Vec3i(SIZE_X, SIZE_Y, SIZE_Z);
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(
            StructureTemplateManager manager,
            BlockPos pos,
            Rotation rotation,
            RandomSource random) {
        // Provide a jigsaw connection point at ground level facing outward (the door side).
        // This tells the Jigsaw system where this building connects to village paths.
        // The jigsaw block is at position (1, 0, -1) relative to the structure origin
        // (just outside the door, at the entrance step), then rotated.
        int[] rotated = rotatePos(1, -1, SIZE_X, SIZE_Z, rotation);

        BlockState jigsawState = Blocks.JIGSAW.defaultBlockState()
                .setValue(net.minecraft.world.level.block.JigsawBlock.ORIENTATION,
                        net.minecraft.core.FrontAndTop.NORTH_UP);

        StructureTemplate.StructureBlockInfo jigsawBlock = new StructureTemplate.StructureBlockInfo(
                pos.offset(rotated[0], 0, rotated[1]),
                jigsawState,
                createJigsawNbt()
        );
        return Collections.singletonList(jigsawBlock);
    }

    /**
     * Creates the NBT data for the jigsaw block connection.
     * This defines how the building connects to village paths.
     */
    private net.minecraft.nbt.CompoundTag createJigsawNbt() {
        net.minecraft.nbt.CompoundTag tag = new net.minecraft.nbt.CompoundTag();
        tag.putString("name", "sakura:japanese_house");
        tag.putString("target", "minecraft:building_entrance");
        tag.putString("pool", "minecraft:village/common/empty");
        tag.putString("final_state", "minecraft:air");
        tag.putString("joint", "rollable");
        return tag;
    }

    @Override
    public BoundingBox getBoundingBox(StructureTemplateManager manager, BlockPos pos,
                                      Rotation rotation) {
        // The structure spans x=[0..8], z=[-1..5] in local space (including entrance step).
        // We rotate the two corners and compute the bounding box from the min/max of the results.
        int[] corner1 = rotatePos(0, -1, SIZE_X, SIZE_Z, rotation);
        int[] corner2 = rotatePos(SIZE_X - 1, 5, SIZE_X, SIZE_Z, rotation);

        int minX = Math.min(corner1[0], corner2[0]);
        int maxX = Math.max(corner1[0], corner2[0]);
        int minZ = Math.min(corner1[1], corner2[1]);
        int maxZ = Math.max(corner1[1], corner2[1]);

        return new BoundingBox(
                pos.getX() + minX, pos.getY(), pos.getZ() + minZ,
                pos.getX() + maxX, pos.getY() + SIZE_Y - 1, pos.getZ() + maxZ
        );
    }

    /**
     * Rotates a relative (x, z) position around the structure origin for the given rotation.
     *
     * <p>Minecraft's Rotation enum rotates clockwise when viewed from above (positive Y looking down).
     * The rotation pivot is at the origin (0, 0).</p>
     *
     * @param x local X coordinate
     * @param z local Z coordinate
     * @param sizeX structure width (unused but kept for API consistency)
     * @param sizeZ structure depth (unused but kept for API consistency)
     * @param rotation the rotation to apply
     * @return int array of [rotatedX, rotatedZ]
     */
    private static int[] rotatePos(int x, int z, int sizeX, int sizeZ, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_90:
                return new int[]{-z, x};
            case CLOCKWISE_180:
                return new int[]{-x, -z};
            case COUNTERCLOCKWISE_90:
                return new int[]{z, -x};
            case NONE:
            default:
                return new int[]{x, z};
        }
    }

    /**
     * Rotates a Direction in the horizontal plane by the given rotation.
     */
    private static Direction rotateDirection(Direction dir, Rotation rotation) {
        if (rotation == Rotation.NONE) return dir;
        Direction result = dir;
        switch (rotation) {
            case CLOCKWISE_90:
                result = dir.getClockWise();
                break;
            case CLOCKWISE_180:
                result = dir.getOpposite();
                break;
            case COUNTERCLOCKWISE_90:
                result = dir.getCounterClockWise();
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public boolean place(StructureTemplateManager manager, WorldGenLevel level,
                         StructureManager structureManager, ChunkGenerator chunkGenerator,
                         BlockPos origin, BlockPos jigsawTargetPos,
                         Rotation rotation,
                         BoundingBox boundingBox, RandomSource random, LiquidSettings liquidSettings, boolean keepJigsaws) {
        // Direct port of WAVillagerHouse.addComponentParts() from 1.12.2,
        // with rotation support applied to all block placements.
        // Coordinates: x=0..8, z=-1..5, y=0..9 relative to origin (before rotation)

        // ===== Block states =====
        // Directional blocks get their facing rotated.
        BlockState stoneBricks = Blocks.STONE_BRICKS.defaultBlockState();
        BlockState strawStairNorth = BlockRegistry.STRAW_STAIRS.get().defaultBlockState()
                .setValue(StairBlock.FACING, rotateDirection(Direction.NORTH, rotation));
        BlockState strawStairSouth = BlockRegistry.STRAW_STAIRS.get().defaultBlockState()
                .setValue(StairBlock.FACING, rotateDirection(Direction.SOUTH, rotation));
        BlockState bambooPlankStairNorth = BlockRegistry.BAMBOO_PLANK_STAIRS.get().defaultBlockState()
                .setValue(StairBlock.FACING, rotateDirection(Direction.NORTH, rotation));
        BlockState bambooPlankStairEast = BlockRegistry.BAMBOO_PLANK_STAIRS.get().defaultBlockState()
                .setValue(StairBlock.FACING, rotateDirection(Direction.EAST, rotation));
        BlockState bambooPlank = BlockRegistry.BAMBOO_PLANK.get().defaultBlockState();
        BlockState mapleStairNorth = BlockRegistry.MAPLE_STAIRS.get().defaultBlockState()
                .setValue(StairBlock.FACING, rotateDirection(Direction.NORTH, rotation));
        BlockState bambooFence = BlockRegistry.BAMBOO_FENCE.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState glassPane = Blocks.GLASS_PANE.defaultBlockState();
        BlockState pressurePlate = Blocks.OAK_PRESSURE_PLATE.defaultBlockState();
        BlockState craftingTable = Blocks.CRAFTING_TABLE.defaultBlockState();

        // ===== Clear interior =====
        // fillWithBlocks(1,1,1 -> 7,5,4, AIR)
        fillBox(level, origin, boundingBox, rotation, 1, 1, 1, 7, 5, 4, air);

        // ===== Foundation floor (Y=0) =====
        // fillWithBlocks(0,0,0 -> 8,0,5, stoneBricks)
        fillBox(level, origin, boundingBox, rotation, 0, 0, 0, 8, 0, 5, stoneBricks);

        // ===== Ceiling/roof shell (Y=5) =====
        // fillWithBlocks(0,5,0 -> 8,5,5, stoneBricks)
        fillBox(level, origin, boundingBox, rotation, 0, 5, 0, 8, 5, 5, stoneBricks);

        // ===== Upper roof layers =====
        // fillWithBlocks(0,6,1 -> 8,6,4, stoneBricks)
        fillBox(level, origin, boundingBox, rotation, 0, 6, 1, 8, 6, 4, stoneBricks);
        // fillWithBlocks(0,7,2 -> 8,7,3, stoneBricks)
        fillBox(level, origin, boundingBox, rotation, 0, 7, 2, 8, 7, 3, stoneBricks);

        // ===== Straw thatch roof (sloping stairs) =====
        // for i = -1..2: for j = 0..8:
        //   setBlockState(strawStairNorth, j, 6+i, i)
        //   setBlockState(strawStairSouth, j, 6+i, 5-i)
        for (int i = -1; i <= 2; ++i) {
            for (int j = 0; j <= 8; ++j) {
                placeBlock(level, origin, boundingBox, rotation, strawStairNorth, j, 6 + i, i);
                placeBlock(level, origin, boundingBox, rotation, strawStairSouth, j, 6 + i, 5 - i);
            }
        }

        // ===== Stone brick lower walls (Y=1 ring) =====
        // West wall
        fillBox(level, origin, boundingBox, rotation, 0, 1, 0, 0, 1, 5, stoneBricks);
        // South wall
        fillBox(level, origin, boundingBox, rotation, 1, 1, 5, 8, 1, 5, stoneBricks);
        // East wall
        fillBox(level, origin, boundingBox, rotation, 8, 1, 0, 8, 1, 4, stoneBricks);
        // North wall
        fillBox(level, origin, boundingBox, rotation, 2, 1, 0, 7, 1, 0, stoneBricks);

        // ===== Stone brick corner pillars (Y=2..4) =====
        fillBox(level, origin, boundingBox, rotation, 0, 2, 0, 0, 4, 0, stoneBricks);
        fillBox(level, origin, boundingBox, rotation, 0, 2, 5, 0, 4, 5, stoneBricks);
        fillBox(level, origin, boundingBox, rotation, 8, 2, 5, 8, 4, 5, stoneBricks);
        fillBox(level, origin, boundingBox, rotation, 8, 2, 0, 8, 4, 0, stoneBricks);

        // ===== Bamboo plank walls (Y=2..4) =====
        // West wall interior
        fillBox(level, origin, boundingBox, rotation, 0, 2, 1, 0, 4, 4, bambooPlank);
        // South wall
        fillBox(level, origin, boundingBox, rotation, 1, 2, 5, 7, 4, 5, bambooPlank);
        // East wall
        fillBox(level, origin, boundingBox, rotation, 8, 2, 1, 8, 4, 4, bambooPlank);
        // North wall (front)
        fillBox(level, origin, boundingBox, rotation, 1, 2, 0, 7, 4, 0, bambooPlank);

        // ===== Glass pane windows =====
        // North wall windows (front, x=4,5,6 at y=2,3)
        placeBlock(level, origin, boundingBox, rotation, glassPane, 4, 2, 0);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 5, 2, 0);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 6, 2, 0);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 4, 3, 0);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 5, 3, 0);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 6, 3, 0);

        // West wall windows (z=2,3 at y=2,3)
        placeBlock(level, origin, boundingBox, rotation, glassPane, 0, 2, 2);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 0, 2, 3);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 0, 3, 2);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 0, 3, 3);

        // East wall windows (z=2,3 at y=2,3)
        placeBlock(level, origin, boundingBox, rotation, glassPane, 8, 2, 2);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 8, 2, 3);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 8, 3, 2);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 8, 3, 3);

        // South wall windows (x=2,3 and x=5,6 at y=2)
        placeBlock(level, origin, boundingBox, rotation, glassPane, 2, 2, 5);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 3, 2, 5);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 5, 2, 5);
        placeBlock(level, origin, boundingBox, rotation, glassPane, 6, 2, 5);

        // ===== Ceiling detail beams (Y=4) =====
        fillBox(level, origin, boundingBox, rotation, 1, 4, 1, 7, 4, 1, bambooPlank);
        fillBox(level, origin, boundingBox, rotation, 1, 4, 4, 7, 4, 4, bambooPlank);

        // ===== Interior furniture (Y=1) =====
        // Raised platform in back-right corner
        placeBlock(level, origin, boundingBox, rotation, bambooPlank, 7, 1, 4);
        placeBlock(level, origin, boundingBox, rotation, bambooPlankStairEast, 7, 1, 3);
        placeBlock(level, origin, boundingBox, rotation, bambooPlankStairNorth, 6, 1, 4);
        placeBlock(level, origin, boundingBox, rotation, bambooPlankStairNorth, 5, 1, 4);
        placeBlock(level, origin, boundingBox, rotation, bambooPlankStairNorth, 4, 1, 4);
        placeBlock(level, origin, boundingBox, rotation, bambooPlankStairNorth, 3, 1, 4);

        // Bamboo fence tables with pressure plates (lanterns)
        placeBlock(level, origin, boundingBox, rotation, bambooFence, 6, 1, 3);
        placeBlock(level, origin, boundingBox, rotation, pressurePlate, 6, 2, 3);
        placeBlock(level, origin, boundingBox, rotation, bambooFence, 4, 1, 3);
        placeBlock(level, origin, boundingBox, rotation, pressurePlate, 4, 2, 3);

        // Crafting table
        placeBlock(level, origin, boundingBox, rotation, craftingTable, 7, 1, 1);

        // ===== Door (at x=1, z=0, front wall) =====
        // Clear door space
        placeBlock(level, origin, boundingBox, rotation, air, 1, 1, 0);
        placeBlock(level, origin, boundingBox, rotation, air, 1, 2, 0);

        // Place bamboo door (facing rotated with the structure)
        BlockState doorBlock = BlockRegistry.BAMBOO_DOOR.get().defaultBlockState()
                .setValue(DoorBlock.FACING, rotateDirection(Direction.NORTH, rotation))
                .setValue(DoorBlock.OPEN, false);
        placeBlock(level, origin, boundingBox, rotation,
                doorBlock.setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER), 1, 1, 0);
        placeBlock(level, origin, boundingBox, rotation,
                doorBlock.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 1, 2, 0);

        // ===== Entrance step =====
        // In 1.12.2: if block at (1,0,-1) is air and block at (1,-1,-1) is not air, place maple stair step
        int[] stepRot = rotatePos(1, -1, SIZE_X, SIZE_Z, rotation);
        BlockPos stepPos = origin.offset(stepRot[0], 0, stepRot[1]);
        if (boundingBox.isInside(stepPos)) {
            BlockState stateAtStep = level.getBlockState(stepPos);
            BlockState stateBelowStep = level.getBlockState(stepPos.below());
            if (stateAtStep.isAir() && !stateBelowStep.isAir()) {
                placeBlock(level, origin, boundingBox, rotation, mapleStairNorth, 1, 0, -1);
            }
        }

        // ===== Foundation fill downward =====
        // Replace air/liquid below the building with stone bricks (foundation pillars)
        for (int z = 0; z < 6; ++z) {
            for (int x = 0; x < SIZE_X; ++x) {
                fillDownwards(level, origin, boundingBox, rotation, stoneBricks, x, -1, z);
            }
        }

        // ===== Clear above the building =====
        for (int z = 0; z < 6; ++z) {
            for (int x = 0; x < SIZE_X; ++x) {
                clearAbove(level, origin, boundingBox, rotation, x, SIZE_Y, z);
            }
        }

        return true;
    }

    // ===== Helper methods (port of StructureVillagePieces.Village utility methods) =====

    /**
     * Fills a box region with the given block state, applying rotation to all positions.
     * Port of {@code fillWithBlocks()} from 1.12.2.
     */
    private void fillBox(WorldGenLevel level, BlockPos origin, BoundingBox bb,
                         Rotation rotation,
                         int x1, int y1, int z1, int x2, int y2, int z2, BlockState state) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    placeBlock(level, origin, bb, rotation, state, x, y, z);
                }
            }
        }
    }

    /**
     * Places a single block at the given local coordinates, rotated and offset by the structure origin.
     * Only places if the position is within the allowed bounding box.
     * Port of {@code setBlockState()} from 1.12.2.
     */
    private void placeBlock(WorldGenLevel level, BlockPos origin, BoundingBox bb,
                            Rotation rotation,
                            BlockState state, int localX, int localY, int localZ) {
        int[] rotated = rotatePos(localX, localZ, SIZE_X, SIZE_Z, rotation);
        BlockPos pos = origin.offset(rotated[0], localY, rotated[1]);
        if (bb.isInside(pos)) {
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        }
    }

    /**
     * Fills downward from the given local position, replacing air/liquid with the given block.
     * Port of {@code replaceAirAndLiquidDownwards()} from 1.12.2.
     */
    private void fillDownwards(WorldGenLevel level, BlockPos origin, BoundingBox bb,
                               Rotation rotation,
                               BlockState state, int localX, int startY, int localZ) {
        int[] rotated = rotatePos(localX, localZ, SIZE_X, SIZE_Z, rotation);
        BlockPos.MutableBlockPos pos = origin.offset(rotated[0], startY, rotated[1]).mutable();
        while (pos.getY() > level.getMinBuildHeight() && bb.isInside(pos)) {
            BlockState existing = level.getBlockState(pos);
            if (!existing.isAir() && existing.getFluidState().isEmpty()) {
                break;
            }
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
            pos.move(Direction.DOWN);
        }
    }

    /**
     * Clears blocks above the structure to prevent terrain from poking through the roof.
     * Port of {@code clearCurrentPositionBlocksUpwards()} from 1.12.2.
     */
    private void clearAbove(WorldGenLevel level, BlockPos origin, BoundingBox bb,
                            Rotation rotation,
                            int localX, int startY, int localZ) {
        int[] rotated = rotatePos(localX, localZ, SIZE_X, SIZE_Z, rotation);
        BlockPos.MutableBlockPos pos = origin.offset(rotated[0], startY, rotated[1]).mutable();
        // Clear a reasonable number of blocks above (up to 16)
        for (int i = 0; i < 16; i++) {
            if (!bb.isInside(pos)) break;
            BlockState existing = level.getBlockState(pos);
            if (existing.isAir()) break;
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
            pos.move(Direction.UP);
        }
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return type();
    }

    @Override
    public String toString() {
        return "JapaneseHouse[]";
    }
}
