package ru.megains.techworld.client.renderer.entity

import org.lwjgl.opengl.GL11.GL_LINES
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureManager}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.physics.BoundingBox

import java.awt.Color

class EntityModelCow extends EntityModel {


    var entityCube: Mesh = _
    var head: EntityBox = _
    var body: EntityBox = _

    var leftFrontLeg: EntityBox = _
    var rightFrontLeg: EntityBox = _

    var leftBackLeg: EntityBox = _
    var rightBackLeg: EntityBox = _


    def init(): Unit = {

        texture =  TextureManager.getTexture("textures/entity/cow.png")
        val mm = MeshMaker.startMake(GL_LINES)
        val aabb: BoundingBox = new BoundingBox(-0.45f, 0, -0.45f, 0.45f, 1.3f, 0.45f)

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
        head = new EntityBox(this,-4, -4, -6, 8, 8, 6, 0, 18)
        head.init()
        head.offsetY = 4
        head.offsetZ = -9
        head.rotationPoint.add(0,4,-8)


        body = new EntityBox(this,-6, -10, -7, 12, 18, 10, 18, 0)
        body.init()

        body.offsetZ = -8
        body.rotationPoint.add(0,5,2)
        body.xRot = -90// Math.PI.toFloat / 2F


        leftFrontLeg = new EntityBox(this,-2, 0, -2, 4, 12, 4, 0, 0)
        leftFrontLeg.init()
        leftFrontLeg.offsetZ = -7
        leftFrontLeg.offsetX = -4
        leftFrontLeg.offsetY = -16
        leftFrontLeg.rotationPoint.add(0,12,0)
        rightFrontLeg = new EntityBox(this,-2, 0, -2, 4, 12, 4, 0 , 0)
        rightFrontLeg.init()
        rightFrontLeg.offsetZ = -7
        rightFrontLeg.offsetX = 4
        rightFrontLeg.offsetY = -16
        rightFrontLeg.rotationPoint.add(0,12,0)
        leftBackLeg = new EntityBox(this,-2, 0, -2, 4, 12, 4, 0, 0)
        leftBackLeg.init()
        leftBackLeg.offsetZ = 6
        leftBackLeg.offsetX = -4
        leftBackLeg.offsetY = -16
        leftBackLeg.rotationPoint.add(0,12,0)
        rightBackLeg = new EntityBox(this,-2, 0, -2, 4, 12, 4, 0, 0)
        rightBackLeg.init()
        rightBackLeg.offsetZ = 6
        rightBackLeg.offsetX = 4
        rightBackLeg.offsetY = -16
        rightBackLeg.rotationPoint.add(0,12,0)
        offsetY = 16
        addChildren(body,head,leftBackLeg,rightBackLeg,leftFrontLeg,rightFrontLeg)
    }


    def setRotationAngles(p_78087_1:Float, p_78087_2: Float, p_78087_3: Float, p_78087_4: Float, p_78087_5: Float, p_78087_6: Float, p_78087_7:Entity):Unit = {
        head.xRot = p_78087_5 / (180F / Math.PI.toFloat)
        head.yRot = p_78087_4 / (180F / Math.PI.toFloat)
        leftFrontLeg.xRot = Math.cos(p_78087_1 * 0.6662F).toFloat * 1.4F * p_78087_2
        rightFrontLeg.xRot = Math.cos(p_78087_1 * 0.6662F + Math.PI.toFloat).toFloat * 1.4F * p_78087_2
        leftBackLeg.xRot = Math.cos(p_78087_1 * 0.6662F + Math.PI.toFloat).toFloat * 1.4F * p_78087_2
        rightBackLeg.xRot = Math.cos(p_78087_1 * 0.6662F).toFloat * 1.4F * p_78087_2
    }

}
