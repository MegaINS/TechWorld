package ru.megains.techworld.client

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.entity.EntityPlayerC
import ru.megains.techworld.client.network.handler.NetHandlerPlayClient
import ru.megains.techworld.client.renderer.Mouse
import ru.megains.techworld.client.renderer.gui.GuiInGameMenu
import ru.megains.techworld.common.world.World

import scala.util.Random

class PlayerController(game: TechWorld, netHandler:NetHandlerPlayClient) {
    def sendQuittingDisconnectingPacket(): Unit = {
        netHandler.netManager.closeChannel("Quitting")
    }


    val gui = new GuiInGameMenu()

    def createClientPlayer(world: World): EntityPlayerC = {
        val player = new EntityPlayerC(/*game.playerName*/)
        player.connection = netHandler
        player.world = world
        player
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit ={
        if (action == GLFW_PRESS) {
            key match {
                case GLFW_KEY_E => game.guiManager.openPlayerInventory()


                case GLFW_KEY_ESCAPE =>
                    game.setScreen(gui)
               // case GLFW_KEY_R => gameScene.gameRenderer.worldRenderer.reRenderWorld()
                case GLFW_KEY_N => Mouse.setGrabbed(true)
                case GLFW_KEY_M =>  Mouse.setGrabbed(false)
                //case GLFW_KEY_F5 => gameScene.gameRenderer.chunkBoundsRenderer.isActive = !gameScene.gameRenderer.chunkBoundsRenderer.isActive

                case GLFW_KEY_U =>
                  //  val entityCube = new EntityBot()
                   // entityCube.setPosition(gameScene.player.posX + Random.nextInt(50) - 25, gameScene.player.posY + Random.nextInt(50), gameScene.player.posZ + Random.nextInt(50) - 25)
                   // gameScene.world.spawnEntityInWorld(entityCube)
                //case GLFW_KEY_L => renderer.isLight = !renderer.isLight
              //  case GLFW_KEY_C => gameScene.player.gameType = if(gameScene.player.gameType.isCreative) GameType.SURVIVAL else GameType.CREATIVE
                // case GLFW_KEY_O =>  guiManager.setGuiScreen(new GuiTestSet())
                case _ =>
            }
        }
    }

    def runTickMouse(button: Int, action: Int, mods: Int): Unit = {


//        if (button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS && gameScene.blockSetPosition!=null) {
//            gameScene.world.setBlock(gameScene.blockSetPosition)
//            gameScene.gameRenderer.worldRenderer.reRender(gameScene.blockSetPosition.x,gameScene.blockSetPosition.y,gameScene.blockSetPosition.z)
//        }
//
//        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS && gameScene.rayTrace.traceType != RayTraceType.VOID) {
//            gameScene.world.removeBlock(gameScene.rayTrace.blockState)
//            gameScene.gameRenderer.worldRenderer.reRender(gameScene.rayTrace.blockState.x,gameScene.rayTrace.blockState.y,gameScene.rayTrace.blockState.z)
//        }

    }
}
