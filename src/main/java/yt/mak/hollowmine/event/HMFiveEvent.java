package yt.mak.hollowmine.event;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.items.*;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HMFiveEvent {
    private static final String FORGE_DATA = "ForgeData";
    private static final String MESSAGE_SENT_TAG = "hollow_message_sent";
    public static boolean FIVE = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        CompoundTag data = player.getPersistentData();

        CompoundTag forgeData = data.contains(FORGE_DATA, Tag.TAG_COMPOUND) ? data.getCompound(FORGE_DATA) : new CompoundTag();

        if (!forgeData.getBoolean(MESSAGE_SENT_TAG) && !FIVE) {
            if (HollowMaskItem.READY && DreamNail.READY && HollowMana.MANA_COMPLETE && HollowAmuletHp.AMULET_COMPLETE && HollowAmuletHealth.HEALTH_COMPLETE) {
                MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Отлично, ты собрал все артефакты. Теперь соедини их и активируй то, что создал!")
                                .withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message);

                forgeData.putBoolean(MESSAGE_SENT_TAG, true);
                data.put(FORGE_DATA, forgeData);

                FIVE = true;
            }
        }
    }
}