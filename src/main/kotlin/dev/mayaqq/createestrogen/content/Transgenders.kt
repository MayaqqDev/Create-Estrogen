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

// Block entities
// these need to be inline/crossinline for server-side safety
inline fun <BE : BlockEntity> BlockEntityBuilder<BE>.visual(crossinline factory: (VisualizationContext, BE, Float) -> BlockEntityVisual<in BE>, noinline predicate: (BE) -> Boolean = { true }) {
    clientOnly {
        onRegister {
            val builder = SimpleBlockEntityVisualizer.builder(it)
                .factory { ctx, be, f -> factory(ctx, be, f) }
            predicate.let { builder.skipVanillaRender(it) }
            builder.apply()
        }
    }
}

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