package ru.megains.techworld.server.network

import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelFuture, EventLoopGroup}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.server.TWServer
import ru.megains.techworld.server.network.protocol.TechWorldServerChannelInitializer

import java.net.InetAddress
import scala.collection.mutable.ArrayBuffer


class NetworkSystem(server: TWServer) {

    var networkServer: ServerBootstrap = _
    var channelFuture: ChannelFuture = _
    val bossExec: EventLoopGroup = new NioEventLoopGroup(0, (new ThreadFactoryBuilder).setNameFormat("Netty IO #%d").setDaemon(true).build)

    def startLan(address: InetAddress, port: Int): Unit = {


        networkServer = new ServerBootstrap()
                .group(bossExec)
                .localAddress(address, port)
                .channel(classOf[NioServerSocketChannel])
                .childHandler(new TechWorldServerChannelInitializer(server))
        channelFuture = networkServer.bind.syncUninterruptibly()
        // channelFuture.channel().closeFuture().syncUninterruptibly()

    }


    def tick():Unit = {

        NetworkSystem.networkManagers = NetworkSystem.networkManagers.flatMap(
            networkManager => {

                if (!networkManager.hasNoChannel) {
                    if (networkManager.isChannelOpen) {

                        try {
                            networkManager.processReceivedPackets()
                        } catch {
                            case exception: Exception =>
                                exception.printStackTrace()
                        }
                        Some(networkManager)
                    } else {
                        networkManager.checkDisconnected()
                        None
                    }
                } else {
                    Some(networkManager)
                }

            }

        )

    }


}

object NetworkSystem {
    var networkManagers: ArrayBuffer[NetworkManager] = new ArrayBuffer[NetworkManager]
}

