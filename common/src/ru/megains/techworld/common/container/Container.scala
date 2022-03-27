package ru.megains.techworld.common.container

import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.inventory.Slot
import ru.megains.techworld.common.item.ItemType
import ru.megains.techworld.common.item.itemstack.ItemStack

import scala.collection.mutable.ArrayBuffer

abstract class Container {

    val inventorySlots: ArrayBuffer[Slot] = new ArrayBuffer[Slot]()

    def addSlotToContainer(slot: Slot): Unit = {

        slot.slotNumber = inventorySlots.size
        inventorySlots += slot
    }

    def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        val slot = getSlotAtPosition(x, y)
        if (slot != null) {
            slotClick(slot.slotNumber, button, player)
        }
    }

    def slotClick(slotId: Int, button: Int, player: EntityPlayer): Unit = {

        val slot = inventorySlots(slotId)
        val inventoryPlayer = player.inventory

        button match {
            case 0 =>
                if (inventoryPlayer.itemStack == null) {
                    if (!slot.isEmpty) {
                        inventoryPlayer.itemStack = slot.getStack
                        slot.putStack(null)
                    }
                } else {
                    if (slot.isEmpty) {
                        slot.putStack(inventoryPlayer.itemStack)
                        inventoryPlayer.itemStack = null
                    } else if (slot.getStack.item == inventoryPlayer.itemStack.item) {
                        slot.getStack.item.itemType match {
                            case ItemType.SINGLE =>
                                val temp = slot.getStack
                                slot.putStack(inventoryPlayer.itemStack)
                                inventoryPlayer.itemStack = temp
                            case ItemType.MASS =>
                                slot.getStack.stackMass += inventoryPlayer.itemStack.stackMass
                                inventoryPlayer.itemStack = null
                            case ItemType.STACK =>
                                slot.getStack.stackSize += inventoryPlayer.itemStack.stackSize
                                slot.getStack.stackMass += inventoryPlayer.itemStack.stackMass
                                inventoryPlayer.itemStack = null
                        }
                    }else{
                        val temp = slot.getStack
                        slot.putStack(inventoryPlayer.itemStack)
                        inventoryPlayer.itemStack = temp
                    }
                }
            case 1 =>
                if (inventoryPlayer.itemStack != null) {
                    if (slot.isEmpty) {
                        slot.putStack(inventoryPlayer.itemStack.splitStack(1))
                    } else if (slot.getStack.item == inventoryPlayer.itemStack.item) {
                        slot.getStack.item.itemType match {
                            case ItemType.SINGLE =>

                            case ItemType.MASS =>

                            case ItemType.STACK =>
                                slot.getStack.stackSize += 1
                                slot.getStack.stackMass += inventoryPlayer.itemStack.item.mass
                                inventoryPlayer.itemStack.stackSize -= 1
                                inventoryPlayer.itemStack.stackMass -= inventoryPlayer.itemStack.item.mass
                        }
                    }
                    if (inventoryPlayer.itemStack.stackSize < 1) {
                        inventoryPlayer.itemStack = null
                    }
                } else {
                    if (!slot.isEmpty) {
                        val size: Int =  slot.getStack.item.itemType match {
                            case ItemType.MASS =>
                                slot.getStack.stackMass
                            case ItemType.STACK | ItemType.SINGLE =>
                                slot.getStack.stackSize
                        }
                        inventoryPlayer.itemStack = slot.decrStackSize(Math.ceil( size/ 2.0).toInt)
                    }
                }
            case _ =>
        }

    }

    def getInventory: Array[ItemStack] = {
        val array = ArrayBuffer[ItemStack]()
        inventorySlots.foreach(array += _.getStack)
        array.toArray
    }

    def putStackInSlot(slot: Int, item: ItemStack): Unit = {
        if (slot < inventorySlots.length) {
            inventorySlots(slot).putStack(item)
        }
    }

    def isMouseOverSlot(slot: Slot, mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= slot.xPos && mouseX <= slot.xPos + 40 && mouseY >= slot.yPos && mouseY <= slot.yPos + 40
    }

    def getSlotAtPosition(x: Int, y: Int): Slot = inventorySlots.find(isMouseOverSlot(_, x, y)).orNull

//    //todo delete server
//    val listeners: ArrayBuffer[EntityPlayerMP] = ArrayBuffer[EntityPlayerMP]()
//    def addListener(listener: EntityPlayerMP) {
//        if (listeners.contains(listener)) throw new IllegalArgumentException("Listener already listening")
//        else {
//            listeners += listener
//            listener.updateCraftingInventory(this, getInventory)
//            detectAndSendChanges()
//        }
//    }
//    def detectAndSendChanges() {
//
//        for (i <- inventorySlots.indices) {
//            val itemStack = inventorySlots(i).getStack
//            listeners.foreach(_.sendSlotContents(this, i, itemStack))
//        }
//
//    }
}
