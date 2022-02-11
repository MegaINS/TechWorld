package ru.megains.techworld.common.network

import scala.collection.mutable

class PacketProcessHandler extends IPacketProcessHandler{

    val futureTaskQueue: mutable.Queue[()=>Unit] = new mutable.Queue[()=>Unit]

    def tick(): Unit = {
        futureTaskQueue synchronized {
            while (futureTaskQueue.nonEmpty){
                val a =  futureTaskQueue.dequeue()
                if(a!= null){
                    a()
                }
            }

        }
    }

    override def addPacket(packet: () => Unit): Unit = {
        futureTaskQueue += packet
    }

}
