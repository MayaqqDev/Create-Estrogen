package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.WashingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

class CreateEstrogenSplashingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : WashingRecipeGen(output, lookup, MOD_ID) {
    init {
        create("thigh_high_washing") {
            it.require(EstrogenItems.ThighHighs).output(EstrogenItems.ThighHighs)
        }
    }
}