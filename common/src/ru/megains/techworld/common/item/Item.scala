package ru.megains.techworld.common.item

import ru.megains.techworld.common.item.ItemType.ItemType


abstract class Item(val name: String,val itemType: ItemType) {


    var maxStackSize: Int = 64
    val mass:Int = 1

   // def onItemUse(stack: ItemPack, playerIn: EntityPlayer, worldIn: World, pos : BlockState, facing: Direction, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

}

object Item {


//    def getItemById(id: Int): Item = GameRegister.getItemById(id)
//
//    def getItemFromBlock(block: Block): Item = GameRegister.getItemFromBlock(block)
//
//    def getIdFromItem(item: Item): Int = {
//        GameRegister.getIdByItem(item)
//    }



}
