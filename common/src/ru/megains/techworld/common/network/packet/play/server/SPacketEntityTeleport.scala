package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketEntityTeleport extends Packet[INetHandlerPlayClient]{

    var entityId: Int = 0
    var rotation: Byte = 0
    var pitch: Byte = 0
    var posX: Int = 0
    var posY: Int = 0
    var posZ: Int = 0

    def this(entityIdIn: Int, posXIn: Int, posYIn: Int, posZIn: Int, rotateIn: Byte, pitchIn: Byte) {
        this()
        entityId = entityIdIn
        posX = posXIn
        posY = posYIn
        posZ = posZIn
        pitch = pitchIn
        rotation = rotateIn
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(entityId)
        buf.writeInt(posX)
        buf.writeInt(posY)
        buf.writeInt(posZ)
        buf.writeByte(pitch)
        buf.writeByte(rotation)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        entityId = buf.readInt()
        posX = buf.readInt()
        posY = buf.readInt()
        posZ = buf.readInt()
        pitch = buf.readByte()
        rotation = buf.readByte()
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleEntityTeleport(this)
    }
}
