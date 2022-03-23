package ru.megains.techworld.client.renderer

import org.lwjgl.opengl.GL11.{GL_CULL_FACE, GL_DEPTH_TEST, GL_STENCIL_TEST, glEnable}
import ru.megains.techworld.client.entity.EntityPlayerC
import ru.megains.techworld.client.register.GameRegisterRender
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.world.ChunkRenderer
import ru.megains.techworld.client.renderer.world.ChunkRenderer.game
import ru.megains.techworld.client.world.WorldClient
import ru.megains.techworld.common.block.BlockType
import ru.megains.techworld.common.entity.EntityPlayer
import ru.megains.techworld.common.world.{Chunk, ChunkPosition, World}

import scala.collection.mutable

class RendererWorld(world: WorldClient) {

    val chunkRenderer = new mutable.HashMap[Long, ChunkRenderer]()
    var playerRenderChunks: Seq[ChunkRenderer] = Seq[ChunkRenderer]()
    var lastX: Int = Int.MinValue
    var lastY: Int = Int.MinValue
    var lastZ: Int = Int.MinValue

    def render(player: EntityPlayerC, shader: Shader): Unit = {
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)


        ChunkRenderer.resetRenderTime()

        val chunks = getRenderChunks(player)

        chunks.foreach(_.render(BlockType.SOLID.id, shader))
        //chunks.foreach(_.render(BlockType.GLASS.id, shader))

        // glDisable(GL_DEPTH_TEST)
        // glDisable(GL_CULL_FACE)
        // glLineWidth(2f)
         renderEntities(shader: Shader)
        // glLineWidth(0.5f)
    }

    def getRenderChunks(entityPlayer: EntityPlayer): Seq[ChunkRenderer] = {
        //        // TODO:  OPTIMIZE
        val posX: Int = (entityPlayer.posX / Chunk.blockSize - (if (entityPlayer.posX < 0) 1 else 0)).toInt
        val posY: Int = (entityPlayer.posY / Chunk.blockSize - (if (entityPlayer.posY < 0) 1 else 0)).toInt
        val posZ: Int = (entityPlayer.posZ / Chunk.blockSize - (if (entityPlayer.posZ < 0) 1 else 0)).toInt
        val flag = playerRenderChunks.exists(_.isVoid)


        if (posX != lastX ||
                posY != lastY ||
                posZ != lastZ ||
                playerRenderChunks.isEmpty ||
                flag) {
            lastX = posX
            lastY = posY
            lastZ = posZ


            val renderW = game.settings.RENDER_DISTANCE_WIDTH
            val renderH = game.settings.RENDER_DISTANCE_HEIGHT
            val R = renderW * renderW

            val futures = (posX - renderW to posX + renderW).map(
                x1 =>
                    //  Future {
                    (posY - renderH to posY + renderH).flatMap(y1 =>
                        (posZ - renderW to posZ + renderW).map(
                            (x1, y1, _)
                        )
                                //.filter(a => ((a._1 - posX) * (a._1 - posX) + (a._3 - posZ) * (a._3 - posZ) <= R))
                                .map(a => getRenderChunk(a._1, a._2, a._3))
                    )
                //}
            )

            playerRenderChunks = futures.flatten.sortBy((c) => Math.abs(posY - c.yPos) + (Math.abs(posX - c.xPos) + Math.abs(posZ - c.zPos)) / 10)
        }

        // Seq(getRenderChunk(0,0,0))
        playerRenderChunks //.filter(chunk => Frustum.cubeInFrustum(chunk.xPos, chunk.yPos, chunk.zPos, chunk.xPos + Chunk.blockSize, chunk.yPos + Chunk.blockSize, chunk.zPos + Chunk.blockSize))
    }

    def createChunkRen(x: Int, y: Int, z: Int): ChunkRenderer = {
        new ChunkRenderer(ChunkPosition(x, y, z), world)
    }

    def getRenderChunk(x: Int, y: Int, z: Int): ChunkRenderer = {
        val i: Long = Chunk.getIndex(x, y, z)
        if (chunkRenderer.contains(i) && !chunkRenderer(i).isVoid) chunkRenderer(i)
        else {
            val chunkRen = createChunkRen(x, y, z)
            chunkRenderer += i -> chunkRen
            chunkRen
        }
    }

    def reRender(pos: ChunkPosition): Unit = {
        //TODO
        val x: Int = pos.x >> Chunk.offset
        val y: Int = pos.y >> Chunk.offset
        val z: Int = pos.z >> Chunk.offset
        getRenderChunk(x, y, z).reRender()
        getRenderChunk(x + 1, y, z).reRender()
        getRenderChunk(x - 1, y, z).reRender()
        getRenderChunk(x, y + 1, z).reRender()
        getRenderChunk(x, y - 1, z).reRender()
        getRenderChunk(x, y, z + 1).reRender()
        getRenderChunk(x, y, z - 1).reRender()
    }

    def unload(pos: ChunkPosition): Unit ={
        chunkRenderer.get(Chunk.getIndex(pos)) match {
            case Some(value) =>
                value.cleanUp()
                chunkRenderer -= Chunk.getIndex(pos)
            case None =>
        }
    }

    def renderEntities(shader: Shader): Unit = {


        world.entityList.filter {
            case /*_: EntityItem |*/ _: EntityPlayerC => false
            case _ => true
        } /*.splitter*/ .foreach(
            entity => {
                // if (frustum.cubeInFrustum(entity.body)) {
                // val modelViewMatrix = transformation.buildEntityModelViewMatrix(entity)
                // Renderer.currentShaderProgram.setUniform("model", modelViewMatrix)
                GameRegisterRender.getEntityRender(entity).render(entity, world, shader)
            }
            // }
        )
    }
}
