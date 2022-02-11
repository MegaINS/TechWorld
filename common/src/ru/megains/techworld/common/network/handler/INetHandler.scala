package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.Packet
import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect


trait INetHandler {

    def disconnect(msg: String):Unit

    def sendPacket(packetIn: Packet[_ <: INetHandler]):Unit ={}

}
