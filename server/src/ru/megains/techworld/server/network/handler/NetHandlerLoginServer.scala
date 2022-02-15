package ru.megains.techworld.server.network.handler

import ru.megains.techworld.common.network.ConnectionState.{DOWNLOAD, STATUS}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.{INetHandlerDownloadServer, INetHandlerLoginServer}
import ru.megains.techworld.common.network.packet.download.server.SPacketDownloadStart
import ru.megains.techworld.common.network.packet.login.client.CPacketLoginStart
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.techworld.server.TWServer
import ru.megains.techworld.server.entity.EntityPlayerS

class NetHandlerLoginServer(server: TWServer, networkManager: NetworkManager) extends INetHandlerLoginServer /*with ITickable*/ {

    var currentLoginState: LoginState.Value = LoginState.HELLO
    var name: String = _


    override def processLoginStart(packetIn: CPacketLoginStart): Unit = {
        currentLoginState = LoginState.READY_TO_ACCEPT
        name = packetIn.name
        tryAcceptPlayer()
    }



    def update(): Unit = {
        if (currentLoginState == LoginState.READY_TO_ACCEPT) tryAcceptPlayer()
       // if (currentLoginState == LoginState.ACCEPTED) startDownload()
        //        if ( {
        //            connectionTimer += 1; connectionTimer - 1
        //        } == 600) closeConnection("Took too long to log in")
    }

    def tryAcceptPlayer(): Unit = {
        //  val s: String = server.playerList.allowUserToConnect(networkManager.getRemoteAddress, loginGameProfile)
        //  if (s != null) closeConnection(s)
        //   else {
        currentLoginState = LoginState.ACCEPTED

        networkManager.sendPacket(new SPacketLoginSuccess())

        var entityPlayer: EntityPlayerS = server.playerList.getPlayerByName(name)
        if (entityPlayer == null) {
            entityPlayer = server.playerList.createPlayerForUser(name)
        }

        networkManager.setNetHandler(new NetHandlerDownloadServer(server, networkManager,entityPlayer))
        networkManager.sendPacket(new SPacketDownloadStart())
    }



    object LoginState extends Enumeration {
        type LoginState = Value
        val HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED = Value
    }

    override def disconnect(msg: String): Unit = {

    }
}


