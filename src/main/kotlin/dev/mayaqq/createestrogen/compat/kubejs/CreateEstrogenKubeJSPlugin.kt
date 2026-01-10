package dev.mayaqq.createestrogen.compat.kubejs

import dev.latvian.mods.kubejs.KubeJSPlugin
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent
import dev.mayaqq.createestrogen.compat.kubejs.schemas.CentrifugingRecipeSchema

class CreateEstrogenKubeJSPlugin : KubeJSPlugin() {
    override fun registerRecipeSchemas(event: RegisterRecipeSchemasEvent) {
        event.namespace("createestrogen").register("centrifuging", CentrifugingRecipeSchema.SCHEMA)
    }
}