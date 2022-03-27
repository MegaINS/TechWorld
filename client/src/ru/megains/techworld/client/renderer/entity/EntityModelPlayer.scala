package ru.megains.techworld.client.renderer.entity

import org.lwjgl.opengl.GL11.GL_LINES
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureManager}
import ru.megains.techworld.common.physics.BoundingBox

import java.awt.Color

class EntityModelPlayer extends EntityModel {

    var entityCube: Mesh = _
    var entityHead: EntityBox = _
    var entityTors: EntityBox = _

    var leftArm: EntityBox = _
    var rightArm: EntityBox = _

    var leftLeg: EntityBox = _
    var rightLeg: EntityBox = _

    override def init(): Unit = {
        texture =  TextureManager.getTexture("textures/entity/steve.png")
        val mm = MeshMaker.startMake(GL_LINES)
        val aabb: BoundingBox = new BoundingBox(-0.3f, 0, -0.3f, 0.3f, 1.8f, 0.3f)

        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ


        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        entityCube = mm.make()

        entityTors = new EntityBox(this,-4, 0, -2, 8, 12, 4, 16, 0)
        entityTors.init()
        entityTors.offsetY = 12
        entityHead = new EntityBox(this,-4, 0, -4, 8, 8, 8,  0, 16)
        entityHead.init()

        entityHead.offsetY = 24

        leftArm = new EntityBox(this,-2, 0, -2, 4, 12, 4,  40 , 0)
        leftArm.init()
        leftArm.offsetX = -6
        leftArm.offsetY = 12
        leftArm.rotationPoint.y = 12

        rightArm = new EntityBox(this,-2, 0, -2, 4, 12, 4,  40 , 0)
        rightArm.init()
        rightArm.offsetX = 6
        rightArm.offsetY = 12
        rightArm.rotationPoint.y = 12

        leftLeg = new EntityBox(this,-2, 0, -2, 4, 12, 4,  0, 0)
        leftLeg.init()
        leftLeg.offsetX = -2
        //leftLeg.offsetY = -24
        rightLeg = new EntityBox(this,-2, 0, -2, 4, 12, 4,  0, 0)
        rightLeg.init()
        rightLeg.offsetX = 2
        //rightLeg.offsetY = -24

        addChildren(entityTors,entityHead,leftArm,rightArm,leftLeg,rightLeg)
    }
}
