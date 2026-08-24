package dev.mayaqq.createestrogen.client

import com.simibubi.create.AllPartialModels
import com.simibubi.create.infrastructure.gui.CreateMainMenuScreen
import dev.engine_room.flywheel.lib.model.baked.PartialModel
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.client.content.screen.OpenEstrogenMenuButton
import dev.mayaqq.createestrogen.config.CreateEstrogenClientConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenCommonConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenServerConfig
import dev.mayaqq.createestrogen.content.CreateEstrogenPonderPlugin
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles
import dev.mayaqq.cynosure.client.events.screen.ScreenEvents
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.events.api.MainBus
import dev.mayaqq.estrogen.client.content.screen.config.ConfigCategorySelectionScreen
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.platform.forge.modContainer
import net.createmod.ponder.foundation.PonderIndex
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.contents.TranslatableContents
import net.neoforged.neoforge.client.gui.IConfigScreenFactory

@EntrypointHandler("client")
fun createEstrogenClient(mod: Mod) {
    CreateEstrogenClientConfig.initialize();

    PonderIndex.addPlugin(CreateEstrogenPonderPlugin)
    for (style in CreateEstrogenPackageStyles.estrogenPillStyles) {
        AllPartialModels.PACKAGES[style.itemId] = PartialModel.of(identifier(MOD_ID, "item/${style.itemId.path}"))
        AllPartialModels.PACKAGE_RIGGING[style.itemId] = PartialModel.of(style.riggingModel)
    }


    mod.modContainer!!.registerExtensionPoint(
        IConfigScreenFactory::class.java,
        IConfigScreenFactory { _, screen -> ConfigCategorySelectionScreen(screen, listOf(
            CreateEstrogenClientConfig,
            CreateEstrogenCommonConfig,
            CreateEstrogenServerConfig
        )) }
    )
}

fun initClientEvents() {
    MainBus.register<ScreenEvents.AfterInit> { event ->
        if (event.screen is CreateMainMenuScreen && CreateEstrogenClientConfig.EstrogenButton.enabled) {
            event.listeners
                .filterIsInstance<Button>()
                .firstOrNull { (it.message.contents as? TranslatableContents)?.key == "create.menu.configure" }?.let {
                    event.screen.addRenderableWidget(
                        OpenEstrogenMenuButton(
                            it.getX() + CreateEstrogenClientConfig.EstrogenButton.xOffset,
                            it.getY() + CreateEstrogenClientConfig.EstrogenButton.yOffset
                        )
                    )
                }
        }
    }
}