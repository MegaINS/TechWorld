package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}

trait INetHandlerStatusServer extends INetHandler {
    def processPing(packetIn: CPacketPing):Unit 

    def processServerQuery(packetIn: CPacketServerQuery):Unit 
}
