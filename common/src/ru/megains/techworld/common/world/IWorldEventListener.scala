package ru.megains.techworld.common.world

import ru.megains.techworld.common.block.BlockState

trait IWorldEventListener {

    def notifyBlockUpdate(worldIn: World, pos: BlockState): Unit
}
