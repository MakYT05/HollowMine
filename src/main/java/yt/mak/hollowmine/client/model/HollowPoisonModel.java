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
import yt.mak.hollowmine.client.anim.HollowEntityAnim;
import yt.mak.hollowmine.client.anim.HollowPoisonAnim;
import yt.mak.hollowmine.custom.entities.HollowPoison;

public class HollowPoisonModel<T extends HollowPoison> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "hollow_poison"), "main");
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;

	public HollowPoisonModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = this.bone.getChild("bone2");
		this.bone3 = this.bone.getChild("bone3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -31.0F, -3.0F, 14.0F, 21.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-4.0F, -42.0F, -5.0F, 10.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(12, 48).addBox(-3.0F, -28.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 48).addBox(4.0F, -18.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(28, 48).addBox(3.0F, -30.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 0).addBox(-5.0F, -15.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 4).addBox(-1.0F, -21.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 8).addBox(-9.0F, -28.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 52).addBox(-11.0F, -14.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 28).addBox(-6.0F, -41.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 32).addBox(-6.0F, -35.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 36).addBox(-3.0F, -35.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 40).addBox(3.0F, -38.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 44).addBox(6.0F, -38.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 48).addBox(6.0F, -34.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 52).addBox(2.0F, -44.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 55).addBox(-3.0F, -44.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 0).addBox(-9.0F, -28.0F, -1.5F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 21).addBox(-1.0F, -13.0F, -2.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 0.5F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(52, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -23.0F, 2.5F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(52, 12).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -14.0F, -0.5F, 0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 48).addBox(4.0F, -10.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 52).addBox(7.0F, -7.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 20).addBox(4.0F, -3.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(40, 42).addBox(-4.0F, -10.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(28, 52).addBox(-3.0F, -8.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 24).addBox(-1.0F, -3.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(HollowPoison entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(HollowEntityAnim.GO, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, HollowPoisonAnim.WALK, ageInTicks, 1f);
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