package ru.megains.techworld.client

object StartClient extends App {


    val config = new Configuration() {
        filePath = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/client/resources/"
    }
    File.gamePath = config.filePath

    val techWorld: TechWorld = new TechWorld(config)

    techWorld.start()
}
