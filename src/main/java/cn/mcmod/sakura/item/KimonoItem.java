package cn.mcmod.sakura.item;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

/**
 * Kimono armor item with support for pattern-specific armor textures.
 *
 * In 1.12.2, kimono patterns were stored as NBT data on a single item.
 * In 1.20.1, each pattern is a separate registered item, and the armor
 * texture is determined by the pattern name passed to the constructor.
 *
 * Armor texture files are located at:
 *   assets/sakura/textures/block/models/armor/{patternName}.png
 *
 * The vanilla armor texture resolution path uses:
 *   assets/{namespace}/textures/models/armor/{material}_layer_{layer}.png
 * We override getArmorTexture() to point to our custom texture paths.
 */
public class KimonoItem extends ArmorItem {

    private final String patternName;

    /**
     * Creates a plain kimono/haori with default texture (uses armor material name).
     */
    public KimonoItem(ArmorMaterial material, Type type, Properties properties) {
        this(material, type, properties, null);
    }

    /**
     * Creates a kimono/haori with a specific pattern texture.
     *
     * @param patternName the pattern texture name (e.g. "kimono_1", "haori_2", "yukata_0", "kimono_miko").
     *                    Must correspond to a texture file at textures/block/models/armor/{patternName}.png
     */
    public KimonoItem(ArmorMaterial material, Type type, Properties properties, @Nullable String patternName) {
        super(material, type, properties);
        this.patternName = patternName;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, net.minecraft.world.entity.EquipmentSlot slot, String type) {
        if (this.patternName != null) {
            // Pattern-specific armor texture
            return new ResourceLocation(SakuraMod.MODID,
                    "textures/block/models/armor/" + this.patternName + ".png").toString();
        }
        // Default: use the base kimono armor material texture path
        // This falls through to vanilla resolution: sakura:textures/models/armor/kimono_layer_1.png / _layer_2.png
        return null;
    }
}
