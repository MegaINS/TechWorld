package ru.megains.techworld.client.renderer.shader

import ru.megains.techworld.client.renderer.camera.Camera
import ru.megains.techworld.client.renderer.shader.data.Shader


private class WorldShader extends Shader {


    override val dir: String = "shaders/basic"

    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("viewMatrix" )
        createUniform("modelMatrix")
    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
        setUniform("viewMatrix", camera.buildViewMatrix())
    }

}
