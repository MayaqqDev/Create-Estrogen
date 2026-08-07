package dev.mayaqq.createestrogen.test

import dev.mayaqq.createestrogen.MOD_ID
import dev.mayaqq.createestrogen.content.isEmpty
import dev.mayaqq.estrogen.content.EstrogenFluids
import earth.terrarium.common_storage_lib.fluid.FluidApi
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.gametest.framework.GameTest
import net.minecraft.gametest.framework.GameTestAssertPosException
import net.minecraft.gametest.framework.GameTestHelper
import net.neoforged.neoforge.gametest.GameTestHolder

@GameTestHolder(MOD_ID)
class CentrifugeTests {
    @Suppress("Unused")
    @GameTest(template = "crafts", timeoutTicks = 1)
    fun testCentrifugeCrafts(gameTestHelper: GameTestHelper) {
        gameTestHelper.absolutePos(BlockPos(0, 3, 0))
        val centrifugeInputTankPosition = gameTestHelper.absolutePos(BlockPos(0, 2, 0))
        val centrifugeOutputTankPosition = gameTestHelper.absolutePos(BlockPos(0, 4, 0))
        val inputTank = FluidApi.BLOCK.find(gameTestHelper.level, centrifugeInputTankPosition, Direction.UP)!!

        val outputTank = FluidApi.BLOCK.find(gameTestHelper.level, centrifugeOutputTankPosition, Direction.DOWN)!!
        inputTank.insert(FluidResource.of(EstrogenFluids.FiltratedHorseUrine.value!!),81, false)
        gameTestHelper.runAfterDelay(1) {
            gameTestHelper.succeedWhen {
                if (outputTank.extract(
                        FluidResource.of(EstrogenFluids.LiquidEstrogen.value!!),
                        81,
                        true
                    ) == 81L
                ) {
                    gameTestHelper.assertTrue(inputTank.isEmpty(), "Input tank has to have been used up")
                } else {
                    throw GameTestAssertPosException(
                        "output tank hasn't got any estrogen!",
                        centrifugeOutputTankPosition,
                        gameTestHelper.relativePos(centrifugeOutputTankPosition),
                        gameTestHelper.tick
                    )
                }
            }
        }


    }
}