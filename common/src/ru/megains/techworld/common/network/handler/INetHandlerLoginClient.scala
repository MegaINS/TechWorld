package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess

trait INetHandlerLoginClient extends INetHandler {

    // def handleEncryptionRequest(packetIn: SPacketEncryptionRequest)

    def handleLoginSuccess(packetIn: SPacketLoginSuccess):Unit

    def handleDisconnect(packetIn: SPacketDisconnect):Unit

    // def handleEnableCompression(packetIn: SPacketEnableCompression)
}
