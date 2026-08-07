package dev.mayaqq.createestrogen.content.blockEntities

import com.simibubi.create.content.kinetics.base.KineticBlockEntity
import dev.mayaqq.createestrogen.content.CreateEstrogenRecipes
import dev.mayaqq.createestrogen.content.FluidContainer
import dev.mayaqq.createestrogen.content.recipes.CentrifugingContainer
import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import earth.terrarium.common_storage_lib.fluid.FluidApi
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.rmi.UnexpectedException
import kotlin.math.absoluteValue

class CentrifugeBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) :
    KineticBlockEntity(type, pos, state) {
    override fun tick() {
        super.tick()
        if (isVirtual || level?.isClientSide == true || speed.absoluteValue < 256) return
        val currentLevel = level as ServerLevel
        val outputFluidTank = FluidApi.BLOCK.find(currentLevel,blockPos.above(), Direction.DOWN) ?: return
        val inputFluidTank = FluidApi.BLOCK.find(currentLevel, blockPos.below(), Direction.UP) ?: return
        if (!inputFluidTank.allowsExtraction()) return
        if (!outputFluidTank.allowsInsertion()) return
        val foundRecipe = findApplicableRecipes(currentLevel, inputFluidTank).firstOrNull()?.value ?: return
        doRecipe(foundRecipe, inputFluidTank, outputFluidTank)
    }

    private fun doRecipe(recipe: CentrifugingRecipe, inputTank: FluidContainer, outputTank: FluidContainer) {
        // check if we can actually output it
        if (recipe.result.amountPerTick != outputTank.insert(FluidResource.of(recipe.result.fluid),recipe.result.amountPerTick, true)) return
        // can we actually extract those fluids to start it
        val canStartRecipe = recipe.inputs.toList().all {
            val extracted = inputTank.extract(FluidResource.of(it.fluid), it.amountPerTick,true)
            extracted == it.amountPerTick
        }
        if (!canStartRecipe) return
        /// actually extract the fluids
        val actuallyExtracted = recipe.inputs.toList().all {
            val extracted = inputTank.extract(FluidResource.of(it.fluid),it.amountPerTick, false)
            extracted == it.amountPerTick
        }
        if (!actuallyExtracted) throw UnexpectedException("Container $inputTank lied when simulated to $this!")
        val inserted = outputTank.insert(FluidResource.of(recipe.result.fluid),recipe.result.amountPerTick,false)
        if (inserted != recipe.result.amountPerTick) throw UnexpectedException("Container $inputTank lied when simulated to $this!")
    }


    private fun findApplicableRecipes(serverLevel: ServerLevel, inputTank: FluidContainer): List<RecipeHolder<CentrifugingRecipe?>?> {
        val container = CentrifugingContainer(inputTank)
        return serverLevel.server.recipeManager.getAllRecipesFor(CreateEstrogenRecipes.CENTRIFUGING).filter {
            it.value.matches(container, serverLevel)
        }
    }
}