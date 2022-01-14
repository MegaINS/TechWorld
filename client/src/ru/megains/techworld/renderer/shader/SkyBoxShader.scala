package ru.megains.techworld.renderer.shader

import ru.megains.techworld.renderer.camera.Camera
import ru.megains.techworld.renderer.shader.data.Shader

private class SkyBoxShader extends Shader{


    override val dir: String = "shaders/skybox"


    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("viewMatrix" )
        createUniform("modelMatrix")
        createUniform("ambientLight")
       // createUniform("dataTC")
    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
        val viewMatrix = camera.buildViewMatrix()
        viewMatrix.m30(0)
        viewMatrix.m31(0)
        viewMatrix.m32(0)

        setUniform("viewMatrix",viewMatrix)

    }
}
