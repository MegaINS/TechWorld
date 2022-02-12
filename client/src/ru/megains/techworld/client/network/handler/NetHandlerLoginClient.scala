package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.common.network.{ConnectionState, NetworkManager}
import ru.megains.techworld.common.network.handler.INetHandlerLoginClient
import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadStart
import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect
import ru.megains.techworld.common.network.packet.login.client.CPacketLoginStart
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess

class NetHandlerLoginClient(networkManager: NetworkManager, previousScene: MContainer) extends INetHandlerLoginClient {


    override def handleDisconnect(packetIn: SPacketDisconnect): Unit = {

    }

    override def handleLoginSuccess(packetIn: SPacketLoginSuccess): Unit = {

        networkManager.setConnectionState(ConnectionState.DOWNLOAD)

        networkManager.setNetHandler(new NetHandlerDownloadClient(networkManager, previousScene))
        networkManager.sendPacket(new CPacketDownloadStart("Test"))
    }

    override def disconnect(msg: String): Unit = {

    }
}
