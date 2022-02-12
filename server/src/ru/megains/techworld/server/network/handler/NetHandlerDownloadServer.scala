package ru.megains.techworld.server.network.handler

import ru.megains.techworld.common.network.ConnectionState.{DOWNLOAD, PLAY}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandlerDownloadServer
import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadStart
import ru.megains.techworld.common.network.packet.download.server.SPacketDownloadSuccess
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.server.TWServer

class NetHandlerDownloadServer(server: TWServer, networkManager: NetworkManager) extends INetHandlerDownloadServer with Logger {

    var currentLoginState: DownloadState.Value = DownloadState.HELLO

    networkManager.setConnectionState(DOWNLOAD)
    override def processDownloadStart(start: CPacketDownloadStart): Unit = {
        currentLoginState = DownloadState.READY_TO_ACCEPT
        tryAcceptPlayer()
    }

    def update(): Unit = {
        if (currentLoginState == DownloadState.READY_TO_ACCEPT) tryAcceptPlayer()

        //        if ( {
        //            connectionTimer += 1; connectionTimer - 1
        //        } == 600) closeConnection("Took too long to log in")
    }

    def tryAcceptPlayer(): Unit = {
        //  val s: String = server.playerList.allowUserToConnect(networkManager.getRemoteAddress, loginGameProfile)
        //  if (s != null) closeConnection(s)
        //   else {
        currentLoginState = DownloadState.ACCEPTED

        networkManager.sendPacket(new SPacketDownloadSuccess())
       // networkManager.setConnectionState(PLAY)
       // networkManager.setNetHandler(new NetHandlerPlayServer(server, networkManager))
        // var entityPlayer: EntityPlayerMP = server.playerList.getPlayerByName(name)
        // if (entityPlayer == null) {
        //     entityPlayer = server.playerList.createPlayerForUser(name)
        // }
        // server.playerList.initializeConnectionToPlayer(networkManager, entityPlayer)
        // }
    }

    override def disconnect(msg: String): Unit = {

    }


    object DownloadState extends Enumeration {
        type LoginState = Value
        val HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED = Value
    }
}
