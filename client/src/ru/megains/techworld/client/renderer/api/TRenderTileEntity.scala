package ru.megains.techworld.client.renderer.api

import ru.megains.techworld.client.renderer.gui.inventory.GuiContainer
import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.texture.TTexture
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.tileentity.TileEntity
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

trait TRenderTileEntity extends TRenderTexture {

    def init():Unit

    def render(tileEntity: TileEntity, world: World): Unit

    def getTexture(direction: Direction): TTexture

    def getTexture(blockState: BlockState, direction: Direction, world: World): TTexture

}
