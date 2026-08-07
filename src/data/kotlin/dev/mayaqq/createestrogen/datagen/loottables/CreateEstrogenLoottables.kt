package dev.mayaqq.createestrogen.datagen.loottables

import dev.mayaqq.createestrogen.content.CreateEstrogenBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.concurrent.CompletableFuture

class CreateEstrogenLoottables(
    output: PackOutput,
    lookup: CompletableFuture<HolderLookup.Provider>
) : LootTableProvider(output, setOf(), listOf(
    SubProviderEntry(
        ::CreateEstrogenBlockSubLoottables,
        LootContextParamSets.BLOCK
    )
), lookup)

class CreateEstrogenBlockSubLoottables(
    lookup: HolderLookup.Provider
): BlockLootSubProvider(setOf(), FeatureFlags.DEFAULT_FLAGS, lookup) {
    override fun generate() {
        dropSelf(CreateEstrogenBlocks.Centrifuge)
        dropSelf(CreateEstrogenBlocks.MothSeat)
    }

    override fun getKnownBlocks(): Iterable<Block> {
        return listOf(CreateEstrogenBlocks.Centrifuge, CreateEstrogenBlocks.MothSeat)
    }
}