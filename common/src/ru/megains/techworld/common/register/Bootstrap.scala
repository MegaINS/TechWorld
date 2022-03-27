package ru.megains.techworld.common.register

import ru.megains.techworld.common.block.{Block, BlockAir, BlockGlass}
import ru.megains.techworld.common.entity.mob.EntityCow
import ru.megains.techworld.common.utils.Logger

object Bootstrap extends Logger {

    var isNotInit = true

    def init(gameRegister: TGameRegister): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks(gameRegister)
            log.info("Items init...")
            initItems(gameRegister)
            log.info("TileEntity init...")
            initTileEntity(GameRegister)
            log.info("Entity init...")
            initEntity(GameRegister)
        }

    }

    def initBlocks(gameRegister: TGameRegister): Unit = {
        gameRegister.registerBlock(0, BlockAir)

        gameRegister.registerBlock(1, new Block("stone"))
        gameRegister.registerBlock(2, new Block("cobblestone"))
        gameRegister.registerBlock(3, new Block("obsidian"))
        gameRegister.registerBlock(4, new Block("bedrock"))
        gameRegister.registerBlock(5, new Block("dirt"))
        gameRegister.registerBlock(6, new Block("grass"))
        gameRegister.registerBlock(7, new Block("gravel"))
        gameRegister.registerBlock(8, new Block("sand"))
        gameRegister.registerBlock(9, new BlockGlass("glass"))


        gameRegister.registerBlock(10, new Block("log_birch"))
        gameRegister.registerBlock(11, new Block("log_oak"))
        //gameRegister.registerBlock(12, new BlockLeaves("leaves_birch"))
       // gameRegister.registerBlock(13, new BlockLeaves("leaves_oak"))
    }

    def initItems(gameRegister: TGameRegister): Unit = {
       // gameRegister.registerItem(1000, new ItemPack("stick"))
       // gameRegister.registerItem(1001, new ItemMass("coal"))
       // gameRegister.registerItem(1002, new ItemSingle("helmet"))
    }

    def initTileEntity(gameRegister: TGameRegister): Unit = {
        //  gameRegister.registerTileEntity(0, classOf[TileEntityChest])
    }

    def initEntity(gameRegister: TGameRegister): Unit = {
      //  gameRegister.registerEntity(0, classOf[EntityItem])
        gameRegister.registerEntity(1, classOf[EntityCow])


    }
}

