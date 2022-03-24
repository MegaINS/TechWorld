package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketDestroyEntities extends Packet[INetHandlerPlayClient] {

    var entitiesId: Array[Int] = _


    def this(entitiesIdIn: Array[Int]) {
        this()
        entitiesId = entitiesIdIn
    }


    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeByte(entitiesId.length)
        for (i <- entitiesId.indices) {
            buf.writeInt(entitiesId(i))
        }
    }

    override def readPacketData(buf: PacketBuffer): Unit = {

        entitiesId = new Array[Int](buf.readByte())
        for (i <- entitiesId.indices) {
            entitiesId(i) = buf.readInt()
        }
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleDestroyEntities(this)
    }
}
