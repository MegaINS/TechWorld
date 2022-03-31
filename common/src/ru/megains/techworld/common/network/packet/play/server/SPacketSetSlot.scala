package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}


class SPacketSetSlot extends Packet[INetHandlerPlayClient] {

    var windowId: Int = 0
    var slot: Int = 0
    var item: ItemStack = _


    def this(windowIdIn: Int, slotIn: Int, itemIn: ItemStack) {
        this()
        windowId = windowIdIn
        slot = slotIn
        item = if (itemIn == null) null else itemIn
    }


    def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleSetSlot(this)
    }


    def readPacketData(buf: PacketBuffer): Unit = {
        windowId = buf.readByte
        slot = buf.readShort
        item = buf.readItemStackFromBuffer
    }


    def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeByte(windowId)
        buf.writeShort(slot)
        buf.writeItemStackToBuffer(item)
    }
}
