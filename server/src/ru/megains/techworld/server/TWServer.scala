package ru.megains.techworld.server

import ru.megains.techworld.common.network.{PacketProcessHandler, ServerStatusResponse}
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.server.network.NetworkSystem

import scala.reflect.io.Path

class TWServer(directory:Path) extends Logger{

    var running = true
    val networkSystem:NetworkSystem = new NetworkSystem(this)
    val statusResponse: ServerStatusResponse = new ServerStatusResponse
    val packetProcessHandler:PacketProcessHandler = new PacketProcessHandler
    var timeOfLastWarning = 0L
    val playerList:PlayerList = new PlayerList
    def start(): Boolean = {
        log.info("Starting TechWorld server  version 0.0.1")

        networkSystem.startLan(null, 20000)
        true
    }



    def tick(): Unit = {
        packetProcessHandler.tick()
        networkSystem.tick()
    }

    def stop(): Unit = {

    }

    def error(): Unit = {

    }

    def run(): Unit =
        if(start()){

            var lastTime: Long = TWServer.getCurrentTimeMillis
            var var50: Long = 0L
            while (running){
                val newTime: Long = TWServer.getCurrentTimeMillis
                var dTime: Long = newTime - lastTime


                if (dTime > 2000L && lastTime - timeOfLastWarning >= 15000L) {
                    log.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", dTime, dTime / 50L)
                    dTime = 2000L
                    timeOfLastWarning = lastTime
                }

                if (dTime < 0L) {
                    log.warn("Time ran backwards! Did the system time change?")
                    dTime = 0L
                }



                var50 += dTime
                lastTime = newTime


                while (var50 > 50L) {
                    var50 -= 50L
                    tick()

                }


                Thread.sleep(Math.max(1L, 50L - var50))
            }
            stop()
        }else {
            error()
        }

}
object TWServer {
    def getCurrentTimeMillis: Long = System.currentTimeMillis
}