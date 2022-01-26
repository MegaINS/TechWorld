package ru.megains.techworld.network

import com.google.common.collect.Lists
import ru.megains.techworld.TechWorld
import ru.megains.techworld.utils.Logger

import java.net.{InetAddress, UnknownHostException}
import java.util.Collections


class ServerPinger() extends Logger[ServerPinger] {

   // private val pingDestinations: util.List[NetworkManager] = Collections.synchronizedList[NetworkManager](Lists.newArrayList[NetworkManager])

    @throws[UnknownHostException]
    def ping(server: ServerData): Unit = {
        val serveraddress: ServerAddress = new ServerAddress(server.serverIP, 20000)

     //   val networkmanager: NetworkManager = NetworkManagerClient.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP), serveraddress.getPort,orangeM)
       // pingDestinations.add(networkmanager)

        server.serverMOTD = "Pinging..."
        server.pingToServer = -1L
        server.playerList = null
       // networkmanager.setNetHandler(new NetHandlerStatusClient(server,networkmanager) )
        try {
         //   networkmanager.sendPacket(new CHandshake(210, serveraddress.getIP, serveraddress.getPort, ConnectionState.STATUS))
           // networkmanager.sendPacket(new CPacketServerQuery)

        } catch {
            case throwable: Throwable =>
                log.error("error",throwable)
                throwable.printStackTrace()
        }
    }
}
