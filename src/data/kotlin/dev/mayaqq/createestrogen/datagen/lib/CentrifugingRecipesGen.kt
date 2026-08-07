package dev.mayaqq.createestrogen.datagen.lib

import com.simibubi.create.api.data.recipe.BaseRecipeProvider
import dev.mayaqq.createestrogen.utils.recipe.CreateEstrogenCentrifugingRecipeBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

abstract class CentrifugingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>, modId: String) : BaseRecipeProvider(output,lookup, modId ) {

    override fun getName(): String {
        return "$modid's Centrifuging Recipes"
    }
    protected fun create(name: String, transform: CreateEstrogenCentrifugingRecipeBuilder.() -> CreateEstrogenCentrifugingRecipeBuilder) : GeneratedRecipe {
        val recipe = GeneratedRecipe { out ->
            transform.invoke(
                CreateEstrogenCentrifugingRecipeBuilder(
                    asResource(
                        name
                    )
                )
            ).save(out)
        }
        all.add(recipe)
        return recipe
    }


}