package ru.megains.techworld.common.tileentity

import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.container.{Container, ContainerChest}
import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.utils.Logger


class TileEntityChest(pos:BlockState) extends TileEntityInventory(pos,40) with Logger{

    log.info("Set tile entity")
    println(pos.x)
    println(pos.y)
    println(pos.z)

    override def getContainer(player: EntityPlayer): Container = new ContainerChest(player.inventory,this)
}
