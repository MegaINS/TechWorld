package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketWindowItems extends Packet[INetHandlerPlayClient] {


    var windowId: Int = 0
    var itemStacks: Array[ItemStack] = _


    def this(windowIdIn: Int, stacks: Array[ItemStack]) {
        this()
        windowId = windowIdIn
        itemStacks = stacks
    }


    def readPacketData(buf: PacketBuffer): Unit = {
        windowId = buf.readUnsignedByte
        itemStacks = new Array[ItemStack](buf.readShort)
        for (i <- itemStacks.indices) {
            itemStacks(i) = buf.readItemStackFromBuffer
        }
    }


    def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeByte(windowId)
        buf.writeShort(itemStacks.length)
        for (itemStack <- itemStacks) {
            buf.writeItemStackToBuffer(itemStack)
        }
    }

    def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleWindowItems(this)
    }
}
