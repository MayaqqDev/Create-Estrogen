package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.AllItems
import com.simibubi.create.api.data.recipe.FillingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.EstrogenPotions
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.material.Fluids
import java.util.concurrent.CompletableFuture


@Suppress("UnstableApiUsage")
class CreateEstrogenFillingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : FillingRecipeGen(output, lookup, MOD_ID) {
    init {
        create(
            "crystal_estrogen_pill"
        ) { builder ->
            builder
                .require(EstrogenItems.EstrogenPill)
                .require(EstrogenFluids.MoltenAmethyst.value, 250)
                .output(EstrogenItems.CrystalEstrogenPill, 1)
        }

        create(
            "estrogen_pill"
        ) { builder ->
            builder
                .require(Items.COOKIE)
                .require(EstrogenFluids.LiquidEstrogen.value, 250)
                .output(EstrogenItems.EstrogenPill, 1)
        }

        create(
            "filter"
        ) { builder ->
            builder
                .require(CreateEstrogenItems.UsedFilter)
                .require(Fluids.WATER, 250)
                .output(AllItems.FILTER, 1)
        }

        create(
            "estrogen_tipped_arrow"
        ) { builder ->
            builder
                .require(Items.ARROW)
                .require(EstrogenFluids.LiquidEstrogen.value, 250)
                .output(PotionContents.createItemStack(Items.TIPPED_ARROW, EstrogenPotions.EstrogenPotion.holder))
        }

        create("gender_change_potion") { builder ->
            builder
                .require(Items.GLASS_BOTTLE)
                .require(EstrogenFluids.GenderFluid.value, 250)
                .output(EstrogenItems.GenderChangePotion)
        }
    }
}