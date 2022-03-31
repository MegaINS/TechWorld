package ru.megains.techworld.server.entity

import ru.megains.techworld.common.block.BlockPos
import ru.megains.techworld.common.container.{Container, InventoryListener}
import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.INetHandler
import ru.megains.techworld.common.network.packet.play.server.{SPacketDestroyEntities, SPacketSetSlot, SPacketWindowItems}
import ru.megains.techworld.common.register.GameRegister
import ru.megains.techworld.common.world.World
import ru.megains.techworld.server.PlayerInteractionManager
import ru.megains.techworld.server.world.WorldServer

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class EntityPlayerS(val name:String,val interactionManager: PlayerInteractionManager) extends EntityPlayer with InventoryListener{



    interactionManager.thisPlayerMP = this
    var managedPosZ: Float = 0
    var managedPosY: Float = 0
    var managedPosX: Float = 0

    var connection:INetHandler = _
    GameRegister.getItems.foreach(i => inventory.addItemStackToInventory(new ItemStack(i, Random.nextInt(100))))
    val destroyedItemsNetCache = new ArrayBuffer[Int]()


    override def update(): Unit = {
        super.update()

        while (destroyedItemsNetCache.nonEmpty) {

            val size = Math.min(destroyedItemsNetCache.size, 127)

            connection.sendPacket(new SPacketDestroyEntities(destroyedItemsNetCache.slice(0,size).toArray))

            destroyedItemsNetCache.remove(0,size)

        }

        openContainer.detectAndSendChanges()
    }


    def worldServer:WorldServer = world.asInstanceOf[WorldServer]

    def destroyEntityNetCache(entity:Entity): Unit = {
        if (entity.isInstanceOf[EntityPlayer]) connection.sendPacket(new SPacketDestroyEntities(Array[Int](entity.id)))
        else destroyedItemsNetCache += entity.id
    }

    def addSelfToInternalCraftingInventory(): Unit = {
        openContainer.addListener(this)
    }

    def sendSlotContents(containerToSend: Container, slotInd: Int, stack: ItemStack): Unit = {
        connection.sendPacket(new SPacketSetSlot(-1, slotInd, stack))
    }

    def updateCraftingInventory(containerToSend: Container, itemsList: Array[ItemStack]): Unit = {
        connection.sendPacket(new SPacketWindowItems(-1, itemsList))
        updateHeldItem()
    }

    def updateHeldItem(): Unit = {
        connection.sendPacket(new SPacketSetSlot(-1, -1, inventory.itemStack))
    }



    def closeContainer(): Unit = {
        //this.openContainer.onContainerClosed(this)
        this.openContainer = this.inventoryContainer
    }
   // override def openGui(world: World, pos: BlockPos): Unit = {
       // val tileEntity = world.getTileEntity(pos)
//        tileEntity match {
//            case inv:ATileEntityInventory =>
//                openContainer = inv.getContainer(this)
//                openContainer.addListener(this)
//            case _=>
//        }

   // }

}
