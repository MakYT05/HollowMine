package yt.mak.hollowmine.chat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.effect.HMEffects;
import yt.mak.hollowmine.init.items.HMItems;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().getString().trim();

        if (player.getPersistentData().getBoolean("hollowmask_prompt")) {
            event.setCanceled(true);

            if (message.equalsIgnoreCase("ДА")) {
                if (removeItemFromInventory(player, HMItems.HOLLOW_MASK.get())) {
                    player.addEffect(new MobEffectInstance(HMEffects.HOLLOW_EFFECT.getHolder().get(), MobEffectInstance.INFINITE_DURATION, 0));

                    player.sendSystemMessage(Component.literal("Вы активировали маску!").withStyle(ChatFormatting.GREEN));
                } else {
                    player.sendSystemMessage(Component.literal("У вас нет маски в инвентаре!").withStyle(ChatFormatting.RED));
                }
            } else if (message.equalsIgnoreCase("НЕТ")) {
                player.sendSystemMessage(Component.literal("Вы отказались от активации.").withStyle(ChatFormatting.GRAY));
            }

            player.getPersistentData().remove("hollowmask_prompt");
        }
    }

    private static boolean removeItemFromInventory(Player player, Item itemToRemove) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == itemToRemove) {
                stack.shrink(1);
                return true;
            }
        }
        return false;
    }
}