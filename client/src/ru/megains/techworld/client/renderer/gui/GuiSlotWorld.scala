package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.gui.element.GuiElement
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.text.Label

import java.awt.Color

class GuiSlotWorld(id: Int, val worldName: String) extends GuiElement {

    override val width: Int = 400
    override val height: Int = 60


    val label: Label = new Label(worldName) {
        posX = 10
        posY = 22
    }

    val slotSelect: Model = Gui.createRect(width, height, Color.LIGHT_GRAY)
    val slot: Model = Gui.createRect(width, height, Color.BLACK)
    slotSelect.active = false

    var _select: Boolean = false

    addChildren(slotSelect, slot, label)


    def select: Boolean = _select

    def select_=(select:Boolean): Unit = {
        slotSelect.active = select
        slot.active = !select
    }





    override def resize(width: Int, height: Int): Unit = {

    }



    override def init(): Unit = {}

}
