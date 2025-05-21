package yt.mak.hollowmine.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.custom.entity.HollowSun;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HMEventFinal {
    public static boolean BAD_FINAL = false;
    public static boolean VERY_GOOD_FINAL = false;

    static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof HollowSun boss)) return;
        if (boss.level().isClientSide()) return;

        ServerLevel level = (ServerLevel) boss.level();
        Player player = level.getNearestPlayer(boss, 20);

        if (player == null) return;

        if (HollowSun.ONE && boss.isNoAi()) {
            scheduler.schedule(() -> {
                MutableComponent message1 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Не мешай!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message1);
            }, 0, TimeUnit.SECONDS);

            List<HollowEntity> hollowTargets = level.getEntitiesOfClass(HollowEntity.class,
                    boss.getBoundingBox().inflate(10), e -> true);

            if (!hollowTargets.isEmpty()) {
                HollowEntity hollow = hollowTargets.get(0);
                level.sendParticles(ParticleTypes.EXPLOSION, hollow.getX(), hollow.getY() + 1, hollow.getZ(), 50, 1, 1, 1, 0.1);
                hollow.discard();

                scheduler.schedule(() -> {
                    MutableComponent message2 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                            .append(Component.literal(" Ну давай, нападай!").withStyle(ChatFormatting.WHITE));
                    player.sendSystemMessage(message2);
                }, 5, TimeUnit.SECONDS);

                float maxHealth = boss.getMaxHealth();
                boss.setHealth(Math.min(maxHealth, maxHealth / 2));
                boss.setNoAi(false);
                boss.setInvulnerable(false);
                boss.passivePhaseStarted = false;

                HollowSun.ONE = false;
            }

            event.setCanceled(true);
        }

        if (HollowSun.TWO && boss.isNoAi()) {
            boss.kill();
            HollowSun.TWO = false;

            scheduler.schedule(() -> {
                MutableComponent message1 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" НЕ-Е-ЕТ!!!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message1);
            }, 0, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Ну здравствуй, новый король Холлоунеста.").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message2);
            }, 5, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message3 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Ты вернулся?!").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(message3);
            }, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message4 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" И не уходил.").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message4);
            }, 15, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message5 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Я всегда буду рядом!").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message5);
            }, 20, TimeUnit.SECONDS);

            level.sendParticles(ParticleTypes.CLOUD, boss.getX(), boss.getY(), boss.getZ(), 50, 1, 2, 1, 0.1);

            HollowArenaEvent.destroyArena(level);

            VERY_GOOD_FINAL = true;
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof HollowSun boss)) return;
        if (boss.level().isClientSide()) return;

        ServerLevel level = (ServerLevel) boss.level();
        Player player = level.getNearestPlayer(boss, 20);

        if (player != null) {
            scheduler.schedule(() -> {
                MutableComponent message1 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Чтож...").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message1);
            }, 0, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message2 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Вот и мой... конец...").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message2);
            }, 5, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message3 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Ну здравствуй, новый король Холлоунеста.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message3);
            }, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message4 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Это тебе за друга!").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(message4);
            }, 15, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message5 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Вечных снов... вам двоим...").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(message5);
            }, 20, TimeUnit.SECONDS);

            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 30, 0));

            HollowArenaEvent.destroyArena(level);

            BAD_FINAL = true;
        }
    }
}