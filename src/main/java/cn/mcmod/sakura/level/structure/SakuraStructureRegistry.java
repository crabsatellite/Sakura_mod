package cn.mcmod.sakura.level.structure;

import cn.mcmod.sakura.SakuraMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registry for custom structure-related types used by Sakura.
 *
 * <p>This registers the {@link JapaneseHouseElement} type so the Jigsaw/Structure Pool
 * system can serialize and deserialize our procedurally-generated village building.</p>
 */
public class SakuraStructureRegistry {

    public static final DeferredRegister<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENT_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_POOL_ELEMENT, SakuraMod.MODID);

    public static final RegistryObject<StructurePoolElementType<JapaneseHouseElement>> JAPANESE_HOUSE =
            STRUCTURE_POOL_ELEMENT_TYPES.register("japanese_house",
                    () -> () -> JapaneseHouseElement.CODEC);
}
