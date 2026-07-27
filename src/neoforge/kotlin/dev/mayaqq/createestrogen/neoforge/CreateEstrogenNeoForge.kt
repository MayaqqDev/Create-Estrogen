package dev.mayaqq.createestrogen.neoforge

import dev.mayaqq.createestrogen.CreateEstrogen
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler

object CreateEstrogenNeoForge {
    @EntrypointHandler("init")
    fun init(mod: Mod) {
        CreateEstrogen.init()
    }
}
