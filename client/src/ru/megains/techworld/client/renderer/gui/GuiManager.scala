package ru.megains.techworld.client.renderer.gui

import org.lwjgl.glfw.GLFW.{GLFW_PRESS, GLFW_RELEASE}
import org.lwjgl.opengl.GL11.{GL_BLEND, GL_CULL_FACE, GL_DEPTH_TEST, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_STENCIL_TEST, glBlendFunc, glDisable, glEnable}
import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.Mouse
import ru.megains.techworld.client.renderer.camera.OrthographicCamera
import ru.megains.techworld.client.renderer.shader.ShaderManager
import ru.megains.techworld.client.renderer.shader.data.Shader

class GuiManager(game: TechWorld) {



    val Z_FAR: Float = 100
    var shader: Shader = ShaderManager.guiShader
    var camera: OrthographicCamera = new OrthographicCamera(0, game.window.width, game.window.height, 0, -100, Z_FAR)


    var guiScreen: GuiScreen = _

    def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }

    def setScreen(screen: GuiScreen): Unit = {
        if(screen!=null) screen.setGame(game)
        guiScreen = screen
    }

    def resize(width: Int, height: Int): Unit = {
        camera.setOrtho(0, game.window.width, game.window.height, 0, -100, Z_FAR)
        if (guiScreen != null) guiScreen.resize(width, height)
    }

    def render(): Unit = {


        glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)

        ShaderManager.bindShader(shader)
        shader.setUniform(camera)
        if (guiScreen != null) {
            guiScreen.mouseMove(Mouse.getX, Mouse.getY)
            guiScreen.render(shader)
        }

        ShaderManager.unbindShader()
        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    def update(): Unit = {
        if (guiScreen != null) {
            guiScreen.update()
        }
    }

    def runTickMouse(button: Int, action: Int, mods: Int): Unit = {
        action match {
            case GLFW_PRESS =>
                guiScreen.mousePress(Mouse.getX, Mouse.getY, button)
            case GLFW_RELEASE =>
                guiScreen.mouseRelease(Mouse.getX, Mouse.getY, button)
        }
    }

    def isGuiOpen: Boolean = guiScreen != null
}
