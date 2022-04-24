package ru.megains.techworld.client.renderer.item

import ru.megains.techworld.client.register.GameRegisterRender
import ru.megains.techworld.client.renderer.api.{TRenderItem, TTextureRegister}
import ru.megains.techworld.client.renderer.block.RenderBlock
import ru.megains.techworld.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureAtlas, TextureManager}
import ru.megains.techworld.common.block.{Block, BlockAir, BlockState}
import ru.megains.techworld.common.item.ItemBlock
import ru.megains.techworld.common.register.GameRegister
import ru.megains.techworld.common.utils.Direction

class RenderItemBlock(item: ItemBlock) extends TRenderItem {

    lazy val mesh: Mesh = createMesh()

    val block: Block = item.block

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    override def renderInWorld( shader: Shader): Unit = mesh.render()

    override def renderInInventory(): Unit = mesh.render()

    override def getInventoryModel: Model = {
        new Model(mesh) {
            yRot = 45
            xRot = 200
            posX = 2.5f
            posY = 31
            scale = 25
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
        for(aabb<- aabbs){

            val maxX: Float = aabb.maxX
            val maxY: Float = aabb.maxY
            val maxZ: Float = aabb.maxZ
            val minX: Float = aabb.minX
            val minY: Float = aabb.minY
            val minZ: Float = aabb.minZ

            var texture: TTexture = null

            val blockRender = GameRegisterRender.getBlockRender(block)



            texture = blockRender.getTexture(Direction.UP)
            RenderBlock.renderSideUp(mm, minX, maxX, maxY, minZ, maxZ,texture)



            texture = blockRender.getTexture(Direction.DOWN)
            RenderBlock.renderSideDown(mm, minX, maxX, minY, minZ, maxZ,texture)




            texture = blockRender.getTexture(Direction.NORTH)
            RenderBlock.renderSideNorth(mm, minX, maxX, minY, maxY, minZ,texture)



            texture = blockRender.getTexture(Direction.SOUTH)
            RenderBlock.renderSideSouth(mm, minX, maxX, minY, maxY, maxZ,texture)



            texture = blockRender.getTexture(Direction.WEST)
            RenderBlock.renderSideWest(mm, minX, minY, maxY, minZ, maxZ,texture)



            texture = blockRender.getTexture(Direction.EAST)
            RenderBlock.renderSideEast(mm, maxX, minY, maxY, minZ, maxZ,texture)



        }

        mm.make()
    }

    // override def getItemGui: ItemGui = new ItemBlockGui(item)
}
