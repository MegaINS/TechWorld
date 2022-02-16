package ru.megains.techworld.common.entity

import ru.megains.techworld.common.physics.BoundingBox

abstract class Entity(wight:Float,height:Float) {


    var posX: Float = 0.0F
    var posY: Float = 0.0F
    var posZ: Float = 0.0F
    var rotYaw: Float = 0
    var rotPitch: Float = 0
    val body: BoundingBox = new BoundingBox(-wight/2,0,-wight/2,wight/2,height,wight/2)

    def setPositionAndRotation(x: Float, y: Float, z: Float, yaw: Float, pitch: Float): Unit = {
        posX = x
        posY = y
        posZ = z
        rotYaw = yaw
        rotPitch = pitch

        setPosition(posX, posY, posZ)
        setRotation(yaw, pitch)
    }

    def setPosition(x: Float, y: Float, z: Float) :Unit ={
        posX = x
        posY = y
        posZ = z
        val i = wight/2
        body.set(x-i, y, z-i, x+i,y+ height, z+i)
    }


    def setRotation(yaw: Float, pitch: Float): Unit = {
        rotYaw = yaw % 360.0F
        rotPitch = pitch % 360.0F
    }
}
