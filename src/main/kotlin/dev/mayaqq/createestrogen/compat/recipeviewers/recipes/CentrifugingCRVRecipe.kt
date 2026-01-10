package dev.mayaqq.createestrogen.compat.recipeviewers.recipes

import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo

class CentrifugingCRVRecipe(recipe: CentrifugingRecipe) : CRVRecipe<CentrifugingRecipe>(recipe) {
    override fun init() {
        addTexture(RecipeTextures.JEI_LONG_ARROW, 31, 51)
        addTexture(RecipeTextures.JEI_SHADOW, 40, 36)
        addSlot(inputs[0], 5, 51, Role.INPUT)
        addSlot(outputs[0], 110, 51, Role.OUTPUT)
        addDrawable(55, 40, CentrifugeBlockElement())
    }

    override val inputs: List<CRVIngredient> = listOf(CRVIngredient.of(recipe.inputs.first().fluid))

    override val outputs: List<CRVIngredient> = listOf(CRVIngredient.of(recipe.result.fluid))

    override val catalysts: List<CRVIngredient> = listOf(CRVIngredient.of(CreateEstrogenBlocks.Centrifuge.asItem().defaultInstance))

    companion object : ViewerInfo<CentrifugingRecipe, CentrifugingCRVRecipe>(
        CentrifugingRecipe,
        { CentrifugingCRVRecipe(it as CentrifugingRecipe) },
        CentrifugingRecipe::class
    )
}