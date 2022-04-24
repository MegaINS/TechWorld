package ru.megains.techworld.client.renderer

import org.joml.Matrix4f
import org.lwjgl.opengl.GL11.{GL_DEPTH_TEST, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_STENCIL_TEST, glBlendFunc, glDisable, glEnable, glLineWidth}
import ru.megains.techworld.client.TechWorld
import ru.megains.techworld.client.renderer.camera.PerspectiveCamera
import ru.megains.techworld.client.renderer.shader.ShaderManager
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.utils.Frustum
import ru.megains.techworld.client.world.WorldClient
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.utils.Logger

import java.nio.FloatBuffer

class RendererGame(game:TechWorld) extends Logger{

    val Z_NEAR: Float = 0.01f
    val Z_FAR: Float = 2000f
    val FOV: Float = Math.toRadians(45.0f).toFloat

    var worldShader: Shader = ShaderManager.worldShader
    var worldCamera: PerspectiveCamera = new PerspectiveCamera(FOV, game.window.width, game.window.height, Z_NEAR, Z_FAR)

    var skyBoxRenderer: RendererSkyBox = _
    var rayTraceRender: RayTraceRender = new RayTraceRender()
    var blockPlaceSetRender: BlockSetPositionRender = new BlockSetPositionRender()

    var rendererWorld:RendererWorld = _
    var world:WorldClient = _
    var entity:Entity = _

    def init(): Unit ={
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)
        glLineWidth( 	0.5f)

        skyBoxRenderer = new RendererSkyBox()
        rayTraceRender.init()
        skyBoxRenderer.init()
       // chunkBoundsRenderer.init()
    }


    def setWorld(newWorld:WorldClient): Unit ={
        world = newWorld
        if(rendererWorld!=null) rendererWorld = null
        rendererWorld = new RendererWorld(world)
        entity = game.player
    }

    def render(partialTicks:Double): Unit = {


//        worldCamera.setPerspective(Math.toRadians(game.settings.FOV).toFloat,game.window.width,game.window.height, Z_NEAR, Z_FAR)
//        worldCamera.setPos(
//            (entity.lastTickPosX + (entity.posX- entity.lastTickPosX)*partialTicks).toFloat,
//            (entity.lastTickPosY + (entity.posY- entity.lastTickPosY)*partialTicks).toFloat + entity.levelView,
//            (entity.lastTickPosZ + (entity.posZ- entity.lastTickPosZ)*partialTicks).toFloat)
//        worldCamera.setRot(-entity.rotPitch, (entity.lastRotYaw + (entity.rotYaw - entity.lastRotYaw)*partialTicks).toFloat, 0)

        worldCamera.setPerspective(Math.toRadians(game.settings.FOV).toFloat,game.window.width,game.window.height, Z_NEAR, Z_FAR)
        worldCamera.setPos(game.player.posX, game.player.posY + game.player.levelView, game.player.posZ)
        worldCamera.setRot(-game.player.rotPitch, game.player.rotYaw, 0)

       // log.info("posX " +(entity.posX- entity.lastTickPosX)*partialTicks)
       // val projectionMatrix: Matrix4f =  worldCamera.buildProjectionMatrix()
      //  val viewMatrix: Matrix4f = worldCamera.buildViewMatrix()

      //  projectionMatrix.get(_proj.clear().asInstanceOf[FloatBuffer])
      //  viewMatrix.get(_modl.clear().asInstanceOf[FloatBuffer])

      //  Frustum.calculateFrustum(_proj, _modl)

        skyBoxRenderer.render(worldCamera)

        ShaderManager.bindShader(worldShader)
        worldShader.setUniform(worldCamera)

        rendererWorld.render(game.player, worldShader,partialTicks)

        glDisable(GL_DEPTH_TEST)
        rayTraceRender.render(worldShader)
        blockPlaceSetRender.render(worldShader)
        glEnable(GL_DEPTH_TEST)
       // chunkBoundsRenderer.render(worldShader)

        ShaderManager.unbindShader()

    }

    def update(): Unit = {
        rayTraceRender.update(game.rayTrace)
        blockPlaceSetRender.update(game.blockSetPosition)
    }
}
