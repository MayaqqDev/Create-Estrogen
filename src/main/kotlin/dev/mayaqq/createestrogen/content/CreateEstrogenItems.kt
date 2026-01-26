package dev.mayaqq.createestrogen.content

import com.simibubi.create.content.logistics.box.PackageItem
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.client.content.entityRenderers.goggles.CatEarGogglesBaubleRenderer
import dev.mayaqq.createestrogen.content.items.CatEarGoggles
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles
import dev.mayaqq.estrogen.content.baubleWithRenderer
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry.RegistryEntry
import uwu.serenity.kritter.stdlib.item

object CreateEstrogenItems: Registrar<Item> by CreateEstrogen..Registries.ITEM {
    val UsedFilter by item("used_filter", ::Item)
    val IncompleteEstrogenPatch by item("incomplete_estrogen_patches", ::SequencedAssemblyItem) {
        properties {
            stacksTo(1)
        }
    }
    val IncompleteUwU by item("incomplete_uwu", ::SequencedAssemblyItem) {
        properties {
            stacksTo(1)
        }
    }

    val CatEarGoggles by item("cat_ear_goggles", ::CatEarGoggles) {
        properties {
            stacksTo(1)
            baubleWithRenderer(::CatEarGogglesBaubleRenderer)
        }
    }

    val allEstrogenPillBoxes: List<RegistryEntry<PackageItem>> =
        CreateEstrogenPackageStyles.estrogenPillStyles.map { style ->
            style.itemId
            item(style.itemId.path, { PackageItem(it, style) }) {
                properties {
                    stacksTo(1)
                }

            }
        }

}