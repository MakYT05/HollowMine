package yt.mak.hollowmine.custom.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yt.mak.hollowmine.command.HollowMaskCommand;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.init.entity.HMEntities;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundTag;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.UUID;

public class HollowAmuletHp extends Item {
    private static final UUID HEALTH_BOOST_UUID = UUID.fromString("c614e44d-52a1-4d25-b3d3-e77570b347b3");
    private static final String AMULET_NBT_KEY = "HollowAmuletHealthGiven";
    static boolean AMULET_COMPLETE = false;

    public HollowAmuletHp(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (HollowMaskCommand.MASK_READY && !AMULET_COMPLETE && level instanceof ServerLevel serverLevel) {
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

            MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" Ухты...").withStyle(ChatFormatting.DARK_PURPLE));
            player.sendSystemMessage(message1);

            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                MutableComponent message2 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Смотрю ты у тарговца преобрёл амулет.").withStyle(ChatFormatting.DARK_PURPLE));
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

            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(AMULET_NBT_KEY)) {
                AttributeInstance attr = player.getAttribute(Attributes.MAX_HEALTH);
                ResourceLocation id = ResourceLocation.fromNamespaceAndPath("hollowmine", "hollow_amulet_health");

                if (attr != null && attr.getModifiers().stream().noneMatch(mod -> mod.is(id))) {
                    AttributeModifier modifier = new AttributeModifier(id, 20.0, AttributeModifier.Operation.ADD_VALUE);
                    attr.addTransientModifier(modifier);
                    player.setHealth(player.getMaxHealth());
                }

                data.putBoolean(AMULET_NBT_KEY, true);
            }

            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }

            AMULET_COMPLETE = true;
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}