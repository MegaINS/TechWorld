package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

import scala.collection.immutable.HashSet

class SPacketPlayerPosLook extends Packet[INetHandlerPlayClient] {


    var x: Float = 0

    var y: Float = 0

    var z: Float = 0

    var yaw: Float = 0

    var pitch: Float = 0


    def this(xIn: Float, yIn: Float, zIn: Float, yawIn: Float, pitchIn: Float) ={
        this()
        x = xIn
        y = yIn
        z = zIn
        yaw = yawIn
        pitch = pitchIn

    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        x = buf.readFloat
        y = buf.readFloat
        z = buf.readFloat
        yaw = buf.readFloat
        pitch = buf.readFloat
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeFloat(x)
        buf.writeFloat(y)
        buf.writeFloat(z)
        buf.writeFloat(yaw)
        buf.writeFloat(pitch)
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handlePlayerPosLook(this)
    }
}
