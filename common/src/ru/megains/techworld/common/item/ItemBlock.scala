package ru.megains.techworld.common.item

import ru.megains.techworld.common.block.Block

class ItemBlock(val block: Block) extends ItemPack(block.name) {


//    override def onItemUse(stack: ItemPack, playerIn: EntityPlayer, worldIn: World, pos: BlockState, facing: Direction, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
//
//        if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ)) {
//           // stack.stackSize -= 1
//        }
//        EnumActionResult.SUCCESS
//    }
//
//    def placeBlockAt(stack: ItemPack, player: EntityPlayer, world: World, pos: BlockState, side: Direction, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
//        if(pos == null) return false
//        if (!world.setBlock(pos)) return false
//
//        true
//    }

}
