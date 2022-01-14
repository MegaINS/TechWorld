package ru.megains.techworld.renderer.texture

import org.lwjgl.opengl.GL11


abstract class TTexture() {

    protected var glTextureId: Int = -1

    var minV: Float = 0
    var maxV: Float = 1
    var minU: Float = 0
    var maxU: Float = 1
    var averageV: Float = 0.5f
    var averageU: Float = 0.5f
    var width: Int = -1
    var height: Int = -1
    var components: Int = -1

    def getGlTextureId: Int = {
        if (glTextureId == -1) {
            glTextureId = GL11.glGenTextures
        }
        glTextureId
    }

    def deleteGlTexture(): Unit = {
        if (glTextureId != -1) {
            GL11.glDeleteTextures(glTextureId)
            glTextureId = -1
        }
    }

    def loadTexture():Boolean


}
