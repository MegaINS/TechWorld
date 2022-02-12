package ru.megains.techworld.common.network.packet.login.client

import ru.megains.techworld.common.network.handler.INetHandlerLoginServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketLoginStart extends Packet[INetHandlerLoginServer] {


    var name: String = ""

    def this(name: String) ={
        this()
        this.name = name
    }


    override def readPacketData(packetBuffer: PacketBuffer): Unit = {
        name = packetBuffer.readStringFromBuffer(255)
    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {
        packetBuffer.writeStringToBuffer(name)
    }

    override def processPacket(handler: INetHandlerLoginServer): Unit = {
        handler.processLoginStart(this)
    }
}
