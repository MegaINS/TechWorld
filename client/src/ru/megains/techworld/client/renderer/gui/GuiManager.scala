package ru.megains.techworld.client.renderer.gui

import org.lwjgl.glfw.GLFW.{GLFW_PRESS, GLFW_RELEASE}
import org.lwjgl.opengl.GL11.{GL_BLEND, GL_CULL_FACE, GL_DEPTH_TEST, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_STENCIL_TEST, glBlendFunc, glDisable, glEnable}
import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.Mouse
import ru.megains.techworld.client.renderer.camera.OrthographicCamera
import ru.megains.techworld.client.renderer.gui.inventory.GuiPlayerInventory
import ru.megains.techworld.client.renderer.shader.ShaderManager
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.common.entity.EntityPlayer

import scala.collection.mutable

class GuiManager(game: TechWorld) {



    val Z_FAR: Float = 100
    var shader: Shader = ShaderManager.guiShader
    var camera: OrthographicCamera = new OrthographicCamera(0, game.window.width, game.window.height, 0, -100, Z_FAR)
    var guiScreen: GuiScreen = _
    val guiUIMap: mutable.HashMap[String, GuiScreen] = new mutable.HashMap[String, GuiScreen]()
    var guiPlayerInventory: GuiPlayerInventory = _

    def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        addGuiUI("guiDebugInfo", new GuiDebugInfo())
        addGuiUI("guiBlockSelect", new GuiBlockSelect())
        addGuiUI("guiHotBar", new GuiHotBar())
        addGuiUI("guiTarget", new GuiTarget())
    }

    def setPlayer(entityPlayer: EntityPlayer): Unit ={
        guiPlayerInventory = new GuiPlayerInventory(entityPlayer)
        guiUIMap("guiHotBar").asInstanceOf[GuiHotBar].setPlayer(entityPlayer)
    }

    def addGuiUI(name: String, guiUI: GuiScreen): Unit = {
        guiUI.setGame(game)
        guiUIMap += name -> guiUI
    }
    def setScreen(screen: GuiScreen): Unit = {
        if(screen!=null) screen.setGame(game)
        guiScreen = screen
    }

    def openInventory(screen: GuiScreen): Unit ={
        setScreen(screen)
        Mouse.setGrabbed(false)
    }

    def closeInventory(): Unit ={
        setScreen(null)
        Mouse.setGrabbed(true)
    }

    def openPlayerInventory(): Unit = {
        openInventory(guiPlayerInventory)
    }

    def resize(width: Int, height: Int): Unit = {
        guiUIMap.values.foreach(_.resize(width,height))
        camera.setOrtho(0, game.window.width, game.window.height, 0, -100, Z_FAR)
        if (guiScreen != null) guiScreen.resize(width, height)
    }

    def render(partialTicks:Double): Unit = {


        glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)

        ShaderManager.bindShader(shader)
        shader.setUniform(camera)
        if (isGuiOpen) {
            guiScreen.mouseMove(Mouse.getX, Mouse.getY)
            guiScreen.render(shader)
        }else{
            if(game.world!=null)  {
                guiUIMap.values.toArray.sortWith((a, b) => a.posZ < b.posZ).foreach(_.render(shader))
            }
        }


        ShaderManager.unbindShader()
        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    def update(): Unit = {
        if (guiScreen != null) {
            guiScreen.update()
        }else{
            if(game.world!=null){
                guiUIMap.values.foreach(g=> {
                    g.update()
                    //g.mouseMove(Mouse.x.toInt,Mouse.y.toInt)
                })
            }
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

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            guiScreen.keyTyped(key.toChar, key)
        }
    }

    def isGuiOpen: Boolean = guiScreen != null


}
