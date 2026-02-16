package cn.mcmod.sakura.item;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum SakuraArmorMaterials implements ArmorMaterial {
    // Protection array order: [BOOTS, LEGS, CHEST, HEAD] (indexed by EquipmentSlot ordinal)
    STRAW("straw", "strawhat", 6, new int[]{0, 0, 0, 1}, 30,
            SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY),
    SAMURAI("samurai", "samurai_armor", 33, new int[]{5, 8, 9, 5}, 20,
            SoundEvents.ARMOR_EQUIP_IRON, 3.5F, 0.0F, () -> Ingredient.EMPTY),
    SOLDIER("soldier", "soldier_armor", 16, new int[]{2, 5, 6, 2}, 14,
            SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.0F, () -> Ingredient.of(Items.IRON_INGOT)),
    KIMONO("kimono", "kimono_base", 1, new int[]{0, 0, 0, 0}, 0,
            SoundEvents.WOOL_PLACE, 0.0F, 0.0F, () -> Ingredient.EMPTY);

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final String textureName;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    SakuraArmorMaterials(String name, String textureName, int durabilityMult, int[] protections, int enchant,
                         SoundEvent sound, float tough, float kb, Supplier<Ingredient> repair) {
        this.name = name;
        this.textureName = textureName;
        this.durabilityMultiplier = durabilityMult;
        this.slotProtections = protections;
        this.enchantmentValue = enchant;
        this.sound = sound;
        this.toughness = tough;
        this.knockbackResistance = kb;
        this.repairIngredient = repair;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_PER_SLOT[type.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.slotProtections[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return SakuraMod.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    /**
     * Returns the plain texture name for this armor material (without namespace).
     * Used for armor texture resolution (e.g., "samurai", "soldier", "straw", "kimono").
     */
    public String getTextureName() {
        return this.textureName;
    }
}
