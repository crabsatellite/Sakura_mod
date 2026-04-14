package cn.mcmod.sakura.item;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.client.renderer.SamuraiArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SamuraiArmorItem extends ArmorItem {

    private final boolean isSoldier;

    public SamuraiArmorItem(SakuraArmorMaterials material, Type type, Properties properties, boolean isSoldier) {
        super(material, type, properties);
        this.isSoldier = isSoldier;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String materialName = ((SakuraArmorMaterials) getMaterial()).getTextureName();
        return SakuraMod.MODID + ":textures/models/armor/" + materialName + ".png";
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
