package ru.megains.techworld.common.network.handler

import ru.megains.techworld.common.network.packet.play.server.{SPacketBlockChange, SPacketChangeGameState, SPacketChunkData, SPacketDestroyEntities, SPacketEntity, SPacketEntityTeleport, SPacketEntityVelocity, SPacketJoinGame, SPacketMobSpawn, SPacketMultiBlockChange, SPacketPlayerPosLook, SPacketSetSlot, SPacketSpawnPlayer, SPacketUnloadChunk, SPacketWindowItems}


trait INetHandlerPlayClient extends INetHandler {

    def handleDestroyEntities(packetIn: SPacketDestroyEntities): Unit

    def handleEntitySpawnMob(packetIn: SPacketMobSpawn): Unit

    def handleEntityVelocity(packetIn: SPacketEntityVelocity): Unit

    def handleEntityTeleport(packetIn: SPacketEntityTeleport): Unit

    def handleEntityMovement(packetIn: SPacketEntity): Unit

    def handleSpawnPlayer(packetIn: SPacketSpawnPlayer): Unit

    def handleUnloadChunk(packetIn: SPacketUnloadChunk): Unit

    def handleJoinGame(packetIn: SPacketJoinGame): Unit

    def handlePlayerPosLook(packetIn: SPacketPlayerPosLook): Unit

    def handleChangeGameState(packetIn: SPacketChangeGameState): Unit
//
//    def handlePlayerListItem(packetIn: SPacketPlayerListItem): Unit
//
    def handleWindowItems(items: SPacketWindowItems): Unit

    def handleSetSlot(slot: SPacketSetSlot): Unit
//
//
//
//    def handleHeldItemChange(packetIn: SPacketHeldItemChange): Unit
//
//
    def handleChunkData(packetIn: SPacketChunkData): Unit
//
    def handleBlockChange(packetIn: SPacketBlockChange): Unit
//
    def handleMultiBlockChange(packetIn: SPacketMultiBlockChange): Unit
}
