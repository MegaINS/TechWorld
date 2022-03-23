package ru.megains.techworld.client.register

import ru.megains.techworld.client.renderer.api.{TRenderBlock, TRenderItem, TRenderTileEntity}
import ru.megains.techworld.client.renderer.block.{RenderBlockStandard, RendererMiniBlock}
import ru.megains.techworld.client.renderer.entity.TRenderEntity
import ru.megains.techworld.common.block.{Block, BlockAir, MiniBlock}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.register.GameRegister.entityData
import ru.megains.techworld.common.register.{GameRegister, RegisterRender, TGameRegister}

object GameRegisterRender extends TGameRegister {


    val blockData = new RegisterRender[TRenderBlock]

    val itemData = new RegisterRender[TRenderItem]

    val tileEntityData = new RegisterRender[TRenderTileEntity]

    val entityData = new RegisterRender[TRenderEntity]

    def registerBlockRender(block: Block, renderBlock: TRenderBlock): Unit = {
        val id: Int = GameRegister.getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + block.name + "\" not register")
        }
    }


    def registerBlockRender(name: String, renderBlock: TRenderBlock): Unit = {
        val id: Int = GameRegister.getIdByBlockName(name)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + name + "\" not register")
        }
    }

//    def registerItemRender(item: Item, tRenderItem: TRenderItem): Unit = {
//        val id: Int = GameRegister.getIdByItem(item)
//        if (id != -1) {
//            itemData.registerRender(id, tRenderItem)
//        } else {
//            println("Block +\"" + item.name + "\" not register")
//        }
//    }
    //    def registerTileEntityRender(tileEntity: Class[_<:TileEntity], aRenderBlock: TRenderTileEntity): Unit = {
    //        val id: Int = CommonGameRegister.getIdByTileEntity(tileEntity)
    //        if (id != -1) {
    //            tileEntityData.registerRender(id, aRenderBlock)
    //        } else {
    //            println("Block +\"" + tileEntity.toString + "\" not register")
    //        }
    //    }

    def registerEntityRender(entity: Class[_ <: Entity], aRenderBlock: TRenderEntity): Unit = {
        val id: Int = GameRegister.getIdByEntity(entity)
        if (id != -1) {
            entityData.registerRender(id, aRenderBlock)
        } else {
            println("Entity \"" + entity.toString + "\" not register")
        }
    }


    def registerEntity(id: Int, tileEntity: Class[_ <: Entity], tRenderEntity: TRenderEntity): Unit = {
        if (GameRegister.registerEntity(id, tileEntity)) {
            registerEntityRender(tileEntity, tRenderEntity)
        }
    }

    def registerBlock(id: Int, block: Block): Boolean = {
        if (GameRegister.registerBlock(id, block)) {
            block match {
                case BlockAir =>
                case _: MiniBlock =>
                    registerBlockRender(block, new RendererMiniBlock(block.name))
                  //  val item = GameRegister.getItemById(id)
                   // registerItemRender(item, new RenderItemBlock(item.asInstanceOf[ItemBlock]))
                case _ =>
                    registerBlockRender(block, new RenderBlockStandard(block.name))
                   // val item = GameRegister.getItemById(id)
                  //  registerItemRender(item, new RenderItemBlock(item.asInstanceOf[ItemBlock]))
            }


            // block match {
            // case blockWG:BlockWG =>  registerBlockRender(block,RenderBlockWG(blockWG))
            // case _ =>   registerBlockRender(block,RenderBlockStandart(block))
            //  }
            if (block.name != "air") {
              //  val item = GameRegister.getItemById(id)
               // registerItemRender(item, new RenderItemBlock(item.asInstanceOf[ItemBlock]))
            }
            true
        } else {
            false
        }
    }

    def registerBlock(id: Int, block: Block, tRenderBlock: TRenderBlock): Unit = {
        if (GameRegister.registerBlock(id, block)) {
            registerBlockRender(block, tRenderBlock)
          //  val item = GameRegister.getItemById(id)
           // registerItemRender(item, new RenderItemBlock(item.asInstanceOf[ItemBlock]))
        }
    }

//    def registerItem(id: Int, item: Item): Boolean = {
//        if (GameRegister.registerItem(id, item)) {
//            registerItemRender(item, new RenderItemStandart(item))
//            true
//        } else {
//            false
//        }
//    }

//    def registerItem(id: Int, item: Item, tRenderItem: TRenderItem): Boolean = {
//        if (GameRegister.registerItem(id, item)) {
//            registerItemRender(item, tRenderItem)
//            true
//        } else {
//            false
//        }
//    }

    //
    //    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity],tRenderTileEntity: TRenderTileEntity): Unit = {
    //        if(ru.megains.tartess.common.register.GameRegister.registerTileEntity(id, tileEntity)){
    //            registerTileEntityRender(tileEntity,tRenderTileEntity)
    //        }
    //
    //    }
    //
    //    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean = {
    //        ru.megains.tartess.common.register.GameRegister.registerTileEntity(id, tileEntity)
    //    }
    def registerEntity(id: Int, tileEntity: Class[_ <: Entity]): Boolean = {
        GameRegister.registerEntity(id, tileEntity)
    }

    def getBlockRender(block: Block): TRenderBlock = {
        blockData.getRender(GameRegister.getIdByBlock(block))
    }

   // def getItemRender(item: Item): TRenderItem = itemData.getRender(GameRegister.getIdByItem(item))

    // def getTileEntityRender(tileEntity: TileEntity): TRenderTileEntity = tileEntityData.getRender(CommonGameRegister.getIdByTileEntity(tileEntity.getClass))
     def getEntityRender(entity: Entity): TRenderEntity = entityData.getRender(GameRegister.getIdByEntity(entity.getClass))
}
