package ru.megains.techworld.common.register

import ru.megains.techworld.common.block.{Block, BlockAir}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.utils.Logger


object GameRegister extends TGameRegister with Logger{

    val blockData = new RegisterNamespace[Block]

  //  val itemData = new RegisterNamespace[Item]

   // val tileEntityData = new RegisterNamespace[Class[_<:TileEntity]]

    val entityData = new RegisterNamespace[Class[_<:Entity]]




//    def getTileEntityById(id: Int):Class[_<:TileEntity] = {
//        tileEntityData.getObject(id)
//    }
//
//    def getIdByTileEntity(tileEntity: Class[_ <: TileEntity]): Int = {
//        tileEntityData.getIdByObject(tileEntity)
//    }
//
    def getEntityById(id: Int):Class[_<:Entity] = {
        entityData.getObject(id)
    }

    def getIdByEntity(tileEntity: Class[_ <: Entity]): Int = {
        entityData.getIdByObject(tileEntity)
    }

    def getIdByBlock(block: Block): Int = blockData.getIdByObject(block)

    def getBlockById(id: Int): Block = blockData.getObject(id)

    def getBlockByName(name: String): Block = blockData.getObject(name)

   // def getIdByItem(item: Item): Int = itemData.getIdByObject(item)

   // def getItemById(id: Int): Item = itemData.getObject(id)

   // def getItemFromBlock(block: Block): Item = itemData.getObject(blockData.getIdByObject(block))

    def getIdByBlockName(name: String): Int = blockData.getIdByName(name)


    def registerBlock(id: Int, block: Block): Boolean = {
        if (blockData.contains(block)) {
            println("Block \"" + block.name + "\" already register")
        } else {
            if (blockData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (blockData.contains(block.name)) {
                    println("Name \"" + block.name + "\" not single")
                } else {
                    blockData.registerObject(id, block.name, block)
                    block match {
                        case BlockAir =>

                        case _ =>
                           // registerItem(id, new ItemBlock(block))
                    }

                    return true
                }
            }
        }
        false
    }



//    def registerItem(id: Int, item: Item): Boolean = {
//        if (itemData.contains(item)) {
//            println("Item \"" + item.name + "\" already register")
//        } else {
//            if (itemData.contains(id)) {
//                println("Id \"" + id + "\" not single")
//            } else {
//                if (itemData.contains(item.name)) {
//                    println("Name \"" + item.name + "\" not single")
//                } else {
//                    itemData.registerObject(id, item.name, item)
//
//                    return true
//                }
//            }
//        }
//        false
//    }
//    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean = {
//        if (tileEntityData.contains(tileEntity)) {
//            println("TileEntity \"" + tileEntity.toString + "\" already register")
//        } else {
//            if (tileEntityData.contains(id)) {
//                println("Id \"" + id + "\" not single")
//            } else {
//                if (tileEntityData.contains(tileEntity.toString)) {
//                    println("Name \"" + tileEntity.toString + "\" not single")
//                } else {
//                    tileEntityData.registerObject(id, tileEntity.toString, tileEntity)
//                    return true
//                }
//            }
//        }
//        false
//    }
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean = {
        if (entityData.contains(tileEntity)) {
            println("Entity \"" + tileEntity.toString + "\" already register")
        } else {
            if (entityData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (entityData.contains(tileEntity.toString)) {
                    println("Name \"" + tileEntity.toString + "\" not single")
                } else {
                    entityData.registerObject(id, tileEntity.toString, tileEntity)
                    return true
                }
            }
        }
        false
    }

    def getBlocks: Iterable[Block] = blockData.getObjects
   // def getItems: Iterable[Item] = itemData.getObjects
    //def getTileEntities: Iterable[Class[_ <: TileEntity]] = tileEntityData.getObjects
    def getEntities: Iterable[Class[_ <: Entity]] = entityData.getObjects


}

sealed class GameRegister