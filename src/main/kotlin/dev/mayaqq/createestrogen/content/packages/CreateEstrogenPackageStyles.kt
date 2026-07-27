package dev.mayaqq.createestrogen.content.packages

import com.simibubi.create.content.logistics.box.PackageStyles.PackageStyle
import com.simibubi.create.AllDataComponents
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.createestrogen.generics.CreateEstrogenItemHandler
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents

object CreateEstrogenPackageStyles {

    val estrogenPillStyles = listOf(
        PackageStyle("createestrogen:estrogen_cardboard", 12, 12, 23f, false),
        PackageStyle("createestrogen:estrogen_cardboard", 10, 12, 22f, false),
        PackageStyle("createestrogen:estrogen_cardboard", 10, 8, 18f, false),
        PackageStyle("createestrogen:estrogen_cardboard", 12, 10, 21f, false),
    )
    private val allowedItemsToBeCounted by lazy {
        setOf(
            EstrogenItems.EstrogenPill.value!!,
            EstrogenItems.CrystalEstrogenPill.value!!,
            EstrogenBlocks.EstrogenPillBlock.value!!.asItem()
        )
    }

    @JvmStatic
    fun containing(stacks: CreateEstrogenItemHandler): ItemStack? {
        if (isMajorityOfItemsEstrogenItems(stacks)) {
            val box = ItemStack(CreateEstrogenItems.allEstrogenPillBoxes.random().value!!)
            val contents = (0 until stacks.slots).map(stacks::getStackInSlot)
            box.set(AllDataComponents.PACKAGE_CONTENTS, ItemContainerContents.fromItems(contents))
            return box
        } else return null
    }

    private fun isMajorityOfItemsEstrogenItems(stacks: CreateEstrogenItemHandler): Boolean {
        val itemToAmount = mutableMapOf<Item, Int>()

        for (i in 0..<stacks.slots) {
            val stack = stacks.getStackInSlot(i)
            var amount = itemToAmount.getOrDefault(stack.item, 0)
            amount += stack.count
            itemToAmount[stack.item] = amount
        }
        val itemToAmountImmutable: Map<Item, Int> = itemToAmount
        val amountOfEstrogenPillLikeItems = itemToAmountImmutable.getOrDefault(EstrogenItems.EstrogenPill.value!!, 0) +
                itemToAmountImmutable.getOrDefault(EstrogenItems.CrystalEstrogenPill.value!!, 0) +
                (itemToAmountImmutable.getOrDefault(EstrogenBlocks.EstrogenPillBlock.value!!.asItem(), 0) * 9)
        val amountOfOtherItems = itemToAmountImmutable.filterKeys { !allowedItemsToBeCounted.contains(it) }.asIterable()
            .fold(0) { acc, (_, value) -> acc + value }

        return amountOfEstrogenPillLikeItems > amountOfOtherItems
    }
}
