package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

import java.io.{DataInputStream, DataOutputStream}

class SPacketSpawnPlayer extends Packet[INetHandlerPlayClient] {
   
    var name = ""
    var entityId: Int = 0
    var rotation: Int = 0
    var pitch: Int = 0
    var xPosition: Int = 0
    var yPosition: Int = 0
    var zPosition: Int = 0

    def this(entityPlayer: EntityPlayer){
        this()
        entityId = entityPlayer.id
       // name = entityPlayer.username
        xPosition = Math.floor(entityPlayer.posX * 32.0D).toInt
        yPosition = Math.floor(entityPlayer.posY * 32.0D).toInt
        zPosition = Math.floor(entityPlayer.posZ * 32.0D).toInt
        rotation = (entityPlayer.rotYaw * 256.0F / 360.0F).toInt.toByte
        pitch = (entityPlayer.rotPitch * 256.0F / 360.0F).toInt.toByte
//        val var2 = entityPlayer.inventory.getCurrentItem
//        currentItem = if (var2 == null) 0
//        else var2.itemID
//        metadata = entityPlayer.getDataWatcher
    }


    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(entityId)
        buf.writeStringToBuffer(name)
        buf.writeInt(xPosition)
        buf.writeInt(yPosition)
        buf.writeInt(zPosition)
        buf.writeByte(rotation)
        buf.writeByte(pitch)
       // buf.writeShort(currentItem)
       // metadata.writeWatchableObjects(buf)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        entityId = buf.readInt
        name = buf.readStringFromBuffer(256)
        xPosition = buf.readInt
        yPosition = buf.readInt
        zPosition = buf.readInt
        rotation = buf.readByte
        pitch = buf.readByte
      //  currentItem = buf.readShort
       // field_73517_j = DataWatcher.readWatchableObjects(buf)
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleSpawnPlayer(this)
    }
}
