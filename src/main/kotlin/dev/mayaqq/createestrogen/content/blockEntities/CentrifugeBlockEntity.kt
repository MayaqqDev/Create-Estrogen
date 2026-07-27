package dev.mayaqq.createestrogen.content.blockEntities

import com.simibubi.create.content.kinetics.base.KineticBlockEntity
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.content.CreateEstrogenRecipes
import dev.mayaqq.createestrogen.content.recipes.CentrifugingContainer
import dev.mayaqq.createestrogen.content.recipes.CentrifugingRecipe
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.fluids.capability.IFluidHandler
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction
import java.rmi.UnexpectedException
import kotlin.math.absoluteValue

class CentrifugeBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) :
    KineticBlockEntity(type, pos, state) {
    override fun tick() {
        super.tick()
        if (isVirtual || level?.isClientSide == true || speed.absoluteValue < 256) return
        val currentLevel = level as ServerLevel
        val outputFluidTank =
            currentLevel.getCapability(Capabilities.FluidHandler.BLOCK, blockPos.above(), Direction.DOWN) ?: return
        val inputFluidTank =
            currentLevel.getCapability(Capabilities.FluidHandler.BLOCK, blockPos.below(), Direction.UP) ?: return
        val foundRecipe = findApplicableRecipes(currentLevel, inputFluidTank).firstOrNull() ?: return
        doRecipe(foundRecipe, inputFluidTank, outputFluidTank)
    }

    private fun doRecipe(recipe: CentrifugingRecipe, inputTank: IFluidHandler, outputTank: IFluidHandler) {
        // check if we can actually output it
        if (recipe.result.amountPerTick != outputTank.fill(recipe.result.stack, FluidAction.SIMULATE).toLong()) return
        // can we actually extract those fluids to start it
        val canStartRecipe = recipe.inputs.toList().all {
            val extracted = inputTank.drain(it.stack, FluidAction.SIMULATE)
            extracted.amount.toLong() == it.amountPerTick
        }
        if (!canStartRecipe) return
        /// actually extract the fluids
        val actuallyExtracted = recipe.inputs.toList().all {
            val extracted = inputTank.drain(it.stack, FluidAction.EXECUTE)
            extracted.amount.toLong() == it.amountPerTick
        }
        if (!actuallyExtracted) throw UnexpectedException("Container $inputTank lied when simulated to $this!")
        val inserted = outputTank.fill(recipe.result.stack, FluidAction.EXECUTE)
        if (inserted.toLong() != recipe.result.amountPerTick) throw UnexpectedException("Container $outputTank lied when simulated to $this!")
    }


    private fun findApplicableRecipes(serverLevel: ServerLevel, inputTank: IFluidHandler): List<CentrifugingRecipe> {
        val container = CentrifugingContainer(inputTank)
        return serverLevel.server.recipeManager
            .getAllRecipesFor(CreateEstrogenRecipes.CENTRIFUGING.value!!)
            .map { it.value() }
            .filter { it.matches(container, serverLevel) }
    }
}
