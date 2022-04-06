package ru.megains.techworld.common.world

import org.joml.{Vector3d, Vector3i}
import ru.megains.techworld.common.block.{BlockAir, BlockPos, BlockState}
import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.physics.BoundingBox
import ru.megains.techworld.common.register.Blocks
import ru.megains.techworld.common.tileentity.TileEntity
import ru.megains.techworld.common.utils.{Direction, RayTraceResult}

import java.util
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class World() {

    val tickableTileEntities: ArrayBuffer[TileEntity] = new ArrayBuffer[TileEntity]()
    var playerEntities: ArrayBuffer[EntityPlayer] = new ArrayBuffer[EntityPlayer]
    var loadedEntityList: ArrayBuffer[Entity] = new ArrayBuffer[Entity]
    val eventListeners: ArrayBuffer[IWorldEventListener] = ArrayBuffer[IWorldEventListener]()
    val chunkProvider: IChunkProvider


    def update(): Unit = {
        loadedEntityList.foreach(_.update())
        tickableTileEntities.foreach(_.update(this))
    }

    def getChunkFromBlockPos(x: Int, y: Int, z: Int): Chunk = {
        getChunk(ChunkPosition(x >> Chunk.offset, y >> Chunk.offset, z >> Chunk.offset))
    }

    def getChunkFromChunkPos(x: Int, y: Int, z: Int): Chunk = {
        getChunk(ChunkPosition(x, y, z))
    }

    def getBlock(x: Int, y: Int, z: Int): BlockState = {
        getChunkFromBlockPos(x, y, z).getBlock(x, y, z)
    }

    def getChunk(pos: ChunkPosition): Chunk = {
        chunkProvider.provideChunk(pos)
    }

    //    def setAirBlock(pos: BlockPos):Unit ={
    //        re
    //        setBlock(new BlockState(Blocks.air,pos.x,pos.y,pos.z))
    //    }
    def getTileEntity(x: Int, y: Int, z: Int): TileEntity ={
        //if (!blockPos.isValid(this)){
        //   null
        //} else {
        getChunkFromBlockPos(x,y,z).getTileEntity(x,y,z)
        // }
    }
    def setTileEntity(x: Int, y: Int, z: Int, tileEntityIn: TileEntity): Unit = {

        val chunk = getChunkFromBlockPos(x,y,z)
        if (chunk != null) chunk.addTileEntity(x,y,z, tileEntityIn)
        addTileEntity(tileEntityIn)

    }
    def addTileEntity(tile: TileEntity): Unit = {
        tickableTileEntities += tile
    }
    def removeTileEntity(x: Int, y: Int, z: Int): Unit = {


        val chunk = getChunkFromBlockPos(x,y,z)
        if (chunk != null) {
            tickableTileEntities -= chunk.getTileEntity(x,y,z)
            chunk.removeTileEntity(x,y,z)

        }

    }
    def setBlock(blockState: BlockState): Unit = {


        blockState match {
            case state: BlockState =>
                val minX: Int = state.x + state.getSelectedBlockSize.minX / Chunk.blockSize >> Chunk.offset
                val minY: Int = state.y + state.getSelectedBlockSize.minY / Chunk.blockSize >> Chunk.offset
                val minZ: Int = state.z + state.getSelectedBlockSize.minZ / Chunk.blockSize >> Chunk.offset
                val maxX: Int = state.x + state.getSelectedBlockSize.maxX / Chunk.blockSize >> Chunk.offset
                val maxY: Int = state.y + state.getSelectedBlockSize.maxY / Chunk.blockSize >> Chunk.offset
                val maxZ: Int = state.z + state.getSelectedBlockSize.maxZ / Chunk.blockSize >> Chunk.offset

                for (chunkX <- minX to maxX;
                     chunkY <- minY to maxY;
                     chunkZ <- minZ to maxZ) {
                    val chunk = getChunkFromChunkPos(chunkX, chunkY, chunkZ)
                    chunk.setBlock(blockState)
                    markAndNotifyBlock(blockState, chunk)
                }

            case _ =>
                getChunkFromChunkPos(blockState.x >> Chunk.offset, blockState.y >> Chunk.offset, blockState.z >> Chunk.offset).setBlock(blockState)
        }


    }

    def removeBlock(blockState: BlockState): Unit = {
        blockState match {
            case state: BlockState =>
                val minX: Int = state.x + state.getSelectedBlockSize.minX / Chunk.blockSize >> Chunk.offset
                val minY: Int = state.y + state.getSelectedBlockSize.minY / Chunk.blockSize >> Chunk.offset
                val minZ: Int = state.z + state.getSelectedBlockSize.minZ / Chunk.blockSize >> Chunk.offset
                val maxX: Int = state.x + state.getSelectedBlockSize.maxX / Chunk.blockSize >> Chunk.offset
                val maxY: Int = state.y + state.getSelectedBlockSize.maxY / Chunk.blockSize >> Chunk.offset
                val maxZ: Int = state.z + state.getSelectedBlockSize.maxZ / Chunk.blockSize >> Chunk.offset

                for (chunkX <- minX to maxX;
                     chunkY <- minY to maxY;
                     chunkZ <- minZ to maxZ) {
                    val chunk = getChunkFromChunkPos(chunkX, chunkY, chunkZ)
                    chunk.removeBlock(blockState)
                    markAndNotifyBlock(blockState, chunk)
                }

            case _ =>
                getChunkFromChunkPos(blockState.x >> Chunk.offset, blockState.y >> Chunk.offset, blockState.z >> Chunk.offset).removeBlock(blockState)
        }

    }
    def markAndNotifyBlock(pos: BlockState, chunk: Chunk): Unit = {

        if (chunk == null || chunk.isPopulated) notifyBlockUpdate(pos)
        /*
                        if (!isRemote && (flags & 1) != 0) {
                            notifyNeighborsRespectDebug(pos, iblockstate.getBlock)
                          //  if (newState.hasComparatorInputOverride) this.updateComparatorOutputLevel(pos, newState.getBlock)
                        }
        */
    }

    def notifyBlockUpdate(pos: BlockState): Unit = {

        for (i <- eventListeners.indices) {
            eventListeners(i).notifyBlockUpdate(this, pos)
        }

    }
    def addEventListener(listener: IWorldEventListener): Unit = {
        eventListeners += listener
    }
    def isAirBlock(blockState: BlockState): Boolean = {

        blockState match {
            case state: BlockState =>
                val minX: Int = state.x + state.getSelectedBlockSize.minX / Chunk.blockSize >> Chunk.offset
                val minY: Int = state.y + state.getSelectedBlockSize.minY / Chunk.blockSize >> Chunk.offset
                val minZ: Int = state.z + state.getSelectedBlockSize.minZ / Chunk.blockSize >> Chunk.offset
                val maxX: Int = state.x + state.getSelectedBlockSize.maxX / Chunk.blockSize >> Chunk.offset
                val maxY: Int = state.y + state.getSelectedBlockSize.maxY / Chunk.blockSize >> Chunk.offset
                val maxZ: Int = state.z + state.getSelectedBlockSize.maxZ / Chunk.blockSize >> Chunk.offset

                for (chunkX <- minX to maxX;
                     chunkY <- minY to maxY;
                     chunkZ <- minZ to maxZ) {
                    if (!getChunkFromChunkPos(chunkX, chunkY, chunkZ).isAirBlock(blockState)) {
                        return false
                    }
                }
                true
            case _ =>
                getBlock(blockState.x, blockState.y, blockState.z).block == BlockAir
        }
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
            getChunkFromChunkPos(posX, posY, posZ).addEntity(entity)
            loadedEntityList += entity
            onEntityAdded(entity)
            true
        }
    }

    def removeEntity(entity: Entity): Unit = {

        entity.setDead()
        entity match {
            case entityPlayer: EntityPlayer =>
                playerEntities -= entityPlayer
                // updateAllPlayersSleepingFlag()
                onEntityRemoved(entity)
            case _ =>
        }
    }

    def onEntityRemoved(entity: Entity): Unit


    def onEntityAdded(entity: Entity): Unit

    //    = {
    //        for (var2 <- 0 until this.worldAccesses.size) {
    //            this.worldAccesses.get(var2).asInstanceOf[IWorldAccess].onEntityCreate(p_72923_1_)
    //        }
    //    }
    protected def chunkExists(x: Int, y: Int, z: Int): Boolean = chunkProvider.chunkExists(x, y, z)

    def getEntityByID(id: Int): Entity

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


    def rayTraceBlocks(posIn: Vector3d, lockVec: Vector3d): RayTraceResult = {

        var result: RayTraceResult = RayTraceResult.VOID
        var direction: Direction = Direction.NONE
        val posStart: Vector3d = posIn
        val posEnd2: Vector3d = new Vector3d(posIn).add(lockVec)

        val posEnd: Vector3i = new Vector3i(Math.floor(posStart.x + lockVec.x).toInt, Math.floor(posStart.y + lockVec.y).toInt, Math.floor(posStart.z + lockVec.z).toInt)
        val posCheck: Vector3i = new Vector3i(Math.floor(posStart.x).toInt, Math.floor(posStart.y).toInt, Math.floor(posStart.z).toInt)

        var steps = 200

        while (posEnd != posCheck && result == RayTraceResult.VOID && steps != 0) {
            steps -= 1
            var dnX = 1.1D
            var dnY = 1.1D
            var dnZ = 1.1D

            if (lockVec.x > 0) {
                dnX = posCheck.x - posStart.x + 1
            } else if (lockVec.x < 0) {
                dnX = posCheck.x - posStart.x
            }
            if (lockVec.y > 0) {
                dnY = posCheck.y - posStart.y + 1
            } else if (lockVec.y < 0) {
                dnY = posCheck.y - posStart.y
            }
            if (lockVec.z > 0) {
                dnZ = posCheck.z - posStart.z + 1
            } else if (lockVec.z < 0) {
                dnZ = posCheck.z - posStart.z
            }

            var sX: Double = dnX / lockVec.x
            var sY: Double = dnY / lockVec.y
            var sZ: Double = dnZ / lockVec.z

            if (sX == -0.0D) {
                sX = -1.0E-4f
            }
            if (sY == -0.0D) {
                sY = -1.0E-4f
            }
            if (sZ == -0.0D) {
                sZ = -1.0E-4f
            }


            if (sX < sY && sX < sZ) {
                direction = if (lockVec.x > 0) Direction.WEST else Direction.EAST
                posStart.set(posStart.x + dnX.toFloat, (posStart.y + lockVec.y * sX).toFloat, (posStart.z + lockVec.z * sX).toFloat)
            } else if (sY < sZ) {
                direction = if (lockVec.y > 0) Direction.DOWN else Direction.UP
                posStart.set((posStart.x + lockVec.x * sY).toFloat, (posStart.y + dnY).toFloat, (posStart.z + lockVec.z * sY).toFloat)
            } else {
                direction = if (lockVec.z > 0) Direction.NORTH else Direction.SOUTH
                posStart.set((posStart.x + lockVec.x * sZ).toFloat, (posStart.y + lockVec.y * sZ).toFloat, (posStart.z + dnZ).toFloat)
            }
            posCheck.set(Math.floor(posStart.x).toInt, Math.floor(posStart.y).toInt, Math.floor(posStart.z).toInt)

            direction match {
                case Direction.EAST | Direction.UP | Direction.SOUTH => posCheck.add(-direction.x, -direction.y, -direction.z)
                case _ =>
            }


            val chunk = getChunkFromChunkPos(posCheck.x >> Chunk.offset, posCheck.y >> Chunk.offset, posCheck.z >> Chunk.offset)


            if (chunk != null) {
                val blockState = chunk.getBlock(posCheck.x, posCheck.y, posCheck.z)
                blockState.block match {
                    case BlockAir =>
                    case _ => result = blockState.collisionRayTrace(posStart, posEnd2)
                }
            }
        }
        result
    }


}


