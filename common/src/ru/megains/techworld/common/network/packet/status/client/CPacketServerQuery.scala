package ru.megains.techworld.common.network.packet.status.client

import ru.megains.techworld.common.network.handler.INetHandlerStatusServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class CPacketServerQuery extends Packet[INetHandlerStatusServer] {

    def readPacketData(buf: PacketBuffer) :Unit ={
    }


    def writePacketData(buf: PacketBuffer) :Unit ={
    }


    def processPacket(handler: INetHandlerStatusServer) :Unit ={
        handler.processServerQuery(this)
    }
}
