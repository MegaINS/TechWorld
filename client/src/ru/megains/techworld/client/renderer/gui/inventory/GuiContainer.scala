package ru.megains.techworld.client.renderer.gui.inventory

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.techworld.client.renderer.Mouse
import ru.megains.techworld.client.renderer.gui.item.GuiItemStack
import ru.megains.techworld.client.renderer.gui.{Gui, GuiScreen, GuiSlot}
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.common.container.Container
import ru.megains.techworld.common.inventory.Slot
import ru.megains.techworld.common.item.itemstack.ItemStack

import java.awt.Color


abstract class GuiContainer(val inventorySlots: Container) extends GuiScreen {


    val rect: Model = Gui.createRect(40, 40, new Color(200, 255, 100, 100))
    var stack:ItemStack = _
    var itemStackRender:GuiItemStack =_
    def init(): Unit = {
        addChildren(rect)
        for(slot <- inventorySlots.inventorySlots){
            addChildren( new GuiSlot(slot))
        }

    }

    override def update(): Unit = {
        super.update()
        inventorySlots.inventorySlots.find(inventorySlots.isMouseOverSlot(_, (Mouse.x - posX).toInt, (Mouse.y - posY).toInt)) match {
            case Some(slot) =>
                rect.posX = slot.xPos
                rect.posY = slot.yPos
                rect.active = true
            case _ =>
                rect.active = false
        }

        if(stack !=  game.player.inventory.itemStack){
            stack =  game.player.inventory.itemStack
            if(stack!= null){
                itemStackRender = new GuiItemStack(stack)
            }else{
                itemStackRender = null
            }
        }
    }

   override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_E | GLFW_KEY_ESCAPE =>
                // tar.playerController.net.sendPacket(new CPacketCloseWindow)
                game.setScreen(null)
            case _ =>
        }
    }

    override def render(shader: Shader): Unit = {
        super.render(shader)
        if(itemStackRender!=null){
            itemStackRender.posX = Mouse.getX
            itemStackRender.posY = Mouse.getY
            itemStackRender.render(shader)
        }
    }

    override def mousePress(x: Int, y: Int, button: Int): Unit = {
        inventorySlots.mouseClicked(x-posX.toInt,y-posY.toInt,button,game.player)
       // player.openContainer.mouseClicked(x-posX, y-posY, button, player)
       // tar.playerController.windowClick(x-posX, y-posY, button, player: EntityPlayer)
    }

    def getSlotAtPosition(x: Int, y: Int): Slot = inventorySlots.inventorySlots.find(inventorySlots.isMouseOverSlot(_, x, y)).orNull
}
