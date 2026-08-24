package dev.mayaqq.createestrogen

import com.simibubi.create.api.behaviour.spouting.BlockSpoutingBehaviour
import dev.mayaqq.createestrogen.client.initClientEvents
import dev.mayaqq.createestrogen.config.CreateEstrogenClientConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenCommonConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenServerConfig
import dev.mayaqq.createestrogen.content.*
import dev.mayaqq.createestrogen.interactions.CreateFluidHandlingInteractions
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.estrogen.api.EstrogenEntrypoint
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.api.EstrogenModule
import dev.mayaqq.estrogen.api.ScreenProvider
import dev.mayaqq.estrogen.client.content.screen.config.ConfigCategorySelectionScreen
import dev.mayaqq.estrogen.content.EstrogenBlocks
import invoke.kitty.kritter.events.LateInitEvent
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.utils.clientOnly
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.rgb
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val MOD_ID = "createestrogen"
const val MOD_NAME = "Create: Estrogen"
fun id(path: String) = identifier(MOD_ID, path)

@EntrypointHandler("init")
fun init(mod: Mod) {
    CreateEstrogenCommonConfig.initialize()
    CreateEstrogenServerConfig.initialize()
    CreateEstrogenSerializers.register()
    CreateEstrogenRecipes.register()
    CreateEstrogenBlocks.register()
    CreateEstrogenBlockEntities.register()
    CreateEstrogenItems.register()
    CreateEstrogenCreativeTab.register()
    clientOnly {
        initClientEvents()
    }

    LateInitEvent.subscribe {
        CreateFluidHandlingInteractions.init()
    }
}

@EstrogenEntrypoint
object CreateEstrogen : Logger by LoggerFactory.getLogger(MOD_NAME), EstrogenModule {
    override val color: Color = rgb(255, 199, 167)
    override val description: String = "Create module for Estrogen"
    override val flags: Array<EstrogenFlag> = arrayOf(EstrogenFlag.DISABLES_CAULDRON_ESTROGEN)

    override fun createConfigScreen(): ScreenProvider {
        return ScreenProvider { ConfigCategorySelectionScreen(it, listOf(CreateEstrogenClientConfig, CreateEstrogenCommonConfig, CreateEstrogenServerConfig)) }
    }
}