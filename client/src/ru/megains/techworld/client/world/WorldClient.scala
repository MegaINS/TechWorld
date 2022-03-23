package ru.megains.techworld.client.world

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.entity.EntityOtherPlayerC
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.world.{ChunkPosition, World}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldClient(game: TechWorld) extends World {


    val entityList = new mutable.HashSet[Entity]()

    val entitySpawnQueue = new mutable.HashSet[Entity]()

    val entityHashSet = new mutable.HashMap[Int,Entity]()

    def addEntityToWorld(entityId: Int, entity: Entity): Unit = {
        //val var3 = getEntityByID(entityId)
       // if (var3 != null) setEntityDead(var3)
        entityList += entity
        entity.id = entityId
        if (!spawnEntityInWorld(entity)) entitySpawnQueue.add(entity)
        entityHashSet += entityId -> entity
    }
    val chunkProvider = new ChunkProviderClient(this)

    def doPreChunk(pos: ChunkPosition, loadChunk: Boolean): Any = {
        if (loadChunk) {
            chunkProvider.loadChunk(pos)
        } else {
            println(pos)
            chunkProvider.unloadChunk(pos)
            game.rendererGame.rendererWorld.unload(pos)
            // this.markBlockRangeForRenderUpdate(chunkX * 16, 0, chunkZ * 16, chunkX * 16 + 15, 256, chunkZ * 16 + 15)

        }
    }


    def update(): Unit = {

    }

    override def onEntityAdded(entity: Entity): Unit = {}

    override def getEntityByID(id: Int): Entity = if(game.player.id == id) game.player else entityHashSet.get(id).orNull
}
