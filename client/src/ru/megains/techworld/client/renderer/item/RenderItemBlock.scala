package ru.megains.techworld.client.renderer.item

import ru.megains.techworld.client.register.GameRegisterRender
import ru.megains.techworld.client.renderer.api.{TRenderItem, TTextureRegister}
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureManager}
import ru.megains.techworld.common.block.{Block, BlockAir, BlockState}
import ru.megains.techworld.common.item.ItemBlock
import ru.megains.techworld.common.utils.Direction

class RenderItemBlock(item: ItemBlock) extends TRenderItem {

    lazy val mesh: Mesh = createMesh()

    val block: Block = item.block

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    override def renderInWorld( shader: Shader): Unit = mesh.render()

    override def renderInInventory(): Unit = mesh.render()

    override def getInventoryModel: Model = {
        new Model(mesh) {
            yRot = -45
            xRot = -20
            posX = 20
            posY = 20
        }
    }

    def createMesh(): Mesh = {

        val mm = MeshMaker.startMakeTriangles()

        mm.setTexture(TextureManager.blocksTexture)
        val aabbs = block match {
            case BlockAir => null
            case _ =>
                block.getBoundingBox(new BlockState(block, 0, 0, 0)) //new BlockState(block,0,0,0)//.getBlockBody.div(16)
        }

        //todo
        for (aabb <- aabbs) {

            val maxX: Float = -(aabb.maxX - aabb.minX) / 2 * 25
            val maxY: Float = -(aabb.maxY - aabb.minY) / 2 * 25
            val maxZ: Float = -(aabb.maxZ - aabb.minZ) / 2 * 25
            val minX: Float = (aabb.maxX - aabb.minX) / 2 * 25
            val minY: Float = (aabb.maxY - aabb.minY) / 2 * 25
            val minZ: Float = (aabb.maxZ - aabb.minZ) / 2 * 25



            var minU: Float = 0
            var maxU: Float = 0
            var minV: Float = 0
            var maxV: Float = 0

            var texture: TTexture = null

            val blockRender = GameRegisterRender.getBlockRender(block)

            texture = blockRender.getTexture(Direction.UP)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV

            mm.setCurrentIndex()
            mm.addColor(1,1,1)
            mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addIndex(0, 1, 2)
            mm.addIndex(1, 3, 2)




            texture = blockRender.getTexture(Direction.NORTH)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV



            mm.setCurrentIndex()
            mm.addColor(0.8f, 0.8f, 0.8f)

            mm.addVertexWithUV(minX, minY, minZ, maxU, minV)
            mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
            mm.addVertexWithUV(maxX, minY, minZ, minU, minV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
            mm.addIndex(0, 1, 2)
            mm.addIndex(1, 3, 2)





            texture = blockRender.getTexture(Direction.WEST)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV


            mm.setCurrentIndex()
            mm.addColor(0.5f, 0.5f, 0.5f)
            mm.addVertexWithUV(minX, minY, minZ, minU, minV)
            mm.addVertexWithUV(minX, minY, maxZ, maxU, minV)
            mm.addVertexWithUV(minX, maxY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, maxU, maxV)

            mm.addIndex(0, 1, 2)
            mm.addIndex(1,3, 2)



        }

        mm.make()
    }

    // override def getItemGui: ItemGui = new ItemBlockGui(item)
}
