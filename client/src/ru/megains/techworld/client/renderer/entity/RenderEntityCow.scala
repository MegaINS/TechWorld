package ru.megains.techworld.client.renderer.entity

import org.lwjgl.opengl.GL11.GL_LINES
import ru.megains.techworld.client.renderer.api.TTextureRegister
import ru.megains.techworld.client.renderer.entity.RenderEntityPlayer.{buildViewMatrix, entityModel, xPos, yPos, zPos}
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.model.TModel
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureManager}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.physics.BoundingBox
import ru.megains.techworld.common.world.World

import java.awt.Color

object RenderEntityCow extends TRenderEntity with TModel {


    val entityModel = new EntityModelCow()

    override def init(): Unit = {
        entityModel.init()
    }


    override def render(entity: Entity, world: World, shader: Shader,partialTicks:Double): Boolean = {


        xPos = (entity.lastTickPosX + (entity.posX- entity.lastTickPosX)*partialTicks).toFloat
        yPos =(entity.lastTickPosY + (entity.posY - entity.lastTickPosY)*partialTicks).toFloat
        zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks).toFloat

        shader.setUniform("modelMatrix", buildViewMatrix())
        entityModel.entityCube.render()


        entityModel.posX = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX)*partialTicks).toFloat
        entityModel.posY = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY)*partialTicks).toFloat
        entityModel.posZ = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks).toFloat
        entityModel.yRot = -entity.rotYaw

        entityModel.setRotationAngles(a,20,1,1,1,1,entity)

        entityModel.render(shader)
        a +=0.001f

        true
    }

    var a  = 0.3f


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        // entityTexture = textureRegister.registerTexture("entity/steve")
    }


}
