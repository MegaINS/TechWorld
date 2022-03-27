package ru.megains.techworld.client.renderer.entity

import org.joml.Matrix4f
import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.texture.TTexture

abstract class EntityModel extends MContainer{

    var texture: TTexture = _
    var offsetX = 0
    var offsetY = 0
    var offsetZ = 0

    def init(): Unit



    override def buildMatrix(): Matrix4f = {
        matrix.identity
        // matrix.translate(rotationPoint)
        matrix.translate(posX, posY, posZ)
        matrix.translate(offsetX/16f, offsetY/16f, offsetZ/16f)
        matrix.translate(rotationPoint.x/16f,rotationPoint.y/16f,rotationPoint.z/16f)
        matrix.rotateXYZ(Math.toRadians(xRot).toFloat, Math.toRadians(yRot).toFloat, Math.toRadians(zRot).toFloat)
        matrix.translate(-rotationPoint.x/16f,-rotationPoint.y/16f,-rotationPoint.z/16f)

        //matrix.translate(rotationPoint)
        matrix.scale(scale)

        parent match {
            case null =>
                matrix
            case _ =>
                parent.buildMatrix().mul(matrix)
        }
    }
}
