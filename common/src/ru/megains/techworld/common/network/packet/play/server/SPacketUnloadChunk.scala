package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}
import ru.megains.techworld.common.world.ChunkPosition


class SPacketUnloadChunk extends Packet[INetHandlerPlayClient] {


    var position: ChunkPosition =_
    def this(positionIn: ChunkPosition) ={
        this()
        position = positionIn
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        position = ChunkPosition(buf.readInt(),buf.readInt(),buf.readInt())
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(position.x)
        buf.writeInt(position.y)
        buf.writeInt(position.z)
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleUnloadChunk(this)
    }
}
