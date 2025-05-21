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
import yt.mak.hollowmine.client.anim.HollowSunAnim;
import yt.mak.hollowmine.custom.entity.HollowSun;

public class HollowSunModel <T extends HollowSun> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "hollow_sun"), "main");
	private final ModelPart Model;
	private final ModelPart Body;
	private final ModelPart BodyUp;
	private final ModelPart Head;
	private final ModelPart GolovaAnimated;
	private final ModelPart DontTouch;
	private final ModelPart LeftArm;
	private final ModelPart LeftHand;
	private final ModelPart RightArm;
	private final ModelPart RightHand;
	private final ModelPart RightHandItem;
	private final ModelPart LeftLeg;
	private final ModelPart LeftStep;
	private final ModelPart RightLeg;
	private final ModelPart RightStep;

	public HollowSunModel(ModelPart root) {
		this.Model = root.getChild("Model");
		this.Body = this.Model.getChild("Body");
		this.BodyUp = this.Body.getChild("BodyUp");
		this.Head = this.BodyUp.getChild("Head");
		this.GolovaAnimated = this.Head.getChild("GolovaAnimated");
		this.DontTouch = this.GolovaAnimated.getChild("DontTouch");
		this.LeftArm = this.BodyUp.getChild("LeftArm");
		this.LeftHand = this.LeftArm.getChild("LeftHand");
		this.RightArm = this.BodyUp.getChild("RightArm");
		this.RightHand = this.RightArm.getChild("RightHand");
		this.RightHandItem = this.RightHand.getChild("RightHandItem");
		this.LeftLeg = this.Model.getChild("LeftLeg");
		this.LeftStep = this.LeftLeg.getChild("LeftStep");
		this.RightLeg = this.Model.getChild("RightLeg");
		this.RightStep = this.RightLeg.getChild("RightStep");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Model = partdefinition.addOrReplaceChild("Model", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -60.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition Body = Model.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(68, 41).addBox(-7.0F, -21.0F, -4.0F, 14.0F, 21.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition BodyUp = Body.addOrReplaceChild("BodyUp", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -42.0F, -5.0F, 26.0F, 27.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition Head = BodyUp.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(114, 21).addBox(-1.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -52.0F, 0.0F, -0.0856F, 0.0244F, 0.2484F));

		PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(108, 112).addBox(-1.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -52.0F, 0.0F, 0.006F, -0.0023F, 0.0005F));

		PartDefinition cube_r3 = Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(108, 21).addBox(-1.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -52.0F, 0.0F, 0.0204F, -0.0165F, -0.1727F));

		PartDefinition GolovaAnimated = Head.addOrReplaceChild("GolovaAnimated", CubeListBuilder.create().texOffs(0, 37).addBox(-9.0F, -52.0F, -7.0F, 18.0F, 16.0F, 14.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition DontTouch = GolovaAnimated.addOrReplaceChild("DontTouch", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -4.0F));

		PartDefinition LeftArm = BodyUp.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(12, 100).addBox(0.6946F, 14.5608F, -6.0F, 4.0F, 28.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(72, 103).addBox(0.6946F, -5.9392F, -6.0F, 4.0F, 20.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(11.0F, -33.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition LeftArm_r1 = LeftArm.addOrReplaceChild("LeftArm_r1", CubeListBuilder.create().texOffs(60, 102).addBox(5.6905F, -27.6252F, 3.0F, 4.0F, 20.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.0F, 22.0F, -6.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition LeftHand_r1 = LeftArm.addOrReplaceChild("LeftHand_r1", CubeListBuilder.create().texOffs(0, 100).addBox(5.6905F, -23.1252F, 3.0F, 4.0F, 28.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(7.0F, 37.25F, -6.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition LeftArm_r2 = LeftArm.addOrReplaceChild("LeftArm_r2", CubeListBuilder.create().texOffs(48, 102).addBox(5.3681F, -27.7588F, 3.0F, 4.0F, 20.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-4.0F, 22.0F, -3.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition LeftHand_r2 = LeftArm.addOrReplaceChild("LeftHand_r2", CubeListBuilder.create().texOffs(96, 70).addBox(5.3681F, -23.2588F, 3.0F, 4.0F, 28.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.0F, 37.5F, -3.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition LeftArm_r3 = LeftArm.addOrReplaceChild("LeftArm_r3", CubeListBuilder.create().texOffs(96, 100).addBox(16.0208F, -17.636F, 3.0F, 4.0F, 20.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-4.0F, 22.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition LeftHand = LeftArm.addOrReplaceChild("LeftHand", CubeListBuilder.create(), PartPose.offset(1.0F, 4.0F, 0.0F));

		PartDefinition LeftHand_r3 = LeftHand.addOrReplaceChild("LeftHand_r3", CubeListBuilder.create().texOffs(72, 70).addBox(16.0208F, -13.136F, 3.0F, 4.0F, 31.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.0F, 31.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition RightArm = BodyUp.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(96, 0).addBox(-4.8682F, 12.576F, 1.0F, 4.0F, 30.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(108, 70).addBox(-4.8682F, -6.924F, 1.0F, 4.0F, 19.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-11.0F, -32.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition RightArm_r1 = RightArm.addOrReplaceChild("RightArm_r1", CubeListBuilder.create().texOffs(108, 91).addBox(-10.5F, -28.3301F, -5.0F, 4.0F, 19.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-4.0F, 22.0F, 9.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition RightHand_r1 = RightArm.addOrReplaceChild("RightHand_r1", CubeListBuilder.create().texOffs(84, 70).addBox(-10.5F, -22.3301F, -5.0F, 4.0F, 31.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-8.5F, 34.75F, 9.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition RightArm_r2 = RightArm.addOrReplaceChild("RightArm_r2", CubeListBuilder.create().texOffs(108, 0).addBox(-9.7101F, -28.6985F, -5.0F, 4.0F, 19.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.0F, 22.0F, 3.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition RightHand_r2 = RightArm.addOrReplaceChild("RightHand_r2", CubeListBuilder.create().texOffs(24, 100).addBox(-9.7101F, -22.6985F, -5.0F, 4.0F, 27.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(2.0F, 35.5F, 3.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition RightArm_r3 = RightArm.addOrReplaceChild("RightArm_r3", CubeListBuilder.create().texOffs(84, 103).addBox(-10.1131F, -28.5315F, -5.0F, 4.0F, 19.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition RightHand = RightArm.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(36, 100).addBox(-5.1131F, 9.2185F, -5.0F, 4.0F, 25.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition RightHandItem = RightHand.addOrReplaceChild("RightHandItem", CubeListBuilder.create(), PartPose.offset(0.5F, 4.5F, -1.0F));

		PartDefinition LeftLeg = Model.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 27.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(4.0F, 5.0F, 0.0F));

		PartDefinition LeftStep = LeftLeg.addOrReplaceChild("LeftStep", CubeListBuilder.create().texOffs(48, 70).addBox(-3.0F, 21.0F, -3.0F, 6.0F, 26.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition RightLeg = Model.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(24, 67).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 27.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(-4.0F, 5.0F, 0.0F));

		PartDefinition RightStep = RightLeg.addOrReplaceChild("RightStep", CubeListBuilder.create().texOffs(72, 0).addBox(-3.0F, 21.0F, -3.0F, 6.0F, 26.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(HollowSun entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(HollowSunAnim.FLY, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, HollowSunAnim.FLY, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Model.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return Model;
	}
}