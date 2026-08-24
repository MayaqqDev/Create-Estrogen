package dev.mayaqq.createestrogen.client.content.blockRenderers

import dev.engine_room.flywheel.lib.model.baked.PartialModel
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles
import dev.mayaqq.createestrogen.id


object CreateEstrogenRenderer  {
    val CENTRIFUGE_COG: PartialModel = PartialModel.of(id("block/centrifuge/cog"))

    @JvmField
    val ESTROGEN_PACKAGES_TO_HIDE_AS: List<PartialModel> = CreateEstrogenPackageStyles.estrogenPillStyles.map { packageStyle ->
        PartialModel.of(id("item/" + packageStyle.itemId.path))
    }
}