package ru.megains.techworld.server

import scala.reflect.io.Path

object StartServer {


    def main(args: Array[String]): Unit = {
        try {
            val path = Path("W:/TechWorld/Server").toDirectory
            Thread.currentThread.setName("Server")
            val server = new TWServer(path)
            val serverCommand = new ServerCommand(server)
            serverCommand.setName("serverControl")
            serverCommand.setDaemon(true)
            serverCommand.start()

            server.run()
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }


}
