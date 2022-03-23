package ru.megains.techworld.common.entity

import ru.megains.techworld.common.physics.BoundingBox
import ru.megains.techworld.common.world.World

abstract class Entity(wight: Float, height: Float) {


    def setVelocity(motionXIn: Float, motionYIn: Float, motionZIn: Float): Unit = {
        motionX = motionXIn
        motionY = motionYIn
        motionZ = motionZIn
    }


    var id: Int = Entity.nextId

    var motionX: Float = 0.0F
    var motionY: Float = 0.0F
    var motionZ: Float = 0.0F

    var posX: Float = 0.0F
    var posY: Float = 0.0F
    var posZ: Float = 0.0F
    var rotYaw: Float = 0
    var rotPitch: Float = 0


    var isJumping: Boolean = false
    var moveStrafing: Int = 0
    var moveForward: Int = 0
    var speed = 1.5f


    var prevPosX = .0f
    var prevPosY = .0f
    var prevPosZ = .0f

    var lastTickPosX = .0
    var lastTickPosY = .0
    var lastTickPosZ = .0

    var world: World = _
    var chunkCoordX: Int = 0
    var chunkCoordY: Int = 0
    var chunkCoordZ: Int = 0
    var serverPosX = 0
    var serverPosY = 0
    var serverPosZ = 0

    var isAirBorne: Boolean = false


    var addedToChunk = false


    val body: BoundingBox = new BoundingBox(-wight / 2, 0, -wight / 2, wight / 2, height, wight / 2)

    def setPositionAndRotation(x: Float, y: Float, z: Float, yaw: Float, pitch: Float): Unit = {
        prevPosX = x
        prevPosY = y
        prevPosZ = z
        posX = x
        posY = y
        posZ = z
        rotYaw = yaw
        rotPitch = pitch

        setPosition(posX, posY, posZ)
        setRotation(yaw, pitch)
    }

    def setPositionAndRotation2(x: Float, y: Float, z: Float, yaw: Float, pitch: Float,test:Int): Unit = {
        setPosition(x, y, z)
        setRotation(yaw, pitch)
//        val var10 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125D, 0.0D, 0.03125D))
//
//        if (!var10.isEmpty) {
//            var var11 = 0.0D
//            for (var13 <- 0 until var10.size) {
//                val var14 = var10.get(var13).asInstanceOf[AxisAlignedBB]
//                if (var14.maxY > var11) var11 = var14.maxY
//            }
//            p_70056_3_ += var11 - this.boundingBox.minY
//            this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_)
//        }
    }


    def setPosition(x: Float, y: Float, z: Float): Unit = {
        posX = x
        posY = y
        posZ = z
        val i = wight / 2
        body.set(x - i, y, z - i, x + i, y + height, z + i)
    }


    def setRotation(yaw: Float, pitch: Float): Unit = {
        rotYaw = yaw % 360.0F
        rotPitch = pitch % 360.0F
    }

    def move(x: Float, y: Float, z: Float): Unit = {
        posX += x
        posY += y
        posZ += z
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


            motionZ -= (dX * cosYaw - dZ * sinYaw)
            motionX += (dX * sinYaw + dZ * cosYaw)

        }

    }

    def getDistanceSq(posXIn:Double, posYIn: Double, posZIn: Double): Double = {
        val dX = posX - posXIn
        val dY = posY - posYIn
        val dZ = posZ - posZIn
        dX * dX + dY * dY + dZ * dZ
    }
}

object Entity {
    private var id = -1
    def nextId: Int = {
        id+=1
        id
    }
}
