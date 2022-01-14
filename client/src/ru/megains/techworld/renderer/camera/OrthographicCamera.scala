package ru.megains.techworld.renderer.camera

import org.joml.Matrix4f


class OrthographicCamera() extends Camera {


    var left:Float = 0.0F
    var right:Float = 0.0F
    var bottom:Float = 0.0F
    var top:Float = 0.0F
    var zNear:Float = 0.0F
    var zFar:Float = 0.0F


    def this(leftIn: Float,rightIn: Float,bottomIn: Float,topIn: Float,zNearIn: Float,zFarIn: Float)={
        this()
        setOrtho(leftIn,rightIn,bottomIn,topIn,zNearIn,zFarIn)
    }

    override def buildProjectionMatrix(): Matrix4f ={
        projectionMatrix.identity
        projectionMatrix.setOrtho(left, right, bottom, top, zNear, zFar)
    }

    def setOrtho(leftIn: Float,rightIn: Float,bottomIn: Float,topIn: Float,zNearIn: Float,zFarIn: Float): Unit ={
        left = leftIn
        right = rightIn
        bottom = bottomIn
        top = topIn
        zNear = zNearIn
        zFar = zFarIn
    }



}
