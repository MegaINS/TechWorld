package ru.megains.techworld.common.block

import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.tileentity.TileEntityContainer
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

abstract class BlockContainer(name:String) extends Block(name) with TileEntityContainer{

    override def isOpaqueCube: Boolean = false

    override def onBlockActivated( pos: BlockState,world: World, player: EntityPlayer, itemStack: ItemStack, blockDirection:Direction, float1: Float, float2: Float, float3: Float): Boolean = {
        if (world.getTileEntity(pos.x,pos.y,pos.z) == null) {
            false
        }else{
            player.setContainer(world, pos.x,pos.y,pos.z)
            true
        }
   }

}
