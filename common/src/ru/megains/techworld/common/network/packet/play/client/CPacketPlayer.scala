package ru.megains.techworld.common.network.packet.play.client

import ru.megains.techworld.common.network.handler.INetHandlerPlayServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}


class CPacketPlayer extends Packet[INetHandlerPlayServer] {


    var x: Float = .0f
    var y: Float = .0f
    var z: Float = .0f
    var yaw: Float = .0f
    var pitch: Float = .0f
    var onGround: Boolean = false
    var moving: Boolean = false
    var rotating: Boolean = false

    def this(onGroundIn: Boolean) {
        this()
        onGround = onGroundIn
    }


    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processPlayer(this)
    }


    def getX(posX: Float): Float = if (moving) x else posX

    def getY(posY: Float): Float = if (moving) y else posY

    def getZ(posZ: Float): Float = if (moving) z else posZ

    def getYaw(rotationYaw: Float): Float = if (rotating) yaw else rotationYaw

    def getPitch(rotationPitch: Float): Float = if (rotating) pitch else rotationPitch

}

object CPacketPlayer {

    class Position() extends CPacketPlayer {

        moving = true

        def this(xIn: Float, yIn: Float, zIn: Float, onGroundIn: Boolean) {
            this()
            x = xIn
            y = yIn
            z = zIn
            onGround = onGroundIn
            moving = true
        }


        override def readPacketData(buf: PacketBuffer): Unit = {
            x = buf.readFloat
            y = buf.readFloat
            z = buf.readFloat
            super.readPacketData(buf)
        }


        override def writePacketData(buf: PacketBuffer): Unit = {
            buf.writeFloat(x)
            buf.writeFloat(y)
            buf.writeFloat(z)
            super.writePacketData(buf)
        }
    }


    class PositionRotation() extends CPacketPlayer {
        moving = true
        rotating = true

        def this(xIn: Float, yIn: Float, zIn: Float, yawIn: Float, pitchIn: Float, onGroundIn: Boolean) {
            this()
            x = xIn
            y = yIn
            z = zIn
            yaw = yawIn
            pitch = pitchIn
            onGround = onGroundIn
            rotating = true
            moving = true
        }


        override def readPacketData(buf: PacketBuffer): Unit = {
            x = buf.readFloat
            y = buf.readFloat
            z = buf.readFloat
            yaw = buf.readFloat
            pitch = buf.readFloat
            super.readPacketData(buf)
        }


        override def writePacketData(buf: PacketBuffer): Unit = {
            buf.writeFloat(x)
            buf.writeFloat(y)
            buf.writeFloat(z)
            buf.writeFloat(yaw)
            buf.writeFloat(pitch)
            super.writePacketData(buf)
        }
    }

    class Rotation() extends CPacketPlayer {
        rotating = true

        def this(yawIn: Float, pitchIn: Float, onGroundIn: Boolean) {
            this()
            yaw = yawIn
            pitch = pitchIn
            onGround = onGroundIn
            rotating = true
        }


        override def readPacketData(buf: PacketBuffer): Unit = {
            yaw = buf.readFloat
            pitch = buf.readFloat
            super.readPacketData(buf)
        }

        override def writePacketData(buf: PacketBuffer): Unit = {
            buf.writeFloat(yaw)
            buf.writeFloat(pitch)
            super.writePacketData(buf)
        }
    }

}



