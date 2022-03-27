package ru.megains.techworld.client.renderer.gui.item

import ru.megains.techworld.client.register.GameRegisterRender
import ru.megains.techworld.client.renderer.MContainer
import ru.megains.techworld.common.item.Item

class GuiItem(item:Item) extends MContainer{
    addChildren( GameRegisterRender.getItemRender(item).getInventoryModel)
}
