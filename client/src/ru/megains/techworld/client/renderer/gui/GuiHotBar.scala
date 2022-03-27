package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.{MContainer, MSprite, Resources}
import ru.megains.techworld.common.entity.EntityPlayer

class GuiHotBar() extends GuiScreen {

    var hotBar: MSprite = _
    var stackSelect: MSprite = _

    val container = new MContainer
    override def init(): Unit = {
        stackSelect = new MSprite(Resources.STACK_SELECT , 56, 54)
        hotBar = new MSprite(Resources.HOT_BAR, 484, 52) {
            posX = 2
            posY = 2
        }

        posY = game.window.height - 54
        posX = (game.window.width - 488) / 2

        addChildren(hotBar, stackSelect)


        container.posX = -6


        addChildren(container)
    }


    def setPlayer(entityPlayer: EntityPlayer): Unit ={
        container.removeAllChildren()
        for (i <- 0 to 9) {
            val slot = new GuiSlot(entityPlayer.inventoryContainer.inventorySlots(i)) {
                posY = 7
            }
            container.addChildren(slot)
        }
    }



    override def update(): Unit = {
        super.update()
        posX = (game.window.width - 488) / 2
        stackSelect.posX = game.player.inventory.stackSelect * 48
    }

    override def resize(width:Int,height:Int): Unit ={
        posY = height - 54
        posX = (width - 488) / 2
    }

}
