package yt.mak.hollowmine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.HollowPoisonModel;
import yt.mak.hollowmine.custom.entities.HollowPoison;

public class HollowPoisonRenderer extends MobRenderer<HollowPoison, HollowPoisonModel<HollowPoison>> {
    public HollowPoisonRenderer(EntityRendererProvider.Context context) {
        super(context, new HollowPoisonModel<>(context.bakeLayer(HollowPoisonModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HollowPoison entity) {
        return ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "textures/entity/hollow_poison.png");
    }

    @Override
    public void render(HollowPoison pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1f, 1f, 1f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}