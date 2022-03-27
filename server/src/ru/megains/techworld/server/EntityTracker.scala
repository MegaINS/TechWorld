package ru.megains.techworld.server

import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.entity.mob.{EntityCow}
import ru.megains.techworld.server.entity.EntityPlayerS
import ru.megains.techworld.server.world.WorldServer

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, HashMap, HashSet}

class EntityTracker(world: WorldServer) {

    val entityViewDistance = 512
    val trackedEntityIDs = new mutable.HashMap[Int, EntityTrackerEntry]
    val trackedEntities = new mutable.HashSet[EntityTrackerEntry]

    def addEntityToTracker(entity: Entity): Unit = {
        entity match {
            case entityPlayerS: EntityPlayerS =>
                addEntityToTracker(entity, 512, 2,true)
                trackedEntities.filter(_.entity != entityPlayerS).foreach(_.tryStartWachingThis(entityPlayerS))
            case _: EntityCow => addEntityToTracker(entity, 64, 3, true)
            //            case _: EntityFishHook => this.addEntityToTracker(par1Entity, 64, 5, true)
            //            case _: EntityArrow => this.addEntityToTracker(par1Entity, 64, 20, false)
            //            case _: EntitySmallFireball => this.addEntityToTracker(par1Entity, 64, 10, false)
            //            case _: EntityFireball => this.addEntityToTracker(par1Entity, 64, 10, false)
            //            case _: EntitySnowball => this.addEntityToTracker(par1Entity, 64, 10, true)
            //            case _: EntityEnderPearl => this.addEntityToTracker(par1Entity, 64, 10, true)
            //            case _: EntityEnderEye => this.addEntityToTracker(par1Entity, 64, 4, true)
            //            case _: EntityEgg => this.addEntityToTracker(par1Entity, 64, 10, true)
            //            case _: EntityPotion => this.addEntityToTracker(par1Entity, 64, 10, true)
            //            case _: EntityExpBottle => this.addEntityToTracker(par1Entity, 64, 10, true)
            //            case _: EntityItem => this.addEntityToTracker(par1Entity, 64, 20, true)
            //            case _: EntityMinecart => this.addEntityToTracker(par1Entity, 80, 3, true)
            //            case _: EntityBoat => this.addEntityToTracker(par1Entity, 80, 3, true)
            //            case _: EntitySquid => this.addEntityToTracker(par1Entity, 64, 3, true)
            //            case _: IAnimals => this.addEntityToTracker(par1Entity, 80, 3, true)
            //            case _: EntityDragon => this.addEntityToTracker(par1Entity, 160, 3, true)
            //            case _: EntityTNTPrimed => this.addEntityToTracker(par1Entity, 160, 10, true)
            //            case _: EntityFallingSand => this.addEntityToTracker(par1Entity, 160, 20, true)
            //            case _: EntityPainting => this.addEntityToTracker(par1Entity, 160, Integer.MAX_VALUE, false)
            //            case _: EntityXPOrb => this.addEntityToTracker(par1Entity, 160, 20, true)
            //            case _: EntityEnderCrystal => this.addEntityToTracker(par1Entity, 256, Integer.MAX_VALUE, false)
            case _ =>
        }
    }

    def addEntityToTracker(entity: Entity, blocksDistance: Int, updateFrequency: Int): Unit = {
        this.addEntityToTracker(entity, blocksDistance, updateFrequency, false)
    }

    def addEntityToTracker(entity: Entity, blocksDistance: Int, updateFrequency: Int, trackMotion: Boolean): Unit = {
        if (trackedEntityIDs.contains(entity.id)) throw new IllegalStateException("Entity is already tracked!")
        else {
            val entityTrackerEntry: EntityTrackerEntry = new EntityTrackerEntry(entity, if (blocksDistance > entityViewDistance) entityViewDistance else blocksDistance, updateFrequency, trackMotion)
            trackedEntities += entityTrackerEntry
            trackedEntityIDs += entity.id -> entityTrackerEntry
            entityTrackerEntry.sendEventsToPlayers(world.playerEntities)
        }
    }

    def updateTrackedEntities(): Unit = {
        trackedEntities
                .filter(
                    te => {
                        te.sendLocationToAllClients(world.playerEntities)
                        te.playerEntitiesUpdated && te.entity.isInstanceOf[EntityPlayerS]
                    })
                .map(_.entity.asInstanceOf[EntityPlayerS])
                .foreach(
                    eps =>
                        trackedEntities
                                .filter(_.entity != eps)
                                .foreach(_.tryStartWachingThis(eps))
                )
    }

    def removeEntityFromAllTrackingPlayers(entity:Entity): Unit = {
        entity match {
            case entityPlayer: EntityPlayerS =>
                removePlayerFromTrackers(entityPlayer)
            case _ =>
        }

        trackedEntityIDs.remove(entity.id) match {
            case Some(value) =>
                trackedEntities.remove(value)
                value.informAllAssociatedPlayersOfItemDestruction()
            case None =>
        }
    }

    def removePlayerFromTrackers(entity:EntityPlayerS):Unit = {
        trackedEntities.foreach(_.removePlayerFromTracker(entity))
    }
}
