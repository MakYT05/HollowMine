package yt.mak.hollowmine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.HollowBeatleModel;
import yt.mak.hollowmine.custom.entity.HollowBeatle;

public class HollowBeatleRenderer extends MobRenderer<HollowBeatle, HollowBeatleModel<HollowBeatle>> {
    public HollowBeatleRenderer(EntityRendererProvider.Context context) {
        super(context, new HollowBeatleModel<>(context.bakeLayer(HollowBeatleModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HollowBeatle entity) {
        return ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "textures/entity/hollow_beatle.png");
    }

    @Override
    public void render(HollowBeatle pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1f, 1f, 1f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}