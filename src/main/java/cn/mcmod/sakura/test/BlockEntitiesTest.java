package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.block.BlockRegistry;
import cn.mcmod.sakura.block.entity.BarrelOutputBlockEntity;
import cn.mcmod.sakura.block.entity.CampfireBlockEntity;
import cn.mcmod.sakura.block.entity.CampfirePotBlockEntity;
import cn.mcmod.sakura.block.entity.ChoppingBoardBlockEntity;
import cn.mcmod.sakura.block.entity.CookingPotBlockEntity;
import cn.mcmod.sakura.block.entity.DistillerBlockEntity;
import cn.mcmod.sakura.block.entity.FermenterBlockEntity;
import cn.mcmod.sakura.block.entity.MapleCauldronBlockEntity;
import cn.mcmod.sakura.block.entity.StoneMortarBlockEntity;
import cn.mcmod.sakura.block.entity.StrawWebBlockEntity;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class BlockEntitiesTest {

    private static final BlockPos POS = new BlockPos(1, 1, 1);

    private static BlockEntity placeAndGetBE(GameTestHelper helper, Block block) {
        helper.setBlock(POS, block.defaultBlockState());
        return helper.getBlockEntity(POS);
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_idle_creates_campfire_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.CAMPFIRE_IDLE.get());
        SakuraTestBase.assertNotNull(helper, be, "CAMPFIRE_IDLE must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof CampfireBlockEntity,
                "CAMPFIRE_IDLE BE must be CampfireBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_lit_creates_campfire_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.CAMPFIRE_LIT.get());
        SakuraTestBase.assertNotNull(helper, be, "CAMPFIRE_LIT must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof CampfireBlockEntity,
                "CAMPFIRE_LIT BE must be CampfireBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_idle_creates_pot_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.CAMPFIRE_POT_IDLE.get());
        SakuraTestBase.assertNotNull(helper, be, "CAMPFIRE_POT_IDLE must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof CampfirePotBlockEntity,
                "CAMPFIRE_POT_IDLE BE must be CampfirePotBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void campfire_pot_lit_creates_pot_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.CAMPFIRE_POT_LIT.get());
        SakuraTestBase.assertNotNull(helper, be, "CAMPFIRE_POT_LIT must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof CampfirePotBlockEntity,
                "CAMPFIRE_POT_LIT BE must be CampfirePotBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void cooking_pot_creates_cooking_pot_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.COOKING_POT.get());
        SakuraTestBase.assertNotNull(helper, be, "COOKING_POT must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof CookingPotBlockEntity,
                "COOKING_POT BE must be CookingPotBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void fermenter_creates_fermenter_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.FERMENTER.get());
        SakuraTestBase.assertNotNull(helper, be, "FERMENTER must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof FermenterBlockEntity,
                "FERMENTER BE must be FermenterBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void distiller_creates_distiller_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.DISTILLER.get());
        SakuraTestBase.assertNotNull(helper, be, "DISTILLER must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof DistillerBlockEntity,
                "DISTILLER BE must be DistillerBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void maple_cauldron_creates_cauldron_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.MAPLE_CAULDRON.get());
        SakuraTestBase.assertNotNull(helper, be, "MAPLE_CAULDRON must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof MapleCauldronBlockEntity,
                "MAPLE_CAULDRON BE must be MapleCauldronBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void stone_mortar_creates_mortar_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.STONE_MORTAR.get());
        SakuraTestBase.assertNotNull(helper, be, "STONE_MORTAR must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof StoneMortarBlockEntity,
                "STONE_MORTAR BE must be StoneMortarBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void chopping_board_creates_board_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.CHOPPING_BOARD.get());
        SakuraTestBase.assertNotNull(helper, be, "CHOPPING_BOARD must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof ChoppingBoardBlockEntity,
                "CHOPPING_BOARD BE must be ChoppingBoardBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void barrel_out_creates_barrel_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.BARREL_OUT.get());
        SakuraTestBase.assertNotNull(helper, be, "BARREL_OUT must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof BarrelOutputBlockEntity,
                "BARREL_OUT BE must be BarrelOutputBlockEntity");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void straw_web_creates_web_block_entity(GameTestHelper helper) {
        BlockEntity be = placeAndGetBE(helper, BlockRegistry.STRAW_WEB.get());
        SakuraTestBase.assertNotNull(helper, be, "STRAW_WEB must create a BlockEntity");
        SakuraTestBase.assertTrue(helper, be instanceof StrawWebBlockEntity,
                "STRAW_WEB BE must be StrawWebBlockEntity");
        helper.succeed();
    }
}
