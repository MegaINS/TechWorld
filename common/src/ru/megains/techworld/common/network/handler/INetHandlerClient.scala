package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect

trait INetHandlerClient extends INetHandler{

    def handleDisconnect(disconnect: SPacketDisconnect): Unit

}
