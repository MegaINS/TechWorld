package ru.megains.techworld.client.renderer.model

import org.joml.Matrix4f

trait TModel {

    protected val viewMatrix: Matrix4f = new Matrix4f()

    var xPos:Float = 0.0F
    var yPos:Float = 0.0F
    var zPos:Float = 0.0F
    var scale:Float = 1.0F
    var xRot:Float = 0.0F
    var yRot:Float = 0.0F
    var zRot:Float = 0.0F

   def buildViewMatrix(): Matrix4f ={
       viewMatrix.identity
       viewMatrix.translate(xPos, yPos, zPos)
       viewMatrix.rotateXYZ(Math.toRadians(xRot).toFloat, Math.toRadians(yRot).toFloat, Math.toRadians(zRot).toFloat)
       viewMatrix.scale(scale)
   }

}
