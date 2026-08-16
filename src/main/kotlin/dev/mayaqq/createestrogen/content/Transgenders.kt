package dev.mayaqq.createestrogen.content


import com.simibubi.create.foundation.item.TooltipModifier
import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import dev.mayaqq.createestrogen.interactions.CreateTooltip
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.cynosure.items.extensions.registerExtension
import dev.mayaqq.cynosure.tooltips.DescriptionTooltip
import dev.mayaqq.estrogen.content.tooltip
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import earth.terrarium.common_storage_lib.storage.base.CommonStorage
import earth.terrarium.common_storage_lib.storage.base.StorageSlot
import invoke.kitty.kritter.registry.blockEntity.BlockEntityBuilder
import invoke.kitty.kritter.registry.item.ItemBuilder
import invoke.kitty.kritter.utils.clientOnly
import net.createmod.catnip.lang.FontHelper
import net.minecraft.network.chat.Style
import net.minecraft.world.item.Item
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

inline fun ItemBuilder<*>.createTooltip(crossinline tooltip: (Item) -> TooltipModifier) {
    tooltip { item -> CreateTooltip(tooltip.invoke(item)) }
}

inline val DescriptionTooltip.Theme.create
    get() = FontHelper.Palette(Style.EMPTY.withColor(this.primaryColor.toInt()), Style.EMPTY.withColor(this.highlightColor.toInt())
)