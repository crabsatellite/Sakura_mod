package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.item.ItemRegistry;
import cn.mcmod.sakura.item.enums.SakuraNormalItemSet;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import net.minecraft.core.RegistryAccess;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Reflection on Bug 2: IronChainRecipeTest only checks that individual recipes
 * EXIST; it cannot catch a broken chain where some intermediate item has no
 * consumer (like zuku_ingot had before the fix). This test walks the recipe
 * graph and asserts end-to-end reachability from Tatara-produced raw materials
 * to iron_ingot. Any future break in the chain — a deleted recipe, a renamed
 * ingredient, an orphan intermediate — will make iron_ingot unreachable and
 * fail this test, regardless of which specific link was severed.
 *
 * Catches the whole class of "orphan material / broken progression chain" bugs.
 */
@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class IronChainReachabilityTest {

    private static Set<Item> computeReachable(Set<Item> seed, RecipeManager rm, RegistryAccess ra) {
        Set<Item> reachable = new HashSet<>(seed);
        Collection<Recipe<?>> all = rm.getRecipes();
        int prev = -1;
        while (reachable.size() != prev) {
            prev = reachable.size();
            for (Recipe<?> recipe : all) {
                if (recipe.getIngredients().isEmpty()) continue;
                if (!allIngredientsSatisfied(recipe, reachable)) continue;
                ItemStack result = recipe.getResultItem(ra);
                if (!result.isEmpty()) reachable.add(result.getItem());
            }
        }
        return reachable;
    }

    private static boolean allIngredientsSatisfied(Recipe<?> recipe, Set<Item> reachable) {
        for (Ingredient ing : recipe.getIngredients()) {
            if (ing.isEmpty()) continue;
            boolean satisfied = false;
            for (ItemStack candidate : ing.getItems()) {
                if (reachable.contains(candidate.getItem())) {
                    satisfied = true;
                    break;
                }
            }
            if (!satisfied) return false;
        }
        return true;
    }

    private static Item material(SakuraNormalItemSet which) {
        return ItemRegistry.MATERIALS.get(which).get();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void iron_ingot_reachable_from_zuku(GameTestHelper helper) {
        Set<Item> seed = new HashSet<>();
        seed.add(material(SakuraNormalItemSet.ZUKU));
        seed.add(material(SakuraNormalItemSet.TAMAHAGANE));
        Set<Item> reachable = computeReachable(seed,
                helper.getLevel().getRecipeManager(),
                helper.getLevel().registryAccess());
        SakuraTestBase.assertTrue(helper, reachable.contains(Items.IRON_INGOT),
                "iron_ingot unreachable starting from Tatara default drops (zuku/tamahagane); "
                        + "the progression chain is broken. Reachable size=" + reachable.size());
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void iron_ingot_reachable_from_zuku_ingot(GameTestHelper helper) {
        Set<Item> seed = new HashSet<>();
        seed.add(material(SakuraNormalItemSet.ZUKU_INGOT));
        Set<Item> reachable = computeReachable(seed,
                helper.getLevel().getRecipeManager(),
                helper.getLevel().registryAccess());
        SakuraTestBase.assertTrue(helper, reachable.contains(Items.IRON_INGOT),
                "iron_ingot unreachable from zuku_ingot; zuku_ingot must have at least one consumer "
                        + "leading down the iron chain. Reachable=" + reachable.size());
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void iron_ingot_reachable_from_sagegane(GameTestHelper helper) {
        Set<Item> seed = new HashSet<>();
        seed.add(material(SakuraNormalItemSet.SAGEGANE));
        Set<Item> reachable = computeReachable(seed,
                helper.getLevel().getRecipeManager(),
                helper.getLevel().registryAccess());
        SakuraTestBase.assertTrue(helper, reachable.contains(Items.IRON_INGOT),
                "iron_ingot unreachable from sagegane; sagegane must reach iron via forging.");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void every_iron_chain_intermediate_is_both_produced_and_consumed(GameTestHelper helper) {
        // Each item listed here must have AT LEAST one recipe producing it AND one consuming it.
        // An orphan (produced but never consumed, or consumed but never produced) means the
        // progression chain has a dead link.
        SakuraNormalItemSet[] intermediates = new SakuraNormalItemSet[]{
                SakuraNormalItemSet.ZUKU_INGOT,
                SakuraNormalItemSet.SAGEGANE,
                SakuraNormalItemSet.TAMAHAGANE,
        };
        RecipeManager rm = helper.getLevel().getRecipeManager();
        RegistryAccess ra = helper.getLevel().registryAccess();
        Collection<Recipe<?>> all = rm.getRecipes();
        for (SakuraNormalItemSet which : intermediates) {
            Item item = material(which);
            boolean produced = false, consumed = false;
            for (Recipe<?> recipe : all) {
                if (!produced) {
                    ItemStack result = recipe.getResultItem(ra);
                    if (!result.isEmpty() && result.is(item)) produced = true;
                }
                if (!consumed) {
                    for (Ingredient ing : recipe.getIngredients()) {
                        if (ing.isEmpty()) continue;
                        for (ItemStack cand : ing.getItems()) {
                            if (cand.is(item)) { consumed = true; break; }
                        }
                        if (consumed) break;
                    }
                }
                if (produced && consumed) break;
            }
            SakuraTestBase.assertTrue(helper, produced,
                    which.name() + " is in the iron chain but has NO recipe producer (orphan).");
            SakuraTestBase.assertTrue(helper, consumed,
                    which.name() + " is in the iron chain but has NO recipe consumer (dead-end).");
        }
        helper.succeed();
    }
}
