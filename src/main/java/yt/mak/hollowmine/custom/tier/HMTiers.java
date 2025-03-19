package yt.mak.hollowmine.custom.tier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum HMTiers implements Tier {
    DREAM_NAIL(2500, 9.0F, 12.0F, 22, SoundEvents.ANVIL_LAND, Ingredient.of(Blocks.AMETHYST_BLOCK));

    private final int durability;
    private final float speed;
    private final float attackDamage;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final Ingredient repairIngredient;

    HMTiers(int durability, float speed, float attackDamage, int enchantmentValue, SoundEvent sound, Ingredient repairIngredient) {
        this.durability = durability;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return durability;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return null;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    public SoundEvent getEquipSound() {
        return sound;
    }
}