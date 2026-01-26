package dev.mayaqq.createestrogen.content

import dev.mayaqq.createestrogen.id
import dev.mayaqq.cynosure.utils.itemTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object CreateEstrogenTags {
    object Items {
        val EAR_GOGGLES = itemTag(id("ear_goggles"))
        val HEAD: TagKey<Item> = itemTag(ResourceLocation("trinkets", "head/face"))
        val CURIOS_HEAD: TagKey<Item> = itemTag(ResourceLocation("curios", "head"))
    }
}