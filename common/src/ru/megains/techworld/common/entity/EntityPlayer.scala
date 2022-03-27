package ru.megains.techworld.common.entity

import ru.megains.techworld.common.container.{Container, ContainerPlayerInventory}
import ru.megains.techworld.common.inventory.InventoryPlayer

class EntityPlayer extends Entity(0.6f,1.8f) {


    val inventory = new InventoryPlayer(this)
    val inventoryContainer: Container = new ContainerPlayerInventory(inventory)
    var levelView = 1.6f
}
