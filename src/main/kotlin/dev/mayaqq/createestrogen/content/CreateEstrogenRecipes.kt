package dev.mayaqq.createestrogen.content
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import dev.mayaqq.cynosure.recipes.codecSerializer
import dev.mayaqq.cynosure.recipes.recipeType
import invoke.kitty.kritter.registry.api.Registrar
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType


object CreateEstrogenRecipes : Registrar<RecipeType<*>> by Registrar(MOD_ID, Registries.RECIPE_TYPE) {
    val CENTRIFUGING by recipeType<CentrifugingRecipe>("centrifuging")


}

object CreateEstrogenSerializers : Registrar<RecipeSerializer<*>> by Registrar(MOD_ID, Registries.RECIPE_SERIALIZER) {
    val CENTRIFUGING_SERIALIZER by codecSerializer(
        "centrifuging",
        { CentrifugingRecipe.codec }, { CentrifugingRecipe.netCodec }
    ) {}
}