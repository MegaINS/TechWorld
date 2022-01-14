package ru.megains.techworld.renderer.camera

import org.joml.Matrix4f

abstract class Camera {

    protected val projectionMatrix: Matrix4f  = new Matrix4f()

    protected val viewMatrix: Matrix4f = new Matrix4f()

    var xPos:Float = 0.0F
    var yPos:Float = 0.0F
    var zPos:Float = 0.0F
    var scale:Float = 1.0F
    var xRot:Float = 0.0F
    var yRot:Float = 0.0F
    var zRot:Float = 0.0F

    def buildProjectionMatrix(): Matrix4f

    def buildViewMatrix(): Matrix4f ={
        viewMatrix.identity
        viewMatrix.rotateXYZ(Math.toRadians(xRot).toFloat, Math.toRadians(yRot).toFloat, Math.toRadians(zRot).toFloat)
        viewMatrix.translate(-xPos, -yPos, -zPos)
        viewMatrix.scale(scale)
    }

    def setPos(xPosIn: Float,yPosIn: Float,zPosIn: Float): Unit ={
        xPos = xPosIn
        yPos = yPosIn
        zPos = zPosIn
    }
    def changePos(xPosIn: Float,yPosIn: Float,zPosIn: Float): Unit ={
        xPos += xPosIn
        yPos += yPosIn
        zPos += zPosIn
    }


    def setRot(xRotIn: Float,yRotIn: Float,zRotIn: Float): Unit ={
        xRot = xRotIn
        yRot = yRotIn
        zRot = zRotIn
    }

    def setScale(scaleIn: Float): Unit ={
        scale = scaleIn
    }
}
