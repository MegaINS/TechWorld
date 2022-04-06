package ru.megains.techworld.common.tileentity

import ru.megains.techworld.common.block.{BlockPos, BlockState}
import ru.megains.techworld.common.container.Container
import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.inventory.Inventory
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.item.{Item, ItemPack, ItemType}
import ru.megains.techworld.common.nbt.NBTType
import ru.megains.techworld.common.nbt.NBTType.{EnumNBTCompound, EnumNBTEnd}
import ru.megains.techworld.common.nbt.tag.NBTCompound

abstract class TileEntityInventory(pos:BlockState, slotSize:Int) extends TileEntity(pos) with Inventory{

    var slots:Array[ItemStack] = new Array[ItemStack](slotSize)

    override def getStackInSlot(index: Int): ItemStack = {
        slots(index)
    }

    override def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit = {
        slots(index) = itemStack
    }

    override def decrStackSize(index: Int, size: Int): ItemStack = {

        val stack = slots(index)
        var newStack: ItemStack = null
        if (stack != null) {
            stack.item.itemType match {
                case ItemType.STACK | ItemType.SINGLE  =>
                    if (stack.stackSize <= size) {
                        newStack = stack
                        slots(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackSize < 1) {
                            slots(index) = null
                        }
                    }
                case ItemType.MASS =>
                    if (stack.stackMass <= size) {
                        newStack = stack
                        slots(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackMass < 1) {
                            slots(index) = null
                        }
                    }
            }

        }
        newStack
    }

    def getContainer(player: EntityPlayer): Container

    override def writeToNBT(data: NBTCompound): Unit = {
        super.writeToNBT(data)
        val inventory = data.createList("slots", EnumNBTCompound)
        for (i <- slots.indices) {
            val compound = inventory.createCompound()
            val itemStack = slots(i)

            if (itemStack != null) {
                compound.setValue("id", Item.getIdFromItem(itemStack.item))
                compound.setValue("stackSize", itemStack.stackSize)
            } else {
                compound.setValue("id", -1)
            }
        }
    }

    override def readFromNBT(data: NBTCompound): Unit = {
        super.readFromNBT(data)
        val inventory = data.getList("slots")
        for (i <- slots.indices) {
            val compound = inventory.getCompound(i)
            val id: Int = compound.getInt("id")
            if (id != -1) {
                val itemStack = new ItemStack(Item.getItemById(id), compound.getInt("stackSize"))
                slots(i) = itemStack
            }
        }
    }
}
