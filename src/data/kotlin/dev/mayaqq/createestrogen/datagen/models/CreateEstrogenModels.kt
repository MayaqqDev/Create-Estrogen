package dev.mayaqq.createestrogen.datagen.models

import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.createestrogen.id
import net.minecraft.data.PackOutput
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.*

class CreateEstrogenItemModels(output: PackOutput, efh: ExistingFileHelper) : ItemModelProvider(output, MOD_ID, efh) {
    override fun registerModels() {
        /*
        val textureMapping = TextureMapping.particle(id("item/package/estrogen_cardboard_particle"))
            .put(TextureSlot.LAYER0, id("item/package/estrogen_cardboard"))
        for (styleItem in CreateEstrogenItems.allEstrogenPillBoxes) {
            CreateEstrogen.debug("creating item model for {}", styleItem.value.style.itemId)
            val style = styleItem.value.style
            val size = "_${style.width}x${style.height}"
            val template = ModelTemplate(
                Optional.of(ResourceLocation("create:item/package/cardboard$size")), Optional.empty(),
                TextureSlot.LAYER0, TextureSlot.PARTICLE
            )
            val riggingTemplate = ModelTemplate(
                Optional.of(ResourceLocation("create:item/package/rigging$size")), Optional.empty(),
                TextureSlot.LAYER0, TextureSlot.PARTICLE
            )
            template.create(ModelLocationUtils.getModelLocation(styleItem.value), textureMapping, img.output)
            riggingTemplate.create(style.riggingModel, textureMapping, img.output)
        }
         */
    }
}