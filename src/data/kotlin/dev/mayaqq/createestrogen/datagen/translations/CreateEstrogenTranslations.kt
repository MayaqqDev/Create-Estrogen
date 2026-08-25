package dev.mayaqq.createestrogen.datagen.translations

import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class CreateEstrogenTranslations(output: PackOutput) : LanguageProvider(output, MOD_ID,"en_us") {

    override fun addTranslations() {
        add(CreateEstrogenItems.IncompleteEstrogenPatch, "Incomplete Estrogen Patch")
        add(CreateEstrogenItems.IncompleteUwU, "Incomplete UwU")
        add(CreateEstrogenItems.UsedFilter, "Used Filter")
        add(CreateEstrogenItems.CatEarGoggles, "Cat Ear Goggles")

        add(CreateEstrogenBlocks.Centrifuge, "Centrifuge")
        add("block.createestrogen.centrifuge.tooltip", "This machine only accepts _input_ from the _bottom_ and _output_ from the _top_.")
        add(CreateEstrogenBlocks.MothSeat, "Rosy Maple Seat")
        add("itemGroup.createestrogen.createestrogen", "Create: Estrogen")
        add("createestrogen.recipe.centrifuging", "Centrifuging")
        add("emi.category.createestrogen.centrifuging", "Centrifuging")
        add("createestrogen.ponder.intro.header", "The Centrifuge Requirements")
        add("createestrogen.ponder.intro.text_1", "The Centrifuge needs the maximum speed (256 RPM) to work!")
        add("createestrogen.ponder.basic.header", "How to use the Centrifuge")
        add(
            "createestrogen.ponder.basic.text_1",
            "The Centrifuge doesn't have any inventory, you will need to place fluid containers around it to make it work!"
        )
        add("createestrogen.ponder.basic.text_2", "You can input fluids from the bottom")
        add("createestrogen.ponder.basic.text_3", "And output fluids from the top")
        add(CreateEstrogenItems.allEstrogenPillBoxes[0].get(), "Estrogen Pill Package")

        add("create.tooltip.speedRequirement.max", "Max")
    }
}