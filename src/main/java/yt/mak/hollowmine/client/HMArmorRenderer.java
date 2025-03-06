package yt.mak.hollowmine.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import yt.mak.hollowmine.custom.items.HollowMaskArmorItem;

public class HMArmorRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("hollowmine", "textures/models/armor/hollow_arm_layer_1.png");
    private final HollowMaskArmorItem<T> model;

    public HMArmorRenderer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new HollowMaskArmorItem<>(modelSet.bakeLayer(HollowMaskArmorItem.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getEquipmentSlot() == EquipmentSlot.HEAD) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.renderToBuffer(
                        poseStack,
                        buffer.getBuffer(this.model.renderType(TEXTURE)),
                        packedLight,
                        LivingEntityRenderer.getOverlayCoords(entity, 0.0F),
                        1.0F, 1.0F, 1.0F, 1.0F
                );
            }
        }
    }
}