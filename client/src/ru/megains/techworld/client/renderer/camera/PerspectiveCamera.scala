package ru.megains.techworld.client.renderer.camera

import org.joml.Matrix4f

class PerspectiveCamera extends Camera {

    var fov: Float = 0.0F
    var width: Float = 0.0F
    var height: Float = 0.0F
    var zNear: Float = 0.0F
    var zFar: Float = 0.0F

    def this(fovIn: Float, widthIn: Float, heightIn: Float, zNearIn: Float, zFarIn: Float)={
        this()
        setPerspective(fovIn, widthIn, heightIn, zNearIn, zFarIn)
    }

    override def buildProjectionMatrix(): Matrix4f = {
        val aspectRatio = width / height
        projectionMatrix.identity
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar)
    }

    def setPerspective(fovIn: Float, widthIn: Float, heightIn: Float, zNearIn: Float, zFarIn: Float): Unit ={
        fov = fovIn
        width = widthIn
        height = heightIn
        zNear = zNearIn
        zFar = zFarIn
    }
}
