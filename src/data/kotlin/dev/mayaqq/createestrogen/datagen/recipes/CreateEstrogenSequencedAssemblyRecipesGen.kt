package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen
import com.simibubi.create.content.fluids.transfer.FillingRecipe
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe
import com.simibubi.create.content.kinetics.press.PressingRecipe
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class CreateEstrogenSequencedAssemblyRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) :
    SequencedAssemblyRecipeGen(output, lookup, MOD_ID) {
    init {
        create("estrogen_patch") {
            it.require(Items.PAPER)
                .transitionTo(CreateEstrogenItems.IncompleteEstrogenPatch)
                .addOutput(EstrogenItems.EstrogenPatch.get().getFullStack(), 120f)
                .addOutput(EstrogenItems.EstrogenPill, 16f)
                .addOutput(Items.PAPER, 5f)
                .addOutput(Items.SLIME_BALL, 5f)
                .addOutput(EstrogenItems.HorseUrineBottle, 4f)
                .loops(5)
                .addStep(::FillingRecipe) { rb -> rb.require(EstrogenFluids.MoltenSlime.value, 250) }
                .addStep(::FillingRecipe) { rb ->
                    rb.require(
                        EstrogenFluids.LiquidEstrogen.value,
                        250
                    )
                }
                .addStep(::DeployerApplicationRecipe) { rb -> rb.require(Items.PAPER) }

        }
        create("uwu") {
            it.require(Items.NETHERITE_INGOT)
                .transitionTo(CreateEstrogenItems.IncompleteUwU)
                .addOutput(EstrogenItems.ColonThree, 1f)
                .loops(10)
                .addStep(::DeployerApplicationRecipe) { rb -> rb.require(Items.NETHER_STAR) }
                .addStep(::PressingRecipe) { rb -> rb }
                .addStep(::DeployerApplicationRecipe) { rb -> rb.require(Items.NETHER_STAR) }
                .addStep(::PressingRecipe) { rb -> rb }
                .addStep(::DeployerApplicationRecipe) { rb -> rb.require(Items.NETHER_STAR) }
                .addStep(::PressingRecipe) { rb -> rb }
        }
    }

}