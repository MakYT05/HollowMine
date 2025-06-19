package yt.mak.hollowmine.client.model;

import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.client.anim.HollowDieAnim;
import yt.mak.hollowmine.client.anim.HollowEntityAnim;
import yt.mak.hollowmine.custom.entities.HollowDie;

public class HollowDieModel<T extends HollowDie> extends HierarchicalModel<T>{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "hollow_die"), "main");
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
	private final ModelPart bone;

	public HollowDieModel(ModelPart root) {
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
		this.bone = this.Model.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Model = partdefinition.addOrReplaceChild("Model", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition Body = Model.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 27).addBox(-5.0F, -5.0F, -2.0F, 10.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition BodyUp = Body.addOrReplaceChild("BodyUp", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -6.0F, -2.0F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition Head = BodyUp.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition GolovaAnimated = Head.addOrReplaceChild("GolovaAnimated", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition DontTouch = GolovaAnimated.addOrReplaceChild("DontTouch", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -4.0F));

		PartDefinition LeftArm = BodyUp.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(28, 16).addBox(0.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, -4.0F, 0.0F));

		PartDefinition LeftHand = LeftArm.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(16, 38).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 4.0F, 0.0F));

		PartDefinition RightArm = BodyUp.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(28, 27).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, -4.0F, 0.0F));

		PartDefinition RightHand = RightArm.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(32, 38).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, 4.0F, 0.0F));

		PartDefinition RightHandItem = RightHand.addOrReplaceChild("RightHandItem", CubeListBuilder.create(), PartPose.offset(0.5F, 4.5F, -1.0F));

		PartDefinition LeftLeg = Model.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 5.0F, 0.0F));

		PartDefinition LeftStep = LeftLeg.addOrReplaceChild("LeftStep", CubeListBuilder.create().texOffs(44, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition RightLeg = Model.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 36).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 5.0F, 0.0F));

		PartDefinition RightStep = RightLeg.addOrReplaceChild("RightStep", CubeListBuilder.create().texOffs(44, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition bone = Model.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(44, 31).addBox(21.75F, 3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 34).addBox(23.75F, 3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(25.75F, 3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 11).addBox(27.75F, 3.5F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 47).addBox(29.5F, 2.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0).addBox(29.5F, 0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 3).addBox(29.5F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 6).addBox(28.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 11).addBox(20.75F, 2.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 0).addBox(20.75F, 0.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 3).addBox(20.75F, -1.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 6).addBox(21.75F, -3.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 48).addBox(22.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 48).addBox(24.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 48).addBox(26.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 48).addBox(21.75F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 48).addBox(21.75F, 0.5F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 37).addBox(21.75F, 2.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 40).addBox(23.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 48).addBox(25.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 43).addBox(27.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 46).addBox(23.75F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 49).addBox(23.75F, -1.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 50).addBox(24.75F, -0.5F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 50).addBox(25.75F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 31).addBox(27.75F, -0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 14).addBox(27.75F, -1.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 34).addBox(30.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 51).addBox(30.75F, -5.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 51).addBox(30.75F, -7.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 51).addBox(29.75F, -9.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 51).addBox(28.75F, -11.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 53).addBox(20.75F, -9.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 53).addBox(19.75F, -7.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 52).addBox(19.75F, -5.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 51).addBox(19.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 51).addBox(21.75F, -11.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.25F, -12.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(HollowDie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(HollowEntityAnim.GO, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, HollowDieAnim.GO, ageInTicks, 1f);
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