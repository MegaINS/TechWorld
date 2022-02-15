package ru.megains.techworld.common.network.packet.download.client

import ru.megains.techworld.common.network.handler.INetHandlerDownloadServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketDownloadFinish  extends Packet[INetHandlerDownloadServer] {

    override def isImportant: Boolean = true
    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerDownloadServer): Unit = {
        handler.handleDownloadFinish(this)
    }
}
