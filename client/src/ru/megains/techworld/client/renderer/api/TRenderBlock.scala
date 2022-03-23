package ru.megains.techworld.client.renderer.api

import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.texture.TTexture
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

trait TRenderBlock extends TRenderTexture{

    def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit

    def getTexture(direction: Direction): TTexture

    def getTexture(blockState: BlockState, direction: Direction, world: World): TTexture
}
