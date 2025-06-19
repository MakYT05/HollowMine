package yt.mak.hollowmine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.blocks.HMBlocks;

public class HMBlockStateProvider extends BlockStateProvider {
    public HMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, HollowMine.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(HMBlocks.HOLLOW_ORE);

        logBlock(HMBlocks.HOLLOW_LOG.get());
        axisBlock((RotatedPillarBlock) HMBlocks.HOLLOW_TREE_BLOCK.get(), blockTexture(HMBlocks.HOLLOW_LOG.get()), blockTexture(HMBlocks.HOLLOW_LOG.get()));

        blockItem(HMBlocks.HOLLOW_LOG);
        blockItem(HMBlocks.HOLLOW_TREE_BLOCK);
        leavesBlock(HMBlocks.HOLLOW_LEAVES);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("hollowmine:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("hollowmine:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }
}