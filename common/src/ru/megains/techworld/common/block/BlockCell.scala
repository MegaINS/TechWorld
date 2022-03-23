package ru.megains.techworld.common.block

import org.joml.Vector3d
import ru.megains.techworld.common.physics.BoundingBox

import scala.collection.mutable


class BlockCell(x: Int, y: Int, z: Int) extends Block("BlockCell") {


    def isEmpty: Boolean = blocks.isEmpty

    val childrenBlocks = new mutable.HashSet[BlockState]()
    val blocks = new mutable.HashSet[BlockState]()



    override def isOpaqueCube: Boolean = false

    //override def getBlockSize(blockState: BlockState): BlockSize = blocks.toList.flatMap(_.getBlockSize)

    //override def getSelectedBlockSize(blockState: BlockState): BlockSize = blocks.toList.flatMap(_.getSelectedBlockSize)

    override def getBlockBody(state: BlockState): List[BoundingBox] = blocks.toList.flatMap(_.getBlockBody)

    override def getSelectedBlockBody(blockState: BlockState): List[BoundingBox] = blocks.toList.flatMap(_.getSelectedBlockBody)

    override def getBoundingBox(blockState: BlockState): List[BoundingBox] = blocks.toList.flatMap(_.getBoundingBox)

    override def getSelectedBoundingBox(blockState: BlockState): List[BoundingBox]= blocks.toList.flatMap(_.getSelectedBoundingBox)

    def removeBlock(blockStateIn: BlockState): Any = {
        val blockState = getBlock(blockStateIn)
        blocks -= blockState
        if (isChildren(blockStateIn.x, blockStateIn.y, blockStateIn.z)) {
            childrenBlocks -= blockState
        }
    }

    def getBlock(blockStateIn: BlockState): BlockState = {


        blocks.find(s =>
            s.x == blockStateIn.x &&
                    s.y == blockStateIn.y &&
                    s.z == blockStateIn.z &&
                    s.dx == blockStateIn.dx &&
                    s.dy == blockStateIn.dy &&
                     s.dz == blockStateIn.dz
        ).getOrElse(new BlockState(BlockAir, blockStateIn.x, blockStateIn.y, blockStateIn.z,blockStateIn.direction,blockStateIn.dx,blockStateIn.dy,blockStateIn.dz))
    }

    override def isAirBlock(state1: BlockState): Boolean = {
        blocks.forall(!_.isCollision(state1))
    }



    def setBlock(blockState: BlockState): Unit = {
        blocks += blockState
        if (isChildren(blockState.x, blockState.y, blockState.z)) {
            childrenBlocks += blockState
        }

    }

    def isChildren(x: Int, y: Int, z: Int): Boolean = x == this.x && y == this.y && z == this.z

    def getChildrenBlocksState: List[BlockState] = {
        childrenBlocks.toList
    }
    def  getBlocksState: List[BlockState] = {
        blocks.toList
    }


//    override def collisionRayTrace(blockState: BlockState, posIn: Vector3d, posEnd: Vector3d): RayTraceResult = {
//        blocks.find(bs => {
//            bs.collisionRayTrace(posIn, posEnd) != RayTraceResult.VOID
//        }) match {
//            case Some(value) => value.collisionRayTrace(posIn, posEnd)
//            case None => RayTraceResult.VOID
//        }
//    }
}
