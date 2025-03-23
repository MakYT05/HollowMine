package yt.mak.hollowmine.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.effect.HMEffects;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HMEvents {
    private static final String SHIFTHOLD_TAG = "hollow_shift_hold";
    private static final String READY_TAG = "hollow_ready";
    private static final double NORMAL_SPEED = 0.1;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Player player = event.player;
        if (player.level().isClientSide) return;

        if (player.hasEffect(HMEffects.HOLLOW_EFFECT.getHolder().get())) {
            boolean isShifting = player.isShiftKeyDown();
            CompoundTag data = player.getPersistentData();

            int holdTime = data.getInt(SHIFTHOLD_TAG);
            boolean isReady = data.getBoolean(READY_TAG);

            player.addEffect(new MobEffectInstance(
                    HMEffects.HOLLOW_EFFECT.getHolder().get(),
                    200,
                    0,
                    false,
                    false,
                    true
            ));

            if (!player.level().isClientSide && player.tickCount % 5 == 0) {
                ((ServerLevel) player.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        player.getX() - 1, player.getY() + 1, player.getZ(),
                        3,
                        0.3, 0.3, 0.3,
                        0.02
                );
            }

            if (isShifting) {
                if (holdTime < 60) {
                    data.putInt(SHIFTHOLD_TAG, holdTime + 1);
                }

                if (holdTime == 60 && !isReady) {
                    player.sendSystemMessage(Component.literal("Я готов!").withStyle(ChatFormatting.AQUA));
                    data.putBoolean(READY_TAG, true);
                }

                if (player.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(NORMAL_SPEED);
                }

            } else if (isReady) {
                Vec3 dashVector = player.getLookAngle().normalize().scale(15);
                player.push(dashVector.x, 1, dashVector.z);
                player.hurtMarked = true;

                data.putInt(SHIFTHOLD_TAG, 0);
                data.putBoolean(READY_TAG, false);
            }
        } else {
            if (player.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(NORMAL_SPEED);
            }
        }
    }
}