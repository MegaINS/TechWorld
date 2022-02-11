package ru.megains.techworld.server.network.handler

import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandlerStatusServer
import ru.megains.techworld.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.techworld.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.techworld.server.TWServer

class NetHandlerStatusServer(val server: TWServer, val networkManager: NetworkManager) extends INetHandlerStatusServer {
    private var handled: Boolean = false


    def processServerQuery(packetIn: CPacketServerQuery): Unit = {

        if (handled) this.networkManager.closeChannel(NetHandlerStatusServer.EXIT_MESSAGE)
        else {
            handled = true
            networkManager.sendPacket(new SPacketServerInfo(server.statusResponse))
        }
    }

    def processPing(packetIn: CPacketPing): Unit = {

        this.networkManager.sendPacket(new SPacketPong(packetIn.clientTime))
        this.networkManager.closeChannel(NetHandlerStatusServer.EXIT_MESSAGE)
    }

    override def disconnect(msg: String): Unit = {

    }
}

object NetHandlerStatusServer {

    private val EXIT_MESSAGE: String = "Status request has been handled."
}
