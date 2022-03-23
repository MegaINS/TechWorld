package ru.megains.techworld.server.world

import ru.megains.techworld.common.register.{Blocks, GameRegister}
import ru.megains.techworld.common.world.{BlockStorage, Chunk, ChunkPosition, World}

import scala.util.Random

class ChunkGenerator(world: World) {


    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        val chunk = new Chunk(ChunkPosition(chunkX, chunkY, chunkZ), world)
        val blockStorage: BlockStorage = chunk.blockStorage
        val blockData = blockStorage.blockId

        if (chunkY < 0) {
            chunk.isEmpty = false
            for (i <- blockData.indices) {
               // if(Random.nextBoolean())
                    blockData(i) =  GameRegister.getIdByBlock(Blocks.stone).toShort
            }
        }

        chunk
    }
}
