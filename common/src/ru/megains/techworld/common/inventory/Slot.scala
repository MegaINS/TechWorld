package ru.megains.techworld.common.inventory

import ru.megains.techworld.common.item.itemstack.ItemStack

class Slot(inventory: Inventory, val index: Int, val xPos: Int, val yPos: Int) {

    var slotNumber: Int = 0

    def isEmpty: Boolean = getStack == null

    def getStack: ItemStack = inventory.getStackInSlot(index)

    def putStack(itemStack: ItemStack): Unit = {
        inventory.setInventorySlotContents(index, itemStack)
    }

    def decrStackSize(size: Int): ItemStack = {
        inventory.decrStackSize(index, size)
    }
}
