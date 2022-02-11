package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.packet.handshake.server.SPacketDisconnect
//
//class NetHandlerLoginClient(networkManager: NetworkManager, game: TechWorld, previousScene: MContainer) extends INetHandlerLoginClient {
//
//
//    override def handleDisconnect(packetIn: SPacketDisconnect): Unit = {
//       // PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, gameController)
//    }
//
//    override def onDisconnect(msg: String): Unit = {
//
//    }
//
//    override def handleLoginSuccess(packetIn: SPacketLoginSuccess): Unit = {
//
//
//        networkManager.setConnectionState(ConnectionState.DOWNLOAD)
//        val nhpc: NetHandlerPlayClient = new NetHandlerPlayClient(gameController, previousScene, networkManager)
//        networkManager.setNetHandler(nhpc)
//    }
//  //  override def disconnect(msg: String): Unit = {
//
////}
//}
