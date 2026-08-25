package dev.mayaqq.createestrogen.features.goggles

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.content.CreateEstrogenItems
import dev.mayaqq.createestrogen.features.goggles.CatEarGoggleLoader.TypeInstance
import dev.mayaqq.createestrogen.id
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.core.codecs.forGetter
import dev.mayaqq.cynosure.utils.result.flatMap
import invoke.kitty.kritter.events.DataPackSyncEvent
import invoke.kitty.kritter.resources.AsyncResourceReloadListener
import invoke.kitty.kritter.utils.coroutines.mapAsync
import invoke.kitty.kritter.utils.dfu.toKtResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller

object CatEarGoggleLoader : AsyncResourceReloadListener<List<TypeInstance?>> {

    init {
        DataPackSyncEvent.subscribe { player, _ ->
            CreateEstrogenItems.CatEarGoggles.syncTypes(player)
        }
    }

    data class TypeInstance(val replace: Boolean, val values: List<ResourceLocation>)

    val TYPES_CODEC: Codec<TypeInstance> = RecordCodecBuilder.create { instance -> instance.group(
        Codec.BOOL.optionalFieldOf("replace", false) forGetter TypeInstance::replace,
        ResourceLocation.CODEC.listOf() fieldOf TypeInstance::values
    ).apply(instance, ::TypeInstance) }

    override suspend fun load(resourceManager: ResourceManager, profiler: ProfilerFiller): List<TypeInstance?> = coroutineScope {
        resourceManager.getResourceStack(id("cat_ear_goggle_types.json"))
            .mapAsync(Semaphore(16)) { resource ->
                resource.openAsReader().use { reader ->
                    runCatching { JsonParser.parseReader(reader) }
                        .flatMap { TYPES_CODEC.parse(JsonOps.INSTANCE, it).toKtResult() }
                        .onFailure { CreateEstrogen.error("Error loading Cat Ear Goggle Types", it) }
                        .getOrNull()
                }
            }
    }

    override suspend fun apply(data: List<TypeInstance?>, resourceManager: ResourceManager, profiler: ProfilerFiller) {
        CreateEstrogenItems.CatEarGoggles.loadTypes(
            data.filterNotNull().takeLastWhile { !it.replace }.flatMap { it.values }
        )
    }
}