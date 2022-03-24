package ru.megains.techworld.server

import ru.megains.techworld.common.entity.mob.EntityBot
import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.packet.play.server.{SPacketJoinGame, SPacketPlayerPosLook}
import ru.megains.techworld.server.entity.EntityPlayerS
import ru.megains.techworld.server.network.handler.NetHandlerPlayServer
import ru.megains.techworld.server.world.WorldServer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class PlayerList(server:TWServer) {

    val nameToPlayerMap:mutable.HashMap[String,EntityPlayerS] = new mutable.HashMap[String,EntityPlayerS]()
    val playerEntityList: ArrayBuffer[EntityPlayerS] = new ArrayBuffer[EntityPlayerS]()

    def createPlayerForUser(name: String): EntityPlayerS = {
        nameToPlayerMap += name ->  new EntityPlayerS(name)
        getPlayerByName(name)

    }


    def getPlayerByName(name: String): EntityPlayerS = {
        nameToPlayerMap.get(name).orNull
    }

    def initializeConnectionToPlayer(networkManager: NetworkManager, player: EntityPlayerS): Unit = {

        val nethandlerplayserver: NetHandlerPlayServer = new NetHandlerPlayServer(server, networkManager, player)
        networkManager.setNetHandler(nethandlerplayserver)
        nethandlerplayserver.sendPacket(new SPacketJoinGame(player))
        nethandlerplayserver.sendPacket(new SPacketPlayerPosLook(player.posX,player.posY,player.posZ,player.rotYaw,player.rotPitch))
        player.world = server.world
        playerLoggedIn(player)
        val entityCube = new EntityBot(server.world)
        entityCube.setPosition(player.posX + Random.nextInt(50) - 25, player.posY + Random.nextInt(50), player.posZ + Random.nextInt(50) - 25)
        server.world.spawnEntityInWorld(entityCube)
    }

    def playerLoggedIn(playerIn: EntityPlayerS): Unit = {
        playerEntityList += playerIn
       // nameToPlayerMap += playerIn.name -> playerIn
        //sendPacketToAllPlayers(new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, Array[EntityPlayerMP](playerIn)))
        val worldserver: WorldServer = server.world


        worldserver.spawnEntityInWorld(playerIn)
        preparePlayer(playerIn, null)
    }

    def preparePlayer(playerIn: EntityPlayerS, worldIn: WorldServer): Unit = {
        val worldserver: WorldServer = playerIn.world.asInstanceOf[WorldServer]
        if (worldIn != null) worldIn.playerManager.removePlayer(playerIn)
        worldserver.playerManager.addPlayer(playerIn)
       // worldserver.chunkProvider.provideChunk(playerIn.posX.toInt >> 8, playerIn.posY.toInt >> 8, playerIn.posZ.toInt >> 8)
    }

    def playerLoggedOut(playerIn: EntityPlayerS): Unit = {

        val worldserver: WorldServer = playerIn.world.asInstanceOf[WorldServer]

        //writePlayerData(playerIn)
        worldserver.removeEntity(playerIn)
        worldserver.playerManager.removePlayer(playerIn)
        playerEntityList -= playerIn

        val player = nameToPlayerMap.getOrElse(playerIn.name,null)
        if (playerIn == player) {
            nameToPlayerMap -= playerIn.name
        }

    }

    def serverUpdateMountedMovingPlayer(player: EntityPlayerS): Unit = {
        player.world.asInstanceOf[WorldServer].playerManager.updateMountedMovingPlayer(player)
    }


}
