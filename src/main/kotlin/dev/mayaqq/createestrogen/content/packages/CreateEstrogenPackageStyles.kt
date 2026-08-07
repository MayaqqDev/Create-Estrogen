package dev.mayaqq.createestrogen.content.packages

import com.simibubi.create.AllDataComponents
import com.simibubi.create.content.logistics.box.PackageStyles.PackageStyle
import com.simibubi.create.foundation.item.ItemHelper
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.items.ItemStackHandler

object CreateEstrogenPackageStyles {

    val estrogenPillStyles = listOf(
        PackageStyle("createestrogen:estrogen_cardboard", 12, 12, 23f, false),
        PackageStyle("createestrogen:estrogen_cardboard", 10, 12, 22f, false),
        PackageStyle("createestrogen:estrogen_cardboard", 10, 8, 18f, false),
        PackageStyle("createestrogen:estrogen_cardboard", 12, 10, 21f, false),
    )
    private val estrogenPill by lazy {  BuiltInRegistries.ITEM.get(ResourceLocation.tryParse("estrogen:estrogen_pill")!!)}
    private val crystalEstrogenPill by lazy {  BuiltInRegistries.ITEM.get(ResourceLocation.tryParse("estrogen:crystal_estrogen_pill")!!)}
    private val estrogenPillBlock by lazy {  BuiltInRegistries.ITEM.get(ResourceLocation.tryParse("estrogen:estrogen_pill_block")!!)}

    private val allowedItemsToBeCounted by lazy {
        setOf(
            estrogenPill,
            crystalEstrogenPill,
            estrogenPillBlock,
        )
    }

    @JvmStatic
    fun containing(stacks: ItemStackHandler): ItemStack? {
        if (isMajorityOfItemsEstrogenItems(stacks)) {
            val box = ItemStack(CreateEstrogenItems.allEstrogenPillBoxes.random().value as ItemLike)
            box.set(AllDataComponents.PACKAGE_CONTENTS, ItemHelper.containerContentsFromHandler(stacks))
            return box
        } else return null
    }

    private fun isMajorityOfItemsEstrogenItems(stacks: ItemStackHandler): Boolean {
        val itemToAmount = mutableMapOf<Item, Int>()

        for (i in 0..<stacks.slots) {
            val stack = stacks.getStackInSlot(i)
            var amount = itemToAmount.getOrDefault(stack.item, 0)
            amount += stack.count
            itemToAmount[stack.item] = amount
        }
        val itemToAmountImmutable: Map<Item, Int> = itemToAmount
        val amountOfEstrogenPillLikeItems = itemToAmountImmutable.getOrDefault(estrogenPill, 0) +
                itemToAmountImmutable.getOrDefault(crystalEstrogenPill, 0) +
                (itemToAmountImmutable.getOrDefault(estrogenPillBlock, 0) * 9)
        val amountOfOtherItems = itemToAmountImmutable.filterKeys { !allowedItemsToBeCounted.contains(it) }.asIterable()
            .fold(0) { acc, (_, value) -> acc + value }

        return amountOfEstrogenPillLikeItems > amountOfOtherItems
    }
}

