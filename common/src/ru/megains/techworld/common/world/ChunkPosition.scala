package ru.megains.techworld.common.world

case class ChunkPosition(x:Int, y:Int, z:Int) {

    def blockX: Int = x * Chunk.blockSize
    def blockY: Int = y * Chunk.blockSize
    def blockZ: Int = z * Chunk.blockSize

}
