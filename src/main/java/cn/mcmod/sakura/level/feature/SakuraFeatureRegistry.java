package cn.mcmod.sakura.level.feature;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.level.tree.decorator.ChestnutBurrDecorator;
import cn.mcmod.sakura.level.tree.decorator.FallenLeavesDecorator;
import cn.mcmod.sakura.level.tree.decorator.MapleSapLogDecorator;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registry for custom Features and TreeDecoratorTypes used by Sakura world generation.
 */
public class SakuraFeatureRegistry {

    // ===== Features =====
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(
            ForgeRegistries.FEATURES, SakuraMod.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HOT_SPRING_FEATURE = FEATURES.register(
            "hot_spring_feature", () -> new HotSpringFeature(NoneFeatureConfiguration.CODEC));

    // ===== Tree Decorator Types =====
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(
            Registries.TREE_DECORATOR_TYPE, SakuraMod.MODID);

    public static final RegistryObject<TreeDecoratorType<FallenLeavesDecorator>> FALLEN_LEAVES_DECORATOR = TREE_DECORATOR_TYPES.register(
            "fallen_leaves", () -> new TreeDecoratorType<>(FallenLeavesDecorator.CODEC));

    public static final RegistryObject<TreeDecoratorType<MapleSapLogDecorator>> MAPLE_SAP_LOG_DECORATOR = TREE_DECORATOR_TYPES.register(
            "maple_sap_log", () -> new TreeDecoratorType<>(MapleSapLogDecorator.CODEC));

    public static final RegistryObject<TreeDecoratorType<ChestnutBurrDecorator>> CHESTNUT_BURR_DECORATOR = TREE_DECORATOR_TYPES.register(
            "chestnut_burr", () -> new TreeDecoratorType<>(ChestnutBurrDecorator.CODEC));
}
