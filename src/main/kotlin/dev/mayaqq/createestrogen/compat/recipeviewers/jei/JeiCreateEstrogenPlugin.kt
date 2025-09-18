package dev.mayaqq.createestrogen.compat.recipeviewers.jei

import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.createestrogen.compat.recipeviewers.GenericRecipeViewerPlugin
import dev.mayaqq.createestrogen.id
import dev.mayaqq.estrogen.compat.recipeviewers.base.RVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.jei.first
import dev.mayaqq.estrogen.compat.recipeviewers.jei.toJei
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe

@JeiPlugin
object JeiCreateEstrogenPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation = id("jei_plugin")

    val recipeTypes = buildMap {
        GenericRecipeViewerPlugin.rvRecipes.forEach { data ->
            put(data.info.type, RecipeType(data.info.id, data.actualRecipeClass.java) as RecipeType<Any>) }
        }

    val recipeInstances = mutableMapOf<Recipe<*>, RVRecipe<*>>()

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        GenericRecipeViewerPlugin.rvRecipes.forEach { recipeData ->
            registry.addRecipeCategories(object : IRecipeCategory<Any> {
                override fun getRecipeType(): RecipeType<Any> = recipeTypes[recipeData.info.type]!!

                override fun getTitle(): Component = Text.translatable("${recipeData.info.id.namespace}.recipe.${recipeData.info.id.path}")

                override fun getBackground(): IDrawable {
                    return object : IDrawable {
                        override fun getWidth(): Int = recipeData.info.width
                        override fun getHeight(): Int = recipeData.info.height
                        override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) {}
                    }
                }

                override fun getIcon(): IDrawable = object : IDrawable {
                    override fun getWidth(): Int = 16

                    override fun getHeight(): Int = 16

                    override fun draw(graphics: GuiGraphics, offsetX: Int, offsetY: Int) {
                        recipeData.info.render(graphics, offsetX, offsetY, 0F)
                    }
                }

                override fun setRecipe(layout: IRecipeLayoutBuilder, recipe: Any, group: IFocusGroup) {
                    val actualRecipe = recipe as Recipe<*>
                    if (!recipeInstances.contains(actualRecipe)) {
                        recipeInstances[actualRecipe] = recipeData.recipeClass.constructors.first().call(actualRecipe)
                        recipeInstances[actualRecipe]!!.init()
                    }
                    val rvRecipe = recipeInstances[actualRecipe]!!
                    rvRecipe.slots.forEach { slot ->
                        val jeiSlot = layout.addSlot(slot.role.toJei(), slot.x, slot.y)
                            .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)

                            if (slot.ingredient.ingredient != null) jeiSlot.addIngredients(slot.ingredient.ingredient!!)
                        else if (slot.ingredient.item != null) jeiSlot.addItemStack(slot.ingredient.item!!)
                        else if (slot.ingredient.fluid != null) jeiSlot.addFluidStack(slot.ingredient.fluid!!, 1000L)
                        else if (slot.ingredient.fluidTag != null) jeiSlot.addFluidStack(slot.ingredient.fluidTag!!.first, 1000L)
                    }
                }

                override fun draw(recipe: Any, slotView: IRecipeSlotsView, graphics: GuiGraphics, mouseX: Double, mouseY: Double) {
                    val actualRecipe = recipe as Recipe<*>
                    if (!recipeInstances.contains(actualRecipe)) {
                        recipeInstances[actualRecipe] = recipeData.recipeClass.constructors.first().call(actualRecipe)
                        recipeInstances[actualRecipe]!!.init()
                    }
                    val rvRecipe = recipeInstances[actualRecipe]
                    rvRecipe!!.textures.forEach { texture ->
                        texture.coorded.render(graphics, texture.x, texture.y)
                    }
                    rvRecipe.drawables.forEach { drawable ->
                        drawable.coorded.draw(graphics, drawable.x, drawable.y, mouseX.toInt(), mouseY.toInt(), 0F)
                    }
                }

            })
        }
    }

    override fun registerRecipes(registry: IRecipeRegistration) {
        recipeTypes.forEach { (type, jeiType) ->
            registry.addRecipes(jeiType, Minecraft.getInstance().level?.recipeManager?.getRecipes()?.filter { it.type == type }?: return@forEach)
        }

        // Hiding
        GenericRecipeViewerPlugin.removedFromRecipeViewers.forEach {
            registry.ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, listOf(it))
        }
    }

    class JeiSlot(val texture: RecipeTextures) : IDrawable {
        override fun getWidth(): Int = texture.width
        override fun getHeight(): Int = texture.height
        override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) = texture.render(graphics, xOffset, yOffset)
    }
}