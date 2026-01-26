package dev.mayaqq.createestrogen.client.content.entityRenderers.goggles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.id
import dev.mayaqq.cynosure.client.models.ModelData
import dev.mayaqq.cynosure.client.models.animations.Animatable
import dev.mayaqq.cynosure.client.models.animations.animate
import dev.mayaqq.cynosure.client.models.entity.AnimationDataLoader
import dev.mayaqq.cynosure.client.models.entity.ModelDataLoader
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.client.cosmetics.Cosmetic.Companion.animationTime
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.common.SlotInfo
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

class CatEarGogglesBaubleRenderer : BaubleRenderer {

    override fun render(
        stack: ItemStack,
        slotInfo: SlotInfo,
        pose: PoseStack,
        model: EntityModel<out LivingEntity>,
        buffer: MultiBufferSource,
        light: Int,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        if (model is HumanoidModel<out LivingEntity>) {
            pose.pushPop {
                val head: ModelPart = model.head
                //translate(model.head.x / 16.0, model.head.y / 16.0, model.head.z / 16.0)
                mulPose(Axis.ZP.rotation(head.zRot))
                mulPose(Axis.YP.rotation(head.yRot))
                mulPose(Axis.XP.rotation(head.xRot))

                /*animation?.let {
                    (model as? Animatable.Provider)?.animate(animation, animationTime)
                }
                 */
                val buffer = buffer.getBuffer(RenderType.entityCutoutNoCull(id("textures/item/cat_ears/cat_ears_black.png")))
                ears.render(buffer, pose, light = light)
            }
        }
    }

    companion object {
        val ears by lazy { ModelDataLoader.loadAndBakeModel(id("cat_ears")) }
        val animation by lazy { AnimationDataLoader.getAnimation(id("cat_ears")) }
    }
}