package ru.megains.techworld.server.network.protocol

import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.protocol.{TechWorldCodec, TechWorldMessageCodec}
import ru.megains.techworld.server.TWServer
import ru.megains.techworld.server.network.NetworkSystem
import ru.megains.techworld.server.network.handler.NetHandlerHandshake

class TechWorldServerChannelInitializer(server:TWServer) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        val networkManager = new NetworkManager(server.packetProcessHandler)
        ch.pipeline()
                .addLast("serverCodec",new TechWorldCodec)
                .addLast("messageCodec",new TechWorldMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))


        networkManager.setNetHandler(new NetHandlerHandshake(server, networkManager))
        NetworkSystem.networkManagers += networkManager
    }
}
