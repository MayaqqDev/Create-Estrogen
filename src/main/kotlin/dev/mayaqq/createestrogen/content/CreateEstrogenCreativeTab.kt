package dev.mayaqq.createestrogen.content

import dev.mayaqq.createestrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.creativeTab.creativeTab
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block

object CreateEstrogenCreativeTab: Registrar<CreativeModeTab> by Registrar(MOD_ID, Registries.CREATIVE_MODE_TAB) {
    // Kritter's default custom-tab position is (null, -1). Estrogen uses that
    // default too, and Minecraft requires every registered tab position to be
    // unique even when the row is unspecified.
    val CreateEstrogen = creativeTab("createestrogen", null, -2) {
        title = Component.translatable("itemGroup.createestrogen.createestrogen")
        icon { CreateEstrogenBlocks.Centrifuge.getOrThrow().asItem().defaultInstance }
        displayItems {
            acceptBlock(CreateEstrogenBlocks.Centrifuge.getOrThrow())
            acceptBlock(CreateEstrogenBlocks.MothSeat.getOrThrow())
            CreateEstrogenItems.allEstrogenPillBoxes
                .map { it.getOrThrow().defaultInstance }
                .forEach(::acceptStack)
            acceptStack(CreateEstrogenItems.UsedFilter.getOrThrow().defaultInstance)
        }
    }
}

private fun CreativeModeTab.Output.acceptBlock(block: Block) {
    val blockId = BuiltInRegistries.BLOCK.getKey(block)
    val item = BuiltInRegistries.ITEM.get(blockId)
    if (item != Items.AIR) acceptStack(item.defaultInstance)
}

private fun CreativeModeTab.Output.acceptStack(stack: ItemStack) {
    if (!stack.isEmpty) accept(stack.copyWithCount(1))
}
