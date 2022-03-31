package ru.megains.techworld.server

import ru.megains.techworld.common.block.{BlockPos, BlockState}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.item.{ItemBlock, ItemPack}
import ru.megains.techworld.common.network.packet.play.server.SPacketBlockChange
import ru.megains.techworld.common.utils.{Direction, RayTraceResult}
import ru.megains.techworld.common.world.World
import ru.megains.techworld.server.entity.EntityPlayerS

class PlayerInteractionManager(world: World) {



    var thisPlayerMP: EntityPlayerS = _
    var blockReachDistance: Double = 5.0d *16
    //var gameType: GameType = GameType.CREATIVE
    var isDestroyingBlock: Boolean = false

//    def setGameType(gameTypeIn: GameType): Unit = {
//        gameType = gameTypeIn
//
//    }






   // def isCreative: Boolean = gameType.isCreative

    def onBlockClicked(pos: BlockPos, side: Direction): Unit = {

      /* // if (isCreative)*/  tryHarvestBlock(pos)

    }

    def tryHarvestBlock(pos: BlockPos): Boolean = {

        var flag1: Boolean = false
       // if (isCreative) {
            flag1 = removeBlock(pos)
             thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, pos))
       // }

        flag1

    }

    private def removeBlock(pos: BlockPos): Boolean = removeBlock(pos, canHarvest = false)

    private def removeBlock(pos: BlockPos, canHarvest: Boolean): Boolean = {
         val block: BlockState = world.getBlock(pos.x,pos.y,pos.z)
         val flag: Boolean = block.removedByPlayer(world, thisPlayerMP, canHarvest)
         if (flag) block.onBlockDestroyedByPlayer(world, pos)
         flag

    }


    def getBlockReachDistance: Double = blockReachDistance




    def processRightClickBlock(rayTrace: RayTraceResult,blockState:BlockState): Unit = {


        val itemstack: ItemStack = thisPlayerMP.getHeldItem
        val block: BlockState = world.getBlock(rayTrace.blockState.x,rayTrace.blockState.y,rayTrace.blockState.z)

        if (block.onBlockActivated(world,  thisPlayerMP, itemstack, rayTrace.sideHit, rayTrace.hitVec.x.toFloat, rayTrace.hitVec.y.toFloat, rayTrace.hitVec.z.toFloat)){

        }else{
            if(itemstack!= null) {
                itemstack.item match {
                    case _: ItemBlock =>
                        if (blockState != null) {
                            itemstack.onItemUse(thisPlayerMP, world, blockState, rayTrace.sideHit, rayTrace.hitVec.x.toFloat, rayTrace.hitVec.y.toFloat, rayTrace.hitVec.z.toFloat)
                            thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, new BlockPos(blockState.x,blockState.y,blockState.z) ))
                        }

                    case _ =>
                }
            }

        }


    }
}

