package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.api.data.recipe.DeployingRecipeGen
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class CreateEstrogenDeployingRecipesGen(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>) : DeployingRecipeGen(output, lookup, MOD_ID) {
    init {
        this.create("moth_elytra") {
            it
                .require(Items.ELYTRA)
                .require(EstrogenItems.MothFuzz)
                .output(EstrogenItems.MothElytra)
        }
    }
}