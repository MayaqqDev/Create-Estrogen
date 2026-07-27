package dev.mayaqq.createestrogen.compat.recipeviewers.recipes

import dev.mayaqq.createestrogen.client.content.blockRenderers.centrifuge.CreateEstrogenRenderer
import dev.mayaqq.createestrogen.compat.recipeviewers.api.GenericAnimatedKinetics
import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import dev.mayaqq.cynosure.client.utils.pushPop
import net.minecraft.client.gui.GuiGraphics

class CentrifugeBlockElement : GenericAnimatedKinetics() {
    override fun draw(
        graphics: GuiGraphics,
        offsetX: Int,
        offsetY: Int,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
        val scale = 22.0
        graphics.pushPop {
            translate(offsetX.toDouble(), offsetY.toDouble(), 0.0)

            blockElement(CreateEstrogenRenderer.CENTRIFUGE_COG)
                .rotateBlock(22.5, (getCurrentAngle() * 10).toDouble(), 0.0)
                .scale(scale)
                .render(graphics)
            blockElement(CreateEstrogenBlocks.Centrifuge.value!!.defaultBlockState())
                .rotateBlock(22.5, 22.5, 0.0)
                .scale(scale)
                .render(graphics)
        }
    }
}
