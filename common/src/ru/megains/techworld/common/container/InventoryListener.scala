package ru.megains.techworld.common.container

import ru.megains.techworld.common.item.itemstack.ItemStack

trait InventoryListener {

    def sendSlotContents(container: Container, slotId: Int, itemStack: ItemStack): Unit


    def updateCraftingInventory(container: Container, inventory: Array[ItemStack]) :Unit

}
