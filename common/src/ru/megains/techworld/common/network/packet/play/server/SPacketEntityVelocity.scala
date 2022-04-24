package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketEntityVelocity extends Packet[INetHandlerPlayClient] {

    var entityId: Int = 0
    var motionX: Int = 0
    var motionY: Int = 0
    var motionZ: Int = 0


    def this(entityIdIn:Int, motionXIn:Float, motionYIn:Float, motionZIn:Float){
        this()
        entityId = entityIdIn
        motionX = (motionXIn *8000F).toInt
        motionY = (motionYIn *8000F).toInt
        motionZ = (motionZIn *8000F).toInt



    }
    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(entityId)
        buf.writeShort(motionX)
        buf.writeShort(motionY)
        buf.writeShort(motionZ)

    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        entityId = buf.readInt()
        motionX = buf.readShort()
        motionY = buf.readShort()
        motionZ = buf.readShort()

    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleEntityVelocity(this)
    }
}
