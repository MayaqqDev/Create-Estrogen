package dev.mayaqq.createestrogen.utils.recipe


import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import dev.mayaqq.createestrogen.content.recipes.RatioFluidIngredient
import dev.mayaqq.createestrogen.content.recipes.RatioFluidOutput
import net.minecraft.advancements.Criterion
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.common.conditions.ICondition


class CreateEstrogenCentrifugingRecipeBuilder(val _id: ResourceLocation) : RecipeBuilder {
    private val recipeInputs = mutableListOf<RatioFluidIngredient>()
    private var recipeOutput: RatioFluidOutput? = null
    private val conditions = ArrayList<ICondition>()
    fun addInput(fluid: Fluid, amountPerTick: Long): CreateEstrogenCentrifugingRecipeBuilder {
        recipeInputs.add(RatioFluidIngredient(fluid, amountPerTick))
        return this
    }

    fun addOutput(fluid: Fluid, amountPerTick: Long): CreateEstrogenCentrifugingRecipeBuilder {
        recipeOutput = RatioFluidOutput(fluid, amountPerTick)
        return this
    }

    fun build(): CentrifugingRecipe = CentrifugingRecipe(recipeInputs, recipeOutput!!)


    override fun unlockedBy(
        name: String,
        criterion: Criterion<*>
    ): RecipeBuilder {
        return this
    }

    override fun group(groupName: String?): RecipeBuilder {
        return this
    }

    override fun getResult(): Item =
        recipeOutput!!.fluid.bucket


    override fun save(
        recipeOutput: RecipeOutput,
        id: ResourceLocation
    ) {
        val recipe = build()
        recipeOutput.accept(id, recipe, null, *conditions.toTypedArray())
    }

    override fun save(recipeOutput: RecipeOutput) =
        save(recipeOutput, _id)

}



