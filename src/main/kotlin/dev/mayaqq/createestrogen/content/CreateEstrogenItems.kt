package dev.mayaqq.createestrogen.content

import com.simibubi.create.content.logistics.box.PackageItem
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem
import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.item.item
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

object CreateEstrogenItems: Registrar<Item> by Registrar(MOD_ID, Registries.ITEM) {
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