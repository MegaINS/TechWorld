package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.{MSprite, Resources}

class GuiTarget extends GuiScreen {

    var target: MSprite = _

    override def init(): Unit = {
        target = new MSprite(Resources.TARGET, 40, 40)
        addChildren(target)
        resize(game.window.width,game.window.height)
    }

    override def resize(width:Int,height:Int): Unit = {
        posY = height / 2 - 20
        posX = width / 2 - 20
    }
}
