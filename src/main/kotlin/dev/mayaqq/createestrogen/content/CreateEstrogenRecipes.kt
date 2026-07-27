package dev.mayaqq.createestrogen.content
import net.minecraft.core.registries.Registries
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.cynosure.recipes.codecSerializer
import dev.mayaqq.cynosure.recipes.recipeType
import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import invoke.kitty.kritter.registry.api.Registrar
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType

object CreateEstrogenRecipes : Registrar<RecipeType<*>> by Registrar(MOD_ID, Registries.RECIPE_TYPE) {
    val CENTRIFUGING = recipeType<CentrifugingRecipe>("centrifuging")

    object Serializers : Registrar<RecipeSerializer<*>> by Registrar(MOD_ID, Registries.RECIPE_SERIALIZER) {
        val CENTRIFUGING_SERIALIZER = codecSerializer("centrifuging",
            { CentrifugingRecipe.codec(it) }, { CentrifugingRecipe.netCodec(it) }) {}
    }
}
