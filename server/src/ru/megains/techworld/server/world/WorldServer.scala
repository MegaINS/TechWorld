package ru.megains.techworld.server.world

import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.world.World
import ru.megains.techworld.server.{EntityTracker, PlayerChunkMap}
import ru.megains.techworld.server.entity.EntityPlayerS

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldServer extends World {


    val playerManager = new PlayerChunkMap(this)
    val entityTracker = new EntityTracker(this)
    val chunkProvider = new ChunkProviderServer(this)
    val entityIdMap = new mutable.HashMap[Int, Entity]()

    override def update(): Unit = {
        super.update()
        playerManager.tick()
    }

    override def onEntityAdded(entity: Entity): Unit = {
        entityTracker.addEntityToTracker(entity)
        entityIdMap += entity.id -> entity
    }

    override def onEntityRemoved(entity: Entity): Unit = {
        entityIdMap -= entity.id
        entityTracker.removeEntityFromAllTrackingPlayers(entity)
    }

    override def getEntityByID(id: Int): Entity = entityIdMap.get(id).orNull

    override val isServer: Boolean = true
    override val isClient: Boolean = false
}
