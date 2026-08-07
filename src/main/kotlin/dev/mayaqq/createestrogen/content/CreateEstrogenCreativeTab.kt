package dev.mayaqq.createestrogen.content

import dev.mayaqq.createestrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.creativeTab.creativeTab
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab

object CreateEstrogenCreativeTab: Registrar<CreativeModeTab> by Registrar(MOD_ID, Registries.CREATIVE_MODE_TAB) {
    val CreateEstrogen = creativeTab("createestrogen") {
        title = Component.translatable("itemGroup.createestrogen.createestrogen")
        icon { CreateEstrogenBlocks.Centrifuge.asItem().defaultInstance }
        displayItems {
            accept(CreateEstrogenBlocks.Centrifuge)
            accept(CreateEstrogenBlocks.MothSeat)
            acceptAll(CreateEstrogenItems.allEstrogenPillBoxes.map { it.value!!.defaultInstance })
            accept(CreateEstrogenItems.UsedFilter)
        }
    }
}