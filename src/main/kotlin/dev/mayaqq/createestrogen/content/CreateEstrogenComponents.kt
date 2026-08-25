package dev.mayaqq.createestrogen.content

import dev.mayaqq.createestrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation

object CreateEstrogenComponents : Registrar<DataComponentType<*>> by Registrar(MOD_ID, Registries.DATA_COMPONENT_TYPE) {
    val CatEarGoggleTypeComponent: DataComponentType<ResourceLocation> by entry("cat_ear_goggle_type",
        DataComponentType.builder<ResourceLocation>().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC)::build
    )
}