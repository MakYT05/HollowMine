package yt.mak.hollowmine.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.init.items.HMItems;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class JoinGiveBookHandler {

    private static final String HAS_RECEIVED_BOOK_TAG = "hollow_has_book";
    private static final String PERSISTED_NBT_TAG = "ForgeData";

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        CompoundTag data = player.getPersistentData();
        CompoundTag forgeData = data.getCompound(PERSISTED_NBT_TAG);

        if (!forgeData.getBoolean(HAS_RECEIVED_BOOK_TAG)) {
            ItemStack book = new ItemStack(HMItems.HOLLOW_BOOK.get());

            if (!player.getInventory().add(book)) {
                player.drop(book, false);
            }

            forgeData.putBoolean(HAS_RECEIVED_BOOK_TAG, true);
            data.put(PERSISTED_NBT_TAG, forgeData);
        }
    }
}