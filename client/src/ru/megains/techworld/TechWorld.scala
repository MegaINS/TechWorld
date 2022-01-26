package ru.megains.techworld

import org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load
import ru.megaims.techworld.common.utils.Timer
import ru.megains.techworld.gui.{GuiManager, GuiPlayerSelect, GuiScreen}
import ru.megains.techworld.renderer.shader.ShaderManager
import ru.megains.techworld.renderer.{Mouse, Window}

class TechWorld(config: Configuration) {


    var playerName = ""
    val gameSettings:GameSettings = new GameSettings(this,config.filePath)
    val window: Window = new Window(config.width, config.height, config.title)
    var running: Boolean = true
    val timerFps = new Timer(60)
    val timerTps = new Timer(20)
    val guiManager = new GuiManager(this)


    def start(): Unit = {
        if (init()) {
            gameLoop()
            close()
        } else {
            error()
        }

    }

    def init(): Boolean = {
        window.create(this)
        Mouse.init(this)
        ShaderManager.init()
        stbi_set_flip_vertically_on_load(true)
        guiManager.init()


        guiManager.setScreen(new GuiPlayerSelect)
        true
    }

    def render(): Unit = {


        guiManager.render()
        window.update()
        Mouse.update()
    }

    def update(): Unit = {
        guiManager.update()
    }

    def gameLoop(): Unit = {
        timerFps.init()
        timerTps.init()
        while (running) {

            if (window.isClose) running = false


            timerFps.update()
            for (_ <- 0 until timerFps.tick) {
                render()
            }

            timerTps.update()
            for (_ <- 0 until timerTps.tick) {
                update()
            }
            Thread.sleep(1)

        }
    }

    def runTickMouse(button: Int, action: Int, mods: Int): Unit = {
        if (guiManager.isGuiOpen) {
            guiManager.runTickMouse(button, action, mods)
        } else {

        }


    }

    def setScreen(value: GuiScreen): Unit = {
        guiManager.setScreen(value)
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {

        println(s"$key $action $mods")
    }


    def close(): Unit = {
        window.close()
    }

    def error(): Unit = {

    }

    def resize(width: Int, height: Int): Unit = {
        guiManager.resize(width, height)
    }


}
