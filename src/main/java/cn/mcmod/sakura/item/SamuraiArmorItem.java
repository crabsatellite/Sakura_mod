package cn.mcmod.sakura.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.renderer.SamuraiArmorRenderer;

import java.util.function.Consumer;
import javax.annotation.Nullable;

public class SamuraiArmorItem extends ArmorItem {

    private final boolean isSoldier;

    public SamuraiArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, boolean isSoldier) {
        super(material, type, properties);
        this.isSoldier = isSoldier;
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        String materialName = SakuraArmorMaterials.getTextureName(this.material);
        return ResourceLocation.fromNamespaceAndPath(SakuraMod.MODID, "textures/models/armor/" + materialName + ".png");
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel<?> model = SamuraiArmorRenderer.getModel(isSoldier);

                SamuraiArmorRenderer.copyModelPose(original, model);
                SamuraiArmorRenderer.updateModelForSlot(model, equipmentSlot);

                return model;
            }
        });
    }
}
