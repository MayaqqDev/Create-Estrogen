package dev.mayaqq.createestrogen.client.content.baubles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.pushPop
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.common.SlotInfo
import net.minecraft.client.Minecraft
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack


class EarsGogglesRenderer(part: ModelPart, val model: HumanoidModel<LivingEntity> = HumanoidModel<LivingEntity>(part)) : BaubleRenderer {
    override fun render(
        stack: ItemStack,
        slotInfo: SlotInfo,
        pose: PoseStack,
        entityModel: EntityModel<out LivingEntity>,
        buffer: MultiBufferSource,
        light: Int,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        model.setupAnim(slotInfo.wearer(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        model.prepareMobModel(slotInfo.wearer(), limbSwing, limbSwingAmount, partialTicks)
        if (entityModel is HumanoidModel) {
            pose.pushPop {
                mulPose(Axis.ZP.rotation(entityModel.head.zRot))
                mulPose(Axis.YP.rotation(entityModel.head.yRot))
                mulPose(Axis.XP.rotation(entityModel.head.xRot))

                translate(model.head.x / 16.0, model.head.y / 16.0, model.head.z / 16.0)
                mulPose(Axis.ZP.rotation(model.head.zRot))
                mulPose(Axis.YP.rotation(model.head.yRot))
                mulPose(Axis.XP.rotation(model.head.xRot))


                translate(0F, -0.25F, 0F)
                mulPose(Axis.ZP.rotationDegrees(180.0f))
                scale(0.625f, 0.625f, 0.625f)

                if (!slotInfo.wearer().getItemBySlot(EquipmentSlot.HEAD).isEmpty) {
                    mulPose(Axis.ZP.rotationDegrees(180.0f))
                    translate(0F, -0.25F, 0F)
                }


                // Render
                val mc = Minecraft.getInstance()
                mc.itemRenderer.renderStatic(stack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, pose, buffer, mc.level, 0)
            }
        }
    }
}