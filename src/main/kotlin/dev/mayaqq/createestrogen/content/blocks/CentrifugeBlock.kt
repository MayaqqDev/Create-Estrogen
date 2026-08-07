package dev.mayaqq.createestrogen.registry.blocks

import com.simibubi.create.content.equipment.wrench.IWrenchable
import com.simibubi.create.content.kinetics.base.IRotate
import com.simibubi.create.content.kinetics.base.KineticBlock
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel
import com.simibubi.create.foundation.advancement.AdvancementBehaviour
import com.simibubi.create.foundation.block.IBE
import dev.mayaqq.createestrogen.content.CreateEstrogenBlockEntities
import dev.mayaqq.createestrogen.content.blockEntities.CentrifugeBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape


class CentrifugeBlock(properties: Properties) : KineticBlock(properties), IBE<CentrifugeBlockEntity>,ICogWheel,IWrenchable   {
    override fun getRotationAxis(p0: BlockState?): Direction.Axis = Direction.Axis.Y

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity = CentrifugeBlockEntity(blockEntityType, p0,p1)

    override fun getBlockEntityClass(): Class<CentrifugeBlockEntity> = CentrifugeBlockEntity::class.java

    override fun getBlockEntityType(): BlockEntityType<out CentrifugeBlockEntity> = CreateEstrogenBlockEntities.Centrifuge

    override fun setPlacedBy(pLevel: Level, pPos: BlockPos, pState: BlockState, pPlacer: LivingEntity?, pStack: ItemStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack)
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer)
    }


    @Deprecated("Deprecated in Java")
    override fun canSurvive(p0: BlockState, p1: LevelReader, p2: BlockPos): Boolean = true

    override fun isSmallCog(): Boolean = true
    @Deprecated("Deprecated in Java")
    override fun isPathfindable(p0: BlockState, p3: PathComputationType): Boolean = false

    @Deprecated("Deprecated in Java")
    override fun getShape(p0: BlockState, p1: BlockGetter, p2: BlockPos, p3: CollisionContext): VoxelShape = Shapes.block()
    @Deprecated("Deprecated in Java")
    override fun updateShape(
        p0: BlockState,
        p1: Direction,
        p2: BlockState,
        p3: LevelAccessor,
        p4: BlockPos,
        p5: BlockPos
    ): BlockState =p0
    override fun hasShaftTowards(world: LevelReader?, pos: BlockPos?, state: BlockState?, face: Direction): Boolean {
        return face == Direction.DOWN
    }
    override fun getMinimumRequiredSpeedLevel(): IRotate.SpeedLevel = IRotate.SpeedLevel.valueOf("MAX")

    override fun <S : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<S>
    ): BlockEntityTicker<S>? {
        return super.getTicker(level, state, blockEntityType)
    }
}