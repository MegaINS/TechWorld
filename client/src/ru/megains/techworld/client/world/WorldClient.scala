package ru.megains.techworld.client.world

import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.entity.EntityOtherPlayerC
import ru.megains.techworld.common.block.{BlockAir, BlockState}
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.register.Blocks
import ru.megains.techworld.common.world.{ChunkPosition, World}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldClient(game: TechWorld) extends World {


    val entityList = new mutable.HashSet[Entity]()

    val entitySpawnQueue = new mutable.HashSet[Entity]()

    val entityHashSet = new mutable.HashMap[Int, Entity]()

    def addEntityToWorld(entityId: Int, entity: Entity): Unit = {
        val entityOld = getEntityByID(entityId)
        if (entityOld != null) removeEntity(entityOld)
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
            chunkProvider.unloadChunk(pos)
            game.rendererGame.rendererWorld.unload(pos)
            // this.markBlockRangeForRenderUpdate(chunkX * 16, 0, chunkZ * 16, chunkX * 16 + 15, 256, chunkZ * 16 + 15)

        }
    }
    def invalidateRegionAndSetBlock( block: BlockState): Boolean = {
        /*
          val i: Int = pos.getX
          val j: Int = pos.getY
           val k: Int = pos.getZ
         invalidateBlockReceiveRegion(i, j, k, i, j, k)
 */
        if(block.block == BlockAir){
            removeBlock(block)
        }else{
            setBlock(block)
        }


        // setBlock(pos, block, 3)
        true

    }

    override def update(): Unit = {

    }

    override def removeEntity(entity: Entity): Unit = {
        super.removeEntity(entity)
        entityList -= entity
    }

    override def onEntityRemoved(entity: Entity): Unit = {
        if (entityList.contains(entity)) {
            if (entity.isEntityAlive) {
                entitySpawnQueue.add(entity)
            } else {
                entityList.remove(entity)
            }
        }
    }

    def removeEntityFromWorld(id: Int): Entity = {
        entityHashSet.remove(id) match {
            case Some(entity) =>
                entityList.remove(entity)
                removeEntity(entity)
                entity
            case None => null
        }
    }

    override def onEntityAdded(entity: Entity): Unit = {}

    override def getEntityByID(id: Int): Entity = if (game.player != null && game.player.id == id) game.player else entityHashSet.getOrElse(id, null)
}
