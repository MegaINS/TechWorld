package ru.megains.techworld.common.network

trait IPacketProcessHandler {
    def addPacket(packet: () => Unit): Unit
}
