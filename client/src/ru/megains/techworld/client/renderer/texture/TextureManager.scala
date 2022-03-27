package ru.megains.techworld.client.renderer.texture


import org.lwjgl.opengl.GL11
import org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load
import ru.megains.techworld.client.register.GameRegisterRender

import scala.collection.mutable


object TextureManager {

    val blocksTexture: TextureMap = new TextureMap("texture/blocks.png","block")
   // val entityTexture: TextureMap = new TextureMap("texture/entity.png")
    val mapTTexture: mutable.HashMap[String, TTexture] = new mutable.HashMap[String, TTexture]
    var currentTexture: TTexture = _
    val missingTexture: TTexture = TextureManager.getTexture("textures/missing.png")
    val baseTexture: TTexture =  TextureManager.getTexture("textures/base.png")


    def init(): Unit = {
        stbi_set_flip_vertically_on_load(true)
        blocksTexture.registerTexture( GameRegisterRender.blockData.idRender.values.toList)
        blocksTexture.registerTexture( GameRegisterRender.itemData.idRender.values.toList)

        GameRegisterRender.entityData.idRender.values.foreach(_.init())
       // blocksTexture.registerTexture( GameRegisterRender.itemData.idRender.values.toList)
        loadTexture(blocksTexture.name, TextureManager.blocksTexture)
       // entityTexture.registerTexture( GameRegisterRender.entityData.idRender.values.toList)
       // entityTexture.loadTextureAtlas()

       // loadTexture( entityTexture.name,  entityTexture)

    }




    def bindTexture(texture: TTexture): Unit = {

        if (texture != currentTexture) {
            currentTexture = texture
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture.getGlTextureId)
        }
    }

    def unbindTexture(): Unit = {
        currentTexture = null
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }


    def loadTexture(name: String, aTexture: TTexture): Boolean = {

        if (aTexture.loadTexture()) {
            mapTTexture += name -> aTexture
            true
        } else {
            println("Error load texture " + name)
            mapTTexture += name -> missingTexture
            false
        }

    }


    def getTexture(name: String): TTexture = {
        mapTTexture.getOrElse(name, default = {
            val aTexture = new Texture(name)
            loadTexture(name, aTexture)
            aTexture
        })
    }

}



