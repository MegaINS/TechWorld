package ru.megains.techworld.common.entity

import org.joml.Vector3d
import ru.megains.techworld.common.physics.BoundingBox
import ru.megains.techworld.common.utils.{Direction, RayTraceResult}
import ru.megains.techworld.common.world.World

import scala.collection.mutable

abstract class Entity(wight: Float, height: Float,val levelView:Float) {


    var side:Direction = Direction.NONE



    var id: Int = Entity.nextId

    var motionX: Float = 0.0F
    var motionY: Float = 0.0F
    var motionZ: Float = 0.0F

    var posX: Float = 0.0F
    var posY: Float = 0.0F
    var posZ: Float = 0.0F
    var rotYaw: Float = 0
    var rotPitch: Float = 0

    var onGround  = true
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







    def isEntityAlive: Boolean = !isDead
    var isDead = false

    def setDead(): Unit = {
        isDead = true
    }


    def update(): Unit ={
        rotYaw match {
            case y if y > 315 || y <45 => side = Direction.NORTH
            case y if y <135 => side = Direction.EAST
            case y if y <225 => side = Direction.SOUTH
            case y if y <315 => side = Direction.WEST
            case _ => side = Direction.UP
        }
    }

    def setVelocity(motionXIn: Float, motionYIn: Float, motionZIn: Float): Unit = {
        motionX = motionXIn
        motionY = motionYIn
        motionZ = motionZIn
    }



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

    var goY = 0.5f
    def move(x: Float, y: Float, z: Float): Unit = {
        var x0: Float = x
        var z0: Float = z
        var y0: Float = y
        var x1: Float = x
        var z1: Float = z
        var y1: Float = y

        val bodyCopy: BoundingBox = body.getCopy
        var physicalBodes: mutable.HashSet[BoundingBox] = world.addBlocksInList(body.expand(x0, y0, z0))

        physicalBodes.foreach(aabb => {
            y0 = aabb.checkYcollision(body, y0)
        })
        body.move(0, y0, 0)

        physicalBodes.foreach(aabb => {
            x0 = aabb.checkXcollision(body, x0)
        })
        body.move(x0, 0, 0)

        physicalBodes.foreach(aabb => {
            z0 = aabb.checkZcollision(body, z0)
        })
        body.move(0, 0, z0)

        onGround = y != y0 && y < 0.0F
        var a = true
        if (onGround && (Math.abs(x) > Math.abs(x0) || Math.abs(z) > Math.abs(z0))) {
            val b: Float = 0.0625f
            var tY = b
            while (tY <= goY && a) {
                val bodyCopy1: BoundingBox = bodyCopy.getCopy
                x1 = x
                z1 = z
                y1 = y
                physicalBodes = world.addBlocksInList(bodyCopy1.expand(x1, tY, z1))

                physicalBodes.foreach(aabb => {
                    y1 = aabb.checkYcollision(bodyCopy1, tY)
                })
                bodyCopy1.move(0, y1, 0)

                physicalBodes.foreach(aabb => {
                    x1 = aabb.checkXcollision(bodyCopy1, x1)
                })
                bodyCopy1.move(x1, 0, 0)

                physicalBodes.foreach(aabb => {
                    z1 = aabb.checkZcollision(bodyCopy1, z1)
                })
                bodyCopy1.move(0, 0, z1)

                if (Math.abs(x1) > Math.abs(x0) || Math.abs(z1) > Math.abs(z0)) {
                    body.set(bodyCopy1)
                    a = false
                }
                tY += b
            }
        }
        if (x0 != x & x1 != x) {
            motionX = 0.0F
        }
        if (y0 != y) {
            motionY = 0.0F
        }
        if (z0 != z & z1 != z) {
            motionZ = 0.0F
        }

        posX = body.getCenterX
        posY = body.minY
        posZ = body.getCenterZ
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

    def rayTrace(blockDistance: Int): RayTraceResult = {
        val pos = new Vector3d(posX,posY+levelView,posZ)
        val lockVec: Vector3d = getLook.mul(blockDistance)
        world.rayTraceBlocks(pos,lockVec)
    }

    def getLook: Vector3d = {
        getVectorForRotation(rotPitch, rotYaw)
    }

    def getVectorForRotation(pitch : Float, yaw : Float):Vector3d = {

        val sinPitch:Float = Math.sin(pitch.toRadians).toFloat
        val cosPitch:Float = Math.cos(pitch.toRadians).toFloat
        val cosYaw : Float = Math.cos(yaw.toRadians- Math.PI ).toFloat
        val sinYaw : Float = Math.sin(yaw.toRadians).toFloat
        new Vector3d(sinYaw*cosPitch, sinPitch, cosYaw*cosPitch)

    }

    def canEqual(other: Any): Boolean = other.isInstanceOf[Entity]

    override def equals(other: Any): Boolean = other match {
        case that: Entity =>
            (that canEqual this) &&
                    id == that.id
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(id)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}

object Entity {
    private var id = -1
    def nextId: Int = {
        id+=1
        id
    }
}
