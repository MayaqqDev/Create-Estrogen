package dev.mayaqq.createestrogen.network.messages.s2c

import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import invoke.kitty.kritter.serialization.builtins.ResourceLocationSerializer
import kotlinx.serialization.Serializable
import net.minecraft.resources.ResourceLocation

@Serializable @JvmRecord
data class CatEarGoggleTypesPacket(val types: List<@Serializable(ResourceLocationSerializer::class) ResourceLocation>) {
    fun handle() {
        CreateEstrogenItems.CatEarGoggles.loadTypes(types)
    }
}