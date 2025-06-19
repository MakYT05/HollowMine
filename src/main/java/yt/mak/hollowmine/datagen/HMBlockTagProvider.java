package yt.mak.hollowmine.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.blocks.HMBlocks;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HMBlockTagProvider extends BlockTagsProvider {
    public HMBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, HollowMine.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(HMBlocks.HOLLOW_ORE.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(HMBlocks.HOLLOW_LOG.get())
                .add(HMBlocks.HOLLOW_TREE_BLOCK.get());
    }
}