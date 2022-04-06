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


    val entityModel = new EntityModelPlayer()



    override def init(): Unit = {
        entityModel.init()

    }

    override def render(entity: Entity, world: World, shader: Shader,partialTicks:Double): Boolean = {

        zPos = entity.posZ
        xPos = entity.posX
        yPos = entity.posY
        shader.setUniform("modelMatrix", buildViewMatrix())
        entityModel.entityCube.render()

        entityModel.posX = entity.posX
        entityModel.posY = entity.posY
        entityModel.posZ = entity.posZ
        entityModel.yRot = -entity.rotYaw


        entityModel.render(shader)
//        entityModel.entityHead.render(shader)
//        entityModel.leftArm.render(shader)
//        entityModel.rightArm.render(shader)
//        entityModel.rightLeg.render(shader)
//        entityModel.leftLeg.render(shader)
        entityModel.leftArm.xRot += a
        entityModel.rightArm.xRot -= a

        true
    }

    var a = 0.3f


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        // entityTexture = textureRegister.registerTexture("entity/steve")
    }
}
