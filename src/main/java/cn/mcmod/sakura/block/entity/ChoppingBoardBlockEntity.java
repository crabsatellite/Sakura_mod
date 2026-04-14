package cn.mcmod.sakura.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import cn.mcmod.sakura.block.machines.ChoppingBoardBlock;
import cn.mcmod.sakura.recipes.ChoppingRecipe;
import cn.mcmod.sakura.recipes.RecipeTypeRegistry;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import cn.mcmod_mmf.mmlib.utils.LevelUtils;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChoppingBoardBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandler inventory;
    private ResourceLocation lastRecipeID;
    
    private int recipeTime;
    private int recipeTimeTotal;

    public ChoppingBoardBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CHOPPING_BOARD.get(), pos, state);
        inventory = createHandler();
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
        recipeTimeTotal = compound.getInt("RecipeTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        compound.putInt("RecipeTime", this.recipeTime);
        compound.putInt("RecipeTimeTotal", this.recipeTimeTotal);
    }
    
    public int getRecipeTime() {
        return recipeTime;
    }

    public boolean processStoredItemUsingTool(ItemStack toolStack, @Nullable Player player) {
        if (level == null)
            return false;

        Optional<ChoppingRecipe> matchingRecipe = getMatchingRecipe(new RecipeWrapper(inventory), toolStack, player);

        matchingRecipe.ifPresent(recipe -> {
            this.recipeTimeTotal = recipe.getRecipeTime();
            
            int fortuneLevel = level.registryAccess().lookup(net.minecraft.core.registries.Registries.ENCHANTMENT)
                    .flatMap(reg -> reg.get(Enchantments.FORTUNE))
                    .map(holder -> EnchantmentHelper.getItemEnchantmentLevel(holder, toolStack))
                    .orElse(0);
            List<ItemStack> results = recipe.rollByproducts(level.random, fortuneLevel);
            for (ItemStack resultStack : results) {
                Direction direction = getBlockState().getValue(ChoppingBoardBlock.FACING).getCounterClockWise();
                LevelUtils.spawnItemEntity(level, resultStack.copy(),
                        worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2,
                        worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2), direction.getStepX() * 0.2F, 0.0F,
                        direction.getStepZ() * 0.2F);
            }
            if (player != null) {
                toolStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            } else {
                // In 1.21, ItemStack.hurt(int, RandomSource, ServerPlayer) was removed
                // Use hurtAndBreak with a null-safe approach
                toolStack.setDamageValue(toolStack.getDamageValue() + 1);
                if (toolStack.getDamageValue() >= toolStack.getMaxDamage()) {
                    toolStack.setCount(0);
                }
            }
            playProcessingSound(toolStack, getStoredItem());
            if(this.recipeTime < recipeTimeTotal - 1) {
                this.recipeTime++;
            } else {
                if(!setResult(recipe))
                    removeItem();
            }
        });

        return matchingRecipe.isPresent();
    }

    private Optional<ChoppingRecipe> getMatchingRecipe(RecipeWrapper recipeWrapper, ItemStack toolStack,
            @Nullable Player player) {
        if (level == null)
            return Optional.empty();

        if (lastRecipeID != null) {
            Optional<RecipeHolder<ChoppingRecipe>> recipeOpt = level.getRecipeManager()
                    .getAllRecipesFor(RecipeTypeRegistry.CHOPPING_RECIPE_TYPE.get()).stream()
                    .filter(holder -> holder.id().equals(lastRecipeID)).findFirst();
            if (recipeOpt.isPresent()) {
                ChoppingRecipe recipe = recipeOpt.get().value();
                if (recipe.matches(recipeWrapper, level) && recipe.getTool().test(toolStack)) {
                    return Optional.of(recipe);
                }
            } else {
                lastRecipeID = null;
            }
        }

        List<RecipeHolder<ChoppingRecipe>> recipeList = level.getRecipeManager()
                .getRecipesFor(RecipeTypeRegistry.CHOPPING_RECIPE_TYPE.get(), recipeWrapper, level);
        if (recipeList.isEmpty()) {
            if (player != null)
                player.displayClientMessage(Component.translatable("sakura.block.chopping_board.invalid_item"), true);
            return Optional.empty();
        }
        Optional<ChoppingRecipe> recipe = recipeList.stream()
                .map(RecipeHolder::value)
                .filter(cuttingRecipe -> cuttingRecipe.getTool().test(toolStack)).findFirst();
        if (!recipe.isPresent()) {
            if (player != null)
                player.displayClientMessage(Component.translatable("sakura.block.chopping_board.invalid_tool"), true);
            return Optional.empty();
        }
        // Find the holder to get the ID
        lastRecipeID = recipeList.stream()
                .filter(h -> h.value() == recipe.get()).findFirst()
                .map(RecipeHolder::id).orElse(null);
        return recipe;
    }

    public void playProcessingSound(ItemStack tool, ItemStack boardItem) {
        if (tool.is(net.minecraft.world.item.Items.SHEARS)) {
            playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
        } else if (boardItem.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            SoundType soundType = block.defaultBlockState().getSoundType();
            playSound(soundType.getBreakSound(), 1.0F, 0.8F);
        } else {
            playSound(SoundEvents.WOOD_HIT, 1.0F, 0.8F);
        }
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        if (level != null)
            level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F,
                    sound, SoundSource.BLOCKS, volume, pitch);
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }
    
    public boolean setResult(ChoppingRecipe recipe) {
        ItemStack resultItem = recipe.getResultItem(level.registryAccess());
        if (!resultItem.isEmpty()) {
            if(resultItem.getCount() > 1) {
                for(int i=1;i < resultItem.getCount(); i++) {
                    Direction direction = getBlockState().getValue(ChoppingBoardBlock.FACING).getCounterClockWise();
                    LevelUtils.spawnItemEntity(level, resultItem.copy().split(1),
                            worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2,
                            worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2), direction.getStepX() * 0.2F, 0.0F,
                            direction.getStepZ() * 0.2F);
                }
            }
            inventory.setStackInSlot(0, resultItem.copy().split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    public ItemStack getStoredItem() {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    protected void inventoryChanged() {
        this.recipeTime = 0;
        super.inventoryChanged();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler() {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }
}
