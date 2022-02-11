package ru.megains.techworld.client.network

import com.google.common.collect.Lists
import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.network.handler.NetHandlerStatusClient
import ru.megains.techworld.common.network.{ConnectionState, NetworkManager}
import ru.megains.techworld.common.network.packet.handshake.client.CHandshake
import ru.megains.techworld.common.network.packet.status.client.CPacketServerQuery
import ru.megains.techworld.common.utils.Logger

import java.net.{InetAddress, UnknownHostException}
import java.util.Collections


class ServerPinger(game:TechWorld) extends Logger {

    @throws[UnknownHostException]
    def ping(server: ServerData): Unit = {
        val serveraddress: ServerAddress = new ServerAddress(server.serverIP, 20000)

        val networkmanager: NetworkManager = NetworkManagerClient.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP), serveraddress.getPort,game.packetProcessHandler)

        server.serverMOTD = "Pinging..."
        server.pingToServer = -1L
        server.playerList = null
        networkmanager.setNetHandler(new NetHandlerStatusClient(server,networkmanager) )
        try {
            networkmanager.sendPacket(new CHandshake(210, serveraddress.getIP, serveraddress.getPort, ConnectionState.STATUS))
            networkmanager.sendPacket(new CPacketServerQuery)

        } catch {
            case throwable: Throwable =>
                log.error("error",throwable)
                throwable.printStackTrace()
        }
    }
}
