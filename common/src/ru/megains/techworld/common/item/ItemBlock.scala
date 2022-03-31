package ru.megains.techworld.common.item

import ru.megains.techworld.common.block.{Block, BlockState}
import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.utils.{Direction, EnumActionResult}
import ru.megains.techworld.common.utils.EnumActionResult.EnumActionResult
import ru.megains.techworld.common.world.World

class ItemBlock(val block: Block) extends ItemPack(block.name) {


    override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockState, facing: Direction, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {

        if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ)) {
           // stack.stackSize -= 1
        }
        EnumActionResult.SUCCESS
    }

    def placeBlockAt(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockState, side: Direction, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
        if(pos == null) return false
        world.setBlock(pos)
     //   if (!world.setBlock(pos)) return false

        true
    }

}
