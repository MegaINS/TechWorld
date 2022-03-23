package ru.megains.techworld.server.world

import ru.megains.techworld.common.world.{Chunk, ChunkPosition, IChunkProvider}

import scala.collection.mutable

class ChunkProviderServer(world:WorldServer) extends IChunkProvider {

    ///ChunkProviderServer.voidChunk = ChunkVoid
   // val voidChunk: ChunkVoid.type = ChunkVoid
    val chunkMap = new mutable.HashMap[Long,Chunk]()
    val chunkGenerator: ChunkGenerator = new ChunkGenerator(world)

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }


    def loadChunk(pos:ChunkPosition): Chunk = {
        var chunk: Chunk = getLoadedChunk(pos.x, pos.y, pos.z)
        if (chunk == null) {
           // chunk =  chunkLoader.loadChunk(world, chunkX, chunkY, chunkZ)
            if (chunk != null) {
                chunkMap += Chunk.getIndex(pos.x, pos.y, pos.z) -> chunk
            }
        }
        chunk
    }

    def provideChunk(pos:ChunkPosition): Chunk = {
        var chunk: Chunk = loadChunk(pos)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(pos.x, pos.y, pos.z)
            try {
                chunk = chunkGenerator.provideChunk(pos.x, pos.y, pos.z)
            } catch {
                case throwable: Throwable =>
                    throwable.printStackTrace()
            }
            chunkMap += i -> chunk
        }
        chunk
    }


    def unload(chunk: Chunk): Unit = {

    }

    override def chunkExists(x: Int, y: Int, z: Int): Boolean = {
        chunkMap.contains(Chunk.getIndex(x, y, z))
    }
}
