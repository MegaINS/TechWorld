package ru.megains.techworld.client.network.protocol

import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.protocol.{TechWorldCodec, TechWorldMessageCodec}


class TechWorldChannelInitializer(networkManager:NetworkManager) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        ch.pipeline()
                .addLast("serverCodec",new TechWorldCodec)
                .addLast("messageCodec", new TechWorldMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))
    }
}
