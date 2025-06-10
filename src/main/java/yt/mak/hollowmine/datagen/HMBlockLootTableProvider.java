package yt.mak.hollowmine.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.init.blocks.HMBlocks;
import yt.mak.hollowmine.init.items.HMItems;

import java.util.Set;

public class HMBlockLootTableProvider extends BlockLootSubProvider {
    protected HMBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(HMBlocks.HOLLOW_ORE.get());

        this.add(HMBlocks.HOLLOW_ORE.get(),
                block -> createOreDrop(HMBlocks.HOLLOW_ORE.get(), HMItems.HOLLOW.get()));;

        this.add(HMBlocks.HOLLOW_ORE.get(),
                block -> createMultipleOreDrops(HMBlocks.HOLLOW_ORE.get(), HMItems.HOLLOW.get(), 4, 8));
        this.add(HMBlocks.HOLLOW_ORE.get(),
                block -> createMultipleOreDrops(HMBlocks.HOLLOW_ORE.get(), HMItems.HOLLOW.get(), 1, 6));


        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return HMBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}