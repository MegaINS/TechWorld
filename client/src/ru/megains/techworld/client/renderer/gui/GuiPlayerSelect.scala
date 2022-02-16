package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.gui.element.MButton

class GuiPlayerSelect() extends GuiScreen {

    val buttonTest_1: MButton = new MButton("Test_1", 300, 40, buttonSelectPlayer)
    val buttonTest_2: MButton = new MButton("Test_2", 300, 40, buttonSelectPlayer)
    val buttonTest_3: MButton = new MButton("Test_3", 300, 40, buttonSelectPlayer)
    val buttonTest_4: MButton = new MButton("Test_4", 300, 40, buttonSelectPlayer)



    def buttonSelectPlayer(mButton: MButton): Unit = {
        game.playerName = mButton.buttonText
        game.setScreen(new GuiMainMenu())
    }


    override def resize(width: Int, height: Int): Unit = {

        buttonTest_1.posX = (width - 300) / 2
        buttonTest_1.posY = 240

        buttonTest_2.posX = (width - 300) / 2
        buttonTest_2.posY = 310


        buttonTest_3.posX = (width - 300) / 2
        buttonTest_3.posY = 380

        buttonTest_4.posX = (width - 300) / 2
        buttonTest_4.posY = 450


    }

    override def init(): Unit = {
        addChildren(buttonTest_1, buttonTest_2, buttonTest_3, buttonTest_4)
    }
}
