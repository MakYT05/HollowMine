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
import yt.mak.hollowmine.client.anim.HollowTraderAnim;
import yt.mak.hollowmine.custom.entity.HollowTrader;

public class HollowTraderModel<T extends HollowTrader> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "hollow_trader"), "main");
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

	public HollowTraderModel(ModelPart root) {
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

		PartDefinition Body = Model.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition BodyUp = Body.addOrReplaceChild("BodyUp", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -6.0F, -2.0F, 12.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition Head = BodyUp.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition GolovaAnimated = Head.addOrReplaceChild("GolovaAnimated", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition DontTouch = GolovaAnimated.addOrReplaceChild("DontTouch", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -4.0F));

		PartDefinition LeftArm = BodyUp.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 12).addBox(1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, -4.0F, 0.0F));

		PartDefinition LeftHand = LeftArm.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 4.0F, 0.0F));

		PartDefinition RightArm = BodyUp.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(32, 23).addBox(-5.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, -4.0F, 0.0F));

		PartDefinition RightHand = RightArm.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(16, 39).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, 4.0F, 0.0F));

		PartDefinition RightHandItem = RightHand.addOrReplaceChild("RightHandItem", CubeListBuilder.create(), PartPose.offset(0.5F, 4.5F, -1.0F));

		PartDefinition LeftLeg = Model.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(32, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 5.0F, 0.0F));

		PartDefinition LeftStep = LeftLeg.addOrReplaceChild("LeftStep", CubeListBuilder.create().texOffs(32, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition RightLeg = Model.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(36, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 5.0F, 0.0F));

		PartDefinition RightStep = RightLeg.addOrReplaceChild("RightStep", CubeListBuilder.create().texOffs(48, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition bone = Model.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(48, 24).addBox(21.75F, 3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 27).addBox(23.75F, 3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 30).addBox(25.75F, 3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 21).addBox(27.75F, 3.5F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 33).addBox(29.5F, 2.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 36).addBox(29.5F, 0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 39).addBox(29.5F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 42).addBox(28.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 52).addBox(30.5F, -3.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 33).addBox(20.0F, -3.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 36).addBox(20.0F, -5.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 39).addBox(20.0F, -7.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 42).addBox(21.0F, -9.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 45).addBox(22.0F, -11.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 52).addBox(30.5F, -5.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 24).addBox(30.5F, -7.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 27).addBox(29.5F, -9.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 30).addBox(28.5F, -11.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 54).addBox(20.75F, 2.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 48).addBox(20.75F, 0.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 51).addBox(20.75F, -1.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 54).addBox(21.75F, -3.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 45).addBox(22.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 48).addBox(24.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 49).addBox(26.75F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 49).addBox(21.75F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 49).addBox(21.75F, 0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 49).addBox(21.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 49).addBox(23.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 51).addBox(25.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(27.75F, 1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 0).addBox(23.75F, -0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 3).addBox(23.75F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 52).addBox(25.75F, -0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 6).addBox(25.75F, -1.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 52).addBox(27.75F, -0.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 9).addBox(27.75F, -1.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.25F, -12.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(HollowTrader entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(HollowTraderAnim.GO, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, HollowTraderAnim.GO, ageInTicks, 1f);
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