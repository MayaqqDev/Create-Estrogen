package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.ItemApplicationRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import java.util.concurrent.CompletableFuture

class CreateEstrogenItemApplicationRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : ItemApplicationRecipeGen(output, lookup, MOD_ID) {
    init {
        create("dormant_dream_block") {
            it
                .require(Blocks.TINTED_GLASS)
                .require(Items.ENDER_PEARL)
                .output(EstrogenBlocks.DreamBlock.get())
        }
    }
}