package ru.megains.techworld.client.gui

import ru.megains.techworld.client.gui.element.MButton

class GuiMainMenu() extends GuiScreen {



    val buttonSingleGame: MButton = new MButton("SingleGame", 300, 40, _ => {
        game.setScreen(new GuiSelectWorld(this))
    })
    val buttonMultiPlayerGame: MButton = new MButton("MultiPlayerGame", 300, 40, _ => {
        game.setScreen(new GuiMultiPlayer(this))
    })
    val buttonOption: MButton = new MButton("Option", 300, 40, _ => {
        game.setScreen(new GuiGameSettings( this))
    })
    val buttonExitGame: MButton = new MButton("Exit game", 300, 40,_ => {
        game.running = false
    })

    override def init(): Unit ={
        addChildren(buttonSingleGame, buttonMultiPlayerGame, buttonOption, buttonExitGame)
    }


    override def resize(width: Int, height: Int): Unit = {

        buttonSingleGame.posX = (width - 300) / 2
        buttonSingleGame.posY = 240

        buttonMultiPlayerGame.posX = (width - 300) / 2
        buttonMultiPlayerGame.posY = 310

        buttonOption.posX = (width - 300) / 2
        buttonOption.posY = 380

        buttonExitGame.posX = (width - 300) / 2
        buttonExitGame.posY = 450


    }

}
