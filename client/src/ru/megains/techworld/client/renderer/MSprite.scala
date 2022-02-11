package ru.megains.techworld.client.renderer

import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.texture.TTexture

class MSprite() extends Model {

    var _texture: TTexture = _


    def this(texture: TTexture, x: Int = 0, y: Int = 0) = {
        this()
        _texture = texture


        val maxX = if (x > 0) x else texture.width
        val maxY = if (y > 0) y else texture.height
        val minX = 0
        val minY = 0
        val zZero = 0.0f


        var minU: Float = 0
        var maxU: Float = 0
        var minV: Float = 0
        var maxV: Float = 0


        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(texture)

        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, zZero, minU, maxV)
        mm.addVertexWithUV(minX, maxY, zZero, minU, minV)
        mm.addVertexWithUV(maxX, maxY, zZero, maxU, minV)
        mm.addVertexWithUV(maxX, minY, zZero, maxU, maxV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(0, 2, 3)

        mesh = mm.make()
    }

    override def update(): Unit = {
    }
}
