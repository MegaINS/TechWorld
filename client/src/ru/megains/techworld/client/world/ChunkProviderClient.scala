package ru.megains.techworld.client.world

import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.{Chunk, ChunkPosition, ChunkVoid, IChunkProvider, World}

import scala.collection.mutable


class ChunkProviderClient(world: World) extends IChunkProvider with Logger{



    val voidChunk: ChunkVoid.type = ChunkVoid
    val chunkMap = new mutable.HashMap[Long,Chunk]()

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }

//    override def unload(chunk: Chunk): Unit = {
//
//    }

    def loadChunk(pos:ChunkPosition): Chunk = {
        val key = Chunk.getIndex(pos.x, pos.y, pos.z)
        if (chunkMap.contains(key)) chunkMap(key)
        else {
            val chunk: Chunk = new Chunk(pos,world)
            chunkMap += key -> chunk
            chunk
        }
    }

    def unloadChunk(pos: ChunkPosition): Unit = {
        val chunk:Chunk = provideChunk(pos)

       // if (!chunk.isEmpty) chunk.onChunkUnload()

        this.chunkMap.remove(Chunk.getIndex(pos))
        //this.chunkListing.remove(var3)
    }

    def provideChunk(pos:ChunkPosition): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(pos.x, pos.y, pos.z), voidChunk)
    }

    def saveChunks(p_186027_1: Boolean): Boolean = {
        true
    }

    override def chunkExists(x: Int, y: Int, z: Int): Boolean = {true}
}
