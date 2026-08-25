package dev.mayaqq.createestrogen.network

import dev.mayaqq.createestrogen.id
import dev.mayaqq.createestrogen.network.messages.s2c.CatEarGoggleTypesPacket
import invoke.kitty.kritter.network.api.NetworkChannel
import invoke.kitty.kritter.serialization.builtins.MinecraftSerializersModule

val CreateEstrogenNetwork = NetworkChannel(id("main"), 1, serializers = MinecraftSerializersModule) {
    playS2C<CatEarGoggleTypesPacket>(handler = CatEarGoggleTypesPacket::handle)
}