package yt.mak.hollowmine.custom.entity;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.event.HollowArenaEvent;
import yt.mak.hollowmine.init.entity.HMEntities;
import yt.mak.hollowmine.init.items.HMItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HollowSun extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private double lastX;
    private double lastY;
    private double lastZ;

    private int laserCooldown = 100;
    private int teleportCooldown = 400;

    public boolean passivePhaseStarted = false;
    private boolean passivePhaseTriggeredOnce = false;

    public static volatile boolean ONE = false;
    public static volatile boolean TWO = false;

    public static HollowSun ACTIVE_SUN = null;

    static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public HollowSun(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(HMItems.HOLLOW.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("hollowsun")
                .then(Commands.literal("one").executes(context -> oneSun(context.getSource().getPlayerOrException())))
                .then(Commands.literal("two").executes(context -> twoSun(context.getSource().getPlayerOrException()))));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 300D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(HMItems.HOLLOW.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return HMEntities.HOLLOW_SUN.get().create(level);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (passivePhaseStarted && this.isNoAi()) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            double dx = this.getX() - lastX;
            double dy = this.getY() - lastY;
            double dz = this.getZ() - lastZ;
            double distanceMoved = dx * dx + dy * dy + dz * dz;

            if (distanceMoved > 0.0001) {
                if (!idleAnimationState.isStarted()) {
                    idleAnimationState.start(this.tickCount);
                }
            } else {
                idleAnimationState.stop();
            }

            lastX = this.getX();
            lastY = this.getY();
            lastZ = this.getZ();
        }

        if (!this.level().isClientSide()) {
            if (!passivePhaseStarted && !passivePhaseTriggeredOnce && this.getHealth() <= 10) {
                passivePhaseStarted = true;
                passivePhaseTriggeredOnce = true;

                this.setInvulnerable(true);
                this.setNoAi(true);

                ACTIVE_SUN = this;

                Player player = ((ServerLevel) this.level()).getNearestPlayer(this, 20);

                if (player != null && this.level() instanceof ServerLevel serverLevel) {

                    scheduler.schedule(() -> {
                        MutableComponent message1 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                                .append(Component.literal(" Стой...").withStyle(ChatFormatting.WHITE));
                        player.sendSystemMessage(message1);
                    }, 5, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message2 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                                .append(Component.literal(" Остановись!").withStyle(ChatFormatting.WHITE));
                        player.sendSystemMessage(message2);
                    }, 10, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message3 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                                .append(Component.literal(" Я не хочу никому зла...").withStyle(ChatFormatting.WHITE));
                        player.sendSystemMessage(message3);
                    }, 15, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message4 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                                .append(Component.literal(" Пощади!").withStyle(ChatFormatting.WHITE));
                        player.sendSystemMessage(message4);
                    }, 20, TimeUnit.SECONDS);

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

                    scheduler.schedule(() -> {
                        MutableComponent message5 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" Чего ты ждёшь?!").withStyle(ChatFormatting.DARK_PURPLE));
                        player.sendSystemMessage(message5);
                    }, 25, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message6 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                                .append(Component.literal(" УБЕЙ ЕГО!!!").withStyle(ChatFormatting.DARK_PURPLE));
                        player.sendSystemMessage(message6);
                    }, 30, TimeUnit.SECONDS);

                    scheduler.schedule(() -> {
                        MutableComponent message = Component.literal("[ВЫ]\n")
                                .append(Component.literal("1) Убить Лучезарность!\n").withStyle(ChatFormatting.AQUA))
                                .append(Component.literal("2) Пощадить Лучезарность!\n").withStyle(ChatFormatting.AQUA))
                                .append(Component.literal("[1]")
                                        .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                                        .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hollowsun one"))))
                                .append("  ")
                                .append(Component.literal("[2]")
                                        .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)
                                        .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hollowsun two"))));
                        player.sendSystemMessage(message);
                    }, 35, TimeUnit.SECONDS);
                }
            }

            if (!passivePhaseStarted) {
                if (laserCooldown-- <= 0) {
                    shootFireLaser();
                    laserCooldown = 100;
                }

                if (teleportCooldown-- <= 0) {
                    teleportWithinArena();
                    teleportCooldown = 400;
                }
            }
        }
    }

    private void shootFireLaser() {
        ServerLevel level = (ServerLevel) this.level();
        Player target = level.getNearestPlayer(this, 64);
        if (target == null) return;

        Vec3 from = this.position().add(0, this.getEyeHeight(), 0);
        Vec3 to = target.position().add(0, target.getBbHeight() / 2, 0);
        Vec3 direction = to.subtract(from).normalize();

        double length = 20.0;
        int steps = 40;
        boolean hasDamaged = false;

        for (int i = 0; i < steps; i++) {
            double factor = (double) i / steps;
            Vec3 pos = from.add(direction.scale(length * factor));
            BlockPos blockPos = BlockPos.containing(pos);

            BlockState blockState = level.getBlockState(blockPos);
            if (!blockState.isAir() && !blockState.getCollisionShape(level, blockPos).isEmpty()) {
                break;
            }

            level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 5, 0.1, 0.1, 0.1, 0.001);
            level.sendParticles(ParticleTypes.SMALL_FLAME, pos.x, pos.y, pos.z, 2, 0, 0, 0, 0);

            if (!hasDamaged && target.distanceToSqr(pos.x, pos.y, pos.z) < 1.0) {
                target.hurt(level.damageSources().mobAttack(this), 8.0F);
                hasDamaged = true;
            }
        }
    }

    private void teleportWithinArena() {
        if (!(this.level() instanceof ServerLevel level)) return;

        BlockPos center = HollowArenaEvent.getArenaCenter();
        int radius = HollowArenaEvent.getArenaSize() - 1;
        RandomSource rand = this.getRandom();

        for (int attempt = 0; attempt < 10; attempt++) {
            int dx = rand.nextInt(radius * 2 + 1) - radius;
            int dz = rand.nextInt(radius * 2 + 1) - radius;
            int x = center.getX() + dx;
            int z = center.getZ() + dz;
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

            BlockPos pos = new BlockPos(x, y, z);

            boolean groundSolid = level.getBlockState(pos.below()).isSolid();
            boolean headClear = level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above());

            if (groundSolid && headClear) {
                this.teleportTo(x + 0.5, y, z + 0.5);
                level.sendParticles(ParticleTypes.PORTAL, x + 0.5, y + 1, z + 0.5, 50, 0.5, 1, 0.5, 0.1);
                break;
            }
        }
    }

    private static int oneSun(ServerPlayer player) {
        if (!ONE) {
            scheduler.schedule(() -> {
                MutableComponent message = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Хорошо!").withStyle(ChatFormatting.AQUA));
                player.sendSystemMessage(message);
            }, 5, TimeUnit.SECONDS);
        }

        ONE = true;
        return 1;
    }

    private static int twoSun(ServerPlayer player) {
        if (!TWO) {
            scheduler.schedule(() -> {
                MutableComponent message1 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Что...?!").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message1);
            }, 5, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message2 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Я... я не могу убить его...").withStyle(ChatFormatting.AQUA));
                player.sendSystemMessage(message2);
            }, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message3 = Component.literal("[ВЫ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Прости...").withStyle(ChatFormatting.AQUA));
                player.sendSystemMessage(message3);
            }, 15, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message4 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Нет!").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message4);
            }, 20, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                MutableComponent message5 = Component.literal("[ПУСТОЙ]").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(" Ты не понимаешь!").withStyle(ChatFormatting.DARK_PURPLE));
                player.sendSystemMessage(message5);
            }, 25, TimeUnit.SECONDS);

            HollowSun boss = ACTIVE_SUN;
            boss.setHealth(150.0F);

            scheduler.schedule(() -> {
                MutableComponent message6 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(" Не мешай!").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(message6);

                if (ACTIVE_SUN != null && !ACTIVE_SUN.level().isClientSide()) {
                    ServerLevel level = (ServerLevel) ACTIVE_SUN.level();

                    List<HollowEntity> targets = level.getEntitiesOfClass(HollowEntity.class,
                            new AABB(boss.blockPosition()).inflate(20), e -> true);

                    if (!targets.isEmpty()) {
                        HollowEntity h = targets.get(0);

                        level.sendParticles(ParticleTypes.END_ROD,
                                boss.getX(), boss.getY() + 1, boss.getZ(),
                                30, 0.3, 0.3, 0.3, 0.01);
                        level.playSound(null, boss.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.0F, 1.0F);

                        scheduler.schedule(() -> {
                            Vec3 from = boss.position().add(0, boss.getEyeHeight(), 0);
                            Vec3 to = h.position().add(0, h.getBbHeight() / 2, 0);
                            Vec3 direction = to.subtract(from).normalize();

                            for (int i = 0; i < 20; i++) {
                                Vec3 pos = from.add(direction.scale(i));
                                level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 2, 0, 0, 0, 0);
                            }

                            level.sendParticles(ParticleTypes.EXPLOSION, h.getX(), h.getY() + 1, h.getZ(),
                                    50, 1, 1, 1, 0.1);
                            level.playSound(null, boss.getX(), boss.getY(), boss.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.0F, 1.0F);
                            player.push((level.random.nextDouble() - 0.5) * 0.5, 0.2, (level.random.nextDouble() - 0.5) * 0.5);

                            h.discard();

                            float newHp = boss.getHealth() + boss.getMaxHealth() / 2;
                            boss.setHealth(Math.min(boss.getMaxHealth(), newHp));

                            boss.setNoAi(false);
                            boss.setInvulnerable(false);
                            boss.passivePhaseStarted = false;

                            scheduler.schedule(() -> {
                                MutableComponent message7 = Component.literal("[Лучезарность]").withStyle(ChatFormatting.GOLD)
                                        .append(Component.literal(" Ну давай, нападай!").withStyle(ChatFormatting.WHITE));
                                player.sendSystemMessage(message7);
                            }, 5, TimeUnit.SECONDS);

                            level.sendParticles(ParticleTypes.FLAME, boss.getX(), boss.getY() + 1, boss.getZ(),
                                    80, 0.5, 0.5, 0.5, 0.01);

                        }, 2, TimeUnit.SECONDS);
                    }
                }
            }, 30, TimeUnit.SECONDS);
        }

        TWO = true;
        return 1;
    }
}