package ru.megains.techworld.client.entity

import ru.megains.techworld.client.network.handler.NetHandlerPlayClient
import ru.megains.techworld.client.renderer.world.ChunkRenderer.game
import ru.megains.techworld.common.entity.{Entity, EntityPlayer}
import ru.megains.techworld.common.item.itemstack.ItemStack
import ru.megains.techworld.common.network.packet.play.client.CPacketPlayer
import ru.megains.techworld.common.register.GameRegister

import scala.util.Random

class EntityPlayerC() extends EntityPlayer{
    private var lastReportedPosX: Double = .0
    private var lastReportedPosY: Double = .0
    private var lastReportedPosZ: Double = .0
    private var lastReportedYaw: Double = .0
    private var lastReportedPitch: Double = .0
    private var positionUpdateTicks: Int = 0
    private var prevOnGround: Boolean = false
    var connection: NetHandlerPlayClient =_

    GameRegister.getItems.foreach(i => inventory.addItemStackToInventory(new ItemStack(i, Random.nextInt(100))))
    def update(y: Int): Unit = {


        motionY = y
        motionX = 0
        motionZ = 0
        calculateMotion(moveForward, moveStrafing, 1.14f)

        move(motionX,motionY,motionZ)
        onUpdateWalkingPlayer()
    }



    def onUpdateWalkingPlayer(): Unit = {
        val onGround = true
        if (isCurrentViewEntity) {
            val axisalignedbb  = body
            val d0: Double = posX - lastReportedPosX
            val d1: Double = axisalignedbb.minY - lastReportedPosY
            val d2: Double = posZ - lastReportedPosZ
            val d3: Double = rotYaw - lastReportedYaw
            val d4: Double = rotPitch - lastReportedPitch
            positionUpdateTicks += 1
            val flag2: Boolean = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || positionUpdateTicks >= 20
            val flag3: Boolean = d3 != 0.0D || d4 != 0.0D


            if (flag2 && flag3) connection.sendPacket(new CPacketPlayer.PositionRotation(posX, axisalignedbb.minY, posZ, rotYaw, rotPitch, onGround))
            else if (flag2) connection.sendPacket(new CPacketPlayer.Position(posX, axisalignedbb.minY, posZ, onGround))
            else if (flag3) connection.sendPacket(new CPacketPlayer.Rotation(rotYaw, rotPitch, onGround))
            else if (prevOnGround != onGround) connection.sendPacket(new CPacketPlayer(onGround))
            if (flag2) {
                lastReportedPosX = posX
                lastReportedPosY = axisalignedbb.minY
                lastReportedPosZ = posZ
                positionUpdateTicks = 0
            }
            if (flag3) {
                lastReportedYaw = rotYaw
                lastReportedPitch = rotPitch
            }
            prevOnGround = onGround

        }
    }
    protected def isCurrentViewEntity: Boolean = game.player == this
}
