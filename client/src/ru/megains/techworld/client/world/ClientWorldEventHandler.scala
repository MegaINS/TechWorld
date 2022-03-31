package ru.megains.techworld.client.world

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.world.{IWorldEventListener, World}


class ClientWorldEventHandler(game: TechWorld, world: WorldClient) extends IWorldEventListener {
    override def notifyBlockUpdate(worldIn: World, pos: BlockState): Unit = {
        game.rendererGame.rendererWorld.reRender(pos.x,pos.y,pos.z)
     }
}
