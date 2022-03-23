package ru.megains.techworld.client.renderer.gui

import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import ru.megains.techworld.client.renderer.gui.element.MButton

class GuiInGameMenu() extends GuiScreen {

    val buttonMainMenu: MButton = new MButton("Main menu", 300, 40, _ => {
        game.playerController.sendQuittingDisconnectingPacket()
        game.setWorld(null)
        game.setScreen(new GuiMainMenu())
    })
    val buttonOption: MButton = new MButton("Option", 300, 40, _ => {
        game.setScreen(new GuiGameSettings(this))
    })
    val buttonReturnToGame: MButton = new MButton("Return to game", 300, 40, _ => {
        game.setScreen(null)
    })


    override def init(): Unit = {

        addChildren(buttonMainMenu, buttonOption, buttonReturnToGame)
    }
    override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_ESCAPE =>
                // tar.playerController.net.sendPacket(new CPacketCloseWindow)
                game.setScreen(null)
            case _ =>
        }
    }

    override def resize(width: Int, height: Int): Unit = {
        buttonMainMenu.posX = (width - 300) / 2
        buttonMainMenu.posY = 240

        buttonOption.posX = (width - 300) / 2
        buttonOption.posY = 310

        buttonReturnToGame.posX = (width - 300) / 2
        buttonReturnToGame.posY = 380
    }
}

