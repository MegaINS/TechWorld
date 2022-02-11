package ru.megains.techworld.server

import scala.collection.mutable
import scala.io.StdIn

class ServerCommand(server: TWServer) extends Thread {

    val MB: Int = 1014*1024
    val commands: mutable.HashMap[String, Array[String] => Unit] = new mutable.HashMap[String, Array[String] => Unit]
    initCommand()


    override def run(): Unit = {

        while (server.running) {
            val data = StdIn.readLine().trim.split("\\s+")
            parseCommand(data)
        }
    }

    def parseCommand(command: Array[String]): Unit = {
        commands.getOrElse(command(0), (_: Array[String]) => println(s"Command [${command(0)}] not found"))(command)
    }

    def initCommand(): Unit = {
        commands += "" -> ((_: Array[String]) => {})
        commands += "stop" -> ((_: Array[String]) => server.running = false)
        commands += "help" -> ((_: Array[String]) => {
            println("All commands:")
            commands.keySet.filter(_ != "").toIndexedSeq.sorted.foreach(println)
        })
        commands += "players" -> ((_: Array[String]) => {
          //  println("Players online: " + server.playerList.nameToPlayerMap.size)
           // server.playerList.nameToPlayerMap.keySet.foreach(println)
        })
       // commands += "save" -> ((_: Array[String]) => server.saveAllWorlds(false))
        commands += "gt" -> ((command: Array[String]) => {
//            val player = server.playerList.getPlayerByName(command(1))
//            val gameType = GameType(command(2))
//            if (player != null) {
//                player.setGameType(gameType)
//                println("GameType update successful")
//            } else {
//                println("GameType update failed")
//            }
        })
        commands += "info" -> ((command: Array[String]) => {
//            val player = server.playerList.getPlayerByName(command(1))
//            if (player != null) {
//                println("Player position:")
//                println("x: " + player.posX)
//                println("y: " + player.posY)
//                println("z: " + player.posZ)
//                println("Game type: " + player.interactionManager.gameType.name)
//            } else {
//                println("Player offline")
//            }
        })

        commands += "status"->((_: Array[String]) => {
            val r: Runtime = Runtime.getRuntime
            System.out.println("Memory usage: total=" + r.totalMemory / MB + " MB, free=" + r.freeMemory / MB + " MB, max=" + r.maxMemory / MB + "f MB")
        })

    }


}
