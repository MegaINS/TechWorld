package ru.megains.techworld.client.renderer

import ru.megains.techworld.client.renderer.shader.data.Shader

import scala.collection.mutable.ArrayBuffer

class MContainer extends MObject {

    val _children:ArrayBuffer[MObject] = ArrayBuffer[MObject]()

    override def render(shader: Shader): Unit = {
        _children.sortWith((a, b) => a.posZ < b.posZ).foreach(_.render(shader))

    }

    def addChildren(children:MObject*): Unit ={
        children.foreach(_.parent = this)
        _children.addAll(children)
    }

    def removeChildren(children:MObject*): Unit = {
        children.foreach(_children -= _)
    }
    
    def removeAllChildren(): Unit ={
        _children.clear()
    }

    override def update(): Unit = {
        _children.foreach(_.update())
    }

    override def mousePress(x: Int, y: Int,button: Int): Unit = {
        _children.foreach(c => if(c!= null) c.mousePress(x- posX.toInt,y - posY.toInt,button))
    }

    override def mouseRelease(x:Int,y:Int,button: Int):Unit= {
        _children.foreach(_.mouseRelease(x- posX.toInt,y - posY.toInt,button))
    }

    override def mouseMove(x: Int, y: Int): Unit = {
        _children.foreach(_.mouseMove(x- posX.toInt,y- posY.toInt))

    }

    override def resize(width: Int, height: Int): Unit = {
        _children.foreach(_.resize(width,height))
    }

}
