package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.PolishingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

class CreateEstrogenSandpaperPolishingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : PolishingRecipeGen(output, lookup, MOD_ID) {
    init {
        create("testosterone_chunk") {
            it.require(EstrogenItems.Balls)
                .output(EstrogenItems.TestosteroneChunk, 1)
        }
    }
}