package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.client.renderer.gui.item.GuiItemStack
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.common.inventory.Slot
import ru.megains.techworld.common.item.itemstack.ItemStack

class GuiSlot(slot:Slot) extends MContainer {


    var itemStackRender:GuiItemStack = _
    var stack:ItemStack = _
    posX = slot.xPos
    posY = slot.yPos

    override def render(shader: Shader): Unit = {
        if (stack != slot.getStack) {
            removeChildren(itemStackRender)
            val slotStack = slot.getStack

            slotStack match {
                case null =>
                    stack = null
                case _ =>
                    stack = new ItemStack(slot.getStack)
                    itemStackRender = new GuiItemStack(stack)
                    addChildren(itemStackRender)
            }
        }

        super.render(shader)
    }
}
