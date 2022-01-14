package ru.megains.techworld.renderer.texture

import org.lwjgl.opengl.GL11

import scala.collection.mutable


object TextureManager {

    val mapTTexture: mutable.HashMap[String, TTexture] = new mutable.HashMap[String, TTexture]
    var currentTexture: TTexture = _
    val missingTexture = new Texture("textures/missing.png")
    val baseTexture = new Texture("textures/base.png")


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



