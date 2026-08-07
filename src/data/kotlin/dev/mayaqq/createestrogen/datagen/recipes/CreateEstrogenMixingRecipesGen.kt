package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.AllItems
import com.simibubi.create.api.data.recipe.MixingRecipeGen
import com.simibubi.create.content.processing.recipe.HeatCondition
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

@Suppress("UnstableApiUsage")
class CreateEstrogenMixingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : MixingRecipeGen(output, lookup, MOD_ID) {
    init {
        create("filtrated_horse_urine") {
            it.require(EstrogenFluids.HorseUrine.value, 250)
                .require(AllItems.FILTER)
                .output(EstrogenFluids.FiltratedHorseUrine.value, 250)
                .output(CreateEstrogenItems.UsedFilter)
        }
        create("molten_amethyst") {
            it.require(Items.AMETHYST_SHARD)
                .output(EstrogenFluids.MoltenAmethyst.value, 250)
                .requiresHeat(HeatCondition.HEATED)
        }
        create("balls") {
            it.require(Items.SLIME_BALL)
                .output(EstrogenItems.Balls)
                .output(EstrogenFluids.MoltenSlime.value, 250)
                .requiresHeat(HeatCondition.HEATED)
        }
        create("testosterone_mixture") {
            it.require(EstrogenItems.TestosteronePowder).require(Items.COAL)
                .output(EstrogenFluids.TestosteroneMixture.value, 500)
                .requiresHeat(HeatCondition.HEATED)
        }
        create("gender_fluid") {
            it.require(EstrogenFluids.LiquidEstrogen.value, 250)
                .require(EstrogenFluids.TestosteroneMixture.value, 250)
                .require(Items.POPPY)
                .require(Items.DANDELION)
                .require(Items.BLUE_ORCHID)
                .require(Items.ALLIUM)
                .require(Items.AZURE_BLUET)
                .require(Items.RED_TULIP)
                .require(Items.ORANGE_TULIP)
                .output(EstrogenFluids.GenderFluid.value, 500)
        }
    }
}