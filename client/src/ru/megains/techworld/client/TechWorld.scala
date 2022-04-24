package ru.megains.techworld.client

import org.joml.Vector3i
import org.lwjgl.glfw.GLFW.{GLFW_KEY_M, _}
import org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load
import ru.megains.techworld.client.entity.EntityPlayerC
import ru.megains.techworld.client.register.{Bootstrap, GameRegisterRender}
import ru.megains.techworld.client.renderer.entity.RenderEntityCow.log
import ru.megains.techworld.client.renderer.gui.{GuiManager, GuiPlayerSelect, GuiScreen}
import ru.megains.techworld.client.renderer.shader.ShaderManager
import ru.megains.techworld.client.renderer.texture.TextureManager
import ru.megains.techworld.client.renderer.world.ChunkRenderer
import ru.megains.techworld.client.renderer.{Mouse, RendererGame, RendererWorld, Window}
import ru.megains.techworld.client.utils.FrameCounter
import ru.megains.techworld.client.world.{ClientWorldEventHandler, WorldClient}
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.entity.GameType
import ru.megains.techworld.common.item.ItemBlock
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.network.{NetworkManager, PacketProcessHandler}
import ru.megains.techworld.common.utils.{Logger, RayTraceResult, RayTraceType, Timer}


class TechWorld(config: Configuration) extends Logger{



    var playerName = ""
    val settings:GameSettings = new GameSettings(this,config.filePath)
    val window: Window = new Window(config.width, config.height, config.title)
    var running: Boolean = true
    val timerFps = new Timer(60)
    val timerTps = new Timer(20)
    val guiManager = new GuiManager(this)
    val packetProcessHandler:PacketProcessHandler = new PacketProcessHandler

    var blockSetPosition: BlockState = _
    var rayTrace:RayTraceResult = RayTraceResult.VOID

    val moved = new Vector3i(0, 0, 0)
    var world:WorldClient = _
    val rendererGame:RendererGame = new RendererGame(this)
    var networkManager:NetworkManager = _
    var playerController:PlayerController = _
    var player:EntityPlayerC = _


    def start(): Unit = {
        if (init()) {
            gameLoop()
            close()
        } else {
            error()
        }

    }

    def init(): Boolean = {
        ChunkRenderer.game = this
        log.info("Init Windows")
        window.create(this)
        log.info("Init Mouse")
        Mouse.init(this)

        log.info("Init Bootstrap")
        Bootstrap.init()
        log.info("Init ShaderManager")
        ShaderManager.init()
        log.info("Init TextureManager")
        TextureManager.init()
        log.info("Init GuiManager")
        guiManager.init()
        log.info("Init RendererGame")
        rendererGame.init()
        FrameCounter.start()
        log.info("Start Game")
        guiManager.setScreen(new GuiPlayerSelect)
        true
    }

    def render(partialTicks:Double): Unit = {
        guiManager.render(partialTicks)
        window.update(partialTicks)


        if(world!= null) rendererGame.render(partialTicks)
        FrameCounter.gameRender()
    }

    def update(): Unit = {
        packetProcessHandler.tick()



        if(world!= null){
            world.update()
            if(!guiManager.isGuiOpen){
                moved.zero()
                if (glfwGetKey(window.id, GLFW_KEY_W) == GLFW_PRESS) moved.add(0, 0, 1)
                if (glfwGetKey(window.id, GLFW_KEY_S) == GLFW_PRESS) moved.add(0, 0, -1)
                if (glfwGetKey(window.id, GLFW_KEY_A) == GLFW_PRESS) moved.add(-1, 0, 0)
                if (glfwGetKey(window.id, GLFW_KEY_D) == GLFW_PRESS) moved.add(1, 0, 0)
                if (glfwGetKey(window.id, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) moved.add(0, -1, 0)
                if (glfwGetKey(window.id, GLFW_KEY_SPACE) == GLFW_PRESS) moved.add(0, 1, 0)

                player.inventory.changeStackSelect(Mouse.getDWheel * -1)

                player.moveForward = moved.z
                player.moveStrafing = moved.x
                player.isJumping = moved.y == 1

                player.turn(Mouse.getDX.toFloat,-Mouse.getDY.toFloat)
                player.update(moved.y)
            }

            if(!player.gameType.isSpectator){
                rayTrace = player.rayTrace(5)
            }else{
                rayTrace = RayTraceResult.VOID
            }


            if (rayTrace.traceType == RayTraceType.BLOCK) {

                val stack: ItemStack = player.inventory.getStackSelect
                if (stack != null) {
                    blockSetPosition = stack.item match {
                        case itemBlock: ItemBlock => itemBlock.block.getSelectPosition(world, player, rayTrace)
                        case _ => null
                    }
                } else blockSetPosition = null
            } else blockSetPosition = null


            rendererGame.update()
        }


        guiManager.update()
        FrameCounter.gameUpdate()
        Mouse.update()
        while (FrameCounter.isTimePassed(1000)) {


//            log.info(s"${
//                FrameCounter.frames
//            } fps, ${
//                FrameCounter.tick
//            } tick, ${
//                ChunkRenderer.chunkUpdate
//            } chunkUpdate, ${
//                ChunkRenderer.chunkRender / (if (FrameCounter.frames == 0) 1 else FrameCounter.frames)
//            } chunkRender, ${
//                ChunkRenderer.blockRender / (if (FrameCounter.frames == 0) 1 else FrameCounter.frames)
//            } blockRender ")
            ChunkRenderer.reset()
            FrameCounter.step(1000)
        }

    }

    def gameLoop(): Unit = {
//        timerFps.init()
//        timerTps.init()
        var start = TechWorld.getSystemTime
        while (running) {

            if (window.isClose) running = false


            timerFps.updateTimer()
            for (_ <- 0 until timerFps.elapsedTicks) {
                start = TechWorld.getSystemTime
                render(timerTps.renderPartialTicks)
               // log.info(s"render = ${start - TechWorld.getSystemTime}")
            }

            timerTps.updateTimer()
            for (_ <- 0 until timerTps.elapsedTicks) {
                start = TechWorld.getSystemTime
                update()
               // log.info(s"update = ${start - TechWorld.getSystemTime}")
            }
            Thread.sleep(1)

        }
    }



    def setScreen(screen: GuiScreen): Unit = {
        guiManager.setScreen(screen)
        if(screen!=null){
            Mouse.setGrabbed(false)
        }else{
            Mouse.setGrabbed(true)
        }
    }

    def setWorld(newWorld: WorldClient): Unit = {
        world = newWorld

        if(newWorld!= null){
            newWorld.addEventListener(new ClientWorldEventHandler(this, newWorld))
            if(player == null){
                player = playerController.createClientPlayer(world)
            }
            guiManager.setPlayer(player)
        }else{
            player = null
        }


        rendererGame.setWorld(world)
    }




    def runTickMouse(button: Int, action: Int, mods: Int): Unit = {
        if (guiManager.isGuiOpen) {
            guiManager.runTickMouse(button: Int, action, mods)
        } else {
            playerController.runTickMouse(button: Int, action, mods)
        }
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (guiManager.isGuiOpen) {
            guiManager.runTickKeyboard(key: Int, action: Int, mods: Int)
        } else {
            playerController.runTickKeyboard(key: Int, action: Int, mods: Int)
        }
    }


    def close(): Unit = {
        window.close()
    }

    def error(): Unit = {

    }

    def resize(width: Int, height: Int): Unit = {
        guiManager.resize(width, height)
    }


}
object TechWorld{

    def getSystemTime: Long = System.currentTimeMillis()
}
