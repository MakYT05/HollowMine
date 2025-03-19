package yt.mak.hollowmine.custom.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yt.mak.hollowmine.command.HollowMaskCommand;
import yt.mak.hollowmine.effect.MakEffects;

public class HollowMana extends Item {
    public HollowMana(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (HollowMaskCommand.MASK_READY) {
            if (!level.isClientSide) {
                player.addEffect(new MobEffectInstance(MakEffects.HOLLOW_EFFECT.getHolder().get(), 10000 * 3, 0));

                ItemStack itemStack = player.getItemInHand(hand);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}