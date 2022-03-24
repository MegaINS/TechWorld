package ru.megains.techworld.common.world

import ru.megains.techworld.common.block.{BlockAir, BlockState}
import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.physics.BoundingBox

import java.util
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class World() {


    var playerEntities: ArrayBuffer[EntityPlayer] = new ArrayBuffer[EntityPlayer]
    var loadedEntityList: ArrayBuffer[Entity] = new ArrayBuffer[Entity]

    val chunkProvider: IChunkProvider



    def update(): Unit ={
        loadedEntityList.foreach(_.update())
    }

    def getChunkFromBlockPos(x: Int, y: Int, z: Int): Chunk = {
        getChunk(ChunkPosition(x >> Chunk.offset, y >> Chunk.offset, z >> Chunk.offset))
    }

    def getChunkFromChunkCoords(x: Int, y: Int, z: Int): Chunk = {
        getChunk(ChunkPosition(x, y, z))
    }

    def getBlock(x: Int, y: Int, z: Int): BlockState = {
        getChunkFromBlockPos(x, y, z).getBlock(x, y, z)
    }

    def getChunk(pos: ChunkPosition): Chunk = {
        chunkProvider.provideChunk(pos)
    }


    def spawnEntityInWorld(entity: Entity): Boolean = {
        val posX = Math.floor(entity.posX).toInt >> Chunk.offset
        val posY = Math.floor(entity.posY).toInt >> Chunk.offset
        val posZ = Math.floor(entity.posZ).toInt >> Chunk.offset
        var forceSpawn = false // entity.forceSpawn
        if (entity.isInstanceOf[EntityPlayer]) forceSpawn = true
        if (!forceSpawn && !chunkExists(posX, posY, posZ)) false
        else {
            entity match {
                case var5: EntityPlayer =>
                    playerEntities += var5
                // updateAllPlayersSleepingFlag()
                case _ =>
            }
            getChunkFromChunkCoords(posX, posY, posZ).addEntity(entity)
            loadedEntityList += entity
            onEntityAdded(entity)
            true
        }
    }

    def removeEntity(entity:Entity): Unit = {

        entity.setDead()
        entity match {
            case entityPlayer: EntityPlayer =>
                playerEntities -= entityPlayer
                // updateAllPlayersSleepingFlag()
                onEntityRemoved(entity)
            case _ =>
        }
    }

    def onEntityRemoved(entity:Entity): Unit


    def onEntityAdded(entity: Entity): Unit

    //    = {
    //        for (var2 <- 0 until this.worldAccesses.size) {
    //            this.worldAccesses.get(var2).asInstanceOf[IWorldAccess].onEntityCreate(p_72923_1_)
    //        }
    //    }
    protected def chunkExists(x: Int, y: Int, z: Int): Boolean = chunkProvider.chunkExists(x, y, z)

    def getEntityByID(id:Int):Entity

    def addBlocksInList(aabb: BoundingBox): mutable.HashSet[BoundingBox] = {
        var x0: Int = Math.floor(aabb.minX).toInt
        var y0: Int = Math.floor(aabb.minY).toInt
        var z0: Int = Math.floor(aabb.minZ).toInt
        var x1: Int = Math.ceil(aabb.maxX).toInt
        var y1: Int = Math.ceil(aabb.maxY).toInt
        var z1: Int = Math.ceil(aabb.maxZ).toInt


//        if (x0 < -length *  Chunk.blockSize) {
//            x0 = -length * Chunk.blockSize
//        }
//        if (y0 < -height * Chunk.blockSize) {
//            y0 = -height * Chunk.blockSize
//        }
//        if (z0 < -width * Chunk.blockSize) {
//            z0 = -width * Chunk.blockSize
//        }
//        if (x1 > length * Chunk.blockSize) {
//            x1 = length * Chunk.blockSize
//        }
//        if (y1 > height * Chunk.blockSize) {
//            y1 = height * Chunk.blockSize
//        }
//        if (z1 > width * Chunk.blockSize) {
//            z1 = width * Chunk.blockSize
//        }

        val aabbs = mutable.HashSet[BoundingBox]()

        for (x <- x0 to x1; y <- y0 to y1; z <- z0 to z1) {


            getBlock(x, y, z) match {
                case blockState: BlockState if (blockState.block == BlockAir) =>
                case blockState =>
                    aabbs ++= blockState.getSelectedBlockBody
            }
            //            block match {
            //                case state: BlockCellState =>
            //                    aabbs ++= state.block.asInstanceOf[BlockCell].getBlocksState.map(_.getSelectedBlockBody)
            //                case _ =>
            //                    if (!block.isAirBlock) {
            //                        aabbs += block.getSelectedBlockBody
            //                    }
            //
            //            }
        }
        aabbs
    }
}


