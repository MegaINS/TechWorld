package ru.megains.techworld.client.renderer

import ru.megains.techworld.client.world.WorldClient

class RendererGame {



    var rendererWorld:RendererWorld = _
    var world:WorldClient = _

    def setWorld(newWorld:WorldClient): Unit ={
        world = newWorld
    }

    def render(): Unit = {

    }

    def update(): Unit = {

    }
}
