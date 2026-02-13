package cn.mcmod.sakura.block.machines;

import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Tatara (traditional iron smelting furnace).
 * Right-click with flint and steel to ignite (LIT=true).
 * When LIT, random ticks advance the TIMER from 0 to 3.
 * Adjacent unlit Tatara blocks are also ignited (spread smelting).
 * When broken while TIMER==3 (finished), drops tamahagane or steel ingots.
 * When broken while still smelting (TIMER<3), drops unlit Tatara block.
 */
public class TataraBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty TIMER = IntegerProperty.create("timer", 0, 3);

    public TataraBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                .strength(1.75F, 10.0F)
                .sound(SoundType.STONE)
                .lightLevel(state -> state.getValue(LIT) ? 13 : 0)
                .randomTicks());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false)
                .setValue(TIMER, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT, TIMER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 0.8F);
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND && !state.getValue(LIT)) {
            if (stack.is(Items.FLINT_AND_STEEL)) {
                level.setBlock(pos, state.setValue(LIT, true).setValue(TIMER, 0), 3);
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (!state.getValue(LIT)) return;
        if (!level.isAreaLoaded(pos, 1)) return;

        // Spread smelting to adjacent unlit Tatara blocks
        spreadSmelting(level, pos);

        int timer = state.getValue(TIMER);
        if (timer < 3) {
            // Advance the timer (1.12.2 used rand.nextInt(1)==0 which is always true)
            level.setBlock(pos, state.setValue(TIMER, timer + 1), 2);
        }
    }

    private void spreadSmelting(ServerLevel level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);
            BlockState adjacentState = level.getBlockState(adjacent);
            if (adjacentState.is(this) && !adjacentState.getValue(LIT)) {
                level.setBlock(adjacent, adjacentState.setValue(LIT, true).setValue(TIMER, 0), 2);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        if (!state.getValue(LIT) || state.getValue(TIMER) < 3) {
            // Not finished smelting: drop the unlit tatara block
            drops.add(new ItemStack(BlockRegistry.TATARA.get()));
            return drops;
        }
        // Finished smelting (TIMER == 3): drop tamahagane/steel
        RandomSource random = builder.getLevel().random;
        if (random.nextInt(10) == 0) {
            // Rare chance: drop tamahagane (special steel)
            for (int i = 0; i < 2; ++i) {
                if (random.nextInt(2) == 0) {
                    drops.add(new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TAMAHAGANE).get()));
                }
            }
        } else {
            // Normal: drop steel ingots
            for (int i = 0; i < 9; ++i) {
                if (random.nextInt(9) <= 7) {
                    drops.add(new ItemStack(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STEEL_INGOT).get()));
                }
            }
        }
        return drops;
    }
}
