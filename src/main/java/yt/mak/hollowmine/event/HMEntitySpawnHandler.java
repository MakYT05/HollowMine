package yt.mak.hollowmine.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entity.HollowKnight;
import yt.mak.hollowmine.init.items.HMItems;

//@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class HMEntitySpawnHandler {
//    @SubscribeEvent
//    public static void onEntityJoin(EntityJoinLevelEvent event) {
//        if (event.getEntity() instanceof HollowKnight hollowKnight) {
//            if (!hollowKnight.level().isClientSide()) {
//                hollowKnight.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(HMItems.DREAM_NAIL.get()));
//                hollowKnight.setPersistenceRequired();
//            }
//        }
//    }
//}