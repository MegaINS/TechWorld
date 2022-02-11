package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.network.ServerData
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandlerStatusClient
import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect
import ru.megains.techworld.common.network.packet.status.client.CPacketPing
import ru.megains.techworld.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.techworld.common.utils.Logger

class NetHandlerStatusClient(server:ServerData,networkManager: NetworkManager) extends INetHandlerStatusClient with Logger{

    var successful: Boolean = false
    var receivedStatus: Boolean = false
    var pingSentAt: Long = 0L

    override def handleServerInfo(packetIn: SPacketServerInfo):Unit = {
        if (receivedStatus) networkManager.closeChannel("ServerPinger" + "receivedStatus")
        else {
            receivedStatus = true

            this.pingSentAt = TechWorld.getSystemTime
            networkManager.sendPacket(new CPacketPing(pingSentAt))
            this.successful = true
        }
    }

    override def handlePong(packetIn: SPacketPong) :Unit ={
        val i: Long = pingSentAt
        val j: Long = TechWorld.getSystemTime
        server.pingToServer = j - i
        networkManager.closeChannel("ServerPinger" + "handlePong")
    }

    override def disconnect(msg: String):Unit = {
        if (!successful) {
            log.error("Can\'t ping {}: {}", Array[AnyRef](server.serverIP, msg))
            server.serverMOTD = "Can\'t connect to server."
            server.populationInfo = ""
        }
    }

    override def handleDisconnect(disconnect: SPacketDisconnect): Unit = {

    }
}
