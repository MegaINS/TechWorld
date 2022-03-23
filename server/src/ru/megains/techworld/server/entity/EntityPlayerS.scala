package ru.megains.techworld.server.entity

import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandler
import ru.megains.techworld.common.world.World
import ru.megains.techworld.server.world.WorldServer

class EntityPlayerS(val name:String) extends EntityPlayer{


    def worldServer:WorldServer = world.asInstanceOf[WorldServer]

    var managedPosZ: Float = 0
    var managedPosY: Float = 0
    var managedPosX: Float = 0

    var connection:INetHandler = _
}
