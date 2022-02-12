package ru.megains.techworld.client

import org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load
import ru.megains.techworld.client.gui.{GuiManager, GuiPlayerSelect, GuiScreen}
import ru.megains.techworld.client.renderer.shader.ShaderManager
import ru.megains.techworld.client.renderer.{Mouse, Window}
import ru.megains.techworld.common.network.{NetworkManager, PacketProcessHandler}
import ru.megains.techworld.common.utils.{Logger, Timer}


class TechWorld(config: Configuration) extends Logger{



    var networkManager:NetworkManager = _

    var playerName = ""
    val gameSettings:GameSettings = new GameSettings(this,config.filePath)
    val window: Window = new Window(config.width, config.height, config.title)
    var running: Boolean = true
    val timerFps = new Timer(60)
    val timerTps = new Timer(20)
    val guiManager = new GuiManager(this)
    val packetProcessHandler:PacketProcessHandler = new PacketProcessHandler


    def start(): Unit = {
        if (init()) {
            gameLoop()
            close()
        } else {
            error()
        }

    }

    def init(): Boolean = {

        log.info("Init Windows")
        window.create(this)
        log.info("Init Mouse")
        Mouse.init(this)
        log.info("Init ShaderManager")
        ShaderManager.init()
        stbi_set_flip_vertically_on_load(true)
        log.info("Init GuiManager")
        guiManager.init()

        log.info("Start Game")
        guiManager.setScreen(new GuiPlayerSelect)
        true
    }

    def render(): Unit = {


        guiManager.render()
        window.update()
        Mouse.update()
    }

    def update(): Unit = {
        packetProcessHandler.tick()
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
object TechWorld{

    def getSystemTime: Long = System.currentTimeMillis()
}
