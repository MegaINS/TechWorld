package ru.megains.techworld.gui

import ru.megains.techworld.TechWorld
import ru.megains.techworld.renderer.MContainer
import ru.megains.techworld.renderer.mesh.MeshMaker
import ru.megains.techworld.renderer.model.Model

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
        mm.addColorRGBA(color.getRed, color.getGreen, color.getBlue, color.getAlpha)
        mm.addVertex(xZero, zZero, zZero)
        mm.addVertex(xZero, height, zZero)
        mm.addVertex(width, height, zZero)
        mm.addVertex(width, zZero, zZero)
        mm.addIndex(0, 1, 2)
        mm.addIndex(0, 2, 3)
        new Model(mm.make())

    }

}

