package ru.megains.techworld.client.renderer.block

import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.common.block.{BlockGlass, BlockState}
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

class RenderBlockGlass(name:String) extends RenderBlockStandard(name) {

   


    override def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit = {

        //todo head.
        val box = blockState.getBoundingBox.head.sum(xRIn, yRIn, zRIn)

        if (!world.getBlock(blockState.x - 1, blockState.y, blockState.z).block.isInstanceOf[BlockGlass]) {
            RenderBlock.renderSideWest(mm, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.WEST, world))
        }

        if (!world.getBlock(blockState.x + 1, blockState.y, blockState.z).block.isInstanceOf[BlockGlass]) {
            RenderBlock.renderSideEast(mm, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.EAST, world))
        }

        if (!world.getBlock(blockState.x, blockState.y, blockState.z - 1).block.isInstanceOf[BlockGlass]) {
            RenderBlock.renderSideNorth(mm, box.minX, box.maxX, box.minY, box.maxY, box.minZ, getTexture(blockState, Direction.NORTH, world))
        }

        if (!world.getBlock(blockState.x, blockState.y, blockState.z + 1).block.isInstanceOf[BlockGlass]) {
            RenderBlock.renderSideSouth(mm, box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getTexture(blockState, Direction.SOUTH, world))
        }

        if (!world.getBlock(blockState.x, blockState.y - 1, blockState.z).block.isInstanceOf[BlockGlass]) {
            RenderBlock.renderSideDown(mm, box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getTexture(blockState, Direction.DOWN, world))
        }

        if (!world.getBlock(blockState.x, blockState.y + 1, blockState.z).block.isInstanceOf[BlockGlass]) {
            RenderBlock.renderSideUp(mm, box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.UP, world))
        }
    }

   
}
