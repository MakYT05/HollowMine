package yt.mak.hollowmine.custom.items;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;

public class MakItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HollowMine.MODID);

    public static final RegistryObject<Item> GEM = ITEMS.register("gem",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HOLLOW = ITEMS.register("hollow",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HOLLOW_MASK = ITEMS.register("hollow_mask",
            () -> new ArmorItem(MakArmorItems.HOLLOW_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistryObject<Item> ALEXANDRITE_CHESTPLATE = ITEMS.register("alexandrite_chestplate",
            () -> new ArmorItem(MakArmorItems.HOLLOW_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));

    public static void register(IEventBus bus) {ITEMS.register(bus);}
}