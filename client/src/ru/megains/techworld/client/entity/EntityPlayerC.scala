package ru.megains.techworld.client.entity

import ru.megains.techworld.common.entity.{Entity, EntityPlayer}

class EntityPlayerC extends EntityPlayer{


    var speed = 1.5f

    def update(y: Int): Unit = {


        motionY = y

        calculateMotion(moveForward, moveStrafing, 0.04f)


        posX += motionX
        posX += motionY
        posX += motionZ
    }

    def turn(xo: Float, yo: Float): Unit = {
        rotYaw += xo * 0.15f
        rotPitch += yo * 0.15f
        if (rotPitch < -90.0F) {
            rotPitch = -90.0F
        }
        if (rotPitch > 90.0F) {
            rotPitch = 90.0F
        }
        if (rotYaw > 360.0F) {
            rotYaw -= 360.0F
        }
        if (rotYaw < 0.0F) {
            rotYaw += 360.0F
        }
    }

    def calculateMotion(x: Float, z: Float, limit: Float): Unit = {
        val dist: Float = x * x + z * z
        if (dist > 0.0f) {
            val dX: Float = x / dist * speed * limit
            val dZ: Float = z / dist * speed * limit
            val cosYaw: Float = Math.cos(rotYaw.toRadians).toFloat
            val sinYaw: Float = Math.sin(rotYaw.toRadians).toFloat


            motionZ  -= (dX * cosYaw - dZ * sinYaw)
            motionX  += (dX * sinYaw + dZ * cosYaw)

        }

    }

}
