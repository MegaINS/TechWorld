package ru.megains.techworld.common.network.packet.handshake.client

import ru.megains.techworld.common.network.ConnectionState
import ru.megains.techworld.common.network.handler.INetHandlerHandshake
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CHandshake() extends Packet[INetHandlerHandshake] {

    var version: Int = 0

    var connectionState: ConnectionState = _

    def this(version: Int, ip: String, port: Int, connectionState: ConnectionState) = {
        this()
        this.version = version
        this.connectionState = connectionState
    }

    override def readPacketData(packetBuffer: PacketBuffer): Unit = {
        version = packetBuffer.readInt

        connectionState = ConnectionState.getFromId(packetBuffer.readByte())
    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {
        packetBuffer.writeInt(version)

        packetBuffer.writeByte(connectionState.id)
    }


    override def isImportant: Boolean = true


    override def processPacket(handler: INetHandlerHandshake): Unit = {
        handler.processHandshake(this)
    }
}
