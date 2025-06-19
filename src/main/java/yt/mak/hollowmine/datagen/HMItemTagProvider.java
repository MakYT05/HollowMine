package yt.mak.hollowmine.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.blocks.HMBlocks;
import yt.mak.hollowmine.init.items.HMItems;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HMItemTagProvider extends ItemTagsProvider {
    public HMItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                              CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, HollowMine.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.TRIM_MATERIALS)
                .add(HMItems.HOLLOW.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(HMBlocks.HOLLOW_LOG.get().asItem())
                .add(HMBlocks.HOLLOW_TREE_BLOCK.get().asItem());
    }
}