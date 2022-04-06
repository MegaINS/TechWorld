package ru.megains.techworld.client

import org.lwjgl.glfw.GLFW._
import ru.megains.techworld.client.entity.EntityPlayerC
import ru.megains.techworld.client.network.handler.NetHandlerPlayClient
import ru.megains.techworld.client.renderer.Mouse
import ru.megains.techworld.client.renderer.gui.GuiInGameMenu
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.entity.{EntityPlayer, GameType}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.item.{ItemBlock, ItemPack}
import ru.megains.techworld.common.network.packet.play.client.{CPacketClickWindow, CPacketHeldItemChange, CPacketPlayerAction, CPacketPlayerMouse}
import ru.megains.techworld.common.utils.{Action, RayTraceType}
import ru.megains.techworld.common.world.World

import scala.util.Random

class PlayerController(game: TechWorld,val net: NetHandlerPlayClient) {
    def sendQuittingDisconnectingPacket(): Unit = {
        net.netManager.closeChannel("Quitting")
    }


    val gui = new GuiInGameMenu()

    def createClientPlayer(world: World): EntityPlayerC = {
        val player = new EntityPlayerC(/*game.playerName*/)
        player.connection = net
        player.world = world
        player
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            key match {
                case GLFW_KEY_E => game.guiManager.openPlayerInventory()


                case GLFW_KEY_ESCAPE =>
                    game.setScreen(gui)
                // case GLFW_KEY_R => gameScene.gameRenderer.worldRenderer.reRenderWorld()
                case GLFW_KEY_N => Mouse.setGrabbed(true)
                case GLFW_KEY_M => Mouse.setGrabbed(false)
                //case GLFW_KEY_F5 => gameScene.gameRenderer.chunkBoundsRenderer.isActive = !gameScene.gameRenderer.chunkBoundsRenderer.isActive

                case GLFW_KEY_U =>
                //  val entityCube = new EntityBot()
                // entityCube.setPosition(gameScene.player.posX + Random.nextInt(50) - 25, gameScene.player.posY + Random.nextInt(50), gameScene.player.posZ + Random.nextInt(50) - 25)
                // gameScene.world.spawnEntityInWorld(entityCube)
                //case GLFW_KEY_L => renderer.isLight = !renderer.isLight
                case GLFW_KEY_C =>
                    game.player.gameType = GameType.next(game.player.gameType.id)
                    net.sendPacket(new CPacketPlayerAction(Action.GAME_TYPE,game.player.gameType.id))
                // case GLFW_KEY_O =>  guiManager.setGuiScreen(new GuiTestSet())
                case _ =>
            }
        }
    }

    def runTickMouse(button: Int, action: Int, mods: Int): Unit = {


        if (button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS /* && game.blockSetPosition!=null*/ ) {
            rightClickMouse()
            //            game.world.setBlock(game.blockSetPosition)
            //            game.rendererGame.rendererWorld.reRender(game.blockSetPosition.x,game.blockSetPosition.y,game.blockSetPosition.z)
        }

        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS /*&& game.rayTrace.traceType != RayTraceType.VOID*/ ) {
            leftClickMouse()
            //            game.world.removeBlock(game.rayTrace.blockState)
            //            game.rendererGame.rendererWorld.reRender(game.rayTrace.blockState.x,game.rayTrace.blockState.y,game.rayTrace.blockState.z)
        }

    }

    def rightClickMouse(): Unit = {
        syncCurrentPlayItem()

        net.sendPacket(new CPacketPlayerMouse(1, 0, game.rayTrace, game.blockSetPosition))

        val rayTrace = game.rayTrace
        rayTrace.traceType match {
            case RayTraceType.BLOCK =>
                val itemstack: ItemStack = game.player.getHeldItem
                val block: BlockState = game.world.getBlock(rayTrace.blockState.x, rayTrace.blockState.y, rayTrace.blockState.z)

                if (block.onBlockActivated(game.world, game.player, itemstack, rayTrace.sideHit, rayTrace.hitVec.x.toFloat, rayTrace.hitVec.y.toFloat, rayTrace.hitVec.z.toFloat)) {

                } else {
                    if (itemstack != null) {
                        itemstack.item match {
                            case _: ItemBlock =>
                                val blockState = game.blockSetPosition
                                if (blockState != null) {
                                    itemstack.onItemUse(game.player, game.world, blockState, rayTrace.sideHit, rayTrace.hitVec.x.toFloat, rayTrace.hitVec.y.toFloat, rayTrace.hitVec.z.toFloat)
                                }

                            case _ =>
                        }
                    }


                }
            case RayTraceType.VOID =>
            case RayTraceType.ENTITY =>
        }
    }

    def leftClickMouse(): Unit = {
        syncCurrentPlayItem()
        net.sendPacket(new CPacketPlayerMouse(0, 0, game.rayTrace, game.blockSetPosition))

        game.rayTrace.traceType match {
            case RayTraceType.BLOCK =>

            case RayTraceType.VOID =>
            case RayTraceType.ENTITY =>
        }
    }


    var currentPlayerItem: Int = -1

    def syncCurrentPlayItem(): Unit = {
        val i: Int = game.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
            net.sendPacket(new CPacketHeldItemChange(currentPlayerItem))
        }
    }
    def windowClick(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        player.openContainer.mouseClicked(x, y, button, player)
        net.sendPacket(new CPacketClickWindow(x, y, button))
    }

}
