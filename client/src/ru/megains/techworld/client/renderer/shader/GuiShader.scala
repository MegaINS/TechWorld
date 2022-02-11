package ru.megains.techworld.client.renderer.shader

import ru.megains.techworld.client.renderer.camera.Camera
import ru.megains.techworld.client.renderer.shader.data.Shader

private class GuiShader extends Shader {

    override val dir: String = "shaders/2d"

    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("modelMatrix")
    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
    }

}