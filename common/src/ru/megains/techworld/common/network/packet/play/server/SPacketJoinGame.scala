package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketJoinGame extends Packet[INetHandlerPlayClient] {

    var playerId: Int = -1

    def this(entityPlayer: EntityPlayer){
        this()
        playerId = entityPlayer.id
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        playerId = buf.readInt

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(playerId)
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleJoinGame(this)
    }
}
