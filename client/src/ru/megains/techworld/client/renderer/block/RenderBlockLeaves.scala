package ru.megains.techworld.client.renderer.block

import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

import java.awt.Color

class RenderBlockLeaves(name:String) extends RenderBlockStandard(name) {


    override def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit = {

        //todo head.
        val box = blockState.getBoundingBox.head.sum(xRIn, yRIn, zRIn)
        val color: Color = new Color(0,128,0)

        

        RenderBlock.renderSideWest(mm, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.WEST, world),color)
        RenderBlock.renderSideWest(mm, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.WEST, world),color)



        RenderBlock.renderSideEast(mm, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.EAST, world),color)
        RenderBlock.renderSideEast(mm, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.EAST, world),color)


        RenderBlock.renderSideNorth(mm, box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getTexture(blockState, Direction.NORTH, world),color)
        RenderBlock.renderSideNorth(mm, box.minX, box.maxX, box.minY, box.maxY, box.minZ, getTexture(blockState, Direction.NORTH, world),color)


        RenderBlock.renderSideSouth(mm, box.minX, box.maxX, box.minY, box.maxY, box.minZ, getTexture(blockState, Direction.SOUTH, world),color)
        RenderBlock.renderSideSouth(mm, box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getTexture(blockState, Direction.SOUTH, world),color)


        RenderBlock.renderSideDown(mm, box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.DOWN, world),color)
        RenderBlock.renderSideDown(mm, box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getTexture(blockState, Direction.DOWN, world),color)


        RenderBlock.renderSideUp(mm, box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getTexture(blockState, Direction.UP, world),color)
        RenderBlock.renderSideUp(mm, box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getTexture(blockState, Direction.UP, world),color)

    }
}
