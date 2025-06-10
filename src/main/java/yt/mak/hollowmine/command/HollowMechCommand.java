package yt.mak.hollowmine.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.init.entity.HMEntities;

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

            TWO = true;
            FINAL = true;
            VERY_BAD_FINAL = true;
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