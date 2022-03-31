package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.block.{BlockPos, BlockState}
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}
import ru.megains.techworld.common.world.Chunk

import scala.collection.mutable.ArrayBuffer


class SPacketMultiBlockChange() extends Packet[INetHandlerPlayClient] {


    var changedBlocks: Array[BlockState] = _

    def this(changedBlocksIn: ArrayBuffer[BlockPos], chunk: Chunk) {
        this()
        changedBlocks = new Array[BlockState](changedBlocksIn.length)
        for (i <- changedBlocksIn.indices) {
            changedBlocks(i) = chunk.getBlock(changedBlocksIn(i).x,changedBlocksIn(i).y,changedBlocksIn(i).z)
        }

    }


    def readPacketData(buf: PacketBuffer): Unit = {

        changedBlocks = new Array[BlockState](buf.readInt)
        for (i <- changedBlocks.indices) {
            changedBlocks(i) = buf.readBlockState()
        }
    }


    def writePacketData(buf: PacketBuffer): Unit = {

        buf.writeInt(changedBlocks.length)
        for (blockState <- changedBlocks) {
            buf.writeBlockState(blockState)
        }
    }


    def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleMultiBlockChange(this)
    }

}
