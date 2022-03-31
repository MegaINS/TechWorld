package ru.megains.techworld.common.network.packet.play.client

import ru.megains.techworld.common.network.handler.INetHandlerPlayServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketHeldItemChange() extends Packet[INetHandlerPlayServer] {
    var slotId: Int = 0

    def this(slotIdIn: Int) {
        this()
        slotId = slotIdIn
    }


    def readPacketData(buf: PacketBuffer): Unit = {
        slotId = buf.readShort
    }


    def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeShort(slotId)
    }

    def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processHeldItemChange(this)
    }
}
