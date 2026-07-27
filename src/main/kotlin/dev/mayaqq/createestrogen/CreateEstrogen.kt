package dev.mayaqq.createestrogen

import dev.mayaqq.createestrogen.config.CreateEstrogenCommonConfig
import dev.mayaqq.createestrogen.config.CreateEstrogenServerConfig
import dev.mayaqq.createestrogen.content.*
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.estrogen.api.EstrogenEntrypoint
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.api.EstrogenModule
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.rgb
import net.minecraft.client.gui.screens.Screen
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val MOD_ID = "createestrogen"
const val MOD_NAME = "Create: Estrogen"
fun id(path: String) = identifier(MOD_ID, path)

@EstrogenEntrypoint
@EventSubscriber
object CreateEstrogen : Logger by LoggerFactory.getLogger(MOD_NAME), EstrogenModule {
    fun init() {
        CreateEstrogenCommonConfig.initialize()
        CreateEstrogenServerConfig.initialize()

        CreateEstrogenRecipes.register()
        CreateEstrogenRecipes.Serializers.register()
        CreateEstrogenBlocks.register()
        CreateEstrogenBlockEntities.register()
        CreateEstrogenItems.register()
        CreateEstrogenCreativeTab.register()
    }

    override val color: Color = rgb(255, 199, 167)
    override val description: String = "Create module for Estrogen"
    override val flags: Array<EstrogenFlag> = arrayOf(EstrogenFlag.DISABLES_CAULDRON_ESTROGEN)

    override fun createConfigScreen(): (Screen) -> Screen = {
        EstrogenMenuScreen(it)
    }
}
