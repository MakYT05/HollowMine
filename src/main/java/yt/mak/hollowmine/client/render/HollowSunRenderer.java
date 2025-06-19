package yt.mak.hollowmine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.HollowSunModel;
import yt.mak.hollowmine.custom.entities.HollowSun;

public class HollowSunRenderer extends MobRenderer<HollowSun, HollowSunModel<HollowSun>> {
    public HollowSunRenderer(EntityRendererProvider.Context context) {
        super(context, new HollowSunModel<>(context.bakeLayer(HollowSunModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HollowSun entity) {
        return ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "textures/entity/hollow_sun.png");
    }

    @Override
    public void render(HollowSun pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1f, 1f, 1f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}