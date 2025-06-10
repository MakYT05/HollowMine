package yt.mak.hollowmine.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.worldgen.HMBiomeModifiers;
import yt.mak.hollowmine.worldgen.HMConfiguredFeatures;
import yt.mak.hollowmine.worldgen.HMPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HMDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, HMConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, HMPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, HMBiomeModifiers::bootstrap);

    public HMDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(HollowMine.MODID));
    }
}