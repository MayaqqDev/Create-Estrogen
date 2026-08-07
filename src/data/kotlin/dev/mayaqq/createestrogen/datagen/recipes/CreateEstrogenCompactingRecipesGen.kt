package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.CompactingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenFluids
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

@Suppress("UnstableApiUsage")
class CreateEstrogenCompactingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : CompactingRecipeGen(output, lookup, MOD_ID) {
    init {
        create("slime_ball") {
            it.require(EstrogenFluids.MoltenSlime.get(), 500)
                .output(Items.SLIME_BALL, 1)
        }
    }
}