package yt.mak.hollowmine.custom.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yt.mak.hollowmine.command.HollowMaskCommand;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.init.entity.HMEntities;

public class DreamNail extends SwordItem {
    private static final String TEXT_TAG = "hollow_shift_hold";
    boolean READY = false;

    public DreamNail(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            applyEffects(player);

            if (!READY && HollowMaskCommand.MASK_READY) {
                if (level instanceof ServerLevel serverLevel) {
                    CompoundTag data = player.getPersistentData();

                    int holdTime = data.getInt(TEXT_TAG);

                    Vec3 lookVec = player.getLookAngle().normalize().scale(3);
                    BlockPos spawnPos = player.blockPosition().offset((int) lookVec.x, 0, (int) lookVec.z);

                    HollowEntity hollowEntity = new HollowEntity(HMEntities.HOLLOW_ENTITY.get(), serverLevel);

                    hollowEntity.moveTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);

                    Vec3 directionToPlayer = player.position().subtract(hollowEntity.position()).normalize();
                    float yaw = (float) (Math.atan2(directionToPlayer.z, directionToPlayer.x) * (180 / Math.PI)) - 90;
                    hollowEntity.setYRot(yaw);
                    hollowEntity.setYHeadRot(yaw);

                    serverLevel.addFreshEntity(hollowEntity);

                    MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                            .append(Component.literal(" Ты правда думаешь, что сможешь собрать все артифакты?").withStyle(ChatFormatting.DARK_PURPLE));

                    player.sendSystemMessage(message1);

                    MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                            .append(Component.literal(" Это просто смешно!").withStyle(ChatFormatting.DARK_PURPLE));

                    player.sendSystemMessage(message2);

                    READY = true;

                    serverLevel.getServer().execute(() -> {
                        try {
                            Thread.sleep(5000);
                            hollowEntity.discard();
                        } catch (InterruptedException ignored) {}
                    });
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void applyEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 1));
    }
}