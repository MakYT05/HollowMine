package yt.mak.hollowmine.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.anim.HollowFlyAnim;
import yt.mak.hollowmine.custom.entities.HollowFly;

public class HollowFlyModel<T extends HollowFly> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "hollow_fly"), "main");
	private final ModelPart bone;

	public HollowFlyModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -26.0F, -1.0F, 8.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-5.0F, -29.0F, -8.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(30, 25).addBox(-2.0F, -25.0F, -10.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 17).addBox(0.0F, -26.0F, 3.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 21).addBox(-10.0F, -26.0F, 3.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 31).addBox(0.0F, -2.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -31.0F, -5.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 31).addBox(0.0F, -2.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -31.0F, -5.0F, 0.0F, 0.0F, -0.2618F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(HollowFly entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(HollowFlyAnim.FLY, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, HollowFlyAnim.FLY, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return bone;
	}
}