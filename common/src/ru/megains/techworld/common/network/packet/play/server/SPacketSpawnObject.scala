package ru.megains.techworld.common.network.packet.play.server

import io.netty.buffer.{ByteBufInputStream, ByteBufOutputStream}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.nbt.NBTData
import ru.megains.techworld.common.nbt.tag.NBTCompound
import ru.megains.techworld.common.network.handler.INetHandlerPlayClient
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}

class SPacketSpawnObject extends Packet[INetHandlerPlayClient]{

    var entityId:Int = 0
    var entityClassId:Int = 0
    var posX:Int = 0
    var posY:Int = 0
    var posZ:Int = 0

    var isMotion = false
    var motionX:Int = 0
    var motionY:Int = 0
    var motionZ:Int = 0
    var rotYaw:Int = 0
    var rotPitch:Int = 0

    val nbt: NBTCompound = NBTData.createCompound()



    def this(entity: Entity, entityClassIdIn:Int, isMotionIn: Boolean){
        this()
        entity.writeToNBT(nbt)
        entityId = entity.id
        posX = Math.floor(entity.posX * 32.0D).toInt
        posY = Math.floor(entity.posY * 32.0D).toInt
        posZ = Math.floor(entity.posZ * 32.0D).toInt
        rotPitch = Math.floor(entity.rotPitch * 256.0F / 360.0F).toInt
        rotYaw = Math.floor(entity.rotYaw * 256.0F / 360.0F).toInt
        entityClassId = entityClassIdIn
        isMotion = isMotionIn
        if (isMotion) {
            var var4 = entity.motionX
            var var6 = entity.motionY
            var var8 = entity.motionZ
            val var10 = 3.9F
            if (var4 < -var10) var4 = -var10
            if (var6 < -var10) var6 = -var10
            if (var8 < -var10) var8 = -var10
            if (var4 > var10) var4 = var10
            if (var6 > var10) var6 = var10
            if (var8 > var10) var8 = var10
            motionX = (var4 * 8000.0D).toInt
            motionY = (var6 * 8000.0D).toInt
            motionZ = (var8 * 8000.0D).toInt
        }
    }

    def this(entity: Entity, entityClassIdIn:Int) {
        this(entity, entityClassIdIn,false)
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

        nbt.write(new ByteBufOutputStream(buf))

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
        nbt.read(new ByteBufInputStream(buf))
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleSpawnObject(this)
    }
}
