package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.client.renderer.gui.GuiScreen
import ru.megains.techworld.common.network.{ConnectionState, NetworkManager}
import ru.megains.techworld.common.network.handler.INetHandlerDownloadClient
import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadFinish
import ru.megains.techworld.common.network.packet.download.server.{SPacketDownloadStart, SPacketDownloadSuccess}
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.techworld.common.utils.Logger

class NetHandlerDownloadClient(game: TechWorld,networkManager: NetworkManager, previousScene: GuiScreen) extends INetHandlerDownloadClient with Logger{



    override def disconnect(msg: String): Unit = {

    }

    override def processDownloadStart(start: SPacketDownloadStart): Unit = {

        networkManager.sendPacket(new CPacketDownloadFinish())

    }

    override def handleDownloadSuccess(success: SPacketDownloadSuccess): Unit = {
        networkManager.setConnectionState(ConnectionState.PLAY)
        log.info("DownloadSuccess")
        networkManager.setNetHandler(new NetHandlerPlayClient(game, previousScene,networkManager ))
    }
}
