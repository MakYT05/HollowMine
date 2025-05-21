package yt.mak.hollowmine.custom.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraftforge.client.event.RenderHighlightEvent;
import yt.mak.hollowmine.init.entity.HMEntities;
import yt.mak.hollowmine.init.items.HMItems;

import javax.annotation.Nullable;

public class HollowDie extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(36, ItemStack.EMPTY);
    private int idleAnimationTimeout = 0;
    private double lastX;
    private double lastY;
    private double lastZ;

    public HollowDie(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(HMItems.HOLLOW.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return HMEntities.HOLLOW_DIE.get().create(pLevel);
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
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
        } else {
            boolean hasNearbyPlayers = !this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(10)).isEmpty();
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(hasNearbyPlayers ? 0.35 : 0.0);
        }

        if (!this.level().isClientSide && this.tickCount % 5 == 0) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() - 1, this.getY() + 1, this.getZ(),
                    3,
                    0.3, 0.3, 0.3,
                    0.02
            );
        }
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }
    }
}