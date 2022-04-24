package ru.megains.techworld.client.renderer.entity

import ru.megains.techworld.client.register.GameRegisterRender
import ru.megains.techworld.client.renderer.MObject
import ru.megains.techworld.client.renderer.api.{TRenderItem, TTextureRegister}
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.common.entity.{Entity, EntityItem}
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.World

object RenderEntityItem extends MObject with TRenderEntity with  Logger{


   //val entityModel = new EntityModelCow()

    val entityDebug:EntityDebug = new EntityDebug(0.25f,0.25f)
    override def init(): Unit = {
      //  entityModel.init()
        scale = 0.25f
        rotationPoint.set(0.125f,0f,0.125f)
    }


    var last = 0.0

    var last1 = 0.0

    var last2 = 0.0
    override def render(entity: Entity, world: World, shader: Shader,partialTicks:Double): Boolean = {

        val entityItem = entity.asInstanceOf[EntityItem]
        val itemStack  = entityItem.itemStack

        val itemRender: TRenderItem =  GameRegisterRender.getItemRender(itemStack.item)

        entityDebug.posX = entity.posX
        entityDebug.posY = entity.posY
        entityDebug.posZ = entity.posZ
        entityDebug.render(shader)
       // xPos = (entity.lastTickPosX + (entity.posX- entity.lastTickPosX)*partialTicks).toFloat
      //  yPos =(entity.lastTickPosY + (entity.posY - entity.lastTickPosY)*partialTicks).toFloat + 1
      //  zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks).toFloat

        val anim2:Float = ((Math.sin(System.currentTimeMillis() % 2000.0 / 2000 * 2 * Math.PI) * 0.1f) + 0.3f).toFloat
        //modelMatrix.translate((position.minX + 0.125f) /16 , position.minY / 16 + anim2 toFloat, (position.minZ + 0.125f)/16)
        //modelMatrix.rotateY( )



        posX = entity.posX -0.125f
        posY = entity.posY + anim2
        posZ =  entity.posZ -0.125f
        yRot = (entity.posX.toInt << 2 & 360) +  /*Math.toRadians*/( System.currentTimeMillis() % 10801.0 / 30).toFloat

        //log.info((System.currentTimeMillis() % 2000.0).toString)
        //yRot = a
        shader.setUniform("modelMatrix", buildMatrix())

        itemRender.renderInWorld(shader)
       // entityModel.entityCube.render()

        //log.info((entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks).toString)

//        if(last-(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks)>0.05){
//            log.info(entity.posZ + " "+ entity.lastTickPosZ )
//        }
//
//        last = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks)
//        last1 = entity.posZ
//        last2 = entity.lastTickPosZ
//        entityModel.posX = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX)*partialTicks).toFloat
//        entityModel.posY = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY)*partialTicks).toFloat
//        entityModel.posZ = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ)*partialTicks).toFloat
//        entityModel.yRot = -(entity.lastRotYaw + (entity.rotYaw - entity.lastRotYaw)*partialTicks).toFloat
//        entityModel.setRotationAngles(a,20,1,1,1,1,entity)

        //  entityModel.render(shader)
        a +=0.01f

        true
    }

    var a  = 0.3f


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        // entityTexture = textureRegister.registerTexture("entity/steve")
    }


}
