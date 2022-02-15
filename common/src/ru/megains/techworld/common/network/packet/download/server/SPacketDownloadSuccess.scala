package ru.megains.techworld.common.network.packet.download.server

import ru.megains.techworld.common.network.handler.{INetHandlerDownloadClient, INetHandlerDownloadServer, INetHandlerLoginClient}
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketDownloadSuccess extends Packet[INetHandlerDownloadClient] {


    override def isImportant: Boolean = true
    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerDownloadClient): Unit = {
        handler.handleDownloadSuccess(this)
    }
}
