package dev.mayaqq.createestrogen.content

import dev.mayaqq.createestrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.creativeTab.creativeTab
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab

object CreateEstrogenCreativeTab: Registrar<CreativeModeTab> by Registrar(MOD_ID, Registries.CREATIVE_MODE_TAB) {
    // Kritter's default custom-tab position is (null, -1). Estrogen uses that
    // default too, and Minecraft requires every registered tab position to be
    // unique even when the row is unspecified.
    val CreateEstrogen = creativeTab("createestrogen", null, -2) {
        title = Component.translatable("itemGroup.createestrogen.createestrogen")
        icon { CreateEstrogenBlocks.Centrifuge.getOrThrow().asItem().defaultInstance }
        displayItems {
            accept(CreateEstrogenBlocks.Centrifuge.getOrThrow())
            accept(CreateEstrogenBlocks.MothSeat.getOrThrow())
            acceptAll(CreateEstrogenItems.allEstrogenPillBoxes.map { it.getOrThrow().defaultInstance })
            accept(CreateEstrogenItems.UsedFilter.getOrThrow())
        }
    }
}
