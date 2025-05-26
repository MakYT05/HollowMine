package yt.mak.hollowmine.event;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.items.HMItems;
import yt.mak.hollowmine.init.villager.HMVillager;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillagerEvent {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == HMVillager.HOLLOWENGER.get()) {
            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(HMItems.HOLLOW.get(), 1),
                    6, 4, 0.05f
            ));

            event.getTrades().get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(HMItems.HOLLOW.get(), 64),
                    new ItemStack(HMItems.HOLLOW_AMULET_HP.get(), 1),
                    6, 4, 0.05f
            ));
        }
    }
}