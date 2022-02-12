package ru.megains.techworld.server.network.handler

import ru.megains.techworld.common.network.ConnectionState.{LOGIN, STATUS}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandlerHandshake
import ru.megains.techworld.common.network.packet.handshake.client.CHandshake
import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect
import ru.megains.techworld.server.TWServer

class NetHandlerHandshake(server: TWServer, networkManager: NetworkManager) extends INetHandlerHandshake {


    def processHandshake(packetIn: CHandshake): Unit = {

        packetIn.connectionState match {
            case LOGIN =>
                networkManager.setConnectionState(LOGIN)
                if (packetIn.version > 210) {
                    val text: String = "Outdated server! I\'m still on 1.10.2"
                    networkManager.sendPacket(new SPacketDisconnect(text))
                    networkManager.closeChannel(text)
                }
                else if (packetIn.version < 210) {
                    val text: String = "Outdated client! Please use 1.10.2"
                    networkManager.sendPacket(new SPacketDisconnect(text))
                    networkManager.closeChannel(text)
                }
                else{
                    networkManager.setNetHandler(new NetHandlerLoginServer(server, networkManager))
                }

            case STATUS =>
                networkManager.setConnectionState(STATUS)
                networkManager.setNetHandler(new NetHandlerStatusServer(server, networkManager))

            case _ =>
                throw new UnsupportedOperationException("Invalid intention " + packetIn.connectionState)
        }
    }


    override def disconnect(msg: String): Unit = {

    }
}
