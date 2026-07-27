package dev.mayaqq.createestrogen.client.content.blockRenderers.centrifuge

import com.mojang.blaze3d.vertex.PoseStack
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer
import dev.mayaqq.createestrogen.content.blockEntities.CentrifugeBlockEntity
import net.createmod.catnip.platform.CatnipServices
import net.createmod.catnip.render.CachedBuffers
import net.createmod.catnip.render.SuperByteBuffer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities


class CentrifugeRenderer(ctx: BlockEntityRendererProvider.Context) : KineticBlockEntityRenderer<CentrifugeBlockEntity>(ctx) {
    override fun getRotatedModel(be: CentrifugeBlockEntity, state: BlockState): SuperByteBuffer
        = CachedBuffers.partial(CreateEstrogenRenderer.CENTRIFUGE_COG,state)


    override fun renderSafe(
        be: CentrifugeBlockEntity,
        partialTicks: Float,
        ms: PoseStack,
        buffer: MultiBufferSource,
        light: Int,
        overlay: Int
    ) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay)
        val level: Level? = be.level
        if (level == null || !level.isClientSide) return
        val up: BlockEntity? = getBlockEntity(level, be.blockPos.above())
        val down: BlockEntity? = getBlockEntity(level, be.blockPos.below())
        renderFluid(level, up, true, buffer, ms, light)
        renderFluid(level, down, false, buffer, ms, light)
    }
    private fun renderFluid(
        level: Level,
        blockEntity: BlockEntity?,
        isTop: Boolean,
        buffer: MultiBufferSource,
        ms: PoseStack,
        light: Int
    ) {
        if (blockEntity != null) {
            val container = level.getCapability(
                Capabilities.FluidHandler.BLOCK,
                blockEntity.blockPos,
                null
            )
            if (container != null) {
                val fluid = (0 until container.tanks)
                    .map(container::getFluidInTank)
                    .firstOrNull { !it.isEmpty }
                if (fluid != null) {
                    val yMin = if (isTop) 0.71f else 0.01f
                    val yMax = if (isTop) 0.97f else 0.3f
                    CatnipServices.FLUID_RENDERER.renderFluidBox(
                        fluid.fluid.defaultFluidState(),
                        0.01f,
                        yMin,
                        0.01f,
                        0.99f,
                        yMax,
                        0.99f,
                        buffer,
                        ms,
                        light,
                        false,
                        false
                    )
                }
            }
        }
    }
    private fun getBlockEntity(level: Level, pos: BlockPos): BlockEntity? {
        try {
            val be = level.getBlockEntity(pos)
            return if (be is FluidTankBlockEntity) {
                be.controllerBE
            } else {
                be
            }
        } catch (_: Exception) {
            return null
        }
    }

}
