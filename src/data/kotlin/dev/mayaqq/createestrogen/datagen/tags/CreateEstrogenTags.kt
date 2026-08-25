package dev.mayaqq.createestrogen.datagen.tags

import com.simibubi.create.AllTags
import com.simibubi.create.content.logistics.box.PackageItem
import dev.mayaqq.createestrogen.MOD_ID
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture
import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.estrogen.content.EstrogenTags
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.data.tags.TagsProvider
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class CreateEstrogenBlockTags(
    output: PackOutput,
    lookup: CompletableFuture<HolderLookup.Provider>,
    efh: ExistingFileHelper
) : BlockTagsProvider(output, lookup, MOD_ID, efh) {
    override fun addTags(provider: HolderLookup.Provider) {
        tag(EstrogenTags.Blocks.PICKAXE_MINABLE)
            .add(CreateEstrogenBlocks.Centrifuge)
        tag(BlockTags.MINEABLE_WITH_AXE)
            .add(CreateEstrogenBlocks.MothSeat)
        tag(AllTags.AllBlockTags.SEATS.tag)
            .add(CreateEstrogenBlocks.MothSeat)
    }
}

class CreateEstrogenItemTags(
    output: PackOutput,
    lookup: CompletableFuture<HolderLookup.Provider>,
    blockLookup: CompletableFuture<TagLookup<Block>>,
    efh: ExistingFileHelper
) : ItemTagsProvider(output, lookup, blockLookup, MOD_ID, efh) {
    override fun addTags(provider: HolderLookup.Provider) {
        tag(AllTags.AllItemTags.SEATS.tag)
            .add(CreateEstrogenBlocks.MothSeat.asItem())
        tag(EstrogenTags.Items.NON_RECOLORABLE)
            .add(CreateEstrogenBlocks.MothSeat.asItem())

        tag(AllTags.AllItemTags.PACKAGES.tag)
            .add(*CreateEstrogenItems.allEstrogenPillBoxes.map(RegistryEntry<PackageItem>::value).toTypedArray())
        tag(AllTags.AllItemTags.CURIOS_HEAD.tag)
            .add(CreateEstrogenItems.CatEarGoggles)
    }
}