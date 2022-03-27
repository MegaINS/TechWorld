package ru.megains.techworld.common.utils

import org.joml.Vector3d
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.utils.RayTraceType.RayTraceType


class RayTraceResult(var traceType:RayTraceType = RayTraceType.VOID) {

    var blockState: BlockState = _
    var hitVec: Vector3d = _
    var sideHit: Direction = _
    def this(blockStateIn: BlockState,hitVecIn: Vector3d,sideHitIn: Direction) ={
        this(RayTraceType.BLOCK)
        blockState = blockStateIn
        hitVec = hitVecIn
        sideHit = sideHitIn
    }


    def canEqual(other: Any): Boolean = other.isInstanceOf[RayTraceResult]

    override def equals(other: Any): Boolean = other match {
        case that: RayTraceResult =>
            (that canEqual this) &&
                    blockState == that.blockState &&
                    hitVec == that.hitVec &&
                    sideHit == that.sideHit &&
                    traceType == that.traceType
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(blockState, hitVec, sideHit, traceType)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}

object RayTraceResult{


    def apply(): RayTraceResult = new RayTraceResult()
    def apply(blockState: BlockState,hitVec: Vector3d,side: Direction): RayTraceResult = new RayTraceResult(blockState,hitVec,side)

    val VOID: RayTraceResult = apply()
}
