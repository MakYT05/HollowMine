package yt.mak.hollowmine.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entities.HollowEntity;
import yt.mak.hollowmine.custom.entities.HollowGoodSun;
import yt.mak.hollowmine.event.HollowArenaEvent;
import yt.mak.hollowmine.event.HollowArenaFinalEvent;
import yt.mak.hollowmine.init.entity.HMEntities;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HollowMechCommand {
    static boolean ONE = false;
    static boolean TWO = false;
    public static boolean FINAL = false;
    public static boolean VERY_BAD_FINAL = false;

    public static boolean canAttackPlayer = false;
    private static boolean dialogueStarted = false;

    private static final ServerBossEvent bossBar = new ServerBossEvent(
            Component.literal("ПУСТОЙ").withStyle(ChatFormatting.DARK_PURPLE),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS
    );

    private static int bossHits = 0;
    private static final int maxHits = 20;
    private static LivingEntity bossEntity = null;

    static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("hollowmech")
                .then(Commands.literal("one").executes(context -> oneMech(context.getSource().getPlayerOrException())))
                .then(Commands.literal("two").executes(context -> twoMech(context.getSource().getPlayerOrException()))));
    }

    public static void showQuestion(ServerPlayer player) {
        scheduler.schedule(() -> {
            MutableComponent message = Component.literal("[ВЫ]\n")
                    .append(Component.literal("1) Я убью вас! (выступить против Халлоунеста)\n").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("2) Я готов на это! (согласиться стать сосудом)\n").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("[1]")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                            .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hollowmech one"))))
                    .append("  ")
                    .append(Component.literal("[2]")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                            .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hollowmech two"))));

            player.sendSystemMessage(message);
        }, 5, TimeUnit.SECONDS);
    }

    private static int oneMech(ServerPlayer player) {
        if (!ONE) {
            scheduler.schedule(() -> {
                MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Что...?!").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message1);
            }, 5, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Я считал тебя другом...").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message2);
            }, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message3 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Ты не оставляешь мне выбора!").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message3);

                ServerLevel level = (ServerLevel) player.level();
                bossEntity = new HollowEntity(HMEntities.HOLLOW_ENTITY.get(), level);
                bossEntity.setPos(player.getX(), player.getY(), player.getZ() + 3);
                bossEntity.setInvulnerable(true);
                ((Mob) bossEntity).setNoAi(false);
                level.addFreshEntity(bossEntity);

                bossBar.setProgress(1.0F);
                bossBar.addPlayer(player);
                bossHits = 0;
                canAttackPlayer = true;
                dialogueStarted = false;

                if (bossEntity instanceof HollowEntity hollow) {
                    hollow.setTargetPlayer(player);
                    hollow.setCanAttack(true);
                }
            }, 15, TimeUnit.SECONDS);

            ONE = true;
        }

        return 1;
    }

    private static int twoMech(ServerPlayer player) {
        if (!TWO) {
            MutableComponent message = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Хорошо, я перенесу тебя, но запомни, я всегда буду рядом, не бойся.").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message);

            scheduler.schedule(() -> {
                ServerLevel level = player.serverLevel();
                BlockPos center = HollowArenaFinalEvent.getArenaCenter();
                player.teleportTo(level, center.getX() + 0.5, center.getY() + 2, center.getZ() + 0.5, player.getYRot(), player.getXRot());

                if (!HollowArenaFinalEvent.arenaBuilt) {
                    HollowArenaFinalEvent.buildArena(level, center);
                }

                HollowGoodSun hollowSun = new HollowGoodSun(HMEntities.HOLLOW_GOOD_SUN.get(), level);
                hollowSun.setPos(center.getX() + 0.5, center.getY() + 1, center.getZ() + 0.5);
                hollowSun.setInvulnerable(true);
                hollowSun.setSilent(true);
                hollowSun.setNoAi(true);
                level.addFreshEntity(hollowSun);

                MutableComponent msg1 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Здравствуй, дитя.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg1);
            }, 5, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg2 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Меня зовут Лучезарность.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg2);
            }, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg3 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Наверное ты не помнишь, но я именно тот, кто даровал тебе... жизнь.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg3);
            }, 15, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg4 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Чтож, подойди ко мне, стань частью меня!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg4);
            }, 20, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg5 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Подожди.").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(msg5);
            }, 25, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg6 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Что такое?").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg6);
            }, 30, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg7 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Почему ты ничего не упоминул про 'Вознесение'?").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(msg7);
            }, 35, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg8 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" А я то думаю, почему ты пахнешь Пустым?").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg8);
            }, 40, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg9 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Так он в тебе!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg9);
            }, 45, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg10 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Чёрт!").withStyle(ChatFormatting.DARK_PURPLE));

                player.sendSystemMessage(msg10);
            }, 50, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg11 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Ну да... а что?").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(msg11);
            }, 55, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg12 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Ничего, просто он сейчас умрёт!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg12);
            }, 60, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg13 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Прощай... друг!").withStyle(ChatFormatting.DARK_PURPLE));

                player.sendSystemMessage(msg13);
            }, 65, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg14 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" НЕ-Е-ЕТ, ОСТАНОВИСЬ!!!").withStyle(ChatFormatting.AQUA));

                player.sendSystemMessage(msg14);
            }, 70, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                if (!player.isAlive()) return;

                ServerLevel lvl = player.serverLevel();
                List<HollowGoodSun> possible = lvl.getEntitiesOfClass(HollowGoodSun.class,
                        new AABB(player.blockPosition()).inflate(30), e -> true);

                if (!possible.isEmpty()) {
                    HollowGoodSun shooter = possible.get(0);

                    lvl.sendParticles(ParticleTypes.END_ROD,
                            shooter.getX(), shooter.getY() + 1, shooter.getZ(),
                            30, 0.3, 0.3, 0.3, 0.01);
                    lvl.playSound(null, shooter.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.0F, 1.0F);

                    scheduler.schedule(() -> {
                        Vec3 from = shooter.position().add(0, shooter.getEyeHeight(), 0);
                        Vec3 to = player.position().add(0, player.getBbHeight() / 2, 0);
                        Vec3 direction = to.subtract(from).normalize();

                        for (int i = 0; i < 20; i++) {
                            Vec3 pos = from.add(direction.scale(i));
                            lvl.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 2, 0, 0, 0, 0);
                        }

                        lvl.sendParticles(ParticleTypes.EXPLOSION, player.getX(), player.getY() + 1, player.getZ(),
                                50, 1, 1, 1, 0.1);
                        lvl.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.0F, 1.0F);

                        player.push((lvl.random.nextDouble() - 0.5) * 0.5, 0.4, (lvl.random.nextDouble() - 0.5) * 0.5);
                    }, 1, TimeUnit.SECONDS);
                }
            }, 72, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg15 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Ха-ха-ха.").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg15);
            }, 80, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent msg16 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" А теперь стань частью меня!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(msg16);

                ServerLevel lvl = player.serverLevel();
                HollowArenaEvent.destroyArena(lvl);

                VERY_BAD_FINAL = true;
            }, 85, TimeUnit.SECONDS);

            TWO = true;
            FINAL = true;
        }

        return 1;
    }

    @SubscribeEvent
    public static void onPlayerAttack(LivingAttackEvent event) {
        if (bossEntity != null && event.getEntity() == bossEntity && !FINAL) {
            event.setCanceled(true);

            if (bossHits < maxHits) {
                bossHits++;
                float progress = 1.0f - (bossHits / (float) maxHits);
                bossBar.setProgress(progress);
            }

            if (bossHits >= maxHits && !dialogueStarted) {
                bossBar.removeAllPlayers();

                canAttackPlayer = false;
                if (bossEntity instanceof HollowEntity hollow) {
                    hollow.setCanAttack(false);
                    ((Mob) bossEntity).setNoAi(true);
                    bossEntity.setInvulnerable(true);
                }

                dialogueStarted = true;

                if (event.getSource().getEntity() instanceof ServerPlayer player) {
                    continueDialogue(player);
                }
            }
        }

        if (event.getSource().getEntity() == bossEntity && FINAL) {
            event.setCanceled(true);
        }
    }

    private static void continueDialogue(ServerPlayer player) {
        canAttackPlayer = false;

        if (bossEntity instanceof HollowEntity hollow) {
            hollow.setCanAttack(false);
            ((Mob) bossEntity).setNoAi(true);
            bossEntity.setInvulnerable(true);
        }

        scheduler.schedule(() -> {
            MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Хватит... ты доказал всё.").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message1);
        }, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ты меня победил уже во второй раз.").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message2);
        }, 10, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            MutableComponent message3 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Хах, смешно!").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message3);

            if (bossEntity != null && player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        bossEntity.getX(), bossEntity.getY() + 1, bossEntity.getZ(),
                        30, 0.5, 0.5, 0.5, 0.01);
                bossEntity.discard();
                bossEntity = null;
            }
        }, 15, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            MutableComponent message4 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Чтож... ступай, но смотри не помри сразу!!!").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message4);
        }, 20, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            MutableComponent message5 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ха-ха-ха.").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message5);

            FINAL = true;
        }, 25, TimeUnit.SECONDS);
    }
}