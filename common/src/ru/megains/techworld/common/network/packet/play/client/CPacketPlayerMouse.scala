package ru.megains.techworld.common.network.packet.play.client

import org.joml.{Vector3d, Vector3f}
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.network.handler.INetHandlerPlayServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer, PacketBufferS}
import ru.megains.techworld.common.register.Blocks
import ru.megains.techworld.common.utils.{Direction, RayTraceResult, RayTraceType}

class CPacketPlayerMouse extends Packet[INetHandlerPlayServer]{

    var button:Int = -1
    var action:Int = -1
    var rayTraceResult:RayTraceResult = _
    var blockState:BlockState = _
    def this(buttonIn:Int,actionIn:Int,rayTraceResultIn:RayTraceResult,blockStateIn:BlockState){
        this()
        button = buttonIn
        action = actionIn
        rayTraceResult = rayTraceResultIn
        blockState = blockStateIn
    }


    override def writePacketData(buf: PacketBuffer): Unit = {

        buf.writeInt(button)
        buf.writeInt(action)
        buf.writeByte(rayTraceResult.traceType.id)
        if( rayTraceResult.traceType!=RayTraceType.VOID){
            buf.writeBlockState(rayTraceResult.blockState)
            buf.writeDouble(rayTraceResult.hitVec.x)
            buf.writeDouble(rayTraceResult.hitVec.x)
            buf.writeDouble(rayTraceResult.hitVec.x)
            buf.writeByte(rayTraceResult.sideHit.id)
        }

        buf.writeBlockState(blockState)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        button = buf.readInt()
        action = buf.readInt()
        val rayTraceType = RayTraceType(buf.readByte())
        if( rayTraceType!=RayTraceType.VOID){
            rayTraceResult = new RayTraceResult(buf.readBlockState(),new Vector3d(buf.readDouble(),buf.readDouble(),buf.readDouble()),Direction.DIRECTIONAL_BY_ID(buf.readByte()))
        }else{
            rayTraceResult = RayTraceResult.VOID
        }

        blockState = buf.readBlockState()
    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processPlayerMouse(this)
    }
}
