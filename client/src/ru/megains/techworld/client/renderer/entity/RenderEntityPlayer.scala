package ru.megains.techworld.client.renderer.entity

import org.lwjgl.opengl.GL11._
import ru.megains.techworld.client.renderer.api.TTextureRegister
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.model.TModel
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.texture.{TTexture, Texture, TextureManager}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.physics.BoundingBox
import ru.megains.techworld.common.world.World

import java.awt.Color


object RenderEntityPlayer extends TRenderEntity with TModel {


    var entityCube: Mesh = _
    var entityTexture: TTexture = _
    var entityHead: EntityBox = _
    var entityTors: EntityBox = _

    var leftArm: EntityBox = _
    var rightArm: EntityBox = _

    var leftLeg: EntityBox = _
    var rightLeg: EntityBox = _

    override def init(): Unit = {
        entityTexture =  TextureManager.getTexture("textures/entity/steve.png")
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

        entityTors = new EntityBox(-0.2f, 0f, -0.1f, 0.4f, 0.6f, 0.2f, entityTexture, 0.25f, 0f, 24 / 64f, 0.5f)
        entityTors.init()

        entityHead = new EntityBox(-0.2f, 0f, -0.2f, 0.4f, 0.4f, 0.4f, entityTexture, 0, 0.5f, 0.5f, 0.5f)
        entityHead.init()

        entityHead.parent = entityTors
        entityHead.posY = 0.6f

        leftArm = new EntityBox(-0.1f, 0f, -0.1f, 0.2f, 0.6f, 0.2f, entityTexture, 40 / 64f, 0f, 16 / 64f, 0.5f)
        leftArm.init()
        leftArm.parent = entityTors
        leftArm.posX = -0.3f

        leftArm.rotationPoint.y = 0.5f

        rightArm = new EntityBox(-0.1f, 0f, -0.1f, 0.2f, 0.6f, 0.2f, entityTexture, 40 / 64f, 0f, 16 / 64f, 0.5f)
        rightArm.init()
        rightArm.parent = entityTors
        rightArm.posX = 0.3f
        rightArm.rotationPoint.y = 0.5f

        leftLeg = new EntityBox(-0.1f, 0f, -0.1f, 0.2f, 0.6f, 0.2f, entityTexture, 0f, 0f, 16 / 64f, 0.5f)
        leftLeg.init()
        leftLeg.parent = entityTors
        leftLeg.posX = -0.1f
        leftLeg.posY = -0.6f
        rightLeg = new EntityBox(-0.1f, 0f, -0.1f, 0.2f, 0.6f, 0.2f, entityTexture, 0f, 0f, 16 / 64f, 0.5f)
        rightLeg.init()
        rightLeg.parent = entityTors
        rightLeg.posX = 0.1f
        rightLeg.posY = -0.6f

    }

    override def render(entity: Entity, world: World, shader: Shader): Boolean = {

        zPos = entity.posZ
        xPos = entity.posX
        yPos = entity.posY
        shader.setUniform("modelMatrix", buildViewMatrix())
        entityCube.render()

        entityTors.yRot = -entity.rotYaw
        entityTors.posX = entity.posX
        entityTors.posY = entity.posY + 0.6f
        entityTors.posZ = entity.posZ
        entityTors.render(shader)
        entityHead.render(shader)
        leftArm.render(shader)
        rightArm.render(shader)
        rightLeg.render(shader)
        leftLeg.render(shader)
        leftArm.xRot += a
        rightArm.xRot -= a

        true
    }

    var a = 0.3f


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        // entityTexture = textureRegister.registerTexture("entity/steve")
    }
}
