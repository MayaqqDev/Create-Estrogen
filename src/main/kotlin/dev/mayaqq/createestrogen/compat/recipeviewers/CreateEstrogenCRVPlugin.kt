package dev.mayaqq.createestrogen.compat.recipeviewers


import dev.mayaqq.createestrogen.compat.recipeviewers.recipes.CentrifugingCRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPlugin
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPluginEntrypoint
import dev.mayaqq.estrogen.compat.recipeviewers.api.PseudoRecipeHolder
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

@CRVPluginEntrypoint
object CreateEstrogenCRVPlugin : CRVPlugin {
    override val removedItems: List<ItemStack> = listOf()
    override val removedRecipes: List<ResourceLocation> = listOf()
    override val recipes: List<ViewerInfo<*, *>> = listOf(CentrifugingCRVRecipe)
    override val pseudoRecipes: List<PseudoRecipeHolder<*>> = listOf()
}