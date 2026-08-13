@file:      OptIn(RequiresTomlKt::class)
package dev.mayaqq.createestrogen.config

import dev.mayaqq.createestrogen.MOD_ID
import invoke.kitty.kritter.config.api.Config
import invoke.kitty.kritter.config.api.ConfigCategory
import invoke.kitty.kritter.config.api.SyncedConfig
import invoke.kitty.kritter.config.formats.Json5Format
import invoke.kitty.kritter.config.formats.RequiresTomlKt
import invoke.kitty.kritter.config.formats.TomlFormat
import invoke.kitty.kritter.config.validation.types.range


object CreateEstrogenClientConfig : Config("$MOD_ID/client", Json5Format.Default) {
    object EstrogenButton : ConfigCategory(comment = "Settings for the estrogen button in the create screen") {

        val enabled: Boolean by field(true) {
            comment = "Enable the estrogen button in the create screen"
        }

        val xOffset: Int by field(-23) {
            comment = """
                    X offset the estrogen button in the create screen
                    Offset is calculated off of the center of the Configure Button
                """
        }

        val yOffset: Int by field(0) {
            comment = """
                X offset the estrogen button in the create screen
                Offset is calculated off of the center of the Configure Button
            """
        }
    }
}

object CreateEstrogenCommonConfig : SyncedConfig("$MOD_ID/common", Json5Format.Default) {
    val hiHowAreYou: String by field("placeholder")
}

object CreateEstrogenServerConfig : Config("$MOD_ID/server", Json5Format.Default) {
    val hiHowAreYou: String by field("placeholder")
}