package yt.mak.hollowmine.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.blocks.HMBlocks;

import java.util.List;

public class HMPlacedFeatures {
    public static final ResourceKey<PlacedFeature> HOLLOW_ORE_PLACED_KEY = registerKey("hollow_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HOLLOW_ORE_PLACED_KEY = registerKey("nether_hollow_ore_placed");
    public static final ResourceKey<PlacedFeature> END_HOLLOW_ORE_PLACED_KEY = registerKey("end_hollow_ore_placed");
    public static final ResourceKey<PlacedFeature> WALNUT_PLACED_KEY = registerKey("walnut_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, HOLLOW_ORE_PLACED_KEY, configuredFeatures.getOrThrow(HMConfiguredFeatures.OVERWORLD_HOLLOW_ORE_KEY),
                HMOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NETHER_HOLLOW_ORE_PLACED_KEY, configuredFeatures.getOrThrow(HMConfiguredFeatures.NETHER_HOLLOW_ORE_KEY),
                HMOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_HOLLOW_ORE_PLACED_KEY, configuredFeatures.getOrThrow(HMConfiguredFeatures.END_HOLLOW_ORE_KEY),
                HMOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, WALNUT_PLACED_KEY, configuredFeatures.getOrThrow(HMConfiguredFeatures.WALNUT_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25f, 1),
                        Blocks.GRASS_BLOCK));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}