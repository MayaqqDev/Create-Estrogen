package dev.mayaqq.createestrogen.content


import dev.engine_room.flywheel.api.visual.BlockEntityVisual
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer
import invoke.kitty.kritter.registry.blockEntity.BlockEntityBuilder
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.world.level.block.entity.BlockEntity
import java.util.function.Predicate

// Block entities
// these need to be inline/crossinline for server-side safety
inline fun <BE : BlockEntity> BlockEntityBuilder<BE>.visual(crossinline factory: (VisualizationContext, BE, Float) -> BlockEntityVisual<in BE>, predicate: Predicate<BE> = Predicate { true }) {
    clientOnly {
        onSetup {
            val builder = SimpleBlockEntityVisualizer.builder(it)
                .factory { ctx, be, f -> factory(ctx, be, f) }
            builder.skipVanillaRender(predicate)
            builder.apply()
        }
    }
}

val matchIdRegex = Regex("[A-Za-z]+:.*")
