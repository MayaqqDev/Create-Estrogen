package dev.mayaqq.createestrogen.client

import com.simibubi.create.AllPartialModels
import dev.engine_room.flywheel.lib.model.baked.PartialModel
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.config.CreateEstrogenClientConfig
import dev.mayaqq.createestrogen.content.CreateEstrogenPonderPlugin
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles
import dev.mayaqq.cynosure.core.identifier
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import net.createmod.ponder.foundation.PonderIndex
import net.minecraft.resources.ResourceLocation

@EntrypointHandler("client")
fun createEstrogenClient() {
    CreateEstrogenClientConfig.initialize();

    PonderIndex.addPlugin(CreateEstrogenPonderPlugin)
    for (style in CreateEstrogenPackageStyles.estrogenPillStyles) {
        AllPartialModels.PACKAGES[style.itemId] = PartialModel.of(identifier(MOD_ID, "item/${style.itemId.path}"))
        AllPartialModels.PACKAGE_RIGGING[style.itemId] = PartialModel.of(style.riggingModel)
    }

    /*
    @Suppress("Deprecation", "Removal")
    ModLoadingContext.get().activeContainer.registerExtensionPoint(
        ConfigScreenHandler.ConfigScreenFactory::class.java
    ) {
        ConfigScreenHandler.ConfigScreenFactory { _, screen ->
            ConfigCategorySelectionScreen(
                screen,
                listOf("createestrogen/client", "createestrogen/common", "createestrogen/server")
            )
        }
    }
     */
}