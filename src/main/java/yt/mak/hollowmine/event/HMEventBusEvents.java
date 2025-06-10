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
import yt.mak.hollowmine.client.model.*;
import yt.mak.hollowmine.custom.entity.*;
import yt.mak.hollowmine.init.entity.HMEntities;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HMEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HollowEntityModel.LAYER_LOCATION, HollowEntityModel::createBodyLayer);
        event.registerLayerDefinition(HollowFlyModel.LAYER_LOCATION, HollowFlyModel::createBodyLayer);
        event.registerLayerDefinition(HollowBeatleModel.LAYER_LOCATION, HollowBeatleModel::createBodyLayer);
        event.registerLayerDefinition(HollowDieModel.LAYER_LOCATION, HollowDieModel::createBodyLayer);
        event.registerLayerDefinition(HollowSunModel.LAYER_LOCATION, HollowSunModel::createBodyLayer);
        event.registerLayerDefinition(HollowGoodSunModel.LAYER_LOCATION, HollowGoodSunModel::createBodyLayer);
        event.registerLayerDefinition(MakModel.LAYER_LOCATION, MakModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HMEntities.HOLLOW_ENTITY.get(), HollowEntity.createAttributes().build());
        event.put(HMEntities.HOLLOW_FLY.get(), HollowFly.createAttributes().build());
        event.put(HMEntities.HOLLOW_KNIGHT.get(), HollowKnight.createAttributes().build());
        event.put(HMEntities.HOLLOW_BEATLE.get(), HollowBeatle.createAttributes().build());
        event.put(HMEntities.HOLLOW_DIE.get(), HollowDie.createAttributes().build());
        event.put(HMEntities.HOLLOW_SUN.get(), HollowSun.createAttributes().build());
        event.put(HMEntities.HOLLOW_GOOD_SUN.get(), HollowGoodSun.createAttributes().build());
        event.put(HMEntities.MAK.get(), Mak.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(HMEntities.HOLLOW_BEATLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(HMEntities.HOLLOW_FLY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(HMEntities.HOLLOW_DIE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(HMEntities.HOLLOW_SUN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(HMEntities.HOLLOW_GOOD_SUN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}