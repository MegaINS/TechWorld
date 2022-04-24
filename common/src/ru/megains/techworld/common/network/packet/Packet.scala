package ru.megains.techworld.common.network.packet

import ru.megains.techworld.common.network.handler.INetHandler
import ru.megains.techworld.common.utils.Logger

abstract class Packet[T <: INetHandler] extends Logger{

    def isImportant = false

    def writePacketData(buf: PacketBuffer): Unit

    def readPacketData(buf: PacketBuffer): Unit

    def processPacket(handler: T): Unit
}



