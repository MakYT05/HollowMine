package yt.mak.hollowmine.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import yt.mak.hollowmine.command.HollowMechCommand;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.init.blocks.HMBlocks;
import yt.mak.hollowmine.init.entity.HMEntities;
import yt.mak.hollowmine.init.items.HMItems;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = "hollowmine", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HMBlockEvent {
    public static boolean START = false;

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack item = event.getItemStack();

        if (!START) {
            if (level.getBlockState(pos).getBlock() == HMBlocks.HOLLOW_MECH_BLOCK.get()) {
                if (!level.isClientSide && item.getItem() == HMItems.HOLLOW_KEY.get()) {
                    BlockPos.betweenClosedStream(pos.offset(-10, -10, -10), pos.offset(10, -1, 10))
                            .forEach(p -> {
                                Block b = level.getBlockState(p).getBlock();
                                if (b == HMBlocks.HOLLOW_BLOCK.get() || b == HMBlocks.HOLLOW_BED_BLOCK.get()) {
                                    level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                                }
                            });

                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);

                    START = true;

                    if (level instanceof ServerLevel serverLevel) {
                        Vec3 lookVec = player.getLookAngle().normalize().scale(3);
                        BlockPos spawnPos = player.blockPosition().offset((int) lookVec.x, 0, (int) lookVec.z);

                        HollowEntity hollowEntity = new HollowEntity(HMEntities.HOLLOW_ENTITY.get(), serverLevel);

                        hollowEntity.moveTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);

                        hollowEntity.setNoAi(true);
                        hollowEntity.setInvulnerable(true);
                        hollowEntity.setPersistenceRequired(true);

                        Vec3 directionToPlayer = player.position().subtract(hollowEntity.position()).normalize();
                        float yaw = (float) (Math.atan2(directionToPlayer.z, directionToPlayer.x) * (180 / Math.PI)) - 90;
                        hollowEntity.setYRot(yaw);
                        hollowEntity.setYHeadRot(yaw);

                        serverLevel.addFreshEntity(hollowEntity);

                        serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                hollowEntity.getX(), hollowEntity.getY(), hollowEntity.getZ(),
                                100, 1, 1, 1, 0.5);

                        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

                        scheduler.schedule(() -> {
                            MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" СТОЙ!!!").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message1);
                        }, 0, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Ты и вправду готов его возрадить?").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message2);
                        }, 5, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message3 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" А что не так?").withStyle(ChatFormatting.AQUA));

                            player.sendSystemMessage(message3);
                        }, 10, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message4 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Ты ведь умрёшь!").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message4);
                        }, 15, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message5 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Да, я побоялся сказать... но зато говорю сейчас. Ты погибнешь!").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message5);
                        }, 20, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message6 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Почему?").withStyle(ChatFormatting.AQUA));

                            player.sendSystemMessage(message6);
                        }, 25, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message7 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Ты его воскрешаешь, отдавая своё тело.").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message7);
                        }, 30, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message8 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Считай ты становишься для него пустым сосудом.").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message8);
                        }, 35, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message9 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Подумай сейчас.").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message9);
                        }, 40, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            MutableComponent message10 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                    .append(Component.literal(" Ты точно готов?").withStyle(ChatFormatting.DARK_PURPLE));

                            player.sendSystemMessage(message10);
                        }, 45, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            HollowMechCommand.showQuestion((ServerPlayer) player);
                        }, 50, TimeUnit.SECONDS);

                        scheduler.schedule(() -> {
                            hollowEntity.discard();
                            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                    hollowEntity.getX(), hollowEntity.getY(), hollowEntity.getZ(),
                                    100, 1, 1, 1, 0.5);
                        }, 55, TimeUnit.SECONDS);
                    }
                }
            }
        }
    }
}