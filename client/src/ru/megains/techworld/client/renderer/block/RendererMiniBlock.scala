package ru.megains.techworld.client.renderer.block

import ru.megains.techworld.client.renderer.api.{TRenderBlock, TTextureRegister}
import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.texture.TTexture
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

class RendererMiniBlock(name: String) extends TRenderBlock {
        var texture: TTexture = _


        override def registerTexture(textureRegister: TTextureRegister): Unit = {
            texture = textureRegister.registerTexture(name)
        }


        override def render(mm: MeshMaker , blockStateIn: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit = {

            //todo head.
            val box = blockStateIn.getBoundingBox.head.sum(xRIn, yRIn , zRIn )


           // if (!world.getCell(blockState.x - 16, blockState.y, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideWest(mm,box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockStateIn,Direction.WEST,world))
           // }

           // if (!world.getCell(blockState.x + 16, blockState.y, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideEast(mm,box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockStateIn,Direction.EAST,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y, blockState.z - 16).isOpaqueCube) {
                RenderBlock.renderSideNorth(mm,box.minX, box.maxX, box.minY, box.maxY, box.minZ, getTexture(blockStateIn,Direction.NORTH,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y, blockState.z + 16).isOpaqueCube) {
                RenderBlock.renderSideSouth(mm,box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getTexture(blockStateIn,Direction.SOUTH,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y - 16, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideDown(mm,box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getTexture(blockStateIn,Direction.DOWN,world))
           // }

           // if (!world.getCell(blockState.x, blockState.y + 16, blockState.z).isOpaqueCube) {
                RenderBlock.renderSideUp(mm,box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getTexture(blockStateIn,Direction.UP,world))
           // }
        }

        override def getTexture(direction: Direction): TTexture = texture

        override def getTexture(blockState: BlockState, direction: Direction, world: World): TTexture = texture
}
