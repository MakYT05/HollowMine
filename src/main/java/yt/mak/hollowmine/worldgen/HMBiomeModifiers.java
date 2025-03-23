package yt.mak.hollowmine.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.entity.HMEntities;

import java.util.List;

public class HMBiomeModifiers{
    public static final ResourceKey<BiomeModifier> ADD_HOLLOW_ORE = registerKey("add_hollow_ore");

    public static final ResourceKey<BiomeModifier> SPAWN_HOLLOW_TRADER = registerKey("spawn_hollow_trader");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(SPAWN_HOLLOW_TRADER, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.PLAINS)),
                List.of(new MobSpawnSettings.SpawnerData(HMEntities.HOLLOW_TRADER.get(), 25, 3, 5))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, name));
    }
}