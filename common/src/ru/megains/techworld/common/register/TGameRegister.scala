package ru.megains.techworld.common.register

import ru.megains.techworld.common.block.Block
import ru.megains.techworld.common.entity.Entity

trait TGameRegister {

    def registerBlock(id: Int, block: Block): Boolean
   // def registerItem(id: Int, item: Item): Boolean
  //  def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean
}
