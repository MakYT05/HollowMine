package yt.mak.hollowmine.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.entity.HMEntities;

import java.util.List;

public class HMBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_HOLLOW_ORE = registerKey("add_hollow_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_HOLLOW_ORE = registerKey("add_nether_hollow_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_HOLLOW_ORE = registerKey("add_end_hollow_ore");
    public static final ResourceKey<BiomeModifier> ADD_WALNUT_TREE = registerKey("add_tree_walnut");
    public static final ResourceKey<BiomeModifier> SPAWN_HOLLOW_FLY = registerKey("spawn_hollow_fly");
    public static final ResourceKey<BiomeModifier> SPAWN_HOLLOW_BEATLE = registerKey("spawn_hollow_beatle");
    public static final ResourceKey<BiomeModifier> SPAWN_HOLLOW_POISON = registerKey("spawn_hollow_poison");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_HOLLOW_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(HMPlacedFeatures.HOLLOW_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_HOLLOW_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(HMPlacedFeatures.NETHER_HOLLOW_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_HOLLOW_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(HMPlacedFeatures.END_HOLLOW_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_WALNUT_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
                HolderSet.direct(placedFeature.getOrThrow(HMPlacedFeatures.WALNUT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(SPAWN_HOLLOW_FLY, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BAMBOO_JUNGLE), biomes.getOrThrow(Biomes.PLAINS)),
                List.of(new MobSpawnSettings.SpawnerData(HMEntities.HOLLOW_FLY.get(), 25, 3, 5))));

        context.register(SPAWN_HOLLOW_BEATLE, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BEACH), biomes.getOrThrow(Biomes.SAVANNA)),
                List.of(new MobSpawnSettings.SpawnerData(HMEntities.HOLLOW_FLY.get(), 25, 3, 5))));

        context.register(SPAWN_HOLLOW_POISON, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.ICE_SPIKES), biomes.getOrThrow(Biomes.SNOWY_TAIGA)),
                List.of(new MobSpawnSettings.SpawnerData(HMEntities.HOLLOW_FLY.get(), 25, 3, 5))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, name));
    }
}