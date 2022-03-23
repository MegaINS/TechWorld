package ru.megains.techworld.common.block

import org.joml.Vector3d
import ru.megains.techworld.common.physics.{BlockSize, BoundingBox}
import ru.megains.techworld.common.utils.Direction

class BlockState(val block: Block, val x:Int, val y:Int, val z:Int, val direction: Direction = Direction.NONE, val dx:Int = 0, val dy:Int = 0, val dz:Int = 0) {


    def isFullBlock: Boolean = !block.isInstanceOf[BlockCell]

    def isCollision(blockState: BlockState): Boolean = block.getSelectedBoundingBox(this).exists(_.isCollision(blockState.getSelectedBoundingBox))

    def getBoundingBox: List[BoundingBox] = block.getBoundingBox(this)

    def getSelectedBoundingBox:List[BoundingBox]  = block.getSelectedBoundingBox(this)

    def getBlockSize:BlockSize = block.getBlockSize(this)

    def getSelectedBlockSize:BlockSize = block.getSelectedBlockSize(this)

    def getBlockBody:List[BoundingBox]  = block.getBlockBody(this)

    def getSelectedBlockBody:List[BoundingBox]  = block.getSelectedBlockBody(this)

//    def collisionRayTrace(posIn: Vector3d, posEnd: Vector3d): RayTraceResult = {
//        block.collisionRayTrace(this,posIn,posEnd)
//    }

    def isOpaqueCube:Boolean = block.isOpaqueCube

    def isAirBlock(state: BlockState):Boolean = block.isAirBlock(state)

    def setBlock(blockState: BlockState): Unit = {
        if(!isFullBlock)  block.asInstanceOf[BlockCell].setBlock(blockState)
    }

}
