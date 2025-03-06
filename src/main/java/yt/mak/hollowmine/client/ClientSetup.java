package yt.mak.hollowmine.client;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void registerArmor(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HMArmorModel.LAYER_LOCATION, HMArmorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addArmorRenderer(EntityRenderersEvent.AddLayers event) {
        EntityRendererProvider.Context context = event.getContext();

        EntityRenderer<ArmorEntity> armorRenderer = new ArmorRenderer<>(context);

        EntityRenderers.register(ArmorEntity.class, armorRenderer);
        
        armorRenderer.addLayer(new HMArmorRenderer<>(armorRenderer, event.getEntityModels()));
    }
}
