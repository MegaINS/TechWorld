package ru.megains.techworld.server.world

import ru.megains.techworld.common.block.{BlockPos, BlockState}
import ru.megains.techworld.common.world.{IWorldEventListener, World}
import ru.megains.techworld.server.TWServer

class ServerWorldEventHandler(server: TWServer, world: WorldServer) extends IWorldEventListener {
    override def notifyBlockUpdate(worldIn: World, pos: BlockState): Unit = {

        world.playerManager.markBlockForUpdate(new BlockPos(pos.x,pos.y,pos.z))

    }
}
