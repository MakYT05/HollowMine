package yt.mak.hollowmine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.HollowKnightModel;
import yt.mak.hollowmine.custom.entity.HollowKnight;

public class HollowKnightRenderer extends MobRenderer<HollowKnight, HollowKnightModel<HollowKnight>>{
    public HollowKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new HollowKnightModel<>(context.bakeLayer(HollowKnightModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HollowKnight entity) {
        return ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "textures/entity/hollow_entity.png");
    }

    @Override
    public void render(HollowKnight pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1f, 1f, 1f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}