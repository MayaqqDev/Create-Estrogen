package dev.mayaqq.createestrogen.client.content.screen

import dev.mayaqq.createestrogen.config.CreateEstrogenClientConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenCommonConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenServerConfig
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.estrogen.client.content.screen.config.ConfigCategorySelectionScreen
import dev.mayaqq.estrogen.content.EstrogenItems
import invoke.kitty.kritter.utils.extensions.asStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.world.item.ItemStack

class OpenEstrogenMenuButton(x: Int, y: Int) : Button(
    x, y, 20, 20, CommonText.EMPTY, OnPress { button: Button -> click(button) }, DEFAULT_NARRATION
) {
    override fun renderString(graphics: GuiGraphics, pFont: Font, pColor: Int) {
        graphics.renderItem(ICON, getX() + 2, getY() + 2)
    }

    companion object {
        val ICON: ItemStack = EstrogenItems.EstrogenPill.asStack()

        fun click(button: Button) {
            McClient.setScreen(
                ConfigCategorySelectionScreen(McClient.screen, listOf(CreateEstrogenClientConfig, CreateEstrogenCommonConfig, CreateEstrogenServerConfig))
            )
        }
    }
}
