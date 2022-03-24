package ru.megains.techworld.server

import ru.megains.techworld.common.block.BlockPos
import ru.megains.techworld.common.world.{Chunk, ChunkPosition}
import ru.megains.techworld.server.entity.EntityPlayerS
import ru.megains.techworld.server.world.WorldServer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class PlayerChunkMap(val worldServer: WorldServer) {


    val players: ArrayBuffer[EntityPlayerS] = new ArrayBuffer[EntityPlayerS]

    var pendingSendToPlayers: ArrayBuffer[PlayerChunkMapEntry] = new ArrayBuffer[PlayerChunkMapEntry]
    val playerInstanceList: ArrayBuffer[PlayerChunkMapEntry] = new ArrayBuffer[PlayerChunkMapEntry]
    var playersNeedingChunks: ArrayBuffer[PlayerChunkMapEntry] = new ArrayBuffer[PlayerChunkMapEntry]

    val playerInstances: mutable.HashMap[Long, PlayerChunkMapEntry] = new mutable.HashMap[Long, PlayerChunkMapEntry]()

    val playerInstancesToUpdate: mutable.HashSet[PlayerChunkMapEntry] = new mutable.HashSet[PlayerChunkMapEntry]()

    val playerViewRadiusH: Int = 1
    val playerViewRadiusW: Int = 1
    var previousTotalWorldTime: Long = 0

    val CAN_GENERATE_CHUNKS: EntityPlayerS => Boolean = (player: EntityPlayerS) => player != null


    def tick(): Unit = {
        val i: Long = System.currentTimeMillis()
        if (i - previousTotalWorldTime > 8000L) {
            previousTotalWorldTime = i

            playerInstanceList.foreach(
                player => {
                    player.update()
                    player.updateChunkInhabitedTime()
                }
            )
        }

        playerInstancesToUpdate.foreach(_.update())
        playerInstancesToUpdate.clear()
        /*
                        if (this.sortMissingChunks && i % 4L == 0L) {
                            this.sortMissingChunks = false
                            Collections.sort(this.playersNeedingChunks, new Comparator[PlayerChunkMapEntry]() {
                                def compare(p_compare_1_:

                                PlayerChunkMapEntry, p_compare_2_: PlayerChunkMapEntry): Int = {
                                    return ComparisonChain.start.compare(p_compare_1_.getClosestPlayerDistance, p_compare_2_.getClosestPlayerDistance).result
                                }
                            })
                        }
                        if (this.sortSendToPlayers && i % 4L == 2L) {
                            this.sortSendToPlayers = false
                            Collections.sort(this.pendingSendToPlayers, new Comparator[PlayerChunkMapEntry]() {
                                def compare(p_compare_1_:

                                PlayerChunkMapEntry, p_compare_2_: PlayerChunkMapEntry): Int = {
                                    return ComparisonChain.start.compare(p_compare_1_.getClosestPlayerDistance, p_compare_2_.getClosestPlayerDistance).result
                                }
                            })
                        }
        */

        if (playersNeedingChunks.nonEmpty) {
            val l: Long = System.nanoTime + 50000000L
            var k: Int = 49


            playersNeedingChunks = playersNeedingChunks.flatMap(
                playerChunkMapEntry => {
                    if (!(k < 0 || System.nanoTime > l) && (playerChunkMapEntry.chunk == null)) {
                        val flag: Boolean = playerChunkMapEntry.hasPlayerMatching(CAN_GENERATE_CHUNKS)
                        if (playerChunkMapEntry.providePlayerChunk(flag)) {
                            if (playerChunkMapEntry.sentToPlayers()) pendingSendToPlayers -= playerChunkMapEntry
                            k -= 1
                            None
                        } else {
                            Some(playerChunkMapEntry)
                        }
                    } else {
                        Some(playerChunkMapEntry)
                    }
                }

            )


        }

        if (pendingSendToPlayers.nonEmpty) {
            var i1: Int = 81

            pendingSendToPlayers = pendingSendToPlayers.flatMap(
                player => {
                    if (!(i1 < 0)) {
                        if (player.sentToPlayers()) {
                            i1 -= 1
                            None
                        } else {
                            Some(player)
                        }
                    } else {
                        Some(player)
                    }
                }
            )
        }


        if (players.isEmpty) {
            //   val worldprovider: WorldProvider = worldServer.provider
            //   if (!worldprovider.canRespawnHere) worldServer.chunkProvider.unloadAllChunks()
        }
    }

    def addPlayer(player: EntityPlayerS): Unit = {
        val x: Int = player.posX.toInt >> Chunk.offset
        val y: Int = player.posY.toInt >> Chunk.offset
        val z: Int = player.posZ.toInt >> Chunk.offset
        player.managedPosX = player.posX
        player.managedPosY = player.posY
        player.managedPosZ = player.posZ
        for (x1 <- x - playerViewRadiusW to x + playerViewRadiusW) {
            for (y1 <- y - playerViewRadiusH to y + playerViewRadiusH) {
                for (z1 <- z - playerViewRadiusW to z + playerViewRadiusW) {
                    getOrCreateEntry(x1, y1, z1).addPlayer(player)
                }
            }
        }

        players += player
        //  markSortPending()
    }

    def removePlayer(player: EntityPlayerS): Unit = {

        val x: Int = player.managedPosX.toInt >> Chunk.offset
        val y: Int = player.managedPosY.toInt >> Chunk.offset
        val z: Int = player.managedPosZ.toInt >> Chunk.offset

        for (x1 <- x - playerViewRadiusW to x + playerViewRadiusW) {
            for (y1 <- y - playerViewRadiusH to y + playerViewRadiusH) {
                for (z1 <- z - playerViewRadiusW to z + playerViewRadiusW) {
                    getEntry(x1, y1, z1).foreach(_.removePlayer(player))
                }
            }
        }


        players -= player
        // markSortPending()
    }

    def getOrCreateEntry(chunkX: Int, chunkY: Int, chunkZ: Int): PlayerChunkMapEntry = {

        getEntry(chunkX, chunkY, chunkZ).getOrElse(
            default = {
                val playerchunkmapentry: PlayerChunkMapEntry = new PlayerChunkMapEntry(this, chunkX, chunkY, chunkZ)
                playerInstances += Chunk.getIndex(chunkX, chunkY, chunkZ) -> playerchunkmapentry
                playerInstanceList += playerchunkmapentry
                if (playerchunkmapentry.chunk == null) playersNeedingChunks += playerchunkmapentry
                if (!playerchunkmapentry.sentToPlayers) pendingSendToPlayers += playerchunkmapentry
                playerchunkmapentry
            }
        )
    }

    def updateMountedMovingPlayer(player: EntityPlayerS): Unit = {

        val x: Int = player.posX.toInt >> Chunk.offset
        val y: Int = player.posY.toInt >> Chunk.offset
        val z: Int = player.posZ.toInt >> Chunk.offset
        val d0: Double = player.managedPosX - player.posX
        val d1: Double = player.managedPosY - player.posY
        val d2: Double = player.managedPosZ - player.posZ
        val d3: Double = d0 * d0 + d2 * d2 + d1 * d1

        if (d3 >= 8.0D) {
            val mX: Int = player.managedPosX.toInt >> Chunk.offset
            val mY: Int = player.managedPosY.toInt >> Chunk.offset
            val mZ: Int = player.managedPosZ.toInt >> Chunk.offset
            val xmX: Int = x - mX
            val ymY: Int = y - mY
            val zmZ: Int = z - mZ

            if (xmX != 0 || ymY != 0 || zmZ != 0) {
                for (x1 <- x - playerViewRadiusW to x + playerViewRadiusW;
                     y1 <- y - playerViewRadiusH to y + playerViewRadiusH;
                     z1 <- z - playerViewRadiusW to z + playerViewRadiusW) {
                    if (!overlaps(x1, y1, z1, mX, mY, mZ, playerViewRadiusW,playerViewRadiusH)) getOrCreateEntry(x1, y1, z1).addPlayer(player)
                    if (!overlaps(x1 - xmX, y1 - ymY, z1 - zmZ, x, y, z, playerViewRadiusW,playerViewRadiusH)) {
                        val playerchunkmapentry: PlayerChunkMapEntry = getEntry(x1 - xmX, y1 - ymY, z1 - zmZ).orNull
                        if (playerchunkmapentry != null) playerchunkmapentry.removePlayer(player)
                    }


                }

                player.managedPosX = player.posX
                player.managedPosY = player.posY
                player.managedPosZ = player.posZ
                // markSortPending()
            }
        }
    }

    private def overlaps(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int, radiusW: Int,radiusH: Int): Boolean = {
        val x: Int = x1 - x2
        val y: Int = y1 - y2
        val z: Int = z1 - z2

        x >= -radiusW && x <= radiusW && y >= -radiusH && y <= radiusH && z >= -radiusW && z <= radiusW
    }

    def markBlockForUpdate(pos: BlockPos): Unit = {
        val x: Int = pos.x >> Chunk.offset
        val y: Int = pos.y >> Chunk.offset
        val z: Int = pos.z >> Chunk.offset
        val playerchunkmapentry: PlayerChunkMapEntry = getEntry(x, y, z).get
        if (playerchunkmapentry != null) playerchunkmapentry.blockChanged(pos)
    }

    def removeEntry(entry: PlayerChunkMapEntry): Unit = {
        val chunkpos: ChunkPosition = entry.pos
        val i: Long = Chunk.getIndex(chunkpos.x, chunkpos.y, chunkpos.z)
        entry.updateChunkInhabitedTime()
        playerInstances -= i
        playerInstanceList -= entry
        playerInstancesToUpdate -= entry
        pendingSendToPlayers -= entry
        playersNeedingChunks -= entry
        val chunk: Chunk = entry.chunk

        if (chunk != null) worldServer.chunkProvider.unload(chunk)
    }

    def contains(chunkX: Int, chunkY: Int, chunkZ: Int): Boolean = {
        val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
        playerInstances.get(i) != null
    }

    def addEntry(entry: PlayerChunkMapEntry): Unit = {
        playerInstancesToUpdate.add(entry)
    }

    def isPlayerWatchingChunk(par1EntityPlayerMP: EntityPlayerS, chunkX: Int, chunkY: Int, chunkZ: Int): Boolean = {
        val playerChunkMapEntry =  getOrCreateEntry(chunkX, chunkY, chunkZ)
        if (playerChunkMapEntry == null) false
        else playerChunkMapEntry.players.contains(par1EntityPlayerMP) //&& !par1EntityPlayerMP.chunksToLoad.contains(PlayerInstance.getChunkLocation(playerChunkMapEntry))
    }

    def getEntry(x: Int, y: Int, z: Int): Option[PlayerChunkMapEntry] = playerInstances.get(Chunk.getIndex(x, y, z))
}
