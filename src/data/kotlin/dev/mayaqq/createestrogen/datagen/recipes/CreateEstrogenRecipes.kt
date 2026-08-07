package dev.mayaqq.createestrogen.datagen.recipes

import com.simibubi.create.AllBlocks
import com.simibubi.create.AllItems
import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import dev.mayaqq.createestrogen.id
import dev.mayaqq.cynosure.utils.tag
import dev.mayaqq.estrogen.cid
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.tags.ItemTags
import java.util.concurrent.CompletableFuture

class CreateEstrogenRecipes(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>): RecipeProvider(output, lookup) {
    override fun buildRecipes(output: RecipeOutput) {
        with(output) {
            crafting()
        }
    }

    private fun RecipeOutput.crafting() {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.EstrogenChipCookie)
            .requires(EstrogenItems.EstrogenPill)
            .unlockedBy(getHasName(EstrogenItems.EstrogenPill), has(EstrogenItems.EstrogenPill))
            .requires(AllItems.WHEAT_FLOUR)
            .requires(AllItems.BAR_OF_CHOCOLATE)
            .save(this, id("estrogen_pill"))
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CreateEstrogenBlocks.Centrifuge, 1)
            .define('P', AllBlocks.MECHANICAL_PUMP)
            .define('T', AllBlocks.FLUID_TANK)
            .define('C', Registries.ITEM.tag(cid("plates/copper")))
            .define('M', AllItems.PRECISION_MECHANISM)
            .pattern("CTC")
            .pattern("PMP")
            .pattern("CTC")
            .unlockedBy(getHasName(AllItems.PRECISION_MECHANISM), has(AllItems.PRECISION_MECHANISM))
            .save(this, id("centrifuge"))
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, CreateEstrogenBlocks.MothSeat)
            .requires(EstrogenBlocks.QuiltedMothWool.get(), 1)
            .requires(ItemTags.WOODEN_SLABS)
            .unlockedBy(
                getHasName(EstrogenBlocks.MothWool.get()), has(EstrogenBlocks.MothWool.get())
            )
            .save(this, id("moth_wool"))
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.CookieJar.get(), 1)
            .define('G', Registries.ITEM.tag(cid("glass_panes")))
            .define('Z', Registries.ITEM.tag(cid("nuggets/zinc")))
            .pattern("GZG")
            .pattern("G G")
            .pattern("GGG")
            .unlockedBy(getHasName(AllItems.ZINC_NUGGET), has(AllItems.ZINC_NUGGET))
            .save(this, id("cookie_jar"))
    }
}