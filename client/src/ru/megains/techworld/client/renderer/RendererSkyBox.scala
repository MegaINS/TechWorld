package ru.megains.techworld.client.renderer

import org.joml.Vector3f
import org.lwjgl.opengl.GL11._
import ru.megains.techworld.client.renderer.camera.Camera
import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.shader.ShaderManager
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.texture.{TTexture, Texture, TextureManager}

class RendererSkyBox {

    var skyBoxShader: Shader = ShaderManager.skyBoxShader
    var skybox:Model = _
    val skyboxTexture: TTexture = TextureManager.getTexture("textures/skybox/Daylight Box UV_0.png")

    def init(): Unit ={
        val minX = -3
        val maxX = 3
        val minY = -3
        val maxY = 3
        val minZ = -3
        val maxZ = 3

        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(skyboxTexture)

       //  RenderBlock.renderSideUp(mm,0,100,100,0,100,skyboxTexture)

        var minU = 0.25f
        var maxU = 0.5f
        var minV = 2f/3f
        var maxV = 1f

        //  //mm.addNormals(0, 1, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, maxY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)


        minU = 0f
        maxU = 0.25f
        minV = 2f/3f
        maxV = 1f/3f

        ////mm.addNormals(-1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)

        mm.addIndex(0, 2, 1)
        mm.addIndex(1,2, 3)




        minU = 0.5f
        maxU = 0.75f

        //mm.addNormals(1, 0, 0)
        mm.setCurrentIndex()
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)

        minU = 0.75f
        maxU = 1f

        //mm.addNormals(0, 0, 1)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, maxZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)

        mm.addIndex(0,1, 2)
        mm.addIndex(1, 3, 2)


        minU = 0.25f
        maxU = 0.5f
        //mm.addNormals(0, 0, -1)
        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)

        minU = 0.25f
        maxU = 0.5f
        minV = 0f
        maxV = 1f/3f
        // //mm.addNormals(0, -1, 0)
        mm.setCurrentIndex()

        mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, minV)
        mm.addIndex(0, 1,2)
        mm.addIndex(1, 3, 2)


        skybox = new Model(mm.make())
    }

    def render(worldCamera:Camera): Unit ={
        ShaderManager.bindShader(skyBoxShader)
        skyBoxShader.setUniform(worldCamera)
        glEnable(GL_CULL_FACE)
       // glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glDisable(GL_DEPTH_TEST)

//
        skyBoxShader.setUniform("ambientLight",new Vector3f(1,1,1))
        skybox.render(skyBoxShader)


        ShaderManager.unbindShader()



    }

    def cleanUp(): Unit ={
        skybox.cleanUp()
    }
}
