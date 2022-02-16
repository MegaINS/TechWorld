package ru.megains.techworld.server

import ru.megains.techworld.common.network.NetworkManager
import ru.megains.techworld.common.network.packet.play.server.{SPacketJoinGame, SPacketPlayerPosLook}
import ru.megains.techworld.server.entity.EntityPlayerS

import scala.collection.mutable

class PlayerList {




    val nameToPlayerMap:mutable.HashMap[String,EntityPlayerS] = new mutable.HashMap[String,EntityPlayerS]()


    def createPlayerForUser(name: String): EntityPlayerS = {
        nameToPlayerMap += name ->  new EntityPlayerS
        getPlayerByName(name)

    }


    def getPlayerByName(name: String): EntityPlayerS = {
        nameToPlayerMap.get(name).orNull
    }

    def initializeConnectionToPlayer(networkManager: NetworkManager, player: EntityPlayerS): Unit = {
        networkManager.sendPacket(new SPacketJoinGame())
        networkManager.sendPacket(new SPacketPlayerPosLook(player.posX,player.posY,player.posZ,player.rotYaw,player.rotPitch))
    }


}
