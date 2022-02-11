package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}

trait INetHandlerStatusClient extends INetHandlerClient {


    def handleServerInfo(packetIn: SPacketServerInfo):Unit 

    def handlePong(packetIn: SPacketPong):Unit 
}