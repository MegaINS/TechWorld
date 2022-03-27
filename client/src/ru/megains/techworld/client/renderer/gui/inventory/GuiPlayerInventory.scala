package ru.megains.techworld.client.renderer.gui.inventory

import ru.megains.techworld.client.renderer.{MSprite, Resources}
import ru.megains.techworld.common.entity.EntityPlayer

class GuiPlayerInventory(entityPlayer: EntityPlayer) extends GuiContainer(entityPlayer.inventoryContainer) {

    val playerInventory = new MSprite(Resources.PLAYER_INVENTORY, 500, 240)

    override def init(): Unit = {
        addChildren(playerInventory)
        super.init()

    }

    override def resize(width:Int,height:Int): Unit = {
        posX = (width - 500) / 2
        posY = height - 240
    }
}
