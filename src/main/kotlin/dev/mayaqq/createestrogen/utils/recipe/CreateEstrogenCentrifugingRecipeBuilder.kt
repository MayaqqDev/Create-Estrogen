package dev.mayaqq.createestrogen.utils.recipe

import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import dev.mayaqq.createestrogen.content.recipes.RatioFluidIngredient
import dev.mayaqq.createestrogen.content.recipes.RatioFluidOutput
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.material.Fluid
import java.util.function.Consumer

class CreateEstrogenCentrifugingRecipeBuilder(val _id: ResourceLocation) {
    private val recipeInputs = mutableListOf<RatioFluidIngredient>()
    private var recipeOutput: RatioFluidOutput? = null

    fun addInput(fluid: Fluid,amountPerTick: Long) : CreateEstrogenCentrifugingRecipeBuilder {
        recipeInputs.add(RatioFluidIngredient(fluid,amountPerTick))
        return this
    }
    fun addOutput(fluid: Fluid,amountPerTick: Long): CreateEstrogenCentrifugingRecipeBuilder {
        recipeOutput = RatioFluidOutput(fluid,amountPerTick)
        return this
    }
     fun build(): CentrifugingRecipe {
        return CentrifugingRecipe(_id,recipeInputs,recipeOutput!!)
    }

    fun build(consumer: Consumer<FinishedRecipe?>) {
        consumer.accept(
            DataGenResult(
                this.build()
            )
        )
    }
    class DataGenResult(val recipe: CentrifugingRecipe) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject)  {
            val r = CentrifugingRecipe.codec(recipe.id).encodeStart(JsonOps.INSTANCE, recipe).getOrThrow(true) {
                CreateEstrogen.error(
                    "Error encoding codec recipe: $it"
                )
            }
            r.asJsonObject.entrySet().forEach {
                json.add(it.key,it.value)
            }

        }

        override fun getId(): ResourceLocation =
            ResourceLocation.fromNamespaceAndPath(recipe.id.namespace, "centrifuging/${recipe.id.path}")

        override fun getType(): RecipeSerializer<*> =recipe.serializer

        override fun serializeAdvancement(): JsonObject? = null

        override fun getAdvancementId(): ResourceLocation? = null
    }
}
