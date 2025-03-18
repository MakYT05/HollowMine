package yt.mak.hollowmine.event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.HollowEntityModel;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.init.entity.HMEntities;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HMEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HollowEntityModel.LAYER_LOCATION, HollowEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HMEntities.HOLLOW_ENTITY.get(), HollowEntity.createAttributes().build());
    }
}