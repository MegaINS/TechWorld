package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.play.client.{CPacketClickWindow, CPacketHeldItemChange, CPacketPlayer, CPacketPlayerMouse}

trait INetHandlerPlayServer extends INetHandler {

    def processPlayer(packetIn: CPacketPlayer): Unit

    def processPlayerMouse(packetIn: CPacketPlayerMouse): Unit

    def processHeldItemChange(packetIn: CPacketHeldItemChange): Unit

   // def processCloseWindow(packetIn: CPacketCloseWindow): Unit
//    def processNEI(packetIn: CPacketNEI): Unit
    def processClickWindow(packetIn: CPacketClickWindow): Unit
//    def processPlayerDigging(packetIn: CPacketPlayerDigging): Unit
   // def processPlayerBlockPlacement(packetIn: CPacketPlayerTryUseItem): Unit
   // def processRightClickBlock(packetIn: CPacketPlayerTryUseItemOnBlock): Unit

}
