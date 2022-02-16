package ru.megains.techworld.client.network.handler

import ru.megains.techworld.client.{PlayerController, TechWorld}
import ru.megains.techworld.client.renderer.gui.{GuiDownloadTerrain, GuiScreen}
import ru.megains.techworld.client.world.WorldClient
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.handler.{INetHandler, INetHandlerPlayClient}
import ru.megains.techworld.common.network.packet.Packet
import ru.megains.techworld.common.network.packet.play.server.{SPacketJoinGame, SPacketPlayerPosLook}
import ru.megains.techworld.common.utils.Logger

class NetHandlerPlayClient(game: TechWorld, previousScene: GuiScreen, val netManager: NetworkManager) extends INetHandlerPlayClient with Logger {


    var clientWorld: WorldClient = _
    var doneLoadingTerrain: Boolean = false


    override def sendPacket(packetIn: Packet[_ <: INetHandler]): Unit = {
        netManager.sendPacket(packetIn)
    }

    override def handleJoinGame(packetIn: SPacketJoinGame): Unit = {

        game.setScreen(new GuiDownloadTerrain(game: TechWorld, this))
        game.playerController = new PlayerController(game, this)
        clientWorld = new WorldClient()
        game.setWorld(clientWorld)

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
    //    def handleChunkData(packetIn: SPacketChunkData): Unit = {
    //
    ////        clientWorldController.doPreChunk(packetIn.position, loadChunk = true)
    ////        if(!packetIn.chunkVoid){
    ////            val chunk = clientWorldController.getChunk(packetIn.position)
    ////            chunk.blockStorage = packetIn.blockStorage
    ////            packetIn.tileEntityMap.foreach(chunk.addTileEntity)
    ////            gameController.worldRenderer.reRender(packetIn.position)
    ////        }
    //
    //
    //
    //    }
    //
    //
    //    def handleBlockChange(packetIn: SPacketBlockChange) {
    //      //  clientWorldController.invalidateRegionAndSetBlock(packetIn.block)
    //
    //    }
    //
    //    def handleMultiBlockChange(packetIn: SPacketMultiBlockChange) {
    //        for (blockData <- packetIn.changedBlocks) {
    //           // clientWorldController.invalidateRegionAndSetBlock(blockData)
    //        }
    //    }
    //
    //    override def handleSetSlot(packetIn: SPacketSetSlot): Unit = {
    //        if (packetIn.slot == -1) {
    //          //  gameController.player.inventory.itemStack = packetIn.item
    //        } else {
    //          //  gameController.player.openContainer.putStackInSlot(packetIn.slot, packetIn.item)
    //        }
    //
    //    }
    //
    //    override def handleWindowItems(packetIn: SPacketWindowItems): Unit = {
    //      //  val openContainer = gameController.player.openContainer
    //
    //        for (i <- packetIn.itemStacks.indices) {
    //          //  openContainer.putStackInSlot(i, packetIn.itemStacks(i))
    //        }
    //    }
    //
    //    override def handlePlayerListItem(packetIn: SPacketPlayerListItem): Unit = {
    //
    //    }
    //
    //    override def handleChangeGameState(packetIn: SPacketChangeGameState): Unit = {
    //        packetIn.state match {
    //           // case 3 => gameController.playerController.setGameType(GameType(packetIn.value))
    //            case _ =>
    //        }
    //    }


}


