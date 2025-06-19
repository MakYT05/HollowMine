package yt.mak.hollowmine.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entities.Mak;
import yt.mak.hollowmine.init.entity.HMEntities;
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
                .then(Commands.literal("confirm").executes(context -> activateBook(context.getSource().getPlayerOrException())))
                .then(Commands.literal("cancel").executes(context -> cancelBook(context.getSource().getPlayerOrException()))));
    }

    private static int activateBook(ServerPlayer player) {
        boolean removed = false;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == HMItems.HOLLOW_BOOK.get()) {
                stack.shrink(1);
                if (stack.isEmpty()) player.getInventory().setItem(i, ItemStack.EMPTY);
                removed = true;
                break;
            }
        }

        if (!removed) {
            player.sendSystemMessage(Component.literal("У вас нет Пустотной книги."));
            return 0;
        }

        ServerLevel level = player.serverLevel();
        Vec3 look = player.getLookAngle().normalize().scale(3);
        BlockPos spawnPos = player.blockPosition().offset((int) look.x, 0, (int) look.z);

        Mak mak = new Mak(HMEntities.MAK.get(), level);
        mak.moveTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
        mak.setNoAi(true);

        Vec3 toPlayer = player.position().subtract(mak.position()).normalize();
        float yaw = (float) (Math.atan2(toPlayer.z, toPlayer.x) * (180 / Math.PI)) - 90;
        mak.setYRot(yaw);
        mak.setYHeadRot(yaw);
        mak.yBodyRot = yaw;
        mak.yBodyRotO = yaw;

        level.addFreshEntity(mak);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        sendMsg(player, "[МАК]", "Приветствую, путник.", 0, scheduler);
        sendMsg(player, "[МАК]", "Меня зовут МАК, и я разработчик этого сюжетного мода.", 5, scheduler);

        sendMsg(player, "[МАК]", "Да-да, сюжетного!", 10, scheduler);
        sendMsg(player, "[МАК]", "Здесь ты встретишь новых мобов, боссов, оружия, постройки, амулеты и т.д.", 15, scheduler);
        sendMsg(player, "[МАК]", "Это мой первый сюжетный мод, не судите строго.", 20, scheduler);
        sendMsg(player, "[МАК]", "Для начала сюжета добудь пустотную маску с Полых.", 25, scheduler);
        sendMsg(player, "[МАК]", "Кликайте ПКМ по сюжетным предметам для активации.", 30, scheduler);
        sendMsg(player, "[МАК]", "Совет: маска → мана → амулет торговца → амулет ХП → меч.", 35, scheduler);
        sendMsg(player, "[МАК]", "В сюжете есть выборы с разными концовками.", 40, scheduler);
        sendMsg(player, "[МАК]", "Приятной игры, путник!", 45, scheduler);
        sendMsg(player, "[МАК]", "ДА ПРЕДАСТ ТЕБЕ СИЛ ХРАНИТЕЛЬ ХАЛЛОУНЕСТА!!!", 50, scheduler, ChatFormatting.DARK_RED);
        sendMsg(player, "[МАК]", "ДА ПОЩАДИТ ТЕБЯ ЕГО ВЕЛИЧЕСТВО ЛУЧЕЗАРНОСТЬ!!!", 55, scheduler, ChatFormatting.DARK_RED);

        scheduler.schedule(() -> {
            if (mak.isAlive()) {
                mak.discard();
            }
        }, 60, TimeUnit.SECONDS);

        return 1;
    }

    private static void sendMsg(ServerPlayer player, String name, String text, int delay, ScheduledExecutorService scheduler) {
        sendMsg(player, name, text, delay, scheduler, ChatFormatting.RED);
    }

    private static void sendMsg(ServerPlayer player, String name, String text, int delay, ScheduledExecutorService scheduler, ChatFormatting nameColor) {
        scheduler.schedule(() -> {
            MutableComponent msg = Component.literal(name).withStyle(nameColor)
                    .append(" ")
                    .append(Component.literal(text).withStyle(ChatFormatting.WHITE));
            player.sendSystemMessage(msg);
        }, delay, TimeUnit.SECONDS);
    }

    private static int cancelBook(ServerPlayer player) {
        player.sendSystemMessage(Component.literal("Будем ждать."));
        return 1;
    }
}