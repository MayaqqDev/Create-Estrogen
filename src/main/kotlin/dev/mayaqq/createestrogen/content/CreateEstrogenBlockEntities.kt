package dev.mayaqq.createestrogen.content

import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.client.content.blockRenderers.centrifuge.CentrifugeRenderer
import dev.mayaqq.createestrogen.client.content.blockRenderers.CreateEstrogenRenderer
import dev.mayaqq.createestrogen.client.platforms.ofVisual
import dev.mayaqq.createestrogen.content.blockEntities.CentrifugeBlockEntity
import dev.mayaqq.estrogen.content.visual
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.blockEntity.blockEntity
import invoke.kitty.kritter.registry.blockEntity.renderer
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType


object CreateEstrogenBlockEntities : Registrar<BlockEntityType<*>> by Registrar(MOD_ID, Registries.BLOCK_ENTITY_TYPE){
    val Centrifuge : BlockEntityType<CentrifugeBlockEntity> by blockEntity("centrifuge", ::CentrifugeBlockEntity) {
        validBlock(CreateEstrogenBlocks::Centrifuge)
        renderer(::CentrifugeRenderer)
        clientOnly {
            visual(CreateEstrogenRenderer.CENTRIFUGE_COG::ofVisual) { false }
        }
    }
}