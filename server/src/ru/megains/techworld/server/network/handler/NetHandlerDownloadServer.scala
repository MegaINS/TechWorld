package ru.megains.techworld.server.network.handler

import ru.megains.techworld.common.network.ConnectionState.{DOWNLOAD, PLAY}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandlerDownloadServer
import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadFinish
import ru.megains.techworld.common.network.packet.download.server.{SPacketDownloadStart, SPacketDownloadSuccess}
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.server.TWServer
import ru.megains.techworld.server.entity.EntityPlayerS

class NetHandlerDownloadServer(server: TWServer, networkManager: NetworkManager,entityPlayer: EntityPlayerS) extends INetHandlerDownloadServer with Logger {

    var currentLoginState: DownloadState.Value = DownloadState.HELLO



    def update(): Unit = {
        if (currentLoginState == DownloadState.READY_TO_ACCEPT) tryAcceptPlayer()

        //        if ( {
        //            connectionTimer += 1; connectionTimer - 1
        //        } == 600) closeConnection("Took too long to log in")
    }

    override def handleDownloadFinish(finish: CPacketDownloadFinish): Unit = {


        currentLoginState = DownloadState.READY_TO_ACCEPT
        tryAcceptPlayer()


    }

    def tryAcceptPlayer(): Unit = {

        currentLoginState = DownloadState.ACCEPTED
        networkManager.sendPacket(new SPacketDownloadSuccess())
        server.playerList.initializeConnectionToPlayer(networkManager, entityPlayer)

    }

    override def disconnect(msg: String): Unit = {

    }


    object DownloadState extends Enumeration {
        type LoginState = Value
        val HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED = Value
    }


}
