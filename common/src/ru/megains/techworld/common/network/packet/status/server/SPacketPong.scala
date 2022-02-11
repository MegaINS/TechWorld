package ru.megains.techworld.common.network.packet.status.server

import ru.megains.techworld.common.network.handler.INetHandlerStatusClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketPong() extends Packet[INetHandlerStatusClient] {

    private var clientTime: Long = 0L

    override def isImportant: Boolean = true

    def this(clientTimeIn: Long) ={
        this()
        this.clientTime = clientTimeIn
    }


    def readPacketData(buf: PacketBuffer) :Unit ={
        this.clientTime = buf.readLong
    }


    def writePacketData(buf: PacketBuffer) :Unit ={
        buf.writeLong(this.clientTime)
    }


    def processPacket(handler: INetHandlerStatusClient) :Unit ={
        handler.handlePong(this)
    }
}
