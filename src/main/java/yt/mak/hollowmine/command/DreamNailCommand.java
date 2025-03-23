package yt.mak.hollowmine.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DreamNailCommand {
    public static int MESSAGE = 0;
    static boolean ONE = false;
    static boolean TWO = false;
    static boolean THREE = false;

    static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("dreamnail")
                .then(Commands.literal("one").executes(context -> oneNail(context.getSource().getPlayerOrException())))
                .then(Commands.literal("two").executes(context -> twoNail(context.getSource().getPlayerOrException())))
                .then(Commands.literal("three").executes(context -> threeNail(context.getSource().getPlayerOrException()))));
    }

    public static void showQuestion(ServerPlayer player) {
        scheduler.schedule(() -> {
            MutableComponent message = Component.literal("[ВЫ]\n")
                    .append(Component.literal("1) Ты меня ненавидишь?\n").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("2) Кто ты?\n").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("3) Что мне делать?\n").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("[1]")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                            .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dreamnail one"))))
                    .append("  ")
                    .append(Component.literal("[2]")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                            .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dreamnail two"))))
                    .append("  ")
                    .append(Component.literal("[3]")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                            .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dreamnail three"))));

            player.sendSystemMessage(message);
        }, 5, TimeUnit.SECONDS);
    }

    private static int oneNail(ServerPlayer player) {
        if (!ONE) {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" А ты умный я смотрю. Почему, спросишь ты? Потому что считаешь, что сможешь добиться нашей силы.").withStyle(ChatFormatting.DARK_PURPLE));

            player.sendSystemMessage(message);

            ONE = true;

            MESSAGE += 1;
        } else {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ты уже задавал этот вопрос.").withStyle(ChatFormatting.DARK_PURPLE));

            player.sendSystemMessage(message);
        }

        if (MESSAGE != 3) {
            showQuestion(player);
        }

        return 1;
    }

    private static int twoNail(ServerPlayer player) {
        if (!TWO) {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Можешь звать меня просто Пустым. Я могу принимать физический облик или быть у тебя в голове.").withStyle(ChatFormatting.DARK_PURPLE));

            player.sendSystemMessage(message);

            TWO = true;

            MESSAGE += 1;
        } else {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ты уже задавал этот вопрос.").withStyle(ChatFormatting.DARK_PURPLE));

            player.sendSystemMessage(message);
        }

        if (MESSAGE != 3) {
            showQuestion(player);
        }

        return 1;
    }

    private static int threeNail(ServerPlayer player) {
        if (!THREE) {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ты не знаешь? Разве ты не хочешь найти все артефакты?!").withStyle(ChatFormatting.DARK_PURPLE));

            player.sendSystemMessage(message);

            THREE = true;

            MESSAGE += 1;
        } else {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ты уже задавал этот вопрос.").withStyle(ChatFormatting.DARK_PURPLE));

            player.sendSystemMessage(message);
        }

        if (MESSAGE != 3) {
            showQuestion(player);
        }

        return 1;
    }
}