package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.compat.jei.SakuraJeiInfo;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class JeiIntegrationTest {

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void tatara_has_jei_information_entry(GameTestHelper helper) {
        SakuraTestBase.assertEquals(helper, "jei_plugin", SakuraJeiInfo.PLUGIN_ID.getPath(),
                "JEI plugin id should stay stable");
        SakuraTestBase.assertEquals(helper, "jei.sakura.tatara.description", SakuraJeiInfo.TATARA_INFO_KEY,
                "Tatara JEI translation key should stay stable");
        ItemStack stack = SakuraJeiInfo.tataraInfoStack();
        SakuraTestBase.assertFalse(helper, stack.isEmpty(),
                "Tatara JEI info stack should not be empty");
        SakuraTestBase.assertTrue(helper, stack.is(BlockRegistry.TATARA.get().asItem()),
                "Tatara JEI info should be attached to the Tatara block item");
        helper.succeed();
    }
}
