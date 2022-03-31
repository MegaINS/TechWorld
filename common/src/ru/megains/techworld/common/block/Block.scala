package ru.megains.techworld.common.block

import org.joml.Vector3d
import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.physics.{BlockSize, BoundingBox}
import ru.megains.techworld.common.utils.{Direction, RayTraceResult}
import ru.megains.techworld.common.world.World

import scala.language.postfixOps

class Block(val name: String) {


    val blockType: BlockType.Value = BlockType.SOLID

    protected val blockSize:BlockSize = new BlockSize(16)

    protected val physicalBody: BoundingBox = new BoundingBox(0, 0, 0, 1, 1, 1)
    protected val boundingBox:BoundingBox = new BoundingBox(0,0,0,1,1,1)


    def getSelectedBoundingBox(blockState: BlockState): List[BoundingBox]  = getBoundingBox(blockState).map(_.sum(blockState.x, blockState.y, blockState.z))

    def getBoundingBox(blockState: BlockState):List[BoundingBox] = List( boundingBox.rotate(blockState.direction))

    def getBlockSize(blockState: BlockState):BlockSize = blockSize.rotate(blockState.direction)

    def getSelectedBlockSize(blockState: BlockState):BlockSize = getBlockSize(blockState).sum(blockState.dx,blockState.dy,blockState.dz)

    def getBlockBody(state: BlockState):  List[BoundingBox] = List( physicalBody.rotate(state.direction))

    def getSelectedBlockBody(blockState: BlockState):  List[BoundingBox]=  getBlockBody(blockState).map(_.sum(blockState.x, blockState.y, blockState.z))

    def isOpaqueCube = true

    def isAirBlock(state: BlockState) = false

    def collisionRayTrace(blockState: BlockState, posStartIn: Vector3d, posEndIn: Vector3d): RayTraceResult = {
        val posIn: Vector3d = new Vector3d(posStartIn).sub(blockState.x , blockState.y , blockState.z )
        val posEnd: Vector3d = new Vector3d(posEndIn).sub(blockState.x , blockState.y , blockState.z )
        //ToDo Head
        val res: List[(Vector3d, Direction)] = getBoundingBox(blockState).map(_.calculateIntercept(posIn, posEnd)).filter(_!=null)



        if (res.nonEmpty)
            RayTraceResult(blockState, res.head._1, res.head._2)
        else
            RayTraceResult.VOID
    }


    def getSelectPosition(worldIn: World, entity: Entity, objectMouseOver: RayTraceResult): BlockState = {


        val posTarget: BlockPos = new BlockPos(
            objectMouseOver.blockState.x,
            objectMouseOver.blockState.y,
            objectMouseOver.blockState.z)
        val hitVec: Vector3d = objectMouseOver.hitVec
        var posSet: BlockPos = objectMouseOver.sideHit match {
            case Direction.DOWN => posTarget.sum(0, -1, 0)
            case Direction.WEST => posTarget.sum(-1, 0, 0)
            case Direction.NORTH => posTarget.sum(0, 0, -1)
            case _ => posTarget
        }

        posSet = posSet.sum(hitVec.x toInt, hitVec.y toInt , hitVec.z toInt)

        val blockState = new BlockState(this, posSet.x,posSet.y,posSet.z, entity.side)
        if (worldIn.isAirBlock(blockState)) blockState else null
    }

    def onBlockActivated(pos: BlockState,world: World,  player: EntityPlayer, itemStack: ItemStack, blockDirection:Direction, float1: Float, float2: Float, float3: Float): Boolean = {
        false
    }
    def removedByPlayer(world: World, pos: BlockState, player: EntityPlayer, willHarvest: Boolean): Boolean = {
        onBlockHarvested(world, pos, player)
        world.removeBlock(pos)
        true
    }
    def onBlockHarvested(worldIn: World, pos: BlockState, player: EntityPlayer): Unit = {
    }

    def onBlockDestroyedByPlayer(worldIn: World, pos: BlockPos): Unit = {
    }
}


