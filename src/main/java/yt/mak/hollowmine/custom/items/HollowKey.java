package yt.mak.hollowmine.custom.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yt.mak.hollowmine.custom.entities.HollowEntity;
import yt.mak.hollowmine.event.HMFiveEvent;
import yt.mak.hollowmine.init.entity.HMEntities;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HollowKey extends Item {
    public static boolean KEY_COMPLETE = false;

    public HollowKey(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (HMFiveEvent.FIVE) {
            if (!KEY_COMPLETE) {
                if (level instanceof ServerLevel serverLevel) {
                    Vec3 lookVec = player.getLookAngle().normalize().scale(3);
                    BlockPos spawnPos = player.blockPosition().offset((int) lookVec.x, 0, (int) lookVec.z);

                    HollowEntity hollowEntity = new HollowEntity(HMEntities.HOLLOW_ENTITY.get(), serverLevel);

                    hollowEntity.moveTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);

                    Vec3 directionToPlayer = player.position().subtract(hollowEntity.position()).normalize();
                    float yaw = (float) (Math.atan2(directionToPlayer.z, directionToPlayer.x) * (180 / Math.PI)) - 90;
                    hollowEntity.setYRot(yaw);
                    hollowEntity.setYHeadRot(yaw);

                    serverLevel.addFreshEntity(hollowEntity);
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            hollowEntity.getX(), hollowEntity.getY(), hollowEntity.getZ(),
                            100, 1, 1, 1, 0.5);

                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

                    MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                            .append(Component.literal(" Ну... поздравляю!").withStyle(ChatFormatting.DARK_PURPLE));

                    player.sendSystemMessage(message1);

                    scheduler.schedule(() -> {
                        MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Надеюсь это стоило... моей смерти?!").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message2);
                    }, 5, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message3 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Доволен теперь?!").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message3);
                    }, 10, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message4 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Доволен чем?").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message4);
                    }, 15, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message5 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Что?").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message5);
                    }, 20, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message6 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Это я должен спросить! Да, я убил тебя, но я ведь не знал кто ты.").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message6);
                    }, 25, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message7 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Я до сих пор не понимаю, что происходит, зачем я это делаю, что это за предметы, кто такие Пустые, и как я тут оказался?!").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message7);
                    }, 30, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message8 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Ты называешь меня смертным, смеёшься надо мной, но ответь почему? Что я сделал?!").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message8);
                    }, 35, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message9 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Хм... Походу ты и вправду тот самый...").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message9);
                    }, 40, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message10 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Тот самый кто?").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message10);
                    }, 45, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message11 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Тот самый заблудший. Хорошо, я расскажу тебе всё!").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message11);
                    }, 50, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message12 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" По рассказам Лучезарности... ах да, ты же не знаешь, это наш... Всевышний.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message12);
                    }, 55, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message13 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Так вот по его рассказам один человек со слезами на глазах вознёсся, ради того, чтобы не жить как... все?").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message13);
                    }, 60, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message14 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Лучезарность услышал тебя, и ты попал в наш мир под названием Халлоунест.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message14);
                    }, 65, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message15 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Но не думай что всё так просто!").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message15);
                    }, 70, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message16 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Он дал тебе возможность вознестись, чтобы помочь ему воскреснуть.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message16);
                    }, 75, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message17 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Для его воскрешения нужен ключ, который ты сейчас держишь.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message17);
                    }, 80, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message18 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Считай, что ты избранный.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message18);
                    }, 85, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message19 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Мы же, Пустые, обитатели этого мира, которым было поручено несмотря ни на что помочь тебе возродить Лучезарность.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message19);
                    }, 90, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message20 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" А не помнишь ты потому что после вознесения стирается память.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message20);
                    }, 100, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message21 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Оу... спасибо, что ответил, но у меня теперь возникло ещё три вопроса...").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message21);
                    }, 105, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message22 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Задавай.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message22);
                    }, 110, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message21 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Почему ты решил меня убить?").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message21);
                    }, 115, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message22 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" У нас, Пустых, тоже есть работы, и моя работа - убивать монстров. Я просто не понял кто ты.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message22);
                    }, 120, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message23 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" А почему меня ненавидишь?").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message23);
                    }, 125, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message24 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Ну я же сказал, что до этого момента не знал кто ты.").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message24);
                    }, 130, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message25 = Component.literal("[Вы]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Понятно, а что мне делать дальше?").withStyle(ChatFormatting.AQUA));

                        player.sendSystemMessage(message25);
                    }, 135, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message26 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Тебе нужно отыскать Колыбель - это место сна Лучезарности. Пробуди его. Принеси в этот мир свет!").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message26);
                    }, 140, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message27 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" УДАЧИ!!!").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message27);
                    }, 145, TimeUnit.SECONDS);

                    serverLevel.getServer().execute(() -> {
                        try {
                            Thread.sleep(5000);
                            hollowEntity.discard();
                            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                    hollowEntity.getX(), hollowEntity.getY(), hollowEntity.getZ(),
                                    100, 1, 1, 1, 0.5);
                        } catch (InterruptedException ignored) {}
                    });

                    KEY_COMPLETE = true;
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.hollowmine.key.shift_down"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.hollowmine.key"));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}