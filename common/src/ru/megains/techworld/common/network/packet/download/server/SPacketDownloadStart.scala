package ru.megains.techworld.common.network.packet.download.server

import ru.megains.techworld.common.network.handler.{INetHandlerDownloadClient, INetHandlerDownloadServer}
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketDownloadStart extends Packet[INetHandlerDownloadClient] {




    def this(name: String) ={
        this()
    }


    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerDownloadClient): Unit = {
        handler.processDownloadStart(this)
    }
}
