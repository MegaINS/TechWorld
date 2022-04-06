package ru.megains.techworld.common.network.packet.play.client

import ru.megains.techworld.common.network.handler.INetHandlerPlayServer
import ru.megains.techworld.common.network.packet.{Packet, PacketBuffer}
import ru.megains.techworld.common.utils.Action
import ru.megains.techworld.common.utils.Action.Action
import ru.megains.techworld.common.utils.EnumActionResult.Value

class CPacketPlayerAction  extends Packet[INetHandlerPlayServer] {


    var action: Action = Action.NONE
    var data: Array[Int]  = _


    def this(actionIn: Action.Value,dataIn:Int*) {
        this()
        action = actionIn
        data = dataIn.toArray
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeByte(action.id)
        buf.writeInt(data.length)
        data.foreach(buf.writeInt)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        action =Action(buf.readByte())
        data = new Array[Int](buf.readInt())
        for(i<-data.indices){
            data(i) =buf.readInt()
        }


    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processPlayerAction(this)
    }




}
