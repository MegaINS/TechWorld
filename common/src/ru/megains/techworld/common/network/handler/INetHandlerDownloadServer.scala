package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadFinish
import ru.megains.techworld.common.network.packet.download.server.{SPacketDownloadStart, SPacketDownloadSuccess}

trait INetHandlerDownloadServer extends INetHandler {
    def handleDownloadFinish(finish: CPacketDownloadFinish): Unit





}
