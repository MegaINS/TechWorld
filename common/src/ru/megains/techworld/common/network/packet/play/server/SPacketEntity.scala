package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketEntity extends Packet[INetHandlerPlayClient] {
    var name = ""
    var entityId: Int = 0
    var rotYaw: Int = 0
    var rotPitch: Int = 0
    var moveX: Int = 0
    var moveY: Int = 0
    var moveZ: Int = 0
    var isLook = false

    def this(entityIdIn: Int) {
        this()
        entityId = entityIdIn
    }


    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(entityId)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        entityId = buf.readInt
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleEntityMovement(this)
    }



}

object SPacketEntity{

    class SPacketEntityRelMove extends SPacketEntity {

        def this(entityIdIn: Int, moveXIn: Byte, moveYIn: Byte, moveZIn: Byte) {
            this()
            entityId = entityIdIn
            moveX = moveXIn
            moveY = moveYIn
            moveZ = moveZIn
        }

        override def writePacketData(buf: PacketBuffer): Unit = {
            super.writePacketData(buf)
            buf.writeByte(moveX)
            buf.writeByte(moveY)
            buf.writeByte(moveZ)
        }

        override def readPacketData(buf: PacketBuffer): Unit = {
            super.readPacketData(buf)
            moveX = buf.readByte()
            moveY = buf.readByte()
            moveZ = buf.readByte()
        }

    }

    class SPacketEntityLook extends SPacketEntity {

        isLook = true

        def this(entityIdIn: Int, rotYawIn: Byte, rotPitchIn: Byte) {
            this()
            entityId = entityIdIn
            rotPitch = rotPitchIn
            rotYaw = rotYawIn
        }

        override def writePacketData(buf: PacketBuffer): Unit = {
            super.writePacketData(buf)
            buf.writeByte(rotPitch)
            buf.writeByte(rotYaw)
        }

        override def readPacketData(buf: PacketBuffer): Unit = {
            super.readPacketData(buf)
            rotPitch = buf.readByte()
            rotYaw = buf.readByte()
        }

    }
    class SPacketEntityLookMove extends SPacketEntity {

        isLook = true


        def this(entityIdIn: Int, moveXIn: Byte, moveYIn: Byte, moveZIn: Byte, rotYawIn: Byte, rotPitchIn: Byte) {
            this()
            entityId = entityIdIn
            moveX = moveXIn
            moveY = moveYIn
            moveZ = moveZIn
            rotPitch = rotPitchIn
            rotYaw = rotYawIn
        }

        override def writePacketData(buf: PacketBuffer): Unit = {
            super.writePacketData(buf)
            buf.writeByte(moveX)
            buf.writeByte(moveY)
            buf.writeByte(moveZ)
            buf.writeByte(rotPitch)
            buf.writeByte(rotYaw)
        }

        override def readPacketData(buf: PacketBuffer): Unit = {
            super.readPacketData(buf)
            moveX = buf.readByte()
            moveY = buf.readByte()
            moveZ = buf.readByte()
            rotPitch = buf.readByte()
            rotYaw = buf.readByte()
        }

    }
}

