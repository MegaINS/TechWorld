package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.play.server.{SPacketJoinGame, SPacketPlayerPosLook}


trait INetHandlerPlayClient extends INetHandler {




    def handleJoinGame(packetIn: SPacketJoinGame): Unit

    def handlePlayerPosLook(packetIn: SPacketPlayerPosLook): Unit

//    def handleChangeGameState(packetIn: SPacketChangeGameState): Unit
//
//    def handlePlayerListItem(packetIn: SPacketPlayerListItem): Unit
//
//    def handleWindowItems(items: SPacketWindowItems): Unit
//
//    def handleSetSlot(slot: SPacketSetSlot): Unit
//
//
//
//    def handleHeldItemChange(packetIn: SPacketHeldItemChange): Unit
//
//
//    def handleChunkData(packetIn: SPacketChunkData): Unit
//
//    def handleBlockChange(packetIn: SPacketBlockChange): Unit
//
//    def handleMultiBlockChange(packetIn: SPacketMultiBlockChange): Unit
}
