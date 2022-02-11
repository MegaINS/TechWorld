package ru.megains.techworld.common.network.packet.status.client

import ru.megains.techworld.common.network.handler.INetHandlerStatusServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketPing() extends Packet[INetHandlerStatusServer] {

    var clientTime: Long = 0L

    override def isImportant: Boolean = true


    def this(clientTimeIn: Long) ={
        this()
        this.clientTime = clientTimeIn
    }

    def readPacketData(buf: PacketBuffer) :Unit ={
        clientTime = buf.readLong
    }


    def writePacketData(buf: PacketBuffer) :Unit ={
        buf.writeLong(clientTime)
    }


    def processPacket(handler: INetHandlerStatusServer) :Unit ={
        handler.processPing(this)
    }


}
