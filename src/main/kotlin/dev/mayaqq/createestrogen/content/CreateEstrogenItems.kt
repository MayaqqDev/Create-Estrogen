package dev.mayaqq.createestrogen.content

import com.simibubi.create.content.equipment.goggles.GogglesModel
import com.simibubi.create.content.logistics.box.PackageItem
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem
import com.simibubi.create.foundation.data.CreateRegistrate
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.mayaqq.createestrogen.CreateEstrogen
import dev.mayaqq.createestrogen.client.content.baubles.EarsGogglesRenderer
import dev.mayaqq.createestrogen.content.items.EarsGogglesItem
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.content.bauble
import dev.mayaqq.estrogen.content.baubleWithRenderer
import net.minecraft.client.resources.model.BakedModel
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

    val catEarGoggles by item("cat_ear_goggles", ::EarsGogglesItem) {
        properties {
            stacksTo(1)
        }
        onRegister {
            CreateRegistrate.itemModel<EarsGogglesItem> {
                NonNullFunction {
                    GogglesModel(it) as BakedModel
                }
            }
        }
        bauble()
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