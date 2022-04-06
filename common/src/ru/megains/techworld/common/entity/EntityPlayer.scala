package ru.megains.techworld.common.entity

import ru.megains.techworld.common.block.BlockPos
import ru.megains.techworld.common.container.{Container, ContainerPlayerInventory}
import ru.megains.techworld.common.inventory.InventoryPlayer
import ru.megains.techworld.common.item.ItemPack
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.world.World

abstract class EntityPlayer extends Entity(0.6f,1.8f, 1.6f) {



    var _gameType:GameType = GameType.SURVIVAL
    val inventory = new InventoryPlayer(this)
    val inventoryContainer: Container = new ContainerPlayerInventory(inventory)
    var openContainer: Container = inventoryContainer


    def gameType: GameType =  _gameType

    def gameType_=(value:GameType):Unit = {
        _gameType = value

        _gameType match {
            case GameType.SURVIVAL =>
                isCheckCollision = true
            case GameType.CREATIVE =>
                isCheckCollision = true
            case GameType.SPECTATOR =>
                isCheckCollision = false

            case GameType.NOT_SET => println("GameType.NOT_SET")
        }
    }


    def getHeldItem: ItemStack = getItemStackFromSlot

    def getItemStackFromSlot: ItemStack = inventory.getStackSelect

    def setContainer(world: World, x: Int, y: Int, z: Int):Unit
}
