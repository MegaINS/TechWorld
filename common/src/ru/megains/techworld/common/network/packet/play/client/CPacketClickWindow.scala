package ru.megains.techworld.common.network.packet.play.client

import ru.megains.techworld.common.network.handler.INetHandlerPlayServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketClickWindow extends Packet[INetHandlerPlayServer] {

    var mouseX: Int = -1
    var mouseY: Int = -1
    var button: Int = -1

    def this(mouseXIn: Int, mouseYIn: Int, buttonIn: Int) {
        this()
        mouseX = mouseXIn
        mouseY = mouseYIn
        button = buttonIn
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        mouseX = buf.readInt()
        mouseY = buf.readInt()
        button = buf.readInt()
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(mouseX)
        buf.writeInt(mouseY)
        buf.writeInt(button)
    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processClickWindow(this)
    }
}
