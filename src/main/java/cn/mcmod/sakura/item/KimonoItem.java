package cn.mcmod.sakura.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.renderer.KimonoArmorRenderer;

import java.util.function.Consumer;
import javax.annotation.Nullable;

/**
 * Kimono armor item with support for pattern-specific armor textures.
 *
 * In 1.12.2, kimono patterns were stored as NBT data on a single item.
 * In 1.20.1, each pattern is a separate registered item, and the armor
 * texture is determined by the pattern name passed to the constructor.
 *
 * Armor texture files are located at:
 *   assets/sakura/textures/models/armor/{patternName}.png
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
    public KimonoItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        this(material, type, properties, null);
    }

    /**
     * Creates a kimono/haori with a specific pattern texture.
     *
     * @param patternName the pattern texture name (e.g. "kimono_1", "haori_2", "yukata_0", "kimono_miko").
     *                    Must correspond to a texture file at textures/models/armor/{patternName}.png
     */
    public KimonoItem(Holder<ArmorMaterial> material, Type type, Properties properties, @Nullable String patternName) {
        super(material, type, properties);
        this.patternName = patternName;
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        String texName = this.patternName != null ? this.patternName : "kimono_base";
        return ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "textures/models/armor/" + texName + ".png");
    }

    /**
     * Gets the texture name for this kimono pattern.
     * Used by the custom renderer to select the appropriate texture.
     */
    public String getTextureName() {
        return patternName != null ? patternName : "kimono_base";
    }

    /**
     * Initialize client-side extensions for custom armor rendering.
     * This enables the custom kimono model instead of the default armor model.
     */
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel<?> model = KimonoArmorRenderer.getModel(getTextureName());

                // Copy pose from the original model for correct animations
                KimonoArmorRenderer.copyModelPose(original, model);

                // Update visibility based on the equipment slot
                KimonoArmorRenderer.updateModelForSlot(model, equipmentSlot);

                return model;
            }
        });
    }
}
