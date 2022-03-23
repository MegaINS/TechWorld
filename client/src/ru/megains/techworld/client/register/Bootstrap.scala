package ru.megains.techworld.client.register

import ru.megains.techworld.client.entity.EntityOtherPlayerC
import ru.megains.techworld.client.renderer.block.{RenderBlockGlass, RenderBlockGrass, RenderBlockLeaves, RenderBlockLog}
import ru.megains.techworld.client.renderer.entity.RenderEntityCube
import ru.megains.techworld.common.register.{Bootstrap => BootstrapCommon}
import ru.megains.techworld.common.utils.Logger

object Bootstrap extends Logger {

    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks()
            log.info("Items init...")
            initItems()
            log.info("TileEntity init...")
            initTileEntity()
            log.info("Entity init...")
            initEntity()
        }

    }

    def initBlocks(): Unit = {
        BootstrapCommon.initBlocks(GameRegisterRender)

        GameRegisterRender.registerBlockRender("grass", new RenderBlockGrass("grass"))
        GameRegisterRender.registerBlockRender("glass", new RenderBlockGlass("glass"))

       // GameRegisterRender.registerBlockRender("leaves_oak", new RenderBlockLeaves("leaves_oak"))
      //  GameRegisterRender.registerBlockRender("leaves_birch", new RenderBlockLeaves("leaves_birch"))

        GameRegisterRender.registerBlockRender("log_birch", new RenderBlockLog("log_birch"))
        GameRegisterRender.registerBlockRender("log_oak", new RenderBlockLog("log_oak"))


    }

    def initItems(): Unit = {
      //  BootstrapCommon.initItems(GameRegisterRender)
    }

    def initTileEntity(): Unit = {
      //  BootstrapCommon.initTileEntity(GameRegisterRender)
        // GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
    }

    def initEntity(): Unit = {
        BootstrapCommon.initEntity(GameRegisterRender)

        GameRegisterRender.registerEntity(0,classOf[EntityOtherPlayerC], RenderEntityCube)
    }
}
