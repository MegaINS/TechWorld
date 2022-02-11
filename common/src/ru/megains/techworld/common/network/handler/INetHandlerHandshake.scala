package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.handshake.client.CHandshake


trait INetHandlerHandshake extends INetHandler{

    def processHandshake(handshake: CHandshake): Unit

}
