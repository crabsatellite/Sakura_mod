package cn.mcmod.sakura.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import cn.mcmod.sakura.block.CampfirePotBlock;
import cn.mcmod.sakura.container.CampfirePotContainer;
import cn.mcmod.sakura.recipes.CookingPotRecipe;
import cn.mcmod.sakura.recipes.RecipeTypeRegistry;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import cn.mcmod.sakura.recipes.base.FluidIngredient;
import cn.mcmod_mmf.mmlib.utils.LevelUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    private final FluidTank tank;
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
        this.tank = createFluidHandler();
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
            Optional<RecipeHolder<CookingPotRecipe>> recipeOpt = level.getRecipeManager()
                    .getAllRecipesFor(RecipeTypeRegistry.COOKING_RECIPE_TYPE.get()).stream()
                    .filter(holder -> holder.id().equals(lastRecipeID)).findFirst();
            if (recipeOpt.isPresent()) {
                CookingPotRecipe recipe = recipeOpt.get().value();
                if (recipe.matchesWithFluid(this.tank.getFluid(), inventoryWrapper, level)) {
                    return Optional.of(recipe);
                }
            } else {
                lastRecipeID = null;
            }
        }

        if (checkNewRecipe) {
            List<RecipeHolder<CookingPotRecipe>> recipes = level.getRecipeManager()
                    .getRecipesFor(RecipeTypeRegistry.COOKING_RECIPE_TYPE.get(), inventoryWrapper, level);
            for (RecipeHolder<CookingPotRecipe> holder : recipes) {
                CookingPotRecipe recipe = holder.value();
                if (recipe.matchesWithFluid(this.tank.getFluid(), inventoryWrapper, level)) {
                    lastRecipeID = holder.id();
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

        trackRecipeExperience(lastRecipeID);

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

    public void trackRecipeExperience(@Nullable ResourceLocation recipeId) {
        if (recipeId != null) {
            experienceTracker.addTo(recipeId, 1);
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
                            ((CookingPotRecipe) recipe.value()).getExperience()));
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

    public FluidTank getFluidTank() {
        return tank;
    }


    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < TOTAL_SLOTS; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        burnTime = compound.getInt("BurnTime");
        cookTime = compound.getInt("CookTime");
        maxCookTime = compound.getInt("MaxCookTime");
        tank.readFromNBT(registries, compound.getCompound("Tank"));
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            experienceTracker.put(ResourceLocation.parse(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        compound.putInt("BurnTime", burnTime);
        compound.putInt("CookTime", cookTime);
        compound.putInt("MaxCookTime", maxCookTime);
        CompoundTag tankTag = new CompoundTag();
        compound.put("Tank", tank.writeToNBT(registries, tankTag));
        CompoundTag compoundRecipes = new CompoundTag();
        experienceTracker.forEach((recipeId, craftedAmount) ->
                compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
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
