package ru.megains.techworld.common.network.packet.play.server

import io.netty.buffer.{ByteBufInputStream, ByteBufOutputStream}
import ru.megains.techworld.common.block.{BlockPos, BlockState}
import ru.megains.techworld.common.nbt.NBTData
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}
import ru.megains.techworld.common.register.GameRegister
import ru.megains.techworld.common.tileentity.TileEntity
import ru.megains.techworld.common.world.{BlockStorage, Chunk, ChunkPosition}


class SPacketChunkData extends Packet[INetHandlerPlayClient] {



    var blockStorage: BlockStorage = _
    var isEmpty: Boolean = false
    var position: ChunkPosition =_
    var tileEntityMap:Array[TileEntity] = _

    def this(chunkIn: Chunk) ={
        this()
        blockStorage = chunkIn.blockStorage
        isEmpty = chunkIn.isEmpty
        position = chunkIn.pos
        tileEntityMap = chunkIn.chunkTileEntityMap.values.toArray

    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        position = ChunkPosition(buf.readInt(),buf.readInt(),buf.readInt())

        isEmpty = buf.readBoolean()
        blockStorage = if (isEmpty) new BlockStorage(position) else readChunk(buf)
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(position.x)
        buf.writeInt(position.y)
        buf.writeInt(position.z)
        buf.writeBoolean(isEmpty)
        if (!isEmpty) writeChunk(buf)
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleChunkData(this)
    }

    def readChunk(buf: PacketBuffer): BlockStorage = {
        val blockStorage: BlockStorage = new BlockStorage(position)
        val blocksId = blockStorage.blockId

        for (i <- blocksId.indices) {
            blocksId(i) = buf.readShort()

        }


      //  val sizeBlocks = buf.readInt()


//        for (_ <- 0 until sizeBlocks) {
//            val index = buf.readInt()
//            val blockSell = new BlockCell(position)
//            blockStorage.cellId += index.toShort -> blockSell
//
//            val blocks = buf.readInt()
//            for(_ <- 0 until blocks){
//
//                val id = buf.readInt()
//                val side = buf.readInt()
//                val x = buf.readInt()
//                val y = buf.readInt()
//                val z = buf.readInt()
//                val blockState = new BlockState(Blocks.getBlockById(id),new BlockPos(x,y,z),Direction.DIRECTIONAL_BY_ID(side))
//                //todo val blockState = getBlockState(id,side,x,y,z)
//                blockSell.blocks += blockState
//            }
//
//        }

        val sizeTileEntity = buf.readInt()
        tileEntityMap = new Array[TileEntity](sizeTileEntity)

        for (i <- tileEntityMap.indices) {
            val id = buf.readInt()
            val blockState = buf.readBlockState()
            val tileEntityClass = GameRegister.getTileEntityById(id)
            if(tileEntityClass != null){
                val tileEntity:TileEntity = tileEntityClass.getConstructor(classOf[BlockState]).newInstance(blockState)
                val nbt = NBTData.createCompound()
                nbt.read(new ByteBufInputStream(buf))
                tileEntity.readFromNBT(nbt)
                tileEntityMap(i) = tileEntity
            }else{
                println(s"error load tileEntity $id")
            }


        }



        blockStorage
    }

    def writeChunk(buf: PacketBuffer): Unit = {

        val blocksId = blockStorage.blockId
        for (i <- blocksId.indices) {
            buf.writeShort(blocksId(i))

        }


       // val containers = blockStorage.cellId

      //  buf.writeInt(containers.size)

//        containers.foreach {
//            case (index, blockSell) =>
//
//                buf.writeInt(index)
//
//                buf.writeInt(blockSell.block.size)
//
//                blockSell.blocks.foreach {
//                    blockState =>
//                        buf.writeInt(Blocks.getIdByBlock(blockState.block))
//                        buf.writeInt(blockState.blockDirection.id)
//                        buf.writeInt(blockState.pos.x)
//                        buf.writeInt( blockState.pos.y)
//                        buf.writeInt(blockState.pos.z)
//                }
//        }

        buf.writeInt(tileEntityMap.length)

        for (tileEntity <- tileEntityMap) {

            buf.writeInt(GameRegister.getIdByTileEntity(tileEntity.getClass ))
            buf.writeBlockState(tileEntity.blockState)
            val nbt = NBTData.createCompound()
            tileEntity.writeToNBT(nbt)
            nbt.write(new ByteBufOutputStream(buf))

        }

    }

}
