package yt.mak.hollowmine.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.items.HMItems;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HollowBookCommand {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("hollowbook")
                .then(Commands.literal("confirm").executes(context ->
                        activateBook(context.getSource().getPlayerOrException())))
                .then(Commands.literal("cancel").executes(context ->
                        cancelBook(context.getSource().getPlayerOrException()))));
    }

    private static int activateBook(ServerPlayer player) {
        boolean removed = false;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (stack.getItem() == HMItems.HOLLOW_BOOK.get()) {
                stack.shrink(1);

                if (stack.isEmpty()) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }

                removed = true;

                break;
            }
        }

        if (removed) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            MutableComponent message1 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                    .append(Component.literal(" Приветствую путник.").withStyle(ChatFormatting.WHITE));

            player.sendSystemMessage(message1);

            scheduler.schedule(() -> {
                MutableComponent message2 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Меня зовут МАК, и я разработчик этого сюжетного мода.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message2);
            }, 5, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message3 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Да-да, сюжетного!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message3);
            }, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message4 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Здесь ты встретишь новых мобов, боссов, оружия, постройки, амулеты и тд.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message4);
            }, 15, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message5 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Это мой первый сюжетный мод, поэтому не всё может быть продумано, не судите строго.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message5);
            }, 20, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message6 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Ну а для начала сюжета вам нужно заполучить пустотную маску, которая выбивается из Полых.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message6);
            }, 25, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message7 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Для активации скрипта кликайте ПКМ по сюжетным предметам.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message7);
            }, 30, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message8 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Для начала советую собирать предметы в таком порядке: маска - мана - амулет торговца - амулет хп - меч.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message8);
            }, 35, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message9 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Так же в сюжете имеется выборы от которых будет зависеть концовки, выбирайте с умом.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message9);
            }, 40, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message10 = Component.literal("[МАК]").withStyle(ChatFormatting.RED)
                        .append(Component.literal(" Чтож, думаю я всё объяснил. Приятной игры, путник!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message10);
            }, 45, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message11 = Component.literal("[МАК]").withStyle(ChatFormatting.DARK_RED)
                        .append(Component.literal(" ДА ПРЕДАСТ ТЕБЕ СИЛ ХРАНИТЕЛЬ ХОЛЛОУНЕСТА!!!").withStyle(ChatFormatting.RED));
                player.sendSystemMessage(message11);
            }, 50, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message12 = Component.literal("[МАК]").withStyle(ChatFormatting.DARK_RED)
                        .append(Component.literal(" ДА ПОЩАДИТ ТЕБЯ ЕГО ВЕЛИЧЕСТВО ЛУЧЕЗАРНОСТЬ!!!").withStyle(ChatFormatting.RED));
                player.sendSystemMessage(message12);
            }, 55, TimeUnit.SECONDS);
        }

        return Command.SINGLE_SUCCESS;
    }


    private static int cancelBook(ServerPlayer player) {
        player.sendSystemMessage(Component.literal("Будем ждать."));

        return Command.SINGLE_SUCCESS;
    }
}