package ru.megains.techworld.common.network

import io.netty.util.AttributeKey
import ru.megains.techworld.common.network.ConnectionState.HANDSHAKING.registerPacket
import ru.megains.techworld.common.network.packet.Packet
import ru.megains.techworld.common.network.packet.download.client.CPacketDownloadStart
import ru.megains.techworld.common.network.packet.download.server.SPacketDownloadSuccess
import ru.megains.techworld.common.network.packet.handshake.client.CHandshake
import ru.megains.techworld.common.network.packet.login.client.CPacketLoginStart
import ru.megains.techworld.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.techworld.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.techworld.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}

import scala.collection.mutable


sealed abstract class ConnectionState(val name: String, val id: Int) {

    val packetsId = new mutable.HashMap[ Class[_ <: Packet[_]],Int]()
    val idPackets = new mutable.HashMap[ Int,Class[_ <: Packet[_]]]()

    def registerPacket(packet: Class[_<:Packet[_]]): Unit = {
        packetsId +=  packet -> packetsId.size
        idPackets +=  idPackets.size -> packet
    }

    def getPacketId(packet: Class[_<:Packet[_]]): Int = {
        packetsId(packet)
    }

    def getPacket(id: Int): Packet[_] = {
        val packet = idPackets(id)
        if (packet ne null) packet.newInstance() else null.asInstanceOf[Packet[_]]
    }
}


object ConnectionState {

    val PROTOCOL_ATTRIBUTE_KEY: AttributeKey[ConnectionState] = AttributeKey.newInstance[ConnectionState]("ConnectionState")

    val STATES_BY_CLASS = new mutable.HashMap[Class[_ <: Packet[_]], ConnectionState]()

    def getFromId(id: Int): ConnectionState = {
        STATES_BY_ID(id)
    }


    def getFromPacket(inPacket: Packet[_]): ConnectionState = {
        STATES_BY_CLASS(inPacket.getClass)
    }


    case object HANDSHAKING extends ConnectionState("HANDSHAKING", 0) {
        registerPacket(classOf[CHandshake])
    }


    case object STATUS extends ConnectionState("STATUS", 1) {

        registerPacket(classOf[CPacketServerQuery])
        registerPacket(classOf[CPacketPing])

        registerPacket(classOf[SPacketServerInfo])
        registerPacket(classOf[SPacketPong])

    }

    case object LOGIN extends ConnectionState("LOGIN", 2) {
        registerPacket(classOf[CPacketLoginStart])

        registerPacket(classOf[SPacketLoginSuccess])
    }

    case object DOWNLOAD extends ConnectionState("DOWNLOAD",3){
        registerPacket(classOf[CPacketDownloadStart])

        registerPacket(classOf[SPacketDownloadSuccess])
    }

    case object PLAY extends ConnectionState("PLAY",4){

    }


    val STATES_BY_ID: Array[ConnectionState] = Array(HANDSHAKING, STATUS, LOGIN, PLAY)
    addClass(HANDSHAKING)
    addClass(STATUS)
    addClass(LOGIN)
    addClass(DOWNLOAD)
    addClass(PLAY)

    def addClass(state: ConnectionState): Unit = {

        state.packetsId.keySet.foreach(packet => STATES_BY_CLASS += packet -> state)


    }


}

