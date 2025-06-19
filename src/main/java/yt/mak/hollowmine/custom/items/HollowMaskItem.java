package yt.mak.hollowmine.custom.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class HollowMaskItem extends Item {
    public static boolean READY = false;

    public HollowMaskItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!READY) {
            if (!level.isClientSide) {
                MutableComponent message = Component.literal("Вы хотите ")
                        .append(Component.literal("НАВСЕГДА").withStyle(ChatFormatting.RED))
                        .append(" активировать маску?\n")
                        .append(Component.literal("[ДА]")
                                .withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD)
                                .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hollowmask confirm"))))
                        .append("  ")
                        .append(Component.literal("[НЕТ]")
                                .withStyle(ChatFormatting.RED, ChatFormatting.BOLD)
                                .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hollowmask cancel"))));

                player.sendSystemMessage(message);

                READY = true;
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.hollowmine.mask.shift_down"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.hollowmine.mask"));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}