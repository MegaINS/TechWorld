package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.gui.element.MButton
import ru.megains.techworld.client.renderer.text.Label

class GuiDisconnected(previousScreen:GuiScreen, text1:String, text2:String) extends GuiScreen{

    lazy val name = new Label("Disconnected")

    lazy val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        game.setScreen(previousScreen)
    })

    override def init(): Unit = {
        addChildren(name, buttonCancel)
    }




    override def resize(width:Int,height:Int): Unit = {
        name.posX = (width - name.width) / 2
        name.posY = 200
        buttonCancel.posX = 500
        buttonCancel.posY = height - 70

    }

    override def update(): Unit = {

    }


}
