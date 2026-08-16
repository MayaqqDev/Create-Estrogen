package dev.mayaqq.createestrogen.interactions

import com.simibubi.create.foundation.item.TooltipModifier
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

class CreateTooltip(val modifier: TooltipModifier) : CustomTooltip {
    override fun MutableList<Component>.modifyTooltip(
        stack: ItemStack,
        player: Player?,
        flags: TooltipFlag
    ) {
        try {
            ItemTooltipEvent(stack, player, this, flags, Item.TooltipContext.EMPTY).apply { modifier.modify(this) }
        } catch (_: NullPointerException) {}
    }
}