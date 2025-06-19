package yt.mak.hollowmine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.model.MakModel;
import yt.mak.hollowmine.custom.entities.Mak;

public class MakRenderer extends MobRenderer<Mak, MakModel<Mak>> {
    public MakRenderer(EntityRendererProvider.Context context) {
        super(context, new MakModel<>(context.bakeLayer(MakModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mak entity) {
        return ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "textures/entity/mak.png");
    }

    @Override
    public void render(Mak mak, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(mak, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}