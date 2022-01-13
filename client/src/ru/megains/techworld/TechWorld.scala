package ru.megains.techworld

import ru.megaims.techworld.common.utils.Timer
import ru.megains.techworld.render.{MouseKeyboard, Window}

class TechWorld(config: Configuration) {



    val window: Window = new Window(config.width,config.height,config.title)
    var running: Boolean = true
    val timerFps= new Timer(60)
    val timerTps = new Timer(20)


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
        MouseKeyboard.init(this)

        true
    }

    def render(): Unit = {
        window.update()
        MouseKeyboard.update()
    }

    def update(): Unit = {

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

    def runTickMouse(button: Int,action: Int, mods: Int): Unit = {

    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {


    }


    def close(): Unit = {
        window.close()
    }

    def error(): Unit ={

    }

    def resize(width: Int, height: Int): Unit = {

    }


}
