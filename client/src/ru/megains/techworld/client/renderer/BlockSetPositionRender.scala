package ru.megains.techworld.client.renderer

import org.lwjgl.opengl.GL11._
import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.common.block.BlockState

import java.awt.Color

class BlockSetPositionRender {

    var blockSet: Model = new Model()
    var blockStateOld: BlockState = _

    def update(blockState: BlockState): Unit = {
        if (blockState != null && blockState != blockStateOld) {
            blockSet.posX = blockState.x
            blockSet.posY = blockState.y
            blockSet.posZ = blockState.z
            val mm = MeshMaker.startMake(GL_LINES)

            val aabbs = blockState.getBoundingBox

            for (aabb <- aabbs) {
                val minX = aabb.minX
                val minY = aabb.minY
                val minZ = aabb.minZ
                val maxX = aabb.maxX
                val maxY = aabb.maxY
                val maxZ = aabb.maxZ


                mm.setCurrentIndex()
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

            }
            blockSet.mesh = mm.make()


        } else {
            if (blockSet.mesh != null) {
                blockSet.mesh.cleanUp()
                blockSet.mesh = null
            }
        }

    }

    def render(shader: Shader): Unit = {
        if (blockSet.mesh != null) {
            blockSet.render(shader)
        }
    }

    def cleanUp(): Unit ={

        blockSet.cleanUp()
    }
}
