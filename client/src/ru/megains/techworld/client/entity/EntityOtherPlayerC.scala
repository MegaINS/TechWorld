package ru.megains.techworld.client.entity

import ru.megains.techworld.client.world.WorldClient
import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.world.World

class EntityOtherPlayerC(worldClient: WorldClient, name:String) extends EntityPlayer{
    override def setContainer(world: World, x: Int, y: Int, z: Int): Unit = {}
}
