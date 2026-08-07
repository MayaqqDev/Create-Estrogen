@file:Suppress("UnstableApiUsage")

package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.EmptyingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class CreateEstrogenEmptyingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : EmptyingRecipeGen(output, lookup, MOD_ID) {
    init {
        create("horse_urine") {
            it.require(EstrogenItems.HorseUrineBottle)
                .output(EstrogenFluids.HorseUrine.value, 250)
                .output(Items.GLASS_BOTTLE)
        }
    }
}