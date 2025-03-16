package yt.mak.hollowmine.init.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.items.DreamNail;
import yt.mak.hollowmine.custom.items.HollowMaskItem;
import yt.mak.hollowmine.custom.tier.ModTiers;

public class HMItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HollowMine.MODID);

    public static final RegistryObject<Item> GEM = ITEMS.register("gem",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HOLLOW = ITEMS.register("hollow",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DREAM_NAIL = ITEMS.register("dream_nail",
            () -> new DreamNail(ModTiers.DREAM_NAIL, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> HOLLOW_MASK = ITEMS.register("hollow_mask",
            () -> new HollowMaskItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> HOLLOW_MANA = ITEMS.register("hollow_mana",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {ITEMS.register(bus);}
}