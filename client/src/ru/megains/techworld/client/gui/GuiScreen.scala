package ru.megains.techworld.client.gui

abstract class GuiScreen extends Gui{

    def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        //        keyCode match {
        //            case GLFW_KEY_E | GLFW_KEY_ESCAPE =>
        //                // tar.playerController.net.sendPacket(new CPacketCloseWindow)
        //                gameScene.guiRenderer.closeGui()
        //            case _ =>
        //        }
    }
}
