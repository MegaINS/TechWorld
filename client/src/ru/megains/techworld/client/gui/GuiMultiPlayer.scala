package ru.megains.techworld.client.gui

import ru.megains.techworld.client.gui.element.MButton
import ru.megains.techworld.client.network.ServerData
import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.renderer.text.Label

import scala.collection.mutable.ArrayBuffer

class GuiMultiPlayer(previousScreen: GuiScreen) extends GuiScreen {

    val name = new Label("MultiPlayer")
    val slotServers: ArrayBuffer[GuiSlotServer] = new ArrayBuffer[GuiSlotServer]()
    var selectServer: GuiSlotServer = _

    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        game.setScreen(previousScreen)
    })
    val buttonConnect: MButton = new MButton("Connect", 300, 40, _ => {
        connectToServer(selectServer.server)
    })

    def connectToServer(server: ServerData): Unit = {
        game.setScreen(new GuiConnecting(this, server))
    }

    override def init(): Unit = {

        slotServers.clear()
        val localhost = new GuiSlotServer(0, "localhost", "localhost")
        localhost.setGame(game)
        slotServers += localhost


        buttonConnect.enable = false

        addChildren(name, buttonCancel, buttonConnect)

        for (i <- slotServers.indices) {
            addChildren(slotServers(i))
        }
    }


    override def resize(width: Int, height: Int): Unit = {

        name.posX = (width - name.width) / 2
        name.posY = 50

        buttonCancel.posX = width / 2 + 50
        buttonCancel.posY = height - 70

        buttonConnect.posX = width / 2 - 350
        buttonConnect.posY = height - 70

        for (i <- slotServers.indices) {
            val ws = slotServers(i)
            ws.posX = (width - ws.width) / 2
            ws.posY = 100 + 70 * i
        }

    }

    override def mousePress(x: Int, y: Int, button: Int): Unit = {
        super.mousePress(x, y, button)

        if (button == GLFW_MOUSE_BUTTON_1) {
            var isSelect = false
            slotServers.foreach(
                slot => {
                    if (slot.isMouseOver(x - posX.toInt, y - posY.toInt)) {
                        slot.select = true
                        selectServer = slot
                        isSelect = true
                    } else {
                        slot.select = false
                    }
                }
            )
            buttonConnect.enable = isSelect
            if (!isSelect) selectServer = null
        }


    }
}
