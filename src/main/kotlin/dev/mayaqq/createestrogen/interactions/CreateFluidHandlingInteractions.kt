package dev.mayaqq.createestrogen.interactions

import com.simibubi.create.api.behaviour.spouting.BlockSpoutingBehaviour
import com.simibubi.create.api.behaviour.spouting.CauldronSpoutingBehavior
import com.simibubi.create.api.behaviour.spouting.StateChangingBehavior
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenFluids
import net.minecraft.world.level.block.LayeredCauldronBlock

@Suppress("UnstableApiUsage")
object CreateFluidHandlingInteractions {

    val fluidMap by lazy { mapOf(
        EstrogenBlocks.LiquidEstrogenCauldron to EstrogenFluids.LiquidEstrogen,
        EstrogenBlocks.FiltratedHorseUrineCauldron to EstrogenFluids.FiltratedHorseUrine,
        EstrogenBlocks.HorseUrineCauldron to EstrogenFluids.HorseUrine
    ) }

    fun init() {
        fluidMap.forEach { (cauldron, fluid) ->
            BlockSpoutingBehaviour.BY_BLOCK.register(cauldron.get(), StateChangingBehavior.setTo(1000, { test ->
                test.isSame(fluid.get())
            }, cauldron.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3)))

            CauldronSpoutingBehavior.CAULDRON_INFO.register(fluid.get(), CauldronSpoutingBehavior.CauldronInfo(
                1000,
                cauldron.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3)
            ))
        }
    }
}