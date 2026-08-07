package dev.mayaqq.createestrogen.content.recipes

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.createestrogen.content.*
import dev.mayaqq.createestrogen.id
import dev.mayaqq.cynosure.core.bytecodecs.toByteCodec
import dev.mayaqq.cynosure.core.codecs.fieldOf
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluid

/**
 * Container for centrifuges, throws [UnsupportedOperationException] if any methods from [Container] are used
 * @param input fluid that is input into this recipe
 */
data class CentrifugingContainer(val input: FluidContainer) : RecipeInput {
    override fun getItem(index: Int): ItemStack = ItemStack.EMPTY
    override fun size(): Int = 0
}

/**
 * an ingredient for a recipe that requires a throughput of fluid instead of a specific amount
 * @property fluid the type of fluid to input
 * @property amountPerTick the amount per tick this can output/input of the fluid
 */
@JvmRecord
data class RatioFluidIngredient(
    val fluid: Fluid,
    val amountPerTick: Long
) {

    companion object {
        fun codec(): Codec<RatioFluidIngredient> = RecordCodecBuilder.create {instance ->
            instance.group(
                BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(RatioFluidIngredient::fluid),
                Codec.LONG.fieldOf("amount_per_tick").forGetter(RatioFluidIngredient::amountPerTick)
            ).apply(instance,::RatioFluidIngredient)
        }
        fun netCodec() : ByteCodec<RatioFluidIngredient> = ObjectByteCodec.create(
            BuiltInRegistries.FLUID.byNameCodec().toByteCodec() fieldOf RatioFluidIngredient::fluid ,
            ByteCodec.LONG fieldOf RatioFluidIngredient::amountPerTick,
            ::RatioFluidIngredient

        )
    }


}

/**
 * an output of an ingredient for a recipe that requires a throughput of fluid instead of a specific amount
 * @property fluid fluid to output
 * @property amountPerTick the amount per tick to output
 */
data class RatioFluidOutput(
    val fluid: Fluid,
    val amountPerTick: Long
) {
    companion object {
        fun codec(): Codec<RatioFluidOutput> = RecordCodecBuilder.create {instance ->
            instance.group(
                BuiltInRegistries.FLUID.byNameCodec().fieldOf(RatioFluidOutput::fluid),
                Codec.LONG.fieldOf("amount_per_tick").forGetter(RatioFluidOutput::amountPerTick)
            ).apply(instance,::RatioFluidOutput)
        }
        fun netCodec() : ByteCodec<RatioFluidOutput> = ObjectByteCodec.create(
            BuiltInRegistries.FLUID.byNameCodec().toByteCodec() fieldOf RatioFluidOutput::fluid ,
            (ByteCodec.LONG fieldOf RatioFluidOutput::amountPerTick),
            ::RatioFluidOutput
        )
    }

}

class CentrifugingRecipe(
                         val inputs: List<RatioFluidIngredient>,
                         val result: RatioFluidOutput) : Recipe<CentrifugingContainer>{
    override fun matches(circumstance: CentrifugingContainer, p1: Level): Boolean {
        /// this is assuming that .fluids always returns merged fluids
        val actualFluidAmounts = mutableMapOf<Fluid,Long>()
        for (fluidHolder in circumstance.input) {
            if (fluidHolder.amount <= 0) continue
            val fluidAmount = fluidHolder.amount
            actualFluidAmounts.compute(fluidHolder.resource.type) {_,actualAmount ->
                if (actualAmount == null) return@compute fluidAmount
                /// crash if overflow
                return@compute  Math.addExact(fluidAmount,actualAmount)
            }
        }
        /// actualFluidAmounts should now have all the fluids without any slots business
        /// check if ALL the ingredients are satisfied
        return inputs.all { ingredient -> ingredient.amountPerTick <= actualFluidAmounts.getOrDefault(ingredient.fluid,0) }
    }

    override fun assemble(container: CentrifugingContainer, registries: HolderLookup.Provider): ItemStack = result.fluid.bucket.defaultInstance

    override fun canCraftInDimensions(x: Int, y: Int): Boolean = true
    override fun getResultItem(registries: HolderLookup.Provider): ItemStack = result.fluid.bucket.defaultInstance


    override fun getSerializer(): RecipeSerializer<*> = CreateEstrogenSerializers.CENTRIFUGING_SERIALIZER

    override fun getType(): RecipeType<*> = CreateEstrogenRecipes.CENTRIFUGING
    companion object RecipeViewerInfo : dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo {
        val codec: Codec<CentrifugingRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                RatioFluidIngredient.codec().listOf().fieldOf("ingredients").forGetter(CentrifugingRecipe::inputs),
               RatioFluidOutput.codec().fieldOf("result").forGetter(CentrifugingRecipe::result)
            ).apply(instance,::CentrifugingRecipe)
        }

        val netCodec: ByteCodec<CentrifugingRecipe> = ObjectByteCodec.create(
            RatioFluidIngredient.netCodec().listOf() fieldOf CentrifugingRecipe::inputs,
            RatioFluidOutput.netCodec() fieldOf CentrifugingRecipe::result,
            ::CentrifugingRecipe
        )

        override val display: ItemStack
            get() = CreateEstrogenBlocks.Centrifuge.asItem().defaultInstance
        override val catalyst: ItemStack
            get() = Items.AIR.defaultInstance
        override val id: ResourceLocation
            get() = id("centrifuging")
        override val width: Int
            get() = 134
        override val height: Int
            get() = 80
        override val type: RecipeType<*>
            get() = CreateEstrogenRecipes.CENTRIFUGING
    }
}