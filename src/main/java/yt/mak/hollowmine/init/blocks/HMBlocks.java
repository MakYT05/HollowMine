package yt.mak.hollowmine.init.blocks;

import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.blocks.*;
import yt.mak.hollowmine.init.items.HMItems;

import java.util.function.Supplier;

public class HMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HollowMine.MODID);

    public static final RegistryObject<Block> HOLLOW_TREE_BLOCK = registerBlock("hollow_tree_block",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HOLLOW_ORE = registerBlock("hollow_ore",
            () -> new HollowOreBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of().strength(4.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HOLLOW_BED_BLOCK = registerBlock("hollow_bed_block",
            () -> new HollowBedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> HOLLOW_BLOCK = registerBlock("hollow_block",
            () -> new HollowBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_STAIRS).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HOLLOW_MECH_BLOCK = registerBlock("hollow_mech_block",
            () -> new HollowMechBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HOLLOW_TABLE = registerBlock("hollow_table",
            () -> new HollowTable(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        HMItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {BLOCKS.register(bus);}
}