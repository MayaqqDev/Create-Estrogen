package dev.mayaqq.createestrogen.neoforge.extensions

import dev.mayaqq.createestrogen.generics.CreateEstrogenItemHandler
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.ItemStackHandler

@JvmRecord
data class ItemHandlerWrapper(val handler: ItemStackHandler) : CreateEstrogenItemHandler {
    override fun getSlots(): Int {
        return handler.slots
    }

    override fun getStackInSlot(slot: Int): ItemStack {
        return handler.getStackInSlot(slot)
    }
}
