package ru.megains.techworld.common.tileentity

import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.world.World

trait TileEntityContainer {

    def createNewTileEntity(worldIn: World,blockState: BlockState): TileEntity
}
