package dev.mayaqq.createestrogen.content

import com.simibubi.create.AllDisplaySources
import com.simibubi.create.api.behaviour.display.DisplaySource
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour
import com.simibubi.create.api.behaviour.movement.MovementBehaviour
import com.simibubi.create.api.stress.BlockStressValues
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour
import com.simibubi.create.foundation.data.SharedProperties
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.registry.blocks.CentrifugeBlock
import dev.mayaqq.cynosure.utils.standardTooltip
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.block.BlockRenderType
import invoke.kitty.kritter.registry.block.block
import invoke.kitty.kritter.registry.block.renderType
import invoke.kitty.kritter.registry.item.item
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.MapColor

object CreateEstrogenBlocks: Registrar<Block> by Registrar(MOD_ID, Registries.BLOCK) {
    val Centrifuge by block("centrifuge", ::CentrifugeBlock)
    {
        initialPropertiesFrom(SharedProperties::copperMetal)
        properties{
            requiresCorrectToolForDrops()
            mapColor(MapColor.COLOR_ORANGE).noOcclusion()
        }
        renderType = BlockRenderType.CUTOUT_MIPPED
        onRegister {
            BlockStressValues.IMPACTS.register(it) {
                8.0
            }
        }
        item(factory = ::BlockItem) {
            standardTooltip()
        }
    }
    val MothSeat by block("moth_seat", { SeatBlock(it, null) }) {
        initialPropertiesFrom(Blocks::STRIPPED_SPRUCE_WOOD)
        properties {
            mapColor(MapColor.COLOR_ORANGE)
        }
        onRegister {
            MovingInteractionBehaviour.REGISTRY.register(it,SeatInteractionBehaviour())
            MovementBehaviour.REGISTRY.register(it,SeatMovementBehaviour())
        }
        onSetup {
            DisplaySource.BY_BLOCK.register(it, listOf(AllDisplaySources.ENTITY_NAME.get()))
        }
        item(factory = ::BlockItem) {
            standardTooltip()
        }


    }
}