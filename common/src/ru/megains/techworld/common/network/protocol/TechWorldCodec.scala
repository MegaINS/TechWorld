package ru.megains.techworld.common.network.protocol

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec

import java.util

class  TechWorldCodec extends ByteToMessageCodec[ByteBuf]{

    override def encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf): Unit = {

        out.writeInt(msg.readableBytes)
        out.writeBytes(msg)

    }

    override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: util.List[AnyRef]): Unit = {
        in.markReaderIndex

        if(in.readableBytes()>=4){
            val length = in.readInt()
            if (in.readableBytes >= length) out.add(in.readBytes(length))
            else in.resetReaderIndex
        }

    }
}
