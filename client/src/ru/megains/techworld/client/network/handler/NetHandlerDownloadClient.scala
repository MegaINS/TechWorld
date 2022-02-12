package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.common.network.{ConnectionState, NetworkManager}
import ru.megains.techworld.common.network.handler.INetHandlerDownloadClient
import ru.megains.techworld.common.network.packet.download.server.SPacketDownloadSuccess
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.techworld.common.utils.Logger

class NetHandlerDownloadClient(networkManager: NetworkManager, previousScene: MContainer) extends INetHandlerDownloadClient with Logger{


    override def handleDownloadSuccess(packetIn: SPacketDownloadSuccess): Unit = {

        networkManager.setConnectionState(ConnectionState.PLAY)
        log.info("DownloadSuccess")
        // val nhpc: NetHandlerPlayClient = new NetHandlerPlayClient(gameController, previousScene, networkManager)
        // networkManager.setNetHandler(nhpc)
    }

    override def disconnect(msg: String): Unit = {

    }
}
