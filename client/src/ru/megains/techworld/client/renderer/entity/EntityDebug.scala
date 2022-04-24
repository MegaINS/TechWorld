package ru.megains.techworld.client.renderer.entity

import org.lwjgl.opengl.GL11.GL_LINES
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.model.{Model, TModel}
import ru.megains.techworld.common.physics.BoundingBox

import java.awt.Color

class EntityDebug(wight: Float, height: Float) extends Model{

    override var mesh: Mesh = create()

    def create(): Mesh ={
        val mm = MeshMaker.startMake(GL_LINES)
        val aabb: BoundingBox = new BoundingBox(-wight/2, 0, -wight/2, wight/2, height, wight/2)

        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ


        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        mm.make()
    }
}
