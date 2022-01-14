package ru.megains.techworld.renderer.shader

import ru.megains.techworld.renderer.camera.Camera
import ru.megains.techworld.renderer.shader.data.Shader

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