package yt.mak.hollowmine.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entities.HollowDie;
import yt.mak.hollowmine.init.entity.HMEntities;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerDeathEvent {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ServerLevel level = (ServerLevel) player.level();
        HollowDie hollow = HMEntities.HOLLOW_DIE.get().create(level);

        if (hollow == null) return;

        hollow.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);
            if (!stack.isEmpty()) {
                hollow.getInventory().set(i, stack.copy());
            }
        }

        level.addFreshEntity(hollow);

        player.getInventory().clearContent();
    }
}