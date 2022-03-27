package ru.megains.techworld.client.renderer.gui.item

import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.client.renderer.gui.Gui
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.text.Label
import ru.megains.techworld.common.item.itemstack.ItemStack

import java.awt.Color

class GuiItemStack(itemStack:ItemStack) extends MContainer{

    val stackSize: Label = new Label(itemStack.stackSize.toString){
        posY = 26
        scale = 0.7f
    }
    val stackMass: Label = new Label(itemStack.stackMass.toString){
        posX = 23
        posY = 26
        scale = 0.7f
    }
    val cubeSize: Model = Gui.createRect(40,15,Color.WHITE)
        cubeSize.posY = 25

    addChildren(new GuiItem(itemStack.item))
    addChildren(stackSize,stackMass)

}
