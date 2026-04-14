package cn.mcmod.sakura.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import cn.mcmod.sakura.item.ItemRegistry;

import javax.annotation.Nullable;

public class SamuraiIllagerEntity extends AbstractIllager {
    public SamuraiIllagerEntity(EntityType<? extends SamuraiIllagerEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isAggressive()) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        } else {
            return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        RandomSource randomsource = level.getRandom();
        populateDefaultEquipmentSlots(randomsource, difficulty);
        populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        return spawnData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        if (this.getCurrentRaid() == null) {
            // 20% chance for tachi (scaled by difficulty), otherwise katana
            float additionalDifficulty = difficulty.getSpecialMultiplier();
            if (random.nextFloat() < additionalDifficulty * 0.2F) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.TACHI.get()));
            } else {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.KATANA.get()));
            }
        }
    }

    @Override
    protected void populateDefaultEquipmentEnchantments(ServerLevelAccessor level, RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentEnchantments(level, random, difficulty);
        // Additional enchantment chance based on difficulty (matching vanilla vindicator logic)
        if (random.nextInt(3 + difficulty.getDifficulty().getId()) > 3) {
            ItemStack mainhand = this.getMainHandItem();
            // In 1.21, enchantItem requires RegistryAccess; use MOB_SPAWN_EQUIPMENT provider as a reasonable default
            EnchantmentHelper.enchantItemFromProvider(
                mainhand, level.registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, difficulty, random
            );
        }
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // During raids, always equip tachi with enchantment scaling by wave
        ItemStack weapon = new ItemStack(ItemRegistry.TACHI.get());
        Raid raid = this.getCurrentRaid();
        if (raid != null) {
            boolean shouldEnchant = this.random.nextFloat() <= raid.getEnchantOdds();
            if (shouldEnchant) {
                // Use vindicator enchantment providers which scale by wave (matching vanilla behavior)
                net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.providers.EnchantmentProvider> resourcekey =
                    wave > raid.getNumGroups(Difficulty.NORMAL)
                        ? VanillaEnchantmentProviders.RAID_VINDICATOR_POST_WAVE_5
                        : VanillaEnchantmentProviders.RAID_VINDICATOR;
                EnchantmentHelper.enchantItemFromProvider(
                    weapon, level.registryAccess(), resourcekey, level.getCurrentDifficultyAt(this.blockPosition()), this.random
                );
            }
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        // Sync aggressive state for client-side animation: aggressive when has an attack target
        this.setAggressive(this.getTarget() != null);
    }

    /**
     * Treat other illager-type mobs as allies so this entity doesn't attack them.
     * Note: AbstractIllager already handles this via EntityTypeTags.ILLAGER_FRIENDS in 1.21.
     * This override is kept for clarity but delegates to super which uses the tag-based system.
     */
    @Override
    public boolean isAlliedTo(Entity other) {
        if (super.isAlliedTo(other)) {
            return true;
        }
        if (other.getType().is(EntityTypeTags.ILLAGER_FRIENDS)) {
            return this.getTeam() == null && other.getTeam() == null;
        }
        return false;
    }

    // --- Sound overrides matching 1.12.2 vindicator sounds ---

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VINDICATOR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.VINDICATOR_HURT;
    }
}
