package yt.mak.hollowmine.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.items.HMItems;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HMFiveInventoryChecker {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static final Set<UUID> processingPlayers = Collections.synchronizedSet(new HashSet<>());

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;

        if (processingPlayers.contains(player.getUUID())) {
            return;
        }

        if (hasAllItems(player)) {
            processingPlayers.add(player.getUUID());

            scheduler.schedule(() -> {
                MinecraftServer server = player.getServer();
                if (server == null) {
                    processingPlayers.remove(player.getUUID());
                    return;
                }

                server.execute(() -> {
                    ServerPlayer serverPlayer = server.getPlayerList().getPlayer(player.getUUID());
                    if (serverPlayer == null) {
                        processingPlayers.remove(player.getUUID());
                        return;
                    }

                    if (hasAllItems(serverPlayer)) {
                        removeOneItem(serverPlayer, HMItems.DREAM_NAIL.get());
                        removeOneItem(serverPlayer, HMItems.HOLLOW_AMULET_HP.get());
                        removeOneItem(serverPlayer, HMItems.HOLLOW_AMULET_HEALTH.get());
                        removeOneItem(serverPlayer, HMItems.HOLLOW_MANA.get());
                        removeOneItem(serverPlayer, HMItems.HOLLOW_MASK.get());

                        ItemStack keyStack = new ItemStack(HMItems.HOLLOW_KEY.get(), 1);
                        if (!serverPlayer.addItem(keyStack)) {
                            serverPlayer.drop(keyStack, false);
                        }

                        MutableComponent message = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Отлично, я создал ключ!")
                                        .withStyle(ChatFormatting.AQUA));
                        player.sendSystemMessage(message);
                    }

                    processingPlayers.remove(player.getUUID());
                });
            }, 5, TimeUnit.SECONDS);
        }
    }

    private static boolean hasAllItems(Player player) {
        return containsItem(player, HMItems.DREAM_NAIL.get()) &&
                containsItem(player, HMItems.HOLLOW_AMULET_HP.get()) &&
                containsItem(player, HMItems.HOLLOW_AMULET_HEALTH.get()) &&
                containsItem(player, HMItems.HOLLOW_MANA.get()) &&
                containsItem(player, HMItems.HOLLOW_MASK.get());
    }

    private static boolean containsItem(Player player, Item item) {
        return player.getInventory().contains(new ItemStack(item));
    }

    private static void removeOneItem(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == item && !stack.isEmpty()) {
                stack.shrink(1);
                if (stack.isEmpty()) player.getInventory().setItem(i, ItemStack.EMPTY);
                break;
            }
        }
    }
}