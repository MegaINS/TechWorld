package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.download.server.{SPacketDownloadStart, SPacketDownloadSuccess}
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess

trait INetHandlerDownloadClient extends INetHandler {



    def processDownloadStart(start: SPacketDownloadStart): Unit

    def handleDownloadSuccess(success: SPacketDownloadSuccess): Unit
}
