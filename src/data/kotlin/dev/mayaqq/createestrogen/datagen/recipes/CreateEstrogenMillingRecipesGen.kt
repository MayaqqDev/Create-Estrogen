package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.MillingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

class CreateEstrogenMillingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : MillingRecipeGen(output, lookup, MOD_ID) {
    init {
        create("testosterone_powder") {
            it.require(EstrogenItems.TestosteroneChunk).output(EstrogenItems.TestosteronePowder, 3)
        }
    }
}