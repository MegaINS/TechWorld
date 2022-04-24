package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.entity.EntityOtherPlayerC
import ru.megains.techworld.client.{PlayerController, TechWorld}
import ru.megains.techworld.client.renderer.gui.{GuiDownloadTerrain, GuiScreen}
import ru.megains.techworld.client.world.WorldClient
import ru.megains.techworld.common.entity.{Entity, EntityItem, EntityLivingBase, GameType}
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.{INetHandler, INetHandlerPlayClient}
import ru.megains.techworld.common.network.packet.Packet
import ru.megains.techworld.common.network.packet.play.server.{SPacketBlockChange, SPacketChangeGameState, SPacketChunkData, SPacketDestroyEntities, SPacketEntity, SPacketEntityTeleport, SPacketEntityVelocity, SPacketJoinGame, SPacketMobSpawn, SPacketMultiBlockChange, SPacketPlayerPosLook, SPacketSetSlot, SPacketSpawnObject, SPacketSpawnPlayer, SPacketUnloadChunk, SPacketWindowItems}
import ru.megains.techworld.common.register.Entities
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.Chunk

class NetHandlerPlayClient(game: TechWorld, previousScene: GuiScreen, val netManager: NetworkManager) extends INetHandlerPlayClient with Logger {


    var worldClient: WorldClient = _
    var doneLoadingTerrain: Boolean = false


    override def sendPacket(packetIn: Packet[_ <: INetHandler]): Unit = {
        netManager.sendPacket(packetIn)
    }

    override def handleJoinGame(packetIn: SPacketJoinGame): Unit = {

        game.setScreen(new GuiDownloadTerrain(game: TechWorld, this))
        game.playerController = new PlayerController(game, this)
        worldClient = new WorldClient(game)
        game.setWorld(worldClient)

        game.player.id = packetIn.playerId
        //        gameScene = new SceneGame(gameController)
        //        gameController.setScene(gameScene)
        //

        //
        //        clientWorldController = new WorldClient()


        //        gameController.loadWorld(clientWorldController)
        //        gameController.guiManager.setGuiScreen(new GuiDownloadTerrain(this))
        //        gameController.player.setEntityId(packetIn.getPlayerId)
        //        //  currentServerMaxPlayers = packetIn.getMaxPlayers
        //        // gameController.player.setReducedDebug(packetIn.isReducedDebugInfo)
        //
        //        //  gameController.gameSettings.sendSettingsToServer()
        //        //  netManager.sendPacket(new CPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer).writeString(ClientBrandRetriever.getClientModName)))
        //
        //        this.gameController.playerController = new PlayerControllerMP(this.gameController, this)
        //        this.clientWorldController = new WorldClient(this, new WorldSettings(0L, p_147282_1_.func_149198_e, false, p_147282_1_.func_149195_d, p_147282_1_.func_149196_i), p_147282_1_.func_149194_f, p_147282_1_.func_149192_g, this.gameController.mcProfiler)
        //        this.clientWorldController.isClient = true
        //        this.gameController.loadWorld(this.clientWorldController)
        //        this.gameController.thePlayer.dimension = p_147282_1_.func_149194_f
        //        this.gameController.displayGuiScreen(new GuiDownloadTerrain(this))
        //        this.gameController.thePlayer.setEntityId(p_147282_1_.func_149197_c)
        //        this.currentServerMaxPlayers = p_147282_1_.func_149193_h
        //        this.gameController.playerController.setGameType(p_147282_1_.func_149198_e)
        //        this.gameController.gameSettings.sendSettingsToServer()
        //        this.netManager.scheduleOutboundPacket(new C17PacketCustomPayload("MC|Brand", ClientBrandRetriever.getClientModName.getBytes(Charsets.UTF_8)), new Array[GenericFutureListener[_ <: Future[_$1]]](0))

    }

    override def handlePlayerPosLook(packetIn: SPacketPlayerPosLook): Unit = {
        val player = game.player


        game.setScreen(null)
        //       // val entityplayer: EntityPlayer = gameController.player
        //        var d0: Float = packetIn.x
        //        var d1: Float = packetIn.y
        //        var d2: Float = packetIn.z
        //        var f: Float = packetIn.yaw
        //        var f1: Float = packetIn.pitch
        //        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.X)) d0 += entityplayer.posX
        //        else entityplayer.motionX = 0.0f
        //        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.Y)) d1 += entityplayer.posY
        //        else entityplayer.motionY = 0.0f
        //        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.Z)) d2 += entityplayer.posZ
        //        else entityplayer.motionZ = 0.0f
        //        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) f1 += entityplayer.rotationPitch
        //        if (packetIn.flags.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) f += entityplayer.rotationYaw
        player.setPositionAndRotation(packetIn.x, packetIn.y, packetIn.z, packetIn.yaw, packetIn.pitch)
        //        // netManager.sendPacket(new CPacketConfirmTeleport(packetIn.getTeleportId))
        //
        //        netManager.sendPacket(new CPacketPlayer.PositionRotation(entityplayer.posX, entityplayer.body.minY, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch, false))
        //        if (!doneLoadingTerrain) {
        //            gameController.player.prevPosX = gameController.player.posX
        //            gameController.player.prevPosY = gameController.player.posY
        //            gameController.player.prevPosZ = gameController.player.posZ
        //            doneLoadingTerrain = true
        //            gameController.guiManager.setGuiScreen(null)
        //        }
    }

    override def disconnect(msg: String): Unit = {

    }


    //    def handleHeldItemChange(packetIn: SPacketHeldItemChange): Unit = {
    //        // if (InventoryPlayer.isHotBar(packetIn.heldItemHotbarIndex)) this.gameController.player.inventory.stackSelect = packetIn.heldItemHotbarIndex
    //    }

    //
    def handleChunkData(packetIn: SPacketChunkData): Unit = {

        worldClient.doPreChunk(packetIn.position, loadChunk = true)
        if (!packetIn.isEmpty) {
            val chunk: Chunk = worldClient.getChunk(packetIn.position)
            chunk.blockStorage = packetIn.blockStorage
            chunk.isEmpty = packetIn.isEmpty
            packetIn.tileEntityMap.foreach(chunk.addTileEntity)
            game.rendererGame.rendererWorld.reRender(packetIn.position)
        }


    }

    def handleBlockChange(packetIn: SPacketBlockChange): Unit = {
        worldClient.invalidateRegionAndSetBlock(packetIn.block)
    }

    def handleMultiBlockChange(packetIn: SPacketMultiBlockChange): Unit = {
        for (blockData <- packetIn.changedBlocks) {
            worldClient.invalidateRegionAndSetBlock(blockData)
        }
    }

    override def handleSetSlot(packetIn: SPacketSetSlot): Unit = {
        if (packetIn.slot == -1) {
            game.player.inventory.itemStack = packetIn.item
        } else {
            if (packetIn.windowId == -1) {
                game.player.inventoryContainer.putStackInSlot(packetIn.slot, packetIn.item)
            } else {
                game.player.openContainer.putStackInSlot(packetIn.slot, packetIn.item)
            }


        }

    }

    override def handleWindowItems(packetIn: SPacketWindowItems): Unit = {
        val openContainer = game.player.openContainer

        for (i <- packetIn.itemStacks.indices) {
            openContainer.putStackInSlot(i, packetIn.itemStacks(i))
        }
    }

    //
    //    override def handlePlayerListItem(packetIn: SPacketPlayerListItem): Unit = {
    //
    //    }
    //
    override def handleChangeGameState(packetIn: SPacketChangeGameState): Unit = {
        packetIn.state match {
            case 3 => game.player.gameType = GameType(packetIn.value)
            case _ =>
        }
    }

    override def handleUnloadChunk(packetIn: SPacketUnloadChunk): Unit = {
        worldClient.doPreChunk(packetIn.position, loadChunk = false)
    }

    override def handleSpawnPlayer(packetIn: SPacketSpawnPlayer): Unit = {
        val var2: Float = (packetIn.xPosition.toDouble / 32.0D).toFloat
        val var4: Float = (packetIn.yPosition.toDouble / 32.0D).toFloat
        val var6: Float = (packetIn.zPosition.toDouble / 32.0D).toFloat
        val var8: Float = (packetIn.rotation * 360).toFloat / 256.0F
        val var9: Float = (packetIn.pitch * 360).toFloat / 256.0F
        val otherPlayerC = new EntityOtherPlayerC(game.world, packetIn.name)


        otherPlayerC.prevPosX = packetIn.xPosition
        otherPlayerC.lastTickPosX = packetIn.xPosition
        otherPlayerC.serverPosX = packetIn.xPosition
        otherPlayerC.prevPosY = packetIn.yPosition
        otherPlayerC.lastTickPosY = packetIn.yPosition
        otherPlayerC.serverPosY = packetIn.yPosition
        otherPlayerC.prevPosZ = packetIn.zPosition
        otherPlayerC.lastTickPosZ = packetIn.zPosition
        otherPlayerC.serverPosZ = packetIn.zPosition
        //  val var11 = packetIn.currentItem

        //if (var11 == 0) otherPlayerC.inventory.mainInventory(otherPlayerC.inventory.currentItem) = null
        // else otherPlayerC.inventory.mainInventory(otherPlayerC.inventory.currentItem) = new ItemStack(var11, 1, 0)

        otherPlayerC.setPositionAndRotation(var2, var4, var6, var8, var9)
        worldClient.addEntityToWorld(packetIn.entityId, otherPlayerC)
        // val var12 = packetIn.func_73509_c

        // if (var12 != null) otherPlayerC.getDataWatcher.updateWatchedObjectsFromList(var12)
    }

    override def handleEntityVelocity(packetIn: SPacketEntityVelocity): Unit = {
        val entity = worldClient.getEntityByID(packetIn.entityId)

        if (entity != null) entity.setVelocity(packetIn.motionX.toFloat / 8000.0F, packetIn.motionY.toFloat / 8000.0F, packetIn.motionZ.toFloat / 8000.0F)
    }

    override def handleEntityTeleport(packetIn: SPacketEntityTeleport): Unit = {

        val entity = worldClient.getEntityByID(packetIn.entityId)

        if (entity != null) {
            entity.serverPosX = packetIn.posX
            entity.serverPosY = packetIn.posY
            entity.serverPosZ = packetIn.posZ
            val var3 = entity.serverPosX.toFloat / 32.0F
            val var5 = entity.serverPosY.toFloat / 32.0F + 0.015625F
            val var7 = entity.serverPosZ.toFloat / 32.0F
            val var9 = (packetIn.rotation * 360).toFloat / 256.0F
            val var10 = (packetIn.pitch * 360).toFloat / 256.0F
            entity.setPositionAndRotation2(var3, var5, var7, var9, var10, 3)
        }
    }

    override def handleEntityMovement(packetIn: SPacketEntity): Unit = {
        val entity: Entity = worldClient.getEntityByID(packetIn.entityId)

        if (entity != null) {
            entity.serverPosX += packetIn.moveX
            entity.serverPosY += packetIn.moveY
            entity.serverPosZ += packetIn.moveZ
            val var3 = entity.serverPosX.toFloat / 32.0F
            val var5 = entity.serverPosY.toFloat / 32.0F
            val var7 = entity.serverPosZ.toFloat / 32.0F
            val rotYaw = if (packetIn.isLook) (packetIn.rotYaw * 360).toFloat / 256.0F
            else entity.rotYaw
            val rotPitch = if (packetIn.isLook) (packetIn.rotPitch * 360).toFloat / 256.0F
            else entity.rotPitch
            entity.setPositionAndRotation2(var3, var5, var7, rotYaw, rotPitch, 3)
        }
    }

    override def handleEntitySpawnMob(packetIn: SPacketMobSpawn): Unit = {
        val posX = packetIn.posX.toFloat / 32.0f
        val posY = packetIn.posY.toFloat / 32.0f
        val posZ = packetIn.posZ.toFloat / 32.0f
        val rotYaw = (packetIn.rotYaw * 360).toFloat / 256.0F
        val rotPitch = (packetIn.rotPitch * 360).toFloat / 256.0F
        val entity = Entities.createEntityByID(packetIn.entityClassId, game.world).asInstanceOf[EntityLivingBase]
        entity.serverPosX = packetIn.posX
        entity.serverPosY = packetIn.posY
        entity.serverPosZ = packetIn.posZ
        //entity.rotationYawHead = (packetIn.func_149032_n * 360).toFloat / 256.0F
        //        val var11 = entity.getParts
        //        if (var11 != null) {
        //            val var12 = packetIn.func_149024_d - entity.getEntityId
        //            for (var13 <- 0 until var11.length) {
        //                var11(var13).setEntityId(var11(var13).getEntityId + var12)
        //            }
        //        }
        entity.id = packetIn.entityId
        entity.setPositionAndRotation(posX, posY, posZ, rotYaw, rotPitch)
        entity.motionX = packetIn.motionX.toFloat / 8000.0F
        entity.motionY = packetIn.motionY.toFloat / 8000.0F
        entity.motionZ = packetIn.motionZ.toFloat / 8000.0F
        worldClient.addEntityToWorld(packetIn.entityId, entity)
        // val var14 = packetIn.func_149027_c
        // if (var14 != null) entity.getDataWatcher.updateWatchedObjectsFromList(var14)
    }

    override def handleDestroyEntities(packetIn: SPacketDestroyEntities): Unit = {
        for (var2 <- packetIn.entitiesId.indices) {
            worldClient.removeEntityFromWorld(packetIn.entitiesId(var2))
        }
    }

    override def handleSpawnObject(packetIn: SPacketSpawnObject): Unit = {
        val var2: Float = packetIn.posX.toFloat / 32.0F
        val var4: Float = packetIn.posY.toFloat / 32.0F
        val var6: Float = packetIn.posZ.toFloat / 32.0F
        var entity: Entity = null
        //        if (packetIn.func_148993_l == 10) entity = EntityMinecart.createMinecart(this.clientWorldController, var2, var4, var6, packetIn.func_149009_m)
        //        else if (packetIn.func_148993_l == 90) {
        //            val var9 = this.clientWorldController.getEntityByID(packetIn.func_149009_m)
        //            if (var9.isInstanceOf[EntityPlayer]) entity = new EntityFishHook(this.clientWorldController, var2, var4, var6, var9.asInstanceOf[EntityPlayer])
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 60) entity = new EntityArrow(this.clientWorldController, var2, var4, var6)
        //        else if (packetIn.func_148993_l == 61) entity = new EntitySnowball(this.clientWorldController, var2, var4, var6)
        //        else if (packetIn.func_148993_l == 71) {
        //            entity = new EntityItemFrame(this.clientWorldController, var2.toInt, var4.toInt, var6.toInt, packetIn.func_149009_m)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 77) {
        //            entity = new EntityLeashKnot(this.clientWorldController, var2.toInt, var4.toInt, var6.toInt)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 65) entity = new EntityEnderPearl(this.clientWorldController, var2, var4, var6)
        //        else if (packetIn.func_148993_l == 72) entity = new EntityEnderEye(this.clientWorldController, var2, var4, var6)
        //        else if (packetIn.func_148993_l == 76) entity = new EntityFireworkRocket(this.clientWorldController, var2, var4, var6, null.asInstanceOf[ItemStack])
        //        else if (packetIn.func_148993_l == 63) {
        //            entity = new EntityLargeFireball(this.clientWorldController, var2, var4, var6, packetIn.func_149010_g.toDouble / 8000.0D, packetIn.func_149004_h.toDouble / 8000.0D, packetIn.func_148999_i.toDouble / 8000.0D)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 64) {
        //            entity = new EntitySmallFireball(this.clientWorldController, var2, var4, var6, packetIn.func_149010_g.toDouble / 8000.0D, packetIn.func_149004_h.toDouble / 8000.0D, packetIn.func_148999_i.toDouble / 8000.0D)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 66) {
        //            entity = new EntityWitherSkull(this.clientWorldController, var2, var4, var6, packetIn.func_149010_g.toDouble / 8000.0D, packetIn.func_149004_h.toDouble / 8000.0D, packetIn.func_148999_i.toDouble / 8000.0D)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 62) entity = new EntityEgg(this.clientWorldController, var2, var4, var6)
        //        else if (packetIn.func_148993_l == 73) {
        //            entity = new EntityPotion(this.clientWorldController, var2, var4, var6, packetIn.func_149009_m)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 75) {
        //            entity = new EntityExpBottle(this.clientWorldController, var2, var4, var6)
        //            packetIn.func_149002_g(0)
        //        }
        //        else if (packetIn.func_148993_l == 1) entity = new EntityBoat(this.clientWorldController, var2, var4, var6)
        //        else if (packetIn.func_148993_l == 50) entity = new EntityTNTPrimed(this.clientWorldController, var2, var4, var6, null.asInstanceOf[EntityLivingBase])
        //        else if (packetIn.func_148993_l == 51) entity = new EntityEnderCrystal(this.clientWorldController, var2, var4, var6)
        //else if (packetIn.func_148993_l == 2) 
        entity = new EntityItem()
        entity.world = worldClient
        entity.setPosition(var2, var4, var6)
        entity.readFromNBT(packetIn.nbt)
        //        else if (packetIn.func_148993_l == 70) {
        //            entity = new EntityFallingBlock(this.clientWorldController, var2, var4, var6, Block.getBlockById(packetIn.func_149009_m & 65535), packetIn.func_149009_m >> 16)
        //            packetIn.func_149002_g(0)
        //        }
        if (entity != null) {
            entity.serverPosX = packetIn.posX
            entity.serverPosY = packetIn.posY
            entity.serverPosZ = packetIn.posZ
            entity.rotPitch = (packetIn.rotPitch * 360).toFloat / 256.0F
            entity.rotYaw = (packetIn.rotYaw * 360).toFloat / 256.0F
            //            val var12 = entity.getParts
            //            if (var12 != null) {
            //                val var10 = packetIn.func_149001_c - entity.getEntityId
            //                for (var11 <- 0 until var12.length) {
            //                    var12(var11).setEntityId(var12(var11).getEntityId + var10)
            //                }
            //            }
            entity.id = packetIn.entityId
            worldClient.addEntityToWorld(packetIn.entityId, entity)
            if (packetIn.isMotion) {
                if (packetIn.entityClassId == 60) {
                    val var13 = worldClient.getEntityByID(packetIn.entityId)
                    if (var13.isInstanceOf[EntityLivingBase]) {
                        //val var14 = entity.asInstanceOf[EntityArrow]
                        // var14.shootingEntity = var13
                    }
                }
                entity.setVelocity(packetIn.motionX.toFloat / 8000.0F, packetIn.motionY.toFloat / 8000.0F, packetIn.motionZ.toFloat / 8000.0F)
            }
        }
    }
}


