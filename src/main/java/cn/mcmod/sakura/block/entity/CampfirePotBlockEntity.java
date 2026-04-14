package cn.mcmod.sakura.block.entity;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod.sakura.block.CampfirePotBlock;
import cn.mcmod.sakura.container.CampfirePotContainer;
import cn.mcmod.sakura.recipes.CookingPotRecipe;
import cn.mcmod.sakura.recipes.RecipeTypeRegistry;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import cn.mcmod_mmf.mmlib.fluid.FluidIngredient;
import cn.mcmod_mmf.mmlib.utils.LevelUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

/**
 * BlockEntity for the Campfire Pot block.
 * Ported from 1.12.2 TileEntityCampfirePot.
 * Has 9 input slots + 1 output slot, plus a fluid tank (2000 mB).
 * Cooks items when burning, consuming fluid and ingredients.
 */
public class CampfirePotBlockEntity extends SyncedBlockEntity implements MenuProvider {

    public static final int TANK_CAPACITY = 2000;
    public static final int INPUT_SLOTS = 9;
    public static final int OUTPUT_SLOT = 9;
    public static final int TOTAL_SLOTS = 10;

    private final ItemStackHandler inventory;
    private LazyOptional<IItemHandler> inputHandler;
    private LazyOptional<IItemHandler> outputHandler;
    private final FluidTank tank;
    private LazyOptional<FluidTank> fluidTank;
    protected final ContainerData tileData;

    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

    private int burnTime;
    private int cookTime;
    private int maxCookTime = 200;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    public CampfirePotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CAMPFIRE_POT.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> inventory);
        this.outputHandler = LazyOptional.of(() -> inventory);
        this.tank = createFluidHandler();
        this.fluidTank = LazyOptional.of(() -> this.tank);
        this.tileData = createIntArray();
        this.experienceTracker = new Object2IntOpenHashMap<>();
        this.checkNewRecipe = true;
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, CampfirePotBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        boolean wasBurning = blockEntity.isBurning();
        boolean changed = false;

        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
        }

        if (blockEntity.isBurning() && blockEntity.hasInput()) {
            Optional<CookingPotRecipe> recipe = blockEntity.getMatchingRecipe(new RecipeWrapper(blockEntity.inventory));
            if (recipe.isPresent() && blockEntity.canWork(recipe.get(), level)) {
                changed = blockEntity.processRecipe(recipe.get(), level);
            } else {
                blockEntity.cookTime = 0;
            }
        } else if (blockEntity.cookTime > 0) {
            blockEntity.cookTime = 0;
        }

        if (wasBurning != blockEntity.isBurning()) {
            changed = true;
            CampfirePotBlock.setLitState(blockEntity.isBurning(), level, pos, state);
        }

        if (changed) {
            blockEntity.inventoryChanged();
        }
    }

    private boolean hasInput() {
        for (int i = 0; i < INPUT_SLOTS; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private Optional<CookingPotRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (level == null) {
            return Optional.empty();
        }

        if (lastRecipeID != null) {
            Optional<? extends Recipe<RecipeWrapper>> recipeOpt = level.getRecipeManager()
                    .getAllRecipesFor(RecipeTypeRegistry.COOKING_RECIPE_TYPE.get()).stream()
                    .filter(now -> now.getId().equals(lastRecipeID)).findFirst();
            if (recipeOpt.isPresent()) {
                Recipe<RecipeWrapper> recipe = recipeOpt.get();
                if (recipe instanceof CookingPotRecipe cookingRecipe) {
                    if (cookingRecipe.matchesWithFluid(
                            this.tank.getFluid(),
                            inventoryWrapper, level)) {
                        return Optional.of(cookingRecipe);
                    }
                }
            } else {
                lastRecipeID = null;
            }
        }

        if (checkNewRecipe) {
            List<CookingPotRecipe> recipes = level.getRecipeManager()
                    .getRecipesFor(RecipeTypeRegistry.COOKING_RECIPE_TYPE.get(), inventoryWrapper, level);
            for (CookingPotRecipe recipe : recipes) {
                if (recipe.matchesWithFluid(
                        this.tank.getFluid(),
                        inventoryWrapper, level)) {
                    lastRecipeID = recipe.getId();
                    return Optional.of(recipe);
                }
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    protected boolean canWork(CookingPotRecipe recipe, Level level) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem(level.registryAccess());
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(outputStack, resultStack)) {
                    return false;
                } else if (outputStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(OUTPUT_SLOT)) {
                    return true;
                } else {
                    return outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean processRecipe(CookingPotRecipe recipe, Level level) {
        if (level == null) {
            return false;
        }

        ++cookTime;
        maxCookTime = recipe.getRecipeTime();
        if (cookTime < maxCookTime) {
            return false;
        }

        cookTime = 0;

        ItemStack resultStack = recipe.getResultItem(level.registryAccess());
        ItemStack outStack = inventory.getStackInSlot(OUTPUT_SLOT);

        if (outStack.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, resultStack.copy());
        } else if (ItemStack.isSameItem(outStack, resultStack)) {
            outStack.grow(resultStack.getCount());
        }

        if (recipe.getRequiredFluid() != FluidIngredient.EMPTY) {
            this.tank.drain(
                    recipe.getRequiredFluid().getRequiredAmount(), FluidAction.EXECUTE);
        }

        trackRecipeExperience(recipe);

        for (int i = 0; i < INPUT_SLOTS; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack.hasCraftingRemainingItem()) {
                double x = worldPosition.getX() + 0.5;
                double y = worldPosition.getY() + 0.7;
                double z = worldPosition.getZ() + 0.5;
                LevelUtils.spawnItemEntity(level, inventory.getStackInSlot(i).getCraftingRemainingItem(),
                        x, y, z, 0F, 0.25F, 0F);
            }
            if (!slotStack.isEmpty()) {
                slotStack.shrink(1);
            }
        }
        return true;
    }

    public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            experienceTracker.addTo(recipeID, 1);
        }
    }

    public void clearUsedRecipes(Player player) {
        grantStoredRecipeExperience(player.level(), player.position());
        experienceTracker.clear();
    }

    public void grantStoredRecipeExperience(Level world, Vec3 pos) {
        for (Object2IntMap.Entry<ResourceLocation> entry : experienceTracker.object2IntEntrySet()) {
            world.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe ->
                    LevelUtils.splitAndSpawnExperience(world, pos, entry.getIntValue(),
                            ((CookingPotRecipe) recipe).getExperience()));
        }
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public void setBurnTime(int time) {
        this.burnTime = time;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getMaxCookTime() {
        return this.maxCookTime;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public LazyOptional<FluidTank> getFluidTank() {
        return fluidTank;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < TOTAL_SLOTS; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved()) {
            if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
                if (side == null || side.equals(Direction.UP)) {
                    return inputHandler.cast();
                } else {
                    return outputHandler.cast();
                }
            }
            if (cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
                return this.fluidTank.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        burnTime = compound.getInt("BurnTime");
        cookTime = compound.getInt("CookTime");
        maxCookTime = compound.getInt("MaxCookTime");
        tank.readFromNBT(compound.getCompound("Tank"));
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("CookTime", cookTime);
        compound.putInt("MaxCookTime", maxCookTime);
        CompoundTag tankTag = new CompoundTag();
        compound.put("Tank", tank.writeToNBT(tankTag));
        CompoundTag compoundRecipes = new CompoundTag();
        experienceTracker.forEach((recipeId, craftedAmount) ->
                compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
        fluidTank.invalidate();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inputHandler.invalidate();
        outputHandler.invalidate();
        fluidTank.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        inputHandler = LazyOptional.of(() -> inventory);
        outputHandler = LazyOptional.of(() -> inventory);
        fluidTank = LazyOptional.of(() -> this.tank);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(TOTAL_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < INPUT_SLOTS) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot < INPUT_SLOTS;
            }
        };
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(TANK_CAPACITY) {
            @Override
            public void onContentsChanged() {
                checkNewRecipe = true;
                inventoryChanged();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getFluidType().isLighterThanAir();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                case 0:
                    return CampfirePotBlockEntity.this.burnTime;
                case 1:
                    return CampfirePotBlockEntity.this.cookTime;
                case 2:
                    return CampfirePotBlockEntity.this.maxCookTime;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    CampfirePotBlockEntity.this.burnTime = value;
                    break;
                case 1:
                    CampfirePotBlockEntity.this.cookTime = value;
                    break;
                case 2:
                    CampfirePotBlockEntity.this.maxCookTime = value;
                    break;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new CampfirePotContainer(id, player, this, this.tileData);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sakura.campfire_pot");
    }
}
