package dev.mayaqq.createestrogen.config

import dev.mayaqq.createestrogen.MOD_ID
import invoke.kitty.kritter.config.api.Config
import invoke.kitty.kritter.config.api.SyncedConfig
import invoke.kitty.kritter.config.formats.Json5Format

object CreateEstrogenClientConfig : Config("$MOD_ID/client", Json5Format.Default) {

}

object CreateEstrogenCommonConfig : SyncedConfig("$MOD_ID/common", Json5Format.Default) {

}

object CreateEstrogenServerConfig : Config("$MOD_ID/server", Json5Format.Default) {

}
