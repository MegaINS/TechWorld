package ru.megains.techworld.common.world

import ru.megains.techworld.common.block.{BlockContainer, BlockState}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.tileentity.{TileEntity, TileEntityContainer}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Chunk(val pos: ChunkPosition, val world: World) {

    var chunkTileEntityMap = new mutable.HashMap[Long,TileEntity]()
    val entityLists = new ArrayBuffer[Entity]()
    var blockStorage = new BlockStorage(pos)
    var isEmpty: Boolean = true
    var isPopulated = true
    var hasEntities = false

    def getBlock(x: Int, y: Int, z: Int): BlockState = {
        blockStorage.getBlock(x & (Chunk.blockSize - 1), y & (Chunk.blockSize - 1), z & (Chunk.blockSize - 1))
    }

    def getBlockByLocPos(x: Int, y: Int, z: Int): BlockState = {
        blockStorage.getBlock(x, y, z)
    }

    def removeBlock(blockState: BlockState): Unit = {


        val blockStatePrevious =  getBlock(blockState.x,blockState.y,blockState.z)

        blockStorage.removeBlock(blockState)

        blockStatePrevious.block match {
            case _:BlockContainer =>
                world.removeTileEntity(blockState.x,blockState.y,blockState.z)
            case _ =>
        }
    }

    def setBlock(blockState: BlockState): Unit = {
        isEmpty = false
        blockStorage.setBlock(blockState)

        blockState.block match {
            case container:TileEntityContainer =>
                val  tileEntity = container.createNewTileEntity(world,blockState)
                world.setTileEntity(blockState.x,blockState.y,blockState.z, tileEntity)
            case _ =>
        }
    }

    def isAirBlock(blockState: BlockState): Boolean = {
        blockStorage.isAirBlock(blockState: BlockState)
    }

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

    def removeTileEntity(x: Int, y: Int, z: Int): Unit = {
        chunkTileEntityMap -= Chunk.getIndex(x,y,z)
    }

    def addTileEntity(tileEntityIn: TileEntity): Unit = {
        addTileEntity(tileEntityIn.blockState.x,tileEntityIn.blockState.y,tileEntityIn.blockState.z,tileEntityIn)
        world.addTileEntity(tileEntityIn)
    }

    def addTileEntity(x: Int, y: Int, z: Int, tileEntityIn: TileEntity): Unit = {
        getBlock(x, y, z).block match {
            case _:BlockContainer =>
                chunkTileEntityMap += Chunk.getIndex(x,y,z) -> tileEntityIn
            case _ =>
        }
    }
    def getTileEntity(x: Int, y: Int, z: Int): TileEntity = {
        var tileentity = chunkTileEntityMap.get( Chunk.getIndex(x,y,z))
        tileentity.get
    }
}


object Chunk {
    val blockSize: Int = 32

    val offset: Int = log2(blockSize)

    def log2(value: Int, current: Int = 1): Int = {
        if (value > current) log2(value, current * 2) + 1 else 0
    }

    def getIndex(x: Long, y: Long, z: Long): Long = (x & 16777215L) << 40 | (z & 16777215L) << 16 | (y & 65535L)

    def getIndex(pos: ChunkPosition): Long = getIndex(pos.x, pos.y, pos.z)
}