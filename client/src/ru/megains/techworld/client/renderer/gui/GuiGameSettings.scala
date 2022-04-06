package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.gui.element.{MButton, MSlider}
import ru.megains.techworld.client.renderer.text.Label

class GuiGameSettings(previosScreen:GuiScreen) extends GuiScreen {

    val name = new Label("Options")

    lazy val fpsSlider:MSlider = new MSlider("FPS",300,40,30,500,game.settings.FPS, ()=>{game.settings.FPS = fpsSlider.value;game.timerFps.ticksPerSecond = fpsSlider.value})
    lazy val fovSlider:MSlider = new MSlider("FOV",300,40,1,100,game.settings.FOV, ()=>{game.settings.FOV = fovSlider.value})

    lazy val renderDistanceWidthSlider:MSlider = new MSlider("RENDER_DISTANCE_WIDTH",300,40,1,20,game.settings.RENDER_DISTANCE_WIDTH, ()=>{game.settings.RENDER_DISTANCE_WIDTH = renderDistanceWidthSlider.value})
    lazy val renderDistanceHeightSlider:MSlider = new MSlider("RENDER_DISTANCE_HEIGHT",300,40,1,10,game.settings.RENDER_DISTANCE_HEIGHT, ()=>{game.settings.RENDER_DISTANCE_HEIGHT = renderDistanceHeightSlider.value})
    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
       game.setScreen(previosScreen)
    })

    override def init(): Unit ={
        addChildren(name,fpsSlider,fovSlider,renderDistanceWidthSlider,renderDistanceHeightSlider,buttonCancel)
    }


    override def resize(width: Int, height: Int): Unit = {
        name.posX = (width - name.width) / 2
        name.posY = 200

        fpsSlider.posX = (width - 300) / 2
        fpsSlider.posY = 240

        fovSlider.posX = (width - 300) / 2
        fovSlider.posY = 310

        renderDistanceWidthSlider.posX = (width - 300) / 2
        renderDistanceWidthSlider.posY = 380

        renderDistanceHeightSlider.posX = (width - 300) / 2
        renderDistanceHeightSlider.posY = 450

        buttonCancel.posX = (width - 300) / 2
        buttonCancel.posY = 520

    }



}
