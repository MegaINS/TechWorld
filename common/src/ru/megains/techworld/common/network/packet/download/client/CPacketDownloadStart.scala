package ru.megains.techworld.common.network.packet.download.client

import ru.megains.techworld.common.network.handler.{INetHandlerDownloadServer, INetHandlerLoginServer}
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketDownloadStart extends Packet[INetHandlerDownloadServer] {




    def this(name: String) ={
        this()
    }


    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerDownloadServer): Unit = {
        handler.processDownloadStart(this)
    }
}
