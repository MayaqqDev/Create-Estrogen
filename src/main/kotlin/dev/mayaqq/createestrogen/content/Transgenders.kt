package dev.mayaqq.createestrogen.content


import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import earth.terrarium.common_storage_lib.storage.base.CommonStorage
import earth.terrarium.common_storage_lib.storage.base.StorageSlot
import invoke.kitty.kritter.registry.blockEntity.BlockEntityBuilder
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.material.Fluids

typealias FluidContainer = CommonStorage<FluidResource>

operator fun FluidContainer.iterator() = object : Iterator<StorageSlot<FluidResource>> {
    var currentIdx = 0
    val inner = this@iterator
    override fun next(): StorageSlot<FluidResource> {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        return inner[currentIdx++]
    }

    override fun hasNext(): Boolean =
        currentIdx < inner.size()
}

fun FluidContainer.isEmpty(): Boolean {
    for (i in this) if (!i.resource.isOf(Fluids.EMPTY) && i.amount > 0) return false
    return true
}