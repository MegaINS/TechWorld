package ru.megains.techworld.common.network.packet.handshake.server

import ru.megains.techworld.common.network.handler.{INetHandler, INetHandlerClient}
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

import java.io.IOException

class SPacketDisconnect() extends Packet[INetHandlerClient] {

    private var reason: String = _

    def this(text: String) ={
        this()
        this.reason = text
    }

    /**
      * Reads the raw packet data from the data stream.
      */
    @throws[IOException]
    def readPacketData(buf: PacketBuffer) :Unit ={
        this.reason = buf.readStringFromBuffer(32767)
    }

    /**
      * Writes the raw packet data to the data stream.
      */
    @throws[IOException]
    def writePacketData(buf: PacketBuffer) :Unit ={
        buf.writeStringToBuffer(reason)
    }

    /**
      * Passes this Packet on to the NetHandler for processing.
      */
    def processPacket(handler: INetHandlerClient):Unit = {
        handler.handleDisconnect(this)
    }


}
