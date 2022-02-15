package ru.megains.techworld.client.gui

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.network.handler.NetHandlerPlayClient
import ru.megains.techworld.client.renderer.text.Label

class GuiDownloadTerrain(world: TechWorld, client: NetHandlerPlayClient) extends GuiScreen{

    lazy val name = new Label("DownloadTerrain")


    override def init(): Unit = {
        addChildren(name)
    }



    override def resize(width:Int,height:Int): Unit = {
        name.posX = (width - name.width) / 2
        name.posY = 200
    }
}
