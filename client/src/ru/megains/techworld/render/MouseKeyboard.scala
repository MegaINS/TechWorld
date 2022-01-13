package ru.megains.techworld.render

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.TechWorld


object MouseKeyboard {

    var x: Double = 0
    var y: Double = 0
    var preX: Double = 0
    var preY: Double = 0
    var DWheel: Double = 0
    var window: Window = _

    def getY: Int = y.toInt

    def getX: Int = x.toInt

    def getDX: Double = x - preX

    def getDY: Double = y - preY

    def getDWheel: Int = DWheel.toInt

    def update(): Unit = {

        preX = x
        preY = y
        DWheel = 0
        glfwPollEvents()
    }

    def init(game:TechWorld): Unit = {
        window = game.window
        glfwSetCursorPosCallback(window.id, (window, xpos, ypos) => {
            x = xpos
            y = ypos
        })
        glfwSetMouseButtonCallback(window.id, (window, button, action, mods) => {
            game.runTickMouse(button, action, mods)
        })
        glfwSetScrollCallback(window.id, (window: Long, xoffset: Double, yoffset: Double) => {
            DWheel = yoffset
        })
        glfwSetKeyCallback(window.id, (windowHnd: Long, key: Int, scancode: Int, action: Int, mods: Int) => {
            game.runTickKeyboard(key, action, mods)
        })

    }



    def setGrabbed(mode: Boolean): Unit = {
        if (mode) {
            glfwSetInputMode(window.id, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        } else {
            glfwSetInputMode(window.id, GLFW_CURSOR, GLFW_CURSOR_NORMAL)
        }
    }
}
