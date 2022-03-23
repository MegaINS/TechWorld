package ru.megains.techworld.server

import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.network.handler.INetHandler
import ru.megains.techworld.common.network.packet.Packet
import ru.megains.techworld.common.network.packet.play.server.{SPacketEntity, SPacketEntityTeleport, SPacketEntityVelocity, SPacketSpawnPlayer}
import ru.megains.techworld.server.entity.EntityPlayerS

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


class EntityTrackerEntry(val entity: Entity, blocksDistance: Int, updateFrequency: Int, sendVelocityUpdates: Boolean) {

    var lastScaledXPosition = 0
    var lastScaledYPosition = 0
    var lastScaledZPosition = 0

    var motionX: Float = 0.0f
    var motionY: Float = 0.0f
    var motionZ: Float = 0.0f

    var trackingPlayers = new mutable.HashSet[EntityPlayerS]


    def tryStartWachingThis(entityPlayerS: EntityPlayerS): Unit = {
        if (entityPlayerS != entity) {
            val var2 = entityPlayerS.posX - (lastScaledXPosition / 32).toDouble
            val var4 = entityPlayerS.posZ - (lastScaledZPosition / 32).toDouble
            if (var2 >= (-blocksDistance).toDouble && var2 <= blocksDistance.toDouble && var4 >= (-blocksDistance).toDouble && var4 <= blocksDistance.toDouble) {
                println(entityPlayerS.name + "1")
                if (!trackingPlayers.contains(entityPlayerS) && isPlayerWatchingThisChunk(entityPlayerS)) {
                    println(entityPlayerS.name + "2")
                    trackingPlayers.add(entityPlayerS)
                    val packet: Packet[_ <: INetHandler] = getPacketForThisEntity
                    entityPlayerS.connection.sendPacket(packet)
                    motionX = entity.motionX
                    motionY = entity.motionY
                    motionZ = entity.motionZ
                    if (sendVelocityUpdates /*&& !packet.isInstanceOf[Packet24MobSpawn]*/) entityPlayerS.connection.sendPacket(new SPacketEntityVelocity(entity.id, entity.motionX, entity.motionY, entity.motionZ))
                    //if (entity.ridingEntity != null) entityPlayerS.connection.sendPacket(new Packet39AttachEntity(entity, entity.ridingEntity))
                    //                val var7 = entity.getLastActiveItems
                    //                if (var7 != null) for (var8 <- 0 until var7.length) {
                    //                    entityPlayerS.connection.sendPacket(new Packet5PlayerInventory(entity.id, var8, var7(var8)))
                    //                }
                    entity match {
                        case var11: EntityPlayer =>
                        // if (var11.isPlayerSleeping) entityPlayerS.connection.sendPacket(new Packet17Sleep(entity, 0, Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ)))
                        case _ =>
                    }
                    //                entity match {
                    //                    case var12: EntityLiving =>
                    //                        val var9 = var12.getActivePotionEffects.iterator
                    //                        while ( {
                    //                            var9.hasNext
                    //                        }) {
                    //                            val var10 = var9.next.asInstanceOf[PotionEffect]
                    //                            entityPlayerS.connection.sendPacket(new Packet41EntityEffect(entity.id, var10))
                    //                        }
                    //                    case _ =>
                    //                }
                }
                else if (trackingPlayers.contains(entityPlayerS)) {
                    trackingPlayers.remove(entityPlayerS)
                    //  entityPlayerS.destroyedItemsNetCache.add(Integer.valueOf(entity.id))
                }
            }
        }
    }

    def sendEventsToPlayers(entityPlayers: ArrayBuffer[EntityPlayer]): Unit = {
        entityPlayers.map(_.asInstanceOf[EntityPlayerS]).foreach(tryStartWachingThis)
    }

    def isPlayerWatchingThisChunk(entityPlayerS: EntityPlayerS): Boolean = entityPlayerS.worldServer.playerManager.isPlayerWatchingChunk(entityPlayerS, entity.chunkCoordX, entity.chunkCoordY, entity.chunkCoordZ)


    def getPacketForThisEntity: Packet[_ <: INetHandler] = {
        // if (entity.isDead) System.out.println("Fetching addPacket for removed entity")
        //        if (entity.isInstanceOf[EntityItem]) {
        //            val var8 = entity.asInstanceOf[EntityItem]
        //            val var9 = new Packet21PickupSpawn(var8)
        //            var8.posX = var9.xPosition.toDouble / 32.0D
        //            var8.posY = var9.yPosition.toDouble / 32.0D
        //            var8.posZ = var9.zPosition.toDouble / 32.0D
        //            var9
        //        }
        //        else
        /*  if (entity.isInstanceOf[EntityPlayerS])*/ new SPacketSpawnPlayer(entity.asInstanceOf[EntityPlayer])
        //        else {
        //            if (entity.isInstanceOf[EntityMinecart]) {
        //                val var1 = entity.asInstanceOf[EntityMinecart]
        //                if (var1.minecartType == 0) return new Packet23VehicleSpawn(entity, 10)
        //                if (var1.minecartType == 1) return new Packet23VehicleSpawn(entity, 11)
        //                if (var1.minecartType == 2) return new Packet23VehicleSpawn(entity, 12)
        //            }
        //            if (entity.isInstanceOf[EntityBoat]) new Packet23VehicleSpawn(entity, 1)
        //            else if (!entity.isInstanceOf[IAnimals] && !entity.isInstanceOf[EntityDragon]) if (entity.isInstanceOf[EntityFishHook]) {
        //                val var7 = entity.asInstanceOf[EntityFishHook].angler
        //                new Packet23VehicleSpawn(entity, 90, if (var7 != null) var7.id
        //                else entity.id)
        //            }
        //            else if (entity.isInstanceOf[EntityArrow]) {
        //                val var6 = entity.asInstanceOf[EntityArrow].shootingEntity
        //                new Packet23VehicleSpawn(entity, 60, if (var6 != null) var6.id
        //                else entity.id)
        //            }
        //            else if (entity.isInstanceOf[EntitySnowball]) new Packet23VehicleSpawn(entity, 61)
        //            else if (entity.isInstanceOf[EntityPotion]) new Packet23VehicleSpawn(entity, 73, entity.asInstanceOf[EntityPotion].getPotionDamage)
        //            else if (entity.isInstanceOf[EntityExpBottle]) new Packet23VehicleSpawn(entity, 75)
        //            else if (entity.isInstanceOf[EntityEnderPearl]) new Packet23VehicleSpawn(entity, 65)
        //            else if (entity.isInstanceOf[EntityEnderEye]) new Packet23VehicleSpawn(entity, 72)
        //            else {
        //                var var2 = null
        //                if (entity.isInstanceOf[EntitySmallFireball]) {
        //                    val var5 = entity.asInstanceOf[EntitySmallFireball]
        //                    var2 = null
        //                    if (var5.shootingEntity != null) var2 = new Packet23VehicleSpawn(entity, 64, var5.shootingEntity.id)
        //                    else var2 = new Packet23VehicleSpawn(entity, 64, 0)
        //                    var2.speedX = (var5.accelerationX * 8000.0D).toInt
        //                    var2.speedY = (var5.accelerationY * 8000.0D).toInt
        //                    var2.speedZ = (var5.accelerationZ * 8000.0D).toInt
        //                    var2
        //                }
        //                else if (entity.isInstanceOf[EntityFireball]) {
        //                    val var4 = entity.asInstanceOf[EntityFireball]
        //                    var2 = null
        //                    if (var4.shootingEntity != null) var2 = new Packet23VehicleSpawn(entity, 63, entity.asInstanceOf[EntityFireball].shootingEntity.id)
        //                    else var2 = new Packet23VehicleSpawn(entity, 63, 0)
        //                    var2.speedX = (var4.accelerationX * 8000.0D).toInt
        //                    var2.speedY = (var4.accelerationY * 8000.0D).toInt
        //                    var2.speedZ = (var4.accelerationZ * 8000.0D).toInt
        //                    var2
        //                }
        //                else if (entity.isInstanceOf[EntityEgg]) new Packet23VehicleSpawn(entity, 62)
        //                else if (entity.isInstanceOf[EntityTNTPrimed]) new Packet23VehicleSpawn(entity, 50)
        //                else if (entity.isInstanceOf[EntityEnderCrystal]) new Packet23VehicleSpawn(entity, 51)
        //                else if (entity.isInstanceOf[EntityFallingSand]) {
        //                    val var3 = entity.asInstanceOf[EntityFallingSand]
        //                    new Packet23VehicleSpawn(entity, 70, var3.blockID | var3.field_70285_b << 16)
        //                }
        //                else if (entity.isInstanceOf[EntityPainting]) new Packet25EntityPainting(entity.asInstanceOf[EntityPainting])
        //                else if (entity.isInstanceOf[EntityXPOrb]) new Packet26EntityExpOrb(entity.asInstanceOf[EntityXPOrb])
        //                else throw new IllegalArgumentException("Don\'t know how to add " + entity.getClass + "!")
        //            }
        //            else {
        //                lastHeadMotion = MathHelper.floor_float(entity.func_70079_am * 256.0F / 360.0F)
        //                new Packet24MobSpawn(entity.asInstanceOf[EntityLiving])
        //            }
        //  }
    }

    var playerEntitiesUpdated = false

    var ridingEntity = false

    var ticks = 0
    var lastYaw = 0
    var lastPitch = 0
    var posX = 0f
    var posY = 0f
    var posZ = 0f
    var ticksSinceLastForcedTeleport = 0
    var isDataInitialized = false

    def sendLocationToAllClients(entityPlayers: ArrayBuffer[EntityPlayer]): Unit = {
        playerEntitiesUpdated = false
        if (!isDataInitialized || entity.getDistanceSq(posX, posY, posZ) > 16.0D) {
            posX = entity.posX
            posY = entity.posY
            posZ = entity.posZ
            isDataInitialized = true
            playerEntitiesUpdated = true
            sendEventsToPlayers(entityPlayers)
        }
        //        if ((ridingEntity != entity.ridingEntity) || entity.ridingEntity != null && ticks % 60 == 0) {
        //            ridingEntity = entity.ridingEntity
        //            func_151259_a(new S1BPacketEntityAttach(0, entity, entity.ridingEntity))
        //        }
        //        if (entity.isInstanceOf[EntityItemFrame] && ticks % 10 == 0) {
        //            val var23 = entity.asInstanceOf[EntityItemFrame]
        //            val var24 = var23.getDisplayedItem
        //            if (var24 != null && var24.getItem.isInstanceOf[ItemMap]) {
        //                val var26 = Items.filled_map.getMapData(var24, entity.worldObj)
        //                val var27 = entityPlayers.iterator
        //                while ( {
        //                    var27.hasNext
        //                }) {
        //                    val var28 = var27.next.asInstanceOf[EntityPlayer]
        //                    val var29 = var28.asInstanceOf[EntityPlayerMP]
        //                    var26.updateVisiblePlayers(var29, var24)
        //                    val var30 = Items.filled_map.func_150911_c(var24, entity.worldObj, var29)
        //                    if (var30 != null) var29.playerNetServerHandler.sendPacket(var30)
        //                }
        //            }
        //            func_111190_b()
        //        }
        //        else
        if (ticks % updateFrequency == 0 || entity.isAirBorne /*|| entity.getDataWatcher.hasChanges*/ ) {
            var var2 = 0
            var var3 = 0
            //if (entity.ridingEntity == null) {
            ticksSinceLastForcedTeleport += 1
            //var2 = entity.entitySize.multiplyBy32AndRound(entity.posX)
            var2 = Math.floor(entity.posX * 32.0D).toInt
            var3 = Math.floor(entity.posY * 32.0D).toInt
            // val var4 = entity.entitySize.multiplyBy32AndRound(entity.posZ)
            val var4 = Math.floor(entity.posZ * 32.0D).toInt
            val var5 = Math.floor(entity.rotYaw * 256.0F / 360.0F).toInt
            val var6 = Math.floor(entity.rotPitch * 256.0F / 360.0F).toInt
            val var7 = var2 - lastScaledXPosition
            val var8 = var3 - lastScaledYPosition
            val var9 = var4 - lastScaledZPosition
            var var10:Packet[_ <: INetHandler] = null
            val var11 = Math.abs(var7) >= 4 || Math.abs(var8) >= 4 || Math.abs(var9) >= 4 || ticks % 60 == 0
            val var12 = Math.abs(var5 - lastYaw) >= 4 || Math.abs(var6 - lastPitch) >= 4
            if (ticks > 0 /*|| entity.isInstanceOf[EntityArrow]*/ ) if (var7 >= -128 && var7 < 128 && var8 >= -128 && var8 < 128 && var9 >= -128 && var9 < 128 && ticksSinceLastForcedTeleport <= 400 && !ridingEntity) if (var11 && var12) {
                var10 = new SPacketEntity.SPacketEntityLookMove(entity.id, var7.toByte, var8.toByte, var9.toByte, var5.toByte, var6.toByte)
            }

            else if (var11) var10 = new SPacketEntity.SPacketEntityRelMove(entity.id, var7.toByte, var8.toByte, var9.toByte)
            else if (var12) var10 = new SPacketEntity.SPacketEntityLook(entity.id, var5.toByte, var6.toByte)
            else {
                ticksSinceLastForcedTeleport = 0
                var10 = new SPacketEntityTeleport(entity.id, var2, var3, var4, var5.toByte, var6.toByte)
            }
            if (sendVelocityUpdates) {
                val var13 = entity.motionX - motionX
                val var15 = entity.motionY - motionY
                val var17 = entity.motionZ - motionZ
                val var19 = 0.02D
                val var21 = var13 * var13 + var15 * var15 + var17 * var17
                if (var21 > var19 * var19 || var21 > 0.0D && entity.motionX == 0.0D && entity.motionY == 0.0D && entity.motionZ == 0.0D) {
                    motionX = entity.motionX
                    motionY = entity.motionY
                    motionZ = entity.motionZ
                    sendPacketAllTrackingPlayers(new SPacketEntityVelocity(entity.id, motionX, motionY, motionZ))
                }
            }
            if (var10 != null) sendPacketAllTrackingPlayers(var10)
            func_111190_b()
            if (var11) {
                lastScaledXPosition = var2
                lastScaledYPosition = var3
                lastScaledZPosition = var4
            }
            if (var12) {
                lastYaw = var5
                lastPitch = var6
            }
            ridingEntity = false
            //}
            //            else {
            //                var2 = MathHelper.floor_float(entity.rotationYaw * 256.0F / 360.0F)
            //                var3 = MathHelper.floor_float(entity.rotationPitch * 256.0F / 360.0F)
            //                val var25 = Math.abs(var2 - lastYaw) >= 4 || Math.abs(var3 - lastPitch) >= 4
            //                if (var25) {
            //                    func_151259_a(new S14PacketEntity.S16PacketEntityLook(entity.getEntityId, var2.toByte, var3.toByte))
            //                    lastYaw = var2
            //                    lastPitch = var3
            //                }
            //                lastScaledXPosition = entity.entitySize.multiplyBy32AndRound(entity.posX)
            //                lastScaledYPosition = MathHelper.floor_double(entity.posY * 32.0D)
            //                lastScaledZPosition = entity.entitySize.multiplyBy32AndRound(entity.posZ)
            //                func_111190_b()
            //                ridingEntity = true
            //            }
            //            var2 = Math.floor(entity.getRotationYawHead * 256.0F / 360.0F)
            //            if (Math.abs(var2 - lastHeadMotion) >= 4) {
            //                func_151259_a(new S19PacketEntityHeadLook(entity, var2.toByte))
            //                lastHeadMotion = var2
            //            }
            entity.isAirBorne = false
        }
        ticks += 1
//        if (entity.velocityChanged) {
//            func_151261_b(new S12PacketEntityVelocity(entity))
//            entity.velocityChanged = false
//        }
    }


//    def func_151261_b(packet:Packet[_ <: INetHandler]): Unit = {
//        sendPacketAllTrackingPlayers(packet)
//        entity match {
//            case eps: EntityPlayerS => eps.connection.sendPacket(packet)
//            case _ =>
//        }
//    }

    def sendPacketAllTrackingPlayers(packet:Packet[_ <: INetHandler]): Unit = {
        trackingPlayers.foreach(_.connection.sendPacket(packet))
    }

    private def func_111190_b(): Unit = {
//        val var1 = entity.getDataWatcher
//        if (var1.hasChanges) this.func_151261_b(new S1CPacketEntityMetadata(this.myEntity.getEntityId, var1, false))
//        if (this.myEntity.isInstanceOf[EntityLivingBase]) {
//            val var2 = this.myEntity.asInstanceOf[EntityLivingBase].getAttributeMap.asInstanceOf[ServersideAttributeMap]
//            val var3 = var2.getAttributeInstanceSet
//            if (!var3.isEmpty) this.func_151261_b(new S20PacketEntityProperties(this.myEntity.getEntityId, var3))
//            var3.clear()
//        }
    }
}
