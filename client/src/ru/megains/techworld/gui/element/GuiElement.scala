package ru.megains.techworld.gui.element

import ru.megains.techworld.gui.Gui

abstract class GuiElement() extends Gui {

    val width: Int = 0
    val height: Int = 0


    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height
    }

}
