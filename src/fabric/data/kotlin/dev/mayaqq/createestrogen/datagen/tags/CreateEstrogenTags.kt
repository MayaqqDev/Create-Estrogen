package dev.mayaqq.createestrogen.datagen.tags

import com.simibubi.create.AllTags
import com.simibubi.create.content.logistics.box.PackageItem
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.datagen.api.platform.PlatformHelper
import dev.mayaqq.estrogen.datagen.api.tags.BaseTagProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture
import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.createestrogen.content.CreateEstrogenTags
import uwu.serenity.kritter.api.entry.RegistryEntry

class CreateEstrogenBlockTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.BlockProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(EstrogenTags.Blocks.PICKAXE_MINABLE)
            .add(CreateEstrogenBlocks.Centrifuge)
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
            .add(CreateEstrogenBlocks.MothSeat)
        getOrCreateTagBuilder(AllTags.AllBlockTags.SEATS.tag)
            .add(CreateEstrogenBlocks.MothSeat)
    }
}

class CreateEstrogenItemTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.ItemProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(AllTags.AllItemTags.SEATS.tag)
            .add(CreateEstrogenBlocks.MothSeat.asItem())
        getOrCreateTagBuilder(EstrogenTags.Items.NON_RECOLORABLE)
            .add(CreateEstrogenBlocks.MothSeat.asItem())

        getOrCreateTagBuilder(AllTags.AllItemTags.PACKAGES.tag)
            .add(*CreateEstrogenItems.allEstrogenPillBoxes.map(RegistryEntry<PackageItem>::value).toTypedArray())
        getOrCreateTagBuilder(CreateEstrogenTags.Item.EAR_GOGGLES)
            .add(CreateEstrogenItems.catEarGoggles)
        getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
    }
}