package yt.mak.hollowmine.custom.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yt.mak.hollowmine.command.HollowMaskCommand;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.effect.HMEffects;
import yt.mak.hollowmine.init.entity.HMEntities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HollowMana extends Item {
    public static boolean MANA_COMPLETE = false;

    public HollowMana(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (HollowMaskCommand.MASK_READY) {
            if (!MANA_COMPLETE) {
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
                            .append(Component.literal(" Ха ха ха...").withStyle(ChatFormatting.DARK_PURPLE));

                    player.sendSystemMessage(message1);

                    scheduler.schedule(() -> {
                        MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Неужели сил не осталось?").withStyle(ChatFormatting.DARK_PURPLE));

                        player.sendSystemMessage(message2);
                    }, 5, TimeUnit.SECONDS);

                    serverLevel.getServer().execute(() -> {
                        try {
                            Thread.sleep(5000);
                            hollowEntity.discard();
                            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                    hollowEntity.getX(), hollowEntity.getY(), hollowEntity.getZ(),
                                    100, 1, 1, 1, 0.5);
                        } catch (InterruptedException ignored) {}
                    });

                    MANA_COMPLETE = true;
                }
            }

            if (!level.isClientSide) {
                player.addEffect(new MobEffectInstance(HMEffects.HOLLOW_EFFECT.getHolder().get(), 10000 * 3, 0));

                ItemStack itemStack = player.getItemInHand(hand);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}