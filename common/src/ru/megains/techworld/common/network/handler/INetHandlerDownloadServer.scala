package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadStart

trait INetHandlerDownloadServer extends INetHandler {


    def processDownloadStart(start: CPacketDownloadStart): Unit

}
