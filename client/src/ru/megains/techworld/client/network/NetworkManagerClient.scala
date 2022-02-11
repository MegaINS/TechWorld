package ru.megains.techworld.client.network

import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import ru.megains.techworld.client.network.protocol.TechWorldChannelInitializer
import ru.megains.techworld.common.network.{NetworkManager, PacketProcessHandler}

import java.net.InetAddress

object NetworkManagerClient {

    val nioEventLoopGroup: NioEventLoopGroup = new NioEventLoopGroup(0,(new ThreadFactoryBuilder).setNameFormat("Netty IO #%d").setDaemon(true).build)

    def createNetworkManagerAndConnect( address: InetAddress, serverPort: Int,packetProcess: PacketProcessHandler): NetworkManager = {
        val networkManager: NetworkManager = new NetworkManager(packetProcess)

        new Bootstrap()
                .group(nioEventLoopGroup)
                .handler(new TechWorldChannelInitializer(networkManager))
                .channel(classOf[NioSocketChannel])
                .connect(address, serverPort)
                .syncUninterruptibly()
        networkManager

    }


}
