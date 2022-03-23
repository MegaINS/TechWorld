package ru.megains.techworld.common.world

trait IChunkProvider {

    def chunkExists(x: Int, y: Int, z: Int): Boolean

    def loadChunk(pos:ChunkPosition): Chunk

    def provideChunk(pos:ChunkPosition): Chunk
}
