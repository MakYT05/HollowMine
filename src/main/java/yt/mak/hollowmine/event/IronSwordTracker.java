package yt.mak.hollowmine.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.command.DreamNailCommand;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.init.entity.HMEntities;
import yt.mak.hollowmine.init.items.HMItems;

import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IronSwordTracker {
    private static final WeakHashMap<Player, Boolean> hasTriggered = new WeakHashMap<>();
    private static final WeakHashMap<UUID, Boolean> immunePhase = new WeakHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        Player player = event.player;

        if (hasTriggered.getOrDefault(player, false)) return;

        boolean hasIronSword = player.getInventory().items.stream()
                .anyMatch(stack -> stack.getItem() == Items.IRON_SWORD);

        if (hasIronSword) {
            hasTriggered.put(player, true);

            if (player.level() instanceof ServerLevel serverLevel) {
                HollowEntity hollow = HMEntities.HOLLOW_ENTITY.get().create(serverLevel);
                if (hollow != null) {
                    Vec3 lookVec = player.getLookAngle().normalize().scale(3);
                    BlockPos spawnPos = player.blockPosition().offset((int) lookVec.x, 0, (int) lookVec.z);

                    hollow.moveTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);

                    hollow.setNoAi(true);
                    hollow.setInvulnerable(true);
                    hollow.setPersistenceRequired(true);

                    Vec3 directionToPlayer = player.position().subtract(hollow.position()).normalize();
                    float yaw = (float) (Math.atan2(directionToPlayer.z, directionToPlayer.x) * (180 / Math.PI)) - 90;
                    hollow.setYRot(yaw);
                    hollow.setYHeadRot(yaw);

                    serverLevel.addFreshEntity(hollow);

                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            hollow.getX(), hollow.getY(), hollow.getZ(),
                            100, 1, 1, 1, 0.5);

                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

                    scheduler.schedule(() -> {
                        player.sendSystemMessage(Component.literal("[ПУСТОЙ] ")
                                .withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Опа, кто же это?").withStyle(ChatFormatting.DARK_PURPLE)));
                    }, 0, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        player.sendSystemMessage(Component.literal("[ПУСТОЙ] ")
                                .withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Это же новая добыча!!!").withStyle(ChatFormatting.DARK_PURPLE)));
                    }, 5, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        DreamNailCommand.showQuestion((ServerPlayer) player);

                        hollow.setNoAi(false);
                        hollow.setInvulnerable(false);
                        hollow.setTarget(player);
                    }, 8, TimeUnit.SECONDS);

                    UUID hollowId = hollow.getUUID();
                    immunePhase.put(hollowId, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHollowHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof HollowEntity hollow)) return;

        UUID id = hollow.getUUID();

        if (hollow.getHealth() <= 6 && !immunePhase.getOrDefault(id, false)) {
            immunePhase.put(id, true);
            event.setCanceled(true);

            hollow.setInvulnerable(true);
            hollow.setTarget(null);

            if (hollow.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        hollow.getX(), hollow.getY(), hollow.getZ(),
                        50, 0.5, 0.5, 0.5, 0.1);

                for (ServerPlayer player : serverLevel.players()) {
                    if (player.distanceTo(hollow) < 10) {
                        player.sendSystemMessage(Component.literal("[ПУСТОЙ] ")
                                .withStyle(ChatFormatting.WHITE)
                                .append(Component.literal("Чтож... ты... победил!!!").withStyle(ChatFormatting.DARK_PURPLE)));
                    }
                }

                ItemStack reward = new ItemStack(HMItems.HOLLOW_MASK.get());

                ServerPlayer nearestPlayer = (ServerPlayer) serverLevel.getNearestPlayer(hollow, 10);
                if (nearestPlayer != null) {
                    boolean added = nearestPlayer.getInventory().add(reward);
                    if (!added) {
                        hollow.spawnAtLocation(reward);
                    }
                } else {
                    hollow.spawnAtLocation(reward);
                }

                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            hollow.getX(), hollow.getY(), hollow.getZ(),
                            100, 1, 1, 1, 0.5);
                    hollow.discard();
                    immunePhase.remove(id);
                }, 5, TimeUnit.SECONDS);
            }
        }
    }
}