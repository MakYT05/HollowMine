package yt.mak.hollowmine.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.custom.items.HollowMaskArmorItem;

@Mod.EventBusSubscriber(modid = "hollowmine", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<?, ?> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new HMArmorRenderer<>(renderer, new HumanoidModel<>(event.getEntityModels().bakeLayer(HumanoidModelLayers.PLAYER))));
            }
        }
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HollowMaskArmorItem.LAYER_LOCATION, HollowMaskArmorItem::createBodyLayer);
    }
}