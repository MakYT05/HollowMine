package yt.mak.hollowmine.event;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.HollowEntityModel;
import yt.mak.hollowmine.client.model.HollowTraderModel;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.custom.entity.HollowTrader;
import yt.mak.hollowmine.init.entity.HMEntities;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HMEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HollowEntityModel.LAYER_LOCATION, HollowEntityModel::createBodyLayer);
        event.registerLayerDefinition(HollowTraderModel.LAYER_LOCATION, HollowTraderModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HMEntities.HOLLOW_ENTITY.get(), HollowEntity.createAttributes().build());
        event.put(HMEntities.HOLLOW_TRADER.get(), HollowTrader.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(HMEntities.HOLLOW_TRADER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}