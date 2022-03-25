package ru.megains.techworld.server.entity

import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandler
import ru.megains.techworld.common.network.packet.play.server.SPacketDestroyEntities
import ru.megains.techworld.common.world.World
import ru.megains.techworld.server.world.WorldServer

import scala.collection.mutable.ArrayBuffer

class EntityPlayerS(val name:String) extends EntityPlayer{


    def worldServer:WorldServer = world.asInstanceOf[WorldServer]

    var managedPosZ: Float = 0
    var managedPosY: Float = 0
    var managedPosX: Float = 0

    var connection:INetHandler = _

    val destroyedItemsNetCache = new ArrayBuffer[Int]()
    override def update(): Unit = {
        super.update()

        while (destroyedItemsNetCache.nonEmpty) {

            val size = Math.min(destroyedItemsNetCache.size, 127)

            connection.sendPacket(new SPacketDestroyEntities(destroyedItemsNetCache.slice(0,size).toArray))

            destroyedItemsNetCache.remove(0,size)

        }
    }




    def destroyEntityNetCache(entity:Entity): Unit = {
        if (entity.isInstanceOf[EntityPlayer]) connection.sendPacket(new SPacketDestroyEntities(Array[Int](entity.id)))
        else destroyedItemsNetCache += entity.id
    }
}
