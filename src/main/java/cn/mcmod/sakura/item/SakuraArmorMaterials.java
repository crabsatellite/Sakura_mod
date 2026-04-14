package cn.mcmod.sakura.item;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Armor materials for Sakura mod, registered via DeferredRegister for 1.21.1 NeoForge.
 * <p>
 * In 1.21.1, ArmorMaterial is a record and must be registered to the ARMOR_MATERIAL registry.
 * ArmorItem takes Holder&lt;ArmorMaterial&gt; instead of ArmorMaterial directly.
 * DeferredHolder implements Holder, so these fields can be passed directly to ArmorItem constructors.
 */
public class SakuraArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, SakuraMod.MODID);

    /**
     * Map from Holder to texture name, used by SamuraiArmorItem to resolve armor textures.
     * Populated during static init when each material is registered.
     */
    private static final Map<Holder<ArmorMaterial>, String> TEXTURE_NAMES = new HashMap<>();

    // Old protection array order: [BOOTS, LEGS, CHEST, HEAD]
    // New EnumMap order: BOOTS, LEGGINGS, CHESTPLATE, HELMET

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STRAW =
            registerWithTexture("straw", "strawhat",
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0);
                        map.put(ArmorItem.Type.LEGGINGS, 0);
                        map.put(ArmorItem.Type.CHESTPLATE, 0);
                        map.put(ArmorItem.Type.HELMET, 1);
                    }),
                    30, // enchantment value
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.EMPTY,
                    0.0F, // toughness
                    0.0F  // knockback resistance
            );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SAMURAI =
            registerWithTexture("samurai", "samurai_armor",
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 5);
                        map.put(ArmorItem.Type.LEGGINGS, 8);
                        map.put(ArmorItem.Type.CHESTPLATE, 9);
                        map.put(ArmorItem.Type.HELMET, 5);
                    }),
                    20,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.EMPTY,
                    3.5F,
                    0.0F
            );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SOLDIER =
            registerWithTexture("soldier", "soldier_armor",
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.LEGGINGS, 5);
                        map.put(ArmorItem.Type.CHESTPLATE, 6);
                        map.put(ArmorItem.Type.HELMET, 2);
                    }),
                    14,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.of(Items.IRON_INGOT),
                    0.5F,
                    0.0F
            );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KIMONO =
            registerWithTexture("kimono", "kimono_base",
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 0);
                        map.put(ArmorItem.Type.LEGGINGS, 0);
                        map.put(ArmorItem.Type.CHESTPLATE, 0);
                        map.put(ArmorItem.Type.HELMET, 0);
                    }),
                    0,
                    // WOOL_PLACE is a SoundEvent, not Holder<SoundEvent>; wrap it
                    Holder.direct(SoundEvents.WOOL_PLACE),
                    () -> Ingredient.EMPTY,
                    0.0F,
                    0.0F
            );

    /**
     * Returns the texture name for a given armor material holder.
     * Used by SamuraiArmorItem and similar classes to resolve custom armor texture paths.
     *
     * @param holder the armor material holder
     * @return the texture name (e.g. "samurai_armor", "strawhat"), or null if not a Sakura material
     */
    public static String getTextureName(Holder<ArmorMaterial> holder) {
        return TEXTURE_NAMES.get(holder);
    }

    /**
     * Registers an armor material and records its texture name in the lookup map.
     */
    private static DeferredHolder<ArmorMaterial, ArmorMaterial> registerWithTexture(
            String name, String textureName,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<net.minecraft.sounds.SoundEvent> equipSound,
            java.util.function.Supplier<Ingredient> repairIngredient,
            float toughness,
            float knockbackResistance
    ) {
        DeferredHolder<ArmorMaterial, ArmorMaterial> holder = ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                defense,
                enchantmentValue,
                equipSound,
                repairIngredient,
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, textureName))),
                toughness,
                knockbackResistance
        ));
        TEXTURE_NAMES.put(holder, textureName);
        return holder;
    }

    private SakuraArmorMaterials() {
        // Utility class, no instantiation
    }
}
