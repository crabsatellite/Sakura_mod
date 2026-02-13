package cn.mcmod.sakura;


import net.minecraftforge.common.ForgeConfigSpec;

public class SakuraConfig {
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final CommonConfig COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new CommonConfig(builder);
        COMMON_CONFIG = builder.build();
    }

    public static class CommonConfig {
        // World generation weights
        public final ForgeConfigSpec.IntValue vanillaWeight;
        public final ForgeConfigSpec.IntValue pepperWeight;
        public final ForgeConfigSpec.IntValue bambooshotWeight;
        public final ForgeConfigSpec.IntValue umeWeight;
        public final ForgeConfigSpec.IntValue ironSandAmount;
        public final ForgeConfigSpec.IntValue hotspringWeight;

        // Biome generation
        public final ForgeConfigSpec.BooleanValue enableBambooForestBiome;
        public final ForgeConfigSpec.BooleanValue enableMapleForestBiome;

        // Gameplay
        public final ForgeConfigSpec.BooleanValue harderIronRecipe;
        public final ForgeConfigSpec.IntValue harderIronDifficult;
        public final ForgeConfigSpec.BooleanValue everyWhereSakuraDiamond;

        // Display
        public final ForgeConfigSpec.BooleanValue showBambooInCreative;
        public final ForgeConfigSpec.BooleanValue showSakuraInCreative;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("World Generation Settings").push("worldgen");

            vanillaWeight = builder
                    .comment("Changes generate rate of Vanilla. Increase value to gen more Vanilla.")
                    .defineInRange("vanilla_weight", 90, 0, 2000);

            pepperWeight = builder
                    .comment("Changes generate rate of Pepper. Increase value to gen more Pepper.")
                    .defineInRange("pepper_weight", 90, 0, 2000);

            bambooshotWeight = builder
                    .comment("Changes generate rate of BambooShot. Increase value to gen more BambooShot.")
                    .defineInRange("bambooshot_weight", 90, 0, 2000);

            umeWeight = builder
                    .comment("Changes generate rate of Ume. Increase value to gen more Ume.")
                    .defineInRange("ume_weight", 90, 0, 2000);

            ironSandAmount = builder
                    .comment("Changes generate amount of Iron Sand. Increase value to gen more Iron Sand.")
                    .defineInRange("iron_sand_amount", 128, 1, 5120);

            hotspringWeight = builder
                    .comment("Changes generate rate of Hot Spring. Increase value to gen more Hot Spring.")
                    .defineInRange("hotspring_weight", 10, 0, 5000);

            everyWhereSakuraDiamond = builder
                    .comment("Whether to enable spawning Sakura Diamond ore in every biome.")
                    .define("every_where_sakura_diamond", false);

            builder.comment("Biome Generation Settings").push("biomes");

            enableBambooForestBiome = builder
                    .comment("Enable Sakura Bamboo Forest biome generation. Disabled by default since vanilla 1.20 already has bamboo jungle.")
                    .define("enable_bamboo_forest_biome", false);

            enableMapleForestBiome = builder
                    .comment("Enable Sakura Maple Forest biome generation.")
                    .define("enable_maple_forest_biome", true);

            builder.pop();

            builder.pop();

            builder.comment("Gameplay Settings").push("gameplay");

            harderIronRecipe = builder
                    .comment("Whether to enable a more difficult iron ingot recipe.")
                    .define("harder_iron_recipe", false);

            harderIronDifficult = builder
                    .comment("Changes difficulty level of harder iron ingot recipe.")
                    .defineInRange("harder_iron_difficult", 1, 1, 3);

            builder.pop();

            builder.comment("Display Settings").push("display");

            showBambooInCreative = builder
                    .comment("Show Sakura mod bamboo-related blocks in creative tab. Set to false to hide them since vanilla 1.20 has its own bamboo blocks.")
                    .define("show_bamboo_in_creative", true);

            showSakuraInCreative = builder
                    .comment("Show Sakura mod cherry/sakura-related blocks in creative tab. Set to false to hide them since vanilla 1.20 has its own cherry blocks.")
                    .define("show_sakura_in_creative", true);

            builder.pop();
        }
    }
}
