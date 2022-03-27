package ru.megains.techworld.common.inventory

import ru.megains.techworld.common.item.itemstack.ItemStack

trait Inventory {

    def getStackInSlot(index: Int): ItemStack

    def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit

    def decrStackSize(index: Int, size:Int): ItemStack

   // def writeToNBT(data: NBTCompound): Unit

    //def readFromNBT(data: NBTCompound): Unit

}
