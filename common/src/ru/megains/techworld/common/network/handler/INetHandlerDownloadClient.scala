package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.download.server.SPacketDownloadSuccess
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess

trait INetHandlerDownloadClient extends INetHandler {

    def handleDownloadSuccess(packetIn: SPacketDownloadSuccess): Unit


}
