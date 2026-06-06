package cn.mcmod.sakura.test;

import cn.mcmod.sakura.SakuraMod;
import cn.mcmod.sakura.test.util.SakuraTestBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Set;

@GameTestHolder(SakuraMod.MODID)
@PrefixGameTestTemplate(false)
public class ResourceIntegrityTest {
    private static final Set<String> GLOBAL_LOOT_MODIFIERS = Set.of(
            "sakura:fishing",
            "sakura:grass_seeds",
            "sakura:tall_grass_seeds",
            "sakura:fern_seeds",
            "sakura:large_fern_seeds"
    );

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void loot_modifiers_have_scoped_conditions(GameTestHelper helper) {
        JsonObject global = JsonParser.parseString(resourceTextContainingAll(
                "data/neoforge/loot_modifiers/global_loot_modifiers.json", GLOBAL_LOOT_MODIFIERS)).getAsJsonObject();
        JsonArray entries = global.getAsJsonArray("entries");
        SakuraTestBase.assertNotNull(helper, entries, "global_loot_modifiers must have entries");
        SakuraTestBase.assertTrue(helper, entries.size() >= GLOBAL_LOOT_MODIFIERS.size(),
                "global_loot_modifiers must include all Sakura entries");
        String serializedEntries = entries.toString();
        for (String entry : GLOBAL_LOOT_MODIFIERS) {
            SakuraTestBase.assertTrue(helper, serializedEntries.contains(entry),
                    "global_loot_modifiers missing " + entry);
        }

        assertModifierHasCondition(helper, "fishing", "neoforge:loot_table_id", "minecraft:gameplay/fishing");
        assertModifierTargetsBlock(helper, "grass_seeds", "minecraft:short_grass");
        assertModifierTargetsBlock(helper, "tall_grass_seeds", "minecraft:tall_grass");
        assertModifierTargetsBlock(helper, "fern_seeds", "minecraft:fern");
        assertModifierTargetsBlock(helper, "large_fern_seeds", "minecraft:large_fern");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void taiko_model_has_no_missing_texture_references(GameTestHelper helper) {
        String text = resourceText("assets/sakura/models/block/taiko.json");
        SakuraTestBase.assertFalse(helper, text.contains("#missing"),
                "taiko model must not reference Blockbench #missing textures");

        JsonObject model = JsonParser.parseString(text).getAsJsonObject();
        JsonObject textures = model.getAsJsonObject("textures");
        JsonArray elements = model.getAsJsonArray("elements");
        for (JsonElement element : elements) {
            JsonObject faces = element.getAsJsonObject().getAsJsonObject("faces");
            if (faces == null) {
                continue;
            }
            for (String direction : faces.keySet()) {
                JsonObject face = faces.getAsJsonObject(direction);
                String texture = face.get("texture").getAsString();
                if (texture.startsWith("#")) {
                    String key = texture.substring(1);
                    SakuraTestBase.assertTrue(helper, textures.has(key),
                            "taiko face " + direction + " references undefined texture " + texture);
                }
            }
        }
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void terrablender_dependency_is_optional(GameTestHelper helper) {
        String modsToml = resourceTextContaining("META-INF/neoforge.mods.toml", "modId=\"sakura\"");
        String block = dependencyBlock(modsToml, "terrablender");
        SakuraTestBase.assertTrue(helper, block.contains("modId=\"terrablender\""),
                "neoforge.mods.toml should declare TerraBlender compatibility metadata");
        SakuraTestBase.assertTrue(helper, block.contains("type=\"optional\""),
                "TerraBlender must be optional, not a hard dependency");
        SakuraTestBase.assertTrue(helper, block.contains("ordering=\"AFTER\""),
                "Sakura should load after TerraBlender when TerraBlender is present");
        helper.succeed();
    }

    @GameTest(template = SakuraTestBase.EMPTY_TEMPLATE)
    public static void english_lang_has_new_tab_and_jei_keys(GameTestHelper helper) {
        JsonObject lang = json("assets/sakura/lang/en_us.json");
        String[] keys = {
                "itemGroup.sakura.blocks",
                "itemGroup.sakura.items",
                "itemGroup.sakura.foods",
                "itemGroup.sakura.drinks",
                "jei.sakura.tatara.description"
        };
        for (String key : keys) {
            SakuraTestBase.assertTrue(helper, lang.has(key), "en_us.json missing " + key);
            SakuraTestBase.assertFalse(helper, lang.get(key).getAsString().isBlank(),
                    "en_us.json key " + key + " should not be blank");
        }
        helper.succeed();
    }

    private static void assertModifierTargetsBlock(GameTestHelper helper, String name, String blockId) {
        assertModifierHasCondition(helper, name, "block_state_property", blockId);
    }

    private static void assertModifierHasCondition(GameTestHelper helper, String name, String condition, String expectedValue) {
        JsonObject modifier = json("data/sakura/loot_modifiers/" + name + ".json");
        JsonArray conditions = modifier.getAsJsonArray("conditions");
        SakuraTestBase.assertNotNull(helper, conditions, name + " loot modifier must have conditions");
        SakuraTestBase.assertTrue(helper, conditions.size() > 0,
                name + " loot modifier must not apply globally");
        String serialized = conditions.toString();
        SakuraTestBase.assertTrue(helper, serialized.contains("\"condition\":\"" + condition + "\"")
                        || serialized.contains("\"condition\":\"minecraft:" + condition + "\"")
                        || serialized.contains("\"condition\":\"neoforge:" + condition + "\""),
                name + " loot modifier missing condition " + condition);
        SakuraTestBase.assertTrue(helper, serialized.contains(expectedValue),
                name + " loot modifier missing expected scope " + expectedValue);
    }

    private static String dependencyBlock(String text, String modId) {
        int modIndex = text.indexOf("modId=\"" + modId + "\"");
        if (modIndex < 0) {
            return "";
        }
        int blockStart = text.lastIndexOf("[[dependencies.sakura]]", modIndex);
        int nextBlock = text.indexOf("[[dependencies.sakura]]", modIndex + 1);
        if (blockStart < 0) {
            blockStart = modIndex;
        }
        if (nextBlock < 0) {
            nextBlock = text.length();
        }
        return text.substring(blockStart, nextBlock);
    }

    private static JsonObject json(String path) {
        try (InputStream stream = ResourceIntegrityTest.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new GameTestAssertException("Missing resource: " + path);
            }
            return JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (IOException e) {
            throw new GameTestAssertException("Unable to read resource " + path + ": " + e.getMessage());
        }
    }

    private static String resourceText(String path) {
        try (InputStream stream = ResourceIntegrityTest.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new GameTestAssertException("Missing resource: " + path);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GameTestAssertException("Unable to read resource " + path + ": " + e.getMessage());
        }
    }

    private static String resourceTextContaining(String path, String needle) {
        try {
            Enumeration<URL> urls = ResourceIntegrityTest.class.getClassLoader().getResources(path);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                try (InputStream stream = url.openStream()) {
                    String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                    if (text.contains(needle)) {
                        return text;
                    }
                }
            }
        } catch (IOException e) {
            throw new GameTestAssertException("Unable to read resource " + path + ": " + e.getMessage());
        }
        throw new GameTestAssertException("Missing resource " + path + " containing " + needle);
    }

    private static String resourceTextContainingAll(String path, Set<String> needles) {
        try {
            Enumeration<URL> urls = ResourceIntegrityTest.class.getClassLoader().getResources(path);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                try (InputStream stream = url.openStream()) {
                    String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                    if (containsAll(text, needles)) {
                        return text;
                    }
                }
            }

            Path generatedResource = Path.of(System.getProperty("user.dir"))
                    .resolve("../build/resources/main")
                    .resolve(path)
                    .normalize();
            if (Files.isRegularFile(generatedResource)) {
                String text = Files.readString(generatedResource, StandardCharsets.UTF_8);
                if (containsAll(text, needles)) {
                    return text;
                }
            }
        } catch (IOException e) {
            throw new GameTestAssertException("Unable to read resource " + path + ": " + e.getMessage());
        }
        throw new GameTestAssertException("Missing resource " + path + " containing all expected Sakura entries");
    }

    private static boolean containsAll(String text, Set<String> needles) {
        for (String needle : needles) {
            if (!text.contains(needle)) {
                return false;
            }
        }
        return true;
    }
}
