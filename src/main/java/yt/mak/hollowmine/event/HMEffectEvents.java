package yt.mak.hollowmine.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.effect.MakEffects;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HMEffectEvents {
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            ServerPlayer newPlayer = (ServerPlayer) event.getEntity();
            newPlayer.addEffect(new MobEffectInstance(MakEffects.HOLLOW_EFFECT.getHolder().get(), MobEffectInstance.INFINITE_DURATION, 0));
        }
    }
}