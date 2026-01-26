package dev.mayaqq.createestrogen.content.items

import com.simibubi.create.content.equipment.goggles.GogglesItem
import dev.mayaqq.createestrogen.content.CreateEstrogenTags
import dev.mayaqq.cynosure.utils.contains
import earth.terrarium.baubly.common.Bauble
import earth.terrarium.baubly.common.BaubleUtils
import net.minecraft.world.entity.EquipmentSlot


class CatEarGoggles(properties: Properties) : GogglesItem(properties), Bauble {

    companion object {
        init {
            addIsWearingPredicate { it.getItemBySlot(EquipmentSlot.HEAD) in CreateEstrogenTags.Items.EAR_GOGGLES }
            addIsWearingPredicate { player ->
                BaubleUtils.getBaubleContainers(player).any { entry ->
                    entry.value.hasAnyMatching { it in CreateEstrogenTags.Items.EAR_GOGGLES }
                }
            }
        }
    }
}