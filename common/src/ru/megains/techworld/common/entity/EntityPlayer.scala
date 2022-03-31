package ru.megains.techworld.common.entity

import ru.megains.techworld.common.container.{Container, ContainerPlayerInventory}
import ru.megains.techworld.common.inventory.InventoryPlayer
import ru.megains.techworld.common.item.ItemPack
import ru.megains.techworld.common.item.itemstack.ItemStack

class EntityPlayer extends Entity(0.6f,1.8f, 1.6f) {


    val inventory = new InventoryPlayer(this)
    val inventoryContainer: Container = new ContainerPlayerInventory(inventory)
    var openContainer: Container = inventoryContainer

    def getHeldItem: ItemStack = getItemStackFromSlot

    def getItemStackFromSlot: ItemStack = inventory.getStackSelect
}
