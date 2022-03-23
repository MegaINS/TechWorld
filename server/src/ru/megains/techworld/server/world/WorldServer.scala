package ru.megains.techworld.server.world

import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.world.World
import ru.megains.techworld.server.{EntityTracker, PlayerChunkMap}
import ru.megains.techworld.server.entity.EntityPlayerS

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldServer extends World{

   val playerManager: PlayerChunkMap = new PlayerChunkMap(this)
   val entityTracker: EntityTracker = new EntityTracker(this)
   val chunkProvider:ChunkProviderServer = new ChunkProviderServer(this)
   val entityIdMap = new mutable.HashMap[Int,Entity]()

   def tick(): Unit = {
      playerManager.tick()
   }

   override def onEntityAdded(entity: Entity): Unit = {
      entityTracker.addEntityToTracker(entity)
      entityIdMap += entity.id -> entity
   }

   override def getEntityByID(id: Int): Entity = {
      entityIdMap.get(id).orNull
   }
}
