package dev.mayaqq.createestrogen.datagen.recipes

import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.datagen.lib.CentrifugingRecipesGen
import dev.mayaqq.estrogen.content.EstrogenFluids
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

class CreateEstrogenCentrifugingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : CentrifugingRecipesGen(output, lookup, MOD_ID) {
    init {
        create("liquid_estrogen") {
            addInput(EstrogenFluids.FiltratedHorseUrine.get(), 81)
            addOutput(EstrogenFluids.LiquidEstrogen.get(), 81)
        }
    }
}

