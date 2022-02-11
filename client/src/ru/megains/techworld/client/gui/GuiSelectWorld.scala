package ru.megains.techworld.client.gui

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.gui.element.MButton

import scala.collection.mutable.ArrayBuffer

class GuiSelectWorld(previousScreen: GuiScreen) extends GuiScreen {

   //  val sceneGui: SceneGui = orangeM.scene.asInstanceOf[SceneGui]
    var selectWorld: GuiSlotWorld = _
    var savesArray: Array[String] = _
    var slotWorlds: ArrayBuffer[GuiSlotWorld] = new ArrayBuffer[GuiSlotWorld]

    val buttonSelect: MButton = new MButton("Select", 300, 40, _ => {
       // val saveHandler: AnvilSaveHandler = orangeM.saveLoader.getSaveLoader(selectWorld.worldName)
      //  orangeM.setScene(new SceneGame(orangeM/*, saveHandler*/))
    }) {
        enable = false
    }


    val buttonDelete: MButton = new MButton("Delete", 300, 40, _ => {
       // orangeM.saveLoader.deleteWorldDirectory(selectWorld.worldName)
       // sceneGui.setGuiScreen(this)
    }) {
        enable = false
    }

    val buttonCreateWorld: MButton = new MButton("CreateWorld", 300, 40, _ => {
       // val saveHandler = orangeM.saveLoader.getSaveLoader("World " + (worldsSlot.length + 1))
       // orangeM.setScene(new SceneGame(orangeM/*, saveHandler*/))
    })

    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        game.setScreen(previousScreen)
    })

    override def init(): Unit = {

       // savesArray = orangeM.saveLoader.getSavesArray
//        for (i <- savesArray.indices) {
//            slotWorlds(i) = new GuiSlotWorld(i, savesArray(i))
//
//            addChildren(slotWorlds(i))
//        }
        addChildren(buttonSelect, buttonDelete, buttonCreateWorld, buttonCancel)
    }


    override def resize(width: Int, height: Int): Unit = {

        buttonSelect.posX = width / 2 - 350
        buttonSelect.posY = height - 150

        buttonDelete.posX = width / 2 - 350
        buttonDelete.posY = height - 70

        buttonCreateWorld.posX = width / 2 + 50
        buttonCreateWorld.posY = height - 150

        buttonCancel.posX = width / 2 + 50
        buttonCancel.posY = height - 70
        for (i <- slotWorlds.indices) {
            val ws = slotWorlds(i)
            ws.posX = (width - ws.width) / 2
            ws.posY = 100 + 70 * i
        }


    }

    override def mousePress(x: Int, y: Int, button: Int): Unit = {
        super.mousePress(x, y, button)

        if (button == GLFW_MOUSE_BUTTON_1) {
            var isSelect = false
            slotWorlds.foreach(
                slot => {
                    if (slot.isMouseOver(x - posX.toInt, y - posY.toInt)) {
                        slot.select = true
                        selectWorld = slot
                        isSelect = true
                    } else {
                        slot.select = false
                    }
                }
            )
            buttonDelete.enable = isSelect
            buttonSelect.enable = isSelect
            if (!isSelect) selectWorld = null
        }


    }


}
