package ru.megains.techworld.client.renderer.gui.inventory

import ru.megains.techworld.client.renderer.{MSprite, Resources}
import ru.megains.techworld.client.renderer.mesh.Mesh
import ru.megains.techworld.common.container.ContainerChest
import ru.megains.techworld.common.inventory.InventoryPlayer
import ru.megains.techworld.common.tileentity.TileEntityChest

class GuiChest(inventoryPlayer: InventoryPlayer, inventoryTest: TileEntityChest) extends GuiContainer(new ContainerChest(inventoryPlayer,inventoryTest)){

    var chestInventory = new MSprite(Resources.CHEST_INVENTORY, 500, 440)

    override def init(): Unit = {
        addChildren(chestInventory)
        super.init()

    }

    override def resize(width:Int,height:Int): Unit = {
        posX = (width - 500) / 2
        posY = height - 440
    }

}
