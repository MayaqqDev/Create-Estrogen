package dev.mayaqq.createestrogen.test

import com.simibubi.create.content.logistics.depot.DepotBlockEntity
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenItems
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.gametest.framework.GameTest
import net.minecraft.gametest.framework.GameTestAssertException
import net.minecraft.gametest.framework.GameTestHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.Enchantments
import net.neoforged.neoforge.gametest.GameTestHolder

private fun GameTestHelper.assertHasEnchantment(item: ItemStack, t: Holder<Enchantment>, level: Int = 1) {
    val enchantLevel = item.getEnchantmentLevel(t);
    if (enchantLevel < level) {
        throw GameTestAssertException("Enchantment level is less than $level for item $item")
    }
}

@GameTestHolder(MOD_ID)
class DeployingTests {
    /// maya doesn't want this to happen because issue #13 is closed as not planned
    @GameTest(template = "mothfuzzing_elytra", timeoutTicks = 5)
    fun testMothFuzzingElytraDoesNotKeepEnchantments(helper: GameTestHelper) {
        val deployerBaseCoords = BlockPos(1, 2, 0)
        val deployerBase = helper.getBlockEntity<DepotBlockEntity>(deployerBaseCoords)
        val stack = ItemStack(Items.ELYTRA);
        val mending =
            helper.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING);
        val unbreaking =
            helper.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING);
        stack.enchant(mending, 3)
        stack.enchant(unbreaking, 3)
        deployerBase.heldItem = stack;
        helper.succeedWhen {
            helper.assertTrue(deployerBase.heldItem.`is`(EstrogenItems.MothElytra.holder), "Item was not moth elytra!")
            helper.assertHasEnchantment(deployerBase.heldItem, mending, 0)
            helper.assertHasEnchantment(deployerBase.heldItem, unbreaking, 0)
        }
    }
}