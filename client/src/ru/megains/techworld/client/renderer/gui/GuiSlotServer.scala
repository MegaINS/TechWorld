package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.network.{ServerData, ServerPinger}
import ru.megains.techworld.client.renderer.gui.element.GuiElement
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.text.Label

import java.awt.Color

class GuiSlotServer(id: Int, val serverName: String, val serverIP: String) extends GuiElement {


    val server: ServerData = new ServerData(serverName, serverIP, false)
    var serverPinger: ServerPinger = _
    var pingVal: Long = -1
    var _select: Boolean = false

    override val width: Int = 400
    override val height: Int = 60

    val label: Label = new Label(serverName) {
        posX = 10
        posY = 22
    }

    val pingText: Label = new Label(pingVal.toString) {
        posX = 350
        posY = 22
    }

    val slotSelect: Model = Gui.createRect(width - 10, height - 10, Color.DARK_GRAY)
    val slot: Model = Gui.createRect(width, height, Color.BLACK)

    override def init(): Unit = {
        serverPinger = new ServerPinger(game)
        slotSelect.active = false
        slotSelect.posX = 5
        slotSelect.posY = 5
        addChildren(slot, slotSelect, label, pingText)
    }


    def select: Boolean = _select

    def select_=(select: Boolean): Unit = {
        slotSelect.active = select
    }

    var timeToPing = 0

    override def update(): Unit = {
        if (pingVal != server.pingToServer) {
            pingVal = server.pingToServer
            pingText.text = pingVal.toString
        }
        if (timeToPing == 0) {
            ping()
            timeToPing = 100
        } else {
            timeToPing -= 1
        }
    }

    def ping(): Unit = {
        try {
            new Thread("Server ping #" /* + CONNECTION_ID.incrementAndGet*/) {
                override def run(): Unit = {
                    serverPinger.ping(server)
                }
            }.start()

        } catch {
            case e: Throwable =>
                e.printStackTrace()

        }
    }

    override def resize(width: Int, height: Int): Unit = {

    }




}
