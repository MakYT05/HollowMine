package yt.mak.hollowmine.init.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.items.*;
import yt.mak.hollowmine.custom.tier.HMTiers;
import yt.mak.hollowmine.sound.HMSounds;

import java.util.List;

public class HMItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HollowMine.MODID);

    public static final RegistryObject<Item> HOLLOW_BOOK = ITEMS.register("hollow_book",
            () -> new HollowBook(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> GEM = ITEMS.register("gem",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.hollowmine.gem"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                };
            });

    public static final RegistryObject<Item> HOLLOW_STICK = ITEMS.register("hollow_stick",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.hollowmine.stick"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                };
            });

    public static final RegistryObject<Item> HOLLOW = ITEMS.register("hollow",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.hollowmine.hollow"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                };
            });

    public static final RegistryObject<Item> DREAM_NAIL = ITEMS.register("dream_nail",
            () -> new DreamNail(HMTiers.DREAM_NAIL, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> HOLLOW_MASK = ITEMS.register("hollow_mask",
            () -> new HollowMaskItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> HOLLOW_MANA = ITEMS.register("hollow_mana",
            () -> new HollowMana(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SLIPKNOT_MUSIC_DISC = ITEMS.register("slipknot_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(HMSounds.SLIPKNOT_KEY).stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.hollowmine.slipknot"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                };
            });

    public static final RegistryObject<Item> HOLLOW_AMULET_HP = ITEMS.register("hollow_amulet_hp",
            () -> new HollowAmuletHp(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final RegistryObject<Item> HOLLOW_AMULET_HEALTH = ITEMS.register("hollow_amulet_health",
            () -> new HollowAmuletHealth(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final RegistryObject<Item> HOLLOW_KEY= ITEMS.register("hollow_key",
            () -> new HollowKey(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final RegistryObject<Item> HOLLOW_DUST= ITEMS.register("hollow_dust",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.hollowmine.dust"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                };
            });

    public static void register(IEventBus bus) {ITEMS.register(bus);}
}