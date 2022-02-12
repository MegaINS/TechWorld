package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.login.client.CPacketLoginStart

trait INetHandlerLoginServer extends INetHandler {

    def processLoginStart(packetIn: CPacketLoginStart):Unit

    //  def processEncryptionResponse(packetIn: CPacketEncryptionResponse)
}
