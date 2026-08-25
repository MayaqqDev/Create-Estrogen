package dev.mayaqq.createestrogen.content.item

import com.simibubi.create.content.equipment.goggles.GogglesItem
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.createestrogen.network.messages.s2c.CatEarGoggleTypesPacket
import dev.mayaqq.estrogen.network.EstrogenNetwork
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.CuriosCapability
import top.theillusivec4.curios.api.type.capability.ICurioItem

class CatEarGogglesItem(properties: Properties) : GogglesItem(properties), ICurioItem {
    init {
        CuriosApi.registerCurio(this, this)
    }

    private val types = mutableListOf<ResourceLocation>()

    fun syncTypes(player: ServerPlayer) {
        EstrogenNetwork.sendToPlayer(player, CatEarGoggleTypesPacket(types))
    }

    fun loadTypes(types: List<ResourceLocation>) {
        this.types.clear()
        this.types.addAll(types)
    }

    companion object {
        init {
            addIsWearingPredicate {
                it.getCapability(CuriosCapability.INVENTORY)?.curios?.any { (name, handler) ->
                    for (slot in 0 until handler.slots) {
                        if (handler.stacks.getStackInSlot(slot).item == CreateEstrogenItems.CatEarGoggles) {
                            return@any true
                        }
                    }
                    false
                } == true
            }
            addIsWearingPredicate { it.getItemBySlot(EquipmentSlot.HEAD).item == CreateEstrogenItems.CatEarGoggles }
        }
    }
}