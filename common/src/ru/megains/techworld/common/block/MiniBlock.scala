package ru.megains.techworld.common.block

import org.joml.{Vector3d, Vector3i}
import ru.megains.techworld.common.physics.BoundingBox
import ru.megains.techworld.common.world.World


class MiniBlock(name: String) extends Block(name) {


    override def getBoundingBox(state: BlockState):List[BoundingBox]  = {

        super.getBoundingBox(state).map(_.sum(0.0625f * state.dx, 0.0625f * state.dy, 0.0625f * state.dz))
    }

    override def getBlockBody(state: BlockState): List[BoundingBox] = {
        super.getBlockBody(state).map( _.sum(0.0625f * state.dx, 0.0625f * state.dy, 0.0625f * state.dz))
    }

//    override def getSelectPosition(worldIn: World, entity: Entity, objectMouseOver: RayTraceResult): BlockState = {
//
//
//        val side = entity.side
//        val posTarget: BlockPos = new BlockPos(
//            objectMouseOver.blockState.x,
//            objectMouseOver.blockState.y,
//            objectMouseOver.blockState.z)
//
//        var posSet: BlockPos = posTarget
//
//        val a = new Vector3d(Math.floor(objectMouseOver.hitVec.x), Math.floor(objectMouseOver.hitVec.y), Math.floor(objectMouseOver.hitVec.z))
//        val hitVec: Vector3d = new Vector3d(objectMouseOver.hitVec).sub(a)
//        //TODO Проблема с округлением
//        val offset = new Vector3i((hitVec.x *  Chunk.blockSize).toInt, (hitVec.y *  Chunk.blockSize).toInt, (hitVec.z *  Chunk.blockSize).toInt)
//
//
//        objectMouseOver.sideHit match {
//            case Direction.WEST =>
//                posSet = posTarget.sum(-1, 0, 0)
//                offset.add(16 - blockSize.maxX, 0, 0)
//            case Direction.DOWN =>
//                posSet = posTarget.sum(0, -1, 0)
//                offset.add(0, 16 - blockSize.maxY, 0)
//            case Direction.NORTH =>
//                posSet = posTarget.sum(0, 0, -1)
//                offset.add(0, 0, 16 - blockSize.maxZ)
//
//            case Direction.UP =>
//
//            case Direction.EAST =>
//
//            case Direction.SOUTH =>
//
//            case _ =>
//        }
//        //  posSet = posSet.sum(hitVec.x - posTarget.x toInt, hitVec.y - posTarget.y toInt, hitVec.z - posTarget.z toInt)
//        // posSet = posSet.sum((hitVec.x ).toInt, (hitVec.y ).toInt, (hitVec.z ).toInt)
//
//
//        // val offset = new Vector3d(((hitVec.x*16).toInt/16f),((hitVec.y*16).toInt/16f),((hitVec.z*16).toInt/16f))
//        posSet = posSet.sum((a.x).toInt, (a.y).toInt, (a.z).toInt)
//        //        objectMouseOver.sideHit match {
//        //            case Direction.EAST =>
//        //                side match {
//        //                    case Direction.WEST => offset.add(0, 0, -blockSize.maxZ / 2f toInt)
//        //                    case Direction.NORTH => offset.add(0, 0, -blockSize.maxZ+1)
//        //                    case _ =>
//        //                }
//        //            case Direction.WEST =>
//        //                side match {
//        //                    case Direction.EAST =>
//        //                        posSet.sum(0, 0, -boundingBoxes.maxZ / 2f + 1 toInt)
//        //                    case Direction.NORTH =>
//        //                      // posSet.sum(0, 0, -boundingBoxes.rotate(Direction.NORTH).maxZ + 1)
//        //                    case _ => posSet
//        //                }
//        //            case Direction.SOUTH =>
//        //                side match {
//        //                    case Direction.WEST => posSet.sum(-boundingBoxes.rotate(Direction.WEST).maxX + 1, 0, 0)
//        //                    case Direction.NORTH => posSet.sum(-boundingBoxes.rotate(Direction.NORTH).maxX / 2, 0, 0)
//        //                    case _ => posSet
//        //                }
//        //
//        //            case Direction.NORTH =>
//        //                side match {
//        //                   // case Direction.WEST => posSet.sum(-boundingBoxes.rotate(Direction.WEST).maxX + 1, 0, 0)
//        //                  //  case Direction.SOUTH => posSet.sum(-boundingBoxes.rotate(Direction.SOUTH).maxX / 2, 0, 0)
//        //                    case _ => posSet
//        //                }
//        //            case Direction.UP | Direction.DOWN =>
//        //                side match {
//        //                    case Direction.EAST => posSet.sum(0, 0, Math.floor(-blockSize.rotate(Direction.EAST).maxZ / 2f) + 1 toInt)
//        //                    case Direction.WEST => posSet.sum(-blockSize.rotate(Direction.WEST).maxX + 1, 0, -blockSize.rotate(Direction.WEST).maxZ / 2f toInt)
//        //                    case Direction.SOUTH => posSet.sum(-blockSize.rotate(Direction.SOUTH).maxX / 2, 0, 0)
//        //                    case Direction.NORTH => posSet.sum(Math.floor(-blockSize.rotate(Direction.NORTH).maxX / 2f) + 1 toInt, 0, -blockSize.rotate(Direction.NORTH).maxZ + 1)
//        //                    case _ => posSet
//        //                }
//        //            case _ => posSet
//        //       }
//
//        val ax: Int = offset.x / Chunk.blockSize
//        val ay: Int = offset.y /  Chunk.blockSize
//        val az: Int = offset.z /  Chunk.blockSize
//
//        posSet = posSet.sum(ax, ay, az)
//        offset.sub(ax *  Chunk.blockSize, ay *  Chunk.blockSize, az *  Chunk.blockSize)
//
//        val blockState = new BlockState(this, posSet.x, posSet.y, posSet.z, side, offset.x, offset.y, offset.z)
//        if (worldIn.isAirBlock(blockState)) blockState else null
//    }
}
