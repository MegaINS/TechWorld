package ru.megains.techworld.common.register

import ru.megains.techworld.common.entity.{Entity, EntityLivingBase}
import ru.megains.techworld.common.world.World

object Entities {


    def createEntityByID(entityClassId: Int, world: World): Entity = {
       GameRegister.getEntityById(entityClassId).getConstructor(classOf[World]).newInstance(world)
    }


    def getEntityID(entity: Entity): Int ={
        GameRegister.getIdByEntity(entity.getClass)
    }


}
