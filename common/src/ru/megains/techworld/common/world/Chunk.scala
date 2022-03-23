package ru.megains.techworld.common.world

import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.entity.Entity

import scala.collection.mutable.ArrayBuffer

class Chunk(val pos: ChunkPosition,val world: World) {

    var blockStorage = new BlockStorage(pos)
    var isEmpty: Boolean = true
    var isPopulated = true
    var hasEntities = false

    def getBlock(x: Int, y: Int, z: Int):BlockState = {
        blockStorage.getBlock(x & (Chunk.blockSize-1), y & (Chunk.blockSize-1), z & (Chunk.blockSize-1))
    }

    def getBlockByLocPos(x: Int, y: Int, z: Int):BlockState ={
        blockStorage.getBlock(x, y, z)
    }


    val entityLists = new ArrayBuffer[Entity]()

    def addEntity(entity: Entity): Unit = {
        hasEntities = true
        val posX = Math.floor(entity.posX).toInt >> Chunk.offset
        val posY = Math.floor(entity.posY).toInt >> Chunk.offset
        val posZ = Math.floor(entity.posZ).toInt >> Chunk.offset
        if (posX != pos.x || posY != pos.y || posZ != pos.z) {
            System.out.println("Wrong location! " + entity)
            Thread.dumpStack()
        }

        entity.addedToChunk = true
        entity.chunkCoordX = pos.x
        entity.chunkCoordY = pos.y
        entity.chunkCoordZ = pos.z
        entityLists += entity
    }

}


object Chunk {
    val blockSize: Int = 32

    val offset: Int = log2(blockSize)

    def log2(value: Int, current: Int = 1): Int = {
        if (value > current) log2(value, current * 2) + 1 else 0
    }

    def getIndex(x: Long, y: Long, z: Long): Long = (x & 16777215L) << 40 | (z & 16777215L) << 16 | (y & 65535L)
    def getIndex(pos:ChunkPosition): Long = getIndex(pos.x,pos.y, pos.z)
}