package yt.mak.hollowmine.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.effect.MakEffects;
import yt.mak.hollowmine.init.items.HMItems;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HollowMaskCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("hollowmask")
                .then(Commands.literal("confirm").executes(context -> activateMask(context.getSource().getPlayerOrException())))
                .then(Commands.literal("cancel").executes(context -> cancelActivation(context.getSource().getPlayerOrException()))));
    }

    private static int activateMask(ServerPlayer player) {
        if (removeItemFromInventory(player, HMItems.HOLLOW_MASK.get())) {
            player.addEffect(new MobEffectInstance(MakEffects.HOLLOW_EFFECT.getHolder().get(), MobEffectInstance.INFINITE_DURATION, 0));
            player.sendSystemMessage(Component.literal("Вы активировали маску!").withStyle(ChatFormatting.GREEN));
        } else {
            player.sendSystemMessage(Component.literal("У вас нет маски в инвентаре!").withStyle(ChatFormatting.RED));
        }
        return 1;
    }

    private static int cancelActivation(ServerPlayer player) {
        player.sendSystemMessage(Component.literal("Вы отказались от активации.").withStyle(ChatFormatting.GRAY));
        return 1;
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