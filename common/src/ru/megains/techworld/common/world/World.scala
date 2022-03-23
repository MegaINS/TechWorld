package ru.megains.techworld.common.world

import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.entity.{Entity, EntityPlayer}

import java.util
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class World() {


    var playerEntities: ArrayBuffer[EntityPlayer] = new ArrayBuffer[EntityPlayer]
    var loadedEntityList: ArrayBuffer[Entity] = new ArrayBuffer[Entity]

    val chunkProvider: IChunkProvider


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
            getChunkFromBlockPos(posX, posY, posZ).addEntity(entity)
            loadedEntityList += entity
            onEntityAdded(entity)
            true
        }
    }

    def onEntityAdded(entity: Entity): Unit

    //    = {
    //        for (var2 <- 0 until this.worldAccesses.size) {
    //            this.worldAccesses.get(var2).asInstanceOf[IWorldAccess].onEntityCreate(p_72923_1_)
    //        }
    //    }
    protected def chunkExists(x: Int, y: Int, z: Int): Boolean = chunkProvider.chunkExists(x, y, z)

    def getEntityByID(id:Int):Entity
}


