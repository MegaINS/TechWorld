package ru.megains.techworld.common.world

import ru.megains.techworld.common.block.{Block, BlockAir, BlockCell, BlockState, MiniBlock}
import ru.megains.techworld.common.register.GameRegister
import ru.megains.techworld.common.utils.Logger

import scala.collection.mutable

class BlockStorage(pos: ChunkPosition) extends Logger {


    lazy val blockId = new Array[Short](Chunk.blockSize *  Chunk.blockSize*Chunk.blockSize)
    val cellId = new mutable.HashMap[Int, BlockState]()
    val AIR: Short = GameRegister.getIdByBlock(BlockAir).toShort
    val CELL: Short = -1

    def isAirBlock(blockStateIn: BlockState): Boolean = {

        val minX: Int = Math.max(blockStateIn.x + blockStateIn.getSelectedBlockSize.minX / Chunk.blockSize - pos.blockX, 0)
        val minY: Int = Math.max(blockStateIn.y + blockStateIn.getSelectedBlockSize.minY / Chunk.blockSize - pos.blockY, 0)
        val minZ: Int = Math.max(blockStateIn.z + blockStateIn.getSelectedBlockSize.minZ / Chunk.blockSize - pos.blockZ, 0)
        val maxX: Int = Math.min(blockStateIn.x + (blockStateIn.getSelectedBlockSize.maxX - 1) / Chunk.blockSize - pos.blockX, Chunk.blockSize-1)
        val maxY: Int = Math.min(blockStateIn.y + (blockStateIn.getSelectedBlockSize.maxY - 1) / Chunk.blockSize - pos.blockY, Chunk.blockSize-1)
        val maxZ: Int = Math.min(blockStateIn.z + (blockStateIn.getSelectedBlockSize.maxZ - 1) / Chunk.blockSize - pos.blockZ, Chunk.blockSize-1)

        for (blockX <- minX to maxX;
             blockY <- minY to maxY;
             blockZ <- minZ to maxZ) {
            val blockState: BlockState = getBlock(blockX, blockY, blockZ)
            if (!blockState.isAirBlock(blockStateIn: BlockState)) {
                return false
            }
        }
        true


    }

    def getBlock(x: Int, y: Int, z: Int): BlockState = {
        val index = getIndex(x, y, z)

        blockId(index) match {
            case AIR => new BlockState(BlockAir, x, y, z)
            case CELL => cellId(index)
            case _ => new BlockState(GameRegister.getBlockById(blockId(index)), pos.blockX + x, pos.blockY + y, pos.blockZ + z)
        }


    }

    def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {


        val index = getIndex(x, y, z)
        blockId(index) = GameRegister.getIdByBlock(block).toShort
    }

    def setBlock(blockStateIn: BlockState): Unit = {

        val minX: Int = Math.max(blockStateIn.x + blockStateIn.getSelectedBlockSize.minX / Chunk.blockSize - pos.blockX, 0)
        val minY: Int = Math.max(blockStateIn.y + blockStateIn.getSelectedBlockSize.minY / Chunk.blockSize - pos.blockY, 0)
        val minZ: Int = Math.max(blockStateIn.z + blockStateIn.getSelectedBlockSize.minZ / Chunk.blockSize - pos.blockZ, 0)
        val maxX: Int = Math.min(blockStateIn.x + (blockStateIn.getSelectedBlockSize.maxX - 1) / Chunk.blockSize - pos.blockX, Chunk.blockSize-1)
        val maxY: Int = Math.min(blockStateIn.y + (blockStateIn.getSelectedBlockSize.maxY - 1) / Chunk.blockSize - pos.blockY, Chunk.blockSize-1)
        val maxZ: Int = Math.min(blockStateIn.z + (blockStateIn.getSelectedBlockSize.maxZ - 1) / Chunk.blockSize - pos.blockZ, Chunk.blockSize-1)

        for (blockX <- minX to maxX;
             blockY <- minY to maxY;
             blockZ <- minZ to maxZ) {
            val nextBlockState: BlockState = getBlock(blockX, blockY, blockZ)
            val index = getIndex(blockX, blockY, blockZ)
            nextBlockState.block match {
                case BlockAir =>
                    blockStateIn.block match {
                        case BlockAir =>

                        case _: MiniBlock =>
                            val cellNew: BlockState = new BlockState(new BlockCell(pos.blockX + blockX, pos.blockY + blockY, pos.blockZ + blockZ), pos.blockX + blockX, pos.blockY + blockY, pos.blockZ + blockZ)
                            cellNew.setBlock(blockStateIn)
                            cellId(index) = cellNew
                            blockId(index) = -1
                        case _ =>
                            blockId(index) = GameRegister.getIdByBlock(blockStateIn.block).toShort
                    }
                case _: BlockCell =>
                    nextBlockState.setBlock(blockStateIn)
                case _ =>





            }

        }
    }

    def removeBlock(blockStateIn: BlockState): Unit = {

        val minX: Int = Math.max(blockStateIn.x + blockStateIn.getSelectedBlockSize.minX / Chunk.blockSize - pos.blockX, 0)
        val minY: Int = Math.max(blockStateIn.y + blockStateIn.getSelectedBlockSize.minY / Chunk.blockSize - pos.blockY, 0)
        val minZ: Int = Math.max(blockStateIn.z + blockStateIn.getSelectedBlockSize.minZ / Chunk.blockSize - pos.blockZ, 0)
        val maxX: Int = Math.min(blockStateIn.x + (blockStateIn.getSelectedBlockSize.maxX - 1) / Chunk.blockSize - pos.blockX, Chunk.blockSize-1)
        val maxY: Int = Math.min(blockStateIn.y + (blockStateIn.getSelectedBlockSize.maxY - 1) / Chunk.blockSize - pos.blockY, Chunk.blockSize-1)
        val maxZ: Int = Math.min(blockStateIn.z + (blockStateIn.getSelectedBlockSize.maxZ - 1) / Chunk.blockSize - pos.blockZ, Chunk.blockSize-1)

        for (blockX <- minX to maxX;
             blockY <- minY to maxY;
             blockZ <- minZ to maxZ) {
            val nextBlockState: BlockState = getBlock(blockX, blockY, blockZ)
            val index = getIndex(blockX, blockY, blockZ)
            nextBlockState.block match {
                case BlockAir =>

                case blockCell: BlockCell =>
                    blockCell.removeBlock(blockStateIn)
                    if (blockCell.isEmpty) {
                        blockId(index) = AIR
                        cellId -= index
                    }

                case _ =>
                    blockId(index) = AIR
            }
        }
    }





    def getIndex(x: Int, y: Int, z: Int): Short = (x << (Chunk.offset*2) | y << Chunk.offset | z).toShort
}
