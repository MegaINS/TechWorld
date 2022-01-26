package ru.megains.techworld.gui

import ru.megains.techworld.TechWorld
import ru.megains.techworld.renderer.MContainer
import ru.megains.techworld.renderer.mesh.MeshMaker
import ru.megains.techworld.renderer.model.Model
import ru.megains.techworld.renderer.texture.TextureManager

import java.awt.Color


abstract class Gui extends MContainer {
    var game:TechWorld = _


    def setGame(gameIn: TechWorld): Unit ={
        game = gameIn
        removeAllChildren()
        init()
        resize(gameIn.window.width,game.window.height)
    }

    def init(): Unit


}

object Gui{
    val xZero = 0.0f
    val yZero = 0.0f
    val zZero = 0.0f
    def createRect(width: Int, height: Int, color: Color): Model = {

        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(TextureManager.baseTexture)
        mm.addColor(color)
        mm.addVertexWithUV(xZero, yZero, zZero,0,1)
        mm.addVertexWithUV(xZero, height, zZero,0,0)
        mm.addVertexWithUV(width, height, zZero,1,0)
        mm.addVertexWithUV(width, yZero, zZero,1,1)
        mm.addIndex(0, 1, 2)
        mm.addIndex(0, 2, 3)
        new Model(mm.make())

    }

}

