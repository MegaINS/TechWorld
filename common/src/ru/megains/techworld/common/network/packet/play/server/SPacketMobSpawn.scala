package ru.megains.techworld.common.network.packet.play.server

import ru.megains.techworld.common.entity.EntityLivingBase
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}
import ru.megains.techworld.common.register.Entities

class SPacketMobSpawn extends Packet[INetHandlerPlayClient]{
    var entityId:Int = 0
    var entityClassId:Int = 0
    var posX:Int = 0
    var posY:Int = 0
    var posZ:Int = 0
    var motionX:Int = 0
    var motionY:Int = 0
    var motionZ:Int = 0
    var rotYaw:Int = 0
    var rotPitch:Int = 0
    def this(entity: EntityLivingBase){
        this()

        entityId = entity.id
        entityClassId = Entities.getEntityID(entity)
        posX = Math.floor(entity.posX* 32.0D).toInt
        posY = Math.floor(entity.posY * 32.0D).toInt
        posZ = Math.floor(entity.posZ* 32.0D).toInt
        rotYaw = (entity.rotYaw * 256.0F / 360.0F).toInt.toByte
        rotPitch = (entity.rotPitch * 256.0F / 360.0F).toInt.toByte
        //this.field_149046_k = (entity.rotYawHead * 256.0F / 360.0F).toInt.toByte
        val var2 = 3.9F
        var var4 = entity.motionX
        var var6 = entity.motionY
        var var8 = entity.motionZ

        if (var4 < -var2) var4 = -var2

        if (var6 < -var2) var6 = -var2

        if (var8 < -var2) var8 = -var2

        if (var4 > var2) var4 = var2

        if (var6 > var2) var6 = var2

        if (var8 > var2) var8 = var2

        motionX = (var4 * 8000.0D).toInt
        motionY = (var6 * 8000.0D).toInt
        motionZ = (var8 * 8000.0D).toInt
       // this.field_149043_l = entity.getDataWatcher
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(entityId)
        buf.writeInt(entityClassId)
        buf.writeInt(posX)
        buf.writeInt(posY)
        buf.writeInt(posZ)
        buf.writeInt(motionX)
        buf.writeInt(motionY)
        buf.writeInt(motionZ)
        buf.writeByte(rotPitch)
        buf.writeByte(rotYaw)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        entityId = buf.readInt()
        entityClassId = buf.readInt()
        posX = buf.readInt()
        posY = buf.readInt()
        posZ = buf.readInt()
        motionX = buf.readInt()
        motionY = buf.readInt()
        motionZ = buf.readInt()
        rotPitch = buf.readByte()
        rotYaw = buf.readByte()
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleEntitySpawnMob(this)
    }
}
